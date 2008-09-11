import fr.cg95.cvq.exception.CvqException;
import fr.cg95.cvq.security.SecurityContext;
import fr.cg95.cvq.service.authority.ILocalAuthorityRegistry
import fr.cg95.cvq.service.authority.LocalAuthorityConfigurationBean
import fr.cg95.cvq.dao.hibernate.HibernateUtil;

import javax.servlet.ServletException

import org.hibernate.SessionFactory;

class SessionFilters {
    def filters = {
            openSessionInView(controller:'*', action:'*') {
                before = {
                        ILocalAuthorityRegistry localAuthorityRegistry =
                            applicationContext.getBean("localAuthorityRegistry")
            			LocalAuthorityConfigurationBean lacb = 
                			localAuthorityRegistry.getLocalAuthorityBeanByUrl(request.serverName)
            			if (lacb == null)
               	 			throw new ServletException("No local authority found !")
            			SessionFactory sessionFactory = lacb.getSessionFactory()
            			HibernateUtil.setSessionFactory(sessionFactory)
            
            			HibernateUtil.beginTransaction()

            			try {
                			SecurityContext.setCurrentSite(lacb.getName(), "backOffice")
                			SecurityContext.setCurrentLocale(request.getLocale())
                
                			// FIXME : temp hack to avoid setting up authentication right now
                			SecurityContext.setCurrentAgent("admin.valdoise");
                			
            			} catch (CvqException ce) {
                			//logger.error("Error while setting current site")
                			ce.printStackTrace()
                			throw new ServletException()
            			}

            			session.setAttribute("currentSiteName",  lacb.getName().toLowerCase())
            			session.setAttribute("doRollback", false)
                }
                after = {
                        def doRollback = session.getAttribute("doRollback")
                        if (doRollback)
                            HibernateUtil.rollbackTransaction();
                        else
                            HibernateUtil.commitTransaction();
            			// No matter what happens, close the Session.
                        HibernateUtil.closeSession();

                        SecurityContext.resetCurrentSite();
                }
            }
    }
}
