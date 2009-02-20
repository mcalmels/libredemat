import fr.cg95.cvq.business.request.Request
fr.cg95.cvq.business.document.DepositOrigin
import fr.cg95.cvq.security.SecurityContext
import fr.cg95.cvq.service.authority.ILocalAuthorityRegistry
import fr.cg95.cvq.service.request.IRequestServiceRegistry
import fr.cg95.cvq.service.request.IMeansOfContactService
import fr.cg95.cvq.service.users.IIndividualService
import fr.cg95.cvq.service.document.IDocumentService
import fr.cg95.cvq.service.document.IDocumentTypeService
import fr.cg95.cvq.exception.CvqException

import grails.converters.JSON

class RequestCreationController {
    
    IRequestServiceRegistry requestServiceRegistry
    ILocalAuthorityRegistry localAuthorityRegistry
    IMeansOfContactService meansOfContactService
    IIndividualService individualService
    IDocumentService documentService
    IDocumentTypeService documentTypeService
    
    def defaultAction = 'edit'
    
    def draft = {
        def requestService = requestServiceRegistry.getRequestService(
            params.requestTypeLabel.toString()
        )
        
        if(request.post) {
            def cRequest = session[params.uuidString].cRequest
            requestService.prepareDraft(cRequest)
            requestService.processDraft(cRequest)
            flash.cRequest = cRequest
            flash.confirmationMessage = message(code:'message.savedAsDraft')
        } else if (request.get) {
            flash.cRequest = requestService.getById(Long.parseLong(params.id))
        }
        redirect(controller:controllerName, params:['label':params.requestTypeLabel])
        return false
    }
    
    def edit = {
        if (params.label == null)
            redirect(uri: '/frontoffice/requestType')

        def requestService = requestServiceRegistry.getRequestService(params.label)
        if (requestService == null) {
            redirect(uri: '/frontoffice/requestType')
            return false
        }
        
        def cRequest
        if (flash.cRequest) cRequest = flash.cRequest 
        else cRequest = requestService.getSkeletonRequest()

        def uuidString = UUID.randomUUID().toString()
        
        session[uuidString] = [:]
        session[uuidString].put('cRequest', cRequest)

        render( view: "frontofficeRequestType/${CapdematUtils.requestTypeLabelAsDir(params.label)}/edit", 
                model:
                    ['rqt': cRequest,
                     'subjects': getAuthorizedSubjects(requestService, cRequest),
                     'meansOfContact': getMeansOfContact(meansOfContactService),
                     'currentStep': 'subject',
                     'requestTypeLabel': params.label,
                     'stepStates': cRequest.stepStates.size() != 0 ? cRequest.stepStates : null,
                     'helps': localAuthorityRegistry.getBufferedCurrentLocalAuthorityRequestHelpMap(CapdematUtils.requestTypeLabelAsDir(params.label)),
                     'uuidString': uuidString,
                     'isRequestCreatable': isRequestCreatable(cRequest.stepStates),
                     'documentTypes': getDocumentTypes(requestService, cRequest),
                     'isDocumentEditMode': false
                    ])
    }
    
