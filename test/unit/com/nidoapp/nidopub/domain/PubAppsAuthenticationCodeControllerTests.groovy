package com.nidoapp.nidopub.domain



import org.junit.*

import com.nidoapp.nidopub.controller.PubAppsAuthenticationCodeController;

import grails.test.mixin.*

@TestFor(PubAppsAuthenticationCodeController)
@Mock(PubAppsAuthenticationCode)
class PubAppsAuthenticationCodeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/pubAppsAuthenticationCode/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.pubAppsAuthenticationCodeInstanceList.size() == 0
        assert model.pubAppsAuthenticationCodeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.pubAppsAuthenticationCodeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.pubAppsAuthenticationCodeInstance != null
        assert view == '/pubAppsAuthenticationCode/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/pubAppsAuthenticationCode/show/1'
        assert controller.flash.message != null
        assert PubAppsAuthenticationCode.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/pubAppsAuthenticationCode/list'

        populateValidParams(params)
        def pubAppsAuthenticationCode = new PubAppsAuthenticationCode(params)

        assert pubAppsAuthenticationCode.save() != null

        params.id = pubAppsAuthenticationCode.id

        def model = controller.show()

        assert model.pubAppsAuthenticationCodeInstance == pubAppsAuthenticationCode
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/pubAppsAuthenticationCode/list'

        populateValidParams(params)
        def pubAppsAuthenticationCode = new PubAppsAuthenticationCode(params)

        assert pubAppsAuthenticationCode.save() != null

        params.id = pubAppsAuthenticationCode.id

        def model = controller.edit()

        assert model.pubAppsAuthenticationCodeInstance == pubAppsAuthenticationCode
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/pubAppsAuthenticationCode/list'

        response.reset()

        populateValidParams(params)
        def pubAppsAuthenticationCode = new PubAppsAuthenticationCode(params)

        assert pubAppsAuthenticationCode.save() != null

        // test invalid parameters in update
        params.id = pubAppsAuthenticationCode.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/pubAppsAuthenticationCode/edit"
        assert model.pubAppsAuthenticationCodeInstance != null

        pubAppsAuthenticationCode.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/pubAppsAuthenticationCode/show/$pubAppsAuthenticationCode.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        pubAppsAuthenticationCode.clearErrors()

        populateValidParams(params)
        params.id = pubAppsAuthenticationCode.id
        params.version = -1
        controller.update()

        assert view == "/pubAppsAuthenticationCode/edit"
        assert model.pubAppsAuthenticationCodeInstance != null
        assert model.pubAppsAuthenticationCodeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/pubAppsAuthenticationCode/list'

        response.reset()

        populateValidParams(params)
        def pubAppsAuthenticationCode = new PubAppsAuthenticationCode(params)

        assert pubAppsAuthenticationCode.save() != null
        assert PubAppsAuthenticationCode.count() == 1

        params.id = pubAppsAuthenticationCode.id

        controller.delete()

        assert PubAppsAuthenticationCode.count() == 0
        assert PubAppsAuthenticationCode.get(pubAppsAuthenticationCode.id) == null
        assert response.redirectedUrl == '/pubAppsAuthenticationCode/list'
    }
}
