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
class ServicePreExpireService {
	
	def mailingService
	def messageSource
	def templateService
	
	/**
	 * Metodo privado que envia un Mail de notificacion al pub indicandole que en
	 * 15 días caduca un servicio contratado
	 * @param reservation datos de reserva de mesa
	 * @return si se ha enviado correctamente
	 */
	def private sendServicePreExpireEMails()
	{
		def sent = false
		
		def maxDate
		def expiredMinutes = 10
		
		//if (hour==11) {
		
		use(groovy.time.TimeCategory) {
			
			maxDate = new Date() + 15
			maxDate.setMinutes(0)
			maxDate.setSeconds(0)
			
		}
		
		def preExpireTranslationsService = Pub.createCriteria().list(){
			
			
			eq("translationsAvailable", true)
			
			
			eq("translationsAvailableTo", maxDate)
		}
		
		preExpireTranslationsService.each{
				
				log.info "Enviando email de servicio de traducciones caduca en 15 días a: '${it?.email}'..."
			
				def pub = it
				
				def messagePubSubject
				def messagePubContent
				def mailFrom = null
				
				//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
				messagePubSubject = "Servicio de traducciones caduca en 15 días para la sala " + it.name
				messagePubContent = templateService.renderToString('/mailing/_pubServicePreExpire', [pub:it, service:'Traducciones'])
				
				
				mailingService.sendMail(mailFrom, it.email, messagePubSubject, messagePubContent, null, null, null, null)
				
		}
		
		def preExpireCartesService = Pub.createCriteria().list(){
			
			
			eq("cartesAvailable", true)
			
			
			eq("cartesAvailableTo", maxDate)
		}
		
		preExpireCartesService.each{
				
				log.info "Enviando email de servicio de cartas caduca en 15 días a: '${it?.email}'..."
			
				def pub = it
				
				def messagePubSubject
				def messagePubContent
				def mailFrom = null
				
				//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
				messagePubSubject = "Servicio de cartas caduca en 15 días para la sala " + it.name
				messagePubContent = templateService.renderToString('/mailing/_pubServicePreExpire', [pub:it, service:'Cartas'])
				
				
				mailingService.sendMail(mailFrom, it.email, messagePubSubject, messagePubContent, null, null, null, null)
				
		}
		
		def preExpireEventsService = Pub.createCriteria().list(){
			
			
			eq("eventsAvailable", true)
			
			
			eq("eventsAvailableTo", maxDate)
		}
		
		preExpireEventsService.each{
				
				log.info "Enviando email de servicio de eventos caduca en 15 días a: '${it?.email}'..."
			
				def pub = it
				
				def messagePubSubject
				def messagePubContent
				def mailFrom = null
				
				//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
				messagePubSubject = "Servicio de eventos caduca en 15 días para la sala " + it.name
				messagePubContent = templateService.renderToString('/mailing/_pubServicePreExpire', [pub:it, service:'Eventos'])
				
				
				mailingService.sendMail(mailFrom, it.email, messagePubSubject, messagePubContent, null, null, null, null)
				
		}
		
		def preExpireInquiryService = Pub.createCriteria().list(){
			
			
			eq("inquiryAvailable", true)
			
			
			eq("inquiryAvailableTo", maxDate)
		}
		
		preExpireInquiryService.each{
				
				log.info "Enviando email de servicio de encuesta caduca en 15 días a: '${it?.email}'..."
			
				def pub = it
				
				def messagePubSubject
				def messagePubContent
				def mailFrom = null
				
				//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
				messagePubSubject = "Servicio de encuesta caduca en 15 días para la sala " + it.name
				messagePubContent = templateService.renderToString('/mailing/_pubServicePreExpire', [pub:it, service:'Encuesta'])
				
				
				mailingService.sendMail(mailFrom, it.email, messagePubSubject, messagePubContent, null, null, null, null)
				
		}
		
		def preExpireSellTicketsOnlineService = Pub.createCriteria().list(){
			
			
			eq("sellTicketsOnlineAvailable", true)
			
			
			eq("sellTicketsOnlineAvailableTo", maxDate)
		}
		
		preExpireSellTicketsOnlineService.each{
				
				log.info "Enviando email de servicio de venta de entradas online caduca en 15 días a: '${it?.email}'..."
			
				def pub = it
				
				def messagePubSubject
				def messagePubContent
				def mailFrom = null
				
				//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
				messagePubSubject = "Servicio de venta de entradas caduca en 15 días para la sala " + it.name
				messagePubContent = templateService.renderToString('/mailing/_pubServicePreExpire', [pub:it, service:'Venta de entradas online'])
				
				
				mailingService.sendMail(mailFrom, it.email, messagePubSubject, messagePubContent, null, null, null, null)
				
		}
		
	}
	
	
}
