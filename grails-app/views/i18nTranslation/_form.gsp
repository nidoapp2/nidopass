<%@ page import="com.nidoapp.nidopub.domain.i18n.I18nTranslation" %>



<div class="fieldcontain ${hasErrors(bean: i18nTranslationInstance, field: 'lang', 'error')} required">
	<label for="lang">
		<g:message code="i18nTranslation.lang.label" default="Idioma" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="lang" name="lang.id" from="${com.nidoapp.nidopub.domain.i18n.I18nLanguage.list()}" optionKey="id" required="" value="${i18nTranslationInstance?.lang?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: i18nTranslationInstance, field: 'item', 'error')} required">
	<label for="item">
		<g:message code="i18nTranslation.item.label" default="Texto a traducir" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="item" name="item.id" from="${com.nidoapp.nidopub.domain.i18n.I18nItem.list()}" optionKey="id" required="" value="${i18nTranslationInstance?.item?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: i18nTranslationInstance, field: 'text', 'error')} required">
	<label for="text">
		<g:message code="i18nTranslation.text.label" default="Texto traducido" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="text" value="${i18nTranslationInstance?.text}" required=""/>
</div>

