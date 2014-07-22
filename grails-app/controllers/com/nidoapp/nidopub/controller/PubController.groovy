package com.nidoapp.nidopub.controller

import grails.plugins.springsecurity.Secured
import groovy.sql.Sql

import com.nidoapp.nidopub.domain.Association;
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.PubEvent;
import com.nidoapp.nidopub.domain.PubFeature
import com.nidoapp.nidopub.domain.PubMusicType
import com.nidoapp.nidopub.domain.PubPhoto
import com.nidoapp.nidopub.domain.PubPromotionalImage;
import com.nidoapp.nidopub.domain.geo.City
import com.nidoapp.nidopub.domain.geo.Country
import com.nidoapp.nidopub.domain.geo.District
import com.nidoapp.nidopub.domain.geo.Province
import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.user.UserRole;

import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

/**
 * Controlador que atiende peticiones de la admininistracion para gestionar pubs
 * @author Developer
 *
 */
@Secured([Role.ROLE_PUB_OWNER, Role.ROLE_ASSOCIATION_OWNER, Role.ROLE_SUPERADMIN])
class PubController {

    def springSecurityService
	def imageRepositoryService
	def googleMapsService
	def countryDialingService
	
	def scaffold = true

    /**
     * Muestra la ventana de administracion de pub con toda la informacion
     */
    def index = {		
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		/*
		if(user.pubs)
			pub = user.pubs.first()
			*/
		
		[user:user, pub: pub]

	}
	
	/**
	 * Muestra la ventana de crear nuevo pub
	 */
	def newPub = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		
		
