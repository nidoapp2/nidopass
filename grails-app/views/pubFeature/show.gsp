
<%@ page import="com.nidoapp.nidopub.domain.PubFeature" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pubFeature.label', default: 'PubFeature')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-pubFeature" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-pubFeature" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list pubFeature">
			
				<g:if test="${pubFeatureInstance?.code}">
				<li class="fieldcontain">
					<span id="code-label" class="property-label"><g:message code="pubFeature.code.label" default="Code" /></span>
					
						<span class="property-value" aria-labelledby="code-label"><g:fieldValue bean="${pubFeatureInstance}" field="code"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pubFeatureInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="pubFeature.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${pubFeatureInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pubFeatureInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="pubFeature.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${pubFeatureInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${pubFeatureInstance?.repositoryImageId}">
				<li class="fieldcontain">
					<span id="repositoryImageId-label" class="property-label"><g:message code="pubFeature.repositoryImageId.label" default="Repository Image Id" /></span>
					
						<span class="property-value" aria-labelledby="repositoryImageId-label"><g:fieldValue bean="${pubFeatureInstance}" field="repositoryImageId"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${pubFeatureInstance?.id}" />
					<g:link class="edit" action="edit" id="${pubFeatureInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
