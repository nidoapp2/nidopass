package com.nidoapp.nidopub.controller.endpoint

import grails.util.Environment

/**
 * Controlador que atiende peticiones del servicio de SMS BulkSMS
 * @author Developer
 *
 */
class BulkSmsEndpointController {
	
	def grailsApplication
	def reservationService

	/**
	 * Se ejecuta cuando se recibe un SMS
	 * Ver http://bulksms.com.es/docs/eapi/reception/http_push/
	 */
    def receivedSms = { 
		
		log.info "Se ha recibido llamada desde BulkSMS indicando la recepcion de un mensaje..."
		
		try{
		
			if(params.password)
			{
				if(params.password == grailsApplication.config.sms.bulkSms.password)
				{
					def environment = Environment.current.name
					if(environment == params.source_id)
					{
						log.info "Enviando SMS indicando como fuente el entorno de la aplicacion: " +  environment
						def smsId = params.msg_id
						def message = params.message
						def reference = params.referring_batch_id
						def sender = params.sender
						
						log.info "ID Mensaje: " + smsId
						log.info "Mensaje: " + message
						log.info "Referencia: " + reference
						log.info "Sender: " + sender
						
						reservationService.processCancelConfirmedReservationInSmsInbox(smsId, message, reference, sender)
						reservationService.processInProgressReservationInSmsInbox(smsId, message, reference, sender)
					}
					else
					{
						log.warn "El SMS recibido no es para el entorno actual '${environment}', sino para '${params.source_id}'."
					}
					
				}
				else
				{
					log.warn "El password indicado no es correcto."
				}
			}
			else
			{
				log.warn "No hay password en el parametro 'password'."
			}
		
		}catch (Throwable e){
			log.error "Error en receivedSms: ", e
		}
		
		render "Requested"
	}
}