    def step = {
        if (params.requestTypeInfo == null || params.uuidString == null)
            redirect(uri: '/frontoffice/requestType')
            
        def uuidString = params.uuidString
        def requestTypeInfo = JSON.parse(params.requestTypeInfo)
        
        def submitAction = (params.keySet().find { it.startsWith('submit-') }).tokenize('-')
        def currentStep = submitAction[2]
        def editList
        
        def requestService = requestServiceRegistry.getRequestService(requestTypeInfo.label)
        def cRequest = session[uuidString].cRequest
        
        def isDocumentEditMode = false
        def documentType = [:]
        def document = [:]
        
        if (cRequest.stepStates.size() == 0) {
            session[uuidString].stepStates = [:]
            requestTypeInfo.steps.each {
                def nameToken = it.tokenize('-')
                def value = ['state': 'uncomplete',
                             'required': nameToken.size() == 2 ? true : false,
                             'cssClass': 'tag-uncomplete',
                             'i18nKey': 'request.step.state.uncomplete'
                             ]
                cRequest.stepStates.put(nameToken[0], value)
            }
        }
                
        try {
            if (submitAction[1] == 'documentAdd') {
                def docParam = targetAsMap(submitAction[3])
                documentType = getDocumentType(Long.valueOf(docParam.documentTypeId))
                isDocumentEditMode = true;
            }
//            else if (submitAction[1] == 'documentEdit') {
//                def docParam = targetAsMap(submitAction[3])
//                documentType = getDocumentType(Long.valueOf(docParam.documentTypeId))
//                isDocumentEditMode = true;
//                document = getDocument(Long.valueOf(docParam.id))
//            }
//            else if (submitAction[1] == 'documentAddPage') {
//                def docParam = targetAsMap(submitAction[3])
//                documentType = getDocumentType(Long.valueOf(docParam.documentTypeId))
//                isDocumentEditMode = true;
//                
//                DepositOrigin.ECITIZEN
//                
//                documentService.create(newDoc)
//                requestService.addDocument()
//            }
            // removal of a collection element
            else if (submitAction[1] == 'collectionDelete') {
                def listFieldToken = submitAction[3].tokenize('[]')
                def getterMethod = cRequest.class.getMethod('get' + StringUtils.firstCase(listFieldToken[0], 'Upper'))
                        
                getterMethod.invoke(cRequest, null).remove(Integer.valueOf(listFieldToken[1]).intValue())
            }
            // edition of a collection element
            else if (submitAction[1] == 'collectionEdit') {
                def listFieldToken = submitAction[3].tokenize('[]')
                def getterMethod = cRequest.class.getMethod('get' + StringUtils.firstCase(listFieldToken[0], 'Upper'))
                        
                editList = ['name': listFieldToken[0], 
                            'index': listFieldToken[1],
                            (listFieldToken[0]): getterMethod.invoke(cRequest, null).get(Integer.valueOf(listFieldToken[1]).intValue())
                           ]
            }
            // standard save action
            else {
                DataBindingUtils.initBind(cRequest, params)
                bind(cRequest)
                // clean empty collections elements
                DataBindingUtils.cleanBind(cRequest, params)
                
                if (submitAction[1] == 'step') {
                    cRequest.stepStates.get(currentStep).state = 'complete'
                    cRequest.stepStates.get(currentStep).cssClass = 'tag-complete'
                    cRequest.stepStates.get(currentStep).i18nKey = 'request.step.state.complete'
                    cRequest.stepStates.get(currentStep).errorMsg = ''
                }
                
                if (currentStep == "validation") {
                    if (!cRequest.draft) requestService.create(cRequest)
                    else requestService.finalizeDraft(cRequest)
                }
            }        
            session[uuidString].cRequest = cRequest
        
        } catch (CvqException ce) {
            cRequest.stepStates.get(currentStep).state = 'invalid'
            cRequest.stepStates.get(currentStep).cssClass = 'tag-invalid'
            cRequest.stepStates.get(currentStep).i18nKey = 'request.step.state.error'
            cRequest.stepStates.get(currentStep).errorMsg = ce.message
        }

        render( view: "frontofficeRequestType/${CapdematUtils.requestTypeLabelAsDir(requestTypeInfo.label)}/edit",
                model:
                    ['rqt': cRequest,
                     'subjects': getAuthorizedSubjects(requestService, cRequest),
                     'meansOfContact': getMeansOfContact(meansOfContactService),
                     'currentStep': currentStep,
                     'requestTypeLabel': requestTypeInfo.label,
                     'stepStates': cRequest.stepStates,
                     'helps': localAuthorityRegistry.getBufferedCurrentLocalAuthorityRequestHelpMap(CapdematUtils.requestTypeLabelAsDir(requestTypeInfo.label)),
                     'uuidString': uuidString,
                     'editList': editList,
                     'isRequestCreatable': isRequestCreatable(cRequest.stepStates),
                     'documentTypes': getDocumentTypes(requestService, cRequest),
                     'isDocumentEditMode': isDocumentEditMode,
                     'documentType': documentType,
                     'document': document
                    ])
    }

