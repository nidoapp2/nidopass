
<%@ page import="com.nidoapp.nidopub.domain.PubEvent" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pubEvent.label', default: 'PubEvent')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-pubEvent" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-pubEvent" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
						<g:sortableColumn property="title" title="${message(code: 'pubEvent.title.label', default: 'Title')}" />
						
						<g:sortableColumn property="description" title="${message(code: 'pubEvent.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="dateEvent" title="${message(code: 'pubEvent.dateEvent.label', default: 'Date Event')}" />
					
						<th><g:message code="pubEvent.pub.label" default="Pub" /></th>
					
						<g:sortableColumn property="url" title="${message(code: 'pubEvent.url.label', default: 'Url')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${pubEventInstanceList}" status="i" var="pubEventInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${pubEventInstance.id}">${fieldValue(bean: pubEventInstance, field: "title")}</g:link></td>
					
						<td>${fieldValue(bean: pubEventInstance, field: "description")}</td>
					
						<td><g:formatDate date="${pubEventInstance.dateEvent}" /></td>
					
						<td>${fieldValue(bean: pubEventInstance, field: "pub")}</td>
					
						<td>${fieldValue(bean: pubEventInstance, field: "url")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${pubEventInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
