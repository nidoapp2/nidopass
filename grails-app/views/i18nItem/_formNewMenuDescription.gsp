<%@ page import="com.nidoapp.nidopub.domain.i18n.I18nItem" %>



<div class="fieldcontain ${hasErrors(bean: i18nItemInstance, field: 'defaultText', 'error')} ">
	<label for="defaultText">
		<g:message code="i18nItem.defaultText.label" default="Texto por defecto" />
		
	</label>
	<g:textField name="defaultText" value="${i18nItemInstance?.defaultText}"/>
</div>

