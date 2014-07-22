
<%@ page import="com.nidoapp.nidopub.domain.i18n.I18nTranslation" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'i18nTranslation.label', default: 'I18nTranslation')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-i18nTranslation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-i18nTranslation" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list i18nTranslation">
			
				<g:if test="${i18nTranslationInstance?.lang}">
				<li class="fieldcontain">
					<span id="lang-label" class="property-label"><g:message code="i18nTranslation.lang.label" default="Lang" /></span>
					
						<span class="property-value" aria-labelledby="lang-label"><g:link controller="i18nLanguage" action="show" id="${i18nTranslationInstance?.lang?.id}">${i18nTranslationInstance?.lang?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${i18nTranslationInstance?.item}">
				<li class="fieldcontain">
					<span id="item-label" class="property-label"><g:message code="i18nTranslation.item.label" default="Item" /></span>
					
						<span class="property-value" aria-labelledby="item-label"><g:link controller="i18nItem" action="show" id="${i18nTranslationInstance?.item?.id}">${i18nTranslationInstance?.item?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${i18nTranslationInstance?.text}">
				<li class="fieldcontain">
					<span id="text-label" class="property-label"><g:message code="i18nTranslation.text.label" default="Text" /></span>
					
						<span class="property-value" aria-labelledby="text-label"><g:fieldValue bean="${i18nTranslationInstance}" field="text"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${i18nTranslationInstance?.id}" />
					<g:link class="edit" action="edit" id="${i18nTranslationInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
