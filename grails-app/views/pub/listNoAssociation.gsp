<%@ page import="com.nidoapp.nidopub.domain.Pub" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="index">
		<g:set var="entityName" value="${message(code: 'pub.label', default: 'Pub')}" />
		
		<title>NidoPass | Salas</title>
	
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
		<!-- END PAGE LEVEL STYLES -->
	</head>
	<body>
		<div class="portlet-body">
			<table class="table table-striped table-bordered table-advance table-hover">
				<thead>
					<tr>
					
						<th><i class="icon-th"></i><span class="title-icon-separation"> Salas </span></th>
						<th></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${pubInstanceList}" status="i" var="pubInstance">
					
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
							<td>${fieldValue(bean: pubInstance, field: "name")}</td>
							
							<td><g:link controller="association" action="addPub" id="${pubInstance.id}"><i class="icon-create-style icon-plus-sign"></i></g:link></td>
						
						</tr>
					
				</g:each>
				</tbody>
			</table>
		</div>
	</body>
</html>