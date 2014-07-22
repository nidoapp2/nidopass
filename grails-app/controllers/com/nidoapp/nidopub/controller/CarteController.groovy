package com.nidoapp.nidopub.controller

import org.springframework.dao.DataIntegrityViolationException;

import grails.plugins.springsecurity.Secured;

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;

import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.Carte;

class CarteController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def springSecurityService

    def index() {
        redirect(action: "create", params: params)
    }

    /*def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [carteInstanceList: Carte.list(params), carteInstanceTotal: Carte.count()]
    }*/
	
	def list(Integer max, long id_pub) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		params.max = Math.min(max ?: 10, 100)
		
		def pub = Pub.findById(params.id_pub)
		
		id_pub = pub.id
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		def carteInstanceList = Carte.findAllByPub_id(id_pub)
		
		
		[carteInstanceList: carteInstanceList, carteInstanceTotal: Carte.count()]
	}

    def create() {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(params.id_pub)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        [carteInstance: new Carte(params), user:user, pub:pub, id_pub:pub.id]
    }

    def save() {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
        def carteInstance = new Carte(params)
		
		def pub = Pub.findById(params.id_pub)
		
		carteInstance.pub_id = pub.id
		
		carteInstance.pub = pub
		
        if (!carteInstance.save(flush: true)) {
            render(view: "create", model: [carteInstance: carteInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'carte.label', default: 'Carte'), carteInstance.id])
        
		def uri = "/cartesAndProducts/index/" + pub.uri
		
		redirect(uri: uri, model:[user:user,pub:pub])
		
    }

    def show(Long id) {
        def carteInstance = Carte.get(id)
        if (!carteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'carte.label', default: 'Carte'), id])
            redirect(action: "list")
            return
        }

        [carteInstance: carteInstance]
    }

    def edit(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(Carte.findById(id).pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def carteInstance = Carte.get(id)
        if (!carteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'carte.label', default: 'Carte'), id])
            redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
            return
        }

        [carteInstance: carteInstance, user: user, pub: pub]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(Carte.findById(id).pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		
        def carteInstance = Carte.get(id)
		
		carteInstance.pub = Pub.findById(carteInstance.pub_id)
		
		
        if (!carteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'carte.label', default: 'Carte'), id])
            redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
            return
        }

        if (version != null) {
            if (carteInstance.version > version) {
                carteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'carte.label', default: 'Carte')] as Object[],
                          "Another user has updated this Carte while you were editing")
                render(view: "edit", model: [carteInstance: carteInstance, user:user, pub:pub])
                return
            }
        }

        carteInstance.properties = params

        if (!carteInstance.save(flush: true)) {
            render(view: "edit", model: [carteInstance: carteInstance, user:user, pub:pub])
            return
        }
		
		def uri = "/cartesAndProducts/index/" + pub.uri

        flash.message = message(code: 'default.updated.message', args: [message(code: 'carte.label', default: 'Carte'), carteInstance.id])
        redirect(uri: uri, model:[user:user,pub:pub])
    }

    def delete(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(Carte.findById(id).pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		
        def carteInstance = Carte.get(id)
		carteInstance.pub = Pub.findById(carteInstance.pub_id)
		
        if (!carteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'carte.label', default: 'Carte'), id])
            redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
            return
        }

        try {
            carteInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'carte.label', default: 'Carte'), id])
			def uri = "/cartesAndProducts/index/" + pub.uri
			redirect(uri: uri, model:[user:user,pub:pub])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'carte.label', default: 'Carte'), id])
            redirect(action: "edit", model: [carteInstance: carteInstance, user:user, pub:pub])
        }
    }
}
