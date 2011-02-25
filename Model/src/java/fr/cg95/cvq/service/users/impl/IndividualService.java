package fr.cg95.cvq.service.users.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import fr.cg95.cvq.authentication.IAuthenticationService;
import fr.cg95.cvq.business.users.ActorState;
import fr.cg95.cvq.business.users.Adult;
import fr.cg95.cvq.business.users.Child;
import fr.cg95.cvq.business.users.Individual;
import fr.cg95.cvq.business.users.UserAction;
import fr.cg95.cvq.dao.hibernate.HibernateUtil;
import fr.cg95.cvq.dao.users.IAdultDAO;
import fr.cg95.cvq.dao.users.IChildDAO;
import fr.cg95.cvq.dao.users.IIndividualDAO;
import fr.cg95.cvq.exception.CvqAuthenticationFailedException;
import fr.cg95.cvq.exception.CvqBadPasswordException;
import fr.cg95.cvq.exception.CvqDisabledAccountException;
import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.exception.CvqObjectNotFoundException;
import fr.cg95.cvq.service.users.IIndividualService;
import fr.cg95.cvq.util.Critere;
import fr.cg95.cvq.util.ValidationUtils;

/**
 * Implementation of the {@link IIndividualService} service.
 *
 * @author Benoit Orihuela (bor@zenexity.fr)
 */
public class IndividualService implements IIndividualService {

    static Logger logger = Logger.getLogger(IndividualService.class);

    private static Collection<String> bookedLogin =
        Collections.synchronizedCollection(new ArrayList<String>());

    private IIndividualDAO individualDAO;

    private IAdultDAO adultDAO;

    private IChildDAO childDAO;

    private IAuthenticationService authenticationService;

    @Override
    public List<Individual> get(final Set<Critere> criteriaSet, final String orderedBy,
        final boolean searchAmongArchived) {
        return individualDAO.search(criteriaSet, orderedBy,
            searchAmongArchived ? null : new ActorState[] { ActorState.ARCHIVED });
    }

    @Override
    public List<Individual> get(Set<Critere> criterias, Map<String,String> sortParams,
                                    Integer max, Integer offset) {
        return this.individualDAO.search(criterias,sortParams,max,offset);
    }

    @Override
    public Integer getCount(Set<Critere> criterias) {
        return this.individualDAO.searchCount(criterias);
    }

    @Override
    public Individual getById(final Long id)
        throws CvqObjectNotFoundException {
        return (Individual) individualDAO.findById(Individual.class, id);
    }

    @Override
    public Adult getAdultById(final Long id)
        throws CvqObjectNotFoundException {
        return (Adult) adultDAO.findById(Adult.class, id);
    }

    @Override
    public Child getChildById(final Long id)
        throws CvqObjectNotFoundException {
        return (Child) childDAO.findById(Child.class, id);
    }

    @Override
    public Adult getByLogin(final String login) {
        return adultDAO.findByLogin(login);
    }

    @Override
    public Individual getByFederationKey(final String federationKey) {
        return individualDAO.findByFederationKey(federationKey);
    }

    @Override
    public void modifyPassword(final Adult adult, final String oldPassword, 
            final String newPassword)
        throws CvqException, CvqBadPasswordException {

        // check the old password
        try {
            authenticationService.authenticate(adult.getLogin(), oldPassword);
        } catch (CvqAuthenticationFailedException cafe) {
            logger.warn("modifyPassword() old password does not match for user "
                        + adult.getLogin());
            throw new CvqBadPasswordException("Old password does not match for user "
                                   + adult.getLogin());
        } catch (CvqDisabledAccountException cdae) {
            logger.info("modifyPassword() account is disabled, still authorizing password change");
        }

        // it's ok, set the new one
        authenticationService.resetAdultPassword(adult, newPassword);
    }

    private synchronized String computeNewLogin(Adult adult) {
        String baseLogin =  Normalizer.normalize(
            (adult.getFirstName().trim() + '.' + adult.getLastName().trim())
                .replaceAll("\\s", "-")
                .replaceAll("'", ""),
            Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]","").toLowerCase();
        logger.debug("computeNewLogin() searching from " + baseLogin);
        TreeSet<Integer> indexesSet = new TreeSet<Integer>();
        for (String login : individualDAO.getSimilarLogins(baseLogin)) {
            String index = login.substring(baseLogin.length());
            if (index == null || index.equals(""))
                index = "1";
            try {
                Integer intIndex = Integer.valueOf(index);
                indexesSet.add(intIndex);
            } catch (NumberFormatException nfe) {
                // the tail was not an integer, ignore it
            }
        }

        int finalIndex = 0;
        if (!indexesSet.isEmpty()) {
            finalIndex = indexesSet.last();
        }
        logger.debug("computeNewLogin() got final index " + finalIndex);
        
        String loginFromDb = null;
        if (finalIndex == 0)
            loginFromDb = baseLogin;
        else
            loginFromDb = baseLogin + String.valueOf(++finalIndex);
        logger.debug("computeNewLogin() got new login from DB " + loginFromDb);
        String finalLogin = loginFromDb;
        if (bookedLogin.contains(loginFromDb)) {
            String currentIndex = loginFromDb.substring(baseLogin.length());
            int currIdx = 1;
            if (currentIndex != null && !currentIndex.equals(""))
                currIdx = (Integer.parseInt(currentIndex) > finalIndex ? 
                        Integer.parseInt(currentIndex) : finalIndex);
            String loginToTest = null;
            do {
                currIdx++;
                loginToTest = baseLogin + String.valueOf(currIdx);
            } while (bookedLogin.contains(loginToTest));
            finalLogin = loginToTest;
        }
        bookedLogin.add(finalLogin);
        return finalLogin;
    }

