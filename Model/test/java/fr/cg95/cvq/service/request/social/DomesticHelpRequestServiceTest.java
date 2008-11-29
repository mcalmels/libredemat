package fr.cg95.cvq.service.request.social;

import fr.cg95.cvq.business.users.*;
import fr.cg95.cvq.business.request.*;
import fr.cg95.cvq.business.authority.*;
import fr.cg95.cvq.business.document.*;
import fr.cg95.cvq.business.request.social.*;
import fr.cg95.cvq.exception.*;
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.service.document.IDocumentTypeService;
import fr.cg95.cvq.service.request.IRequestService;
import fr.cg95.cvq.service.request.social.IDomesticHelpRequestService;
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
public class DomesticHelpRequestServiceTest extends ServiceTestCase {

    protected IDomesticHelpRequestService iDomesticHelpRequestService;

    protected void onSetUp() throws Exception {
    	super.onSetUp();
        iDomesticHelpRequestService = 
            (IDomesticHelpRequestService) getBean(StringUtils.uncapitalize("DomesticHelpRequest") + "Service");
    }

    protected DomesticHelpRequest fillMeARequest() throws CvqException {

        DomesticHelpRequest request = new DomesticHelpRequest();
          request.setNotRealAssetsValuesTotal(BigInteger.valueOf(1));
                      request.setCurrentDwellingType(DhrDwellingType.DOMICILE);
                request.setProfessionalTaxes(BigInteger.valueOf(1));
            request.setSpousePensions(BigInteger.valueOf(1));
            request.setTaxesTotal(BigInteger.valueOf(1));
                            Address TutorAddress = BusinessObjectsFactory.gimmeAdress("1", "Unit test address", "Paris", "75012");
            request.setTutorAddress(TutorAddress);
    	                          if ("PensionPlanPrecision".length() > 50)
        request.setPensionPlanPrecision("PensionPlanPrecision".substring(0, 50));
      else
        request.setPensionPlanPrecision("PensionPlanPrecision");
                  if ("SpouseComplementaryPensionPlanPrecision".length() > 50)
        request.setSpouseComplementaryPensionPlanPrecision("SpouseComplementaryPensionPlanPrecision".substring(0, 50));
      else
        request.setSpouseComplementaryPensionPlanPrecision("SpouseComplementaryPensionPlanPrecision");
                                Address FamilyReferentAddress = BusinessObjectsFactory.gimmeAdress("1", "Unit test address", "Paris", "75012");
            request.setFamilyReferentAddress(FamilyReferentAddress);
    	                request.setFamilyReferentDesignated(Boolean.valueOf(true));
            request.setCurrentDwellingArrivalDate(new Date());
            request.setIncomeTax(BigInteger.valueOf(1));
            request.setSpouseMoreThan15YearsInFrance(Boolean.valueOf(true));
              request.setRequesterRequestKind(DhrRequestKindType.INDIVIDUAL);
                  if ("TutorName".length() > 38)
        request.setTutorName("TutorName".substring(0, 38));
      else
        request.setTutorName("TutorName");
                request.setSpouseAllowances(BigInteger.valueOf(1));
            request.setCurrentDwellingNetFloorArea(BigInteger.valueOf(1));
              request.setCurrentDwellingStatus(DhrDwellingStatusType.OWNER);
                  if ("SpouseEmployer".length() > 50)
        request.setSpouseEmployer("SpouseEmployer".substring(0, 50));
      else
        request.setSpouseEmployer("SpouseEmployer");
                  request.setRequesterPensionPlan(DhrPensionPlanType.C_R_A_M_I_F);
                  request.setNationality(NationalityType.FRENCH);
                request.setRealAssetsValuesTotal(BigInteger.valueOf(1));
                            Address SpouseEmployerAddress = BusinessObjectsFactory.gimmeAdress("1", "Unit test address", "Paris", "75012");
            request.setSpouseEmployerAddress(SpouseEmployerAddress);
    	                  if ("ComplementaryPensionPlanPrecision".length() > 50)
        request.setComplementaryPensionPlanPrecision("ComplementaryPensionPlanPrecision".substring(0, 50));
      else
        request.setComplementaryPensionPlanPrecision("ComplementaryPensionPlanPrecision");
                  request.setSpouseNationality(NationalityType.FRENCH);
                                Address PreviousDwellingAddress = BusinessObjectsFactory.gimmeAdress("1", "Unit test address", "Paris", "75012");
            request.setPreviousDwellingAddress(PreviousDwellingAddress);
    	                                Address CurrentDwellingAddress = BusinessObjectsFactory.gimmeAdress("1", "Unit test address", "Paris", "75012");
            request.setCurrentDwellingAddress(CurrentDwellingAddress);
    	                  if ("CurrentDwellingPersonalPhone".length() > 10)
        request.setCurrentDwellingPersonalPhone("CurrentDwellingPersonalPhone".substring(0, 10));
      else
        request.setCurrentDwellingPersonalPhone("CurrentDwellingPersonalPhone");
                    request.setSpouseInformation(homeFolderWoman);
                request.setTutorPresence(Boolean.valueOf(true));
            request.setRequesterIncomesAnnualTotal(BigInteger.valueOf(1));
              request.setTutor(DhrTutorType.SAUVEGARDE_JUSTICE);
                  if ("FamilyReferentName".length() > 38)
        request.setFamilyReferentName("FamilyReferentName".substring(0, 38));
      else
        request.setFamilyReferentName("FamilyReferentName");
                request.setSpouseRealEstateInvestmentIncome(BigInteger.valueOf(1));
            request.setLocalRate(BigInteger.valueOf(1));
            request.setRequesterPensions(BigInteger.valueOf(1));
            request.setSpouseFranceArrivalDate(new Date());
              if ("FamilyReferentFirstName".length() > 38)
        request.setFamilyReferentFirstName("FamilyReferentFirstName".substring(0, 38));
      else
        request.setFamilyReferentFirstName("FamilyReferentFirstName");
                  request.setRequesterHasSpouse(DhrRequesterHasSpouse.TRUE);
                request.setPropertyTaxes(BigInteger.valueOf(1));
            request.setPreviousDwellingArrivalDate(new Date());
            request.setSpouseIncomesAnnualTotal(BigInteger.valueOf(1));
              if ("SpouseOccupation".length() > 50)
        request.setSpouseOccupation("SpouseOccupation".substring(0, 50));
      else
        request.setSpouseOccupation("SpouseOccupation");
                request.setFranceArrivalDate(new Date());
            request.setRequesterAllowances(BigInteger.valueOf(1));
              request.setSpousePensionPlan(DhrPensionPlanType.C_R_A_M_I_F);
                request.setMoreThan15YearsInFrance(Boolean.valueOf(true));
            request.setRequesterFurnitureInvestmentIncome(BigInteger.valueOf(1));
            request.setSpouseFurnitureInvestmentIncome(BigInteger.valueOf(1));
            request.setPreviousDwellingDepartureDate(new Date());
              if ("SpousePensionPlanPrecision".length() > 50)
        request.setSpousePensionPlanPrecision("SpousePensionPlanPrecision".substring(0, 50));
      else
        request.setSpousePensionPlanPrecision("SpousePensionPlanPrecision");
                request.setSpouseNetIncome(BigInteger.valueOf(1));
            request.setRequesterRealEstateInvestmentIncome(BigInteger.valueOf(1));
            request.setSpousePensionner(Boolean.valueOf(true));
            request.setRequesterNetIncome(BigInteger.valueOf(1));
            request.setCurrentDwellingRoomNumber(BigInteger.valueOf(1));
  
        // Means Of Contact
        MeansOfContact meansOfContact = iMeansOfContactService.getMeansOfContactByType(
                    MeansOfContactEnum.EMAIL);
        request.setMeansOfContact(meansOfContact);
        
        DomesticHelpRequestFeeder.feed(request);
        
        return request;
    }
        	
