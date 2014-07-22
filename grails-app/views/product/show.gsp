
<%@ page import="com.nidoapp.nidopub.domain.Product" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-product" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-product" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list product">
			
				<g:if test="${productInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="product.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${productInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${productInstance?.photo}">
				<li class="fieldcontain">
					<span id="photo-label" class="property-label"><g:message code="product.photo.label" default="Photo" /></span>
					
						<span class="property-value" aria-labelledby="photo-label"><g:fieldValue bean="${productInstance}" field="photo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${productInstance?.price}">
				<li class="fieldcontain">
					<span id="price-label" class="property-label"><g:message code="product.price.label" default="Price" /></span>
					
						<span class="property-value" aria-labelledby="price-label"><g:fieldValue bean="${productInstance}" field="price"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${productInstance?.available}">
				<li class="fieldcontain">
					<span id="available-label" class="property-label"><g:message code="product.available.label" default="Available" /></span>
					
						<span class="property-value" aria-labelledby="available-label"><g:formatBoolean boolean="${productInstance?.available}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${productInstance?.carteSections}">
				<li class="fieldcontain">
					<span id="carteSections-label" class="property-label"><g:message code="product.carteSections.label" default="Carte Sections" /></span>
					
						<g:each in="${productInstance.carteSections}" var="c">
						<span class="property-value" aria-labelledby="carteSections-label"><g:link controller="carteSection" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${productInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="product.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${productInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${productInstance?.pub_id}">
				<li class="fieldcontain">
					<span id="pub_id-label" class="property-label"><g:message code="product.pub_id.label" default="Pubid" /></span>
					
						<span class="property-value" aria-labelledby="pub_id-label"><g:fieldValue bean="${productInstance}" field="pub_id"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${productInstance?.reviews}">
				<li class="fieldcontain">
					<span id="reviews-label" class="property-label"><g:message code="product.reviews.label" default="Reviews" /></span>
					
						<g:each in="${productInstance.reviews}" var="r">
						<span class="property-value" aria-labelledby="reviews-label"><g:link controller="customerReview" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${productInstance?.id}" />
					<g:link class="edit" action="edit" id="${productInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
