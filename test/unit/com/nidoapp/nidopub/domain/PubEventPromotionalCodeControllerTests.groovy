package com.nidoapp.nidopub.domain



import org.junit.*

import com.nidoapp.nidopub.controller.PubEventPromotionalCodeController;

import grails.test.mixin.*

@TestFor(PubEventPromotionalCodeController)
@Mock(PubEventPromotionalCode)
class PubEventPromotionalCodeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/pubEventPromotionalCode/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.pubEventPromotionalCodeInstanceList.size() == 0
        assert model.pubEventPromotionalCodeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.pubEventPromotionalCodeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.pubEventPromotionalCodeInstance != null
        assert view == '/pubEventPromotionalCode/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/pubEventPromotionalCode/show/1'
        assert controller.flash.message != null
        assert PubEventPromotionalCode.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/pubEventPromotionalCode/list'

        populateValidParams(params)
        def pubEventPromotionalCode = new PubEventPromotionalCode(params)

        assert pubEventPromotionalCode.save() != null

        params.id = pubEventPromotionalCode.id

        def model = controller.show()

        assert model.pubEventPromotionalCodeInstance == pubEventPromotionalCode
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/pubEventPromotionalCode/list'

        populateValidParams(params)
        def pubEventPromotionalCode = new PubEventPromotionalCode(params)

        assert pubEventPromotionalCode.save() != null

        params.id = pubEventPromotionalCode.id

        def model = controller.edit()

        assert model.pubEventPromotionalCodeInstance == pubEventPromotionalCode
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/pubEventPromotionalCode/list'

        response.reset()

        populateValidParams(params)
        def pubEventPromotionalCode = new PubEventPromotionalCode(params)

        assert pubEventPromotionalCode.save() != null

        // test invalid parameters in update
        params.id = pubEventPromotionalCode.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/pubEventPromotionalCode/edit"
        assert model.pubEventPromotionalCodeInstance != null

        pubEventPromotionalCode.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/pubEventPromotionalCode/show/$pubEventPromotionalCode.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        pubEventPromotionalCode.clearErrors()

        populateValidParams(params)
        params.id = pubEventPromotionalCode.id
        params.version = -1
        controller.update()

        assert view == "/pubEventPromotionalCode/edit"
        assert model.pubEventPromotionalCodeInstance != null
        assert model.pubEventPromotionalCodeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/pubEventPromotionalCode/list'

        response.reset()

        populateValidParams(params)
        def pubEventPromotionalCode = new PubEventPromotionalCode(params)

        assert pubEventPromotionalCode.save() != null
        assert PubEventPromotionalCode.count() == 1

        params.id = pubEventPromotionalCode.id

        controller.delete()

        assert PubEventPromotionalCode.count() == 0
        assert PubEventPromotionalCode.get(pubEventPromotionalCode.id) == null
        assert response.redirectedUrl == '/pubEventPromotionalCode/list'
    }
}
