
<%@ page import="com.nidoapp.nidopub.domain.PubEvent" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin"/>
		<g:set var="entityName" value="${message(code: 'pubEvent.label', default: 'PubEvent')}" />
		
		<title>NidoPass | Eventos</title>
	
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
		<link href="${resource(dir: 'js/plugins/fullcalendar/fullcalendar', file: 'fullcalendar.css')}" rel="stylesheet" type="text/css"/>
		
		<script src="${resource(dir: 'js/plugins/fullcalendar/fullcalendar', file: 'fullcalendar.min.js')}" type="text/javascript" ></script>
		<!-- END PAGE LEVEL STYLES -->
		
		
		<script>

		$(document).ready(function() {
		    // page is now ready, initialize the calendar...

		    $('#calendar').fullCalendar({
		        // put your options and callbacks here
		        editable: false,
		        firstDay: 1,
		        events: "${createLink(controller:'pubEvent', action:'listCityEvents')}"
		        
		    });

		});

		</script>
	</head>
	<body>
	<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div id="portlet-config" class="modal hide">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3>portlet Settings</h3>
				</div>
				<div class="modal-body">
					<p>Here will be a configuration form</p>
				</div>
			</div>
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<!-- BEGIN PAGE CONTAINER-->        
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->
				<div class="row-fluid">
					<div class="span12">		 
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<h3 class="page-title">
							Eventos
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">Eventos</a> 
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->   

				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN SAMPLE TABLE PORTLET-->           
						<div class="portlet">
							<div class="portlet-title">
								<div class="caption"><i class="icon-th"></i>Eventos</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a>
									<a href="#portlet-config" data-toggle="modal" class="config"></a>
									<a href="javascript:;" class="reload"></a>
									<a href="javascript:;" class="remove"></a>
								</div>
							</div>
		<div class="portlet-body">
			<table class="table table-striped table-bordered table-advance table-hover">
				<thead>
					<tr>
					
						<th><i class="icon-th"></i><span class="title-icon-separation"> Evento </span><acronym title="Crear nuevo evento"><a href="${createLink(controller:'pubEvent', action:'create', params: [id_pub:params.id_pub])}"><i class="icon-create-style icon-plus-sign"></i></a></acronym></th>
						<th class="hidden-phone"> Tipo Música </th>
						<th class="hidden-phone"> Artista </th>
						<th class="hidden-phone"><i class="icon-calendar"></i> Fecha/Hora evento </th>
						<th class="hidden-phone"> Precio + Comisión </th>
						<th class="hidden-phone"> Inicio numeración entradas </th>
						<th class="hidden-phone"> Entradas venta online </th>
						<th class="hidden-phone"> Entradas vendidas </th>
						<th></th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${pubEventInstanceList}" status="i" var="pubEventInstance">
					
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
							<td><g:link action="edit" id="${pubEventInstance.id}">${fieldValue(bean: pubEventInstance, field: "title")}</g:link></td>
							
							<td>${fieldValue(bean: pubEventInstance, field: "musicType")} ${fieldValue(bean: pubEventInstance, field: "musicSubType")}</td>
							
							<td>${fieldValue(bean: pubEventInstance, field: "artist")}</td>
							
							<td><g:formatDate format="dd-MMMM-yyyy / HH:mm" date="${pubEventInstance.dateEvent}" /></td>
							
							<td>${fieldValue(bean: pubEventInstance, field: "price")} + ${fieldValue(bean: pubEventInstance, field: "commission")}</td>
							
							<td>${fieldValue(bean: pubEventInstance, field: "ticketsInitNumber")}</td>
							
							<td>${fieldValue(bean: pubEventInstance, field: "totalTicketsOnlineSale")}</td>
							
							<td>${fieldValue(bean: pubEventInstance, field: "totalTicketsSold")}</td>
						
							<td><g:link controller="pubEventPromotionalCode" action="list" id="${pubEventInstance.id}">Códigos Promocionales</g:link></td>
							
						</tr>
					
				</g:each>
				</tbody>
			</table>
		</div>
		
		<div class="portlet" style="padding-top:20px;">
			<div class="portlet-title">
				<div class="caption"><i class="icon-th"></i>Calendario eventos Salas</div>
				<div class="tools">
					<a href="javascript:;" class="collapse"></a>
					<a href="#portlet-config" data-toggle="modal" class="config"></a>
					<a href="javascript:;" class="reload"></a>
					<a href="javascript:;" class="remove"></a>
				</div>
			</div>
			<div class="portlet-body">
				<div id="calendar"></div>
			</div>
		</div>
		
		</div>
						<!-- END SAMPLE TABLE PORTLET-->
					</div>
				</div>
				
				<!-- END PAGE CONTENT-->
			</div>
			<!-- END PAGE CONTAINER-->
		</div>
		<!-- END PAGE -->
		
		<!-- BEGIN PAGE LEVEL PLUGINS -->
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${resource(dir: 'js', file: 'app.js')}" type="text/javascript" ></script>
	<!-- END PAGE LEVEL SCRIPTS -->  
	<script>
	jQuery(document).ready(function() {       
		   
		//$("#menu-events").addClass("active");
		//$("#menu-events-pub-${pub?.uri}").addClass("active");
		
		$("#menu-configuration").addClass("active");
			$("#menu-configuration-pubs").addClass("active");
			$("#menu-configuration-pub-${pub?.uri}").addClass("active");

			// initiate layout and plugins
		   App.init();
		});
	</script>
	</body>
</html>
