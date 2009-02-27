<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="layout" content="fo_main" />
    <link rel="stylesheet" type="text/css"
          href="${createLinkTo(dir:'css/frontoffice', file:'request.css')}" />
  </head>
  <body>
    <div class="main-box">
      <h2><g:message code="request.header.summary"
                     args="${[requestTypeLabel,rqt.id]}"/></h2>
      <div id="requestTabView" class="requestSummary">
        <g:render template="/frontofficeRequestType/${validationTemplateDirectory}/validation"
                  model="['rqt':rqt]" />
      </div>
    </div>
  </body>
</html>
