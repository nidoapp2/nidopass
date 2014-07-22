
<%@ page import="com.nidoapp.nidopub.domain.EventsListSend" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin"/>
		<g:set var="entityName" value="${message(code: 'eventsListSend.label', default: 'EventsListSend')}" />
		
		<title>NidoPass | Listas Eventos Envío</title>
	
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
		<link href="${resource(dir: 'js/plugins/fullcalendar/fullcalendar', file: 'fullcalendar.css')}" rel="stylesheet" type="text/css"/>
		
		<!-- END PAGE LEVEL STYLES -->
	</head>
	<body>
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
							Listas Eventos Envío EMail
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">Listas Eventos Envío EMail</a> 
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
								<div class="caption"><i class="icon-th"></i>Listas</div>
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
					
						<th><i class="icon-th"></i><span class="title-icon-separation"> Título Lista </span><acronym title="Crear nueva lista Eventos Envío"><a href="${createLink(controller:'eventsListSend', action:'create', params: [id_pub:params.id_pub])}"><i class="icon-create-style icon-plus-sign"></i></a></acronym></th>
						<th class="hidden-phone"> Fecha Envío </th>
						<th class="hidden-phone"> Eventos incluidos </th>
						<th class="hidden-phone"> Listados Emails incluidos </th>
						<th class="hidden-phone"> Enviado </th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${eventsListSendInstanceList}" status="i" var="eventsListSendInstance">
					
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
							<td><g:link action="edit" id="${eventsListSendInstance.id}">${fieldValue(bean: eventsListSendInstance, field: "title")}</g:link></td>
					
							<td><g:formatDate format="dd-MMMM-yyyy" date="${eventsListSendInstance.sendDate}" /></td>
							
							<td><ul><g:each in="${eventsListSendInstance.events }" status="i2" var="eventInstance">
								<li>${eventInstance.title}</li>
							</g:each></ul></td>
							
							<td><ul><g:each in="${eventsListSendInstance.emailsLists }" status="i3" var="emailsListInstance">
								<li>${emailsListInstance.title}</li>
							</g:each></ul></td>
							
							<g:if test="${eventsListSendInstance.sent}">
								<td>SÍ</td>
							</g:if>
							<g:else>
								<td>NO</td>
							</g:else>
						
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
		   
		//$("#menu-emails").addClass("active");
		//$("#menu-emails-pub-${pub?.uri}").addClass("active");
		
		$("#menu-configuration").addClass("active");
			$("#menu-configuration-pubs").addClass("active");
			$("#menu-configuration-pub-${pub?.uri}").addClass("active");

			// initiate layout and plugins
		   App.init();
		});
	</script>
	</body>
</html>
