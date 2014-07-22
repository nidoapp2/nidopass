
<%@ page import="com.nidoapp.nidopub.domain.PubAppsAuthenticationCode" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pubAppsAuthenticationCode.label', default: 'PubAppsAuthenticationCode')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-pubAppsAuthenticationCode" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-pubAppsAuthenticationCode" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list pubAppsAuthenticationCode">
			
				<g:if test="${pubAppsAuthenticationCodeInstance?.code}">
				<li class="fieldcontain">
					<span id="code-label" class="property-label"><g:message code="pubAppsAuthenticationCode.code.label" default="Code" /></span>
					
						<span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${pubAppsAuthenticationCodeInstance}" field="code"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pubAppsAuthenticationCodeInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="pubAppsAuthenticationCode.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${pubAppsAuthenticationCodeInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pubAppsAuthenticationCodeInstance?.pub}">
				<li class="fieldcontain">
					<span id="pub-label" class="property-label"><g:message code="pubAppsAuthenticationCode.pub.label" default="Pub" /></span>
					
						<span class="property-value" aria-labelledby="pub-label"><g:link controller="pub" action="show" id="${pubAppsAuthenticationCodeInstance?.pub?.id}">${pubAppsAuthenticationCodeInstance?.pub?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${pubAppsAuthenticationCodeInstance?.id}" />
					<g:link class="edit" action="edit" id="${pubAppsAuthenticationCodeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
