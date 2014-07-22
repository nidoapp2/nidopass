package com.nidoapp.nidopub.controller

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;

import grails.plugins.springsecurity.Secured

/**
 * Controlador que atiende peticiones de la admininistracion para administrar
 * @author Developer
 *
 */
@Secured([Role.ROLE_PUB_OWNER])
class AdminController {
	
	def springSecurityService

    def index = { 
		
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def pub = user.pubs[0]
		
		log.debug "Pub: " + pub?.properties
		
		[pub:pub]

	}
}
