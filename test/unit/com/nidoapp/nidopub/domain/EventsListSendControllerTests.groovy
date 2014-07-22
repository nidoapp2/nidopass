package com.nidoapp.nidopub.domain



import org.junit.*

import com.nidoapp.nidopub.controller.EventsListSendController;

import grails.test.mixin.*

@TestFor(EventsListSendController)
@Mock(EventsListSend)
class EventsListSendControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/eventsListSend/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.eventsListSendInstanceList.size() == 0
        assert model.eventsListSendInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.eventsListSendInstance != null
    }

    void testSave() {
        controller.save()

        assert model.eventsListSendInstance != null
        assert view == '/eventsListSend/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/eventsListSend/show/1'
        assert controller.flash.message != null
        assert EventsListSend.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/eventsListSend/list'

        populateValidParams(params)
        def eventsListSend = new EventsListSend(params)

        assert eventsListSend.save() != null

        params.id = eventsListSend.id

        def model = controller.show()

        assert model.eventsListSendInstance == eventsListSend
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/eventsListSend/list'

        populateValidParams(params)
        def eventsListSend = new EventsListSend(params)

        assert eventsListSend.save() != null

        params.id = eventsListSend.id

        def model = controller.edit()

        assert model.eventsListSendInstance == eventsListSend
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/eventsListSend/list'

        response.reset()

        populateValidParams(params)
        def eventsListSend = new EventsListSend(params)

        assert eventsListSend.save() != null

        // test invalid parameters in update
        params.id = eventsListSend.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/eventsListSend/edit"
        assert model.eventsListSendInstance != null

        eventsListSend.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/eventsListSend/show/$eventsListSend.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        eventsListSend.clearErrors()

        populateValidParams(params)
        params.id = eventsListSend.id
        params.version = -1
        controller.update()

        assert view == "/eventsListSend/edit"
        assert model.eventsListSendInstance != null
        assert model.eventsListSendInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/eventsListSend/list'

        response.reset()

        populateValidParams(params)
        def eventsListSend = new EventsListSend(params)

        assert eventsListSend.save() != null
        assert EventsListSend.count() == 1

        params.id = eventsListSend.id

        controller.delete()

        assert EventsListSend.count() == 0
        assert EventsListSend.get(eventsListSend.id) == null
        assert response.redirectedUrl == '/eventsListSend/list'
    }
}
