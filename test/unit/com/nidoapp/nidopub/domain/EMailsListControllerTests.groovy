package com.nidoapp.nidopub.domain



import org.junit.*

import com.nidoapp.nidopub.controller.EMailsListController;

import grails.test.mixin.*

@TestFor(EMailsListController)
@Mock(EMailsList)
class EMailsListControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/EMailsList/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.EMailsListInstanceList.size() == 0
        assert model.EMailsListInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.EMailsListInstance != null
    }

    void testSave() {
        controller.save()

        assert model.EMailsListInstance != null
        assert view == '/EMailsList/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/EMailsList/show/1'
        assert controller.flash.message != null
        assert EMailsList.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/EMailsList/list'

        populateValidParams(params)
        def EMailsList = new EMailsList(params)

        assert EMailsList.save() != null

        params.id = EMailsList.id

        def model = controller.show()

        assert model.EMailsListInstance == EMailsList
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/EMailsList/list'

        populateValidParams(params)
        def EMailsList = new EMailsList(params)

        assert EMailsList.save() != null

        params.id = EMailsList.id

        def model = controller.edit()

        assert model.EMailsListInstance == EMailsList
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/EMailsList/list'

        response.reset()

        populateValidParams(params)
        def EMailsList = new EMailsList(params)

        assert EMailsList.save() != null

        // test invalid parameters in update
        params.id = EMailsList.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/EMailsList/edit"
        assert model.EMailsListInstance != null

        EMailsList.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/EMailsList/show/$EMailsList.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        EMailsList.clearErrors()

        populateValidParams(params)
        params.id = EMailsList.id
        params.version = -1
        controller.update()

        assert view == "/EMailsList/edit"
        assert model.EMailsListInstance != null
        assert model.EMailsListInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/EMailsList/list'

        response.reset()

        populateValidParams(params)
        def EMailsList = new EMailsList(params)

        assert EMailsList.save() != null
        assert EMailsList.count() == 1

        params.id = EMailsList.id

        controller.delete()

        assert EMailsList.count() == 0
        assert EMailsList.get(EMailsList.id) == null
        assert response.redirectedUrl == '/EMailsList/list'
    }
}
