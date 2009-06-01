<html>
  <head>
    <meta name="layout" content="fo_main" />
    <link rel="stylesheet" type="text/css"
          href="${createLinkTo(dir:'css/frontoffice', file:'dashboard.css')}" />
  </head>
  
  <body>
    <div id="yui-main"> 
      <g:if test="${commonInfo != null}">
        <div class="information-box">${commonInfo}</div>
      </g:if>
      <g:render template="requestList" />
      <g:render template="draftList" />
      <g:render template="paymentList" />
      <g:render template="documentList" />
    </div> 
  </body>
</html>

