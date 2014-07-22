package com.nidoapp.nidopub.controller

import grails.plugins.springsecurity.Secured

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;

/**
 * Controlador que permite acceder a la Home de la administracion
 * @author Developer
 *
 */
@Secured([Role.ROLE_CUSTOMER, Role.ROLE_PUB_OWNER, Role.ROLE_ASSOCIATION_OWNER, Role.ROLE_SUPERADMIN])
class HomeController {
	
	def springSecurityService

    def index = {		
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
		
		[user:user]

	}
}
