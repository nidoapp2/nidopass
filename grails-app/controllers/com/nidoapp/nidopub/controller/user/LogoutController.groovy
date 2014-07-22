package com.nidoapp.nidopub.controller.user
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils

/**
 * Controlador de Spring Security para atender operaciones de salida de la administracion
 * @author Developer
 *
 */
class LogoutController {

	/**
	 * Index action. Redirects to the Spring security logout uri.
	 */
	def index = {
		// TODO put any pre-logout code here
		redirect uri: SpringSecurityUtils.securityConfig.logout.filterProcessesUrl // '/j_spring_security_logout'
	}
}
