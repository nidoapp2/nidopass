package com.nidoapp.nidopub.domain



import org.junit.*

import com.nidoapp.nidopub.controller.AssociationNewsController;
import com.nidoapp.nidopub.domain.AssociationNews;

import grails.test.mixin.*

@TestFor(AssociationNewsController)
@Mock(AssociationNews)
class AssociationNewsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        /*controller.index()
        assert "/associationNews/list" == response.redirectedUrl*/
    }

    void testList() {

        /*def model = controller.list()

        assert model.associationNewsInstanceList.size() == 0
        assert model.associationNewsInstanceTotal == 0*/
    }

    void testCreate() {
       /* def model = controller.create()

        assert model.associationNewsInstance != null*/
    }

    void testSave() {
       /* controller.save()

        assert model.associationNewsInstance != null
        assert view == '/associationNews/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/associationNews/show/1'
        assert controller.flash.message != null
        assert AssociationNews.count() == 1*/
    }

    void testShow() {
        /*controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/associationNews/list'

        populateValidParams(params)
        def associationNews = new AssociationNews(params)

        assert associationNews.save() != null

        params.id = associationNews.id

        def model = controller.show()

        assert model.associationNewsInstance == associationNews*/
    }

    void testEdit() {
        /*controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/associationNews/list'

        populateValidParams(params)
        def associationNews = new AssociationNews(params)

        assert associationNews.save() != null

        params.id = associationNews.id

        def model = controller.edit()

        assert model.associationNewsInstance == associationNews*/
    }

    void testUpdate() {
       /* controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/associationNews/list'

        response.reset()

        populateValidParams(params)
        def associationNews = new AssociationNews(params)

        assert associationNews.save() != null

        // test invalid parameters in update
        params.id = associationNews.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/associationNews/edit"
        assert model.associationNewsInstance != null

        associationNews.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/associationNews/show/$associationNews.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        associationNews.clearErrors()

        populateValidParams(params)
        params.id = associationNews.id
        params.version = -1
        controller.update()

        assert view == "/associationNews/edit"
        assert model.associationNewsInstance != null
        assert model.associationNewsInstance.errors.getFieldError('version')
        assert flash.message != null*/
    }

    void testDelete() {
        /*controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/associationNews/list'

        response.reset()

        populateValidParams(params)
        def associationNews = new AssociationNews(params)

        assert associationNews.save() != null
        assert AssociationNews.count() == 1

        params.id = associationNews.id

        controller.delete()

        assert AssociationNews.count() == 0
        assert AssociationNews.get(associationNews.id) == null
        assert response.redirectedUrl == '/associationNews/list'*/
    }
}
