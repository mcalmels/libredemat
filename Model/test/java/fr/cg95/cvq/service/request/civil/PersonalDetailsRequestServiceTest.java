package fr.cg95.cvq.service.request.civil;

import fr.cg95.cvq.business.users.*;
import fr.cg95.cvq.business.request.*;
import fr.cg95.cvq.business.authority.*;
import fr.cg95.cvq.business.document.*;
import fr.cg95.cvq.business.request.civil.*;
import fr.cg95.cvq.exception.*;
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.service.document.IDocumentTypeService;
import fr.cg95.cvq.service.request.IRequestService;
import fr.cg95.cvq.service.request.civil.IPersonalDetailsRequestService;
import fr.cg95.cvq.util.Critere;

import fr.cg95.cvq.testtool.ServiceTestCase;
import fr.cg95.cvq.testtool.BusinessObjectsFactory;

import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;

/**
 * Generated by Velocity if not present, can be edited safely !
 */
public class PersonalDetailsRequestServiceTest extends ServiceTestCase {

    protected IPersonalDetailsRequestService iPersonalDetailsRequestService;

    protected void onSetUp() throws Exception {
    	super.onSetUp();
        iPersonalDetailsRequestService = 
            (IPersonalDetailsRequestService) getBean(StringUtils.uncapitalize("PersonalDetailsRequest") + "Service");
    }

    protected PersonalDetailsRequest fillMeARequest() throws CvqException {

        PersonalDetailsRequest request = new PersonalDetailsRequest();
              request.setDeathFirstNames("DeathFirstNames");
                  request.setFormat(CertificateFormatType.FULL_COPY);
                request.setCopies(BigInteger.valueOf(1));
              if ("BirthPostalCode".length() > 2)
        request.setBirthPostalCode("BirthPostalCode".substring(0, 2));
      else
        request.setBirthPostalCode("BirthPostalCode");
                  if ("BirthCity".length() > 32)
        request.setBirthCity("BirthCity".substring(0, 32));
      else
        request.setBirthCity("BirthCity");
                request.setMarriageDate(new Date());
              if ("MarriageHusbandLastName".length() > 38)
        request.setMarriageHusbandLastName("MarriageHusbandLastName".substring(0, 38));
      else
        request.setMarriageHusbandLastName("MarriageHusbandLastName");
                    request.setMarriageWifeFirstNames("MarriageWifeFirstNames");
                request.setDeathDate(new Date());
            request.setBirthDate(new Date());
                request.setRequesterQualityPrecision("RequesterQualityPrecision");
                  if ("DeathPostalCode".length() > 2)
        request.setDeathPostalCode("DeathPostalCode".substring(0, 2));
      else
        request.setDeathPostalCode("DeathPostalCode");
                  if ("FatherLastName".length() > 38)
        request.setFatherLastName("FatherLastName".substring(0, 38));
      else
        request.setFatherLastName("FatherLastName");
                  request.setRelationship(RelationshipType.HUSBAND);
                    request.setMotherFirstNames("MotherFirstNames");
                    request.setFatherFirstNames("FatherFirstNames");
                  if ("MarriagePostalCode".length() > 2)
        request.setMarriagePostalCode("MarriagePostalCode".substring(0, 2));
      else
        request.setMarriagePostalCode("MarriagePostalCode");
                  request.setCertificate(CertificateType.BIRTH);
                  if ("MotherMaidenName".length() > 38)
        request.setMotherMaidenName("MotherMaidenName".substring(0, 38));
      else
        request.setMotherMaidenName("MotherMaidenName");
                  if ("DeathCity".length() > 32)
        request.setDeathCity("DeathCity".substring(0, 32));
      else
        request.setDeathCity("DeathCity");
                    request.setMarriageHusbandFirstNames("MarriageHusbandFirstNames");
                request.setUsage("Usage");
              request.setRequesterQuality(RequesterQualityType.REQUESTER);
                  if ("MarriageCity".length() > 32)
        request.setMarriageCity("MarriageCity".substring(0, 32));
      else
        request.setMarriageCity("MarriageCity");
                  if ("MarriageWifeLastName".length() > 38)
        request.setMarriageWifeLastName("MarriageWifeLastName".substring(0, 38));
      else
        request.setMarriageWifeLastName("MarriageWifeLastName");
                    request.setBirthFirstNames("BirthFirstNames");
                  if ("DeathLastName".length() > 38)
        request.setDeathLastName("DeathLastName".substring(0, 38));
      else
        request.setDeathLastName("DeathLastName");
                  if ("BirthLastName".length() > 38)
        request.setBirthLastName("BirthLastName".substring(0, 38));
      else
        request.setBirthLastName("BirthLastName");
      
        // Means Of Contact
        MeansOfContact meansOfContact = iMeansOfContactService.getMeansOfContactByType(
                    MeansOfContactEnum.EMAIL);
        request.setMeansOfContact(meansOfContact);
        
        PersonalDetailsRequestFeeder.feed(request);
        
        return request;
    }
        	
