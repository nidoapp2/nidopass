package com.nidoapp.nidopub.controller.i18n

import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured;

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.i18n.I18nItem;
import com.nidoapp.nidopub.domain.i18n.I18nTranslation;

class I18nTranslationController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def springSecurityService
	
    def index() {
        redirect(action: "list", params: params)
    }

    /*def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [i18nTranslationInstanceList: I18nTranslation.list(params), i18nTranslationInstanceTotal: I18nTranslation.count()]
    }*/
	
	def list(Integer max, long id_i18nItem) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		params.max = Math.min(max ?: 10, 100)
		
		def i18nItem = I18nItem.findById(params.id_i18nItem)
		
		id_i18nItem = i18nItem.id
		
		def pub = Pub.findById(i18nItem.pub_id)
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		def i18nTranslationInstanceList = I18nTranslation.findAllByItem(i18nItem)
		
		
		[i18nTranslationInstanceList: i18nTranslationInstanceList, user:user, pub:pub]
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
		
        [i18nTranslationInstance: new I18nTranslation(params), user:user, pub:pub, id_pub:pub.id]
    }

    def save() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		log.info "ID_PUB: " + params.id_pub
		
		def pub = Pub.findById(params.id_pub)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def i18nTranslationInstance = new I18nTranslation(params)
        if (!i18nTranslationInstance.save(flush: true)) {
            render(view: "create", model: [i18nTranslationInstance: i18nTranslationInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'i18nTranslation.label', default: 'I18nTranslation'), i18nTranslationInstance.id])
        redirect(uri: "/textAndTranslations/index/"+pub.uri, model:[user:user,pub:pub])
    }

    def show(Long id) {
        def i18nTranslationInstance = I18nTranslation.get(id)
        if (!i18nTranslationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'i18nTranslation.label', default: 'I18nTranslation'), id])
            redirect(action: "list")
            return
        }

        [i18nTranslationInstance: i18nTranslationInstance]
    }

    def edit(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		def i18nItem = I18nTranslation.findById(id).item
		
		def pub = Pub.findById(i18nItem.pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def i18nTranslationInstance = I18nTranslation.get(id)
        if (!i18nTranslationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'i18nTranslation.label', default: 'I18nTranslation'), id])
            redirect(uri: "/textAndTranslations/index/"+pub.uri, model:[user:user,pub:pub])
            return
        }

        [i18nTranslationInstance: i18nTranslationInstance, user:user, pub:pub]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		def i18nItem = I18nTranslation.findById(id).item
		
		def pub = Pub.findById(i18nItem.pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def i18nTranslationInstance = I18nTranslation.get(id)
        if (!i18nTranslationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'i18nTranslation.label', default: 'I18nTranslation'), id])
            redirect(uri: "/textAndTranslations/index/"+pub.uri, model:[user:user,pub:pub])
            return
        }

        if (version != null) {
            if (i18nTranslationInstance.version > version) {
                i18nTranslationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'i18nTranslation.label', default: 'I18nTranslation')] as Object[],
                          "Another user has updated this I18nTranslation while you were editing")
                render(view: "edit", model: [i18nTranslationInstance: i18nTranslationInstance, user:user, pub:pub])
                return
            }
        }

        i18nTranslationInstance.properties = params

        if (!i18nTranslationInstance.save(flush: true)) {
            render(view: "edit", model: [i18nTranslationInstance: i18nTranslationInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'i18nTranslation.label', default: 'I18nTranslation'), i18nTranslationInstance.id])
        redirect(uri: "/textAndTranslations/index/"+pub.uri, model:[user:user,pub:pub])
    }

    def delete(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		def i18nItem = I18nTranslation.findById(id).item
		
		def pub = Pub.findById(i18nItem.pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def i18nTranslationInstance = I18nTranslation.get(id)
        if (!i18nTranslationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'i18nTranslation.label', default: 'I18nTranslation'), id])
            redirect(uri: "/textAndTranslations/index/"+pub.uri, model:[user:user,pub:pub])
            return
        }

        try {
            i18nTranslationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'i18nTranslation.label', default: 'I18nTranslation'), id])
            redirect(uri: "/textAndTranslations/index/"+pub.uri, model:[user:user,pub:pub])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'i18nTranslation.label', default: 'I18nTranslation'), id])
            redirect(action: "edit", model: [i18nTranslationInstance: i18nTranslationInstance, user:user, pub:pub])
        }
    }
}
