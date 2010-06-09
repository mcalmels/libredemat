


  
    <label class=""><g:message code="rarr.property.contactIndividuals.label" /> <span><g:message code="rarr.property.contactIndividuals.help" /></span></label>
    <div class="collection-fieldset  summary-box">
    <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id':rqt.id, 'currentStep':'contact', 'currentCollection':'contactIndividuals', 'collectionIndex':(rqt.contactIndividuals ? rqt.contactIndividuals.size() : 0)])}" />
      ${message(code:'request.action.newCollectionItem')}
    </a>
    <g:each var="it" in="${rqt.contactIndividuals}" status="index">
      <fieldset class="collection-fieldset-edit">
        <dl>
    
        <dt><g:message code="rarr.property.lastName.label" /></dt>
        <dd>${it.lastName?.toString()}</dd>
    
        <dt><g:message code="rarr.property.firstName.label" /></dt>
        <dd>${it.firstName?.toString()}</dd>
    
        <dt><g:message code="rarr.property.address.label" /></dt>
        
              <g:if test="${it.address}">
                <dd>
                  <p>${it.address?.additionalDeliveryInformation}</p>
                  <p>${it.address?.additionalGeographicalInformation}</p>
                  <p>${it.address?.streetNumber} ${it.address?.streetName}</p>
                  <p>${it.address?.placeNameOrService}</p>
                  <p>${it.address?.postalCode} ${it.address?.city}</p>
                  <p>${it.address?.countryName}</p>
                </dd>
              </g:if>
              
    
        <dt><g:message code="rarr.property.homePhone.label" /></dt>
        <dd>${it.homePhone?.toString()}</dd>
    
        <dt><g:message code="rarr.property.officePhone.label" /></dt>
        <dd>${it.officePhone?.toString()}</dd>
    
        </dl>
         <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id':rqt.id, 'currentStep':'contact', 'currentCollection':'contactIndividuals', 'collectionIndex':index])}">
           ${message(code:'request.action.editCollectionItem')}
         </a>
         <a href="${createLink(controller : 'frontofficeRequest', action : 'collectionRemove', params:['id':rqt.id, 'currentStep':'contact', 'currentCollection':'contactIndividuals', 'collectionIndex':index])}">
           ${message(code:'request.action.deleteCollectionItem')}
         </a>
      </fieldset>
    </g:each>
    </div>
  

