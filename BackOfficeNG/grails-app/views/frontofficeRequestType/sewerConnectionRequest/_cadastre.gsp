


  
    
    <label class="required"><g:message code="scr.property.requesterQuality.label" /> *  <span><g:message code="scr.property.requesterQuality.help" /></span></label>
            <ul class="required ${rqt.stepStates['cadastre'].invalidFields.contains('requesterQuality') ? 'validation-failed' : ''}">
              <g:each in="${['OWNER','TENANT']}">
              <li>
                <input type="radio" id="requesterQuality_${it}" class="required condition-isTenant-trigger  validate-one-required" value="${it}" name="requesterQuality" ${it == rqt.requesterQuality.toString() ? 'checked="checked"': ''} title="<g:message code="scr.property.requesterQuality.validationError" />" />
                <label for="requesterQuality_${it}"><g:libredematEnumToText var="${it}" i18nKeyPrefix="scr.property.requesterQuality" /></label>
              </li>
              </g:each>
            </ul>
            

  

  
    
    <label for="ownerLastName" class="required condition-isTenant-filled"><g:message code="scr.property.ownerLastName.label" /> *  <span><g:message code="scr.property.ownerLastName.help" /></span></label>
            <input  type="text" id="ownerLastName"
                   name="ownerLastName"
                   value="${rqt.ownerLastName?.toString()}"
                   class="required condition-isTenant-filled  validate-lastName ${rqt.stepStates['cadastre'].invalidFields.contains('ownerLastName') ? 'validation-failed' : ''}"
                   title="<g:message code="scr.property.ownerLastName.validationError" />"  maxlength="38" />
            

  

  
    
    <label for="ownerFirstNames" class="required condition-isTenant-filled"><g:message code="scr.property.ownerFirstNames.label" /> *  <span><g:message code="scr.property.ownerFirstNames.help" /></span></label>
            <input  type="text" id="ownerFirstNames"
                   name="ownerFirstNames"
                   value="${rqt.ownerFirstNames?.toString()}"
                   class="required condition-isTenant-filled  validate-string ${rqt.stepStates['cadastre'].invalidFields.contains('ownerFirstNames') ? 'validation-failed' : ''}"
                   title="<g:message code="scr.property.ownerFirstNames.validationError" />"   />
            

  

  
    
    <label class="required condition-isTenant-filled"><g:message code="scr.property.ownerAddress.label" /> *  <span><g:message code="scr.property.ownerAddress.help" /></span></label>
            <div id="ownerAddress" class="address required condition-isTenant-filled  ${rqt.stepStates['cadastre'].invalidFields.contains('ownerAddress') ? 'validation-failed' : ''}">
            <label for="ownerAddress.additionalDeliveryInformation"><g:message code="address.property.additionalDeliveryInformation" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['cadastre'].invalidFields.contains('ownerAddress.additionalDeliveryInformation') ? 'validation-failed' : ''}" value="${rqt.ownerAddress?.additionalDeliveryInformation}" maxlength="38" id="ownerAddress.additionalDeliveryInformation" name="ownerAddress.additionalDeliveryInformation" />  
            <label for="ownerAddress.additionalGeographicalInformation"><g:message code="address.property.additionalGeographicalInformation" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['cadastre'].invalidFields.contains('ownerAddress.additionalGeographicalInformation') ? 'validation-failed' : ''}" value="${rqt.ownerAddress?.additionalGeographicalInformation}" maxlength="38" id="ownerAddress.additionalGeographicalInformation" name="ownerAddress.additionalGeographicalInformation" />
            <label for="ownerAddress_streetNumber"><g:message code="address.property.streetNumber" /></label> - 
            <label for="ownerAddress_streetName" class="required"><g:message code="address.property.streetName" /> *</label><br />
            <input  type="text" class="line1 validate-streetNumber ${rqt.stepStates['cadastre'].invalidFields.contains('ownerAddress.streetNumber') ? 'validation-failed' : ''}" value="${rqt.ownerAddress?.streetNumber}" size="5" maxlength="5" id="ownerAddress_streetNumber" name="ownerAddress.streetNumber" autocomplete="off" />
            <input  type="text" class="line2 required validate-streetName ${rqt.stepStates['cadastre'].invalidFields.contains('ownerAddress.streetName') ? 'validation-failed' : ''}" value="${rqt.ownerAddress?.streetName}" maxlength="32" id="ownerAddress_streetName" name="ownerAddress.streetName" title="<g:message code="address.property.streetName.validationError" />" autocomplete="off" />
            <input  type="hidden" value="${rqt.ownerAddress?.streetMatriculation}" id="ownerAddress_streetMatriculation" name="ownerAddress.streetMatriculation" />
            <input  type="hidden" value="${rqt.ownerAddress?.streetRivoliCode}" id="ownerAddress_streetRivoliCode" name="ownerAddress.streetRivoliCode" />
            <label for="ownerAddress.placeNameOrService"><g:message code="address.property.placeNameOrService" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['cadastre'].invalidFields.contains('ownerAddress.placeNameOrService') ? 'validation-failed' : ''}" value="${rqt.ownerAddress?.placeNameOrService}" maxlength="38" id="ownerAddress.placeNameOrService" name="ownerAddress.placeNameOrService" />
            <label for="ownerAddress_postalCode" class="required"><g:message code="address.property.postalCode" /> * </label> - 
            <label for="ownerAddress_city" class="required"><g:message code="address.property.city" /> *</label><br />
            <input  type="text" class="line1 required validate-postalCode ${rqt.stepStates['cadastre'].invalidFields.contains('ownerAddress.postalCode') ? 'validation-failed' : ''}" value="${rqt.ownerAddress?.postalCode}" size="5" maxlength="5" id="ownerAddress_postalCode" name="ownerAddress.postalCode" title="<g:message code="address.property.postalCode.validationError" />" autocomplete="off" />
            <input  type="text" class="line2 required validate-city ${rqt.stepStates['cadastre'].invalidFields.contains('ownerAddress.city') ? 'validation-failed' : ''}" value="${rqt.ownerAddress?.city}" maxlength="32" id="ownerAddress_city" name="ownerAddress.city" title="<g:message code="address.property.city.validationError" />" autocomplete="off" />
            <input  type="hidden" value="${rqt.ownerAddress?.cityInseeCode}" id="ownerAddress_cityInseeCode" name="ownerAddress.cityInseeCode" />
            <label for="ownerAddress.countryName"><g:message code="address.property.countryName" /></label>
            <input  type="text" class="validate-addressLine38 ${rqt.stepStates['cadastre'].invalidFields.contains('ownerAddress.countryName') ? 'validation-failed' : ''}" value="${rqt.ownerAddress?.countryName}" maxlength="38" id="ownerAddress.countryName" name="ownerAddress.countryName" />
            </div>
            

  

  
    
    <label for="section" class="required"><g:message code="scr.property.section.label" /> *  <span><g:message code="scr.property.section.help" /></span></label>
            <input  type="text" id="section"
                   name="section"
                   value="${rqt.section?.toString()}"
                   class="required  validate-string ${rqt.stepStates['cadastre'].invalidFields.contains('section') ? 'validation-failed' : ''}"
                   title="<g:message code="scr.property.section.validationError" />"   />
            

  

  
    
    <label for="number" class="required"><g:message code="scr.property.number.label" /> *  <span><g:message code="scr.property.number.help" /></span></label>
            <input  type="text" id="number"
                   name="number"
                   value="${rqt.number?.toString()}"
                   class="required  validate-positiveInteger ${rqt.stepStates['cadastre'].invalidFields.contains('number') ? 'validation-failed' : ''}"
                   title="<g:message code="scr.property.number.validationError" />"   />
            

  

  
    
    <label for="locality" class=""><g:message code="scr.property.locality.label" />   <span><g:message code="scr.property.locality.help" /></span></label>
            <input  type="text" id="locality"
                   name="locality"
                   value="${rqt.locality?.toString()}"
                   class="  validate-string ${rqt.stepStates['cadastre'].invalidFields.contains('locality') ? 'validation-failed' : ''}"
                   title="<g:message code="scr.property.locality.validationError" />"   />
            

  

  
    
    <label for="transportationRoute" class=""><g:message code="scr.property.transportationRoute.label" />   <span><g:message code="scr.property.transportationRoute.help" /></span></label>
            <input  type="text" id="transportationRoute"
                   name="transportationRoute"
                   value="${rqt.transportationRoute?.toString()}"
                   class="  validate-string ${rqt.stepStates['cadastre'].invalidFields.contains('transportationRoute') ? 'validation-failed' : ''}"
                   title="<g:message code="scr.property.transportationRoute.validationError" />"   />
            

  

  
    
    <label class="required"><g:message code="scr.property.moreThanTwoYears.label" /> *  <span><g:message code="scr.property.moreThanTwoYears.help" /></span></label>
            <ul class="yes-no required ${rqt.stepStates['cadastre'].invalidFields.contains('moreThanTwoYears') ? 'validation-failed' : ''}">
              <g:each in="${[true,false]}">
              <li>
                <input type="radio" id="moreThanTwoYears_${it ? 'yes' : 'no'}" class="required  validate-one-required boolean" title="" value="${it}" name="moreThanTwoYears" ${it == rqt.moreThanTwoYears ? 'checked="checked"': ''} />
                <label for="moreThanTwoYears_${it ? 'yes' : 'no'}"><g:message code="message.${it ? 'yes' : 'no'}" /></label>
              </li>
              </g:each>
            </ul>
            

  

