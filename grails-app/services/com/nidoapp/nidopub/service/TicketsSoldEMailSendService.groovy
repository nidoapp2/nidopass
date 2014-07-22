package com.nidoapp.nidopub.service

import java.util.Date;
import java.text.SimpleDateFormat;

//import com.nidoapp.nidopub.domain.Customer;


import com.nidoapp.nidopub.domain.EventsListSend
import com.nidoapp.nidopub.domain.PubEvent
import com.nidoapp.nidopub.domain.TicketsSold;
import com.nidoapp.nidopub.domain.api.ApiKey
//import com.nidoapp.nidopub.domain.geo.Country
//import com.nidoapp.nidopub.dto.ReservationRequest



import grails.util.Environment

import org.apache.commons.lang.StringUtils


/**
 * Servicio que realiza operaciones de envío de emails de ventas de entradas de
 * eventos de salas
 * @author Developer
 *
 */
class TicketsSoldEMailSendService {
	
	def mailingService
	def messageSource
	def templateService
	
	/**
	 * Metodo privado que envia a la sala un Mail de notificacion de ventas de entradas de
	 * un evento una vez se ha cerrado la venta
	 * @param reservation datos de reserva de mesa
	 * @return si se ha enviado correctamente
	 */
	def private sendTicketsSoldEMail()
	{
		def sent = false
		
		def maxDate
		def cloudbessDiference = 2
		def salesClosedHours = 2
		
		//if (hour==11) {
		
		use(groovy.time.TimeCategory) {
			
			maxDate = new Date() + cloudbessDiference.hours + salesClosedHours.hours
			
		}
		
		/**
		translationsAvailable = false
		cartesAvailable = false
		eventsAvailable = false
		inquiryAvailable = false
		sellTicketsOnlineAvailable = false 
		 * */
		
		def closedTicketsSalesEvent = PubEvent.createCriteria().list(){
			
			
			eq("ticketsSalesSendEmail", false)
			
			
			lt("dateEvent", maxDate)
		}
		
		closedTicketsSalesEvent.each{
				
				log.info "Enviando email de listado de ventas de entradas de '${it?.title}' a: '${it?.pub.notificationsEmail}' o '${it?.pub.email}'..."
			
				def pubEvent = it
				
				def messagePubSubject
				def messagePubContent
				def mailFrom = null
				
				//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
				messagePubSubject = "Ventas de Entradas: " + it.title
				messagePubContent = templateService.renderToString('/mailing/_pubEventTicketsSales', [pubEvent: it])
				def sales = TicketsSold.findAllByPubEventAndStatus(it, TicketsSold.STATUS_CONFIRMED)
				if (it.pub.notificationsEmail)
					mailingService.sendMailTicketsSalesWithExcel(mailFrom, it.pub.notificationsEmail, messagePubSubject, messagePubContent, null, null, null, null, it, sales)
				else
					mailingService.sendMailTicketsSalesWithExcel(mailFrom, it.pub.email, messagePubSubject, messagePubContent, null, null, null, null, it, sales)
				
				it.ticketsSalesSendEmail = true
				
				it.save(flush:true, failOnError:true)
				
		}
		
	}
	
	
}
