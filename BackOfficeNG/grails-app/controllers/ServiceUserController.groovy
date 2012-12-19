import fr.cg95.cvq.security.SecurityContext
import fr.cg95.cvq.service.users.IUserSearchService
import fr.cg95.cvq.authentication.IAuthenticationService
import fr.cg95.cvq.exception.CvqAuthenticationFailedException
import fr.cg95.cvq.exception.CvqDisabledAccountException
import fr.cg95.cvq.exception.CvqUnknownUserException
import grails.converters.JSON

class ServiceUserController {
    IUserSearchService userSearchService
    IAuthenticationService authenticationService

    /**
     * Send back a JSON object {"connected": false}
     *                      or {"connected": true, "firstname": "Jean", "lastname": "DUPONT"}
     *
     * By default, the JSON object is wrapped in JSONP.
     * But it can be sent as it if we're asked explicitly for JSON (http://…/login.json)
     *
     * See http://jsfiddle.net/HjCc2/6/ for usage.
     */
    def login = {
        def user
        try {
            SecurityContext.setCurrentContext(SecurityContext.FRONT_OFFICE_CONTEXT)
            if (session.currentEcitizenId) {
                SecurityContext.setCurrentEcitizen(session.currentEcitizenId)
                user = userSearchService.getById(session.currentEcitizenId)
            }
        } catch (Exception e) {
            log.error e.message
            render status: 500
        }
        def map
        if (user)
            map = [connected:true, firstname:user.firstName, lastName:user.lastName]
        else
            map = [connected:false]

        withFormat {
            js {
                render text: (params.callback ?: 'callback') + '(' + (map as JSON) + ')',
                       contentType: 'text/javascript',
                       status: 200
            }
            json {
                // Note: '*' can't be used with credentials.
                response.setHeader 'Access-Control-Allow-Origin', request.getHeader('Origin')
                response.setHeader 'Access-Control-Allow-Credentials', 'true'
                render text: map as JSON,
                       contentType: 'application/json',
                       status: 200
            }
        }
    }
    def auth = {
      println("POST - auth")
      def error
        try {
          def res = authenticationService.authenticate(params.login,params.password)
          render text: "",
                contentType: "application/json",
                status: 200
          return true;
        } catch (CvqUnknownUserException e) {
          error = "account.error.invalidLogin"
        } catch (CvqAuthenticationFailedException e) {
          error = "account.error.authenticationFailed"
        } catch (CvqDisabledAccountException e) {
          error = "account.error.disabledAccount"
        }
        render text: "{ error: \""+error+"\", error_description: \""+message(code : error)+"\"}",
               contentType: 'application/json',
               status: (error == "account.error.authenticationFailed") ? 500 :
                         (error == "account.error.invalidLogin") ? 404 :
                         401
    }
}
