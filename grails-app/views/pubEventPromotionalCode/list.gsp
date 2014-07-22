
<%@ page import="com.nidoapp.nidopub.domain.PubEventPromotionalCode" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin"/>
		<g:set var="entityName" value="${message(code: 'pubEvent.label', default: 'PubEvent')}" />
		
		<title>NidoPass | Códigos Promocionales Evento</title>
	
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
		<link href="${resource(dir: 'js/plugins/fullcalendar/fullcalendar', file: 'fullcalendar.css')}" rel="stylesheet" type="text/css"/>
		
		<script src="${resource(dir: 'js/plugins/fullcalendar/fullcalendar', file: 'fullcalendar.min.js')}" type="text/javascript" ></script>
		<!-- END PAGE LEVEL STYLES -->
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
							Códigos Promocionales de Evento
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">Códigos Promocionales</a> 
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
								<div class="caption"><i class="icon-th"></i>Códigos Promocionales</div>
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
					
						<th><i class="icon-th"></i><span class="title-icon-separation"> Código Promocional </span><acronym title="Crear nuevo código promocional"><a href="${createLink(controller:'pubEventPromotionalCode', action:'create', params: [eventId:params.id])}"><i class="icon-create-style icon-plus-sign"></i></a></acronym></th>
						<th class="hidden-phone"> Descuento </th>
						<th class="hidden-phone"> Máximo Entradas </th>
						<th class="hidden-phone"> Email </th>
						<th class="hidden-phone"> Utilizado </th>
						<th class="hidden-phone"> Estado </th>
					</tr>
				</thead>
				<tbody>
				<g:each in="${pubEventPromotionalCodeInstanceList}" status="i" var="pubEventPromotionalCodeInstance">
					
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
							<td><g:link action="edit" id="${pubEventPromotionalCodeInstance.id}">${fieldValue(bean: pubEventPromotionalCodeInstance, field: "code")}</g:link></td>
							
							<td>${fieldValue(bean: pubEventPromotionalCodeInstance, field: "discount")}</td>
							
							<td>${fieldValue(bean: pubEventPromotionalCodeInstance, field: "maxTickets")}</td>
							
							<td>${fieldValue(bean: pubEventPromotionalCodeInstance, field: "email")}</td>
							
							<g:if test="${pubEventPromotionalCodeInstance.used}">
								<td>SÍ</td>
							</g:if>
							<g:else>
								<td>NO</td>
							</g:else>
							
							<g:if test="${pubEventPromotionalCodeInstance.status==PubEventPromotionalCode.STATUS_PENDING}">
								<td>PENDIENTE</td>
							</g:if>
							<g:elseif test="${pubEventPromotionalCodeInstance.status==PubEventPromotionalCode.STATUS_CONFIRMED}">
								<td>CONFIRMADO</td>
							</g:elseif>
							<g:elseif test="${pubEventPromotionalCodeInstance.status==PubEventPromotionalCode.STATUS_EXPIRED}">
								<td>EXPIRADO</td>
							</g:elseif>
						
						</tr>
					
				</g:each>
				</tbody>
			</table>
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
