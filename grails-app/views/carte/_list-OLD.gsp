
<%@ page import="com.nidoapp.nidopub.domain.Carte" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="index">
		<g:set var="entityName" value="${message(code: 'carte.label', default: 'Carte')}" />
		
		<title>NidoPub | Cartas</title>
	
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
		<!-- END PAGE LEVEL STYLES -->
	</head>
	<body>
		<div class="portlet-body">
			<table class="table table-striped table-bordered table-advance table-hover">
				<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'carte.name.label', default: 'Nombre Carta')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'carte.description.label', default: 'Descripción')}" />
					
						<th><g:message code="carte.pub.label" default="Pub" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${carteInstanceList}" status="i" var="carteInstance">
					<g:if test="${carteInstance.pub_id == 28}">
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
							<td><g:link action="show" id="${carteInstance.id}">${fieldValue(bean: carteInstance, field: "name")}</g:link></td>
						
							<td>${fieldValue(bean: carteInstance, field: "description")}</td>
						
							<td>${fieldValue(bean: carteInstance, field: "pub.name")}</td>
						
						</tr>
					</g:if>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${carteInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
