package com.nidoapp.nidopub.domain



import org.junit.*

import com.nidoapp.nidopub.controller.PubEventController;
import com.nidoapp.nidopub.domain.PubEvent;

import grails.test.mixin.*

@TestFor(PubEventController)
@Mock(PubEvent)
class PubEventControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        /*controller.index()
        assert "/pubEvent/list" == response.redirectedUrl*/
    }

    void testList() {

       /* def model = controller.list()

        assert model.pubEventInstanceList.size() == 0
        assert model.pubEventInstanceTotal == 0*/
    }

    void testCreate() {
        /*def model = controller.create()

        assert model.pubEventInstance != null*/
    }

    void testSave() {
        /*controller.save()

        assert model.pubEventInstance != null
        assert view == '/pubEvent/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/pubEvent/show/1'
        assert controller.flash.message != null
        assert PubEvent.count() == 1*/
    }

    void testShow() {
        /*controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/pubEvent/list'

        populateValidParams(params)
        def pubEvent = new PubEvent(params)

        assert pubEvent.save() != null

        params.id = pubEvent.id

        def model = controller.show()

        assert model.pubEventInstance == pubEvent*/
    }

    void testEdit() {
        /*controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/pubEvent/list'

        populateValidParams(params)
        def pubEvent = new PubEvent(params)

        assert pubEvent.save() != null

        params.id = pubEvent.id

        def model = controller.edit()

        assert model.pubEventInstance == pubEvent*/
    }

    void testUpdate() {
        /*controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/pubEvent/list'

        response.reset()

        populateValidParams(params)
        def pubEvent = new PubEvent(params)

        assert pubEvent.save() != null

        // test invalid parameters in update
        params.id = pubEvent.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/pubEvent/edit"
        assert model.pubEventInstance != null

        pubEvent.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/pubEvent/show/$pubEvent.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        pubEvent.clearErrors()

        populateValidParams(params)
        params.id = pubEvent.id
        params.version = -1
        controller.update()

        assert view == "/pubEvent/edit"
        assert model.pubEventInstance != null
        assert model.pubEventInstance.errors.getFieldError('version')
        assert flash.message != null*/
    }

    void testDelete() {
        /*controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/pubEvent/list'

        response.reset()

        populateValidParams(params)
        def pubEvent = new PubEvent(params)

        assert pubEvent.save() != null
        assert PubEvent.count() == 1

        params.id = pubEvent.id

        controller.delete()

        assert PubEvent.count() == 0
        assert PubEvent.get(pubEvent.id) == null
        assert response.redirectedUrl == '/pubEvent/list'*/
    }
}
