
<%@ page import="com.nidoapp.nidopub.domain.PubEvent" %>
<html>
	<head>
		<meta name="layout" content="index">
		<g:set var="entityName" value="${message(code: 'pubEvent.label', default: 'Eventos')}" />
		
		<title>NidoPass | Facturación</title>
	
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
						<th class="hidden-phone"><i class="icon-calendar"></i> Fecha/Hora evento </th>
						<th class="hidden-phone"> Precio Entrada </th>
						<th class="hidden-phone"> Comisión Entrada </th>
						<th class="hidden-phone"> Precio Total Entrada </th>
						<th class="hidden-phone"> Total Entradas Vendidas </th>
						<th class="hidden-phone"> Total € (Solo Precio) </th>
						<th class="hidden-phone"> Total € (Solo Comisión) </th>
						<th class="hidden-phone"> Total € (Precio + Comisión) </th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${pubEventInstanceList}" status="i" var="pubEventInstance">
					
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
							<td>${fieldValue(bean: pubEventInstance, field: "title")}</td>
							
							<td><g:formatDate format="dd-MMMM-yyyy / HH:mm" date="${pubEventInstance.dateEvent}" /></td>
							
							<td>${fieldValue(bean: pubEventInstance, field: "price")}</td>
							
							<td>${fieldValue(bean: pubEventInstance, field: "commission")}</td>
							
							<td>${pubEventInstance.price + pubEventInstance.commission}</td>
							
							<td>${fieldValue(bean: pubEventInstance, field: "totalTicketsSold")}</td>
							
							<td>${pubEventInstance.totalTicketsSold * pubEventInstance.price}</td>
							
							<td>${pubEventInstance.totalTicketsSold * pubEventInstance.commission}</td>
							
							<td>${pubEventInstance.totalTicketsSold * (pubEventInstance.price + pubEventInstance.commission)}</td>
						
						</tr>
					
				</g:each>
				</tbody>
			</table>
			<table class="table table-striped table-bordered table-advance table-hover">
				<thead>
					<tr>
						<th colspan="5"></th>
						<th>Total Entradas</th>
						<th>Total Precios</th>
						<th>Total Comisiones</th>
						<th>TOTAL</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td colspan="5" style="padding-left">Totales TODOS los Eventos fechas seleccionadas:</td>
						<td>${totalTicketsSold}</td>
						<td>${totalTicketsPrice }</td>
						<td>${totalTicketsCommission }</td>
						<td>${totalTicketsPriceCommission }</td>
					</tr>
				</tbody>
			</table>
		</div>
	</body>
</html>
