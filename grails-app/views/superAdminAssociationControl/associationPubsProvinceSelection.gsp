<!DOCTYPE html>
<%@page import="com.nidoapp.nidopub.domain.Association"%>
<%@page import="com.nidoapp.nidopub.domain.geo.Province"%>
<head>
	<meta name="layout" content="admin"/>
	<title>NidoPass | Administración Salas Asociación Provincias</title>
	
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
	<!-- END PAGE LEVEL STYLES -->
	
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
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
							Administración Salas Asociación Provincias
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">Administración Salas Asociación</a> 
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
								<div class="caption"><i class="icon-th"></i>Administración Salas ${association?.name }</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a>
									<a href="#portlet-config" data-toggle="modal" class="config"></a>
									<a href="javascript:;" class="reload"></a>
									<a href="javascript:;" class="remove"></a>
								</div>
							</div>
							<div class="portlet-body">
								<g:each in="${Province.findAll([sort:"name", order:"asc"])}">
									<g:link controller="pub" action="pubsAssociationSuperAdmin" params="[provinceId: it.id]" id="${association?.id}" class="btn blue" style="min-width:30%;">${it.name }</g:link>
									<br/><br/>
								</g:each>
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
			$("#menu-configuration-associations").addClass("active");
			$("#menu-configuration-associations-province-${association?.province.name}").addClass("active");

			// initiate layout and plugins
		   	App.init();
		});
	</script>

</body>
<!-- END BODY -->
</html>
<!-- Localized -->