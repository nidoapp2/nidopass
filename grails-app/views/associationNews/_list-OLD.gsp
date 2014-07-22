
<%@ page import="com.nidoapp.nidopub.domain.AssociationNews" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'associationNews.label', default: 'AssociationNews')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-associationNews" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-associationNews" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="title" title="${message(code: 'associationNews.title.label', default: 'Title')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'associationNews.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="photo" title="${message(code: 'associationNews.photo.label', default: 'Photo')}" />
					
						<g:sortableColumn property="url" title="${message(code: 'associationNews.url.label', default: 'Url')}" />
					
						<th><g:message code="associationNews.association.label" default="Association" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${associationNewsInstanceList}" status="i" var="associationNewsInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${associationNewsInstance.id}">${fieldValue(bean: associationNewsInstance, field: "title")}</g:link></td>
					
						<td>${fieldValue(bean: associationNewsInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: associationNewsInstance, field: "photo")}</td>
					
						<td>${fieldValue(bean: associationNewsInstance, field: "url")}</td>
					
						<td>${fieldValue(bean: associationNewsInstance, field: "association")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${associationNewsInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