		if(UserRole.findByUser(user).role.authority != Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SuperAdmin"
			response.sendError(404)
		}
		/*
		if(user.pubs)
			pub = user.pubs.first()
			*/
		render(view: "newPub", model: [user:user])
	
	}
	
	
	/**
	 * Guarda un nuevo pub, añadiendo como propietario al usuario seleccionado, 
	 * crea la uri automaticamente y asigna como país España.
	 */
	def save() {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def pubInstance = new Pub(params)
		
		
		if (Pub.findByUri(pubInstance.name.toLowerCase().replaceAll(" ", "-").trim())) {
			flash.message = message(code: 'Ya existe un pub con ese nombre.')
			render(view: "newPub", model: [pubInstance: pubInstance, user:user])
			return
		}
		
		if (!params.cartePriceFrom) {
			pubInstance.cartePriceFrom = 0;
		}
		
		if (!params.cartePriceTo) {
			pubInstance.cartePriceTo = 0;
		}
		
		//pubInstance.owner = user
		pubInstance.uri = pubInstance.name.toLowerCase().replaceAll(" ", "-").trim()
		pubInstance.country = Country.findById(1)
		pubInstance.LunesM = false
		pubInstance.LunesN = false
		pubInstance.MartesM = false
		pubInstance.MartesN = false
		pubInstance.MiercolesM = false
		pubInstance.MiercolesN = false
		pubInstance.JuevesM = false
		pubInstance.JuevesN = false
		pubInstance.ViernesM = false
		pubInstance.ViernesN = false
		pubInstance.SabadoM = false
		pubInstance.SabadoN = false
		pubInstance.DomingoM = false
		pubInstance.DomingoN = false
		pubInstance.LunesM = false
		//pubInstance.MinComensales = 0
		//pubInstance.onlineReservations = false
		pubInstance.inquiryActive = false
		
		Association.findByName('NidoPass').pubs.add(pubInstance)
		
		if (!pubInstance.save(flush: true)) {
			render(view: "newPub", model: [pubInstance: pubInstance, user:user])
			log.info "No se ha podido guardar la sala."
			return
		}

		flash.message = message(code: 'Pub creado correctamente')
		//redirect(action: "show", id: pubInstance.id)
		redirect(uri:"/pub/listProvincePubs?id_province="+pubInstance.province.id)
	}
	
	/**
	 * Muestra la ventana de crear nuevo pub desde una cuenta de asociación
	 */
	def newAssociationPub = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		
		
		if(UserRole.findByUser(user).role.authority != Role.ROLE_PUB_OWNER && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario de asociación y no puede crear un nuevo pub'"
			response.sendError(404)
		}
		/*
		if(user.pubs)
			pub = user.pubs.first()
			*/
		render(view: "newAssociationPub", model: [user:user])
	
	}
	
	
	/**
	 * Guarda un nuevo pub, añadiendo a la asociación desde la que se ha creado,
	 * crea la uri automaticamente y asigna como país España.
	 */
	def saveAssociationPub() {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def association = user.association
		
		def pubInstance = new Pub(params)
		
		if (association.pubs.size()>=association.maxPubsCreate && association.maxPubsCreate>0) {
			flash.message = message(code: 'Has alcanzado tu límite de creación de salas. Para más información o ampliar el límite contacta en dmv@nidoapp.com.')
			render(view: "newAssociationPub", model: [pubInstance: pubInstance, user:user])
			return
		}
		
		
		if (Pub.findByUri(pubInstance.name.toLowerCase().replaceAll(" ", "-").trim())) {
			flash.message = message(code: 'Ya existe un pub con ese nombre.')
			render(view: "newAssociationPub", model: [pubInstance: pubInstance, user:user])
			return
		}
		
		if (!params.cartePriceFrom) {
			pubInstance.cartePriceFrom = 0;
		}
		
		if (!params.cartePriceTo) {
			pubInstance.cartePriceTo = 0;
		}
		
		//pubInstance.owner = user
		pubInstance.uri = pubInstance.name.toLowerCase().replaceAll(" ", "-").trim()
		pubInstance.country = Country.findById(1)
		pubInstance.LunesM = false
		pubInstance.LunesN = false
		pubInstance.MartesM = false
		pubInstance.MartesN = false
		pubInstance.MiercolesM = false
		pubInstance.MiercolesN = false
		pubInstance.JuevesM = false
		pubInstance.JuevesN = false
		pubInstance.ViernesM = false
		pubInstance.ViernesN = false
		pubInstance.SabadoM = false
		pubInstance.SabadoN = false
		pubInstance.DomingoM = false
		pubInstance.DomingoN = false
		pubInstance.LunesM = false
		//pubInstance.MinComensales = 0
		//pubInstance.onlineReservations = false
		pubInstance.inquiryActive = false
		
		association.pubs.add(pubInstance)
		
		Association.findByName('NidoPass').pubs.add(pubInstance)
		
		
		if (!pubInstance.save(flush: true)) {
			render(view: "newAssociationPub", model: [pubInstance: pubInstance, user:user])
			return
		}
		

		flash.message = message(code: 'Pub creado correctamente')
		//redirect(action: "show", id: pubInstance.id)
		redirect(uri:"/pub/index/"+pubInstance.uri)
	}
	
	/**
	 * Elimina el pub.
	 * @param id
	 * @return
	 */
	def delete(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def pubInstance = Pub.get(id)
		
		if (!pubInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
			redirect(uri: "/")
			return
		}
		
		
		if(pubInstance?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pubInstance?.id}'"
			response.sendError(404)
			return
		}

		try {
			pubInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'pub.label', default: 'Pub'), id])
			redirect(uri: "/")
		}
		catch (Exception e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pub.label', default: 'Pub'), id])
			redirect(action: "index", id: pubInstance.uri)
		}
	}
	
	/**
	 * Lista los pubs de una asociacion
	 * @param max
	 * @param id_pub
	 * @return
	 */
	def list(Integer max) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		//params.max = Math.min(max ?: 10, 100)
		
		def association
		
		association = Association.findById(user.association.id)
		
		def id_association = association.id
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la asociación con ID '${association?.id}'"
			response.sendError(404)
		}
		
		//def pubInstanceList = association.pubs
		
		def pubInstanceList = Pub.findAll("from Pub where id in (:pubs) order by name asc" , [pubs: association.pubs.id])
		
		
		[pubInstanceList: pubInstanceList]
	}
	
	/**
	 * Lista los pubs de una provincia
	 * @param max
	 * @param id_province
	 * @return
	 */
	def listProvincePubs() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		//params.max = Math.min(max ?: 10, 100)
		
		def province
		
		province = Province.findById(params.id_province)
		
		def id_province = province.id
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SUPERADMIN"
			response.sendError(404)
		}
		
		//def pubInstanceList = Pub.findAll("from Pub where id not in (:pubs) and province_id = (:province) order by name asc" , [pubs: association.pubs.id, province: association.province])
		
		def pubInstanceList = Pub.findAllByProvince(province, [sort:"name", order:"asc"])
		
		log.info "Pubs: " + pubInstanceList
		
		[pubInstanceList: pubInstanceList, province: province, user: user]
	}
	
	/**
	 * Lista los pubs de una provincia para editar sus datos financieros para 
	 * venta de entradas online
	 * @param max
	 * @param id_province
	 * @return
	 */
	def listProvincePubsFinance() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		//params.max = Math.min(max ?: 10, 100)
		
		def province
		
		province = Province.findById(params.id_province)
		
		def id_province = province.id
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SUPERADMIN"
			response.sendError(404)
		}
		
		//def pubInstanceList = Pub.findAll("from Pub where id not in (:pubs) and province_id = (:province) order by name asc" , [pubs: association.pubs.id, province: association.province])
		
		def pubInstanceList = Pub.findAllByProvince(province, [sort:"name", order:"asc"])
		
		log.info "Pubs: " + pubInstanceList
		
		[pubInstanceList: pubInstanceList, province: province, user: user]
	}
	
	
	/**
	 * Lista los pubs sin asociacion
	 * @param max
	 * @param id_pub
	 * @return
	 */
	def listNoAssociation() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		//params.max = Math.min(max ?: 10, 100)
		
		def association
		
		association = Association.findById(user.association.id)
		
		def id_association = association.id
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la asociación con ID '${association?.id}'"
			response.sendError(404)
		}
		
		def pubInstanceList = Pub.findAll("from Pub where id not in (:pubs) and province_id = (:province) order by name asc" , [pubs: association.pubs.id, province: association.province])
		
		log.info "Pubs: " + pubInstanceList
		
		[pubInstanceList: pubInstanceList]
	}
	
	/**
	 * Lista los pubs de una asociacion para SuperAdmin
	 * @param max
	 * @param id_pub
	 * @return
	 */
	def listAssociationSuperAdmin(Integer max) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		//params.max = Math.min(max ?: 10, 100)
		
		def association
		
		association = Association.findById(params.id)
		
		def id_association = association.id
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SuperAdmin"
			response.sendError(404)
		}
		
		//def pubInstanceList = association.pubs
		
		def pubInstanceList = Pub.findAll("from Pub where id in (:pubs) order by name asc" , [pubs: association.pubs.id])
		
		
		[pubInstanceList: pubInstanceList, association:association]
	}
	
	/**
	 * Lista los pubs sin asociacion para SuperAdmin
	 * @param max
	 * @param id_pub
	 * @return
	 */
	def listNoAssociationSuperAdmin() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		//params.max = Math.min(max ?: 10, 100)
		
		def association
		
		association = Association.findById(params.id)
		
		def id_association = association.id
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SuperAdmin"
			response.sendError(404)
		}
		
		def pubInstanceList = Pub.findAll("from Pub where id not in (:pubs) and province_id = (:province) order by name asc" , [pubs: association.pubs.id, province: Province.findById(params.provinceId)])
		
		log.info "Pubs: " + pubInstanceList
		
		[pubInstanceList: pubInstanceList, association: association]
	}
	
	
	/**
     * Muestra la ventana de administracion de pubs de una asociación
     */
    def pubsAssociation = {		
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		def association
		
		association = Association.findById(user.association.id)
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario de la asociación con ID '${association?.id}'"
			response.sendError(404)
		}
		
		render(view: "pubsAssociation", model: [user:user])

	}
	
	/**
	 * Muestra para SUPERADMIN la ventana de administracion de pubs de una asociación
	 */
	def pubsAssociationSuperAdmin = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		log.info "PARAMS: " + params
		def association
		
		association = Association.findById(params.id)
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SuperAdmin"
			response.sendError(404)
		}
		log.info association
		render(view: "pubsAssociationSuperAdmin", model: [user:user, association: association])

	}
	
	/**
	 * Muestra la ventana de edicion de datos basicos de pub
	 */
	def editBasicInfo = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		/*
		if(user.pubs)
			pub = user.pubs.first()			
			*/

		
		[user:user, pub: pub]

	}
	
	/**
	 * Guarda los datos basicos indicados en la ventana de edicion
	 */
	def saveBasicInfo = { BasicInfoCommand cmd ->
		
		if(cmd.validate())
		{
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			log.debug "Pubs: " + user.pubs
			
			def pub = Pub.findByUri(cmd.pubUri)
			
			if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
			{
				log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
				response.sendError(404)
			}
			
			/*
			if(user.pubs)
				pub = user.pubs.first()
				*/
	
			
			
			pub.name = cmd.name
			pub.description = cmd.description
			pub.telephone = cmd.telephone
			pub.fax = cmd.fax
			pub.email = cmd.email
			pub.website = cmd.website
				
			pub.save(flush:true)
			
			redirect(action: "index", id: pub?.uri)
		}
		else
		{
			log.warn "Errores en los datos: " + cmd?.errors
			chain action: "editBasicInfo", model: [cmd: cmd], id: cmd?.pubUri
		}
		
		

	}
	
	/**
	 * Muestra la ventana de edicion de especialidad y tipo de música
	 */
	def editSpecialty = { 
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		/*
		if(user.pubs)
			pub = user.pubs.first()
		*/

		def musicTypes = PubMusicType.list(sort: "name", order: "asc")
		
		[user:user, pub: pub, musicTypes: musicTypes]

	}
	
	/**
	 * Guarda la especialidad y tipo de música indicados en la ventana de edicion
	 */
	def saveSpecialty = { SpecialtyCommand cmd ->
		
		if(cmd.validate())
		{
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			log.debug "Pubs: " + user.pubs
			
			def pub = Pub.findByUri(cmd.pubUri)
			
			if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
			{
				log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
				response.sendError(404)
			}
	
			
			
			pub.specialty = cmd.specialty
			pub.musicType = PubMusicType.get(cmd.musicTypeId)
	
				
			pub.save(flush:true)
			
			redirect(action: "index", id: pub?.uri)
		}
		else
		{
			log.warn "Errores en los datos: " + cmd?.errors
			chain action: "editSpecialty", model: [cmd: cmd], id: cmd?.pubUri
		}
		
		
		

	}
	
	/**
	 * Muestra la ventana de edicion de caracteristicas
	 */
	def editFeatures = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}

		def features = PubFeature.list(sort: "name", order: "asc")
		
		[user:user, pub: pub, features:features]

	}
	
	/**
	 * Guarda las caracteristicas indicadas en la ventana de edicion
	 */
	def saveFeatures = {
		
		log.debug "Params: " + params
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.pubUri)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}

		
		log.debug "Selected Features: " + params.list('selectedFeatures')
		
		pub.features = PubFeature.getAll(params.list('selectedFeatures'))
			
		pub.save(flush:true)
		
		redirect(action: "index", id: pub?.uri)

	}
	
	/**
	 * Muestra la ventana de edicion de fotos, que permite incluir nuevas fotos, eliminarlas, o modificarlas
	 */
	def editPhotos = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}

		
		[user:user, pub: pub]

	}
	
	/**
	 * Muestra la ventana de nueva foto
	 */
	def editNewPhoto = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}

		
		[user:user, pub: pub]

	}
	
	/**
	 * Guarda la foto indicada en la ventana de nueva foto
	 */
	def saveNewPhoto = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.pubUri)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
			
			
		def f = request.getFile('photoFile')
		def description = params.description ? params.description : pub?.name
		def main = new Boolean(params.main)
		
		if (f && !f.empty) {
			
			def repositoryImageId = imageRepositoryService.saveImage(f.inputStream)
			if(repositoryImageId)
			{
				def photo = new PubPhoto()
				photo.repositoryImageId = repositoryImageId
				photo.description = description
				
				if(main)
				{
					def mainPhotos = PubPhoto.findAllByPubAndMain(pub, true)
					mainPhotos.each{
						it.main = false
						it.save(flush:true, failOnError:true)
					}
					
					photo.main = main
				}
				
				
				log.info "Incluyendo foto: " + photo.repositoryImageId
				
				pub.addToPhotos(photo)
				
				pub.save(flush:true, failOnError:true)
			}
		}
		
		redirect(action: "editPhotos", id: pub?.uri)
	}
	
	/**
	 * Muestra la ventana de edicion de foto
	 */
	def editPhoto = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pub: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
			
		def photoId = params.repositoryImageId
		def photo = PubPhoto.findByPubAndRepositoryImageId(pub, photoId)

		
		[user:user, pub: pub, photo: photo]

	}
	
	/**
	 * Guarda la foto indicada en la ventana de edicion de foto
	 */
	def savePhoto = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.pubUri)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
			
		
		def photoId = params.repositoryImageId		
		def f = request.getFile('photoFile')
		def description = params.description ? params.description : pub?.name
		def main = new Boolean(params.main)
		
		def photo = PubPhoto.findByPubAndRepositoryImageId(pub, photoId)
		
		if(photo)
		{
			if (f && !f.empty) {
				
				def repositoryImageId = imageRepositoryService.saveImage(f.inputStream)
				if(repositoryImageId)
				{
					photo.repositoryImageId = repositoryImageId					

				}
			}
			
			photo.description = description
			
			if(main)
			{
				def mainPhotos = PubPhoto.findAllByPubAndMain(pub, true)
				mainPhotos.each{
					it.main = false
					it.save(flush:true, failOnError:true)
				}
				
				photo.main = main
			}
			
			photo.save(flush:true, failOnError:true)
		}
		
		
		
		redirect(action: "editPhotos", id: pub?.uri)
	}
	
	
	/**
	 * Establece una foto como foto principal
	 */
	def saveMainPhoto = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
			
		def nextMainPhotoId = params.repositoryImageId 	
		
		def nextMainPhoto = PubPhoto.findByRepositoryImageIdAndPub(nextMainPhotoId, pub)
		def mainPhotos = PubPhoto.findAllByPubAndMain(pub, true)
		
		if(nextMainPhoto)
		{
			mainPhotos.each{
				it.main = false
				it.save(flush:true, failOnError:true)
			}
			
			nextMainPhoto.main = true						
			nextMainPhoto.save(flush:true, failOnError:true)
		}
		
		redirect(action: "editPhotos", id: pub?.uri)
	}
	
	/**
	 * Elimina una foto
	 */
	def removePhoto = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
			
		def photoId = params.repositoryImageId
		
		def photo = PubPhoto.findByRepositoryImageIdAndPub(photoId, pub)
		
		if(photo)
		{
			def newMainPhotoNeeded = false
			if(photo.main)
			{		
				newMainPhotoNeeded = true
			}
			
			pub.removeFromPhotos(photo)
			photo.delete(flush:true, failOnError:true)				
			
			pub.save(flush:true, failOnError:true)
			
			if(newMainPhotoNeeded)
			{
				if(pub.photos)
				{
					def nextMainPhoto = pub.photos.first()
					nextMainPhoto.main = true
					nextMainPhoto.save(flush:true, failOnError:true)
				}
			}
		}
		
		redirect(action: "editPhotos", id: pub?.uri)
	}
	
	/**
	 * Muestra la ventana de edicion de logo
	 */
	def editPubLogo = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pub: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
			
		//def photoId = params.repositoryImageId
		//def photo = Pub.logoImage

		
		[user:user, pub: pub]

	}
	
	/**
	 * Guarda la imagen de logo indicada en la ventana de nuevo logo
	 */
	def saveNewLogo() {
		
		def pub = Pub.findByUri(params.pubUri)
			
		def f = request.getFile('photoFile')
		
		if (f && !f.empty) {
			
			def repositoryImageId = imageRepositoryService.saveImage(f.inputStream)
			if(repositoryImageId)
			{
				
				pub.logoImage = repositoryImageId
				pub.save(flush:true, failOnError:true)
				
			}
		}
		
		redirect(action: "index", id: pub?.uri)
	}
	
	
	/**
	 * Muestra la ventana de edicion de ubicacion
	 */
	def editLocation = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}

		
		[user:user, pub: pub]

	}
	
	
	/**
	 * Guarda la ubicacion indicada en la ventana de edicion
	 */
	def saveLocation = { LocationCommand cmd ->
		
		
		
		if(cmd.validate())
		{
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			log.debug "Pubs: " + user.pubs
			
			def pub = Pub.findByUri(cmd.pubUri)
			
			if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
			{
				log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
				response.sendError(404)
			}
				
			def latitude = cmd.latitude
			def longitude = cmd.longitude
			def address = cmd.address
			
			pub.latitude = new Double(latitude)
			pub.longitude = new Double(longitude)
			pub.address = address
			
			def locationDescription = googleMapsService.getLocationDescription(pub.latitude, pub.longitude)
			
			log.debug "LocationDescription: " + locationDescription
			
			if(!locationDescription.error)
			{
				def country = Country.findByCode(locationDescription.countryIsoCode)
				if(!country && locationDescription.countryName && locationDescription.countryIsoCode)
				{
					def dialCode = countryDialingService.getDialCode(locationDescription.countryIsoCode)
					country = new Country(name: locationDescription.countryName, code: locationDescription.countryIsoCode, dialCode: dialCode).save(flush:true, failOnError:true)
				}
				pub.country = country
				
				
				def province = Province.findByNameAndCountry(locationDescription.province, country)
				if(!province && locationDescription.province)
				{
					province = new Province(name: locationDescription.province, country:country).save(flush:true, failOnError:true)
				}
				pub.province = province
				
			
				def city = City.findByNameAndProvince(locationDescription.city, province)
				if(!city && locationDescription.city)
				{
					city = new City(name: locationDescription.city, province: province).save(flush:true, failOnError:true)
				}
				pub.city = city
				
				
				def district = District.findByNameAndCity(locationDescription.district, city)
				if(!district && locationDescription.district)
				{
					district = new District(name: locationDescription.district, city: city).save(flush:true, failOnError:true)
				}
				pub.district = district
				
				pub.save(flush:true, failOnError:true)
				
			}
			
				
			redirect(action: "index", id: pub?.uri)
		}
		else
		{
			log.warn "Errores en los datos: " + cmd?.errors
			chain action: "editLocation", model: [cmd: cmd], id: cmd.pubUri
		}
		
		

	}
	
	
	/**
	 * Muestra la ventana de edicion de configuracion de reservas
	 */
	def editReservationSettings = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		if(!pub.reservationsAvailable) {
			redirect (action: "pubInquiryDenied", params: [id:pub.uri], model: [user:user, pub:pub])
			return
		}

		
		[user:user, pub: pub]

	}
	
	/**
	 * Guarda la configuracion de reserva indicada en la ventana de edicion
	 */
	/*def saveReservationSettings = { ReservationSettingsCommand cmd ->
		
		
		if(cmd.validate())
		{
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			log.debug "Pubs: " + user.pubs
			
			def pub = Pub.findByUri(cmd.pubUri)
			
			
			if(params.onlineReservations && (cmd.reservationPhone == null && cmd.reservationEmail == null))
			{
				log.warn "El usuario '${user?.username}' está intentando activar las reservas sin rellenar el campo de teléfono ni email en el pub con ID '${pub?.id}'."
				flash.message = "No puede activar las reservas sin añadir número de teléfono o email."
				chain action: "editReservationSettings", model: [cmd: cmd], id: cmd.pubUri
				return
			}
			
			if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
			{
				log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
				response.sendError(404)
			}
	
			def reservationPhone = cmd.reservationPhone
			def reservationEmail = cmd.reservationEmail
			
			def minComensales = params.minComensales
			
			
			def lunesM = params.lunesM
			def lunesN = params.lunesN
			def martesM = params.martesM
			def martesN = params.martesN
			def miercolesM = params.miercolesM
			def miercolesN = params.miercolesN
			def juevesM = params.juevesM
			def juevesN = params.juevesN
			def viernesM = params.viernesM
			def viernesN = params.viernesN
			def sabadoM = params.sabadoM
			def sabadoN = params.sabadoN
			def domingoM = params.domingoM
			def domingoN = params.domingoN
			
			def onlineReservations = params.onlineReservations
			
			
			if (lunesM == null) {
				lunesM = false
			}
			if (lunesN == null) {
				lunesN = false
			}
			if (martesM == null) {
				martesM = false
			}
			if (martesN == null) {
				martesN = false
			}
			if (miercolesM == null) {
				miercolesM = false
			}
			if (miercolesN == null) {
				miercolesN = false
			}
			if (juevesM == null) {
				juevesM = false
			}
			if (juevesN == null) {
				juevesN = false
			}
			if (viernesM == null) {
				viernesM = false
			}
			if (viernesN == null) {
				viernesN = false
			}
			if (sabadoM == null) {
				sabadoM = false
			}
			if (sabadoN == null) {
				sabadoN = false
			}
			if (domingoM == null) {
				domingoM = false
			}
			if (domingoN == null) {
				domingoN = false
			}
			
			if (onlineReservations == null) {
				onlineReservations = false
			}
			
			
			pub.reservationPhone = reservationPhone
			pub.reservationEmail = reservationEmail
			
			pub.MinComensales = Integer.parseInt(minComensales)
			
			
			pub.LunesM = lunesM
			pub.LunesN = lunesN
			pub.MartesM = martesM
			pub.MartesN = martesN
			pub.MiercolesM = miercolesM
			pub.MiercolesN = miercolesN
			pub.JuevesM = juevesM
			pub.JuevesN = juevesN
			pub.ViernesM = viernesM
			pub.ViernesN = viernesN
			pub.SabadoM = sabadoM
			pub.SabadoN = sabadoN
			pub.DomingoM = domingoM
			pub.DomingoN = domingoN
			
			pub.onlineReservations = onlineReservations
			
			pub.save(flush:true, failOnError:true)
				
			redirect(action: "index", id: pub?.uri)
		}
		else
		{
			log.warn "Errores en los datos: " + cmd?.errors
			chain action: "editReservationSettings", model: [cmd: cmd], id: cmd.pubUri
		}
		
		

	}*/
	
	/**
	 * Muestra la ventana de edicion de configuracion de venta de entradas online
	 */
	def editTicketOnlineSaleSettings = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		[user:user, pub: pub]

	}
	
	/**
	 * Guarda la configuracion de venta de entradas online indicada en la ventana de edicion
	 */
	def saveTicketOnlineSaleSettings = { TicketOnlineSaleSettingsCommand cmd ->
		
		
		if(cmd.validate())
		{
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			log.debug "Pubs: " + user.pubs
			
			def pub = Pub.findByUri(cmd.pubUri)
			
			
			if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
			{
				log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
				response.sendError(404)
			}
	
			def businessName = cmd.businessName
			def contactPerson = cmd.contactPerson
			def contactTelephone = cmd.contactTelephone
			
			def nif = cmd.nif
			def invoiceAddress = cmd.invoiceAddress
			
			pub.businessName = businessName
			pub.contactPerson = contactPerson
			pub.contactTelephone = contactTelephone
			
			pub.nif = nif
			pub.invoiceAddress = invoiceAddress
			
			
			pub.save(flush:true, failOnError:true)
				
			redirect(action: "index", id: pub?.uri)
		}
		else
		{
			log.warn "Errores en los datos: " + cmd?.errors
			chain action: "editTicketOnlineSaleSettings", model: [cmd: cmd], id: cmd.pubUri
		}
		
		

	}
	
	/**
	 * Muestra la ventana de edicion de configuracion de otros datos de
	 * venta online de entradas
	 */
	def editTicketOnlineSaleOtherSettings = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		[user:user, pub: pub]

	}
	
	/**
	 * Guarda la configuracion de otros datos de venta de entradas online indicada en 
	 * la ventana de edicion
	 */
	def saveTicketOnlineSaleOtherSettings = { TicketOnlineSaleOtherSettingsCommand cmd ->
		
		
		if(cmd.validate())
		{
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			log.debug "Pubs: " + user.pubs
			
			def pub = Pub.findByUri(cmd.pubUri)
			
			
			if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
			{
				log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
				response.sendError(404)
			}
	
			def ticketsSaleInitNumber = cmd.ticketsSaleInitNumber
			
			
			pub.ticketsSaleInitNumber = ticketsSaleInitNumber
			
			if (params.ticketsSaleInitNumberReset_value) {
				def ticketsSaleInitNumberReset = new Date(params.ticketsSaleInitNumberReset_month+"/"+params.ticketsSaleInitNumberReset_day+"/"+params.ticketsSaleInitNumberReset_year+" 00:00")
				pub.ticketsSaleInitNumberReset = ticketsSaleInitNumberReset
			}
			
			
			pub.save(flush:true, failOnError:true)
				
			redirect(action: "index", id: pub?.uri)
		}
		else
		{
			log.warn "Errores en los datos: " + cmd?.errors
			chain action: "editTicketOnlineSaleOtherSettings", model: [cmd: cmd], id: cmd.pubUri
		}
		
		

	}
	
	/**
	 * Muestra la ventana de edicion de configuracion de email de envío
	 */
	def editEMailSettings = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id_pub)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		if(!pub.eventsAvailable) {
			redirect (action: "pubInquiryDenied", params: [id:pub.uri], model: [user:user, pub:pub])
			return
		}

		
		[user:user, pub: pub]

	}
	
	/**
	 * Guarda la configuracion de email de envío indicada en la ventana de edicion
	 */
	def saveEMailSettings = { EMailSettingsCommand cmd ->
		
		
		if(cmd.validate())
		{
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			log.debug "Pubs: " + user.pubs
			
			def pub = Pub.findByUri(cmd.pubUri)
			
			
			if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
			{
				log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
				response.sendError(404)
			}
	
			def mailAddress = cmd.mailAddress
			def mailHost = cmd.mailHost
			def mailPort = cmd.mailPort
			def mailUsername = cmd.mailUsername
			def mailPassword = cmd.mailPassword
			
			pub.mailAddress = mailAddress
			pub.mailHost = mailHost
			pub.mailPort = mailPort
			pub.mailUsername = mailUsername
			pub.mailPassword = mailPassword
			
			
			pub.save(flush:true, failOnError:true)
				
			redirect(controller:"EMailsAdminAccess", action: "index", id: pub?.uri, model: [user:user, pub:pub])
		}
		else
		{
			log.warn "Errores en los datos: " + cmd?.errors
			chain action: "editEMailSettings", model: [cmd: cmd], id: cmd.pubUri
		}
		
		

	}
	
	/**
	 * Muestra la ventana de edicion de Servicios de Pub
	 */
	def editPubServices = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pub: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SUPERADMIN"
			response.sendError(404)
		}

		
		[user:user, pub: pub]

	}
	
	/**
	 * Guarda los servicios de pub indicado en la ventana de edicion
	 */
	def savePubServices = { PubServicesCommand cmd ->
		
		
		if(cmd.validate())
		{
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			
			
			def pub = Pub.findByUri(cmd.pubUri)
			
			
			if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
			{
				log.warn "El usuario '${user?.username}' no es SUPERADMIN"
				response.sendError(404)
			}
			
			//def reservationsAvailable = params.reservationsAvailable
			def cartesAvailable = params.cartesAvailable
			def translationsAvailable = params.translationsAvailable
			def eventsAvailable = params.eventsAvailable
			def inquiryAvailable = params.inquiryAvailable
			def sellTicketsOnlineAvailable = params.sellTicketsOnlineAvailable
			
			
			def cartesAvailableFrom = null
			def translationsAvailableFrom = null
			def eventsAvailableFrom = null
			def inquiryAvailableFrom = null
			def sellTicketsOnlineAvailableFrom = null
			def cartesAvailableTo = null
			def translationsAvailableTo = null
			def eventsAvailableTo = null
			def inquiryAvailableTo = null
			def sellTicketsOnlineAvailableTo = null
			
			
			if (params.cartesAvailableFrom_value)
				cartesAvailableFrom = new Date(params.cartesAvailableFrom_month+"/"+params.cartesAvailableFrom_day+"/"+params.cartesAvailableFrom_year+" 00:00")
			if (params.translationsAvailableFrom_value)
				translationsAvailableFrom = new Date(params.translationsAvailableFrom_month+"/"+params.translationsAvailableFrom_day+"/"+params.translationsAvailableFrom_year+" 00:00")
			if (params.eventsAvailableFrom_value)
				eventsAvailableFrom = new Date(params.eventsAvailableFrom_month+"/"+params.eventsAvailableFrom_day+"/"+params.eventsAvailableFrom_year+" 00:00")
			if (params.inquiryAvailableFrom_value)
				inquiryAvailableFrom = new Date(params.inquiryAvailableFrom_month+"/"+params.inquiryAvailableFrom_day+"/"+params.inquiryAvailableFrom_year+" 00:00")
			if (params.sellTicketsOnlineAvailableFrom_value)
				sellTicketsOnlineAvailableFrom = new Date(params.sellTicketsOnlineAvailableFrom_month+"/"+params.sellTicketsOnlineAvailableFrom_day+"/"+params.sellTicketsOnlineAvailableFrom_year+" 00:00")
			
			if (params.cartesAvailableTo_value)
				cartesAvailableTo = new Date(params.cartesAvailableTo_month+"/"+params.cartesAvailableTo_day+"/"+params.cartesAvailableTo_year+" 00:00")
			if (params.translationsAvailableTo_value)
				translationsAvailableTo = new Date(params.translationsAvailableTo_month+"/"+params.translationsAvailableTo_day+"/"+params.translationsAvailableTo_year+" 00:00")
			if (params.eventsAvailableTo_value)
				eventsAvailableTo = new Date(params.eventsAvailableTo_month+"/"+params.eventsAvailableTo_day+"/"+params.eventsAvailableTo_year+" 00:00")
			if (params.inquiryAvailableTo_value)
				inquiryAvailableTo = new Date(params.inquiryAvailableTo_month+"/"+params.inquiryAvailableTo_day+"/"+params.inquiryAvailableTo_year+" 00:00")
			if (params.sellTicketsOnlineAvailableTo_value)
				sellTicketsOnlineAvailableTo = new Date(params.sellTicketsOnlineAvailableTo_month+"/"+params.sellTicketsOnlineAvailableTo_day+"/"+params.sellTicketsOnlineAvailableTo_year+" 00:00")
			
			if (params.inquiryAvailable) {
				def http = new HTTPBuilder( 'http://encuestas.nidoapp.com')
				
				// Hace login como Administrador
				http.request( GET ) {
				  uri.path = '/admin/index.php'
				  uri.query = [ admin:user.username, password: user.password ]
				}
				
				// Da de alta el usuario y pub en la aplicación de encuestas
				http.request( GET ) {
				  uri.path = '/admin/admin_habilitar.php'
				  uri.query = [ u:pub.owner.username, p: pub.owner.password, r: pub.uri, rnombre: pub.name ]
				}
			}
			
			/*if (reservationsAvailable == null) {
				reservationsAvailable = false
			}*/
			if (cartesAvailable == null) {
				cartesAvailable = false
			}
			if (translationsAvailable == null) {
				translationsAvailable = false
			}
			if (eventsAvailable == null) {
				eventsAvailable = false
			}
			if (inquiryAvailable == null) {
				inquiryAvailable = false
			}
			if (sellTicketsOnlineAvailable == null) {
				sellTicketsOnlineAvailable = false
			}
			
			//pub.reservationsAvailable = reservationsAvailable
			pub.cartesAvailable = cartesAvailable
			pub.translationsAvailable = translationsAvailable
			pub.eventsAvailable = eventsAvailable
			pub.inquiryAvailable = inquiryAvailable
			pub.sellTicketsOnlineAvailable = sellTicketsOnlineAvailable
			
			pub.cartesAvailableFrom = cartesAvailableFrom
			pub.translationsAvailableFrom = translationsAvailableFrom
			pub.eventsAvailableFrom = eventsAvailableFrom
			pub.inquiryAvailableFrom = inquiryAvailableFrom
			pub.sellTicketsOnlineAvailableFrom = sellTicketsOnlineAvailableFrom
			
			pub.cartesAvailableTo = cartesAvailableTo
			pub.translationsAvailableTo = translationsAvailableTo
			pub.eventsAvailableTo = eventsAvailableTo
			pub.inquiryAvailableTo = inquiryAvailableTo
			pub.sellTicketsOnlineAvailableTo = sellTicketsOnlineAvailableTo
			
			
			pub.save(flush:true, failOnError:true)
				
			redirect(url: "/superAdminPubControl/index/"+pub.uri)
		}
		else
		{
			log.warn "Errores en los datos: " + cmd?.errors
			chain action: "editPubServices", model: [cmd: cmd], id: cmd.pubUri
		}
		
		

	}
	
	/**
	 * Muestra la ventana de edicion de datos financieros de venta de
	 * entradas online de Pub
	 */
	def editPubFinance = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pub: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SUPERADMIN"
			response.sendError(404)
		}

		
		[user:user, pub: pub]

	}
	
	/**
	 * Guarda los datos financieros de venta de entradas online de pub indicado 
	 * en la ventana de edicion
	 */
	def savePubFinance = { PubFinanceCommand cmd ->
		
		
		if(cmd.validate())
		{
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			
			
			def pub = Pub.findByUri(cmd.pubUri)
			
			
			if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
			{
				log.warn "El usuario '${user?.username}' no es SUPERADMIN"
				response.sendError(404)
			}
			
			
			def iban = params.iban
			def sepa = params.sepa
			
			def businessName = params.businessName
			def contactPerson = params.contactPerson
			def contactTelephone = params.contactTelephone
			def nif = params.nif
			def invoiceAddress = params.invoiceAddress
			
			if (sepa == null) {
				sepa = false
			}
			
			
			pub.iban = iban
			pub.sepa = sepa
			
			pub.businessName = businessName
			pub.contactPerson = contactPerson
			pub.contactTelephone = contactTelephone
			pub.nif = nif
			pub.invoiceAddress = invoiceAddress
			
			
			pub.save(flush:true, failOnError:true)
				
			redirect(url: "/superAdminPubControl/index/"+pub.uri)
		}
		else
		{
			log.warn "Errores en los datos: " + cmd?.errors
			chain action: "editPubFinance", model: [cmd: cmd], id: cmd.pubUri
		}
		
		

	}
	
	/**
	 * Muestra la ventana de edicion de datos de configuración de TPV de la sala
	 */
	def editPubTPVConfig = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pub: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SUPERADMIN"
			response.sendError(404)
		}

		
		[user:user, pub: pub]

	}
	
	/**
	 * Guarda los datos de configuración del TPV de la sala
	 */
	def savePubTPVConfig = { PubTPVConfigCommand cmd ->
		
		
		if(cmd.validate())
		{
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			
			
			def pub = Pub.findByUri(cmd.pubUri)
			
			
			if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
			{
				log.warn "El usuario '${user?.username}' no es SUPERADMIN"
				response.sendError(404)
			}
			
			
			def encryptionKey = params.encryptionKey
			def merchantId = params.merchantId
			def acquirerBIN = params.acquirerBIN
			def terminalId = params.terminalId
			
			
			pub.encryptionKey = encryptionKey
			pub.merchantId = merchantId
			pub.acquirerBIN = acquirerBIN
			pub.terminalId = terminalId
			
			
			pub.save(flush:true, failOnError:true)
				
			redirect(url: "/superAdminPubControl/index/"+pub.uri)
		}
		else
		{
			log.warn "Errores en los datos: " + cmd?.errors
			chain action: "editPubFinance", model: [cmd: cmd], id: cmd.pubUri
		}
		
		

	}
	
	/**
	 * Muestra la ventana de edicion de código de acceso a apps de Sala.
	 */
	def editPubAppsAuthenticationCode = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
	
		
		log.debug "User: " + user?.properties
				
		log.debug "Pub: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SUPERADMIN"
			response.sendError(404)
		}

		
		[user:user, pub: pub]

	}
	
	/**
	 * Guarda el código de acceso a apps de la Sala.
	 */
	def savePubAppsAuthenticationCode = { PubAppsAuthenticationCodeCommand cmd ->
		
		
		if(cmd.validate())
		{
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			
			
			def pub = Pub.findByUri(cmd.pubUri)
			
			
			if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
			{
				log.warn "El usuario '${user?.username}' no es SUPERADMIN"
				response.sendError(404)
			}
			
			
			def appsAuthenticationCode = params.appsAuthenticationCode
			
			
			pub.appsAuthenticationCode = appsAuthenticationCode
			
			pub.save(flush:true, failOnError:true)
				
			redirect(url: "/superAdminPubControl/index/"+pub.uri)
		}
		else
		{
			log.warn "Errores en los datos: " + cmd?.errors
			chain action: "editPubAppsAuthenticationCode", model: [cmd: cmd], id: cmd.pubUri
		}
		
		

	}
	
	/**
	 * Guarda si un pub ha indicado en la ventana de edicion que la encuesta está activa o no
	 */
	def savePubInquiryActive = {
		
		
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			
			
			def pub = Pub.findByUri(params.pubUri)
			
			
			if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_PUB_OWNER)
			{
				log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
				response.sendError(404)
			}
			
			def inquiryActive = params.inquiryActive
			if (params.inquiryActive == null) {
				inquiryActive = false
			}
			
			
			pub.inquiryActive = inquiryActive
			
			
			pub.save(flush:true, failOnError:true)
				
			redirect(url: "/pub/pubInquiry/"+pub.uri)
		

	}
	
	
	/**
	 * Muestra la ventana de edicion de otros datos
	 */
	def editOtherData = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}

		
		[user:user, pub: pub]

	}
	
	/**
	 * Guarda otros datos indicados en la ventana de edicion
	 */
	def saveOtherData = { OtherDataCommand cmd ->
		
		if(cmd.validate())
		{
			def principal = springSecurityService.principal
			def user = User.get(principal.id)
			
			
			log.debug "User: " + user?.properties
					
			log.debug "Pubs: " + user.pubs
			
			def pub = Pub.findByUri(cmd.pubUri)
			
			if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
			{
				log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
				response.sendError(404)
			}
			
			pub.customerCapacity = Integer.parseInt(cmd.customerCapacity)
			pub.openingHours = cmd.openingHours
			//pub.closingDays = cmd.closingDays
			//pub.closingSpecial = cmd.closingSpecial
			
			if (cmd.cartePriceFrom == "")
				pub.cartePriceFrom = null
			else
				pub.cartePriceFrom = Double.parseDouble(cmd.cartePriceFrom)
			
			if (cmd.cartePriceTo == "")
				pub.cartePriceTo = null
			else
				pub.cartePriceTo = Double.parseDouble(cmd.cartePriceTo)
			
			
			pub.save(flush:true, failOnError:true)
				
			redirect(action: "index", id: pub?.uri)
		}
		else
		{
			log.warn "Errores en los datos: " + cmd?.errors
			chain action: "editOtherData", model: [cmd: cmd], id: cmd.pubUri
		}
		
		

	}
	
	/** Muestra la ventana de adminitración de encuesta del pub */
	def pubInquiry() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.info "PARAMS: " + params
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_PUB_OWNER)
			{
				log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
				response.sendError(404)
			}
		log.info "PUB: " + pub

		pub.properties = params

		//flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
		//redirect(controller:"account", action: "index", model: [id: userInstance.id, userInstance:userInstance])
		[pub: pub, user: user]
	}
	
	
	/** Muestra la ventana de servicio NO CONTRATADO */
	def pubInquiryDenied() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.info "PARAMS: " + params
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_PUB_OWNER)
			{
				log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
				response.sendError(404)
			}
		log.info "PUB: " + pub

		pub.properties = params

		//flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
		//redirect(controller:"account", action: "index", model: [id: userInstance.id, userInstance:userInstance])
		[pub: pub, user: user]
	}
	
	/**
	 * Muestra la ventana de información de facturación de venta de
	 * entradas online de Pub
	 */
	def listPubTicketsSoldInvoices = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pub: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(UserRole.findByUser(user).role.authority!=Role.ROLE_SUPERADMIN)
		{
			log.warn "El usuario '${user?.username}' no es SUPERADMIN"
			response.sendError(404)
		}

		
		[user:user, pub: pub]

	}
	
	
	
	
	
	/**
	 * Muestra la ventana de edicion de imagenes promocionales, que permite
	 * incluir nuevas imagenes, eliminarlas, o modificarlas
	 */
	def editPromotionalImages = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}

		
		[user:user, pub: pub]

	}
	
	/**
	 * Muestra la ventana de nueva imagen promocional
	 */
	def editNewPromotionalImage = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}

		
		[user:user, pub: pub]

	}
	
	/**
	 * Guarda la imagen indicada en la ventana de nueva imagen promocional
	 */
	def saveNewPromotionalImage = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.pubUri)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
			
			
		def f = request.getFile('photoFile')
		def dateFrom = new Date(params.dateFrom_month + '/' + params.dateFrom_day + '/' + params.dateFrom_year + ' 00:00:00')
		def dateTo = new Date(params.dateTo_month + '/' + params.dateTo_day + '/' + params.dateTo_year + ' 23:59:59')
		
		if (f && !f.empty) {
			
			def repositoryImageId = imageRepositoryService.saveImage(f.inputStream)
			if(repositoryImageId)
			{
				def promotionalImage = new PubPromotionalImage()
				promotionalImage.image = repositoryImageId
				promotionalImage.dateFrom = dateFrom
				promotionalImage.dateTo = dateTo
				promotionalImage.pub = pub
				
				promotionalImage.save(flush:true, failOnError:true)
				
				log.info "Incluyendo imagen: " + promotionalImage.image
				
				pub.addToPromotionalImages(promotionalImage)
				
				pub.save(flush:true, failOnError:true)
			}
		}
		
		redirect(action: "editPromotionalImages", id: pub?.uri)
	}
	
	/**
	 * Muestra la ventana de edicion de imagen promocional
	 */
	def editPromotionalImage = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pub: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
			
		def imageId = params.image
		def image = PubPromotionalImage.findByPubAndImage(pub, imageId)

		
		[user:user, pub: pub, promotionalImage: image]

	}
	
	/**
	 * Guarda la imagen indicada en la ventana de edicion de imagen promocional
	 */
	def savePromotionalImage = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.pubUri)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
			
		
		def imageId = params.repositoryImageId
		def f = request.getFile('photoFile')
		def dateFrom = new Date(params.dateFrom_month + '/' + params.dateFrom_day + '/' + params.dateFrom_year + ' 00:00:00')
		def dateTo = new Date(params.dateTo_month + '/' + params.dateTo_day + '/' + params.dateTo_year + ' 23:59:59')
		
		def image = PubPromotionalImage.findByPubAndImage(pub, imageId)
		
		if(image)
		{
			if (f && !f.empty) {
				
				def repositoryImageId = imageRepositoryService.saveImage(f.inputStream)
				if(repositoryImageId)
				{
					image.image = repositoryImageId

				}
			}
			
			image.dateFrom = dateFrom
			image.dateTo = dateTo
			
			image.save(flush:true, failOnError:true)
		}
		
		
		
		redirect(action: "editPromotionalImages", id: pub?.uri)
	}
	
	/**
	 * Elimina una imagen promocional
	 */
	def removePromotionalImage = {
		
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		def pub = Pub.findByUri(params.id)
		
		if(pub?.owner != user && UserRole.findByUser(user).role.authority!=Role.ROLE_ASSOCIATION_OWNER)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
			
		def imageId = params.image
		
		def image = PubPromotionalImage.findByImageAndPub(imageId, pub)
		
		if(image)
		{
			
			pub.removeFromPromotionalImages(image)
			image.delete(flush:true, failOnError:true)
			
			pub.save(flush:true, failOnError:true)
			
		}
		
		redirect(action: "editPromotionalImages", id: pub?.uri)
	}
	
	
}

