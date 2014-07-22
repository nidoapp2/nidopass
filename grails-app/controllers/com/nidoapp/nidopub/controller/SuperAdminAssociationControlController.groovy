package com.nidoapp.nidopub.controller
import grails.plugins.springsecurity.Secured

import com.nidoapp.nidopub.domain.Association;
import com.nidoapp.nidopub.domain.request.ServiceRequest
import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.user.UserRole;

/**
 * Controlador que atiende peticiones de la admininistracion para gestionar
 * las cartas y men???s.
 * @author Developer
 *
 */
@Secured([Role.ROLE_SUPERADMIN])
class SuperAdminAssociationControlController {

     def springSecurityService

    def index = { 
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def association = Association.findById(params.id)
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SuperAdmin"
			response.sendError(404)
		}
	
		[user:user, association:association]		
		
	}
	
	def associationPubsProvinceSelection = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def association = Association.findById(params.id)
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SuperAdmin"
			response.sendError(404)
		}
	
		[user:user, association:association]
		
	}
}
