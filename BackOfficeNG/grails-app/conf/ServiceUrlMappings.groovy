class ServiceUrlMappings {
  static mappings = {

    "/service/fakePayment/$action?/$id?" (controller : "serviceFakePayment" )
    "/service/spplus/$action?/$id?" (controller : "serviceSpplus" )
    "/autocomplete/$action?" (controller: "serviceAutocomplete")
    "/service/request/$requestId/documents" (controller: "serviceRequestExternal", action: "requestDocuments")
    "/service/request/$requestId/document/$documentId?" (controller: "serviceRequestExternal", action: "requestDocument")
  }
}