/**
 * Encapsula los datos que se recogen del formulario de edicion de datos basicos 
 * @author Developer
 *
 */
class BasicInfoCommand{
	String name
	String description
	String telephone
	String fax
	String email
	String website
	String pubUri

	static constraints = {
		name blank:false
		description blank:false
		telephone blank:false, matches: "[0-9]{9}"
		fax nullable: true, matches: "[0-9]{9}"
		email blank:false, email:true
		website blank:false, url:true

	}
}

/**
 * Encapsula los datos que se recogen del formulario de edicion de especialidad y tipo de música
 * @author Developer
 *
 */
class SpecialtyCommand{
	String specialty
	String musicTypeId
	String pubUri

	static constraints = {
		specialty blank:false
		musicTypeId nullable:true
	}
}

/**
 * Encapsula los datos que se recogen del formulario de edicion de ubicacion
 * @author Developer
 *
 */
class LocationCommand{
	String latitude
	String longitude
	String address
	String pubUri

	static constraints = {
		latitude blank:false, matches: "[-]?[0-9]+([.][0-9]+)?"
		longitude blank:false, matches: "[-]?[0-9]+([.][0-9]+)?"
		address blank:false
	}
}

/**
 * Encapsula los datos que se recogen del formulario de edicion de configuracion de reserva
 * @author Developer
 *
 */
