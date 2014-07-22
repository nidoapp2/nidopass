package com.nidoapp.nidopub.controller

import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured;

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.user.UserRole;
import com.nidoapp.nidopub.domain.PubEvent;
import com.nidoapp.nidopub.domain.PubEventPromotionalCode;

class PubEventPromotionalCodeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def springSecurityService
	
	def mailingService
	def messageSource
	def templateService

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max, long eventId) {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
        params.max = Math.min(max ?: 10, 100)
		
		def event = PubEvent.findById(params.id)
		
		eventId = event.id
		
		if(event.pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${event?.pub.id}'"
			response.sendError(404)
		}
		
		if(!event.pub.eventsAvailable) {
			redirect (controller: "pub", action: "pubInquiryDenied", params: [id:event.pub.uri], model: [user:user, pub:event.pub])
			return
		}
		
		def pubEventPromotionalCodeInstanceList = PubEventPromotionalCode.findAllByPubEvent(event)
		
        [pubEventPromotionalCodeInstanceList: pubEventPromotionalCodeInstanceList, pubEventPromotionalCodeInstanceTotal: PubEventPromotionalCode.count(), user:user, pub:event.pub, event:event]
    }

    def create() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def event = PubEvent.findById(params.eventId)
		
		log.debug "Evento: " + event?.properties
		
		if(event.pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${event.pub?.id}'"
			response.sendError(404)
		}
		
        [pubEventPromotionalCodeInstance: new PubEventPromotionalCode(params), user:user, pub:event.pub, event:event]
    }

    def save() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
        def pubEventPromotionalCodeInstance = new PubEventPromotionalCode(params)
		
		def event = PubEvent.findById(params.eventId)
		
		pubEventPromotionalCodeInstance.pubEvent = event
		
        if (!pubEventPromotionalCodeInstance.save(flush: true)) {
            render(view: "create", model: [pubEventPromotionalCodeInstance: pubEventPromotionalCodeInstance, user:user, pub:event.pub, event:event])
            return
        }
		
		sendPromotionalCodeMail(pubEventPromotionalCodeInstance)

        flash.message = message(code: 'default.created.message', args: [message(code: 'pubEventPromotionalCode.label', default: 'PubEventPromotionalCode'), pubEventPromotionalCodeInstance.id])
        redirect(uri: "/pubEventPromotionalCode/list/"+event.id, model:[user:user,pub:event.pub, event:event])
    }
	
	/**
	 * Servicio que envía el email con el código promocional
	 */
	def sendPromotionalCodeMail(promotionalCode) {
		
		log.info params
		
		if (promotionalCode) {
			
			log.info "Enviando email de código promocional para compra de entradas para el evento: '${promotionalCode.pubEvent.title}' de la sala: '${promotionalCode.pubEvent.pub.name}' a email del cliente: '${promotionalCode.email}'..."
			
			def pub = promotionalCode.pubEvent.pub
			
			def messageCustomerSubject
			def messageCustomerContent
			def mailFrom = promotionalCode.pubEvent.pub.name + " <" + promotionalCode.pubEvent.pub.mailAddress + ">"
			
			//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
			messageCustomerSubject = promotionalCode.pubEvent.pub.name + " - Código promocional evento " + promotionalCode.pubEvent.title
			messageCustomerContent = templateService.renderToString('/mailing/_customerPromotionalCode', [promotionalCode:promotionalCode])
			
			
			mailingService.sendMail(mailFrom, promotionalCode.email, messageCustomerSubject, messageCustomerContent, pub.mailHost, pub.mailPort, pub.mailUsername, pub.mailPassword)
		}
	
	}

    def show(Long id) {
        def pubEventPromotionalCodeInstance = PubEventPromotionalCode.get(id)
        if (!pubEventPromotionalCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubEventPromotionalCode.label', default: 'PubEventPromotionalCode'), id])
            redirect(action: "list")
            return
        }

        [pubEventPromotionalCodeInstance: pubEventPromotionalCodeInstance]
    }

    def edit(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def event = PubEvent.findById(PubEventPromotionalCode.findById(id).pubEvent.id)
		
		log.debug "Evento: " + event?.properties
		
		if(event.pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${event.pub?.id}'"
			response.sendError(404)
		}
		
        def pubEventPromotionalCodeInstance = PubEventPromotionalCode.get(id)
        if (!pubEventPromotionalCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubEventPromotionalCode.label', default: 'PubEventPromotionalCode'), id])
            redirect(uri: "/pubEventPromotionalCode/list/"+event.id, model:[user:user, pub:event.pub, event:event])
            return
        }

        [pubEventPromotionalCodeInstance: pubEventPromotionalCodeInstance, user:user, pub:event.pub, event:event]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def event = PubEvent.findById(PubEventPromotionalCode.findById(id).pubEvent.id)
		
		log.debug "Evento: " + event?.properties
		
		if(event.pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${event.pub?.id}'"
			response.sendError(404)
		}
		
		
        def pubEventPromotionalCodeInstance = PubEventPromotionalCode.get(id)
		
		pubEventPromotionalCodeInstance.pubEvent = event
		
        if (!pubEventPromotionalCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubEventPromotionalCode.label', default: 'PubEventPromotionalCode'), id])
            redirect(uri: "/pubEventPromotionalCode/list/"+event.id, model:[user:user, pub:event.pub, event:event])
            return
        }

        if (version != null) {
            if (pubEventPromotionalCodeInstance.version > version) {
                pubEventPromotionalCodeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'pubEventPromotionalCode.label', default: 'PubEventPromotionalCode')] as Object[],
                          "Another user has updated this PubEventPromotionalCode while you were editing")
                render(view: "edit", model: [pubEventPromotionalCodeInstance: pubEventPromotionalCodeInstance, user:user, pub:event.pub, event:event])
                return
            }
        }

        pubEventPromotionalCodeInstance.properties = params

        if (!pubEventPromotionalCodeInstance.save(flush: true)) {
            render(view: "edit", model: [pubEventPromotionalCodeInstance: pubEventPromotionalCodeInstance, user:user, pub:event.pub, event:event])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pubEventPromotionalCode.label', default: 'PubEventPromotionalCode'), pubEventPromotionalCodeInstance.id])
        redirect(uri: "/pubEventPromotionalCode/list/"+event.id, model:[user:user, pub:event.pub, event:event])
    }

    def delete(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def event = PubEvent.findById(PubEventPromotionalCode.findById(id).pubEvent.id)
		
		log.debug "Evento: " + event?.properties
		
		if(event.pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${event.pub?.id}'"
			response.sendError(404)
		}
		
        def pubEventPromotionalCodeInstance = PubEventPromotionalCode.get(id)
        if (!pubEventPromotionalCodeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubEventPromotionalCode.label', default: 'PubEventPromotionalCode'), id])
            redirect(uri: "/pubEventPromotionalCode/list/"+event.id, model:[user:user,pub:event.pub, event:event])
            return
        }

        try {
            pubEventPromotionalCodeInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'pubEventPromotionalCode.label', default: 'PubEventPromotionalCode'), id])
            redirect(uri: "/pubEventPromotionalCode/list/"+event.id, model:[user:user,pub:event.pub, event:event])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pubEventPromotionalCode.label', default: 'PubEventPromotionalCode'), id])
            redirect(action: "edit", model: [pubEventPromotionalCodeInstance: pubEventPromotionalCodeInstance, user:user, pub:event.pub, event:event])
        }
    }
}
