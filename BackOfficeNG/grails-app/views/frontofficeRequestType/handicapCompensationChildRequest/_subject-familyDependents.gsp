

  <label class="required condition-isReferentFamilyDependents-filled"><g:message code="hccr.property.familyDependents.label" /> <span><g:message code="hccr.property.familyDependents.help" /></span></label>
  <div class="collection-fieldset required condition-isReferentFamilyDependents-filled validation-scope summary-box">
    <fieldset class="collection-fieldset-add required condition-isReferentFamilyDependents-filled">
    <g:set var="currentCollectionItem" value="${rqt?.familyDependents.size() > collectionIndex ? rqt.familyDependents.get(collectionIndex) : null}" />
  
      <label for="familyDependents.${collectionIndex}.referentFamilyDependentLastName" class="required"><g:message code="hccr.property.referentFamilyDependentLastName.label" /> *  <span><g:message code="hccr.property.referentFamilyDependentLastName.help" /></span></label>
            <input type="text" id="familyDependents.${collectionIndex}.referentFamilyDependentLastName" name="familyDependents[${collectionIndex}].referentFamilyDependentLastName" value="${currentCollectionItem?.referentFamilyDependentLastName?.toString()}" 
                    class="required  validate-lastName ${rqt.stepStates['subject'].invalidFields.contains('familyDependents.referentFamilyDependentLastName') ? 'validation-failed' : ''}" title="<g:message code="hccr.property.referentFamilyDependentLastName.validationError" />"  maxlength="38" />
            

  
      <label for="familyDependents.${collectionIndex}.referentFamilyDependentFirstName" class="required"><g:message code="hccr.property.referentFamilyDependentFirstName.label" /> *  <span><g:message code="hccr.property.referentFamilyDependentFirstName.help" /></span></label>
            <input type="text" id="familyDependents.${collectionIndex}.referentFamilyDependentFirstName" name="familyDependents[${collectionIndex}].referentFamilyDependentFirstName" value="${currentCollectionItem?.referentFamilyDependentFirstName?.toString()}" 
                    class="required  validate-firstName ${rqt.stepStates['subject'].invalidFields.contains('familyDependents.referentFamilyDependentFirstName') ? 'validation-failed' : ''}" title="<g:message code="hccr.property.referentFamilyDependentFirstName.validationError" />"  maxlength="38" />
            

  
      <label for="familyDependents.${collectionIndex}.referentFamilyDependentBirthDate" class="required"><g:message code="hccr.property.referentFamilyDependentBirthDate.label" /> *  <span><g:message code="hccr.property.referentFamilyDependentBirthDate.help" /></span></label>
            <input type="text" id="familyDependents.${collectionIndex}.referentFamilyDependentBirthDate" name="familyDependents[${collectionIndex}].referentFamilyDependentBirthDate" value="${formatDate(formatName:'format.date',date:currentCollectionItem?.referentFamilyDependentBirthDate)}" 
                   class="required  validate-date ${rqt.stepStates['subject'].invalidFields.contains('familyDependents.referentFamilyDependentBirthDate') ? 'validation-failed' : ''}" title="<g:message code="hccr.property.referentFamilyDependentBirthDate.validationError" />" />
            

  
      <label for="familyDependents.${collectionIndex}.referentFamilyDependentActualSituation" class="required"><g:message code="hccr.property.referentFamilyDependentActualSituation.label" /> *  <span><g:message code="hccr.property.referentFamilyDependentActualSituation.help" /></span></label>
            <select id="familyDependents.${collectionIndex}.referentFamilyDependentActualSituation" name="familyDependents[${collectionIndex}].referentFamilyDependentActualSituation" class="required  validate-not-first ${rqt.stepStates['subject'].invalidFields.contains('familyDependents.referentFamilyDependentActualSituation') ? 'validation-failed' : ''}" title="<g:message code="hccr.property.referentFamilyDependentActualSituation.validationError" />">
              <option value=""><g:message code="message.select.defaultOption" /></option>
              <g:each in="${['Schooling','Learning','MedicoSocial']}">
                <option value="fr.cg95.cvq.business.request.social.HccrReferentFamilyDependentActualSituationType_${it}" ${it == currentCollectionItem?.referentFamilyDependentActualSituation?.toString() ? 'selected="selected"': ''}><g:capdematEnumToText var="${it}" i18nKeyPrefix="hccr.property.referentFamilyDependentActualSituation" /></option>
              </g:each>
            </select>
            

  
      <input type="submit" id="submit-step-subject-familyDependents" name="submit-step-subject-familyDependents[${collectionIndex}]" value="${message(code:'action.save')}" />
      <a href="${createLink(controller : 'frontofficeRequest', action : 'edit', params:['id': rqt.id, 'currentStep': 'subject'])}">
        ${message(code:'request.action.cancelCollectionItemEdit')}
      </a>  
    </fieldset>
</div>
  