/*class ReservationSettingsCommand{
	String reservationEmail
	String reservationPhone
	String pubUri
	
	def lunesM
	def lunesN
	def martesM
	def martesN
	def miercolesM
	def miercolesN
	def juevesM
	def juevesN
	def viernesM
	def viernesN
	def sabadoM
	def sabadoN
	def domingoM
	def domingoN

	static constraints = {
		reservationPhone nullable: true, matches: "6[0-9]{8}"
		reservationEmail nullable: true, email: true
	}
}*/

class EMailSettingsCommand{
	String pubUri
	
	String mailAddress
	String mailHost
	int mailPort
	String mailUsername
	String mailPassword

	static constraints = {
		mailAddress nullable: false
		mailHost nullable: false
		mailPort nullable: false
		mailUsername nullable: false
		mailPassword nullable: false
	}
}

/**
 * Encapsula los datos que se recogen del formulario de edicion de configuracion de venta de
 * entradas online
 * @author Developer
 *
 */
class TicketOnlineSaleSettingsCommand{
	String pubUri
	String businessName
	String contactPerson
	String contactTelephone
	String nif
	String invoiceAddress

	static constraints = {
		businessName nullable: false
		contactPerson nullable: false
		contactTelephone nullable: false, matches: "[0-9]{9}"
		nif nullable: false
		invoiceAddress nullable: false
	}
}

