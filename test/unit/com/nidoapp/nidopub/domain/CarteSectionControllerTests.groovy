package com.nidoapp.nidopub.domain



import org.junit.*

import com.nidoapp.nidopub.controller.CarteSectionController;
import com.nidoapp.nidopub.domain.CarteSection;

import grails.test.mixin.*

@TestFor(CarteSectionController)
@Mock(CarteSection)
class CarteSectionControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        /*controller.index()
        assert "/carteSection/list" == response.redirectedUrl*/
    }

    void testList() {

       /* def model = controller.list()

        assert model.carteSectionInstanceList.size() == 0
        assert model.carteSectionInstanceTotal == 0*/
    }

    void testCreate() {
        /*def model = controller.create()

        assert model.carteSectionInstance != null*/
    }

    void testSave() {
        /*controller.save()

        assert model.carteSectionInstance != null
        assert view == '/carteSection/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/carteSection/show/1'
        assert controller.flash.message != null
        assert CarteSection.count() == 1*/
    }

    void testShow() {
        /*controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/carteSection/list'

        populateValidParams(params)
        def carteSection = new CarteSection(params)

        assert carteSection.save() != null

        params.id = carteSection.id

        def model = controller.show()

        assert model.carteSectionInstance == carteSection*/
    }

    void testEdit() {
       /* controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/carteSection/list'

        populateValidParams(params)
        def carteSection = new CarteSection(params)

        assert carteSection.save() != null

        params.id = carteSection.id

        def model = controller.edit()

        assert model.carteSectionInstance == carteSection*/
    }

    void testUpdate() {
       /* controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/carteSection/list'

        response.reset()

        populateValidParams(params)
        def carteSection = new CarteSection(params)

        assert carteSection.save() != null

        // test invalid parameters in update
        params.id = carteSection.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/carteSection/edit"
        assert model.carteSectionInstance != null

        carteSection.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/carteSection/show/$carteSection.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        carteSection.clearErrors()

        populateValidParams(params)
        params.id = carteSection.id
        params.version = -1
        controller.update()

        assert view == "/carteSection/edit"
        assert model.carteSectionInstance != null
        assert model.carteSectionInstance.errors.getFieldError('version')
        assert flash.message != null*/
    }

    void testDelete() {
       /* controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/carteSection/list'

        response.reset()

        populateValidParams(params)
        def carteSection = new CarteSection(params)

        assert carteSection.save() != null
        assert CarteSection.count() == 1

        params.id = carteSection.id

        controller.delete()

        assert CarteSection.count() == 0
        assert CarteSection.get(carteSection.id) == null
        assert response.redirectedUrl == '/carteSection/list'*/
    }
}