    def condition = {
        if (params.requestTypeLabel == null)
            render ([status: 'error', error_msg:message(code:'error.unexpected')] as JSON)
            
        def triggers = JSON.parse(params.triggers)
        try {
            def requestService = requestServiceRegistry.getRequestService(params.requestTypeLabel)
            render (
              [test: requestService.isConditionFilled(triggers),
              status:'ok',
              success_msg:message(code:'message.conditionTested')
              ] as JSON)
        } catch (CvqException ce) {
            render ([status: 'error', error_msg:message(code:'error.unexpected')] as JSON)
        }
    }
    
    
    /* Step and Validation
     * ------------------------------------------------------------------------------------------- */
    
    def getAuthorizedSubjects(requestService, cRequest) {
        def subjects = [:]
        def authorizedSubjects = requestService.getAuthorizedSubjects(SecurityContext.currentEcitizen.homeFolder.id)
        authorizedSubjects.each { subjectId, seasonsSet ->
            def subject = individualService.getById(subjectId)
            subjects[subjectId] = subject.lastName + ' ' + subject.firstName
        }

        // if it's a draft, its subject has to be manually re-added'
        if(cRequest.draft && !subjects.containsKey(cRequest.subjectId))
            subjects[cRequest.subjectId] = "${cRequest.subjectLastName} ${cRequest.subjectFirstName}"
            
        return subjects
    }
    
    def getMeansOfContact(meansOfContactService) {
        def result = []
        def meansOfContact = meansOfContactService.getCurrentEcitizenEnabledMeansOfContact()
        meansOfContact.each {
            result.add([
                        key:it.type,
                        label: message(code:'request.meansOfContact.' + StringUtils.pascalToCamelCase(it.type.toString()))])
        }
        return result.sort {it.label}
    }
     
    // TODO - refactor. Maybe move to Request class ...
    def isRequestCreatable(stepStates) {
        if (stepStates == null || stepStates.size() == 0)
            return false;
        def steps = stepStates.findAll {
            it.value.required && it.value.state != 'complete'
        }
        if (steps.size() == 0) return true;
        else return false;
    }
    
    
    /* Documents
     * ------------------------------------------------------------------------------------------- */

    def getDocumentTypes(requestService, cRequest) {
        def requestType = requestService.getRequestTypeByLabel(requestService.getLabel())
        def documentTypes = requestService.getAllowedDocuments(requestType.getId())
        
        def result = [:]
        documentTypes.each {
            def requestDocType = [:]
            requestDocType.i18nKey = CapdematUtils.adaptDocumentTypeName(it.name)
            requestDocType.associated = getAssociatedDocument (it, cRequest)
            requestDocType.provided = documentService.getProvidedDocuments(it, SecurityContext.currentEcitizen.homeFolder.id, null)
            result[it.id] = requestDocType
        }
        return result
    }
    
    def  getAssociatedDocument (docType, cRequest) {
        def requestDocuments = cRequest.documents
        def documents = requestDocuments.collect{ documentService.getById(it.documentId) }
        return documents.findAll{ it.type.documentType.id == docType.id }
    }
    
    def getDocument (id) {
        def doc = documentService.getById(id)
        def result = [:]
        result.id = doc.id
        result.ecitizenNote = doc.ecitizenNote
        doc.datas.each {
            result.datas[it.pageNumber] = ['id': it.id, 'pageNumber':it.pageNumber]  
        }
        return result 
    }
    
    def getDocumentType(id) {
        def result = [:]
        def docType = documentTypeService.getDocumentTypeById(id)
        result.id = docType.id
        result.i18nKey = CapdematUtils.adaptDocumentTypeName(docType.name)
        return (result)
    }
    
    
    /* Utils
     * ------------------------------------------------------------------------------------------- */
    
    // Convert a substring of <input type=submit name > representing target object of action in a map
    def targetAsMap(stringTarget) {
        def result = [:]
        stringTarget.tokenize('_').each {
            def property = it.tokenize(':')
            result[property[0]] = property[1]
        }
        return result
    }
    
}

