package com.nidoapp.nidopub.service

import java.util.Date;
import java.text.SimpleDateFormat;

//import com.nidoapp.nidopub.domain.Customer;


import com.nidoapp.nidopub.domain.PubEventPromotionalCode
import com.nidoapp.nidopub.domain.PubEvent
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
class PromotionalCodeExpiredService {
	
	/**
	 * Metodo privado que marca como expirados los códigos promocionales que se pasan de
	 * la fecha limite de uso para que no se puedan utilizar
	 * @return si se ha enviado correctamente
	 */
	def private expirePromotionalCodes()
	{
		
		def actualDate
		def maxDate
		def cloudbeesDiference = 2
		def expiredHours = cloudbeesDiference + 5
		
		//if (hour==11) {
		
		use(groovy.time.TimeCategory) {
			
			maxDate = new Date() + expiredHours.hours
			actualDate = new Date() + cloudbeesDiference.hours
			
		}
		
		
		def events = PubEvent.findAllByDateEventLessThanEqualsAndExpiredPromotionalCodes(maxDate, false)
		
		events.each {
			
			log.info "Marcando como expirados los códigos promocionales PENDIENTES del evento " + it.title
			
			/*def expiredPromotionalCodes = PubEventPromotionalCode.createCriteria().list(){
				
				
				eq("pubEvent", it)
				
				eq("status", PubEventPromotionalCode.STATUS_PENDING)
				
			}*/
			
			def expiredPromotionalCodes = PubEventPromotionalCode.findAllByPubEventAndStatus(it, PubEventPromotionalCode.STATUS_PENDING)
			
			expiredPromotionalCodes.each{
			
				
				it.status = PubEventPromotionalCode.STATUS_EXPIRED
				
				it.save(flush:true, failOnError:true)
					
			}
			
			it.expiredPromotionalCodes = true
			
		}
		
		
	}
	
	
}
