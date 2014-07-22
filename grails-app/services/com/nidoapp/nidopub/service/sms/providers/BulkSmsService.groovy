package com.nidoapp.nidopub.service.sms.providers

import grails.util.Environment

/**
 * Conjunto de servicios relacionados con operaciones con BulkSMS, proveedor concreto de mensajeria SMS
 * @author Developer
 *
 */
class BulkSmsService {
	
	def grailsApplication

    /**
     * Envio de SMS
     * @param text
     * @param phone
     * @param source
     * @return
     */
    def sendSms(text, phone, source) {
		
		def response = [:]
		response.error = false
		//response.sent = false
		
		
		//TODO: Pasar texto a ASCII
		//TODO: Comprobar que no supera 160 caracteres
		//TODO: Comprobar formato de telefono (34666666666)
		
		
		
		log.info "Enviando SMS a ${phone} con contenido: '${text}', y fuente: '${source}'"
		
		try
		{
			def sent
			withHttp(uri:grailsApplication.config.sms.bulkSms.uri)
			{
				sent = post(path: "/eapi/submission/send_sms/2/2.0", query: [username:grailsApplication.config.sms.bulkSms.username, password:grailsApplication.config.sms.bulkSms.password, message:text, msisdn:phone, repliable:'1', source_id:source])
			}
			
			def line = sent.readLine()

			log.debug "Line: " + line
			def splitedLine = line.split("\\|")

			if(splitedLine?.size() > 0)
			{
				def status = splitedLine[0]
				if(status == "0")
				{
					//response.sent = true
					response.reference = splitedLine[2]
					log.debug "Referencia: " +  splitedLine[2]
					log.info "SMS enviado correctamente."
				}
				else
				{
					response.error = true
					response.errorCode = 3
					response.errorDescription = "Estado de envio no aceptado: ${status} (${splitedLine[1]})"
					log.error "Estado de envio no aceptado: ${status} (${splitedLine[1]})"
				}
			}
			else
			{
				response.error = true
				response.errorCode = 2
				response.errorDescription = "La respuesta no cumple el formato: " + line
				log.error "La respuesta no cumple el formato: " + line
			}




		}
		catch(Exception e)
		{
			log.error "Error enviando SMS: " + e
			response.error = true
			response.errorCode = 1
			response.errorDescription = "Error enviando SMS: " + e.getMessage()
		}
		
		response

    }
	

	/**
	 * Obtencion de bandeja de entrada a partir de un mensaje
	 * @param from
	 * @return
	 */
	def getInbox(from)
	{
		def response = [:]
		response.error = false
		
		log.info "Obteniendo bandeja de entrada de SMS a partir del mensaje '${from}'"
		
		try
		{
			def inbox
			withHttp(uri:grailsApplication.config.sms.bulkSms.uri)
			{
				inbox = post(path: "/eapi/reception/get_inbox/1/1.1", query: [username:grailsApplication.config.sms.bulkSms.username, password:grailsApplication.config.sms.bulkSms.password, last_retrieved_id:from])
			}


			response.messages = []

			def lines = inbox.readLines()
			lines.each {
				log.debug "Line: " + it
				def splitedLine = it.split("\\|")

				if(it == lines.first()) {
					log.info "Comprobacion de estado de servicio..."
					if(splitedLine?.size() == 3)
					{
						def status = splitedLine[0]
						if(status == "0")
						{
							log.info "Hay ${splitedLine[2]} mensajes..."
						}
						else
						{
							response.error = true
							response.errorCode = 3
							response.errorDescription = "Estado no aceptado: ${status} (${splitedLine[1]})"
							log.error "Estado no aceptado: ${status} (${splitedLine[1]})"
						}
					}
					else
					{
						response.error = true
						response.errorCode = 2
						response.errorDescription = "La respuesta de estado no cumple el formato, tiene ${splitedLine?.size()} trozos en lugar de 3: " + it
						log.error "La respuesta de estado no cumple el formato, tiene ${splitedLine?.size()} trozos en lugar de 3: " + it
					}
				}
				else if(splitedLine?.size() == 7)
				{
					log.debug "ID SMS: " +  splitedLine[0]
					log.debug "Telefono: " +  splitedLine[1]
					log.debug "Mensaje: " +  splitedLine[2]
					log.debug "Referencia: " +  splitedLine[5]

					response.messages << [id: splitedLine[0], phone: splitedLine[1], message: splitedLine[2], reference: splitedLine[5]]

				}


			}


		}
		catch(Exception e)
		{
			log.error "Error obteniendo bandeja de entrada: " + e
			response.error = true
			response.errorCode = 1
			response.errorDescription = "Error obteniendo bandeja de entrada: " + e.getMessage()
		}
		
		response
	}
}
