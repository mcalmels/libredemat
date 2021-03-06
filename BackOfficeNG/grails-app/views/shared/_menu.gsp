<h1><a class="logo">LibreDémat</a></h1>
<div id="userInfo">
  <g:if test="${session.currentCredentialBean}">
    <span style="font-weight:bold;">${session.currentUser}</span>
    - <a href="${createLink(controller:'backofficeLogin',action:'logout')}">
        <g:message code="action.logout" />
      </a>
    - <a href="${createLink(controller:'localAuthorityResource',action:'resource',id:'helpBo')}"
         target="blank"><g:message code="header.help" /></a>
  </g:if>
</div>
<div id="menu">
  <g:if test="${session.currentCredentialBean}">
    <g:if test="${session.currentCredentialBean.hasSiteAgentRole()}">
      <a id="tasksMenuItem" href="${createLink(controller:'backofficeTasks')}">
        <g:message code="menu.tasks" />
      </a>
      <a id="requestsMenuItem" href="${createLink(controller:'backofficeRequest')}">
        <g:message code="menu.requests" />
      </a>
      <a id="usersMenuItem" href="${createLink(controller:'backofficeHomeFolder')}">
        <g:message code="menu.users" />
      </a>
      <g:if test="${session.isACategoryManager}">
        <a id="statisticsMenuItem" href="${createLink(controller:'backofficeStatistic')}">
          <g:message code="menu.statistics" />
        </a>
      </g:if>
      <g:if test="${session.isViewPayment}">
        <a id="paymentMenuItem" href="${createLink(controller:'backofficePayment')}">
            <g:message code="menu.payments" />
        </a>
      </g:if>
    </g:if>
    <g:if test="${session.currentCredentialBean.hasSiteAdminRole()}">
      <a id="localAuthorityMenuItem" href="${createLink(controller:'backofficeLocalAuthority')}">
        <g:message code="menu.localAuthority" />
      </a>
       <a id="requestAdminMenuItem" href="${createLink(controller:'backofficeRequestAdmin')}">
        <g:message code="menu.requestsAdmin" />
      </a>
      <a id="referentialMenuItem" href="${createLink(controller:'backofficeReferential')}">
        <g:message code="menu.referentials" />
      </a>
      <a id="usersMenuItem" href="${createLink(controller:'backofficeUserAdmin', action:'index')}">
        <g:message code="menu.users" />
      </a>
      <g:if test="${session.additionalTabs.contains('Payments')}">
        <a id="paymentMenuItem" href="${createLink(controller:'backofficePayment')}">
          <g:message code="menu.payments" />
        </a>
      </g:if>
    </g:if>
  </g:if>
  <g:else>
    <a id="loginMenuItem" href="${createLink(controller : 'backofficeLogin')}">
      <g:message code="menu.login" />
    </a>
  </g:else>
</div>

