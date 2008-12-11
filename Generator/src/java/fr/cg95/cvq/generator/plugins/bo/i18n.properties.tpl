${acronym}.label = ${requestI18n?.get(lang)?.get('short')}
${acronym}.descritption = ${requestI18n?.get(lang)?.get('long') != null ? requestI18n?.get(lang)?.get('long') : ''}
<% elements.each { el -> %>
${el.i18nPrefixCode}.label = ${el.i18nUserDoc.get(lang) != null ? el.i18nUserDoc.get(lang).text : ''} 
<% el.i18nUserDoc.get('fr').xmlTranslationNodes.each { entry -> %> ${el.i18nPrefixCode}.${entry.key} = ${el.i18nUserDoc?.get(lang)?.xmlTranslationNodes != null ? el.i18nUserDoc?.get(lang)?.xmlTranslationNodes?.get(entry.key) : ''}
<% } %><% } %>
