package com.nidoapp.nidopub.controller.i18n

import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured;

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.i18n.I18nItem;

class I18nItemController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def springSecurityService

    def index() {
        redirect(action: "create", params: params)
    }

    /*def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [i18nItemInstanceList: I18nItem.list(params), i18nItemInstanceTotal: I18nItem.count()]
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
		
		def i18nItemInstanceList = I18nItem.findAllByPub_id(id_pub)
		
		
		[i18nItemInstanceList: i18nItemInstanceList, i18nItemInstanceTotal: I18nItem.count()]
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
		
        [i18nItemInstance: new I18nItem(params), user:user, pub:pub]
    }

    def save() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
        def i18nItemInstance = new I18nItem(params)
		
		def pub = Pub.findById(params.id_pub)
		
		i18nItemInstance.pub_id = pub.id
		
        if (!i18nItemInstance.save(flush: true)) {
            render(view: "create", model: [i18nItemInstance: i18nItemInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'i18nItem.label', default: 'I18nItem'), i18nItemInstance.id])
        redirect(uri: "/textAndTranslations/index/"+pub.uri, model:[user:user,pub:pub])
    }
	
	/*def createMenuDescription() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
				
		log.debug "Restaurantes: " + user.restaurants
		
		//def restaurant = Restaurant.findByUri(params.id)
		def restaurant = Pub.findById(params.id_rest)
		
		log.debug "Restaurant: " + restaurant?.properties
		
		if(restaurant?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del restaurante con ID '${restaurant?.id}'"
			response.sendError(404)
		}
		
		[i18nItemInstance: new I18nItem(params), user:user, restaurant:restaurant]
	}

	def saveMenuDescription() {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def i18nItemInstance = new I18nItem(params)
		
		def restaurant = Pub.findById(params.id_rest)
		
		i18nItemInstance.restaurant_id = restaurant.id
		
		if (!i18nItemInstance.save(flush: true)) {
			render(view: "create", model: [i18nItemInstance: i18nItemInstance, user:user, restaurant:restaurant])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'i18nItem.label', default: 'I18nItem'), i18nItemInstance.id])
		def menuAction=params.menuAction
		
		if(menuAction=="create") {
			redirect(uri: "/menu/create?id_rest="+params.id_rest, model:[user:user,restaurant:restaurant, id_rest:restaurant.id])
		} else if(menuAction=="edit") {
			redirect(uri: "/menu/edit/"+params.id_menu, model:[user:user,restaurant:restaurant, id_rest:restaurant.id])
		}
	}*/

    def show(Long id) {
        def i18nItemInstance = I18nItem.get(id)
        if (!i18nItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'i18nItem.label', default: 'I18nItem'), id])
            redirect(action: "list")
            return
        }

        [i18nItemInstance: i18nItemInstance]
    }

    def edit(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(I18nItem.findById(id).pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def i18nItemInstance = I18nItem.get(id)
        if (!i18nItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'i18nItem.label', default: 'I18nItem'), id])
            redirect(uri: "/textAndTranslations/index/"+pub.uri, model:[user:user,pub:pub])
            return
        }

        [i18nItemInstance: i18nItemInstance, user:user, pub:pub]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(I18nItem.findById(id).pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def i18nItemInstance = I18nItem.get(id)
        if (!i18nItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'i18nItem.label', default: 'I18nItem'), id])
            redirect(uri: "/textAndTranslations/index/"+pub.uri, model:[user:user,pub:pub])
            return
        }

        if (version != null) {
            if (i18nItemInstance.version > version) {
                i18nItemInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'i18nItem.label', default: 'I18nItem')] as Object[],
                          "Another user has updated this I18nItem while you were editing")
                render(view: "edit", model: [i18nItemInstance: i18nItemInstance, user:user, pub:pub])
                return
            }
        }

        i18nItemInstance.properties = params

        if (!i18nItemInstance.save(flush: true)) {
            render(view: "edit", model: [i18nItemInstance: i18nItemInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'i18nItem.label', default: 'I18nItem'), i18nItemInstance.id])
        redirect(uri: "/textAndTranslations/index/"+pub.uri, model:[user:user,pub:pub])
    }

    def delete(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(I18nItem.findById(id).pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def i18nItemInstance = I18nItem.get(id)
		
		//def menus = Menu.findAllByDescription(i18nItemInstance)
		
		/*menus.eachWithIndex { item, index ->
			item.description = null
		}*/
		
        if (!i18nItemInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'i18nItem.label', default: 'I18nItem'), id])
            redirect(uri: "/textAndTranslations/index/"+pub.uri, model:[user:user,pub:pub])
            return
        }

        try {
            i18nItemInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'i18nItem.label', default: 'I18nItem'), id])
            redirect(uri: "/textAndTranslations/index/"+pub.uri, model:[user:user,pub:pub])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'i18nItem.label', default: 'I18nItem'), id])
            redirect(action: "edit", model: [i18nItemInstance: i18nItemInstance, user:user, pub:pub])
        }
    }
}
