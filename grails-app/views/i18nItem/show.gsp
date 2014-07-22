
<%@ page import="com.nidoapp.nidopub.domain.i18n.I18nItem" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'i18nItem.label', default: 'I18nItem')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-i18nItem" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-i18nItem" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list i18nItem">
			
				<g:if test="${i18nItemInstance?.defaultText}">
				<li class="fieldcontain">
					<span id="defaultText-label" class="property-label"><g:message code="i18nItem.defaultText.label" default="Default Text" /></span>
					
						<span class="property-value" aria-labelledby="defaultText-label"><g:fieldValue bean="${i18nItemInstance}" field="defaultText"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${i18nItemInstance?.translations}">
				<li class="fieldcontain">
					<span id="translations-label" class="property-label"><g:message code="i18nItem.translations.label" default="Translations" /></span>
					
						<g:each in="${i18nItemInstance.translations}" var="t">
						<span class="property-value" aria-labelledby="translations-label"><g:link controller="i18nTranslation" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${i18nItemInstance?.id}" />
					<g:link class="edit" action="edit" id="${i18nItemInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
