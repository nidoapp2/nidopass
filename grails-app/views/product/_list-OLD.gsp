
<%@ page import="com.nidoapp.nidopub.domain.Product" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-product" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-product" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="description" title="${message(code: 'product.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="photo" title="${message(code: 'product.photo.label', default: 'Photo')}" />
					
						<g:sortableColumn property="price" title="${message(code: 'product.price.label', default: 'Price')}" />
					
						<g:sortableColumn property="available" title="${message(code: 'product.available.label', default: 'Available')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'product.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="pub_id" title="${message(code: 'product.pub_id.label', default: 'Pubid')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${productInstanceList}" status="i" var="productInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${productInstance.id}">${fieldValue(bean: productInstance, field: "description")}</g:link></td>
					
						<td>${fieldValue(bean: productInstance, field: "photo")}</td>
					
						<td>${fieldValue(bean: productInstance, field: "price")}</td>
					
						<td><g:formatBoolean boolean="${productInstance.available}" /></td>
					
						<td>${fieldValue(bean: productInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: productInstance, field: "pub_id")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${productInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
