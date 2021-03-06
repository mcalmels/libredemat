class ServiceUrlMappings {
  static mappings = {

    "/service/fakePayment/$action?/$id?" (controller : "serviceFakePayment" )
    "/service/spplus/$action?/$id?" (controller : "serviceSpplus" )
    "/service/systempay/$action?/$id?" (controller : "serviceSystemPay" )
    "/service/tipi" (controller : "serviceTipi", action: "index" )
    "/autocomplete/$action?" (controller: "serviceAutocomplete")
    "/service/request/$requestId/documents" (controller: "serviceRequestExternal", action: "requestDocuments")
    "/service/request/$requestId/document/$documentId?" (controller: "serviceRequestExternal", action: "requestDocument")
    "/service/paylineV4/$action?/$id?" (controller : "servicePaylineV4")
    "/service/provisioning/$localAuthority/$action?/$id?" (controller : "serviceProvisioning")
    "/service/requestType/$requestTypeLabel/localReferential" (controller : "serviceRequestExternal"){action = [POST : "localReferential"]}
    "/service/request/$requestId/state" (controller : "serviceRequestExternal"){action = [POST : "requestState"]}
    "/service/requestType/$requestTypeLabel/season" (controller : "serviceRequestExternal"){action = [GET : "requestTypeSeason"]}
    "/service/templates/$filename?" (controller : "localAuthorityResource"){action = [PUT : "template"]}
    "/service/login" (controller : "serviceUser"){action = [GET : "login", POST : "auth"]}
    "/service/loginAgent" (controller : "serviceUser"){action = [GET : "loginAgent", POST : "authAgent"]}
    "/service/oauth/v$version/user" (controller : "serviceIndividual" )
    "/service/oauth/v$version/home" (controller : "serviceHomeFolder" )
    "/service/oauth/v$version/authority" (controller : "serviceLocalAuthority" )
    "/service/oauth/v$version/request/$individual/$requestTypeLabel" (controller : "serviceRequest" )
    "/service/oauth/v$version/agent" (controller : "serviceUser"){action = [GET : "loginAgent"]}
    "/service/oauth/v$version/invoice" (controller : "serviceInvoice" ){action = [POST: "create"]}
    "/service/ciril/$action?/$id?" (controller: "serviceCiril")
    "/service/paybox/$action?/$id?" (controller : "servicePaybox" )
  }
}
