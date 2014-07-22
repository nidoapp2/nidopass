
<%@ page import="com.nidoapp.nidopub.domain.Association" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'association.label', default: 'Association')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-association" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-association" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'association.name.label', default: 'Name')}" />
					
						<th><g:message code="association.owner.label" default="Owner" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${associationInstanceList}" status="i" var="associationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${associationInstance.id}">${fieldValue(bean: associationInstance, field: "name")}</g:link></td>
					
						<td>${fieldValue(bean: associationInstance, field: "owner")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${associationInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
