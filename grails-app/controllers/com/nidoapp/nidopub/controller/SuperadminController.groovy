package com.nidoapp.nidopub.controller

import com.nidoapp.nidopub.domain.Association
import com.nidoapp.nidopub.domain.PubFeature
import com.nidoapp.nidopub.domain.PubPhoto
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.Carte
import com.nidoapp.nidopub.domain.CarteSection
import com.nidoapp.nidopub.domain.Product
import com.nidoapp.nidopub.domain.api.ApiKey
import com.nidoapp.nidopub.domain.i18n.I18nLanguage
import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;

import grails.plugins.springsecurity.Secured

import grails.converters.JSON
import groovy.json.JsonSlurper

/**
 * Controlador que atiende peticiones de superadministracion
 * @author Developer
 *
 */
@Secured([Role.ROLE_SUPERADMIN])
class SuperadminController {
	
	def springSecurityService
	def databaseIntegrationService
	def mailingService
	def templateService
	def messageSource
	def seoFriendlyUrlsService
	def grailsApplication
	
	

    def index = { 
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def pubs = Pub.list()		
		
		[user:user, pubs:pubs]
	}
	
	def pubDetail = {
		
		def pubId = params.id
		
		def pub = Pub.get(pubId)
		
		[pub:pub]
	}
	
	def carteDetail = {
		
		def carteId = params.id
		
		def carte = Carte.get(carteId)
		
		[carte:carte]
	}
	
	def carteSectionDetail = {
		
		def carteSectionId = params.id
		
		def carteSection = CarteSection.get(carteSectionId)
		
		[carteSection:carteSection]
	}
	
	def productDetail = {
		
		def productId = params.id
		
		def product = Product.get(productId)
		
		[product:product]
	}
	
	/**
	 * Metodo realiza la importacion de los datos de la base de datos de Horeca
	 */
	def importHorecaLegacyData = {
		//databaseIntegrationService.importHorecaLegacyData()
		
	}
	
	/**
	 * Metodo que muestra todas las asociaciones de pubs
	 */
	def getAssociations = {
		
		def response = [:]
		response.error = false
		
		response.associations = Association.list()
		
		
		render response as JSON

	
	}
	
	/**
	 * Metodo que muestra todos los idiomas dados de alta
	 */
	def getI18nLanguages = {
		
		
		def response = [:]
		response.error = false
		
		response.languages = I18nLanguage.list()
		
		
		render response as JSON

	
	}
	
	/**
	 * Metodo de ejemplo para incluir idiomas
	 */
	def insertChinese = {
		
		
		def response = [:]
		response.error = false
		
		new I18nLanguage(isoCode: "cn", language:'Chinese').save(flush: true)
		
		response.languages = I18nLanguage.list()
		
		
		render response as JSON

	
	}
	
	/**
	 * Metodo que muestra todas las asociaciones de pubs
	 */
	def getAssociationApiKey = {
		
		def response = [:]
		response.error = false
		
		def associationId = params.id
		
		/*
		 * Validaciones:
		 */
		log.info "Obteniendo Asociacion con ID: " + associationId
		def association = Association.get(associationId)
		
		if(!response.error && !association)
		{
			response.error = true
			response.errorCode = "ASSOCIATION_NOT_FOUND"
			log.warn "Asociacion con ID '${associationId}' no encontrado."
		}
		
		
		if(!response.error)
		{
			def apiKey = ApiKey.findByAssociation(association)
			response.apiKey = apiKey
		}		
	
		
		render response as JSON
	
	}
	
	/**
	 * Metodo que establece una foto principal a aquellos pubs que no tengan
	 */
	def setMainPhotos = {
		
		def response = [:]
		response.pubsProcessed = 0
		response.pubsUpdated = 0
		
		
		def pubs = Pub.list()
		
		
		pubs.each{ pub ->
			def photos = pub.photos
			
			def mainPhoto = PubPhoto.findByPubAndMain(pub, true)
			if(!mainPhoto && photos)
			{
				mainPhoto = photos.first()
				mainPhoto.main = true
				mainPhoto.save(flush:true, failOnError:true)
				
				response.pubsUpdated++
			}
			
			response.pubsProcessed++
		}
		
		render response as JSON
	}
	
