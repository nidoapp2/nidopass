package com.nidoapp.nidopub.service

import java.util.Date;
import java.text.SimpleDateFormat;

//import com.nidoapp.nidopub.domain.Customer;
import com.nidoapp.nidopub.domain.EventsListSend
import com.nidoapp.nidopub.domain.Pub
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
class EventsEMailSendService {
	
	def mailingService
	def messageSource
	def templateService
	
	/**
	 * Metodo privado que envia un Mail de notificacion de peticion de reserva al pub
	 * @param reservation datos de reserva de mesa
	 * @return si se ha enviado correctamente
	 */
	def private sendEventsEMails()
	{
		def sent = false
		
		def time = new Date()+1
		
		SimpleDateFormat hourFormat = new SimpleDateFormat("HH")
		def hour = Integer.parseInt(hourFormat.format(time));
		
		if (hour==11) {
		
			def eventsEMailList = EventsListSend.findAllBySentAndSendDateLessThan(false, time)
			
			eventsEMailList.each {
			
				log.info "Enviando email de eventos del pub a: '${it?.emailsLists.emails.email}'..."
				log.info "Enviando listado de eventos: " + it
				def pub = it.pub
				//Object[] name = {it.pub.name}
				def messageSubject = "Nuevos Eventos " + pub.name //messageSource.getMessage('mailing.subject.pubEventsEMailSend', name, null)
				def messageContent = templateService.renderToString('/mailing/_pubEventsEMailSend', [eventsEMailList:it/*, apiKey: ApiKey.findByPub(pub)*/])
				
				def emailList = "";
				
				it.emailsLists.each {
					
					it.emails.each {
						emailList = it.email
						log.info "Enviando a email: " + emailList
						
						def mailFrom = pub.name + " <" + pub.mailAddress + ">"
						
						mailingService.sendMail(mailFrom, emailList, messageSubject, messageContent, pub.mailHost, pub.mailPort, pub.mailUsername, pub.mailPassword)
					}
				}
				it.sent = true
				it.save(flush:true, failOnError:true)
				
				sent = true
				
				sent
			}
		} else {
			log.info "No se envian los emails de eventos de pubs porque todavía no son las 11 de la mañana"
		}
	}
	
	
}
