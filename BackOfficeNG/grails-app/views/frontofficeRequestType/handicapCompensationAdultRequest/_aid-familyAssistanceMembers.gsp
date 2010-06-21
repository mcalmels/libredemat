

  <h4>${message(code:'hcar.property.familyAssistanceMembers.label')}<span>${message(code:'hcar.property.familyAssistanceMembers.help')}</span></h4>
  <g:set var="currentCollectionItem" value="${rqt?.familyAssistanceMembers.size() > collectionIndex ? rqt.familyAssistanceMembers.get(collectionIndex) : null}" />
  
    <label for="familyAssistanceMembers.${collectionIndex}.familyAssistanceMemberRelationship" class="required"><g:message code="hcar.property.familyAssistanceMemberRelationship.label" /> *  <span><g:message code="hcar.property.familyAssistanceMemberRelationship.help" /></span></label>
            <input type="text" id="familyAssistanceMembers.${collectionIndex}.familyAssistanceMemberRelationship" name="familyAssistanceMembers[${collectionIndex}].familyAssistanceMemberRelationship" value="${currentCollectionItem?.familyAssistanceMemberRelationship?.toString()}" 
                    class="required   ${rqt.stepStates['aid'].invalidFields.contains('familyAssistanceMembers['+collectionIndex+'].familyAssistanceMemberRelationship') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.familyAssistanceMemberRelationship.validationError" />"  maxlength="60" />
            

  
    <label for="familyAssistanceMembers.${collectionIndex}.familyAssistanceMemberLastName" class="required"><g:message code="hcar.property.familyAssistanceMemberLastName.label" /> *  <span><g:message code="hcar.property.familyAssistanceMemberLastName.help" /></span></label>
            <input type="text" id="familyAssistanceMembers.${collectionIndex}.familyAssistanceMemberLastName" name="familyAssistanceMembers[${collectionIndex}].familyAssistanceMemberLastName" value="${currentCollectionItem?.familyAssistanceMemberLastName?.toString()}" 
                    class="required  validate-lastName ${rqt.stepStates['aid'].invalidFields.contains('familyAssistanceMembers['+collectionIndex+'].familyAssistanceMemberLastName') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.familyAssistanceMemberLastName.validationError" />"  maxlength="38" />
            

  
    <label for="familyAssistanceMembers.${collectionIndex}.familyAssistanceMemberFirstName" class="required"><g:message code="hcar.property.familyAssistanceMemberFirstName.label" /> *  <span><g:message code="hcar.property.familyAssistanceMemberFirstName.help" /></span></label>
            <input type="text" id="familyAssistanceMembers.${collectionIndex}.familyAssistanceMemberFirstName" name="familyAssistanceMembers[${collectionIndex}].familyAssistanceMemberFirstName" value="${currentCollectionItem?.familyAssistanceMemberFirstName?.toString()}" 
                    class="required  validate-firstName ${rqt.stepStates['aid'].invalidFields.contains('familyAssistanceMembers['+collectionIndex+'].familyAssistanceMemberFirstName') ? 'validation-failed' : ''}" title="<g:message code="hcar.property.familyAssistanceMemberFirstName.validationError" />"  maxlength="38" />
            

  
  <input type="hidden" name="currentCollection" value="${currentCollection}" />
  <input type="hidden" name="collectionIndex" value="${collectionIndex}" />
  <input type="submit" id="submit-step-aid-familyAssistanceMembers" name="submit-step-aid-familyAssistanceMembers[${collectionIndex}]" value="${message(code:'action.save')}" />
  <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id': rqt.id, 'currentStep': 'aid'])}">
    ${message(code:'request.action.cancelCollectionItemEdit')}
  </a>
  
