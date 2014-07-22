package com.nidoapp.nidopub.service

import java.util.Date;
import java.text.SimpleDateFormat;

//import com.nidoapp.nidopub.domain.Customer;


import com.nidoapp.nidopub.domain.EventsListSend
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.TicketsSold;
import com.nidoapp.nidopub.domain.api.ApiKey
//import com.nidoapp.nidopub.domain.geo.Country
//import com.nidoapp.nidopub.dto.ReservationRequest



import grails.util.Environment

import org.apache.commons.lang.StringUtils


/**
 * Servicios que realiza operaciones de envío de emails de eventos de pubs
 * @author Developer
 *
 */
class ServiceExpiredService {
	
	def mailingService
	def messageSource
	def templateService
	
	/**
	 * Metodo privado que envia un Mail de notificacion de expiración de servicio al pub y
	 * deshabilita el servicio para que no lo puedan utilizar
	 * @param reservation datos de reserva de mesa
	 * @return si se ha enviado correctamente
	 */
	def private disableServices()
	{
		def sent = false
		
		def maxDate
		def expiredMinutes = 10
		
		//if (hour==11) {
		
		use(groovy.time.TimeCategory) {
			
			maxDate = new Date()// - expiredMinutes.minutes
			
		}
		
		/**
		translationsAvailable = false
		cartesAvailable = false
		eventsAvailable = false
		inquiryAvailable = false
		sellTicketsOnlineAvailable = false 
		 * */
		
		def expiredTranslationsService = Pub.createCriteria().list(){
			
			
			eq("translationsAvailable", true)
			
			
			lt("translationsAvailableTo", maxDate)
		}
		
		expiredTranslationsService.each{
			
				it.translationsAvailable = false
				
				it.save(flush:true, failOnError:true)
				
				log.info "Enviando email de servicio de traducciones deshabilitado a: '${it?.email}'..."
			
				def pub = it
				
				def messagePubSubject
				def messagePubContent
				def mailFrom = null
				
				//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
				messagePubSubject = "Servicio de traducciones deshabilitado a sala " + it.name
				messagePubContent = templateService.renderToString('/mailing/_pubServiceExpired', [pub:it, service:'Traducciones'])
				
				
				mailingService.sendMail(mailFrom, it.email, messagePubSubject, messagePubContent, null, null, null, null)
				
		}
		
		def expiredCartesService = Pub.createCriteria().list(){
			
			
			eq("cartesAvailable", true)
			
			
			lt("cartesAvailableTo", maxDate)
		}
		
		expiredCartesService.each{
			
				it.cartesAvailable = false
				
				it.save(flush:true, failOnError:true)
				
				log.info "Enviando email de servicio de cartas deshabilitado a: '${it?.email}'..."
			
				def pub = it
				
				def messagePubSubject
				def messagePubContent
				def mailFrom = null
				
				//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
				messagePubSubject = "Servicio de cartas deshabilitado a sala " + it.name
				messagePubContent = templateService.renderToString('/mailing/_pubServiceExpired', [pub:it, service:'Cartas'])
				
				
				mailingService.sendMail(mailFrom, it.email, messagePubSubject, messagePubContent, null, null, null, null)
				
		}
		
		def expiredEventsService = Pub.createCriteria().list(){
			
			
			eq("eventsAvailable", true)
			
			
			lt("eventsAvailableTo", maxDate)
		}
		
		expiredEventsService.each{
			
				it.eventsAvailable = false
				
				it.save(flush:true, failOnError:true)
				
				log.info "Enviando email de servicio de eventos deshabilitado a: '${it?.email}'..."
			
				def pub = it
				
				def messagePubSubject
				def messagePubContent
				def mailFrom = null
				
				//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
				messagePubSubject = "Servicio de eventos deshabilitado a sala " + it.name
				messagePubContent = templateService.renderToString('/mailing/_pubServiceExpired', [pub:it, service:'Eventos'])
				
				
				mailingService.sendMail(mailFrom, it.email, messagePubSubject, messagePubContent, null, null, null, null)
				
		}
		
		def expiredInquiryService = Pub.createCriteria().list(){
			
			
			eq("inquiryAvailable", true)
			
			
			lt("inquiryAvailableTo", maxDate)
		}
		
		expiredInquiryService.each{
			
				it.inquiryAvailable = false
				
				it.save(flush:true, failOnError:true)
				
				log.info "Enviando email de servicio de encuesta deshabilitado a: '${it?.email}'..."
			
				def pub = it
				
				def messagePubSubject
				def messagePubContent
				def mailFrom = null
				
				//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
				messagePubSubject = "Servicio de encuesta deshabilitado a sala " + it.name
				messagePubContent = templateService.renderToString('/mailing/_pubServiceExpired', [pub:it, service:'Encuesta'])
				
				
				mailingService.sendMail(mailFrom, it.email, messagePubSubject, messagePubContent, null, null, null, null)
				
		}
		
		def expiredSellTicketsOnlineService = Pub.createCriteria().list(){
			
			
			eq("sellTicketsOnlineAvailable", true)
			
			
			lt("sellTicketsOnlineAvailableTo", maxDate)
		}
		
		expiredSellTicketsOnlineService.each{
			
				it.sellTicketsOnlineAvailable = false
				
				it.save(flush:true, failOnError:true)
				
				log.info "Enviando email de servicio de venta de entradas online deshabilitado a: '${it?.email}'..."
			
				def pub = it
				
				def messagePubSubject
				def messagePubContent
				def mailFrom = null
				
				//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
				messagePubSubject = "Servicio de venta de entradas deshabilitado a sala " + it.name
				messagePubContent = templateService.renderToString('/mailing/_pubServiceExpired', [pub:it, service:'Venta de entradas online'])
				
				
				mailingService.sendMail(mailFrom, it.email, messagePubSubject, messagePubContent, null, null, null, null)
				
		}
		
	}
	
	
}
