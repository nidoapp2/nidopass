package com.nidoapp.nidopub.domain.i18n



import org.junit.*

import com.nidoapp.nidopub.controller.i18n.I18nItemController;
import com.nidoapp.nidopub.domain.i18n.I18nItem;

import grails.test.mixin.*

@TestFor(I18nItemController)
@Mock(I18nItem)
class I18nItemControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
       /* controller.index()
        assert "/i18nItem/list" == response.redirectedUrl*/
    }

    void testList() {

        /*def model = controller.list()

        assert model.i18nItemInstanceList.size() == 0
        assert model.i18nItemInstanceTotal == 0*/
    }

    void testCreate() {
       /* def model = controller.create()

        assert model.i18nItemInstance != null*/
    }

    void testSave() {
        /*controller.save()

        assert model.i18nItemInstance != null
        assert view == '/i18nItem/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/i18nItem/show/1'
        assert controller.flash.message != null
        assert I18nItem.count() == 1*/
    }

    void testShow() {
       /* controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/i18nItem/list'

        populateValidParams(params)
        def i18nItem = new I18nItem(params)

        assert i18nItem.save() != null

        params.id = i18nItem.id

        def model = controller.show()

        assert model.i18nItemInstance == i18nItem*/
    }

    void testEdit() {
     /*   controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/i18nItem/list'

        populateValidParams(params)
        def i18nItem = new I18nItem(params)

        assert i18nItem.save() != null

        params.id = i18nItem.id

        def model = controller.edit()

        assert model.i18nItemInstance == i18nItem*/
    }

    void testUpdate() {
       /* controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/i18nItem/list'

        response.reset()

        populateValidParams(params)
        def i18nItem = new I18nItem(params)

        assert i18nItem.save() != null

        // test invalid parameters in update
        params.id = i18nItem.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/i18nItem/edit"
        assert model.i18nItemInstance != null

        i18nItem.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/i18nItem/show/$i18nItem.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        i18nItem.clearErrors()

        populateValidParams(params)
        params.id = i18nItem.id
        params.version = -1
        controller.update()

        assert view == "/i18nItem/edit"
        assert model.i18nItemInstance != null
        assert model.i18nItemInstance.errors.getFieldError('version')
        assert flash.message != null*/
    }

    void testDelete() {
        /*controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/i18nItem/list'

        response.reset()

        populateValidParams(params)
        def i18nItem = new I18nItem(params)

        assert i18nItem.save() != null
        assert I18nItem.count() == 1

        params.id = i18nItem.id

        controller.delete()

        assert I18nItem.count() == 0
        assert I18nItem.get(i18nItem.id) == null
        assert response.redirectedUrl == '/i18nItem/list'*/
    }
}
