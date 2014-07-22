<%@ page import="com.nidoapp.nidopub.domain.i18n.I18nItem" %>



<div class="fieldcontain ${hasErrors(bean: i18nItemInstance, field: 'defaultText', 'error')} ">
	<label for="defaultText">
		<g:message code="i18nItem.defaultText.label" default="Texto por defecto" />
		
	</label>
	<g:textField name="defaultText" value="${i18nItemInstance?.defaultText}"/>
</div>
<!--
<div class="fieldcontain ${hasErrors(bean: i18nItemInstance, field: 'translations', 'error')} ">
	<label for="translations">
		<g:message code="i18nItem.translations.label" default="Translations" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${i18nItemInstance?.translations?}" var="t">
    <li><g:link controller="i18nTranslation" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="i18nTranslation" action="create" params="['i18nItem.id': i18nItemInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'i18nTranslation.label', default: 'I18nTranslation')])}</g:link>
</li>
</ul>

</div>
-->
