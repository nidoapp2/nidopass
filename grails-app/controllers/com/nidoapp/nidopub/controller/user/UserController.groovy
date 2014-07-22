package com.nidoapp.nidopub.controller.user

import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured;
import grails.converters.*;

import com.nidoapp.nidopub.domain.Association;
import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.user.UserRole;

class UserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def springSecurityService
	
	def imageRepositoryService
	
    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def create() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		if(UserRole.findByUser(user).role.authority != Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SuperAdmin"
			response.sendError(404)
		}
		
        [userInstance: new User(params), user:user]
    }

    def save() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		params.enabled = true;
        def userInstance = new User(params)
		
		if(UserRole.findByUser(user).role.authority != Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SuperAdmin"
			response.sendError(404)
		}
		
        if (!userInstance.save(flush: true)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }
		
		Role role = Role.findById(params.role.id)		
		new UserRole(user: userInstance, role: role).save(flush: true)

        //flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
		flash.message = message(code: 'Registro correcto, ya se puede acceder con los datos introducidos.')
        
		render(view: "createCorrectMessage", model:[user:user])
		
		//redirect(uri: "/")
    }

    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def edit(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
        def userInstance = user
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(controller:"login", action: "auth")
            return
        }
		log.info "USER: " + userInstance
        if (version != null) {
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'user.label', default: 'User')] as Object[],
                          "Another user has updated this User while you were editing")
                render(controller:"account", view: "index", model: [userInstance: userInstance])
                return
            }
        }

        userInstance.properties = params

        if (!userInstance.save(flush: true)) {
			log.info "NO GUARDA"
            render(controller:"account", view: "index", model: [userInstance: userInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(controller:"account", action: "index", model: [id: userInstance.id, userInstance:userInstance])
    }
	
	def updateAccessInfo(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.info "PARAMS: " + params
		
		if (params.password!=user.password && params.password != params.password2) {
			log.info "ENTRA PASSWORD NO COINDICE"
			flash.message = message(code: 'Para cambiar la contrase??a, deben coincidir los dos campos, revise la informaci??n introducida.', args: [message(code: 'user.label', default: 'User'), user.id])
			//redirect (controller:"account", view: "index", model: [userInstance: user])
			render (text: "<script type=\"text/javascript\"> alert(\"Para cambiar la contrase??a, deben coincidir los dos campos, revise la informaci??n introducida.\"); document.location.href=\"/emenu-webapp/account/index\"; </script>", contentType: 'js')
			return
		}
		
		def userInstance = user
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(controller:"login", action: "auth")
			return
		}
		log.info "USER: " + userInstance
		if (version != null) {
			if (userInstance.version > version) {
				userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						  [message(code: 'user.label', default: 'User')] as Object[],
						  "Another user has updated this User while you were editing")
				render(controller:"account", view: "index", model: [userInstance: userInstance])
				return
			}
		}

		userInstance.properties = params

		if (!userInstance.save(flush: true)) {
			log.info "NO GUARDA"
			render(controller:"account", view: "index", model: [userInstance: userInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
		redirect(controller:"account", action: "index", model: [id: userInstance.id, userInstance:userInstance])
	}

    def delete(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        try {
            userInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def updatePhoto(long id, long version) {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		id = user.id
		
		log.debug "User: " + user?.properties
		
		
		/*if(UserRole.findByUser(user).role.authority != Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la asociaci??n con ID '${id}'"
			response.sendError(404)
		}*/
		
		def userInstance = User.get(id)
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(controller:"account", action: "index", model: [userInstance:user])
			return
		}

		userInstance.properties = params
		
		saveUserPhoto(userInstance)
		
		if (!userInstance.save(flush: true)) {
			render(controller:"account", view: "index", model: [userInstance: user])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
		redirect(controller:"account", action: "index", model: [id: user.id, userInstance:user])
	}
	
	/**
	 * Guarda la foto indicada en la ventana de foto de usuario
	 */
	def saveUserPhoto(User user) {
			
		def f = request.getFile('photoFile')
		
		if (f && !f.empty) {
			
			def repositoryImageId = imageRepositoryService.saveImage(f.inputStream)
			if(repositoryImageId)
			{
				
				user.photo = repositoryImageId
				
			}
		}
	}
	
	def inquiriesAdmin() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.info "PARAMS: " + params
		
		def userInstance = user
		if (!userInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(controller:"login", action: "auth")
			return
		}
		log.info "USER: " + userInstance

		userInstance.properties = params

		//flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
		//redirect(controller:"account", action: "index", model: [id: userInstance.id, userInstance:userInstance])
		[user: userInstance]
	}
}
