package com.nidoapp.nidopub.domain



import org.junit.*

import com.nidoapp.nidopub.controller.ProductValorationsController;
import com.nidoapp.nidopub.domain.ProductValorations;

import grails.test.mixin.*

@TestFor(ProductValorationsController)
@Mock(ProductValorations)
class ProductValorationsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        /*controller.index()
        assert "/productValorations/list" == response.redirectedUrl*/
    }

    void testList() {

       /* def model = controller.list()

        assert model.productValorationsInstanceList.size() == 0
        assert model.productValorationsInstanceTotal == 0*/
    }

    void testCreate() {
       /* def model = controller.create()

        assert model.productValorationsInstance != null*/
    }

    void testSave() {
      /*  controller.save()

        assert model.productValorationsInstance != null
        assert view == '/productValorations/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/productValorations/show/1'
        assert controller.flash.message != null
        assert ProductValorations.count() == 1*/
    }

    void testShow() {
       /* controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/productValorations/list'

        populateValidParams(params)
        def productValorations = new ProductValorations(params)

        assert productValorations.save() != null

        params.id = productValorations.id

        def model = controller.show()

        assert model.productValorationsInstance == productValorations*/
    }

    void testEdit() {
        /*controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/productValorations/list'

        populateValidParams(params)
        def productValorations = new ProductValorations(params)

        assert productValorations.save() != null

        params.id = productValorations.id

        def model = controller.edit()

        assert model.productValorationsInstance == productValorations*/
    }

    void testUpdate() {
        /*controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/productValorations/list'

        response.reset()

        populateValidParams(params)
        def productValorations = new ProductValorations(params)

        assert productValorations.save() != null

        // test invalid parameters in update
        params.id = productValorations.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/productValorations/edit"
        assert model.productValorationsInstance != null

        productValorations.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/productValorations/show/$productValorations.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        productValorations.clearErrors()

        populateValidParams(params)
        params.id = productValorations.id
        params.version = -1
        controller.update()

        assert view == "/productValorations/edit"
        assert model.productValorationsInstance != null
        assert model.productValorationsInstance.errors.getFieldError('version')
        assert flash.message != null*/
    }

    void testDelete() {
        /*controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/productValorations/list'

        response.reset()

        populateValidParams(params)
        def productValorations = new ProductValorations(params)

        assert productValorations.save() != null
        assert ProductValorations.count() == 1

        params.id = productValorations.id

        controller.delete()

        assert ProductValorations.count() == 0
        assert ProductValorations.get(productValorations.id) == null
        assert response.redirectedUrl == '/productValorations/list'*/
    }
}
