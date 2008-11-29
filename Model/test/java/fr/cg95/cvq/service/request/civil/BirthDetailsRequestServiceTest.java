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
import fr.cg95.cvq.service.request.civil.IBirthDetailsRequestService;
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
public class BirthDetailsRequestServiceTest extends ServiceTestCase {

    protected IBirthDetailsRequestService iBirthDetailsRequestService;

    protected void onSetUp() throws Exception {
    	super.onSetUp();
        iBirthDetailsRequestService = 
            (IBirthDetailsRequestService) getBean(StringUtils.uncapitalize("BirthDetailsRequest") + "Service");
    }

    protected BirthDetailsRequest fillMeARequest() throws CvqException {

        BirthDetailsRequest request = new BirthDetailsRequest();
            request.setFormat(BirthCertificateFormatType.FULL_COPY);
                request.setCopies(BigInteger.valueOf(1));
              if ("BirthPostalCode".length() > 2)
        request.setBirthPostalCode("BirthPostalCode".substring(0, 2));
      else
        request.setBirthPostalCode("BirthPostalCode");
                request.setComment("Comment");
                request.setBirthFirstNames("BirthFirstNames");
                  request.setMotive(BirthCertificateMotiveType.NOTARY_ACT);
                    request.setRequesterQualityPrecision("RequesterQualityPrecision");
                request.setBirthDate(new Date());
              request.setRequesterQuality(BirthRequesterQualityType.REQUESTER);
                  if ("BirthCity".length() > 32)
        request.setBirthCity("BirthCity".substring(0, 32));
      else
        request.setBirthCity("BirthCity");
                  if ("FatherLastName".length() > 38)
        request.setFatherLastName("FatherLastName".substring(0, 38));
      else
        request.setFatherLastName("FatherLastName");
                    request.setMotherFirstNames("MotherFirstNames");
                    request.setFatherFirstNames("FatherFirstNames");
                  if ("MotherMaidenName".length() > 38)
        request.setMotherMaidenName("MotherMaidenName".substring(0, 38));
      else
        request.setMotherMaidenName("MotherMaidenName");
                  if ("BirthLastName".length() > 38)
        request.setBirthLastName("BirthLastName".substring(0, 38));
      else
        request.setBirthLastName("BirthLastName");
      
        // Means Of Contact
        MeansOfContact meansOfContact = iMeansOfContactService.getMeansOfContactByType(
                    MeansOfContactEnum.EMAIL);
        request.setMeansOfContact(meansOfContact);
        
        BirthDetailsRequestFeeder.feed(request);
        
        return request;
    }
        	
    protected void completeValidateAndDelete(BirthDetailsRequest request) 
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
        iBirthDetailsRequestService.addDocument(request.getId(), documentId);
        Set<RequestDocument> documentsSet =
            iBirthDetailsRequestService.getAssociatedDocuments(request.getId());
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
        iBirthDetailsRequestService.complete(request.getId());
        iBirthDetailsRequestService.validate(request.getId());

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
        iBirthDetailsRequestService.delete(request.getId());
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

         BirthDetailsRequest request = fillMeARequest();
         request.setRequesterId(SecurityContext.getCurrentUserId());
         BirthDetailsRequestFeeder.setSubject(request, 
             iBirthDetailsRequestService.getSubjectPolicy(), null, homeFolder);
         
         Long requestId =
              iBirthDetailsRequestService.create(request);

         BirthDetailsRequest requestFromDb =
        	 	(BirthDetailsRequest) iBirthDetailsRequestService.getById(requestId);
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

	      if (!iBirthDetailsRequestService.supportUnregisteredCreation())
	         return;

	      startTransaction();
	
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        
        BirthDetailsRequest request = fillMeARequest();

        Address address = BusinessObjectsFactory.gimmeAdress("12", "Rue d'Aligre", "Paris", "75012");
        Adult requester =
            BusinessObjectsFactory.gimmeAdult(TitleType.MISTER, "LASTNAME", "requester", address,
                                              FamilyStatusType.MARRIED);
        requester.setPassword("requester");
        requester.setAdress(address);
        iHomeFolderService.addHomeFolderRole(requester, RoleEnum.HOME_FOLDER_RESPONSIBLE);
        BirthDetailsRequestFeeder.setSubject(request, 
            iBirthDetailsRequestService.getSubjectPolicy(), requester, null);

        Long requestId =
             iBirthDetailsRequestService.create(request, requester, requester);
        
        // close current session and re-open a new one
        continueWithNewTransaction();
        
        // start testing request creation
        /////////////////////////////////

        BirthDetailsRequest requestFromDb =
            (BirthDetailsRequest) iBirthDetailsRequestService.getById(requestId);
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
