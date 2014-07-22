package com.nidoapp.nidopub.service

import java.util.Date;
import java.text.SimpleDateFormat;

import com.nidoapp.nidopub.domain.Customer;
import com.nidoapp.nidopub.domain.Reservation
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.api.ApiKey
import com.nidoapp.nidopub.domain.geo.Country
import com.nidoapp.nidopub.dto.ReservationRequest

import grails.util.Environment

import org.apache.commons.lang.StringUtils


/**
 * Servicios que realizan operaciones de reserva de mesa
 * @author Developer
 *
 */
class ReservationService {
	
	def smsService
	def mailingService
	def messageSource
	def templateService
	def redisService
	def countryDialingService
	

    /**
     * Realiza una reserva de mesa
     * @param request datos de la reserva
     * @return objeto de reserva realizada, false si no se ha realizado por cierre pub
     * 			 o null si no se ha relizado por algun motivo
     */
    def makeReservation(ReservationRequest request ) {
		
		def reservationDone

		
		try
		{
			log.info "Realizando reserva para la siguiente peticion: " + request?.properties
			
			def pub = Pub.get(request.pubId);
			
			if(pub)
			{
				log.info "Pub para el que se realiza la reserva: ${pub.name}, con id: ${pub.id}"
				
				//Comprobamos si la reserva se hace para un d??a en el que el pub no abre
				
				Date reservationDate = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).parse(request.reservationDate.toString())
				
				
				SimpleDateFormat dayFormat = new SimpleDateFormat("u")
				SimpleDateFormat hourFormat = new SimpleDateFormat("HH")
				
				def day = Integer.parseInt(dayFormat.format(reservationDate))
				def hour = Integer.parseInt(hourFormat.format(reservationDate));
				
				if (( ( (!pub.LunesM && (hour>9 && hour<18)) || (!pub.LunesN && (hour>18 && hour<24)) ) && day == 1) || 
					( ( (!pub.MartesM && (hour>9 && hour<18)) || (!pub.MartesN && (hour>18 && hour<24)) ) && day == 2) ||
					( ( (!pub.MiercolesM && (hour>9 && hour<18)) || (!pub.MiercolesN && (hour>18 && hour<24)) ) && day == 3) ||
					( ( (!pub.JuevesM && (hour>9 && hour<18)) || (!pub.JuevesN && (hour>18 && hour<24)) ) && day == 4) ||
					( ( (!pub.ViernesM && (hour>9 && hour<18)) || (!pub.ViernesN && (hour>18 && hour<24)) ) && day == 5) ||
					( ( (!pub.SabadoM && (hour>9 && hour<18)) || (!pub.SabadoN && (hour>18 && hour<24)) ) && day == 6) ||
					( ( (!pub.DomingoM && (hour>9 && hour<18)) || (!pub.DomingoN && (hour>18 && hour<24)) ) && day == 7) ) {
					
					log.info "Intento de reserva en el pub: " + pub.name + " pero día " + request.reservationDate.toString() + " está cerrado y no puede reservar. Se avisa al cliente."
					
					log.info "Se notifica al cliente mediante SMS a: " + Country.findByCode(request.customerPhoneCountryIsoCode).dialCode+request.customerPhone + " y a través de EMAIL a: " + request.customerEmail
					
					def sent = false
					def sent2 = false
		
					
					sent = sendSmsReservationNotAvailableNotificationToCustomer(request)
		
					sent2 = sendEmailReservationNotAvailableNotificationToCustomer(request)
		
					if(!sent && !sent2) {
						log.error "Imposible realizar notificacion al cliente de reserva no disponible por cierre de pub ese día."
						def messageSubject = messageSource.getMessage('mailing.subject.support.reservationNotAvailableCustomerNotificationProblems', null, null)
						def messageContent = templateService.renderToString('/mailing/_reservationNotAvailableCustomerNotificationProblems', [reservationRequest:request])
						mailingService.sendMailToSupport(messageSubject, messageContent)
		
					}
					
					reservationDone = false
					
					
				}
				else if(pub.reservationPhone || pub.reservationEmail)
				{
					def customer
					
					
					if(request.customerEmail || request.customerPhone && request.customerPhoneCountryIsoCode)
					{
						if(request.customerEmail)
						{
							log.info "Buscando cliente con email: '${request.customerEmail}'"
							
							customer = Customer.findByEmail(request.customerEmail)
						}
						
						if(!customer && request.customerPhone && request.customerPhoneCountryIsoCode)
						{
							log.info "Buscando cliente con telefono: '${request.customerPhone}' en pais: " + request.customerPhoneCountryIsoCode
							
							def country = Country.findByCode(request.customerPhoneCountryIsoCode)
							
							customer = Customer.findByPhoneAndCountry(request.customerPhone,country)
						}
						
						if(customer)
						{
							def updateCustomer = false
							
							if(request.customerPhoneCountryIsoCode && request.customerPhoneCountryIsoCode != customer.country?.code)
							{
								log.info "Actualizando pais de cliente: '${customer.country?.code}' por '${request.customerPhoneCountryIsoCode}'"
								
								def country = Country.findByCode(request.customerPhoneCountryIsoCode)								
								if(!country)
								{
									log.info "No se ha encontrado pais. Se crea uno nuevo..."
									def dialCode = countryDialingService.getDialCode(request.customerPhoneCountryIsoCode)
									country = new Country(code: request.customerPhoneCountryIsoCode, dialCode: dialCode).save(flush:true, failOnError:true)
									
								}
								customer.country = country
								customer.phone = request.customerPhone
								updateCustomer = true
								
							}
							
							if(request.customerPhone && request.customerPhone != customer.phone)
							{
								log.info "Actualizando telefono de cliente: '${customer.phone}' por '${request.customerPhone}'"
								customer.phone = request.customerPhone
								updateCustomer = true
								
							}
							
							if(request.customerEmail && request.customerEmail != customer.email)
							{
								log.info "Actualizando email de cliente: '${customer.email}' por '${request.customerEmail}'"
								customer.email = request.customerEmail
								updateCustomer = true
							}
							
							if(updateCustomer)
								customer.save(flush:true, failOnError:true)
						}
						else
						{
							log.info "No se ha encontrado cliente. Se crea uno nuevo..."
							customer = new Customer()
							customer.name = request.customerName
							customer.email = request.customerEmail
							customer.phone = request.customerPhone
							
							if(request.customerPhoneCountryIsoCode)
							{
								def country = Country.findByCode(request.customerPhoneCountryIsoCode)
								if(!country)
								{
									log.info "No se ha encontrado pais. Se crea uno nuevo..."
									def dialCode = countryDialingService.getDialCode(request.customerPhoneCountryIsoCode)
									country = new Country(code: request.customerPhoneCountryIsoCode, dialCode: dialCode).save(flush:true, failOnError:true)
									
								}
								customer.country = country
							}
							
							
							customer.save(flush:true, failOnError:true)
						}
						
						
						def reservation = new Reservation()
						reservation.customer = customer
						reservation.pub = pub
						reservation.adults = request.adults
						reservation.children = request.children
						reservation.reservationDate = request.reservationDate
						reservation.comments = request.comments
						reservation.sourceApiKey = request.sourceApiKey
						reservation.createDate = new Date()
						
						reservation.save(flush:true, failOnError:true)
						
						def sent = false
						def sentByEmail = false
							
						if(Pub.NOTIFICATION_MODE_SMS == pub.reservationNotificationPreferenceMode && pub.reservationPhone)
						{
							log.info "El pub prefiere notificacion de reserva mediante SMS a: " + pub.reservationPhone
							
	
							sent = sendSmsReservationNotificationToPub(reservation)
							
							if(!sent && pub.reservationEmail)
							{
								log.info "Se ha producido un error en el envio del SMS. Se prueba envio de email a:" + pub.reservationEmail
								sent = sendEmailReservationNotificationToPub(reservation)
								
								if(sent)
									sentByEmail = true
							}
							
							
						}
						else if(Pub.NOTIFICATION_MODE_EMAIL == pub.reservationNotificationPreferenceMode && pub.reservationEmail)
						{
							log.info "El pub prefiere notificacion de reserva mediante email a: " + pub.reservationEmail
							
							sent = sendEmailReservationNotificationToPub(reservation)
							
							if(sent)
								sentByEmail = true
							
							if(!sent && pub.reservationPhone)
							{
								log.info "Se ha producido un error en el envio del email. Se prueba envio de SMS a:" + pub.reservationPhone
								sent = sendSmsReservationNotificationToPub(reservation)
							}
						}
						else if(Pub.NOTIFICATION_MODE_SMS_AND_EMAIL == pub.reservationNotificationPreferenceMode && pub.reservationPhone && pub.reservationEmail)
						{
							log.info "El pub prefiere notificacion de reserva mediante SMS a '" + pub.reservationPhone + "' y email a: " + pub.reservationEmail
							
							log.info "Intentando notificacion de reserva mediante SMS a: " + pub.reservationPhone
							sent = sendSmsReservationNotificationToPub(reservation)
							
							log.info "Se ha producido un error en el envio del SMS. Se prueba envio de email a:" + pub.reservationEmail
							def sent2 = sendEmailReservationNotificationToPub(reservation)
							
							if(sent2)
								sentByEmail = true
							
							sent = sent || sent2

						}
						else
						{
							
							log.warn "El pub no tiene bien configurada la preferencia de envio de notificaciones de reserva con movil o email. Se intentara por SMS o Email independientemente de la preferencia."
									
							if(pub.reservationPhone)
							{
								log.info "Intentando notificacion de reserva mediante SMS a: " + pub.reservationPhone
								sent = sendSmsReservationNotificationToPub(reservation)
							}
							
							if(pub.reservationEmail)
							{
								log.info "Se ha producido un error en el envio del SMS. Se prueba envio de email a:" + pub.reservationEmail
								def sent2 = sendEmailReservationNotificationToPub(reservation)
								
								if(sent2)
									sentByEmail = true
								
								sent = sent || sent2
								
							}
							
							
						}
						
						
						if(sent)
						{
							reservationDone = reservation
							
							if(!sentByEmail && reservation.comments)
								sendEmailReservationCommentsToPub(reservation)
						}
		
					}
					else
					{
						log.error "Debe especificarse el email del cliente, o bien el telefono y pais"												
					}
					
					
			
					
				}
				else
				{
					log.warn "El pub ${pub.name}, con id: ${pub.id}, no tiene ni telefono movil ni email de reservas."
				}
			
			}
			else
			{
				log.warn = "No existe pub con id: " +  request.pubId
			}
	
			
		}
		catch(Exception e)
		{
			log.error "Error durante la creacion de la reserva: " + e	
		}
		
