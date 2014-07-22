
<%@ page import="com.nidoapp.nidopub.domain.PubFeature" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'pubFeature.label', default: 'PubFeature')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-pubFeature" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-pubFeature" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="code" title="${message(code: 'pubFeature.code.label', default: 'Code')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'pubFeature.description.label', default: 'Description')}" />
					
						<g:sortableColumn property="name" title="${message(code: 'pubFeature.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="repositoryImageId" title="${message(code: 'pubFeature.repositoryImageId.label', default: 'Repository Image Id')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${pubFeatureInstanceList}" status="i" var="pubFeatureInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${pubFeatureInstance.id}">${fieldValue(bean: pubFeatureInstance, field: "code")}</g:link></td>
					
						<td>${fieldValue(bean: pubFeatureInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: pubFeatureInstance, field: "name")}</td>
					
						<td>${fieldValue(bean: pubFeatureInstance, field: "repositoryImageId")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${pubFeatureInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
