class RequestAdaptorService {
    
    def instructionService;
    def translationService;
    
    def prepareRecords = { requests ->
        if (!requests?.records) requests.records = []
        requests.all.each {
            requests.records.add([
                'id':it.id,
                'draft':it.draft,
                'label':translationService.getEncodedRequestTypeLabelTranslation(it.requestType.label),
                'creationDate':it.creationDate,
                'requesterLastName':it.requesterLastName,
                'requesterFirstName': it.requesterFirstName,
                'subjectLastName':it.subjectLastName,
                'subjectFirstName': it.subjectFirstName,
                'state':it.state.toString(),
                'lastModificationDate':it.lastModificationDate,
                'lastInterveningAgentId': instructionService.getActionPosterDetails(it.lastInterveningAgentId) ,
            ]);
        }
        
        return requests
    }
}
