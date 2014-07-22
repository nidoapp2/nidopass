package com.nidoapp.nidopub.controller

import org.springframework.dao.DataIntegrityViolationException

import com.nidoapp.nidopub.domain.ProductValorations;

class ProductValorationsController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [productValorationsInstanceList: ProductValorations.list(params), productValorationsInstanceTotal: ProductValorations.count()]
    }

    def create() {
        [productValorationsInstance: new ProductValorations(params)]
    }

    def save() {
        def productValorationsInstance = new ProductValorations(params)
        if (!productValorationsInstance.save(flush: true)) {
            render(view: "create", model: [productValorationsInstance: productValorationsInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'productValorations.label', default: 'ProductValorations'), productValorationsInstance.id])
        redirect(action: "show", id: productValorationsInstance.id)
    }

    def show(Long id) {
        def productValorationsInstance = ProductValorations.get(id)
        if (!productValorationsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'productValorations.label', default: 'ProductValorations'), id])
            redirect(action: "list")
            return
        }

        [productValorationsInstance: productValorationsInstance]
    }

    def edit(Long id) {
        def productValorationsInstance = ProductValorations.get(id)
        if (!productValorationsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'productValorations.label', default: 'ProductValorations'), id])
            redirect(action: "list")
            return
        }

        [productValorationsInstance: productValorationsInstance]
    }

    def update(Long id, Long version) {
        def productValorationsInstance = ProductValorations.get(id)
        if (!productValorationsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'productValorations.label', default: 'ProductValorations'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (productValorationsInstance.version > version) {
                productValorationsInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'productValorations.label', default: 'ProductValorations')] as Object[],
                          "Another user has updated this ProductValorations while you were editing")
                render(view: "edit", model: [productValorationsInstance: productValorationsInstance])
                return
            }
        }

        productValorationsInstance.properties = params

        if (!productValorationsInstance.save(flush: true)) {
            render(view: "edit", model: [productValorationsInstance: productValorationsInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'productValorations.label', default: 'ProductValorations'), productValorationsInstance.id])
        redirect(action: "show", id: productValorationsInstance.id)
    }

    def delete(Long id) {
        def productValorationsInstance = ProductValorations.get(id)
        if (!productValorationsInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'productValorations.label', default: 'ProductValorations'), id])
            redirect(action: "list")
            return
        }

        try {
            productValorationsInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'productValorations.label', default: 'ProductValorations'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'productValorations.label', default: 'ProductValorations'), id])
            redirect(action: "show", id: id)
        }
    }
}
