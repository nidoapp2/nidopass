package com.nidoapp.nidopub.controller
import grails.plugins.springsecurity.Secured
import com.nidoapp.nidopub.domain.request.ServiceRequest
import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;

/**
 * Controlador que atiende peticiones de la admininistracion para gestionar los camareros
 * @author Developer
 *
 */
@Secured([Role.ROLE_PUB_OWNER])
class WaitersController {

     def springSecurityService

    def index = { 
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)		
	
		[user:user]		
		
	}
}