    protected void completeValidateAndDelete(DomesticHelpRequest request) 
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
        iDomesticHelpRequestService.addDocument(request.getId(), documentId);
        Set<RequestDocument> documentsSet =
            iDomesticHelpRequestService.getAssociatedDocuments(request.getId());
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
        iDomesticHelpRequestService.complete(request.getId());
        iDomesticHelpRequestService.validate(request.getId());

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
        iDomesticHelpRequestService.delete(request.getId());
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

         DomesticHelpRequest request = fillMeARequest();
         request.setRequesterId(SecurityContext.getCurrentUserId());
         DomesticHelpRequestFeeder.setSubject(request, 
             iDomesticHelpRequestService.getSubjectPolicy(), null, homeFolder);
         
         Long requestId =
              iDomesticHelpRequestService.create(request);

         DomesticHelpRequest requestFromDb =
        	 	(DomesticHelpRequest) iDomesticHelpRequestService.getById(requestId);
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

	      if (!iDomesticHelpRequestService.supportUnregisteredCreation())
	         return;

	      startTransaction();
	
        SecurityContext.setCurrentSite(localAuthorityName, SecurityContext.FRONT_OFFICE_CONTEXT);
        
        DomesticHelpRequest request = fillMeARequest();

        Address address = BusinessObjectsFactory.gimmeAdress("12", "Rue d'Aligre", "Paris", "75012");
        Adult requester =
            BusinessObjectsFactory.gimmeAdult(TitleType.MISTER, "LASTNAME", "requester", address,
                                              FamilyStatusType.MARRIED);
        requester.setPassword("requester");
        requester.setAdress(address);
        iHomeFolderService.addHomeFolderRole(requester, RoleEnum.HOME_FOLDER_RESPONSIBLE);
        DomesticHelpRequestFeeder.setSubject(request, 
            iDomesticHelpRequestService.getSubjectPolicy(), requester, null);

        Long requestId =
             iDomesticHelpRequestService.create(request, requester, requester);
        
        // close current session and re-open a new one
        continueWithNewTransaction();
        
        // start testing request creation
        /////////////////////////////////

        DomesticHelpRequest requestFromDb =
            (DomesticHelpRequest) iDomesticHelpRequestService.getById(requestId);
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
