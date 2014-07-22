package com.nidoapp.nidopub.domain



import org.junit.*

import com.nidoapp.nidopub.controller.AssociationController;
import com.nidoapp.nidopub.domain.Association;

import grails.test.mixin.*

@TestFor(AssociationController)
@Mock(Association)
class AssociationControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
       /* controller.index()
        assert "/association/list" == response.redirectedUrl*/
    }

    void testList() {

        /*def model = controller.list()

        assert model.associationInstanceList.size() == 0
        assert model.associationInstanceTotal == 0*/
    }

    void testCreate() {
      /*  def model = controller.create()

        assert model.associationInstance != null*/
    }

    void testSave() {
       /* controller.save()

        assert model.associationInstance != null
        assert view == '/association/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/association/show/1'
        assert controller.flash.message != null
        assert Association.count() == 1*/
    }

    void testShow() {
        /*controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/association/list'

        populateValidParams(params)
        def association = new Association(params)

        assert association.save() != null

        params.id = association.id

        def model = controller.show()

        assert model.associationInstance == association*/
    }

    void testEdit() {
        /*controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/association/list'

        populateValidParams(params)
        def association = new Association(params)

        assert association.save() != null

        params.id = association.id

        def model = controller.edit()

        assert model.associationInstance == association*/
    }

    void testUpdate() {
        /*controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/association/list'

        response.reset()

        populateValidParams(params)
        def association = new Association(params)

        assert association.save() != null

        // test invalid parameters in update
        params.id = association.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/association/edit"
        assert model.associationInstance != null

        association.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/association/show/$association.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        association.clearErrors()

        populateValidParams(params)
        params.id = association.id
        params.version = -1
        controller.update()

        assert view == "/association/edit"
        assert model.associationInstance != null
        assert model.associationInstance.errors.getFieldError('version')
        assert flash.message != null*/
    }

    void testDelete() {
       /* controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/association/list'

        response.reset()

        populateValidParams(params)
        def association = new Association(params)

        assert association.save() != null
        assert Association.count() == 1

        params.id = association.id

        controller.delete()

        assert Association.count() == 0
        assert Association.get(association.id) == null
        assert response.redirectedUrl == '/association/list'*/
    }
}
