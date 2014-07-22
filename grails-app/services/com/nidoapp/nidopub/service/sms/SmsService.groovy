package com.nidoapp.nidopub.service.sms

import grails.util.Environment

/**
 * Conjutno de servicios que realizan operaciones asociadas al envio o recepcion de SMS
 * @author Developer
 *
 */
class SmsService {
	
	def grailsApplication
	def bulkSmsService
	
	def smsInboxSimulation = []
	def smsReferenceSimulation = 1
	def smsIdSimulation = 1

    /**
     * Servicio que encia un SMS. SU comportamiento depende el entorno en el que se ejecuta la aplicacion.
     * Solo el entorno de produccion permite el envio de SMS a cualquier telefono.
     * @param text texto del SMS
     * @param phone telefono al que enviar el SMS
     * @return
     */
    def sendSms(text, phone) {
		
		def response = [:]		
	
		def environment = Environment.current.name
		
		log.info "Enviando SMS indicando como fuente el entorno de la aplicacion: " +  environment
		
		if (Environment.current == Environment.DEVELOPMENT ) 
		{
			//log.info "Se simula el envio de SMS... "
			//response = simulateSmsSend()
			
			def recipient = recipientChecked(phone)
			
			response = bulkSmsService.sendSms(text, recipient, environment)
		}
		else if (Environment.current == Environment.TEST ) 
		{
			def recipient = recipientChecked(phone)
						
			response = bulkSmsService.sendSms(text, recipient, environment)
		}
		
		else if (Environment.current == Environment.PRODUCTION )
		{
			response = bulkSmsService.sendSms(text, phone, environment)
		}
		
		
    }
	
	/**
	 * Metodo privado que comprueba si un destinatario es valido segun el entorno, y su no lo es
	 * devuelve uno que si sea aceptado para el entorno.
	 * @param to destinatario
	 * @return destinatario correcto
	 */
	def private recipientChecked(to)
	{
		def recipient
		
		def limits = grailsApplication.config.mail.limits
		
		if(limits)
		{
			log.info "Hay limites en los receptores de SMS."
			
			def recipients = grailsApplication.config.sms.recipients
			
			log.info "Comprobando si el destinatario '${to}' esta entre los destinatarios permitidos: " + recipients
			
			if(recipients?.contains(to))
			{
				log.info "El destinatario '${to}' esta entre los destinatarios permitidos. Se le envia el email."
				recipient = to
			}
			else
			{
				recipient = recipients?.first()
				log.info "El destinatario '${to}' NO esta entre los destinatarios permitidos. Se envia email a: " + recipient
			}
		}
		else
		{
			log.info "No hay limites en los receptores de SMS. Se envia a: " + to
			recipient = to
		}
		
		
		
		recipient
	}
	
	/**
	 * NO SE USA. Pretendia sumular un envio de SMS para no consumir creditos.
	 * @return
	 */
	def private simulateSmsSend()
	{
		log.info "Simulando el envio de SMS..."
		
		def response = [:]
		
		Random random = new Random()
		def intRandom = random.nextInt(11)
		
		if(intRandom <= 2)
		{
			log.info "Se simula error el envio de SMS ... "
			response.error = true
		}
		else
		{
			log.info "Se simula acierto el envio de SMS ... "
			response.error = false
			response.reference = smsReferenceSimulation
			
			log.info "Referencia: " + response.reference
			
			smsReferenceSimulation++
			
			/*
			
			log.info "Se simula entrada de respuesta en inbox... "
			
			intRandom = random.nextInt(11)
			
			def message
			if(intRandom <= 3)
			{
				log.info "Se simula rechazo de reserva... "
				message = "No"
			}
			else
			{
				log.info "Se simula confirmacion de reserva... "
				message = "Si"
			}
			smsInboxSimulation << [id: smsIdSimulation, phone: "666666666", message: message, reference: response.reference]
			
			withHttp(uri:'http://localhost:8080')
			{
				post(path: "/nidopub-webapp/bulkSmsEndpoint", query: [password:grailsApplication.config.sms.bulkSms.password, msg_id:smsIdSimulation, message: message])
			}
			
			smsIdSimulation++
			
			*/
			
		}
		
		response
	}
	
	
	def getInbox(from)
	{
		def response = [:]
		
		response = bulkSmsService.getInbox(from)
	}
}