    @Override
    public Long create(Adult adult, boolean assignLogin)
        throws CvqException {
        if (assignLogin) {
            adult.setLogin(computeNewLogin(adult));
        }
        if (adult.getPassword() != null)
            adult.setPassword(authenticationService.encryptPassword(adult.getPassword()));
        return create(adult);
    }


    @Override
    public Long create(Child child) {
        return create((Individual)child);
    }

    private Long create(Individual individual) {
        individual.setState(ActorState.PENDING);
        individual.setCreationDate(new Date());
        Long id = individualDAO.create(individual);
        individual.getHomeFolder().getActions().add(new UserAction(UserAction.Type.CREATION, id));
        individualDAO.update(individual.getHomeFolder());
        return id;
    }

    @Override
    public void modify(final Individual individual, JsonObject atom)
        throws CvqException {

        if (individual == null)
            throw new CvqException("No adult object provided");
        else if (individual.getId() == null)
            throw new CvqException("Cannot modify a transient individual");
        JsonObject payload = new JsonObject();
        payload.add("atom", atom);
        UserAction action = new UserAction(UserAction.Type.MODIFICATION, individual.getId(), payload);
        // FIXME hack for specific business when changing a user's first or last name
        if ("identity".equals(atom.get("name").getAsString())) {
            JsonObject fields = atom.get("fields").getAsJsonObject();
            if (fields.has("firstName") || fields.has("lastName")) {
                String firstName = fields.has("firstName") ?
                    fields.get("firstName").getAsJsonObject().get("from").getAsString()
                    : individual.getFirstName();
                String lastName = fields.has("lastName") ?
                        fields.get("lastName").getAsJsonObject().get("from").getAsString()
                        : individual.getLastName();
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(JsonObject.class, new JsonDeserializer<JsonObject>() {
                    @Override
                    public JsonObject deserialize(JsonElement arg0, @SuppressWarnings("unused") Type arg1,
                        @SuppressWarnings("unused") JsonDeserializationContext arg2)
                        throws JsonParseException {
                        return arg0.getAsJsonObject();
                    }
                });
                Gson gson = gsonBuilder.create();
                payload = gson.fromJson(action.getData(), JsonObject.class);
                payload.get("target").getAsJsonObject()
                    .addProperty("name", firstName + ' ' + lastName);
                if (individual.getId().equals(payload.get("user").getAsJsonObject().get("id").getAsLong())) {
                    payload.get("user").getAsJsonObject()
                        .addProperty("name", firstName + ' ' + lastName);
                }
                if (individual instanceof Adult) {
                    Adult adult = (Adult)individual;
                    if (!StringUtils.isEmpty(adult.getLogin())) {
                        JsonObject login = new JsonObject();
                        login.addProperty("from", adult.getLogin());
                        adult.setLogin(computeNewLogin(adult));
                        login.addProperty("to", adult.getLogin());
                        payload.get("atom").getAsJsonObject().get("fields").getAsJsonObject()
                            .add("login", login);
                        // hack to refresh security context
                        HibernateUtil.getSession().flush();
                    }
                }
                action.setData(gson.toJson(payload));
            }
        }
        individual.getHomeFolder().getActions().add(action);
        individualDAO.update(individual.getHomeFolder());
    }

    @Override
    public void updateIndividualState(Individual individual, ActorState newState) {
        individual.setState(newState);
        individualDAO.update(individual);
        JsonObject payload = new JsonObject();
        payload.addProperty("state", newState.toString());
        individual.getHomeFolder().getActions().add(
            new UserAction(UserAction.Type.STATE_CHANGE, individual.getId(), payload));
        individualDAO.update(individual.getHomeFolder());
    }

    public void setIndividualDAO(IIndividualDAO individualDAO) {
        this.individualDAO = individualDAO;
    }

    public void setAdultDAO(IAdultDAO adultDAO) {
        this.adultDAO = adultDAO;
    }

    public void setChildDAO(IChildDAO childDAO) {
        this.childDAO = childDAO;
    }

    public void setAuthenticationService(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public List<String> validate(Adult adult, boolean login)
        throws ClassNotFoundException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Validator validator = new Validator();
        validator.disableAllProfiles();
        validator.enableProfile("default");
        if (login) {
            validator.enableProfile("login");
        }
        Map<String, List<String>> invalidFields = new LinkedHashMap<String, List<String>>();
        for (ConstraintViolation violation : validator.validate(adult)) {
            ValidationUtils.collectInvalidFields(violation, invalidFields, "", "");
        }
        List<String> result = new ArrayList<String>();
        for (String profile : new String[] {"", "login"}) {
            if (invalidFields.get(profile) != null) {
                result.addAll(invalidFields.get(profile));
            }
        }
        return result;
    }

    @Override
    public List<String> validate(Child child)
        throws ClassNotFoundException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        Validator validator = new Validator();
        validator.disableAllProfiles();
        validator.enableProfile("default");
        Map<String, List<String>> invalidFields = new LinkedHashMap<String, List<String>>();
        for (ConstraintViolation violation : validator.validate(child)) {
            ValidationUtils.collectInvalidFields(violation, invalidFields, "", "");
        }
        return invalidFields.get("") != null ? invalidFields.get("") : Collections.<String>emptyList();
    }
}

