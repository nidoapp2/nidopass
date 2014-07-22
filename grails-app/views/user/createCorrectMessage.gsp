<%@ page import="com.nidoapp.nidopub.domain.user.User" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin"/>
		<title>NidoPass | Nuevo Usuario Creado</title>
		
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
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
							Nuevo Usuario Creado Correctamente
						</h3>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->   

				<div class="row-fluid">
					<div class="span12">
						<h3 class="form-title" style="text-align:center;"><g:message code="Se ha registrado el nuevo usuario correctamente, ya se puede acceder con el email y contraseña indicados." /></h3>
			
						<a href="${createLink(uri:'/user/create/')}">Volver al formulario de registro de usuarios</a>
					</div>
				</div>
				<div class="clearfix"></div>
				
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

		$("#menu-configuration").addClass("active");
		$("#menu-new-user").addClass("active");
		$("#menu-configuration-users").addClass("active");

			// initiate layout and plugins
		   App.init();
		});
	</script>
	</body>
</html>
