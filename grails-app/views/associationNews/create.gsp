<%@ page import="com.nidoapp.nidopub.domain.AssociationNews" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin"/>
		<title>NidoPass | Nueva Noticia</title>
		
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
							Crear Nueva Noticia
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">Noticia</a> 
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
							<!--<g:hiddenField name="association" value="${params.id_pub}" />-->
							<fieldset class="form">
								<g:render template="form"/>
							</fieldset>
							<fieldset class="buttons">
								<g:submitButton name="create" class="save btn green" value="${message(code: 'default.button.create.label', default: 'Create')}" />
							</fieldset>
						</g:uploadForm>
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
		   
		   $("#menu-associationNews").addClass("active");

			// initiate layout and plugins
		   App.init();
		});
	</script>
	</body>
</html>
