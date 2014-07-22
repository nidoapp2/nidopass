
<%@ page import="com.nidoapp.nidopub.domain.PubEvent" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pubEvent.label', default: 'PubEvent')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="pubEvent" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-pubEvent" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list pubEvent">
			
				<g:if test="${pubEventInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="pubEvent.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${pubEventInstance}" field="title"/></span>
					
				</li>
				</g:if>
				
			
				<g:if test="${pubEventInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="pubEvent.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${pubEventInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
			
				<g:if test="${pubEventInstance?.dateEvent}">
				<li class="fieldcontain">
					<span id="dateEvent-label" class="property-label"><g:message code="pubEvent.dateEvent.label" default="Date Event" /></span>
					
						<span class="property-value" aria-labelledby="dateEvent-label"><g:formatDate date="${pubEventInstance?.dateEvent}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${pubEventInstance?.pub}">
				<li class="fieldcontain">
					<span id="pub-label" class="property-label"><g:message code="pubEvent.pub.label" default="Pub" /></span>
					
						<span class="property-value" aria-labelledby="pub-label"><g:link controller="pub" action="show" id="${pubeventInstance?.pub?.id}">${pubEventInstance?.pub?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${pubEventInstance?.url}">
				<li class="fieldcontain">
					<span id="url-label" class="property-label"><g:message code="pubEvent.url.label" default="Url" /></span>
					
						<span class="property-value" aria-labelledby="url-label"><g:fieldValue bean="${pubEventInstance}" field="url"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${pubEventInstance?.id}" />
					<g:link class="edit" action="edit" id="${pubEventInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
