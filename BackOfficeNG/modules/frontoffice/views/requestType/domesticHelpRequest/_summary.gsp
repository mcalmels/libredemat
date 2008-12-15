

  <h3><g:message code="dhr.step.subject.label" /></h3>

          <h4><g:message code="dhr.property.dhrRequester.label" /></h4>
          <dl>

             <dt><g:message code="dhr.property.dhrRequesterTitle.label" /></dt>

          <dd>
            <g:if test="${dhr.dhrRequesterTitle}">
              <g:message code="dhr.property.dhrRequesterTitle.${dhr.dhrRequesterTitle}"/>
            </g:if>
          </dd>
          
             <dt><g:message code="dhr.property.dhrRequesterFamilyStatus.label" /></dt>

          <dd>
            <g:if test="${dhr.dhrRequesterFamilyStatus}">
              <g:message code="dhr.property.dhrRequesterFamilyStatus.${dhr.dhrRequesterFamilyStatus}"/>
            </g:if>
          </dd>
          
             <dt><g:message code="dhr.property.dhrRequesterName.label" /></dt>
<dd>${dhr.dhrRequesterName}</dd>
             <dt><g:message code="dhr.property.dhrRequesterFirstName.label" /></dt>
<dd>${dhr.dhrRequesterFirstName}</dd>
             <dt><g:message code="dhr.property.dhrRequesterMaidenName.label" /></dt>
<dd>${dhr.dhrRequesterMaidenName}</dd>
             <dt><g:message code="dhr.property.dhrRequesterBirthDate.label" /></dt>
<dd><g:formatDate formatName="format.date" date="${dhr.dhrRequesterBirthDate}"/></dd>
             <dt><g:message code="dhr.property.dhrRequesterBirthPlace.label" /></dt>
<dd>${dhr.dhrRequesterBirthPlace}</dd>
             <dt><g:message code="dhr.property.dhrRequesterNationality.label" /></dt>

          <dd>
            <g:if test="${dhr.dhrRequesterNationality}">
              <g:message code="dhr.property.dhrRequesterNationality.${dhr.dhrRequesterNationality}"/>
            </g:if>
          </dd>
          
             <dt><g:message code="dhr.property.dhrRequesterFranceArrivalDate.label" /></dt>
<dd><g:formatDate formatName="format.date" date="${dhr.dhrRequesterFranceArrivalDate}"/></dd>
             <dt><g:message code="dhr.property.dhrRequesterIsFrenchResident.label" /></dt>

          <dd><g:message code="widget.yesno.${dhr.dhrRequesterIsFrenchResident ? 'yes' : 'no'}" /></dd>
          
          </dl>

          <h4><g:message code="dhr.property.dhrRequesterPensionPlan.label" /></h4>
          <dl>

             <dt><g:message code="dhr.property.dhrPrincipalPensionPlan.label" /></dt>

          <dd>
            <g:if test="${dhr.dhrPrincipalPensionPlan}">
              <g:message code="dhr.property.dhrPrincipalPensionPlan.${dhr.dhrPrincipalPensionPlan}"/>
            </g:if>
          </dd>
          
             <dt><g:message code="dhr.property.dhrPensionPlanDetail.label" /></dt>
<dd>${dhr.dhrPensionPlanDetail}</dd>
             <dt><g:message code="dhr.property.dhrComplementaryPensionPlan.label" /></dt>
<dd>${dhr.dhrComplementaryPensionPlan}</dd>
          </dl>

          <h4><g:message code="dhr.property.dhrRequesterGuardian.label" /></h4>
          <dl>

             <dt><g:message code="dhr.property.dhrRequesterHaveGuardian.label" /></dt>

          <dd><g:message code="widget.yesno.${dhr.dhrRequesterHaveGuardian ? 'yes' : 'no'}" /></dd>
          
             <dt><g:message code="dhr.property.dhrGuardianMeasure.label" /></dt>

          <dd>
            <g:if test="${dhr.dhrGuardianMeasure}">
              <g:message code="dhr.property.dhrGuardianMeasure.${dhr.dhrGuardianMeasure}"/>
            </g:if>
          </dd>
          
             <dt><g:message code="dhr.property.dhrGuardianName.label" /></dt>
