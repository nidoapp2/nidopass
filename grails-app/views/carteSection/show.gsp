
<%@ page import="com.nidoapp.nidopub.domain.CarteSection" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'carteSection.label', default: 'CarteSection')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-carteSection" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-carteSection" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list carteSection">
			
				<g:if test="${carteSectionInstance?.carte}">
				<li class="fieldcontain">
					<span id="carte-label" class="property-label"><g:message code="carteSection.carte.label" default="Carte" /></span>
					
						<span class="property-value" aria-labelledby="carte-label"><g:link controller="carte" action="show" id="${carteSectionInstance?.carte?.id}">${carteSectionInstance?.carte?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${carteSectionInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="carteSection.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${carteSectionInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${carteSectionInstance?.orderNumber}">
				<li class="fieldcontain">
					<span id="orderNumber-label" class="property-label"><g:message code="carteSection.orderNumber.label" default="Order Number" /></span>
					
						<span class="property-value" aria-labelledby="orderNumber-label"><g:fieldValue bean="${carteSectionInstance}" field="orderNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${carteSectionInstance?.products}">
				<li class="fieldcontain">
					<span id="products-label" class="property-label"><g:message code="carteSection.products.label" default="Products" /></span>
					
						<g:each in="${carteSectionInstance.products}" var="p">
						<span class="property-value" aria-labelledby="products-label"><g:link controller="product" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${carteSectionInstance?.id}" />
					<g:link class="edit" action="edit" id="${carteSectionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