	/**
	 * metodo que permite ver el usuario propietario de un pub
	 */
	def showPubOwner = {
		
		def response = [:]
		response.error = false
		
		def pubId = params.id		
		
		log.info "Consultando usuario asociado a pub con ID: " + pubId
		
		
		/*
		 * Validaciones:
		 */
		log.info "Obteniendo Pub con ID: " +pubId
		def pub = Pub.get(pubId)
		
		if(!response.error && !pub)
		{
			response.error = true
			response.errorCode = "PUB_NOT_FOUND"
			log.warn "Pub con ID '${pubId}' no encontrado."
		}		
		
		
		if(!response.error)
		{
			def owner = pub.owner		

			
			response.user = owner
		}
		
		render response as JSON
	
	}
	
	
	/**
	 * metodo que permite establecer credenciales de acceso al propietario de un pub
	 */
	def setPubAccessCredentials = {
		
		def response = [:]
		response.error = false
		
		def pubId = params.pub
		
		def username = params.username
		def password = params.password
		
		log.info "Estableciendo usuario y password para usuario asociado a pub con ID: " + pubId
		
		
		/*
		 * Validaciones:
		 */
		log.info "Obteniendo Pub con ID: " + pubId
		def pub = Pub.get(pubId)
		
		if(!response.error && !pub)
		{
			response.error = true
			response.errorCode = "PUB_NOT_FOUND"
			log.warn "Pub con ID '${pubId}' no encontrado."
		}
		
		if(!response.error && !username && !password)
		{
			response.error = true
			response.errorCode = "USERNAME_OR_PASSWORD_NECCESARY"
			log.warn "Se requiere nombre de usuario o password."
		}
		
		if(!response.error && username)
		{
			def user = User.findByUsername(username)
			if(user && user != pub.owner)
			{
				response.error = true
				response.errorCode = "EXISTING_USERNAME"
				log.warn "El nombre de usuario ya existe"
			}
			
		}
		
		if(!response.error && password && password.length() < 4)
		{
			response.error = true
			response.errorCode = "PASSWORD_TOO_SHORT"
			log.warn "El password es demasiado corto."
		}
		
		if(!response.error)
		{
			def owner = pub.owner
			
			if(username)
				owner.username = username
				
			if(password)
				owner.password = password			
				
			
			owner.save(flush:true, failOnError:true)
			
			response.updatedUser = owner
		}
		
		render response as JSON
	
	}
	
	
	
	
	/**
	 * Metodo que permite establecer codigos a las caracteristicas
	 */
	def setPubFeatureCodes = {
		
		log.info "Estableciendo codigos de caraceristicas de pub que no tengan..."
		
		def response = [:]
		response.modified = 0
		response.errors = 0
		response.featuresToReview = []
		
		
		def features = PubFeature.list()
		
		
		features.each{ feature ->
			
			log.info "Feature:" + feature.properties
	
			
			try{				
				
				
	
					feature.code = seoFriendlyUrlsService.getFriendlyUrl(feature.name)
					if(feature.code?.startsWith("indica-que-"))
					{
						feature.code = feature.code.substring(11)
					}
					else if(feature.code?.startsWith("indica-"))
					{
						feature.code = feature.code.substring(7)
					}
					
					feature.save(flush:true, failOnError:true)
					
					response.modified++

				
				
				
			}
			catch(Exception e)
			{
				log.error "Error estableciendo codigo a caracteristica de email a '${feature.name}' (${feature?.id}): " + e
				response.errors++
				response.featuresToReview << feature
			}
		}
		
		render response as JSON
	}
	
	
	/**
	 * Metodo que envia notificacion a todos los pubs para que establezcan la configuracion de reservas
	 */
	/*def notifyConfigurationSettingsToHorecaRestaurants = {
		
		log.info "Enviando notificacion de establecimiento de configuracion de reservas a restaurantes..."
		
		def pubId = params.id
		
		def response = [:]
		response.notified = 0
		response.errors = 0
		response.pubsToReview = []
		
		
		def pubs
		
		if(pubId)
		{
			pubs = Pub.findAllById(pubId)
		}
		else
		{
			pubs = Pub.list()
		}	
	
		
		pubs.each{ pub ->
			
			def emailTo = pub?.owner?.username
			
			try{
				
				
				
				def messageSubject = messageSource.getMessage('mailing.subject.pubReservationSettings', [pub?.name].toArray(), null)
				
				def messageContent = templateService.renderToString('/mailing/_pubReservationSettings', [pub: pub])
				
				mailingService.sendMail(emailTo, messageSubject, messageContent)
				
				response.notified++
				
			}
			catch(Exception e)
			{
				log.error "Error enviando de email a '${emailTo}' (Pub ${pub?.name}): " + e
				response.errors++
				response.pubsToReview << pub
			}	
		}
		
		render response as JSON
	}*/
	
	
	

	
	/**
	 * Metodo que muestra los limites de envio externos
	 */
	def showExternalSendingLimits = {
		
		log.info "Mostrando limites a envios externos (emails, sms, etc.)..."
		
		def response = [:]
		
		
		response.smsLimits = grailsApplication.config.sms.limits
		if(response.smsLimits)
			response.smsRecipients = grailsApplication.config.sms.recipients
		
		response.mailLimits = grailsApplication.config.mail.limits
		if(response.mailLimits)
			response.mailRecipients = grailsApplication.config.mail.recipients
		
	
		
		render response as JSON
	}
	
