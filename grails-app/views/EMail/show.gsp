
<%@ page import="com.nidoapp.nidopub.domain.EMail" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'EMail.label', default: 'EMail')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-EMail" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-EMail" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list EMail">
			
				<g:if test="${EMailInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="EMail.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${EMailInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${EMailInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="EMail.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${EMailInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${EMailInstance?.pub}">
				<li class="fieldcontain">
					<span id="pub-label" class="property-label"><g:message code="EMail.pub.label" default="Pub" /></span>
					
						<span class="property-value" aria-labelledby="pub-label"><g:link controller="pub" action="show" id="${EMailInstance?.pub?.id}">${EMailInstance?.pub?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${EMailInstance?.emailsLists}">
				<li class="fieldcontain">
					<span id="emailsLists-label" class="property-label"><g:message code="EMail.emailsLists.label" default="Emails Lists" /></span>
					
						<g:each in="${EMailInstance.emailsLists}" var="e">
						<span class="property-value" aria-labelledby="emailsLists-label"><g:link controller="EMailsList" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${EMailInstance?.id}" />
					<g:link class="edit" action="edit" id="${EMailInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
