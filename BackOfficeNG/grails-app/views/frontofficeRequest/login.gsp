<html>
  <head>
    <title>${message(code : "home.portal.title", args : [session.currentSiteDisplayTitle])}</title>
    <meta name="layout" content="fo_main" />
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice/common', file:'form.css')}" />
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice/common', file:'data-detail.css')}" />
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice', file:'request.css')}" />
  </head>
  <body>
    <g:if test="${flash.errorMessage}">
      <div class="error-box">
        <p>${flash.errorMessage}</p>
      </div>
    </g:if>
    <div class="yui-g">
      <div class="yui-u first main-box">
        <h2>${message(code:'account.message.useAccountToFillRequest')}</h2>
        <form action="${createLink(controller : 'frontofficeHome', action : 'login')}" method="post">
          <input type="hidden" name="requestTypeLabel" value="${params.requestTypeLabel}" />
          <label for="login" class="required">
            <g:message code="homeFolder.adult.property.login" />
          </label>
          <input type="text" id="login" name="login" value="" class="required"
            title="<g:message code="homeFolder.adult.property.login.validationError" />" />
          <label for="password" class="required">
            <g:message code="homeFolder.adult.property.password" />
          </label>
          <input type="password" id="password" name="password" value="" class="required"
            title="<g:message code="homeFolder.adult.property.password.validationError" />" />
          <input type="submit" value="${message(code:'action.login')}" />
        </form>
      </div>
      <div class="yui-u main-box">
        <h2><g:message code="homeFolder.action.createAccount"/></h2>
        <p>En créant un compte, vous avez la possibilité de:</p>
        <ul>
          <li>gérer vos données administratives et déclarer les membres de votre foyer,</li>
          <li>accéder à des démarches en ligne pour vous ou un membre de votre foyer,</li>
          <li>suivre l'avancement de vos demandes.</li>
        </ul>
        <a href="${createLink(controller : 'frontofficeHomeFolder', action : 'create', params : ['requestTypeLabel' : params.requestTypeLabel])}">
          Je souhaite créer mon compte
        </a>
      </div>
    </div>
  </body>
</html>
