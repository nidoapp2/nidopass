
<%@ page import="com.nidoapp.nidopub.domain.PubEvent" %>
<html>
	<head>
		<meta name="layout" content="index">
		<g:set var="entityName" value="${message(code: 'pubEvent.label', default: 'Eventos')}" />
		
		<title>NidoPass | Eventos</title>
	
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
		<!-- END PAGE LEVEL STYLES -->
	</head>
	<body>
		<div class="portlet-body">
			<table class="table table-striped table-bordered table-advance table-hover">
				<thead>
					<tr>
					
						<th><i class="icon-th"></i><span class="title-icon-separation"> Evento </span></th>
						<th class="hidden-phone"> Sala </th>
						<th class="hidden-phone"> Artista </th>
						<th class="hidden-phone"><i class="icon-calendar"></i> Fecha/Hora evento </th>
						<th class="hidden-phone"> Precio </th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${pubEventInstanceList}" status="i" var="pubEventInstance">
					
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
							<td>${fieldValue(bean: pubEventInstance, field: "title")}</td>
							
							<td>${fieldValue(bean: pubEventInstance, field: "pub")}</td>
							
							<td>${fieldValue(bean: pubEventInstance, field: "artist")}</td>
							
							<td><g:formatDate format="dd-MMMM-yyyy / HH:mm" date="${pubEventInstance.dateEvent}" /></td>
							
							<td>${pubEventInstance.price + pubEventInstance.commission}</td>
						
						</tr>
					
				</g:each>
				</tbody>
			</table>
		</div>
	</body>
</html>
