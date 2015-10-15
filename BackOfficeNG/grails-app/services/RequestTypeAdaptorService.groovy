import org.libredemat.business.users.HomeFolder
import org.libredemat.business.request.DisplayGroup
import org.libredemat.business.request.RequestType
import org.libredemat.business.request.RequestState
import org.libredemat.business.request.Request
import org.libredemat.exception.CvqException
import org.libredemat.exception.CvqModelException
import org.libredemat.security.SecurityContext
import org.libredemat.service.request.ILocalReferentialService
import org.libredemat.service.request.IRequestService
import org.libredemat.service.request.IRequestTypeService
import org.libredemat.service.request.IRequestServiceRegistry
import org.libredemat.service.request.IRequestWorkflowService
import org.libredemat.service.request.IRequestSearchService
import org.libredemat.service.request.IDisplayGroupService
import org.libredemat.service.authority.ILocalAuthorityRegistry
import org.libredemat.util.Critere

import org.codehaus.groovy.grails.web.context.ServletContextHolder

class SplitMap {
    /**
     * Return a list containing two maps, each map being the half of the original map.
     * Example:
     * use(SplitMap) {
     *     assert [1:'a', 2:'b', 3:'c', 4:'d'].split() == [ [1:'a', 2:'b'], [3:'c', 4:'d'] ]
     * }
     */
    static List split(Map delegate) {
        def finalList = delegate.inject([ [:], [:] ]) { currentList, mapElement ->
            (currentList[0].size() < delegate.size() / 2 ? currentList[0] : currentList[1]) << mapElement
            return currentList
        }
    }
}

public class RequestTypeAdaptorService {

    def messageSource

    IRequestTypeService requestTypeService
    IRequestServiceRegistry requestServiceRegistry
    IRequestWorkflowService requestWorkflowService
    IRequestSearchService requestSearchService
    ILocalReferentialService localReferentialService
    IDisplayGroupService displayGroupService
    ILocalAuthorityRegistry localAuthorityRegistry

    public Map getDisplayGroups(HomeFolder homeFolder) {
        def result = [:]
        
        for(DisplayGroup dg : displayGroupService.getAll()) {
            if(!result.keySet().contains(dg.name))
                result[dg.name] = ['label':dg.label,'requests':[]]
            
            for(RequestType rt : dg.requestTypes.sort{it.weight!=null ? it.weight : it.label}) {
                if (!rt.active) 
                    continue
                def i18nError = null
                try {
                    requestWorkflowService.checkRequestTypePolicy(rt, homeFolder)
                } catch (CvqException e) {
                    i18nError = e.i18nKey
                }
                result[dg.name].requests.add(['label': rt.label,
                                              'id': rt.id,
                                              'countDraft': countDraft(rt.label),
                                              'seasons' : requestServiceRegistry.getRequestService(rt.label).isOfRegistrationKind() ? requestTypeService.getOpenSeasons(rt) : [],
                                              'enabled': i18nError == null,
                                              'message': i18nError
                                             ])
            }
            
            result[dg.name].requests = result[dg.name].requests
        }

        // filter groups with no requests
        def tempMap = result.findAll { k,v ->
            !v.requests.isEmpty()
        }

        return tempMap
    }

    public Map getActiveRequestTypeByDisplayGroup(HomeFolder homeFolder) {
        def result = [:]
        result["agentOnly"] = ['label': messageSource.getMessage("displayGroup.label.agentOnly", null, SecurityContext.currentLocale),'requests':[]]

        for(RequestType rt : requestTypeService.getAllRequestTypes()) {
            if (!rt.active)
                continue

            def dgName = rt.displayGroup != null ? rt.displayGroup.name : "agentOnly"
            if (rt.displayGroup != null && !result.keySet().contains(rt.displayGroup.name))
                result[rt.displayGroup.name] = ['label':rt.displayGroup.label,'requests':[]]

            result[dgName].requests.add(['label': rt.label, 'id': rt.id])
            result[dgName].requests = result[dgName].requests.sort{it -> it.label}
        }

        // remove agentOnly from the list if empty
        if (result.get("agentOnly").get("requests").isEmpty())
            result.remove("agentOnly")

       return result
    }

    def protected countDraft(requestTypeLabel) {
        // Security policy hack
        if (SecurityContext.currentEcitizen == null) return 0
        def criterias = [] as Set;

        def critere = new Critere();
        critere.comparatif = Critere.EQUALS;
        critere.attribut = Request.SEARCH_BY_REQUEST_TYPE_LABEL;
        critere.value = requestTypeLabel
        criterias.add(critere)

        critere = new Critere()
        critere.comparatif = Critere.EQUALS
        critere.attribut = Request.SEARCH_BY_STATE
        critere.value = RequestState.DRAFT
        criterias.add(critere)

        return requestSearchService.getCount(criterias)
    }

    // FIXME - feature duplicated in CertificateService
    // TODO - mutualize
    public Map getLocalReferentialTypes(requestTypeLabel) {
        def result = [:]
        try {
            localReferentialService.getLocalReferentialTypes(requestTypeLabel).each {
                result.put(StringUtils.firstCase(it.name,'Lower'), it)
            }
        } catch (CvqException ce) { /* No localReferentialData found ! */ }

        return result
    }

    public Map getCustomJS(requestTypeLabel) {
        def customJS = [
            dir : "js/frontoffice/requesttype",
            file : LibredematUtils.requestTypeLabelAsDir(requestTypeLabel) + ".js"
        ]
        if (ServletContextHolder.servletContext.getResource(["", customJS.dir, customJS.file].join('/')) != null)
            return customJS
        return null
    }

    public Map getCustomCSS(requestTypeLabel) {
        def customCSS = [
                dir : "css/frontoffice/requesttype",
                file : LibredematUtils.requestTypeLabelAsDir(requestTypeLabel) + ".css"
        ]
        if (ServletContextHolder.servletContext.getResource(["", customCSS.dir, customCSS.file].join('/')) != null)
            return customCSS
        return null
    }

    public String generateAcronym(label) {
        def acronym = ''
        label.split(' ').each {
            acronym += it[0].toLowerCase()
        }
        return acronym + 'r'
    }

    public Map requestTypeResources(requestTypeLabel) {
        def requestTypeLabelAsDir = LibredematUtils.requestTypeLabelAsDir(requestTypeLabel)
        return [
            'lrTypes': getLocalReferentialTypes(requestTypeLabel),
            'requestTypeLabel': requestTypeLabel,
            'requestTypeLabelAsDir' : requestTypeLabelAsDir,
            'helps': localAuthorityRegistry.getBufferedCurrentLocalAuthorityRequestHelpMap(requestTypeLabelAsDir),
            'availableRules' : localAuthorityRegistry.getLocalAuthorityRules(requestTypeLabelAsDir),
            'customJS' : getCustomJS(requestTypeLabel)
         ]
    }

}