		reservationDone

    }
	
	/**
	 * Metodo privado que envia un SMS de notificacion de peticion de reserva al pub
	 * @param reservation datos de reserva de mesa
	 * @return si se ha enviado correctamente
	 */
	def private sendSmsReservationNotificationToPub(reservation)
	{
		def sent = false
		log.info "Enviando SMS al pub..."
		def message = generateSmsContentForPubRequest(reservation)
		def smsSendResponse = smsService.sendSms(message, reservation?.pub?.reservationPhoneWithCountryPrefix())
		if(!smsSendResponse?.error)
		{
			log.info "SMS enviado correctamente. Referencia: ${smsSendResponse.reference}"
			reservation.notifiedPub = true
			reservation.notifiedPubMode = Pub.NOTIFICATION_MODE_SMS
			reservation.notifiedPubDate = new Date()
			reservation.smsPubNotificationReference = smsSendResponse.reference
			reservation.status = Reservation.STATUS_IN_PROGRESS
			reservation.save(flush:true, failOnError:true)
			
			sent = true
			
		}
		else
		{
			log.error "Error enviando SMS. (Codigo: ${smsSendResponse.errorCode}, Description: ${smsSendResponse.errorDescription})"
			sent = false
		}
		
		sent
	}
	
	/**
	 * Metodo privado que envia un Mail de notificacion de peticion de reserva al pub
	 * @param reservation datos de reserva de mesa
	 * @return si se ha enviado correctamente
	 */
	def private sendEmailReservationNotificationToPub(reservation)
	{
		def sent = false
		
		log.info "Enviando peticion de reserva a email del pub: '${reservation?.pub.reservationEmail}'..."
		def pub = reservation.pub
		def messageSubject = messageSource.getMessage('mailing.subject.pubNewReservation', null, null)
		def messageContent = templateService.renderToString('/mailing/_pubNewReservation', [reservation:reservation, apiKey: ApiKey.findByPub(pub)])
		
		mailingService.sendMail(pub.reservationEmail, messageSubject, messageContent)
		
		reservation.notifiedPub = true
		reservation.notifiedPubMode = Pub.NOTIFICATION_MODE_EMAIL
		reservation.notifiedPubDate = new Date()
		reservation.status = Reservation.STATUS_IN_PROGRESS
		reservation.save(flush:true, failOnError:true)
		
		sent = true
		
		sent
	}
	
	/**
	 * Metodo privado que envia un Mail de con comentarios de una peticion de reserva al pub
	 * @param reservation datos de reserva de mesa
	 * @return si se ha enviado correctamente
	 */
	def private sendEmailReservationCommentsToPub(reservation)
	{
		def sent = false
		
		log.info "Enviando comentarios de peticion de reserva a email del pub: '${reservation?.pub.reservationEmail}'..."
		def pub = reservation.pub
		def messageSubject = messageSource.getMessage('mailing.subject.pubNewReservationComments', null, null) + ": ${reservation.reservationDate?.format('dd/MM/yyyy HH:mm')}, ${reservation.customer?.name}"
		def messageContent = templateService.renderToString('/mailing/_pubNewReservationComments', [reservation:reservation, apiKey: ApiKey.findByPub(pub)])
		
		mailingService.sendMail(pub.reservationEmail, messageSubject, messageContent)
		
		
		sent = true
		
		sent
	}
	
	/**
	 * Servicio que reliza un procesamiento de las peticiones de reserva de mesa que se encuentran
	 * pendientes en la bandeja de entrada de SMS.
	 * @return
	 */
	def processInProgressReservationsInSmsInbox()
	{
		def smsInboxResponse = smsService.getInbox(getInboxSmsLastProcessed())
		
		if(!smsInboxResponse.error)
		{
			log.info "Existen ${smsInboxResponse.messages?.size()} mensajes:"
			smsInboxResponse.messages?.each {
				log.info "Procesando mensaje ${it.id}, del telefono ${it.phone}, con referencia: ${it.reference}, y contenido: '${it.message}'"
				
				processInProgressReservationInSmsInbox(it.id, it.message, it.reference, it.sender)
			}
		}
		else
		{
			log.error "Error obteniendo bandeja de entrada de SMS. (Codigo: ${result.errorCode}, Description: ${result.errorDescription})"
		}
	}
	
	
	/**
	 * Servicio que reliza un procesamiento de un SMS concreto asociado a una reserva
	 * @param smsId ID de sms
	 * @param message mensaje
	 * @param reference referencia al mensaje al que responde
	 * @return
	 */
	def processInProgressReservationInSmsInbox(smsId, message, reference, sender)
	{
		log.info "Procesando mensaje ${smsId}, con referencia: ${reference}, contenido: '${message}' y n???mero env???o: ${sender}"
		
		def reservation = Reservation.findBySmsPubNotificationReference(reference)
		
		if(reservation)
		{
			
			//Se comprueba que el tel???fono de origen es el del pub.
			if(sender.equals(reservation.pub.reservationPhoneWithCountryPrefix())) {
			
				log.info "Reserva vinculada a la referencia '${reference}': " + reservation?.id
				
				def confirmed = null
				if(message?.contains('S') || message?.contains('s'))
				{
					log.info "La reserva queda confirmada por el pub"
					confirmed = true
	
				}
				else if(message?.contains('N') || message?.contains('n'))
				{
					log.info "La reserva queda denegada por el pub"
					confirmed = false
				}
				else
				{
					log.warn "SMS con referencia de notificacion '${reference}' no contiene SI o NO."
				}
				
				if(confirmed != null)
				{
					def processed = processInProgressReservation(reservation, confirmed)
					
					if(!processed)
						sendSmsReservationAlreadyProcessedNotificationToPub(reservation)
				}
			
			
			}
			
		}
		else
		{
			log.warn "No existe reserva con referencia de notificacion : " + reference
			log.warn "Probablemente fue recibida por entorno diferente al actual : " + Environment.current.name
		}
		
		log.info "Se anota ultimo SMS procesado..."
		inboxSmsProcessed(smsId)
	}
	
	/**
	 * CANCELACI???N RESERVA
	 * Servicio que reliza un procesamiento de un SMS concreto asociado a una reserva
	 * para cancelarla.
	 * @param smsId ID de sms
	 * @param message mensaje
	 * @param reference referencia al mensaje al que responde
	 * @return
	 */
	def processCancelConfirmedReservationInSmsInbox(smsId, message, reference, sender)
	{
		log.info "Procesando mensaje ${smsId}, con referencia: ${reference}, contenido: '${message}' y sender: ${sender}"
		
		def reservation = Reservation.findBySmsPubNotificationReference(reference)
		
		if(reservation)
		{
			
			if(sender.equals(reservation.customer.phoneWithCountryPrefix())) {
			
				log.info "Reserva vinculada a la referencia '${reference}': " + reservation?.id
				
				def canceled = false
				if(message?.contains('C') || message?.contains('c'))
				{
					log.info "La reserva queda cancelada por el cliente"
					canceled = true
	
				}
				else
				{
					log.warn "SMS con referencia de notificacion '${reference}' no contiene CANCELAR."
				}
				
				if(canceled != false)
				{
					def processed = processCancelConfirmedReservation(reservation, canceled)
					
					if(!processed)
						sendSmsReservationAlreadyProcessedNotificationToPub(reservation)
				}
			
			
			}
			
		}
		else
		{
			log.warn "No existe reserva con referencia de notificacion : " + reference
			log.warn "Probablemente fue recibida por entorno diferente al actual : " + Environment.current.name
		}
		
		log.info "Se anota ultimo SMS procesado..."
		inboxSmsProcessed(smsId)
	}
	
	/**
	 * Metodo privado que envia un SMS al pub idnicando que una reserva ya ha sido procesada, ya
	 * sea que fue confirmada, denegada, o haya caducado.
	 * @param reservation datos de la reserva
	 * @return indica si e??? SMS ha sido enviado o no
	 */
	def private sendSmsReservationAlreadyProcessedNotificationToPub(reservation)
	{
		
		def sent = false
		
		log.info "Enviando SMS al pub indicando que la peticion de reserva ya ha sido atendida..."
		
		
		def messageContent
		
		if(reservation.status == Reservation.STATUS_CONFIRMED)
		{
			messageContent = generateSmsContentForPubReservationAlreadyConfirmed(reservation)
		}
		else if(reservation.status == Reservation.STATUS_DENIED)
		{
			messageContent = generateSmsContentForPubReservationAlreadyDenied(reservation)
		}
		else if(reservation.status == Reservation.STATUS_EXPIRED)
		{
			messageContent = generateSmsContentForPubReservationAlreadyExpired(reservation)
		}
		else if(reservation.status == Reservation.STATUS_CANCELLED)
		{
			messageContent = generateSmsContentForPubReservationAlreadyCancelled(reservation)
		}
		
		
		log.info "Enviando SMS: '${messageContent}' al telefono ${reservation?.customer?.phone}"
		def sendSmsResponse = smsService.sendSms(messageContent, reservation?.pub?.reservationPhoneWithCountryPrefix())
		
		if(!sendSmsResponse?.error)
		{
			log.info "El pub ha recibido correctamente notificacion de confirmacion."
			sent = true
		}
		else
		{
			
			log.error "Error enviando SMS al pub. (Codigo: ${sendSmsResponse.errorCode}, Description: ${sendSmsResponse.errorDescription})"
			sent = false
		}
	
		sent
	}
	
	/**
	 * metodo privado que genera el contenido de SMS a pub indicando que una reserva
	 * ya habia sido confirmada
	 * @param reservation datos de la reserva
	 * @return texto generado
	 */
	def private generateSmsContentForPubReservationAlreadyConfirmed(reservation)
	{
		log.info "Generando contenido de SMS para informar a pub que la reserva ya habia sido confirmada. Reserva con ID: " + reservation?.id
		
		//def adults = StringUtils.abbreviate( "${reservation.adults}",2)
		def adults = reservation.adults
		//def children = StringUtils.abbreviate( "${reservation.children}",2)
		def children = reservation.children
		def phone = StringUtils.abbreviate( "${reservation.customer.phone}",9)
		def name = StringUtils.abbreviate( "${reservation.customer.name}",11)
		
		def smsContent = "La solicitud de reserva YA HABIA SIDO CONFIRMADA: Fecha: ${reservation.reservationDate?.format('dd/MM/yyyy HH:mm')}. Adultos: ${adults}, Menores: ${children}, Telefono: ${phone}, Nombre: ${name}."
		
		log.info "Contenido generado: " + smsContent
		
		smsContent = StringUtils.abbreviate( "${smsContent}",160)
		
		log.info "Contenido truncado: " + smsContent
		
		smsContent
	}
	
	/**
	 * metodo privado que genera el contenido de SMS a pub indicando que una reserva
	 * ya habia sido denegada
	 * @param reservation datos de la reserva
	 * @return texto generado
	 */
	def private generateSmsContentForPubReservationAlreadyDenied(reservation)
	{
		log.info "Generando contenido de SMS para informar a pub que la reserva ya habia sido denegada. Reserva con ID: " + reservation?.id
		
		//def adults = StringUtils.abbreviate( "${reservation.adults}",2)
		def adults = reservation.adults
		//def children = StringUtils.abbreviate( "${reservation.children}",2)
		def children = reservation.children
		def phone = StringUtils.abbreviate( "${reservation.customer.phone}",9)
		def name = StringUtils.abbreviate( "${reservation.customer.name}",11)
		
		def smsContent = "La solicitud de reserva YA HABIA SIDO DENEGADA: Fecha: ${reservation.reservationDate?.format('dd/MM/yyyy HH:mm')}. Adultos: ${adults}, Menores: ${children}, Telefono: ${phone}, Nombre: ${name}."
		
		log.info "Contenido generado: " + smsContent
		
		smsContent = StringUtils.abbreviate( "${smsContent}",160)
		
		log.info "Contenido truncado: " + smsContent
		
		smsContent
	}
	
	/**
	 * metodo privado que genera el contenido de SMS a pub indicando que una reserva
	 * ya ha cadudado
	 * @param reservation datos de la reserva
	 * @return texto generado
	 */
	def private generateSmsContentForPubReservationAlreadyExpired(reservation)
	{
		log.info "Generando contenido de SMS para informar a pub que la reserva ya ha expirado. Reserva con ID: " + reservation?.id
		
		//def adults = StringUtils.abbreviate( "${reservation.adults}",2)
		def adults = reservation.adults
		//def children = StringUtils.abbreviate( "${reservation.children}",2)
		def children = reservation.children
		def phone = StringUtils.abbreviate( "${reservation.customer.phone}",9)
		def name = StringUtils.abbreviate( "${reservation.customer.name}",11)
		
		def smsContent = "La solicitud de reserva YA HA EXPIRADO: Fecha: ${reservation.reservationDate?.format('dd/MM/yyyy HH:mm')}. Adultos: ${adults}, Menores: ${children}, Telefono: ${phone}, Nombre: ${name}."
		
		log.info "Contenido generado: " + smsContent
		
		smsContent = StringUtils.abbreviate( "${smsContent}",160)
		
		log.info "Contenido truncado: " + smsContent
		
		smsContent
	}
	
	/**
	 * metodo privado que genera el contenido de SMS a pub indicando que una reserva
	 * ya habia sido cancelada
	 * @param reservation datos de la reserva
	 * @return texto generado
	 */
	def private generateSmsContentForPubReservationAlreadyCancelled(reservation)
	{
		log.info "Generando contenido de SMS para informar a pub que la reserva ya habia sido cancelada. Reserva con ID: " + reservation?.id
		
		//def adults = StringUtils.abbreviate( "${reservation.adults}",2)
		def adults = reservation.adults
		//def children = StringUtils.abbreviate( "${reservation.children}",2)
		def children = reservation.children
		def phone = StringUtils.abbreviate( "${reservation.customer.phone}",9)
		def name = StringUtils.abbreviate( "${reservation.customer.name}",11)
		
		def smsContent = "La solicitud de reserva YA HABIA SIDO CANCELADA: Fecha: ${reservation.reservationDate?.format('dd/MM/yyyy HH:mm')}. Adultos: ${adults}, Menores: ${children}, Telefono: ${phone}, Nombre: ${name}."
		
		log.info "Contenido generado: " + smsContent
		
		smsContent = StringUtils.abbreviate( "${smsContent}",160)
		
		log.info "Contenido truncado: " + smsContent
		
		smsContent
	}
	
	
	/**
	 * Servicio que realiza la confirmacion o denegacion de una reserva. 
	 * @param reservation datos de la reserva
	 * @param confirmed si se confirma o se deniega
	 * @return indica si la reserva ha sido o no procesada
	 */
	def processInProgressReservation(reservation, confirmed)
	{
		def processed = false		
		
		log.info "Procesando reserva con ID '${reservation?.id}' tras respuesta de pub..."

		if(reservation.status == Reservation.STATUS_IN_PROGRESS) {

			if(confirmed) {
				log.info "La reserva queda confirmada por el pub"
				reservation.status = Reservation.STATUS_CONFIRMED
				reservation.save(flush:true, failOnError:true)
			}
			else {
				log.info "La reserva queda denegada por el pub"
				reservation.status = Reservation.STATUS_DENIED
				reservation.save(flush:true, failOnError:true)
			}

			processed = true


			def sent = false
			def sent2 = false

			def customer = reservation.customer
			
			def pub = reservation.pub

			if(Customer.NOTIFICATION_MODE_SMS == customer.reservationNotificationPreferenceMode && customer.phone) {
				log.info "El cliente prefiere notificacion de reserva mediante SMS a: " + customer.phone


				sent = sendSmsReservationNotificationToCustomer(reservation)

				if(!sent && customer.email) {
					log.info "Se ha producido un error en el envio del SMS. Se prueba envio de email a:" + customer.email
					sent = sendEmailReservationNotificationToCustomer(reservation)
				}
			}
			else if(Customer.NOTIFICATION_MODE_EMAIL == customer.reservationNotificationPreferenceMode && customer.email) {
				log.info "El cliente prefiere notificacion de reserva mediante email a: " + customer.email

				sent = sendEmailReservationNotificationToCustomer(reservation)

				if(!sent && customer.phone) {
					log.info "Se ha producido un error en el envio del email. Se prueba envio de SMS a:" + customer.phone
					sent = sendSmsReservationNotificationToCustomer(reservation)
				}
			}
			else {
				log.warn "El cliente no tiene bien configurada la preferencia de envio de notificaciones de reserva con movil o email. Se intentara por SMS o Email independientemente de la preferencia."

				if(customer.phone) {
					log.info "Intentando notificacion de reserva mediante SMS a: " + customer.phone
					sent = sendSmsReservationNotificationToCustomer(reservation)
				}

				if(!sent && customer.email) {
					log.info "Se ha producido un error en el envio del SMS. Se prueba envio de email a:" + customer.email
					sent = sendEmailReservationNotificationToCustomer(reservation)
				}
			}
			
			if (pub.reservationEmail) {
				sent2 = sendEmailReservationConfirmDenyNotificationToPub(reservation)
			}


			if(!sent) {
				log.error "Imposible realizar notificacion al cliente."
				def messageSubject = messageSource.getMessage('mailing.subject.support.reservationCustomerNotificationProblems', null, null)
				def messageContent = templateService.renderToString('/mailing/_reservationCustomerNotificationProblems', [reservation:reservation])
				mailingService.sendMailToSupport(messageSubject, messageContent)

			}
			
			if(!sent2) {
				log.error "Imposible realizar notificacion al pub."
				def messageSubject = messageSource.getMessage('mailing.subject.support.reservationPubConfirmDenyNotificationProblems', null, null)
				def messageContent = templateService.renderToString('/mailing/_reservationPubConfirmDenyNotificationProblems', [reservation:reservation])
				mailingService.sendMailToSupport(messageSubject, messageContent)

			}
		}
		else {
			log.warn "La reserva ya habia sido procesada"
			
			
			
		}

		
		processed
	}
	
	
	
	/**
	 * Servicio que realiza la cancelaci???n de una reserva confirmada.
	 * @param reservation datos de la reserva
	 * @param canceled
	 * @return indica si la reserva ha sido o no procesada
	 */
	def processCancelConfirmedReservation(reservation, canceled)
	{
		def processed = false
		
		log.info "Procesando reserva con ID '${reservation?.id}' tras respuesta de cliente..."

		if(reservation.status == Reservation.STATUS_CONFIRMED) {

			if(canceled) {
				log.info "La reserva queda cancelada por el cliente"
				reservation.status = Reservation.STATUS_CANCELLED
				reservation.save(flush:true, failOnError:true)
			}
			

			processed = true


			def sentPubSMS = false
			def sentPubEMail = false
			def sentCustomer = false

			def customer = reservation.customer
			def pub = reservation.pub
			
			//Envio notificaci???n cancelaci???n al pub
			if(Pub.NOTIFICATION_MODE_SMS_AND_EMAIL == pub.reservationNotificationPreferenceMode && pub.reservationPhone
				&& pub.reservationEmail) {
				log.info "El pub prefiere notificacion de reserva (cancelaci???n) mediante SMS a: " + pub.reservationPhone +
				" y email a: " + pub.reservationEmail

				sentPubSMS = sendSmsCancelReservationNotificationToPub(reservation)

				sentPubEMail = sendEmailCancelReservationNotificationToPub(reservation)
				
			}
			else if(Pub.NOTIFICATION_MODE_SMS == pub.reservationNotificationPreferenceMode && pub.reservationPhone) {
				log.info "El pub prefiere notificacion de reserva (cancelaci???n) mediante SMS a: " + pub.reservationPhone

				sentPubSMS = sendSmsCancelReservationNotificationToPub(reservation)

				if(!sentPubSMS && pub.reservationEmail) {
					log.info "Se ha producido un error en el envio del SMS. Se prueba envio de email a:" + pub.reservationEmail
					sentPubEMail = sendEmailCancelReservationNotificationToPub(reservation)
				}
			}
			else if(Pub.NOTIFICATION_MODE_EMAIL == Pub.reservationNotificationPreferenceMode && pub.reservationEmail) {
				log.info "El pub prefiere notificacion de reserva (cancelaci???n) mediante email a: " + pub.reservationEmail

				sentPubEMail = sendEmailCancelReservationNotificationToPub(reservation)

				if(!sentPubEMail && pub.reservationPhone) {
					log.info "Se ha producido un error en el envio del email. Se prueba envio de SMS a:" + pub.reservationPhone
					sentPubSMS = sendSmsCancelReservationNotificationToPub(reservation)
				}
			}
			else {
				log.warn "El pub no tiene bien configurada la preferencia de envio de notificaciones de reserva con movil o email. Se intentara por SMS o Email independientemente de la preferencia."

				if(pub.reservationPhone) {
					log.info "Intentando notificacion de reserva mediante SMS a: " + pub.reservationPhone
					sentPubSMS = sendSmsCancelReservationNotificationToPub(reservation)
				}

				if(!sentPubSMS && pub.reservationEmail) {
					log.info "Se ha producido un error en el envio del SMS. Se prueba envio de email a:" + pub.reservationEmail
					sentPubEMail = sendEmailCancelReservationNotificationToPub(reservation)
				}
			}

			//Envio notificaci???n cancelaci???n al cliente
			if(Customer.NOTIFICATION_MODE_SMS == customer.reservationNotificationPreferenceMode && customer.phone) {
				log.info "El cliente prefiere notificacion de reserva (cancelaci???n) mediante SMS a: " + customer.phone

				sentCustomer = sendSmsCancelReservationNotificationToCustomer(reservation)

				if(!sentCustomer && customer.email) {
					log.info "Se ha producido un error en el envio del SMS. Se prueba envio de email a:" + customer.email
					sentCustomer = sendEmailCancelReservationNotificationToCustomer(reservation)
				}
			}
			else if(Customer.NOTIFICATION_MODE_EMAIL == customer.reservationNotificationPreferenceMode && customer.email) {
				log.info "El cliente prefiere notificacion de reserva (cancelaci???n) mediante email a: " + customer.email

				sentCustomer = sendEmailCancelReservationNotificationToCustomer(reservation)

				if(!sentCustomer && customer.phone) {
					log.info "Se ha producido un error en el envio del email. Se prueba envio de SMS a:" + customer.phone
					sentCustomer = sendSmsCancelReservationNotificationToCustomer(reservation)
				}
			}
			else {
				log.warn "El cliente no tiene bien configurada la preferencia de envio de notificaciones de reserva con movil o email. Se intentara por SMS o Email independientemente de la preferencia."

				if(customer.phone) {
					log.info "Intentando notificacion de reserva mediante SMS a: " + customer.phone
					sentCustomer = sendSmsCancelReservationNotificationToCustomer(reservation)
				}

				if(!sentCustomer && customer.email) {
					log.info "Se ha producido un error en el envio del SMS. Se prueba envio de email a:" + customer.email
					sentCustomer = sendEmailCancelReservationNotificationToCustomer(reservation)
				}
			}

			
			if(!sentPubSMS && !sentPubEMail) {
				log.error "Imposible realizar notificacion al pub."
				def messageSubject = messageSource.getMessage('mailing.subject.support.reservationCancelPubNotificationProblems', null, null)
				def messageContent = templateService.renderToString('/mailing/_reservationCancelPubNotificationProblems', [reservation:reservation])
				mailingService.sendMailToSupport(messageSubject, messageContent)

			}

			if(!sentCustomer) {
				log.error "Imposible realizar notificacion al cliente."
				def messageSubject = messageSource.getMessage('mailing.subject.support.reservationCancelCustomerNotificationProblems', null, null)
				def messageContent = templateService.renderToString('/mailing/_reservationCancelCustomerNotificationProblems', [reservation:reservation])
				mailingService.sendMailToSupport(messageSubject, messageContent)

			}
		}
		else if (reservation.status == Reservation.STATUS_CANCELLED) {
			log.warn "La reserva ya habia sido cancelada"
		}
		else {
			log.warn "La reserva no se puede cancelar, porque no est??? confirmada todavia"
		}

		
		processed
	}
	
	
	
	/**
	 * Metodo privado que envia un SMS de noificacion de reserva confirmada o denegada al cliente
	 * @param reservation datos de la reserva
	 * @return si se ha enviado correctamente
	 */
	def private sendSmsReservationNotificationToCustomer(reservation)
	{
		
		def sent = false
		
		log.info "Enviando SMS al cliente..."
		
		
		def messageContent
		
		if(reservation.status == Reservation.STATUS_CONFIRMED)
		{			
			messageContent = generateSmsContentForCustomerReservationConfirmation(reservation)
		}
		else if(reservation.status == Reservation.STATUS_DENIED)
		{			
			messageContent = generateSmsContentForCustomerReservationDenegation(reservation)
		}
		
		
		log.info "Enviando SMS: '${messageContent}' al telefono ${reservation?.customer?.phone}"
		def sendSmsResponse = smsService.sendSms(messageContent, reservation?.customer?.phoneWithCountryPrefix())
		
		if(!sendSmsResponse?.error)
		{
			log.info "El cliente ha recibido correctamente notificacion de confirmacion."
			
			reservation.notifiedCustomer = true
			reservation.notifiedCustomerMode = Customer.NOTIFICATION_MODE_SMS
			reservation.notifiedCustomerDate = new Date()
			reservation.smsPubNotificationReference = sendSmsResponse.reference
			reservation.save(flush:true, failOnError:true)
			sent = true
		}
		else
		{
			
			log.error "Error enviando SMS al cliente. (Codigo: ${sendSmsResponse.errorCode}, Description: ${sendSmsResponse.errorDescription})"
			sent = false
		}
	
		sent
	}
	
	/**
	 * Metodo privado que envia un SMS de notificacion de reserva no disponible por pub cerrado al cliente
	 * @param reservation datos de la reserva
	 * @return si se ha enviado correctamente
	 */
	def private sendSmsReservationNotAvailableNotificationToCustomer(reservationRequest)
	{
		
		def sent = false
		
		log.info "Enviando SMS al cliente..."
		
		
		def messageContent
		
		
		messageContent = generateSmsContentForCustomerReservationNotAvailable(reservationRequest)
		
		
		log.info "Enviando SMS: '${messageContent}' al telefono ${Country.findByCode(reservationRequest?.customerPhoneCountryIsoCode).dialCode+reservationRequest?.customerPhone}"
		def sendSmsResponse = smsService.sendSms(messageContent, Country.findByCode(reservationRequest?.customerPhoneCountryIsoCode).dialCode+reservationRequest?.customerPhone)
		
		if(!sendSmsResponse?.error)
		{
			log.info "El cliente ha recibido correctamente notificacion de reserva no disponible el d??a solicitado por cierre pub."
			
			//reservation.notifiedCustomer = true
			//reservation.notifiedCustomerMode = Customer.NOTIFICATION_MODE_SMS
			//reservation.notifiedCustomerDate = new Date()
			//reservation.smsPubNotificationReference = sendSmsResponse.reference
			//reservationRequest.save(flush:true, failOnError:true)
			sent = true
		}
		else
		{
			
			log.error "Error enviando SMS al cliente. (Codigo: ${sendSmsResponse.errorCode}, Description: ${sendSmsResponse.errorDescription})"
			sent = false
		}
	
		sent
	}
	
	/**
	 * Metodo privado que envia un SMS de notificacion de cancelaci???n de
	 * reserva al pub
	 * @param reservation datos de la reserva
	 * @return si se ha enviado correctamente
	 */
	def private sendSmsCancelReservationNotificationToPub(reservation)
	{
		
		def sent = false
		
		log.info "Enviando SMS cancelaci???n reserva al pub..."
		
		
		def messageContent
		
		messageContent = generateSmsContentForPubReservationCancel(reservation)
		
		
		log.info "Enviando SMS: '${messageContent}' al telefono ${reservation.pub.reservationPhone}"
		def sendSmsResponse = smsService.sendSms(messageContent, reservation?.pub?.reservationPhoneWithCountryPrefix())
		
		if(!sendSmsResponse?.error)
		{
			log.info "El pub ha recibido correctamente notificacion de cancelaci???n."
			
			reservation.notifiedPub = true
			reservation.notifiedPubMode = Pub.NOTIFICATION_MODE_SMS
			reservation.notifiedPubDate = new Date()
			reservation.smsPubNotificationReference = sendSmsResponse.reference
			reservation.save(flush:true, failOnError:true)
			sent = true
		}
		else
		{
			
			log.error "Error enviando SMS al pub. (Codigo: ${sendSmsResponse.errorCode}, Description: ${sendSmsResponse.errorDescription})"
			sent = false
		}
	
		sent
	}
	
	
	/**
	 * Metodo privado que envia un SMS de notificacion de cancelaci???n de
	 * reserva al cliente
	 * @param reservation datos de la reserva
	 * @return si se ha enviado correctamente
	 */
	def private sendSmsCancelReservationNotificationToCustomer(reservation)
	{
		
		def sent = false
		
		log.info "Enviando SMS cancelaci???n reserva al cliente..."
		
		
		def messageContent
		
		messageContent = generateSmsContentForCustomerReservationCancel(reservation)
		
		
		log.info "Enviando SMS: '${messageContent}' al telefono ${reservation?.customer?.phone}"
		def sendSmsResponse = smsService.sendSms(messageContent, reservation?.customer?.phoneWithCountryPrefix())
		
		if(!sendSmsResponse?.error)
		{
			log.info "El cliente ha recibido correctamente notificacion de cancelaci???n."
			
			reservation.notifiedCustomer = true
			reservation.notifiedCustomerMode = Customer.NOTIFICATION_MODE_SMS
			reservation.notifiedCustomerDate = new Date()
			reservation.smsPubNotificationReference = sendSmsResponse.reference
			reservation.save(flush:true, failOnError:true)
			sent = true
		}
		else
		{
			
			log.error "Error enviando SMS al cliente. (Codigo: ${sendSmsResponse.errorCode}, Description: ${sendSmsResponse.errorDescription})"
			sent = false
		}
	
		sent
	}
	
	
	/**
	 * metodo privado que envia un email al cliente indicando si la reserva ha sido confirmada
	 * o denegada
	 * @param reservation datos de la reserva
	 * @return si ha sido o no enviado
	 */
	def private sendEmailReservationNotificationToCustomer(reservation)
	{
		def sent = false
		
		def customer = reservation?.customer
		
		log.info "Enviando resolucion de reserva a email del cliente: '${customer.email}'..."
		
		
		def messageSubject
		def messageContent
				
		if(reservation.status == Reservation.STATUS_CONFIRMED)
		{			
			messageSubject = messageSource.getMessage('mailing.subject.customerReservationConfirmed', null, null)
			messageContent = templateService.renderToString('/mailing/_customerReservationConfirmed', [reservation:reservation])
		}
		else if(reservation.status == Reservation.STATUS_DENIED)
		{			
			messageSubject = messageSource.getMessage('mailing.subject.customerReservationDenied', null, null)
			messageContent = templateService.renderToString('/mailing/_customerReservationDenied', [reservation:reservation])
		}
		
		
		mailingService.sendMail(customer.email, messageSubject, messageContent)
		
		reservation.notifiedCustomer = true
		reservation.notifiedCustomerMode = Customer.NOTIFICATION_MODE_EMAIL
		reservation.notifiedCustomerDate = new Date()
		reservation.save(flush:true, failOnError:true)
		
		sent = true
		
		sent
	}
	
	
	/**
	 * metodo privado que envia un email al cliente indicando si la reserva ha sido confirmada
	 * o denegada
	 * @param reservation datos de la reserva
	 * @return si ha sido o no enviado
	 */
	def private sendEmailReservationNotAvailableNotificationToCustomer(reservationRequest)
	{
		def sent = false
		
		//def customer = reservation?.customer
		
		log.info "Enviando resolucion de reserva no disponible por cierre del pub ese día a email del cliente: '${reservationRequest.customerEmail}'..."
		
		
		def messageSubject
		def messageContent
				
		
		messageSubject = messageSource.getMessage('mailing.subject.customerReservationNotAvailable', null, null)
		messageContent = templateService.renderToString('/mailing/_customerReservationNotAvailable', [reservationRequest:reservationRequest])
		
		
		mailingService.sendMail(reservationRequest.customerEmail, messageSubject, messageContent)
		
		//reservation.notifiedCustomer = true
		//reservation.notifiedCustomerMode = Customer.NOTIFICATION_MODE_EMAIL
		//reservation.notifiedCustomerDate = new Date()
		//reservation.save(flush:true, failOnError:true)
		
		sent = true
		
		sent
	}
	
	
	/**
	 * metodo privado que envia un email al pub indicando que reserva ha sido confirmada
	 * o denegada
	 * @param reservation datos de la reserva
	 * @return si ha sido o no enviado
	 */
	def private sendEmailReservationConfirmDenyNotificationToPub(reservation)
	{
		def sent = false
		
		def pub = reservation?.pub
		
		log.info "Enviando resolucion de reserva a email del pub: '${pub.reservationEmail}'..."
		
		
		def messageSubject
		def messageContent
				
		if(reservation.status == Reservation.STATUS_CONFIRMED)
		{
			messageSubject = messageSource.getMessage('mailing.subject.pubReservationConfirmed', null, null)
			messageContent = templateService.renderToString('/mailing/_pubReservationConfirmed', [reservation:reservation])
		}
		else if(reservation.status == Reservation.STATUS_DENIED)
		{
			messageSubject = messageSource.getMessage('mailing.subject.pubReservationDenied', null, null)
			messageContent = templateService.renderToString('/mailing/_pubReservationDenied', [reservation:reservation])
		}
		
		
		mailingService.sendMail(pub.reservationEmail, messageSubject, messageContent)
		
		reservation.notifiedPub = true
		reservation.notifiedPubMode = Pub.NOTIFICATION_MODE_EMAIL
		reservation.notifiedPubDate = new Date()
		reservation.save(flush:true, failOnError:true)
		
		sent = true
		
		sent
	}
	
	
	/**
	 * metodo privado que envia un email al pub indicando si
	 * un reserva concreta ha sido cancelada
	 * @param reservation datos de la reserva
	 * @return si ha sido o no enviado
	 */
	def private sendEmailCancelReservationNotificationToPub(reservation)
	{
		def sent = false
		
		def pub = reservation?.pub
		
		log.info "Enviando cancelación de reserva a email del pub: '${pub.reservationEmail}'..."
		
		
		def messageSubject
		def messageContent
		
		
		messageSubject = messageSource.getMessage('mailing.subject.customerReservationCanceled', null, null)
		messageContent = templateService.renderToString('/mailing/_pubReservationCanceled', [reservation:reservation])
		
		
		mailingService.sendMail(pub.reservationEmail, messageSubject, messageContent)
		
		reservation.notifiedPub = true
		reservation.notifiedPubMode = Pub.NOTIFICATION_MODE_EMAIL
		reservation.notifiedPubDate = new Date()
		reservation.save(flush:true, failOnError:true)
		
		sent = true
		
		sent
	}
	
	
	/**
	 * metodo privado que envia un email al cliente indicando si
	 * la reserva ha sido cancelada
	 * @param reservation datos de la reserva
	 * @return si ha sido o no enviado
	 */
	def private sendEmailCancelReservationNotificationToCustomer(reservation)
	{
		def sent = false
		
		def customer = reservation?.customer
		
		log.info "Enviando cancelaci???n de reserva a email del cliente: '${customer.email}'..."
		
		
		def messageSubject
		def messageContent
				
		messageSubject = messageSource.getMessage('mailing.subject.customerReservationCanceled', null, null)
		messageContent = templateService.renderToString('/mailing/_customerReservationCanceled', [reservation:reservation])
		
		
		mailingService.sendMail(customer.email, messageSubject, messageContent)
		
		reservation.notifiedCustomer = true
		reservation.notifiedCustomerMode = Customer.NOTIFICATION_MODE_EMAIL
		reservation.notifiedCustomerDate = new Date()
		reservation.save(flush:true, failOnError:true)
		
		sent = true
		
		sent
	}
	
	
	
	
	/**
	 * Metodo privado que genera el contenido del SMS para el pub indicando los datos de la
	 * solicitud de reserva
	 * @param reservation datos de la reserva
	 * @return texto generado
	 */
	def private generateSmsContentForPubRequest(reservation)
	{
		log.info "Generando contenido de SMS para peticion de reserva a pub. Reserva con ID: " + reservation?.id
		
		//def adults = StringUtils.abbreviate( "${reservation.adults}",2)
		def adults = reservation.adults
		//def children = StringUtils.abbreviate( "${reservation.children}",2)
		def children = reservation.children
		def phone = StringUtils.abbreviate( "${reservation.customer.phone}",9)
		def name = StringUtils.abbreviate( "${reservation.customer.name}",11)
		
		def smsContent = "Solicitud de Reserva. Fecha: ${reservation.reservationDate?.format('dd/MM/yyyy HH:mm')}. Adultos: ${adults}, Menores: ${children}, Telefono: ${phone}, Nombre: ${name}."
		
		if(reservation.comments)
			smsContent = smsContent + " Ver comentarios en Email."
		
		smsContent = smsContent + " Responda SI o NO."
		
		log.info "Contenido generado: " + smsContent
		
		smsContent = StringUtils.abbreviate( "${smsContent}",160)
		
		log.info "Contenido truncado: " + smsContent
		
		smsContent
	}
	
	/**
	 * Metodo privado que genera el contenido del SMS para el cliente indicando que su reserva
	 * ha sido confirmada
	 * @param reservation datos de la reserva
	 * @return texto generado
	 */
	def private generateSmsContentForCustomerReservationConfirmation(reservation)
	{
		log.info "Generando contenido de SMS para notificacion de confirmacion a usuario. Reserva con ID: " + reservation?.id
		
		def smsContent = "Tu reserva ha sido CONFIRMADA en ${reservation.pub?.name} el ${reservation.reservationDate?.format('dd/MM/yyyy HH:mm')}. Gracias!. Para cancelar la reserva envie Cancelar."
		
		log.info "Contenido generado: " + smsContent
		
		smsContent = StringUtils.abbreviate( "${smsContent}",160)
		
		log.info "Contenido truncado: " + smsContent
		
		smsContent
	}
	
	
	/**
	 * Metodo privado que genera el contenido del SMS para el cliente indicando que su reserva
	 * ha sido denegada
	 * @param reservation datos de la reserva
	 * @return texto generado
	 */
	def private generateSmsContentForCustomerReservationDenegation(reservation)
	{
		log.info "Generando contenido de SMS para notificacion de confirmacion a usuario. Reserva con ID: " + reservation?.id
		
		def smsContent = "Tu reserva ha sido DENEGADA en  ${reservation.pub?.name} el ${reservation.reservationDate?.format('dd/MM/yyyy HH:mm')}. Disculpa las molestias."
		
		log.info "Contenido generado: " + smsContent
		
		smsContent = StringUtils.abbreviate( "${smsContent}",160)
		
		log.info "Contenido truncado: " + smsContent
		
		smsContent
	}
	
	
	/**
	 * Metodo privado que genera el contenido del SMS para el cliente indicando que su reserva
	 * no se ha podido realizar por cierre del pub ese d??a
	 * @param reservation datos de la reserva
	 * @return texto generado
	 */
	def private generateSmsContentForCustomerReservationNotAvailable(reservationRequest)
	{
		log.info "Generando contenido de SMS para notificacion de reserva no disponible por cierre del pub ese d??a a usuario."
		
		def smsContent = "Tu reserva en ${Pub.findById(reservationRequest.pubId).name} el ${reservationRequest.reservationDate?.format('dd/MM/yyyy HH:mm')}, no se ha realizado por cierre del pub ese dia. Contacta si lo deseas en ${Pub.findById(reservationRequest.pubId).telephone}. Gracias."
		
		log.info "Contenido generado: " + smsContent
		
		smsContent = StringUtils.abbreviate( "${smsContent}",160)
		
		log.info "Contenido truncado: " + smsContent
		
		smsContent
	}
	
	
	/**
	 * Metodo privado que genera el contenido del SMS para el pub indicando
	 * que una reserva concreta, ha sido cancelada
	 * @param reservation datos de la reserva
	 * @return texto generado
	 */
	def private generateSmsContentForPubReservationCancel(reservation)
	{
		log.info "Generando contenido de SMS para notificacion de cancelaci???n a pub. Reserva con ID: " + reservation?.id
		
		def smsContent = "La reserva a nombre de ${reservation.customer?.name} el ${reservation.reservationDate?.format('dd/MM/yyyy HH:mm')} ha sido CANCELADA."
		
		log.info "Contenido generado: " + smsContent
		
		smsContent = StringUtils.abbreviate( "${smsContent}",160)
		
		log.info "Contenido truncado: " + smsContent
		
		smsContent
	}
	
	/**
	 * Metodo privado que genera el contenido del SMS para el cliente indicando que su reserva
	 * ha sido cancelada
	 * @param reservation datos de la reserva
	 * @return texto generado
	 */
	def private generateSmsContentForCustomerReservationCancel(reservation)
	{
		log.info "Generando contenido de SMS para notificacion de cancelaci???n a usuario. Reserva con ID: " + reservation?.id
		
		def smsContent = "Tu reserva en ${reservation.pub?.name} el ${reservation.reservationDate?.format('dd/MM/yyyy HH:mm')} ha sido CANCELADA correctamente."
		
		log.info "Contenido generado: " + smsContent
		
		smsContent = StringUtils.abbreviate( "${smsContent}",160)
		
		log.info "Contenido truncado: " + smsContent
		
		smsContent
	}
	
	
	/**
	 * Metodo privado que obtiene el indice del ultimo SMS procesado de la bandeja de entrada.
	 * @return ultimo SMS procesado
	 */
	def private getInboxSmsLastProcessed()
	{		
		def lastProcessed = redisService.lastSmsProcessed
		log.info "Obtenido ultimo SMS procesado: " + lastProcessed
		
		lastProcessed
	}
	
	
	/**
	 * Metodo privado que marca un SMS como procesado
	 * @param smsId ID del sms
	 */
	def private inboxSmsProcessed(smsId)
	{
		log.info "Estableciendo ultimo SMS procesado: " + smsId
		
		def lastProcessed = getInboxSmsLastProcessed()
		
		log.info "Ultimo SMS procesado registrado: " + lastProcessed
		
		lastProcessed = [lastProcessed,smsId].max()  
		
		log.info "Registrando ultiom SMS procesado: " + lastProcessed
		
		redisService.lastSmsProcessed = lastProcessed
	}
	
	
	/**
	 * Servicio que procesa las solicitudes de reserva que han sobrepasado el tiempo de respuesta 
	 * sin confirmacion ni denegacion por parte del pub
	 */
	def processExpiredReservations()
	{	
		def maxDate
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		Date now = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH")
		
		SimpleDateFormat dateFormatDay = new SimpleDateFormat("u")
		
		def day = Integer.parseInt(dateFormatDay.format(now))
		def hour = Integer.parseInt(dateFormat.format(now));
		
		if (hour>9 && hour<22) {
		
			use(groovy.time.TimeCategory) {
							
				maxDate = new Date() - Reservation.EXPIRED_MINUTES.minutes
			}
			
			
			def notProcessedReservations = Reservation.createCriteria().list(){
				
				or
				{
					eq("status", Reservation.STATUS_PENDING)
					eq("status", Reservation.STATUS_IN_PROGRESS)
				}
				
				lt("createDate", maxDate)
			}
			
			notProcessedReservations.each{
				
				if (( ( (!it.pub.LunesM && (hour>9 && hour<19)) || (!it.pub.LunesN && (hour>16 && hour<22)) ) && day == 1) || 
					( ( (!it.pub.MartesM && (hour>9 && hour<19)) || (!it.pub.MartesN && (hour>16 && hour<22)) ) && day == 2) ||
					( ( (!it.pub.MiercolesM && (hour>9 && hour<19)) || (!it.pub.MiercolesN && (hour>16 && hour<22)) ) && day == 3) ||
					( ( (!it.pub.JuevesM && (hour>9 && hour<19)) || (!it.pub.JuevesN && (hour>16 && hour<22)) ) && day == 4) ||
					( ( (!it.pub.ViernesM && (hour>9 && hour<19)) || (!it.pub.ViernesN && (hour>16 && hour<22)) ) && day == 5) ||
					( ( (!it.pub.SabadoM && (hour>9 && hour<19)) || (!it.pub.SabadoN && (hour>16 && hour<22)) ) && day == 6) ||
					( ( (!it.pub.DomingoM && (hour>9 && hour<19)) || (!it.pub.DomingoN && (hour>16 && hour<22)) ) && day == 7) ) {
					
					log.info "No se marca como expirada la reserva con id: " + it.id + " porque el pub " + it.pub.name + " est?? cerrado y no puede responder."
					
				} else {
				
					processExpiredReservation(it)
				}
				
			}
			
			log.info "Se han marcado todos las reservas expiradas."
			
		} else {
			log.info "No se marcan como expiradas las reservas porque la hora actual es entre las 23:00 y las 11:00."
		}
		
	}
	
	/**
	 * Servicio que procesa una solicitud de reserva que ha sobrepasado el tiempo de respuesta 
	 * sin confirmacion ni denegacion por parte del pub
	 * @param reservation datos de la reserva
	 */
	def processExpiredReservation(reservation)
	{
		def processed = false
		
		log.info "Procesando reserva con ID '${reservation?.id}' como excedida..."
		
		def maxDate
		
		def tooOldReservationsDate
		
		use(groovy.time.TimeCategory) {
						
			maxDate = new Date() - Reservation.EXPIRED_MINUTES.minutes
			tooOldReservationsDate = new Date() - (Reservation.EXPIRED_MINUTES*3).minutes
		}
		

		if((Reservation.STATUS_IN_PROGRESS == reservation.status || Reservation.STATUS_PENDING == reservation.status) && reservation.createDate < maxDate) {
			
			log.info "La reserva se marca como excedida por el pub"
			reservation.status = Reservation.STATUS_EXPIRED
			reservation.save(flush:true, failOnError:true)


			processed = true
			
			if(reservation.createDate > tooOldReservationsDate)
			{
				log.info "Se notifica al cliente..."
				
				def sent = false

				def customer = reservation.customer

				if(Customer.NOTIFICATION_MODE_SMS == customer.reservationNotificationPreferenceMode && customer.phone) {
					log.info "El cliente prefiere notificacion de reserva mediante SMS a: " + customer.phone


					sent = sendSmsReservationExpirationNotificationToCustomer(reservation)

					if(!sent && customer.email) {
						log.info "Se ha producido un error en el envio del SMS. Se prueba envio de email a:" + customer.email
						sent = sendEmailReservationExpirationNotificationToCustomer(reservation)
					}
				}
				else if(Customer.NOTIFICATION_MODE_EMAIL == customer.reservationNotificationPreferenceMode && customer.email) {
					log.info "El cliente prefiere notificacion de reserva mediante email a: " + customer.email

					sent = sendEmailReservationExpirationNotificationToCustomer(reservation)

					if(!sent && customer.phone) {
						log.info "Se ha producido un error en el envio del email. Se prueba envio de SMS a:" + customer.phone
						sent = sendSmsReservationExpirationNotificationToCustomer(reservation)
					}
				}
				else {
					log.warn "El cliente no tiene bien configurada la preferencia de envio de notificaciones de reserva con movil o email. Se intentara por SMS o Email independientemente de la preferencia."

					if(customer.phone) {
						log.info "Intentando notificacion de reserva mediante SMS a: " + customer.phone
						sent = sendSmsReservationExpirationNotificationToCustomer(reservation)
					}

					if(!sent && customer.email) {
						log.info "Se ha producido un error en el envio del SMS. Se prueba envio de email a:" + customer.email
						sent = sendEmailReservationExpirationNotificationToCustomer(reservation)
					}
				}


				if(!sent) {
					log.error "Imposible realizar notificacion al cliente."
					def messageSubject = messageSource.getMessage('mailing.subject.support.reservationCustomerNotificationProblems', null, null)
					def messageContent = templateService.renderToString('/mailing/_reservationCustomerNotificationProblems', [reservation:reservation])
					mailingService.sendMailToSupport(messageSubject, messageContent)
				}
			}
			else
			{
				log.info "No se notifica al cliente porque hace demasiado tiempo que realizo la reserva..."
			}


			
		}
		else 
		{
			log.warn "La reserva ya ha sido procesada o todavia no ha excedido"
		}

		
		processed
	}
	
	
	
	/**
	 * Metodo privado que envia un SMS al cliente indicando que su solicitud de reserva
	 * ha caducado sin respuesta.
	 * @param reservation datos de la reserva
	 * @return si se ha enviado correctamente o no
	 */
	def private sendSmsReservationExpirationNotificationToCustomer(reservation)
	{
		
		def sent = false
		
		log.info "Enviando SMS al cliente indicando expiracion..."
		
		
		def messageContent = generateSmsContentForCustomerReservationExpiration(reservation)
		
		
		log.info "Enviando SMS: '${messageContent}' al telefono ${reservation?.customer?.phone}"
		def sendSmsResponse = smsService.sendSms(messageContent, reservation?.customer?.phoneWithCountryPrefix())
		
		if(!sendSmsResponse?.error)
		{
			log.info "El cliente ha recibido correctamente notificacion de confirmacion."
			
			reservation.notifiedCustomer = true
			reservation.notifiedCustomerMode = Customer.NOTIFICATION_MODE_SMS
			reservation.notifiedCustomerDate = new Date()
			reservation.save(flush:true, failOnError:true)
			sent = true
		}
		else
		{
			
			log.error "Error enviando SMS al cliente. (Codigo: ${sendSmsResponse.errorCode}, Description: ${sendSmsResponse.errorDescription})"
			sent = false
		}
	
		sent
	}
	
	/**
	 * Metodo privado que genera el contenido de SMS al cliente indicando la expiracion de la 
	 * solicitud de reserva
	 * @param reservation datos de la reserva
	 * @return texto generado
	 */
	def private generateSmsContentForCustomerReservationExpiration(reservation)
	{
		log.info "Generando contenido de SMS para notificacion de expiration a usuario. Reserva con ID: " + reservation?.id
		
		def smsContent = "Tu solicitud de reserva NO HA OBTENIDO RESPUESTA de ${reservation.pub?.name}. Llama al ${reservation.pub?.telephone} para reservar por telefono. Disculpa las molestias."
		
		log.info "Contenido generado: " + smsContent
		
		smsContent = StringUtils.abbreviate( "${smsContent}",160)
		
		log.info "Contenido truncado: " + smsContent
		
		smsContent
	}
	
	
	
	/**
	 * Metodo privado que envia un email al cliente indicando la expiracion de su solicutud de reserva
	 * sin tener respuesta del pub
	 * @param reservation datos de la reserva
	 */
	def private sendEmailReservationExpirationNotificationToCustomer(reservation)
	{
		def sent = false
		
		def customer = reservation?.customer
		
		log.info "Enviando notificacion de expiracion de reserva a email del cliente: '${customer.email}'..."
		
		
		def messageSubject = messageSource.getMessage('mailing.subject.customerReservationExpired', null, null)
		def	messageContent = templateService.renderToString('/mailing/_customerReservationExpired', [reservation:reservation])

		
		
		mailingService.sendMail(customer.email, messageSubject, messageContent)
		
		reservation.notifiedCustomer = true
		reservation.notifiedCustomerMode = Customer.NOTIFICATION_MODE_EMAIL
		reservation.notifiedCustomerDate = new Date()
		reservation.save(flush:true, failOnError:true)
		
		sent = true
		
		sent
	}
	
	
}
