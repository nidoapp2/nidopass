package com.nidoapp.nidopub.domain.i18n



import org.junit.*

import com.nidoapp.nidopub.controller.i18n.I18nTranslationController;
import com.nidoapp.nidopub.domain.i18n.I18nTranslation;

import grails.test.mixin.*

@TestFor(I18nTranslationController)
@Mock(I18nTranslation)
class I18nTranslationControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
       /* controller.index()
        assert "/i18nTranslation/list" == response.redirectedUrl*/
    }

    void testList() {

        /*def model = controller.list()

        assert model.i18nTranslationInstanceList.size() == 0
        assert model.i18nTranslationInstanceTotal == 0*/
    }

    void testCreate() {
       /* def model = controller.create()

        assert model.i18nTranslationInstance != null*/
    }

    void testSave() {
       /* controller.save()

        assert model.i18nTranslationInstance != null
        assert view == '/i18nTranslation/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/i18nTranslation/show/1'
        assert controller.flash.message != null
        assert I18nTranslation.count() == 1*/
    }

    void testShow() {
       /* controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/i18nTranslation/list'

        populateValidParams(params)
        def i18nTranslation = new I18nTranslation(params)

        assert i18nTranslation.save() != null

        params.id = i18nTranslation.id

        def model = controller.show()

        assert model.i18nTranslationInstance == i18nTranslation*/
    }

    void testEdit() {
       /* controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/i18nTranslation/list'

        populateValidParams(params)
        def i18nTranslation = new I18nTranslation(params)

        assert i18nTranslation.save() != null

        params.id = i18nTranslation.id

        def model = controller.edit()

        assert model.i18nTranslationInstance == i18nTranslation*/
    }

    void testUpdate() {
       /* controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/i18nTranslation/list'

        response.reset()

        populateValidParams(params)
        def i18nTranslation = new I18nTranslation(params)

        assert i18nTranslation.save() != null

        // test invalid parameters in update
        params.id = i18nTranslation.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/i18nTranslation/edit"
        assert model.i18nTranslationInstance != null

        i18nTranslation.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/i18nTranslation/show/$i18nTranslation.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        i18nTranslation.clearErrors()

        populateValidParams(params)
        params.id = i18nTranslation.id
        params.version = -1
        controller.update()

        assert view == "/i18nTranslation/edit"
        assert model.i18nTranslationInstance != null
        assert model.i18nTranslationInstance.errors.getFieldError('version')
        assert flash.message != null*/
    }

    void testDelete() {
       /* controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/i18nTranslation/list'

        response.reset()

        populateValidParams(params)
        def i18nTranslation = new I18nTranslation(params)

        assert i18nTranslation.save() != null
        assert I18nTranslation.count() == 1

        params.id = i18nTranslation.id

        controller.delete()

        assert I18nTranslation.count() == 0
        assert I18nTranslation.get(i18nTranslation.id) == null
        assert response.redirectedUrl == '/i18nTranslation/list'*/
    }
}
