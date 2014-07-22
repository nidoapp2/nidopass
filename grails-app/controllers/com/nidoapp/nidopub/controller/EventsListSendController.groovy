package com.nidoapp.nidopub.controller

import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured;

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.EventsListSend;

class EventsListSendController {

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
		
		def eventsListSendInstanceList = EventsListSend.findAllByPub(pub)
		
        [eventsListSendInstanceList: eventsListSendInstanceList, eventsListSendInstanceTotal: EventsListSend.count(), user:user, pub:pub]
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
		
        [eventsListSendInstance: new EventsListSend(params), user:user, pub:pub]
    }

    def save() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
        def eventsListSendInstance = new EventsListSend(params)
		
		def pub = Pub.findByUri(params.id_pub)
		
		eventsListSendInstance.pub = pub
		
        if (!eventsListSendInstance.save(flush: true)) {
            render(view: "create", model: [eventsListSendInstance: eventsListSendInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'eventsListSend.label', default: 'EventsListSend'), eventsListSendInstance.id])
        //redirect(action: "show", id: eventsListSendInstance.id)
		redirect(uri: "/eventsListSend/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
    }

    def show(Long id) {
        def eventsListSendInstance = EventsListSend.get(id)
        if (!eventsListSendInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eventsListSend.label', default: 'EventsListSend'), id])
            redirect(action: "list")
            return
        }

        [eventsListSendInstance: eventsListSendInstance]
    }

    def edit(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(EventsListSend.findById(id).pub.id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def eventsListSendInstance = EventsListSend.get(id)
        if (!eventsListSendInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eventsListSend.label', default: 'EventsListSend'), id])
            redirect(uri: "/eventsListSend/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
            return
        }

        [eventsListSendInstance: eventsListSendInstance, user:user, pub:pub]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(EventsListSend.findById(id).pub.id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def eventsListSendInstance = EventsListSend.get(id)
		
		eventsListSendInstance.pub = Pub.findById(eventsListSendInstance.pub.id)
		
        if (!eventsListSendInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eventsListSend.label', default: 'EventsListSend'), id])
            redirect(uri: "/eventsListSend/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
            return
        }

        if (version != null) {
            if (eventsListSendInstance.version > version) {
                eventsListSendInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'eventsListSend.label', default: 'EventsListSend')] as Object[],
                          "Another user has updated this EventsListSend while you were editing")
                render(view: "edit", model: [eventsListSendInstance: eventsListSendInstance, user:user, pub:pub])
                return
            }
        }

        eventsListSendInstance.properties = params

        if (!eventsListSendInstance.save(flush: true)) {
            render(view: "edit", model: [eventsListSendInstance: eventsListSendInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'eventsListSend.label', default: 'EventsListSend'), eventsListSendInstance.id])
        redirect(uri: "/eventsListSend/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
    }

    def delete(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(EventsListSend.findById(id).pub.id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def eventsListSendInstance = EventsListSend.get(id)
        if (!eventsListSendInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'eventsListSend.label', default: 'EventsListSend'), id])
            redirect(uri: "/eventsListSend/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
            return
        }

        try {
            eventsListSendInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'eventsListSend.label', default: 'EventsListSend'), id])
            redirect(uri: "/eventsListSend/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'eventsListSend.label', default: 'EventsListSend'), id])
            redirect(action: "edit", model: [eventsListSendInstance: eventsListSendInstance, user:user, pub:pub])
        }
    }
}
