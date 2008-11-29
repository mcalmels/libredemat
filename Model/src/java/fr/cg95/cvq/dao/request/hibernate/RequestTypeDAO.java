package fr.cg95.cvq.dao.request.hibernate;

import java.util.List;

import org.hibernate.Criteria;

import fr.cg95.cvq.business.request.RequestType;
import fr.cg95.cvq.dao.hibernate.GenericDAO;
import fr.cg95.cvq.dao.hibernate.HibernateUtil;
import fr.cg95.cvq.dao.request.IRequestTypeDAO;
import fr.cg95.cvq.util.Critere;

/**
 * Hibernate implementation of the {@link IRequestTypeDAO} interface.
 * 
 * @author bor@zenexity.fr
 */
public class RequestTypeDAO extends GenericDAO implements IRequestTypeDAO {

    public List<RequestType> listAll() {
        StringBuffer sb = new StringBuffer()
            .append("from RequestType as requestType");
        return HibernateUtil.getSession().createQuery(sb.toString()).list();
    }

    public List<RequestType> listByCategoryAndState(final Long categoryId, final Boolean active) {
        Criteria crit = HibernateUtil.getSession().createCriteria(RequestType.class);
        if (categoryId != null)
            crit.createCriteria("category").add(Critere.compose("id", categoryId, Critere.EQUALS));
        if (active != null)
            crit.add(Critere.compose("active", active, Critere.EQUALS));
        return crit.list();
    }

    public RequestType findByLabel(final String requestTypeLabel) {
        Criteria crit = HibernateUtil.getSession().createCriteria(RequestType.class);
        crit.add(Critere.compose("label", requestTypeLabel, Critere.EQUALS));
        return (RequestType) crit.uniqueResult();
    }
}
