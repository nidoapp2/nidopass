
<%@ page import="com.nidoapp.nidopub.domain.PubEventPromotionalCode" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pubEventPromotionalCode.label', default: 'PubEventPromotionalCode')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-pubEventPromotionalCode" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-pubEventPromotionalCode" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list pubEventPromotionalCode">
			
				<g:if test="${pubEventPromotionalCodeInstance?.code}">
				<li class="fieldcontain">
					<span id="code-label" class="property-label"><g:message code="pubEventPromotionalCode.code.label" default="Code" /></span>
					
						<span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${pubEventPromotionalCodeInstance}" field="code"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pubEventPromotionalCodeInstance?.discount}">
				<li class="fieldcontain">
					<span id="discount-label" class="property-label"><g:message code="pubEventPromotionalCode.discount.label" default="Discount" /></span>
					
						<span class="property-value" aria-labelledby="discount-label"><g:fieldValue bean="${pubEventPromotionalCodeInstance}" field="discount"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pubEventPromotionalCodeInstance?.pubEvent}">
				<li class="fieldcontain">
					<span id="pubEvent-label" class="property-label"><g:message code="pubEventPromotionalCode.pubEvent.label" default="Pub Event" /></span>
					
						<span class="property-value" aria-labelledby="pubEvent-label"><g:link controller="pubEvent" action="show" id="${pubEventPromotionalCodeInstance?.pubEvent?.id}">${pubEventPromotionalCodeInstance?.pubEvent?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${pubEventPromotionalCodeInstance?.used}">
				<li class="fieldcontain">
					<span id="used-label" class="property-label"><g:message code="pubEventPromotionalCode.used.label" default="Used" /></span>
					
						<span class="property-value" aria-labelledby="used-label"><g:formatBoolean boolean="${pubEventPromotionalCodeInstance?.used}" /></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${pubEventPromotionalCodeInstance?.id}" />
					<g:link class="edit" action="edit" id="${pubEventPromotionalCodeInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
