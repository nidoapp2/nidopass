
<%@ page import="com.nidoapp.nidopub.domain.AssociationNews" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'associationNews.label', default: 'AssociationNews')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-associationNews" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-associationNews" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list associationNews">
			
				<g:if test="${associationNewsInstance?.title}">
				<li class="fieldcontain">
					<span id="title-label" class="property-label"><g:message code="associationNews.title.label" default="Title" /></span>
					
						<span class="property-value" aria-labelledby="title-label"><g:fieldValue bean="${associationNewsInstance}" field="title"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${associationNewsInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="associationNews.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${associationNewsInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${associationNewsInstance?.photo}">
				<li class="fieldcontain">
					<span id="photo-label" class="property-label"><g:message code="associationNews.photo.label" default="Photo" /></span>
					
						<span class="property-value" aria-labelledby="photo-label"><g:fieldValue bean="${associationNewsInstance}" field="photo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${associationNewsInstance?.url}">
				<li class="fieldcontain">
					<span id="url-label" class="property-label"><g:message code="associationNews.url.label" default="Url" /></span>
					
						<span class="property-value" aria-labelledby="url-label"><g:fieldValue bean="${associationNewsInstance}" field="url"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${associationNewsInstance?.association}">
				<li class="fieldcontain">
					<span id="association-label" class="property-label"><g:message code="associationNews.association.label" default="Association" /></span>
					
						<span class="property-value" aria-labelledby="association-label"><g:link controller="association" action="show" id="${associationNewsInstance?.association?.id}">${associationNewsInstance?.association?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${associationNewsInstance?.id}" />
					<g:link class="edit" action="edit" id="${associationNewsInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
