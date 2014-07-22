<%@ page import="com.nidoapp.nidopub.domain.PubAppsAuthenticationCode" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin"/>
		<title>NidoPass | Editar Código Acceso Apps</title>
		
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
							Editar Código Acceso Apps
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">Código Acceso Apps</a> 
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->   

				<div class="row-fluid">
					<div class="span12">
						<g:uploadForm method="post" >
							<g:hiddenField name="id" value="${pubAppsAuthenticationCodeInstance?.id}" />
							<g:hiddenField name="version" value="${pubAppsAuthenticationCodeInstance?.version}" />
							<!--<g:hiddenField name="menuAction" value="${params.action}" />-->
							<fieldset class="form">
								<g:render template="form"/>
							</fieldset>
							<fieldset class="buttons">
								<g:actionSubmit class="save btn green" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
								<g:actionSubmit class="delete btn red" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: '¿Seguro que quieres borrar este código de acceso a apps?, esta acción no se podrá deshacer.', default: '¿Seguro que quieres borrar este código de acceso a apps?, esta acción no se podrá deshacer.')}');" />
							</fieldset>
						</g:uploadForm>
						<span style="color:red;">IMPORTANTE: Una vez que no quieras que se utilice algún código de acceso, recuerda eliminarlo para que nadie pueda acceder con él.</span>
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
