package com.nidoapp.nidopub.controller

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException

import grails.plugins.springsecurity.Secured;

import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.CarteSection;
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.Product;

class ProductController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def springSecurityService
	
	def imageRepositoryService

    def index() {
        redirect(action: "create", params: params)
    }

    /*def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [productInstanceList: Product.list(params), productInstanceTotal: Product.count()]
    }*/
	
	def list(Integer max, long id_pub) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		params.max = Math.min(max ?: 10, 100)
		
		def pub = Pub.findById(params.id_pub)
		
		id_pub = pub.id
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
		def productInstanceList = Product.findAllByPub_id(id_pub)
		
		
		[productInstanceList: productInstanceList, productInstanceTotal: Product.count()]
	}

    def create() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
				
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(params.id_pub)
		log.info params.id_pub
		log.info pub.id
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        [productInstance: new Product(params), user:user, pub:pub, id_pub:pub.id]
    }

    def save() {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
        def productInstance = new Product(params)
		
		def pub = Pub.findById(params.id_pub)
		
		productInstance.pub_id = pub.id
		
		saveNewPhoto(productInstance)
		
        if (!productInstance.save(flush: true)) {
            render(view: "create", model: [productInstance: productInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'product.label', default: 'Product'), productInstance.id])
		
		redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
    }

    def show(Long id) {
        def productInstance = Product.get(id)
        if (!productInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), id])
            redirect(action: "list")
            return
        }

        [productInstance: productInstance]
    }

    def edit(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(Product.findById(id).pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def productInstance = Product.get(id)
        if (!productInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), id])
            redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
            return
        }

        [productInstance: productInstance, user:user, pub:pub]
    }

    def update(Long id, Long version) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(Product.findById(id).pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def productInstance = Product.get(id)
		
        if (!productInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), id])
            redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
            return
        }
		
		saveNewPhoto(productInstance)

        if (version != null) {
            if (productInstance.version > version) {
                productInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'product.label', default: 'Product')] as Object[],
                          "Another user has updated this Product while you were editing")
                render(view: "edit", model: [productInstance: productInstance, user:user, pub:pub])
                return
            }
        }

        productInstance.properties = params

        if (!productInstance.save(flush: true)) {
            render(view: "edit", model: [productInstance: productInstance, user:user, pub:pub])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'product.label', default: 'Product'), productInstance.id])
        redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
    }

    def delete(Long id) {
		def principal = springSecurityService.principal
		def user = User.get(principal.id)
		
		log.debug "User: " + user?.properties
		
		log.debug "Pubs: " + user.pubs
		
		//def pub = Pub.findByUri(params.id)
		def pub = Pub.findById(Product.findById(id).pub_id)
		
		log.debug "Pub: " + pub?.properties
		
		if(pub?.owner != user)
		{
			log.warn "El usuario '${user?.username}' no es propietario del pub con ID '${pub?.id}'"
			response.sendError(404)
		}
		
        def productInstance = Product.get(id)
        if (!productInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'product.label', default: 'Product'), id])
            redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
            return
        }
		
		
		if (productInstance.carteSections.size() > 0) {
			
			def carteSections = productInstance.carteSections
			
			carteSections.eachWithIndex { item, index ->
				item.products.remove(productInstance)
			}
		}
		

        try {
            productInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'product.label', default: 'Product'), id])
            redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'product.label', default: 'Product'), id])
            redirect(action: "edit", model: [productInstance: productInstance, user:user, pub:pub])
        }
		catch (ConstraintViolationException e) {
			redirect(uri: "/cartesAndProducts/index/" + pub.uri, model:[user:user,pub:pub])
		}
    }
	
	/**
	 * Guarda la foto indicada en la ventana de nueva foto
	 */
	def saveNewPhoto(Product product) {
			
		def f = request.getFile('photoFile')
		
		if (f && !f.empty) {
			
			def repositoryImageId = imageRepositoryService.saveImage(f.inputStream)
			if(repositoryImageId)
			{
				
				product.photo = repositoryImageId
				
			}
		}
	}
	
}
