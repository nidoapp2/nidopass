package com.nidoapp.nidopub.controller

import grails.plugins.springsecurity.Secured

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;

/**
 * Controlador que atiende peticiones de la admininistracion para gestionar las cuentas de usuario 
 * @author Developer
 *
 */
@Secured([Role.ROLE_CUSTOMER, Role.ROLE_PUB_OWNER, Role.ROLE_ASSOCIATION_OWNER, Role.ROLE_SUPERADMIN])
class AccountController {

     def springSecurityService

    def index = {		
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
		
		[user:user, userInstance:user]

	}
	
	def updatePersonalInfo(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		/*log.debug "Restaurant: " + restaurant?.properties
		
		if(restaurant?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del restaurante con ID '${restaurant?.id}'"
			response.sendError(404)
		}*/
		
		
		def userInstance = user

		if (version != null) {
			if (userInstance.version > version) {
				userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'carte.label', default: 'Carte')] as Object[],
						  "Another user has updated this Carte while you were editing")
				render(view: "index", model: [userInstance: userInstance, user:user])
				return
			}
		}

		userInstance.properties = params

		if (!userInstance.save(flush: true)) {
			render(view: "index", model: [userInstance: userInstance, user:user])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
		redirect(action: 'index', model:[user:user])
	}
}
