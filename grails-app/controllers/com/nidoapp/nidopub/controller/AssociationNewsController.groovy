package com.nidoapp.nidopub.controller

import org.springframework.dao.DataIntegrityViolationException
import org.hibernate.exception.ConstraintViolationException;

import grails.plugins.springsecurity.Secured;

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.Association;
import com.nidoapp.nidopub.domain.AssociationNews;
import com.nidoapp.nidopub.domain.Product;

class AssociationNewsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def springSecurityService
	
	def imageRepositoryService
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		params.max = Math.min(max ?: 10, 100)
		
		if(!user.association)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la asociación'"
			response.sendError(404)
		}
		
		def associationNewsInstanceList = AssociationNews.findAllByAssociation(user.association)
		
        [associationNewsInstanceList: associationNewsInstanceList, associationNewsInstanceTotal: AssociationNews.count()]
    }

    def create() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		if(!user.association)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la asociación"
			response.sendError(404)
		}
		
        [associationNewsInstance: new AssociationNews(params), user:user, association:user.association]
    }

    def save() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def associationNewsInstance = new AssociationNews(params)
		
		associationNewsInstance.association = user.association
		associationNewsInstance.creationDate = new Date()
		
		saveNewPhoto(associationNewsInstance)
		
		
        if (!associationNewsInstance.save(flush: true)) {
            render(view: "create", model: [associationNewsInstance: associationNewsInstance, user:user, association:user.association])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'associationNews.label', default: 'AssociationNews'), associationNewsInstance.id])
        redirect(action: "list", model: [user: user, association:user.association])
    }

    def show(Long id) {
        def associationNewsInstance = AssociationNews.get(id)
        if (!associationNewsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'associationNews.label', default: 'AssociationNews'), id])
            redirect(action: "list")
            return
        }

        [associationNewsInstance: associationNewsInstance]
    }

    def edit(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		if(!user.association)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la asociación"
			response.sendError(404)
		}
		
        def associationNewsInstance = AssociationNews.get(id)
        if (!associationNewsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'associationNews.label', default: 'AssociationNews'), id])
            redirect(action: "list")
            return
        }

        [associationNewsInstance: associationNewsInstance, user: user, association: user.association]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		if(!user.association)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la asociación"
			response.sendError(404)
		}
		
        def associationNewsInstance = AssociationNews.get(id)
        if (!associationNewsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'associationNews.label', default: 'AssociationNews'), id])
            redirect(action: "list")
            return
        }
		
		saveNewPhoto(associationNewsInstance)

        if (version != null) {
            if (associationNewsInstance.version > version) {
                associationNewsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'associationNews.label', default: 'AssociationNews')] as Object[],
                          "Another user has updated this AssociationNews while you were editing")
                render(view: "edit", model: [associationNewsInstance: associationNewsInstance])
                return
            }
        }

        associationNewsInstance.properties = params

        if (!associationNewsInstance.save(flush: true)) {
            render(view: "edit", model: [associationNewsInstance: associationNewsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'associationNews.label', default: 'AssociationNews'), associationNewsInstance.id])
        redirect(action: "list", model: [user: user, association:user.association])
    }

    def delete(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		if(!user.association)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la asociación"
			response.sendError(404)
		}
		
        def associationNewsInstance = AssociationNews.get(id)
        if (!associationNewsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'associationNews.label', default: 'AssociationNews'), id])
            redirect(action: "list")
            return
        }

        try {
            associationNewsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'associationNews.label', default: 'AssociationNews'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'associationNews.label', default: 'AssociationNews'), id])
            redirect(action: "edit", model: [associationNewsInstance: associationNewsInstance, user:user, association:user.association])
        }
		catch (ConstraintViolationException e) {
			redirect(action: "list")
		}
    }
	
	/**
	 * Guarda la foto indicada en la ventana de nueva foto
	 */
	def saveNewPhoto(AssociationNews associationNews) {
			
		def f = request.getFile('photoFile')
		
		if (f && !f.empty) {
			
			def repositoryImageId = imageRepositoryService.saveImage(f.inputStream)
			if(repositoryImageId)
			{
				
				associationNews.photo = repositoryImageId
				
			}
		}
	}
}