    protected void completeValidateAndDelete(PersonalDetailsRequest request) 
    	throws CvqException, java.io.IOException {
    	
        // add a document to the request
        ///////////////////////////////

        Document doc = new Document();
        doc.setEcitizenNote("Ma carte d'identitÃ© !");
        doc.setDepositOrigin(DepositOrigin.ECITIZEN);
        doc.setDepositType(DepositType.PC);
        doc.setHomeFolderId(request.getHomeFolderId());
        doc.setIndividualId(request.getRequesterId());
        doc.setDocumentType(iDocumentTypeService.getDocumentTypeByType(IDocumentTypeService.IDENTITY_RECEIPT_TYPE));
        Long documentId = iDocumentService.create(doc);
        iPersonalDetailsRequestService.addDocument(request.getId(), documentId);
        Set<RequestDocument> documentsSet =
            iPersonalDetailsRequestService.getAssociatedDocuments(request.getId());
        assertEquals(documentsSet.size(), 1);

        // FIXME : test list of pending / in-progress registrations
        Critere testCrit = new Critere();
        testCrit.setAttribut(Request.SEARCH_BY_HOME_FOLDER_ID);
        testCrit.setComparatif(Critere.EQUALS);
        testCrit.setValue(request.getHomeFolderId());
        Set<Critere> testCritSet = new HashSet<Critere>();
        testCritSet.add(testCrit);
        List<Request> allRequests = iRequestService.get(testCritSet, null, null, -1, 0);
        assertNotNull(allRequests);

        // close current session and re-open a new one
        continueWithNewTransaction();
        
        SecurityContext.setCurrentSite(localAuthorityName,
                                        SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithCategoriesRoles);
        iPersonalDetailsRequestService.complete(request.getId());
        iPersonalDetailsRequestService.validate(request.getId());

        // close current session and re-open a new one
        continueWithNewTransaction();
        
        byte[] generatedCertificate = iRequestService.getCertificate(request.getId(),
                                                                     RequestState.PENDING);

        if (generatedCertificate == null)
            fail("No certificate found");
            
        //     Write tele-service xml data file
        File xmlFile = File.createTempFile("tmp" + request.getId(), ".xml");
        FileOutputStream xmlFos = new FileOutputStream(xmlFile);
        xmlFos.write(iRequestService.getById(request.getId()).modelToXmlString().getBytes());

        File file = File.createTempFile("tmp" + request.getId(), ".pdf");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(generatedCertificate);

        // close current session and re-open a new one
        continueWithNewTransaction();
        
        // delete request
        iPersonalDetailsRequestService.delete(request.getId());
    }

    public void testWithHomeFolderPojo()
    		throws CvqException, CvqObjectNotFoundException,
                java.io.FileNotFoundException, java.io.IOException {

         SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);

         // create a vo card request (to create home folder and associates)
         CreationBean cb = gimmeAnHomeFolder();

