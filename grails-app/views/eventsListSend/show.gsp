
<%@ page import="com.nidoapp.nidopub.domain.EventsListSend" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'eventsListSend.label', default: 'EventsListSend')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-eventsListSend" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-eventsListSend" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list eventsListSend">
			
				<g:if test="${eventsListSendInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="eventsListSend.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${eventsListSendInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${eventsListSendInstance?.sendDate}">
				<li class="fieldcontain">
					<span id="sendDate-label" class="property-label"><g:message code="eventsListSend.sendDate.label" default="Send Date" /></span>
					
						<span class="property-value" aria-labelledby="sendDate-label"><g:formatDate date="${eventsListSendInstance?.sendDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${eventsListSendInstance?.events}">
				<li class="fieldcontain">
					<span id="events-label" class="property-label"><g:message code="eventsListSend.events.label" default="Events" /></span>
					
						<g:each in="${eventsListSendInstance.events}" var="e">
						<span class="property-value" aria-labelledby="events-label"><g:link controller="pubEvent" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${eventsListSendInstance?.emailsLists}">
				<li class="fieldcontain">
					<span id="emailsLists-label" class="property-label"><g:message code="eventsListSend.emailsLists.label" default="Emails Lists" /></span>
					
						<g:each in="${eventsListSendInstance.emailsLists}" var="e">
						<span class="property-value" aria-labelledby="emailsLists-label"><g:link controller="EMailsList" action="show" id="${e.id}">${e?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${eventsListSendInstance?.pub}">
				<li class="fieldcontain">
					<span id="pub-label" class="property-label"><g:message code="eventsListSend.pub.label" default="Pub" /></span>
					
						<span class="property-value" aria-labelledby="pub-label"><g:link controller="pub" action="show" id="${eventsListSendInstance?.pub?.id}">${eventsListSendInstance?.pub?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${eventsListSendInstance?.id}" />
					<g:link class="edit" action="edit" id="${eventsListSendInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