<dd>${dhr.dhrGuardianName}</dd>
             <dt><g:message code="dhr.property.dhrGuardianAddress.label" /></dt>

          <g:if test="${dhr.dhrGuardianAddress}">
            <dd>${dhr.dhrGuardianAddress?.additionalDeliveryInformation}</dd>
            <dd>${dhr.dhrGuardianAddress?.additionalGeographicalInformation}</dd>
            <dd>${dhr.dhrGuardianAddress?.streetNumber} ${dhr.dhrGuardianAddress?.streetName}</dd>
            <dd>${dhr.dhrGuardianAddress?.placeNameOrService}</dd>
            <dd>${dhr.dhrGuardianAddress?.postalCode} ${dhr.dhrGuardianAddress?.city}</dd>
            <dd>${dhr.dhrGuardianAddress?.countryName}</dd>
          </g:if>
          
          </dl>

  <h3><g:message code="dhr.step.familyReferent.label" /></h3>

          <h4><g:message code="dhr.property.dhrFamilyReferent.label" /></h4>
          <dl>

             <dt><g:message code="dhr.property.dhrHaveFamilyReferent.label" /></dt>

          <dd><g:message code="widget.yesno.${dhr.dhrHaveFamilyReferent ? 'yes' : 'no'}" /></dd>
          
             <dt><g:message code="dhr.property.dhrReferentName.label" /></dt>
<dd>${dhr.dhrReferentName}</dd>
             <dt><g:message code="dhr.property.dhrReferentFirstName.label" /></dt>
<dd>${dhr.dhrReferentFirstName}</dd>
             <dt><g:message code="dhr.property.dhrReferentAddress.label" /></dt>

          <g:if test="${dhr.dhrReferentAddress}">
            <dd>${dhr.dhrReferentAddress?.additionalDeliveryInformation}</dd>
            <dd>${dhr.dhrReferentAddress?.additionalGeographicalInformation}</dd>
            <dd>${dhr.dhrReferentAddress?.streetNumber} ${dhr.dhrReferentAddress?.streetName}</dd>
            <dd>${dhr.dhrReferentAddress?.placeNameOrService}</dd>
            <dd>${dhr.dhrReferentAddress?.postalCode} ${dhr.dhrReferentAddress?.city}</dd>
            <dd>${dhr.dhrReferentAddress?.countryName}</dd>
          </g:if>
          
          </dl>

  <h3><g:message code="dhr.step.spouse.label" /></h3>

          <h4><g:message code="dhr.property.dhrSpouse.label" /></h4>
          <dl>

             <dt><g:message code="dhr.property.dhrRequestKind.label" /></dt>

          <dd>
            <g:if test="${dhr.dhrRequestKind}">
              <g:message code="dhr.property.dhrRequestKind.${dhr.dhrRequestKind.toString()}" />  
            </g:if>
          </dd>
          
             <dt><g:message code="dhr.property.dhrSpouseTitle.label" /></dt>

          <dd>
            <g:if test="${dhr.dhrSpouseTitle}">
              <g:message code="dhr.property.dhrSpouseTitle.${dhr.dhrSpouseTitle}"/>
            </g:if>
          </dd>
          
             <dt><g:message code="dhr.property.dhrSpouseFamilyStatus.label" /></dt>

          <dd>
            <g:if test="${dhr.dhrSpouseFamilyStatus}">
              <g:message code="dhr.property.dhrSpouseFamilyStatus.${dhr.dhrSpouseFamilyStatus}"/>
            </g:if>
          </dd>
          
             <dt><g:message code="dhr.property.dhrSpouseName.label" /></dt>
<dd>${dhr.dhrSpouseName}</dd>
             <dt><g:message code="dhr.property.dhrSpouseFirstName.label" /></dt>
<dd>${dhr.dhrSpouseFirstName}</dd>
             <dt><g:message code="dhr.property.dhrSpouseMaidenName.label" /></dt>
<dd>${dhr.dhrSpouseMaidenName}</dd>
             <dt><g:message code="dhr.property.dhrSpouseBirthDate.label" /></dt>
<dd><g:formatDate formatName="format.date" date="${dhr.dhrSpouseBirthDate}"/></dd>
             <dt><g:message code="dhr.property.dhrSpouseBirthPlace.label" /></dt>
