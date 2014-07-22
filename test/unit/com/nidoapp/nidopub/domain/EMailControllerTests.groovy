package com.nidoapp.nidopub.domain



import org.junit.*

import com.nidoapp.nidopub.controller.EMailController;

import grails.test.mixin.*

@TestFor(EMailController)
@Mock(EMail)
class EMailControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/EMail/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.EMailInstanceList.size() == 0
        assert model.EMailInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.EMailInstance != null
    }

    void testSave() {
        controller.save()

        assert model.EMailInstance != null
        assert view == '/EMail/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/EMail/show/1'
        assert controller.flash.message != null
        assert EMail.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/EMail/list'

        populateValidParams(params)
        def EMail = new EMail(params)

        assert EMail.save() != null

        params.id = EMail.id

        def model = controller.show()

        assert model.EMailInstance == EMail
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/EMail/list'

        populateValidParams(params)
        def EMail = new EMail(params)

        assert EMail.save() != null

        params.id = EMail.id

        def model = controller.edit()

        assert model.EMailInstance == EMail
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/EMail/list'

        response.reset()

        populateValidParams(params)
        def EMail = new EMail(params)

        assert EMail.save() != null

        // test invalid parameters in update
        params.id = EMail.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/EMail/edit"
        assert model.EMailInstance != null

        EMail.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/EMail/show/$EMail.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        EMail.clearErrors()

        populateValidParams(params)
        params.id = EMail.id
        params.version = -1
        controller.update()

        assert view == "/EMail/edit"
        assert model.EMailInstance != null
        assert model.EMailInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/EMail/list'

        response.reset()

        populateValidParams(params)
        def EMail = new EMail(params)

        assert EMail.save() != null
        assert EMail.count() == 1

        params.id = EMail.id

        controller.delete()

        assert EMail.count() == 0
        assert EMail.get(EMail.id) == null
        assert response.redirectedUrl == '/EMail/list'
    }
}
