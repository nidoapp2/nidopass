package com.nidoapp.nidopub.controller

import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured;

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.EMail;

class EMailController {

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
		
		def EMailInstanceList = EMail.findAllByPub(pub)
		
        [EMailInstanceList: EMailInstanceList, EMailInstanceTotal: EMail.count(), user:user, pub:pub]
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
		
        [EMailInstance: new EMail(params), user:user, pub:pub]
    }

    def save() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
        def EMailInstance = new EMail(params)
		
		def pub = Pub.findByUri(params.id_pub)
		
		EMailInstance.pub = pub
		
        if (!EMailInstance.save(flush: true)) {
            render(view: "create", model: [EMailInstance: EMailInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'EMail.label', default: 'EMail'), EMailInstance.id])
        //redirect(action: "show", id: EMailInstance.id)
		redirect(uri: "/EMail/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
    }

    def show(Long id) {
        def EMailInstance = EMail.get(id)
        if (!EMailInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'EMail.label', default: 'EMail'), id])
            redirect(action: "list")
            return
        }

        [EMailInstance: EMailInstance]
    }

    def edit(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(EMail.findById(id).pub.id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def EMailInstance = EMail.get(id)
        if (!EMailInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'EMail.label', default: 'EMail'), id])
            redirect(uri: "/EMail/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
            return
        }

        [EMailInstance: EMailInstance, user:user, pub:pub]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(EMail.findById(id).pub.id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def EMailInstance = EMail.get(id)
		
		EMailInstance.pub = Pub.findById(EMailInstance.pub.id)
		
        if (!EMailInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'EMail.label', default: 'EMail'), id])
            redirect(uri: "/EMail/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
            return
        }

        if (version != null) {
            if (EMailInstance.version > version) {
                EMailInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'EMail.label', default: 'EMail')] as Object[],
                          "Another user has updated this EMail while you were editing")
                render(view: "edit", model: [EMailInstance: EMailInstance, user:user, pub:pub])
                return
            }
        }

        EMailInstance.properties = params

        if (!EMailInstance.save(flush: true)) {
            render(view: "edit", model: [EMailInstance: EMailInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'EMail.label', default: 'EMail'), EMailInstance.id])
        redirect(uri: "/EMail/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
    }

    def delete(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(EMail.findById(id).pub.id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def EMailInstance = EMail.get(id)
        if (!EMailInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'EMail.label', default: 'EMail'), id])
            redirect(uri: "/EMail/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
            return
        }

        try {
            EMailInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'EMail.label', default: 'EMail'), id])
            redirect(uri: "/EMail/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'EMail.label', default: 'EMail'), id])
            redirect(action: "edit", model: [EMailInstance: EMailInstance, user:user, pub:pub])
        }
    }
}
