
<%@ page import="com.nidoapp.nidopub.domain.i18n.I18nTranslation" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'i18nTranslation.label', default: 'I18nTranslation')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-i18nTranslation" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-i18nTranslation" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="i18nTranslation.lang.label" default="Lang" /></th>
					
						<th><g:message code="i18nTranslation.item.label" default="Item" /></th>
					
						<g:sortableColumn property="text" title="${message(code: 'i18nTranslation.text.label', default: 'Text')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${i18nTranslationInstanceList}" status="i" var="i18nTranslationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${i18nTranslationInstance.id}">${fieldValue(bean: i18nTranslationInstance, field: "lang")}</g:link></td>
					
						<td>${fieldValue(bean: i18nTranslationInstance, field: "item")}</td>
					
						<td>${fieldValue(bean: i18nTranslationInstance, field: "text")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${i18nTranslationInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