         SecurityContext.setCurrentEcitizen(cb.getLogin());

         // get the home folder id
         HomeFolder homeFolder = iHomeFolderService.getById(cb.getHomeFolderId());
         assertNotNull(homeFolder);
         Long homeFolderId = homeFolder.getId();
         assertNotNull(homeFolderId);

         // fill and create the request
         //////////////////////////////

         PersonalDetailsRequest request = fillMeARequest();
         request.setRequesterId(SecurityContext.getCurrentUserId());
         PersonalDetailsRequestFeeder.setSubject(request, 
             iPersonalDetailsRequestService.getSubjectPolicy(), null, homeFolder);
         
         Long requestId =
              iPersonalDetailsRequestService.create(request);

         PersonalDetailsRequest requestFromDb =
        	 	(PersonalDetailsRequest) iPersonalDetailsRequestService.getById(requestId);
         assertEquals(requestId, requestFromDb.getId());
         assertNotNull(requestFromDb.getRequesterId());
         assertNotNull(requestFromDb.getRequesterLastName());
         if (requestFromDb.getSubjectId() != null)
             assertNotNull(requestFromDb.getSubjectLastName());
         
         completeValidateAndDelete(requestFromDb);

         HomeFolder homeFolderAfterDelete = iHomeFolderService.getById(homeFolderId);
         assertNotNull(homeFolderAfterDelete);
         assertNotNull(iHomeFolderService.getHomeFolderResponsible(homeFolderAfterDelete.getId()));
         
         SecurityContext.resetCurrentSite();
    }


    public void testWithoutHomeFolder()
        throws CvqException, CvqObjectNotFoundException,
               java.io.FileNotFoundException, java.io.IOException {

	      if (!iPersonalDetailsRequestService.supportUnregisteredCreation())
	         return;

	      startTransaction();
	
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        
        PersonalDetailsRequest request = fillMeARequest();

        Address address = BusinessObjectsFactory.gimmeAdress("12", "Rue d'Aligre", "Paris", "75012");
        Adult requester =
            BusinessObjectsFactory.gimmeAdult(TitleType.MISTER, "LASTNAME", "requester", address,
                                              FamilyStatusType.MARRIED);
        requester.setPassword("requester");
        requester.setAdress(address);
        iHomeFolderService.addHomeFolderRole(requester, RoleEnum.HOME_FOLDER_RESPONSIBLE);
        PersonalDetailsRequestFeeder.setSubject(request, 
            iPersonalDetailsRequestService.getSubjectPolicy(), requester, null);

        Long requestId =
             iPersonalDetailsRequestService.create(request, requester, requester);
        
        // close current session and re-open a new one
        continueWithNewTransaction();
        
        // start testing request creation
        /////////////////////////////////

        PersonalDetailsRequest requestFromDb =
            (PersonalDetailsRequest) iPersonalDetailsRequestService.getById(requestId);
        assertEquals(requestId, requestFromDb.getId());
        assertNotNull(requestFromDb.getRequesterId());
        assertNotNull(requestFromDb.getRequesterLastName());
        if (requestFromDb.getSubjectId() != null)
            assertNotNull(requestFromDb.getSubjectLastName());
        
        Long homeFolderId = requestFromDb.getHomeFolderId();
        Long requesterId = requestFromDb.getRequesterId();

        // close current session and re-open a new one
        continueWithNewTransaction();
        
        completeValidateAndDelete(requestFromDb);
        
        // close current session and re-open a new one
        continueWithNewTransaction();
        
        try {
            iHomeFolderService.getById(homeFolderId);
            fail("should not have found home folder");
        } catch (CvqObjectNotFoundException confe) {
            // great, that was expected
        }
        try {
            iIndividualService.getById(requesterId);
            fail("should not have found requester");
        } catch (CvqObjectNotFoundException confe) {
            // great, that was expected
        }

        SecurityContext.resetCurrentSite();
        
        commitTransaction();
    }
}
