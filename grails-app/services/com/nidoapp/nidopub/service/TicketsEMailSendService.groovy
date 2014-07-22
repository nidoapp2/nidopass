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
class TicketsEMailSendService {
	
	def mailingService
	def messageSource
	def templateService
	
	/**
	 * Metodo privado que envia un Mail de notificacion de compra de entradas expirada
	 * @return si se ha enviado correctamente
	 */
	def private sendTicketsCanceledEMails()
	{
		def sent = false
		
		def maxDate
		def expiredMinutes = 10
		
		//if (hour==11) {
		
		use(groovy.time.TimeCategory) {
			
			maxDate = new Date() - expiredMinutes.minutes
			
		}
		
		def notProcessedPayments = TicketsSold.createCriteria().list(){
			
			
			eq("status", TicketsSold.STATUS_PENDING)
			
			
			lt("buyDate", maxDate)
		}
		
		notProcessedPayments.each{
			
				it.status = TicketsSold.STATUS_EXPIRED
				it.notifiedCustomerDate = new Date()
				
				it.save(flush:true, failOnError:true)
				
				log.info "Enviando email de compra de entradas no realizada a: '${it?.customer.email}'..."
			
				def pub = it.pubEvent.pub
			
				def event = it.pubEvent
				
				log.info "Enviando resolucion de compra de entradas EXPIRADA para el evento: '${it?.pubEvent.title}' de la sala: '${it?.pubEvent.pub.name}' a email del cliente: '${it?.customer.email}'..."
				
				
				def messageCustomerSubject
				def messageCustomerContent
				def mailFrom = pub.name + " <" + pub.mailAddress + ">"
				
				//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
				messageCustomerSubject = it.pubEvent.pub.name + " - Compra de entrada/s NO realizada - " + it.pubEvent.title
				messageCustomerContent = templateService.renderToString('/mailing/_customerTicketsBuyExpired', [ticketsSold:it])
				
				
				mailingService.sendMail(mailFrom, it.customer.email, messageCustomerSubject, messageCustomerContent, pub.mailHost, pub.mailPort, pub.mailUsername, pub.mailPassword)
				
		}
		
	}
	
	
}
