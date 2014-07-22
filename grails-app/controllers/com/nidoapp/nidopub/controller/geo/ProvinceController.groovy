package com.nidoapp.nidopub.controller.geo

import org.springframework.dao.DataIntegrityViolationException

import com.nidoapp.nidopub.domain.geo.Province;

class ProvinceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [provinceInstanceList: Province.list(params), provinceInstanceTotal: Province.count()]
    }

    def create() {
        [provinceInstance: new Province(params)]
    }

    def save() {
        def provinceInstance = new Province(params)
        if (!provinceInstance.save(flush: true)) {
            render(view: "create", model: [provinceInstance: provinceInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'province.label', default: 'Province'), provinceInstance.id])
        redirect(action: "show", id: provinceInstance.id)
    }

    def show(Long id) {
        def provinceInstance = Province.get(id)
        if (!provinceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'province.label', default: 'Province'), id])
            redirect(action: "list")
            return
        }

        [provinceInstance: provinceInstance]
    }

    def edit(Long id) {
        def provinceInstance = Province.get(id)
        if (!provinceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'province.label', default: 'Province'), id])
            redirect(action: "list")
            return
        }

        [provinceInstance: provinceInstance]
    }

    def update(Long id, Long version) {
        def provinceInstance = Province.get(id)
        if (!provinceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'province.label', default: 'Province'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (provinceInstance.version > version) {
                provinceInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'province.label', default: 'Province')] as Object[],
                          "Another user has updated this Province while you were editing")
                render(view: "edit", model: [provinceInstance: provinceInstance])
                return
            }
        }

        provinceInstance.properties = params

        if (!provinceInstance.save(flush: true)) {
            render(view: "edit", model: [provinceInstance: provinceInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'province.label', default: 'Province'), provinceInstance.id])
        redirect(action: "show", id: provinceInstance.id)
    }

    def delete(Long id) {
        def provinceInstance = Province.get(id)
        if (!provinceInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'province.label', default: 'Province'), id])
            redirect(action: "list")
            return
        }

        try {
            provinceInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'province.label', default: 'Province'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'province.label', default: 'Province'), id])
            redirect(action: "show", id: id)
        }
    }
}
