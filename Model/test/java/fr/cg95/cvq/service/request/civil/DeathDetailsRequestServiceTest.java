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
import fr.cg95.cvq.service.request.RequestTestCase;
import fr.cg95.cvq.util.Critere;

import fr.cg95.cvq.testtool.BusinessObjectsFactory;

import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;

/**
 * Generated by Velocity if not present, can be edited safely !
 */
public class DeathDetailsRequestServiceTest extends RequestTestCase {

    protected IRequestService requestService;

    @Override
    protected void onSetUp() throws Exception {
        super.onSetUp();
        requestService = 
            (IRequestService) getBean(StringUtils.uncapitalize("DeathDetailsRequest") + "Service");
    }

    protected DeathDetailsRequest fillMeARequest() {

        DeathDetailsRequest request = new DeathDetailsRequest();
              request.setDeathFirstNames("DeathFirstNames");
                  if ("DeathCity".length() > 32)
        request.setDeathCity("DeathCity".substring(0, 32));
      else
        request.setDeathCity("DeathCity");
                  request.setFormat(DeathCertificateFormatType.FULL_COPY);
                request.setCopies(BigInteger.valueOf(1));
            request.setComment("Comment");
              request.setMotive(DeathCertificateMotiveType.NOTARY_ACT);
                  if ("DeathPostalCode".length() > 2)
        request.setDeathPostalCode("DeathPostalCode".substring(0, 2));
      else
        request.setDeathPostalCode("DeathPostalCode");
                  if ("DeathLastName".length() > 38)
        request.setDeathLastName("DeathLastName".substring(0, 38));
      else
        request.setDeathLastName("DeathLastName");
                request.setDeathDate(new Date());
  
        // Means Of Contact
        MeansOfContact meansOfContact = iMeansOfContactService.getMeansOfContactByType(
                    MeansOfContactEnum.EMAIL);
        request.setMeansOfContact(meansOfContact);
        
        DeathDetailsRequestFeeder.feed(request);
        
        return request;
    }
        	
    protected void completeValidateAndDelete(DeathDetailsRequest request) 
    	throws CvqException, java.io.IOException {
    	
        // add a document to the request
        ///////////////////////////////

        Document doc = new Document();
        doc.setEcitizenNote("Ma carte d'identitÃ© !");
        doc.setDepositOrigin(DepositOrigin.ECITIZEN);
        doc.setDepositType(DepositType.PC);
        doc.setHomeFolderId(request.getHomeFolderId());
        doc.setIndividualId(request.getRequesterId());
        doc.setDocumentType(documentTypeService.getDocumentTypeByType(IDocumentTypeService.IDENTITY_RECEIPT_TYPE));
        Long documentId = documentService.create(doc);
        requestDocumentService.addDocument(request.getId(), documentId);
        Set<RequestDocument> documentsSet =
            requestDocumentService.getAssociatedDocuments(request.getId());
        assertEquals(documentsSet.size(), 1);

        // FIXME : test list of pending / in-progress registrations
        Critere testCrit = new Critere();
        testCrit.setAttribut(Request.SEARCH_BY_HOME_FOLDER_ID);
        testCrit.setComparatif(Critere.EQUALS);
        testCrit.setValue(request.getHomeFolderId());
        Set<Critere> testCritSet = new HashSet<Critere>();
        testCritSet.add(testCrit);
        List<Request> allRequests = requestSearchService.get(testCritSet, null, null, -1, 0);
        assertNotNull(allRequests);

        // close current session and re-open a new one
        continueWithNewTransaction();
        
        SecurityContext.setCurrentSite(localAuthorityName,
                                        SecurityContext.BACK_OFFICE_CONTEXT);
        SecurityContext.setCurrentAgent(agentNameWithCategoriesRoles);
        requestWorkflowService.updateRequestState(request.getId(), RequestState.COMPLETE, null);
        requestWorkflowService.updateRequestState(request.getId(), RequestState.VALIDATED, null);

        // close current session and re-open a new one
        continueWithNewTransaction();
        
        byte[] generatedCertificate = requestSearchService.getCertificate(request.getId(),
                                                                     RequestState.PENDING);

        if (generatedCertificate == null)
            fail("No certificate found");
            
        //     Write tele-service xml data file
        File xmlFile = File.createTempFile("tmp" + request.getId(), ".xml");
        FileOutputStream xmlFos = new FileOutputStream(xmlFile);
        xmlFos.write(requestSearchService.getById(request.getId()).modelToXmlString().getBytes());

        File file = File.createTempFile("tmp" + request.getId(), ".pdf");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(generatedCertificate);

        // close current session and re-open a new one
        continueWithNewTransaction();
        
        // delete request
        requestWorkflowService.delete(request.getId());
    }