<dd>${dhr.dhrSpouseBirthPlace}</dd>
             <dt><g:message code="dhr.property.dhrSpouseNationality.label" /></dt>

          <dd>
            <g:if test="${dhr.dhrSpouseNationality}">
              <g:message code="dhr.property.dhrSpouseNationality.${dhr.dhrSpouseNationality}"/>
            </g:if>
          </dd>
          
             <dt><g:message code="dhr.property.dhrSpouseFranceArrivalDate.label" /></dt>
<dd><g:formatDate formatName="format.date" date="${dhr.dhrSpouseFranceArrivalDate}"/></dd>
             <dt><g:message code="dhr.property.dhrSpouseIsFrenchResident.label" /></dt>

          <dd><g:message code="widget.yesno.${dhr.dhrSpouseIsFrenchResident ? 'yes' : 'no'}" /></dd>
          
          </dl>

          <h4><g:message code="dhr.property.dhrSpouseStatus.label" /></h4>
          <dl>

             <dt><g:message code="dhr.property.dhrIsSpouseRetired.label" /></dt>

          <dd><g:message code="widget.yesno.${dhr.dhrIsSpouseRetired ? 'yes' : 'no'}" /></dd>
          
             <dt><g:message code="dhr.property.dhrSpousePrincipalPensionPlan.label" /></dt>

          <dd>
            <g:if test="${dhr.dhrSpousePrincipalPensionPlan}">
              <g:message code="dhr.property.dhrSpousePrincipalPensionPlan.${dhr.dhrSpousePrincipalPensionPlan}"/>
            </g:if>
          </dd>
          
             <dt><g:message code="dhr.property.dhrSpousePensionPlanDetail.label" /></dt>
<dd>${dhr.dhrSpousePensionPlanDetail}</dd>
             <dt><g:message code="dhr.property.dhrSpouseComplementaryPensionPlan.label" /></dt>
<dd>${dhr.dhrSpouseComplementaryPensionPlan}</dd>
             <dt><g:message code="dhr.property.dhrSpouseProfession.label" /></dt>
<dd>${dhr.dhrSpouseProfession}</dd>
             <dt><g:message code="dhr.property.dhrSpouseEmployer.label" /></dt>
<dd>${dhr.dhrSpouseEmployer}</dd>
             <dt><g:message code="dhr.property.dhrSpouseAddress.label" /></dt>

          <g:if test="${dhr.dhrSpouseAddress}">
            <dd>${dhr.dhrSpouseAddress?.additionalDeliveryInformation}</dd>
            <dd>${dhr.dhrSpouseAddress?.additionalGeographicalInformation}</dd>
            <dd>${dhr.dhrSpouseAddress?.streetNumber} ${dhr.dhrSpouseAddress?.streetName}</dd>
            <dd>${dhr.dhrSpouseAddress?.placeNameOrService}</dd>
            <dd>${dhr.dhrSpouseAddress?.postalCode} ${dhr.dhrSpouseAddress?.city}</dd>
            <dd>${dhr.dhrSpouseAddress?.countryName}</dd>
          </g:if>
          
          </dl>

          <h4><g:message code="dhr.property.dhrSpouseIncomes.label" /></h4>
          <dl>

             <dt><g:message code="dhr.property.pensions.label" /></dt>
<dd>${dhr.pensions}</dd>
             <dt><g:message code="dhr.property.dhrAllowances.label" /></dt>
<dd>${dhr.dhrAllowances}</dd>
             <dt><g:message code="dhr.property.dhrFurnitureInvestmentIncome.label" /></dt>
<dd>${dhr.dhrFurnitureInvestmentIncome}</dd>
             <dt><g:message code="dhr.property.dhrRealEstateInvestmentIncome.label" /></dt>
<dd>${dhr.dhrRealEstateInvestmentIncome}</dd>
             <dt><g:message code="dhr.property.dhrNetIncome.label" /></dt>
