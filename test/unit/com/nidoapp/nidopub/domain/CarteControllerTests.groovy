package com.nidoapp.nidopub.domain



import org.junit.*

import com.nidoapp.nidopub.controller.CarteController;
import com.nidoapp.nidopub.domain.Carte;

import grails.test.mixin.*

@TestFor(CarteController)
@Mock(Carte)
class CarteControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
       /* controller.index()
        assert "/carte/list" == response.redirectedUrl*/
    }

    void testList() {

        /*def model = controller.list()

        assert model.carteInstanceList.size() == 0
        assert model.carteInstanceTotal == 0*/
    }

    void testCreate() {
        /*def model = controller.create()

        assert model.carteInstance != null*/
    }

    void testSave() {
        /*controller.save()

        assert model.carteInstance != null
        assert view == '/carte/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/carte/show/1'
        assert controller.flash.message != null
        assert Carte.count() == 1*/
    }

    void testShow() {
       /* controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/carte/list'

        populateValidParams(params)
        def carte = new Carte(params)

        assert carte.save() != null

        params.id = carte.id

        def model = controller.show()

        assert model.carteInstance == carte*/
    }

    void testEdit() {
        /*controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/carte/list'

        populateValidParams(params)
        def carte = new Carte(params)

        assert carte.save() != null

        params.id = carte.id

        def model = controller.edit()

        assert model.carteInstance == carte*/
    }

    void testUpdate() {
       /* controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/carte/list'

        response.reset()

        populateValidParams(params)
        def carte = new Carte(params)

        assert carte.save() != null

        // test invalid parameters in update
        params.id = carte.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/carte/edit"
        assert model.carteInstance != null

        carte.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/carte/show/$carte.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        carte.clearErrors()

        populateValidParams(params)
        params.id = carte.id
        params.version = -1
        controller.update()

        assert view == "/carte/edit"
        assert model.carteInstance != null
        assert model.carteInstance.errors.getFieldError('version')
        assert flash.message != null*/
    }

    void testDelete() {
        /*controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/carte/list'

        response.reset()

        populateValidParams(params)
        def carte = new Carte(params)

        assert carte.save() != null
        assert Carte.count() == 1

        params.id = carte.id

        controller.delete()

        assert Carte.count() == 0
        assert Carte.get(carte.id) == null
        assert response.redirectedUrl == '/carte/list'*/
    }
}
