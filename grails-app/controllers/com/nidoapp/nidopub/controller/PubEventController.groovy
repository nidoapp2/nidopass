package com.nidoapp.nidopub.controller

import java.text.SimpleDateFormat;

import org.apache.commons.lang.WordUtils;
import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured;

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.user.UserRole;
import com.nidoapp.nidopub.domain.Product;
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.PubEvent;

import grails.converters.JSON
import groovy.json.JsonSlurper

class PubEventController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def springSecurityService
	
	def imageRepositoryService

    def index() {
        redirect(action: "create", params: params)
    }

    def list(Integer max, long id_pub) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		params.max = Math.min(max ?: 10, 100)
		
		def pub = Pub.findByUri(params.id_pub)
		
		id_pub = pub.id
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		if(!pub.eventsAvailable) {
			redirect (controller: "pub", action: "pubInquiryDenied", params: [id:pub.uri], model: [user:user, pub:pub])
			return
		}
		
		def pubEventInstanceList = PubEvent.findAllByPub(pub)

		
        [pubEventInstanceList: pubEventInstanceList, pubEventInstanceTotal: PubEvent.count(), user:user, pub:pub]
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
		
        [pubEventInstance: new PubEvent(params), user:user, pub:pub]
    }

    def save() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def pubEventInstance = new PubEvent(params)
		
		def pub = Pub.findByUri(params.id_pub)
		
		pubEventInstance.pub = pub
		def newDateEvent = new Date(params.dateEventDate_month.toString()+"/"+params.dateEventDate_day.toString()+"/"+params.dateEventDate_year.toString()+" "+params.dateEventHour+":"+params.dateEventMinute+":00")
		pubEventInstance.dateEvent = newDateEvent
		
		if (!pub.ticketsSaleInitNumberReset) {
			if (!PubEvent.findByPubAndTicketsInitNumber(pub, pub.ticketsSaleInitNumber)) {
				pubEventInstance.ticketsInitNumber = pub.ticketsSaleInitNumber
			} else {
				def lastEvent = PubEvent.findAllByPub(pub, [sort:"id", order:"asc"]).last()
				pubEventInstance.ticketsInitNumber = lastEvent.ticketsInitNumber + lastEvent.totalTicketsOnlineSale
			}
		} else {
		log.info params
			if (!PubEvent.findByPubAndTicketsInitNumberAndDateEventGreaterThan(pub, pub.ticketsSaleInitNumber, pub.ticketsSaleInitNumberReset-1) && new Date(params.dateEventDate_value.replace("-", ""))>=pub.ticketsSaleInitNumberReset) {
				pubEventInstance.ticketsInitNumber = pub.ticketsSaleInitNumber
			} else if (new Date(params.dateEventDate_value.replace("-", ""))>=pub.ticketsSaleInitNumberReset) {
				def lastEvent = PubEvent.findAllByPubAndDateEventGreaterThan(pub, pub.ticketsSaleInitNumberReset-1, [sort:"id", order:"asc"]).last()
				pubEventInstance.ticketsInitNumber = lastEvent.ticketsInitNumber + lastEvent.totalTicketsOnlineSale
			} else {
				def lastEvent = PubEvent.findAllByPubAndDateEventLessThan(pub, pub.ticketsSaleInitNumberReset, [sort:"id", order:"asc"]).last()
				pubEventInstance.ticketsInitNumber = lastEvent.ticketsInitNumber + lastEvent.totalTicketsOnlineSale
			}
		}
		
		saveNewPhoto(pubEventInstance)
		
		if (!pubEventInstance.save(flush: true)) {
			render(view: "create", model: [pubEventInstance: pubEventInstance, user:user, pub:pub])
			return
		}

		flash.message = message(code: 'default.created.message', args: [message(code: 'pubEvent.label', default: 'PubEvent'), pubEventInstance.id])
		redirect(uri: "/pubEvent/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
    }

    def show(Long id) {
        def pubEventInstance = PubEvent.get(id)
        if (!pubEventInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubEvent.label', default: 'PubEvent'), id])
            redirect(action: "list")
            return
        }

        [pubEventInstance: pubEventInstance]
    }

    def edit(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(PubEvent.findById(id).pub.id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def pubEventInstance = PubEvent.get(id)
        if (!pubEventInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubEvent.label', default: 'PubEvent'), id])
            redirect(uri: "/pubEvent/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
            return
        }

        [pubEventInstance: pubEventInstance, user:user, pub:pub]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(PubEvent.findById(id).pub.id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def pubEventInstance = PubEvent.get(id)
		
		pubEventInstance.pub = Pub.findById(pubEventInstance.pub.id)
		def newDateEvent = new Date(params.dateEventDate_month.toString()+"/"+params.dateEventDate_day.toString()+"/"+params.dateEventDate_year.toString()+" "+params.dateEventHour+":"+params.dateEventMinute+":00")
		pubEventInstance.dateEvent = newDateEvent
        if (!pubEventInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubEvent.label', default: 'PubEvent'), id])
            redirect(uri: "/pubEvent/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
            return
        }
		
		saveNewPhoto(pubEventInstance)

        if (version != null) {
            if (pubEventInstance.version > version) {
                pubEventInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'pubEvent.label', default: 'PubEvent')] as Object[],
                          "Another user has updated this PubEvent while you were editing")
                render(view: "edit", model: [pubEventInstance: pubEventInstance, user:user, pub:pub])
                return
            }
        }

        pubEventInstance.properties = params

        if (!pubEventInstance.save(flush: true)) {
            render(view: "edit", model: [pubEventInstance: pubEventInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'pubEvent.label', default: 'Pubevent'), pubEventInstance.id])
        redirect(uri: "/pubEvent/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
    }

    def delete(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(PubEvent.findById(id).pub.id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def pubEventInstance = PubEvent.get(id)
        if (!pubEventInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pubEvent.label', default: 'PubEvent'), id])
            redirect(uri: "/pubEvent/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
            return
        }

        try {
            pubEventInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'pubEvent.label', default: 'PubEvent'), id])
            redirect(uri: "/pubEvent/list/?id_pub="+pub.uri, model:[user:user,pub:pub])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pubEvent.label', default: 'PubEvent'), id])
            redirect(action: "edit", model: [pubEventInstance: pubEventInstance, user:user, pub:pub])
        }
    }
	
	/**
	 * Guarda la foto indicada en la ventana de nueva foto
	 */
	def saveNewPhoto(PubEvent event) {
			
		def f = request.getFile('photoFile')
		
		if (f && !f.empty) {
			
			def repositoryImageId = imageRepositoryService.saveImage(f.inputStream)
			if(repositoryImageId)
			{
				
				event.photo = repositoryImageId
				
			}
		}
	}
	
	
	
	def listEventsDate(Integer max, long id_pub) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		params.max = Math.min(max ?: 10, 100)
		
		def pub = Pub.findByUri(params.id_pub)
		
		id_pub = pub.id
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
		String strFecha = params.currentDateEvent;
		
		Date fecha = null;

		fecha = formatoDelTexto.parse(strFecha);
		
		def dateYear = fecha.getYear()
		def dateMonth = fecha.getMonth()
		def dateDay = fecha.getDate()
	
		def dateEvent1 = new Date(dateYear, dateMonth, dateDay, 00, 00, 00)
		def dateEvent2 = new Date(dateYear, dateMonth, dateDay, 23, 59, 59)
		
		/*if(!pub.eventsAvailable) {
			redirect (controller: "pub", action: "pubInquiryDenied", params: [id:pub.uri], model: [user:user, pub:pub])
			return
		}*/
		
		def pubEventInstanceList = PubEvent.findAllByDateEventBetween(dateEvent1, dateEvent2)

		
		[pubEventInstanceList: pubEventInstanceList, pubEventInstanceTotal: PubEvent.count(), user:user, pub:pub]
	}
	
	
	def listPubTicketsSoldByDates(Integer max, long id_pub) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		params.max = Math.min(max ?: 10, 100)
		
		def pub = Pub.findByUri(params.id_pub)
		
		id_pub = pub.id
		
		if(UserRole.findByUser(user).role.authority != Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SuperAdmin"
			response.sendError(404)
		}
		
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
		String strFecha1 = params.dateInvoiceFrom;
		String strFecha2 = params.dateInvoiceTo;
		
		Date fecha1 = null;
		Date fecha2 = null;

		fecha1 = formatoDelTexto.parse( strFecha1 + " - 00:00");
		fecha2 = formatoDelTexto.parse( strFecha2 + " - 00:00");
		
		def dateYear1 = fecha1.getYear()
		def dateMonth1 = fecha1.getMonth()
		def dateDay1 = fecha1.getDate()
		
		def dateYear2 = fecha2.getYear()
		def dateMonth2 = fecha2.getMonth()
		def dateDay2 = fecha2.getDate()
	
		def dateEvent1 = new Date(dateYear1, dateMonth1, dateDay1, 00, 00, 00)
		def dateEvent2 = new Date(dateYear2, dateMonth2, dateDay2, 23, 59, 59)
		
		/*if(!pub.eventsAvailable) {
			redirect (controller: "pub", action: "pubInquiryDenied", params: [id:pub.uri], model: [user:user, pub:pub])
			return
		}*/
		
		def pubEventInstanceList = PubEvent.findAllByDateEventBetween(dateEvent1, dateEvent2)

		def totalTicketsSold = 0
		def totalTicketsPrice = 0
		def totalTicketsCommission = 0
		def totalTicketsPriceCommission = 0
		pubEventInstanceList.each {
			totalTicketsSold = totalTicketsSold + it.totalTicketsSold
			totalTicketsPrice = totalTicketsPrice + (it.price * it.totalTicketsSold)
			totalTicketsCommission = totalTicketsCommission + (it.commission * it.totalTicketsSold)
			totalTicketsPriceCommission = totalTicketsPriceCommission + ((it.price + it.commission) * it.totalTicketsSold)
		}
		
		[pubEventInstanceList: pubEventInstanceList, pubEventInstanceTotal: PubEvent.count(), user:user, pub:pub, totalTicketsPrice:totalTicketsPrice, totalTicketsCommission:totalTicketsCommission, totalTicketsPriceCommission:totalTicketsPriceCommission, totalTicketsSold:totalTicketsSold]
	}
	
	
	def listCityEvents = {
		def response = []
		
		//def apiKeyString = params.key
			
		def events = PubEvent.findAll(sort:"dateEvent")

		
		//response.events = []
		
		events.each{
			
			def event = [:]
			
			event.id = it.id
			
			if (it.musicSubType) {
				event.title = it.title + "\nSala: " + it.pub + "\nArtista: " + it.artist +
						"\nMúsica: " + it.musicType.name + " " + it.musicSubType +
						"\nHora: " + WordUtils.capitalize(g.formatDate(date:it.dateEvent, format: 'HH:mm')) +
						"\nCiudad: " + it.pub.city
			} else {
				event.title = it.title + "\nSala: " + it.pub + "\nArtista: " + it.artist +
						"\nMúsica: " + it.musicType.name +
						"\nHora: " + WordUtils.capitalize(g.formatDate(date:it.dateEvent, format: 'HH:mm')) +
						"\nCiudad: " + it.pub.city	
			}
			
			//event.dateFrom = WordUtils.capitalize(g.formatDate(date:it.dateFrom, format: 'dd MMMM'))
			event.start = WordUtils.capitalize(g.formatDate(date:it.dateEvent, format: 'yyyy-MM-dd'))
			
			
			response << event
		
		}
		
		render getJsonOrJsonp(response, params)
	}
	
	/**
	 * Metodo privado que pemite tanto respuesta JSON como JSONP
	 * @param response
	 * @param params
	 * @return
	 */
	def private getJsonOrJsonp(response, params)
	{
		def jsonOrJsonp
		
		if(params.callback) //JSONP
		{
			jsonOrJsonp = "${params.callback}(${response as JSON})"
		}
		else //JSON
		{
			jsonOrJsonp = response as JSON
		}
		
		jsonOrJsonp
	}
}
