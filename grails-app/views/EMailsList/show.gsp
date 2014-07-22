
<%@ page import="com.nidoapp.nidopub.domain.EMailsList" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'EMailsList.label', default: 'EMailsList')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-EMailsList" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-EMailsList" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list EMailsList">
			
				<g:if test="${EMailsListInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="EMailsList.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${EMailsListInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${EMailsListInstance?.emails}">
				<li class="fieldcontain">
					<span id="emails-label" class="property-label"><g:message code="EMailsList.emails.label" default="Emails" /></span>
					
						<g:each in="${EMailsListInstance.emails}" var="e">
						<span class="property-value" aria-labelledby="emails-label"><g:link controller="EMail" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${EMailsListInstance?.pub}">
				<li class="fieldcontain">
					<span id="pub-label" class="property-label"><g:message code="EMailsList.pub.label" default="Pub" /></span>
					
						<span class="property-value" aria-labelledby="pub-label"><g:link controller="pub" action="show" id="${EMailsListInstance?.pub?.id}">${EMailsListInstance?.pub?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${EMailsListInstance?.id}" />
					<g:link class="edit" action="edit" id="${EMailsListInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
