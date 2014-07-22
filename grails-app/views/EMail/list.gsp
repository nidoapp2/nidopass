
<%@ page import="com.nidoapp.nidopub.domain.EMail" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin"/>
		<g:set var="entityName" value="${message(code: 'EMail.label', default: 'EMail')}" />
		
		<title>NidoPass | Libreta EMails</title>
	
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
							Libreta de EMails
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">EMails</a> 
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
								<div class="caption"><i class="icon-th"></i>EMails</div>
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
					
						<th><i class="icon-th"></i><span class="title-icon-separation"> Nombre </span><acronym title="Crear nuevo email"><a href="${createLink(controller:'EMail', action:'create', params: [id_pub:params.id_pub])}"><i class="icon-create-style icon-plus-sign"></i></a></acronym></th>
						<th class="hidden-phone"> EMail </th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${EMailInstanceList}" status="i" var="EMailInstance">
					
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
							
							<td><g:link action="edit" id="${EMailInstance.id}">${fieldValue(bean: EMailInstance, field: "name")}</g:link></td>
					
							<td>${fieldValue(bean: EMailInstance, field: "email")}</td>
						
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
