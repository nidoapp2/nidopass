package com.nidoapp.nidopub.controller.user

import com.nidoapp.nidopub.domain.user.PasswordChange
import com.nidoapp.nidopub.domain.user.User

/**
 * Controlador que atiende peticiones de cambio de password
 * @author Developer
 *
 */
class PasswordChangeController {
	
	def springSecurityService
	def mailingService
	def messageSource
	def templateService
	
	

	/**
	 * Send password restore email
	 */
	def sendEmailToRestorePassword = { SendEmailToRestorePasswordCommand cmd ->

		if(cmd.validate())
		{
			def user = User.findByUsername(cmd.email)
			if(user)
			{
				def passwordChange = new PasswordChange()
				passwordChange.createDate = new Date()

				use(groovy.time.TimeCategory) {
									   
					passwordChange.expirationDate = passwordChange.createDate + 1.hours
				}
				
				passwordChange.user = user

				passwordChange.save(flush:true, failOnError:true)
				
				//TODO: Enviar email
				log.info "Enviando link de cambio de password a email: '${cmd.email}'..."
				def messageSubject = messageSource.getMessage('mailing.subject.sendChangePassword', null, null)
				def messageContent = templateService.renderToString('/mailing/_sendChangePassword', [user:user, passwordChange: passwordChange])
				//def messageContent = templateService.renderToString('/mailing/_test', [date:new Date()])
				log.debug "messageContent: '${messageContent}'" 
				
				
				mailingService.sendMail(cmd.email, messageSubject, messageContent)
	
				//render view: 'restorePasswordRequestConfirmation', model: [cmd: cmd]
			}
			else
			{
				log.warn "No existe usuario con email: " + cmd.email
				//render view: 'showRestorePasswordRequest', model: [cmd: cmd]
			}
			
		}
		else
		{
			log.warn "Los datos no validan: " + cmd.errors
			//render view: 'showRestorePasswordRequest', model: [cmd: cmd]
		}
		
		render view: 'restorePasswordRequestConfirmation', model: [cmd: cmd]

	}


	/**
	 * Show password restore form
	 */
	def showRestorePassword = {

		log.info "Mostrando formulario de restauracion de password para clave de cambio: " + params.id

		def passwordChange = PasswordChange.findByUrlIdentifier(params.id)

		if(passwordChange)
		{
			log.info "Recuperada peticion de cambio, con ID: " + passwordChange.id
			log.info "Usuario asociado: " + passwordChange.user?.username
			
			if(passwordChange.expirationDate > new Date())
			{
				def cmd = new RestorePasswordCommand()
				cmd.urlIdentifier = passwordChange.urlIdentifier
				[cmd:cmd]
			}
			else
			{
				log.warn "La peticion de cambio caduco el: " + passwordChange.expirationDate
				render view: 'restorePasswordRequestExpired'
			}
			
		}
		else
		{
			log.warn "No existe peticion de cambio con URL id: " + params.id
			response.sendError(404)
		}
	}


	def restorePassword = { RestorePasswordCommand cmd ->

		log.info "Restaurando password para peticion de cambio, con ID URL: " + cmd.urlIdentifier

		if(cmd.validate())
		{
			def passwordChange = PasswordChange.findByUrlIdentifier(cmd.urlIdentifier)

			if(passwordChange)
			{
				log.info "Recuperada peticion de cambio, con ID: " + passwordChange.id
				log.info "Usuario asociado: " + passwordChange.user?.username
				
				if(passwordChange.expirationDate > new Date())
				{
					def user = passwordChange.user
					user.password = cmd.password
					user.save(flush:true, failOnError:true)
					render view: 'restorePasswordConfirmation'
				}
				else
				{
					log.warn "La peticion de cambio caduco el: " + passwordChange.expirationDate
					render view: 'restorePasswordRequestExpired'
				}
				
			}
			else
			{
				log.warn "No existe peticion de cambio con URL id: " + cmd.urlIdentifier
				response.sendError(404)
			}
		}
		else
		{
			log.warn "Los datos no validan: " + cmd.errors
			render view: 'showRestorePassword', model: [cmd: cmd]
		}
	}
	
	

   
}


class SendEmailToRestorePasswordCommand{
	String email

	static constraints = {
		
		email blank:false, email:true
		/*
		email(blank:false, email:true, validator: { val, obj ->
			User.findByUsername(val) != null
		})
		*/

	}
}

class RestorePasswordCommand{
	String password
	String rpassword
	String urlIdentifier

	static constraints = {
		password(blank:false, size: 5..15)
		rpassword(blank:false, validator: {
		   val, obj ->
			  obj.properties['password'] == val
		})

	}
}