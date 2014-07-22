package com.nidoapp.nidopub.controller

import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured;

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.user.UserRole;
import com.nidoapp.nidopub.domain.Product;
import com.nidoapp.nidopub.domain.PubFeature;

class PubFeatureController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def springSecurityService
	
	def imageRepositoryService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [pubFeatureInstanceList: PubFeature.list(params), pubFeatureInstanceTotal: PubFeature.count()]
    }

    def create() {
        [pubFeatureInstance: new PubFeature(params)]
    }

    def save() {
        def pubFeatureInstance = new PubFeature(params)
        if (!pubFeatureInstance.save(flush: true)) {
            render(view: "create", model: [pubFeatureInstance: pubFeatureInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'pubFeature.label', default: 'PubFeature'), pubFeatureInstance.id])
        redirect(action: "show", id: pubFeatureInstance.id)
    }

    def show(Long id) {
        def pubFeatureInstance = PubFeature.get(id)
        if (!pubFeatureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubFeature.label', default: 'PubFeature'), id])
            redirect(action: "list")
            return
        }

        [pubFeatureInstance: pubFeatureInstance]
    }

    def edit(Long id) {
        def pubFeatureInstance = PubFeature.get(id)
        if (!pubFeatureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubFeature.label', default: 'PubFeature'), id])
            redirect(action: "list")
            return
        }

        [pubFeatureInstance: pubFeatureInstance]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		
		if(UserRole.findByUser(user).role.authority != Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SUPERADMIN, por lo que no puede gestionar los datos e iconos de características."
			response.sendError(404)
		}
		
		
        def pubFeatureInstance = PubFeature.get(id)
        if (!pubFeatureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubFeature.label', default: 'PubFeature'), id])
            redirect(action: "list")
            return
        }
		
		saveNewPhoto(pubFeatureInstance)

        if (version != null) {
            if (pubFeatureInstance.version > version) {
                pubFeatureInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'pubFeature.label', default: 'PubFeature')] as Object[],
                          "Another user has updated this PubFeature while you were editing")
                render(view: "edit", model: [pubFeatureInstance: pubFeatureInstance])
                return
            }
        }

        pubFeatureInstance.properties = params

        if (!pubFeatureInstance.save(flush: true)) {
            render(view: "edit", model: [pubFeatureInstance: pubFeatureInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pubFeature.label', default: 'PubFeature'), pubFeatureInstance.id])
        redirect(action: "show", id: pubFeatureInstance.id)
    }

    def delete(Long id) {
        def pubFeatureInstance = PubFeature.get(id)
        if (!pubFeatureInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubFeature.label', default: 'PubFeature'), id])
            redirect(action: "list")
            return
        }

        try {
            pubFeatureInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'pubFeature.label', default: 'PubFeature'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pubFeature.label', default: 'PubFeature'), id])
            redirect(action: "show", id: id)
        }
    }
	
	/**
	 * Guarda la foto indicada
	 */
	def saveNewPhoto(PubFeature feature) {
			
		def f = request.getFile('photoFile')
		
		if (f && !f.empty) {
			
			def repositoryImageId = imageRepositoryService.saveImage(f.inputStream)
			if(repositoryImageId)
			{
				
				feature.repositoryImageId = repositoryImageId
				
			}
		}
	}
}