	/**
	 * Metodo que habilita los limites de envio externos
	 */
	def enableExternalSendingLimits = {
		
		log.info "Estableciendo limites a envios externos (emails, sms, etc.)..."
		
		def response = [:]
		
		
		grailsApplication.config.sms.limits = true
		response.smsLimits = grailsApplication.config.sms.limits
		response.smsRecipients = grailsApplication.config.sms.recipients
		
		grailsApplication.config.mail.limits = true
		response.mailLimits = grailsApplication.config.mail.limits
		response.mailRecipients = grailsApplication.config.mail.recipients
		
	
		
		render response as JSON
	}
	
	/**
	 * Metodo que inhabilita los limites de envio externos
	 */
	def disableExternalSendingLimits = {
		
		log.info "Eliminando limites a envios externos (emails, sms, etc.)..."
		
		def response = [:]
		
		
		grailsApplication.config.sms.limits = false
		response.smsLimits = grailsApplication.config.sms.limits
		//response.smsRecipients = grailsApplication.config.sms.recipients
		
		grailsApplication.config.mail.limits = false
		response.mailLimits = grailsApplication.config.mail.limits
		//response.mailRecipients = grailsApplication.config.mail.recipients
		
	
		
		render response as JSON
	}
	
	/**
	 * Metodo que cambia el username de un ususario
	 */
	def changeUserUsername = {
		
		def response = [:]
		response.error = false
		
		def username = params.current
		def newUsername = params.new
		
		log.info "Cambiando username de usuario '${username}' por '${newUsername}'"
		
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && (!username || !newUsername))
		{
			response.error = true
			response.errorCode = "MANDATORY_CURRENT_AND_NEW_USERNAME"
		}
		
		def user = User.findByUsername(username)
		if(!response.error && !user)
		{
			response.error = true
			response.errorCode = "USER_NOT_FOUND"
		}
		
		def user2 = User.findByUsername(newUsername)
		if(!response.error && (user2 && user2 != user))
		{
			response.error = true
			response.errorCode = "NEW_USERNAME_ALREADY_EXISTS"
		}
		
		
		if(!response.error)
		{
			
			if(username != newUsername)
			{
				user.username = newUsername
				user.save(flush:true, failOnError:true)				
			}

			response.previous = username
			response.current = user.username
			response.details = user

		}

		
	
		
		render response as JSON
	}
	
	
	/**
	 * Metodo que establece el modo de notificacion de todos pubs a email y sms 
	 */
	/*def setPubNotificationModeSmsAndEmail = {
		
		log.info "Estableciendo modo de notificacion a todos los pubs por sms y email..."
		
		def response = [:]
		response.modified = 0
		response.errors = 0
		response.pubsToReview = []
		
		
		def pubs = Pub.list()
		
		
		pubs.each{ pub ->
			
			log.info "Pub:" + pub.name
	
			
			try{
				
				
	
					pub.reservationNotificationPreferenceMode = Pub.NOTIFICATION_MODE_SMS_AND_EMAIL					
					
					pub.save(flush:true, failOnError:true)										
					response.modified++

				
				
				
			}
			catch(Exception e)
			{
				log.error "Error estableciendo modo de notificacion a pub '${pub.name}' (${pub?.id}): " + e
				response.errors++
				response.pubsToReview << pub
			}
		}
		
		render response as JSON
	}*/
	
}
