package com.nidoapp.nidopub.domain



import org.junit.*

import com.nidoapp.nidopub.controller.PubFeatureController;
import com.nidoapp.nidopub.domain.PubFeature;

import grails.test.mixin.*

@TestFor(PubFeatureController)
@Mock(PubFeature)
class PubFeatureControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
       /* controller.index()
        assert "/pubFeature/list" == response.redirectedUrl*/
    }

    void testList() {

        /*def model = controller.list()

        assert model.pubFeatureInstanceList.size() == 0
        assert model.pubFeatureInstanceTotal == 0*/
    }

    void testCreate() {
       /* def model = controller.create()

        assert model.pubFeatureInstance != null*/
    }

    void testSave() {
        /*controller.save()

        assert model.pubFeatureInstance != null
        assert view == '/pubFeature/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/pubFeature/show/1'
        assert controller.flash.message != null
        assert PubFeature.count() == 1*/
    }

    void testShow() {
       /* controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/pubFeature/list'

        populateValidParams(params)
        def pubFeature = new PubFeature(params)

        assert pubFeature.save() != null

        params.id = pubFeature.id

        def model = controller.show()

        assert model.pubFeatureInstance == pubFeature*/
    }

    void testEdit() {
       /* controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/pubFeature/list'

        populateValidParams(params)
        def pubFeature = new PubFeature(params)

        assert pubFeature.save() != null

        params.id = pubFeature.id

        def model = controller.edit()

        assert model.pubFeatureInstance == pubFeature*/
    }

    void testUpdate() {
        /*controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/pubFeature/list'

        response.reset()

        populateValidParams(params)
        def pubFeature = new PubFeature(params)

        assert pubFeature.save() != null

        // test invalid parameters in update
        params.id = pubFeature.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/pubFeature/edit"
        assert model.pubFeatureInstance != null

        pubFeature.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/pubFeature/show/$pubFeature.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        pubFeature.clearErrors()

        populateValidParams(params)
        params.id = pubFeature.id
        params.version = -1
        controller.update()

        assert view == "/pubFeature/edit"
        assert model.pubFeatureInstance != null
        assert model.pubFeatureInstance.errors.getFieldError('version')
        assert flash.message != null*/
    }

    void testDelete() {
        /*controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/pubFeature/list'

        response.reset()

        populateValidParams(params)
        def pubFeature = new PubFeature(params)

        assert pubFeature.save() != null
        assert PubFeature.count() == 1

        params.id = pubFeature.id

        controller.delete()

        assert PubFeature.count() == 0
        assert PubFeature.get(pubFeature.id) == null
        assert response.redirectedUrl == '/pubFeature/list'*/
    }
}
