package com.nidoapp.nidopub.controller

import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured;

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.EMailsList;

class EMailsListController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def springSecurityService
	
    def index() {
        redirect(action: "create", params: params)
    }

    def list(Integer max) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
        params.max = Math.min(max ?: 10, 100)
		
		def pub = Pub.findByUri(params.id_pub)
		
		//id_pub = pub.id
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		if(!pub.eventsAvailable) {
			redirect (controller: "pub", action: "pubInquiryDenied", params: [id:pub.uri], model: [user:user, pub:pub])
			return
		}
		
		def EMailsListInstanceList = EMailsList.findAllByPub(pub)
		
        [EMailsListInstanceList: EMailsListInstanceList, EMailsListInstanceTotal: EMailsList.count(), user:user, pub:pub]
    }

    def create() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findByUri(params.id_pub)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        [EMailsListInstance: new EMailsList(params), user:user, pub:pub]
    }

    def save() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
        def EMailsListInstance = new EMailsList(params)
		
		def pub = Pub.findByUri(params.id_pub)
		
		EMailsListInstance.pub = pub
		
        if (!EMailsListInstance.save(flush: true)) {
            render(view: "create", model: [EMailsListInstance: EMailsListInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'EMailsList.label', default: 'EMailsList'), EMailsListInstance.id])
        //redirect(action: "show", id: EMailsListInstance.id)
		redirect(uri: "/EMailsList/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
    }

    def show(Long id) {
        def EMailsListInstance = EMailsList.get(id)
        if (!EMailsListInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'EMailsList.label', default: 'EMailsList'), id])
            redirect(action: "list")
            return
        }

        [EMailsListInstance: EMailsListInstance]
    }

    def edit(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(EMailsList.findById(id).pub.id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def EMailsListInstance = EMailsList.get(id)
        if (!EMailsListInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'EMailsList.label', default: 'EMailsList'), id])
            redirect(uri: "/EMailsList/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
            return
        }

        [EMailsListInstance: EMailsListInstance, user:user, pub:pub]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(EMailsList.findById(id).pub.id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def EMailsListInstance = EMailsList.get(id)
		
		EMailsListInstance.pub = Pub.findById(EMailsListInstance.pub.id)
		
        if (!EMailsListInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'EMailsList.label', default: 'EMailsList'), id])
            redirect(uri: "/EMailsList/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
            return
        }

        if (version != null) {
            if (EMailsListInstance.version > version) {
                EMailsListInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'EMailsList.label', default: 'EMailsList')] as Object[],
                          "Another user has updated this EMailsList while you were editing")
                render(view: "edit", model: [EMailsListInstance: EMailsListInstance, user:user, pub:pub])
                return
            }
        }

        EMailsListInstance.properties = params

        if (!EMailsListInstance.save(flush: true)) {
            render(view: "edit", model: [EMailsListInstance: EMailsListInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'EMailsList.label', default: 'EMailsList'), EMailsListInstance.id])
        redirect(uri: "/EMailsList/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
    }

    def delete(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(EMailsList.findById(id).pub.id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def EMailsListInstance = EMailsList.get(id)
        if (!EMailsListInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'EMailsList.label', default: 'EMailsList'), id])
            redirect(uri: "/EMailsList/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
            return
        }

        try {
            EMailsListInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'EMailsList.label', default: 'EMailsList'), id])
            redirect(uri: "/EMailsList/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'EMailsList.label', default: 'EMailsList'), id])
            redirect(action: "edit", model: [EMailsListInstance: EMailsListInstance, user:user, pub:pub])
        }
    }
}
