package com.nidoapp.nidopub.controller

import grails.plugins.springsecurity.Secured;

import org.springframework.dao.DataIntegrityViolationException

import com.nidoapp.nidopub.domain.Association;
import com.nidoapp.nidopub.domain.Pub;
import com.nidoapp.nidopub.domain.geo.Province;
import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.user.UserRole;

class AssociationController {
	
	def springSecurityService
	
	def imageRepositoryService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [associationInstanceList: Association.list(params), associationInstanceTotal: Association.count()]
    }
	
	/**
	 * Lista las asociaciones de una provincia
	 * @param max
	 * @param id_province
	 * @return
	 */
	def listProvinceAssociations() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		//params.max = Math.min(max ?: 10, 100)
		
		def province = new Province()
		
		province = Province.findById(params.id_province)
		
		def id_province = province.id
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SUPERADMIN"
			response.sendError(404)
		}
		
		//def pubInstanceList = Pub.findAll("from Pub where id not in (:pubs) and province_id = (:province) order by name asc" , [pubs: association.pubs.id, province: association.province])
		
		def associationInstanceList = Association.findAllByProvince(province, [sort:"name", order:"asc"])
		
		log.info "Associations: " + associationInstanceList
		
		[associationInstanceList: associationInstanceList, province: province, user: user]
	}

    def create() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		if(UserRole.findByUser(user).role.authority != Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SuperAdmin"
			response.sendError(404)
		}
		
        [associationInstance: new Association(params), user:user]
    }

    def save() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
        def associationInstance = new Association(params)
		
		def owner = User.findById(params.owner.id)
		
        if (!associationInstance.save(flush: true)) {
            render(view: "create", model: [associationInstance: associationInstance])
            return
        }
		
		owner.association = associationInstance

        flash.message = message(code: 'default.created.message', args: [message(code: 'association.label', default: 'Asociación'), associationInstance.name])
        render(view: "create", model:[user:user])
    }

    def show(Long id) {
        def associationInstance = Association.get(id)
        if (!associationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'association.label', default: 'Association'), id])
            redirect(action: "list")
            return
        }

        [associationInstance: associationInstance]
    }

    def edit(Long id) {
        def associationInstance = Association.get(id)
        if (!associationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'association.label', default: 'Association'), id])
            redirect(action: "list")
            return
        }

        [associationInstance: associationInstance]
    }

    def update(Long id, Long version) {
        def associationInstance = Association.get(id)
        if (!associationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'association.label', default: 'Association'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (associationInstance.version > version) {
                associationInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'association.label', default: 'Association')] as Object[],
                          "Another user has updated this Association while you were editing")
                render(view: "edit", model: [associationInstance: associationInstance])
                return
            }
        }

        associationInstance.properties = params

        if (!associationInstance.save(flush: true)) {
            render(view: "edit", model: [associationInstance: associationInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'association.label', default: 'Association'), associationInstance.id])
        redirect(action: "show", id: associationInstance.id)
    }
	
	def updateLogo(long id, long version) {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		id = user.association.id
		
		log.debug "User: " + user?.properties
		
		
		if(UserRole.findByUser(user).role.authority != Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la asociaci??n con ID '${id}'"
			response.sendError(404)
		}
		
		def associationInstance = Association.get(id)
		if (!associationInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'association.label', default: 'Association'), id])
			redirect(controller:"account", action: "index", model: [userInstance:user])
			return
		}

		associationInstance.properties = params
		
		saveNewLogo(associationInstance)
		
		if (!associationInstance.save(flush: true)) {
			render(controller:"account", view: "index", model: [userInstance: user])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [message(code: 'association.label', default: 'Association'), associationInstance.id])
		redirect(controller:"account", action: "index", model: [id: user.id, userInstance:user])
	}
	

    def delete(Long id) {
        def associationInstance = Association.get(id)
        if (!associationInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'association.label', default: 'Association'), id])
            redirect(action: "list")
            return
        }

        try {
            associationInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'association.label', default: 'Association'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'association.label', default: 'Association'), id])
            redirect(action: "show", id: id)
        }
    }
	
	/**
	 * Elimina un pub concreto, de la asociaci��n
	 * @param pubId
	 * @return
	 */
	def deletePub(long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def association
		
		association = Association.findById(user.association.id)
		
		log.info "Se elimina de la asociaci��n:" + association.name + " el pub: " + Pub.findById(id).name
		
		association.pubs.remove(Pub.findById(id))
		
		/*if(association?.owner != user)
		{
			log.warn "El usuario '${user?.username}' NNNno es propietario de la asociaci??n con ID '${association?.id}'"
			response.sendError(404)
		}*/
		
		redirect(controller:"pub", action: "pubsAssociation", model: [user:user])
	}
	
	/**
	 * A��ade un pub concreto a la asociaci��n
	 * @param pubId
	 * @return
	 */
	def addPub(long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def association
		
		association = Association.findById(user.association.id)
		
		log.info "Se a��ade a la asociaci��n:" + association.name + " el pub: " + Pub.findById(id).name
		
		association.pubs.add(Pub.findById(id))
		
		/*if(association?.owner != user)
		{
			log.warn "El usuario '${user?.username}' NNNno es propietario de la asociaci??n con ID '${association?.id}'"
			response.sendError(404)
		}*/
		
		redirect(controller:"pub", action: "pubsAssociation", model: [user:user])
	}
	
	/**
	 * Elimina un pub concreto, de la asociación (SuperAdmin)
	 * @param pubId
	 * @return
	 */
	def deletePubSuperAdmin(long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def association
		
		association = Association.findById(params.associationId)
		
		log.info "Se elimina de la asociación:" + association.name + " el pub: " + Pub.findById(id).name
		
		association.pubs.remove(Pub.findById(id))
		
		/*if(association?.owner != user)
		{
			log.warn "El usuario '${user?.username}' NNNno es propietario de la asociaci??n con ID '${association?.id}'"
			response.sendError(404)
		}*/
		
		redirect(controller:"pub", action: "pubsAssociationSuperAdmin", model: [user:user, association: association], id:association.id, params: [provinceId: Pub.findById(id).province.id])
	}
	
	/**
	 * Añade un pub concreto a la asociación (SuperAdmin)
	 * @param pubId
	 * @return
	 */
	def addPubSuperAdmin(long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def association
		
		association = Association.findById(params.associationId)
		
		log.info "Se añade a la asociación:" + association.name + " el pub: " + Pub.findById(id).name
		
		association.pubs.add(Pub.findById(id))
		
		/*if(association?.owner != user)
		{
			log.warn "El usuario '${user?.username}' NNNno es propietario de la asociaci??n con ID '${association?.id}'"
			response.sendError(404)
		}*/
		
		redirect(controller:"pub", action: "pubsAssociationSuperAdmin", model: [user:user, association: association], id:association.id, params: [provinceId: Pub.findById(id).province.id])
	}
	
	/**
	 * Guarda el logo indicada en la ventana de nuevo logo
	 */
	def saveNewLogo(Association association) {
			
		def f = request.getFile('photoFile')
		
		if (f && !f.empty) {
			
			def repositoryImageId = imageRepositoryService.saveImage(f.inputStream)
			if(repositoryImageId)
			{
				
				association.photo = repositoryImageId
				
			}
		}
	}
	
	/**
	 * Muestra la ventana de edicion de datos de configuración de Asociación
	 */
	def editAssociationConfig = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
		
		def association = Association.findById(params.id)
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SUPERADMIN"
			response.sendError(404)
		}

		
		[user:user, association: association]

	}
	
	/**
	 * Guarda los datos de configuración de asociación
	 */
	def saveAssociationConfig = { AssociationConfigCommand cmd ->
		
		
		if(cmd.validate())
		{
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			
			
			def association = Association.findById(cmd.associationId)
			
			
			if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
			{
				log.warn "El usuario '${user?.username}' no es SUPERADMIN"
				response.sendError(404)
			}
			
			def name = params.name
			def maxPubsCreate = params.maxPubsCreate
			
			
			association.name = name
			association.maxPubsCreate = Integer.parseInt(maxPubsCreate)
			
			
			association.save(flush:true, failOnError:true)
				
			redirect(url: "/superAdminAssociationControl/index/"+association.id)
		}
		else
		{
			log.warn "Errores en los datos: " + cmd?.errors
			chain action: "editAssociationConfig", model: [cmd: cmd], id: cmd.associationId
		}
		
		

	}
}


/**
 * Encapsula los datos que se recogen del formulario de edicion de datos de configuración de
 * asociación
 * @author Developer
 *
 */
class AssociationConfigCommand{
	String associationId

	static constraints = {
		
	}
}
