package com.nidoapp.nidopub.controller

import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured;

import com.nidoapp.nidopub.domain.Carte;
import com.nidoapp.nidopub.domain.CarteSection;
import com.nidoapp.nidopub.domain.Pub;
import com.nidoapp.nidopub.domain.user.User;

class CarteSectionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def springSecurityService

    def index() {
        redirect(action: "create", params: params)
    }

    /*def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [carteSectionInstanceList: CarteSection.list(params), carteSectionInstanceTotal: CarteSection.count()]
    }*/
	
	def list(Integer max, long id_carte) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		params.max = Math.min(max ?: 10, 100)
		
		def carte = Carte.findById(params.id_carte)
		
		id_carte = carte.id
		
		def pub = Pub.findById(carte.pub_id)
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		def carteSectionInstanceList = CarteSection.findAllByCarte(carte, [sort: "orderNumber", order: "asc"])
		
		
		[carteSectionInstanceList: carteSectionInstanceList, user:user, pub:pub]
	}

    def create() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findById(params.id_pub)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        [carteSectionInstance: new CarteSection(params), user:user, pub:pub, id_pub:pub.id]
    }

    def save() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		
		def pub = Pub.findById(params.id_pub)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def carteSectionInstance = new CarteSection(params)
        if (!carteSectionInstance.save(flush: true)) {
            render(view: "create", model: [carteSectionInstance: carteSectionInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'carteSection.label', default: 'CarteSection'), carteSectionInstance.id])
        redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
    }

    def show(Long id) {
        def carteSectionInstance = CarteSection.get(id)
        if (!carteSectionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'carteSection.label', default: 'CarteSection'), id])
            redirect(action: "list")
            return
        }

        [carteSectionInstance: carteSectionInstance]
    }

    def edit(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		def carte = CarteSection.findById(id).carte
		
		def pub = Pub.findById(carte.pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def carteSectionInstance = CarteSection.get(id)
        if (!carteSectionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'carteSection.label', default: 'CarteSection'), id])
            redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
            return
        }

        [carteSectionInstance: carteSectionInstance, user:user, pub:pub]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		def carte = CarteSection.findById(id).carte
		
		def pub = Pub.findById(carte.pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def carteSectionInstance = CarteSection.get(id)
        if (!carteSectionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'carteSection.label', default: 'CarteSection'), id])
            redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
            return
        }

        if (version != null) {
            if (carteSectionInstance.version > version) {
                carteSectionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'carteSection.label', default: 'CarteSection')] as Object[],
                          "Another user has updated this CarteSection while you were editing")
                render(view: "edit", model: [carteSectionInstance: carteSectionInstance, user:user, pub:pub])
                return
            }
        }

        carteSectionInstance.properties = params

        if (!carteSectionInstance.save(flush: true)) {
            render(view: "edit", model: [carteSectionInstance: carteSectionInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'carteSection.label', default: 'CarteSection'), carteSectionInstance.id])
        redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
    }

    def delete(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		def carte = CarteSection.findById(id).carte
		
		def pub = Pub.findById(carte.pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def carteSectionInstance = CarteSection.get(id)
        if (!carteSectionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'carteSection.label', default: 'CarteSection'), id])
            redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
            return
        }

        try {
            carteSectionInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'carteSection.label', default: 'CarteSection'), id])
            def uri = "/cartesAndProducts/index/" + pub.uri
			redirect(uri: uri, model:[user:user,pub:pub])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'carteSection.label', default: 'CarteSection'), id])
            redirect(action: "edit", model: [carteSectionInstance: carteSectionInstance, user:user, pub:pub])
        }
    }
}
