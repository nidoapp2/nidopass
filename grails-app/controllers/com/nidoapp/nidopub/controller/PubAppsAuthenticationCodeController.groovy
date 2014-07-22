package com.nidoapp.nidopub.controller

import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured;

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.user.UserRole;
import com.nidoapp.nidopub.domain.Pub;
import com.nidoapp.nidopub.domain.PubAppsAuthenticationCode;

class PubAppsAuthenticationCodeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def springSecurityService
	
	def mailingService
	def messageSource
	def templateService
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max, long pubId) {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
        params.max = Math.min(max ?: 10, 100)
		
		def pub = Pub.findById(params.id)
		
		pubId = pub.id
		
		if(pub.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la sala con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		def pubAppsAuthenticationCodeInstanceList = PubAppsAuthenticationCode.findAllByPub(pub)
		
        [pubAppsAuthenticationCodeInstanceList: pubAppsAuthenticationCodeInstanceList, pubAppsAuthenticationCodeInstanceTotal: PubAppsAuthenticationCode.count(), user:user, pub:pub]
    }

    def create() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
				
		log.debug "Salas: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(params.pubId)
		
		log.debug "Sala: " + pub?.properties
		
		if(pub.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la sala con ID '${pub.id}'"
			response.sendError(404)
		}
		
        [pubAppsAuthenticationCodeInstance: new PubAppsAuthenticationCode(params), user:user, pub:pub]
    }

    def save() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
        def pubAppsAuthenticationCodeInstance = new PubAppsAuthenticationCode(params)
		
		def pub = Pub.findById(params.pubId)
		
		pubAppsAuthenticationCodeInstance.pub = pub
		
		while (PubAppsAuthenticationCode.findByCode(pubAppsAuthenticationCodeInstance.code)) {
			pubAppsAuthenticationCodeInstance.code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6).toUpperCase()
		}
		
        if (!pubAppsAuthenticationCodeInstance.save(flush: true)) {
            render(view: "create", model: [pubAppsAuthenticationCodeInstance: pubAppsAuthenticationCodeInstance, user:user, pub:pub])
            return
        }
		
		sendAppsAuthenticationCodeMail(pubAppsAuthenticationCodeInstance)

        flash.message = message(code: 'default.created.message', args: [message(code: 'pubAppsAuthenticationCode.label', default: 'PubAppsAuthenticationCode'), pubAppsAuthenticationCodeInstance.id])
        redirect(uri: "/pubAppsAuthenticationCode/list/"+pub.id, model:[user:user,pub:pub])
    }
	
	/**
	 * Servicio que envía el email con el código de acceso a apps
	 */
	def sendAppsAuthenticationCodeMail(appsAuthenticationCode) {
		
		log.info params
		
		if (appsAuthenticationCode) {
			
			log.info "Enviando email de código de acceso a apps: '${appsAuthenticationCode.code}' de la sala: '${appsAuthenticationCode.pub.name}' a email: '${appsAuthenticationCode.email}'..."
			
			def pub = appsAuthenticationCode.pub
			
			def messageCustomerSubject
			def messageCustomerContent
			def mailFrom = appsAuthenticationCode.pub.name + " <" + appsAuthenticationCode.pub.mailAddress + ">"
			
			//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
			messageCustomerSubject = appsAuthenticationCode.pub.name + " - Código Acceso Apps"
			messageCustomerContent = templateService.renderToString('/mailing/_pubAppsAuthenticationCode', [appsAuthenticationCode:appsAuthenticationCode])
			
			
			mailingService.sendMail(mailFrom, appsAuthenticationCode.email, messageCustomerSubject, messageCustomerContent, pub.mailHost, pub.mailPort, pub.mailUsername, pub.mailPassword)
		}
	
	}

    def show(Long id) {
        def pubAppsAuthenticationCodeInstance = PubAppsAuthenticationCode.get(id)
        if (!pubAppsAuthenticationCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubAppsAuthenticationCode.label', default: 'PubAppsAuthenticationCode'), id])
            redirect(action: "list")
            return
        }

        [pubAppsAuthenticationCodeInstance: pubAppsAuthenticationCodeInstance]
    }

    def edit(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Salas: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = PubAppsAuthenticationCode.findById(id).pub
		
		log.debug "Sala: " + pub?.properties
		
		if(pub.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la sala con ID '${pub.id}'"
			response.sendError(404)
		}
		
        def pubAppsAuthenticationCodeInstance = PubAppsAuthenticationCode.get(id)
        if (!pubAppsAuthenticationCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubAppsAuthenticationCode.label', default: 'PubAppsAuthenticationCode'), id])
            redirect(uri: "/pubAppsAuthenticationCode/list/"+pub.id, model:[user:user, pub:pub])
            return
        }

        [pubAppsAuthenticationCodeInstance: pubAppsAuthenticationCodeInstance, user:user, pub:pub]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Salas: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = PubAppsAuthenticationCode.findById(id).pub
		
		log.debug "Sala: " + pub?.properties
		
		if(pub.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la sala con ID '${pub.id}'"
			response.sendError(404)
		}
		
        def pubAppsAuthenticationCodeInstance = PubAppsAuthenticationCode.get(id)
		
		pubAppsAuthenticationCodeInstance.pub = pub
		
        if (!pubAppsAuthenticationCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubAppsAuthenticationCode.label', default: 'PubAppsAuthenticationCode'), id])
            redirect(uri: "/pubAppsAuthenticationCode/list/"+pub.id, model:[user:user, pub:pub])
            return
        }

        if (version != null) {
            if (pubAppsAuthenticationCodeInstance.version > version) {
                pubAppsAuthenticationCodeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'pubAppsAuthenticationCode.label', default: 'PubAppsAuthenticationCode')] as Object[],
                          "Another user has updated this PubAppsAuthenticationCode while you were editing")
                render(view: "edit", model: [pubAppsAuthenticationCodeInstance: pubAppsAuthenticationCodeInstance, user:user, pub:pub])
                return
            }
        }

        pubAppsAuthenticationCodeInstance.properties = params

        if (!pubAppsAuthenticationCodeInstance.save(flush: true)) {
            render(view: "edit", model: [pubAppsAuthenticationCodeInstance: pubAppsAuthenticationCodeInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pubAppsAuthenticationCode.label', default: 'PubAppsAuthenticationCode'), pubAppsAuthenticationCodeInstance.id])
        redirect(uri: "/pubAppsAuthenticationCode/list/"+pub.id, model:[user:user, pub:pub])
    }

    def delete(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Salas: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = PubAppsAuthenticationCode.findById(params.id).pub
		
		log.debug "Sala: " + pub?.properties
		
		if(pub.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la sala con ID '${pub.id}'"
			response.sendError(404)
		}
		
        def pubAppsAuthenticationCodeInstance = PubAppsAuthenticationCode.get(id)
        if (!pubAppsAuthenticationCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubAppsAuthenticationCode.label', default: 'PubAppsAuthenticationCode'), id])
            redirect(uri: "/pubAppsAuthenticationCode/list/"+pub.id, model:[user:user,pub:pub])
            return
        }

        try {
            pubAppsAuthenticationCodeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'pubAppsAuthenticationCode.label', default: 'PubAppsAuthenticationCode'), id])
            redirect(uri: "/pubAppsAuthenticationCode/list/"+pub.id, model:[user:user,pub:pub])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pubAppsAuthenticationCode.label', default: 'PubAppsAuthenticationCode'), id])
            redirect(action: "edit", model: [pubAppsAuthenticationCodeInstance: pubAppsAuthenticationCodeInstance, user:user, pub:pub])
        }
    }
	
	def deleteCode(long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Salas: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = PubAppsAuthenticationCode.findById(params.id).pub
		
		log.debug "Sala: " + pub?.properties
		
		if(pub.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la sala con ID '${pub.id}'"
			response.sendError(404)
		}
		
		def pubAppsAuthenticationCodeInstance = PubAppsAuthenticationCode.get(id)
		if (!pubAppsAuthenticationCodeInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubAppsAuthenticationCode.label', default: 'PubAppsAuthenticationCode'), id])
			redirect(uri: "/pubAppsAuthenticationCode/list/"+pub.id, model:[user:user,pub:pub])
			return
		}

		try {
			pubAppsAuthenticationCodeInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'pubAppsAuthenticationCode.label', default: 'PubAppsAuthenticationCode'), id])
			redirect(uri: "/pubAppsAuthenticationCode/list/"+pub.id, model:[user:user,pub:pub])
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pubAppsAuthenticationCode.label', default: 'PubAppsAuthenticationCode'), id])
			redirect(action: "edit", model: [pubAppsAuthenticationCodeInstance: pubAppsAuthenticationCodeInstance, user:user, pub:pub])
		}
	}
}