    public void testWithHomeFolderPojo()
    		throws CvqException, CvqObjectNotFoundException,
                java.io.FileNotFoundException, java.io.IOException {

         SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);

         // create a vo card request (to create home folder and associates)
         CreationBean cb = gimmeAnHomeFolderWithRequest();

         SecurityContext.setCurrentEcitizen(cb.getLogin());

         // get the home folder id
         HomeFolder homeFolder = iHomeFolderService.getById(cb.getHomeFolderId());
         assertNotNull(homeFolder);
         Long homeFolderId = homeFolder.getId();
         assertNotNull(homeFolderId);

         // fill and create the request
         //////////////////////////////

         DeathDetailsRequest request = fillMeARequest();
         request.setRequesterId(SecurityContext.getCurrentUserId());
         request.setHomeFolderId(homeFolderId);
         DeathDetailsRequestFeeder.setSubject(request, 
             requestService.getSubjectPolicy(), null, homeFolder);
         
         Long requestId =
              requestWorkflowService.create(request);

         DeathDetailsRequest requestFromDb =
        	 	(DeathDetailsRequest) requestSearchService.getById(requestId);
         assertEquals(requestId, requestFromDb.getId());
         assertNotNull(requestFromDb.getRequesterId());
         assertNotNull(requestFromDb.getRequesterLastName());
         if (requestFromDb.getSubjectId() != null)
             assertNotNull(requestFromDb.getSubjectLastName());
         
         completeValidateAndDelete(requestFromDb);

         HomeFolder homeFolderAfterDelete = iHomeFolderService.getById(homeFolderId);
         assertNotNull(homeFolderAfterDelete);
         assertNotNull(iHomeFolderService.getHomeFolderResponsible(homeFolderAfterDelete.getId()));
    }


    public void testWithoutHomeFolder()
        throws CvqException, CvqObjectNotFoundException,
               java.io.FileNotFoundException, java.io.IOException {

	      if (!requestService.supportUnregisteredCreation())
	         return;

	      startTransaction();
	
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        
        DeathDetailsRequest request = fillMeARequest();

        Address address = BusinessObjectsFactory.gimmeAdress("12", "Rue d'Aligre", "Paris", "75012");
        Adult requester =
            BusinessObjectsFactory.gimmeAdult(TitleType.MISTER, "LASTNAME", "requester", address,
                                              FamilyStatusType.MARRIED);
        requester.setPassword("requester");
        requester.setAdress(address);
        iHomeFolderService.addHomeFolderRole(requester, RoleType.HOME_FOLDER_RESPONSIBLE);
        DeathDetailsRequestFeeder.setSubject(request, 
            requestService.getSubjectPolicy(), requester, null);

        Long requestId =
             requestWorkflowService.create(request, requester);
        
        // close current session and re-open a new one
        continueWithNewTransaction();
        
        // start testing request creation
        /////////////////////////////////

        DeathDetailsRequest requestFromDb =
            (DeathDetailsRequest) requestSearchService.getById(requestId);
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
