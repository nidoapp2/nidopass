<%@ page import="com.nidoapp.nidopub.domain.PubEventPromotionalCode" %>
<!DOCTYPE html>
<html>
	<head>
		<head>
		<meta name="layout" content="admin"/>
		<title>NidoPass | Nuevo Código Promocinal Evento</title>
		
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
		
		<link href="${resource(dir: 'js/plugins/bootstrap-fileupload', file: 'bootstrap-fileupload.css')}" rel="stylesheet" type="text/css"/>
		<link href="${resource(dir: 'js/plugins/chosen-bootstrap/chosen', file: 'chosen.css')}" rel="stylesheet" type="text/css"/>
		<link href="${resource(dir: 'css/pages', file: 'profile.css')}" rel="stylesheet" type="text/css"/>
	
		<link href="${resource(dir: 'js/plugins/fancybox/source', file: 'jquery.fancybox.css')}" rel="stylesheet" type="text/css"/>
		<!-- END PAGE LEVEL STYLES -->
	</head>
	<body>
		<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN PAGE CONTAINER-->        
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->
				<div class="row-fluid">
					<div class="span12">		 
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<h3 class="page-title">
							Crear Nuevo Código Promocinal de Evento
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">Código Promocional</a> 
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->   

				<div class="row-fluid">
					<div class="span12">
						<g:uploadForm action="save" >
							<g:hiddenField name="eventId" value="${params.eventId}" />
							<!--<g:hiddenField name="menuAction" value="${params.action}" />-->
							<fieldset class="form">
								<g:render template="form"/>
							</fieldset>
							<fieldset class="buttons">
								<g:submitButton name="create" class="save btn green" value="${message(code: 'default.button.create.label', default: 'Create')}" />
							</fieldset>
						</g:uploadForm>
						<span style="color:red;">IMPORTANTE: El descuento se aplicará al precio TOTAL de la compra.</span>
					</div>
				</div>
				<div class="clearfix"></div>
				
				<!-- END PAGE CONTENT-->
			</div>
			<!-- END PAGE CONTAINER-->
		</div>
		<!-- END PAGE -->
		
		<!-- BEGIN PAGE LEVEL PLUGINS -->
		<script src="${resource(dir: 'js/plugins/bootstrap-fileupload', file: 'bootstrap-fileupload.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/chosen-bootstrap/chosen', file: 'chosen.jquery.min.js')}" type="text/javascript" ></script>
	
	<script src="${resource(dir: 'js/plugins/fancybox/source', file: 'jquery.fancybox.pack.js')}" type="text/javascript" ></script>
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