<dd>${dhr.dhrNetIncome}</dd>
          </dl>

  <h3><g:message code="dhr.step.dwelling.label" /></h3>

          <h4><g:message code="dhr.property.dhrCurrentDwelling.label" /></h4>
          <dl>

             <dt><g:message code="dhr.property.dhrCurrentDwellingAddress.label" /></dt>

          <g:if test="${dhr.dhrCurrentDwellingAddress}">
            <dd>${dhr.dhrCurrentDwellingAddress?.additionalDeliveryInformation}</dd>
            <dd>${dhr.dhrCurrentDwellingAddress?.additionalGeographicalInformation}</dd>
            <dd>${dhr.dhrCurrentDwellingAddress?.streetNumber} ${dhr.dhrCurrentDwellingAddress?.streetName}</dd>
            <dd>${dhr.dhrCurrentDwellingAddress?.placeNameOrService}</dd>
            <dd>${dhr.dhrCurrentDwellingAddress?.postalCode} ${dhr.dhrCurrentDwellingAddress?.city}</dd>
            <dd>${dhr.dhrCurrentDwellingAddress?.countryName}</dd>
          </g:if>
          
             <dt><g:message code="dhr.property.dhrCurrentDwellingPhone.label" /></dt>
<dd>${dhr.dhrCurrentDwellingPhone}</dd>
             <dt><g:message code="dhr.property.dhrCurrentDwellingKind.label" /></dt>

          <dd>
            <g:if test="${dhr.dhrCurrentDwellingKind}">
              <g:message code="dhr.property.dhrCurrentDwellingKind.${dhr.dhrCurrentDwellingKind}"/>
            </g:if>
          </dd>
          
             <dt><g:message code="dhr.property.dhrCurrentDwellingArrivalDate.label" /></dt>
<dd><g:formatDate formatName="format.date" date="${dhr.dhrCurrentDwellingArrivalDate}"/></dd>
             <dt><g:message code="dhr.property.dhrCurrentDwellingStatus.label" /></dt>

          <dd>
            <g:if test="${dhr.dhrCurrentDwellingStatus}">
              <g:message code="dhr.property.dhrCurrentDwellingStatus.${dhr.dhrCurrentDwellingStatus.toString()}" />  
            </g:if>
          </dd>
          
             <dt><g:message code="dhr.property.dhrCurrentDwellingNumberOfRoom.label" /></dt>
<dd>${dhr.dhrCurrentDwellingNumberOfRoom}</dd>
             <dt><g:message code="dhr.property.dhrCurrentDwellingNetArea.label" /></dt>
<dd>${dhr.dhrCurrentDwellingNetArea}</dd>
          </dl>

          <!-- <h4><g:message code="dhr.property.dhrPreviousDwelling.label" /></h4> -->
          <!-- TODO : Render one to many elements -->

  <h3><g:message code="dhr.step.resources.label" /></h3>

          <h4><g:message code="dhr.property.dhrRequesterIncomes.label" /></h4>
          <dl>

             <dt><g:message code="dhr.property.pensions.label" /></dt>
<dd>${dhr.pensions}</dd>
             <dt><g:message code="dhr.property.dhrAllowances.label" /></dt>
<dd>${dhr.dhrAllowances}</dd>
             <dt><g:message code="dhr.property.dhrFurnitureInvestmentIncome.label" /></dt>
<dd>${dhr.dhrFurnitureInvestmentIncome}</dd>
             <dt><g:message code="dhr.property.dhrRealEstateInvestmentIncome.label" /></dt>
<dd>${dhr.dhrRealEstateInvestmentIncome}</dd>
             <dt><g:message code="dhr.property.dhrNetIncome.label" /></dt>
<dd>${dhr.dhrNetIncome}</dd>
          </dl>

          <!-- <h4><g:message code="dhr.property.dhrRealAsset.label" /></h4> -->
          <!-- TODO : Render one to many elements -->

          <!-- <h4><g:message code="dhr.property.dhrNotRealAsset.label" /></h4> -->
          <!-- TODO : Render one to many elements -->

  <h3><g:message code="dhr.step.taxes.label" /></h3>

          <h4><g:message code="dhr.property.dhrTaxesAmount.label" /></h4>
          <dl>

             <dt><g:message code="dhr.property.dhrIncomeTax.label" /></dt>
<dd>${dhr.dhrIncomeTax}</dd>
             <dt><g:message code="dhr.property.localRate.label" /></dt>
<dd>${dhr.localRate}</dd>
             <dt><g:message code="dhr.property.propertyTaxes.label" /></dt>
<dd>${dhr.propertyTaxes}</dd>
             <dt><g:message code="dhr.property.professionalTaxes.label" /></dt>
<dd>${dhr.professionalTaxes}</dd>
          </dl>

  <h3><g:message code="request.step.document.label" /></h3>
  <!-- TODO : Render document summary template -->