/**
 * Encapsula los datos que se recogen del formulario de edicion de configuracion de
 * otros datos de venta de entradas online
 * @author Developer
 *
 */
class TicketOnlineSaleOtherSettingsCommand{
	String pubUri
	int ticketsSaleInitNumber
	Date ticketsSaleInitNumberReset

	static constraints = {
		ticketsSaleInitNumberReset nullable:true
	}
}

/**
 * Encapsula los datos que se recogen del formulario de edicion de datos financieros de
 * venta de entradas online de pub
 * @author Developer
 *
 */
class PubFinanceCommand{
	String pubUri

	static constraints = {
		
	}
}

/**
 * Encapsula los datos que se recogen del formulario de edicion de datos de configuración
 * de TPV de la sala
 * @author Developer
 *
 */
class PubTPVConfigCommand{
	String pubUri

	static constraints = {
		
	}
}

/**
 * Encapsula los datos que se recogen del formulario de edicion de código de
 * acceso a apps de la Sala
 * @author Developer
 *
 */
class PubAppsAuthenticationCodeCommand{
	String pubUri

	static constraints = {
		
	}
}

/**
 * Encapsula los datos que se recogen del formulario de edicion de servicios de pub
 * @author Developer
 *
 */
class PubServicesCommand{
	String pubUri
	
	//def reservationsAvailable
	def cartesAvailable
	def translationsAvailable
	def eventsAvailable
	def inquiryAvalable

	static constraints = {
		
	}
}

/**
 * Encapsula los datos que se recogen del formulario de edicion de otros datos
 * @author Developer
 *
 */
class OtherDataCommand{
	String pubUri
	
	String customerCapacity
	String openingHours
	//String closingDays
	//String closingSpecial
	String cartePriceFrom
	String cartePriceTo

	static constraints = {
		customerCapacity blank: false
		openingHours blank: false
		//closingDays blank: false
		//closingSpecial blank: false
	}
}
