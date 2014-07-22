package com.nidoapp.nidopub.controller
import grails.plugins.springsecurity.Secured

import com.nidoapp.nidopub.domain.Pub;
import com.nidoapp.nidopub.domain.request.ServiceRequest
import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;

/**
 * Controlador que atiende peticiones de la admininistracion para gestionar
 * las cartas y men???s.
 * @author Developer
 *
 */
@Secured([Role.ROLE_PUB_OWNER])
class EMailsAdminAccessController {

     def springSecurityService

    def index = { 
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		if(!pub.eventsAvailable) {
			redirect (controller: "pub", action: "pubInquiryDenied", params: [id:pub.uri], model: [user:user, pub:pub])
			return
		}
	
		[user:user, pub:pub]		
		
	}
}
