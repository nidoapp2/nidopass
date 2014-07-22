package com.nidoapp.nidopub.domain.geo



import org.junit.*

import com.nidoapp.nidopub.controller.geo.ProvinceController;
import com.nidoapp.nidopub.domain.geo.Province;

import grails.test.mixin.*

@TestFor(ProvinceController)
@Mock(Province)
class ProvinceControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
       /* controller.index()
        assert "/province/list" == response.redirectedUrl*/
    }

    void testList() {

        /*def model = controller.list()

        assert model.provinceInstanceList.size() == 0
        assert model.provinceInstanceTotal == 0*/
    }

    void testCreate() {
        /*def model = controller.create()

        assert model.provinceInstance != null*/
    }

    void testSave() {
       /* controller.save()

        assert model.provinceInstance != null
        assert view == '/province/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/province/show/1'
        assert controller.flash.message != null
        assert Province.count() == 1*/
    }

    void testShow() {
       /* controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/province/list'

        populateValidParams(params)
        def province = new Province(params)

        assert province.save() != null

        params.id = province.id

        def model = controller.show()

        assert model.provinceInstance == province*/
    }

    void testEdit() {
       /* controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/province/list'

        populateValidParams(params)
        def province = new Province(params)

        assert province.save() != null

        params.id = province.id

        def model = controller.edit()

        assert model.provinceInstance == province*/
    }

    void testUpdate() {
       /* controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/province/list'

        response.reset()

        populateValidParams(params)
        def province = new Province(params)

        assert province.save() != null

        // test invalid parameters in update
        params.id = province.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/province/edit"
        assert model.provinceInstance != null

        province.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/province/show/$province.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        province.clearErrors()

        populateValidParams(params)
        params.id = province.id
        params.version = -1
        controller.update()

        assert view == "/province/edit"
        assert model.provinceInstance != null
        assert model.provinceInstance.errors.getFieldError('version')
        assert flash.message != null*/
    }

    void testDelete() {
       /* controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/province/list'

        response.reset()

        populateValidParams(params)
        def province = new Province(params)

        assert province.save() != null
        assert Province.count() == 1

        params.id = province.id

        controller.delete()

        assert Province.count() == 0
        assert Province.get(province.id) == null
        assert response.redirectedUrl == '/province/list'*/
    }
}
