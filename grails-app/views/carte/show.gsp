
<%@ page import="com.nidoapp.nidopub.domain.Carte" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'carte.label', default: 'Carte')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-carte" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-carte" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list carte">
			
				<g:if test="${carteInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="carte.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${carteInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${carteInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="carte.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${carteInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${carteInstance?.pub}">
				<li class="fieldcontain">
					<span id="pub-label" class="property-label"><g:message code="carte.pub.label" default="Pub" /></span>
					
						<span class="property-value" aria-labelledby="pub-label"><g:link controller="pub" action="show" id="${carteInstance?.pub?.id}">${carteInstance?.pub?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${carteInstance?.pub_id}">
				<li class="fieldcontain">
					<span id="pub_id-label" class="property-label"><g:message code="carte.pub_id.label" default="Pubid" /></span>
					
						<span class="property-value" aria-labelledby="pub_id-label"><g:fieldValue bean="${carteInstance}" field="pub_id"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${carteInstance?.sections}">
				<li class="fieldcontain">
					<span id="sections-label" class="property-label"><g:message code="carte.sections.label" default="Sections" /></span>
					
						<g:each in="${carteInstance.sections}" var="s">
						<span class="property-value" aria-labelledby="sections-label"><g:link controller="carteSection" action="show" id="${s.id}">${s?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${carteInstance?.id}" />
					<g:link class="edit" action="edit" id="${carteInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
