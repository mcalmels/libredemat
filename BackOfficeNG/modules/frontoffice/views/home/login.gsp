<html>
  <head>
    <title>${message(code:'home.portal.title',args:[session.currentSiteDisplayTitle])}</title>
    <meta name="layout" content="fo_main" />
   <!-- TODO : extract styles for form styles -->
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css/frontoffice', file:'request.css')}" />
  </head>
  
  <body>
    <g:if test="${flash.successMessage}">
      <div class="success-box"><p>${flash.successMessage}</p></div>
    </g:if>
      <g:render template="/shared/services" />
  </body>
</html>

