<%@ page import="com.nidoapp.nidopub.domain.user.User" %>
<!DOCTYPE html>
<html>
	<head>
		
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="Nuevo Usuario" args="[entityName]" /></title>
		
		<!-- BEGIN GLOBAL MANDATORY STYLES -->
	<link href="${resource(dir: 'js/plugins/bootstrap/css', file: 'bootstrap.min.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'js/plugins/bootstrap/css', file: 'bootstrap-responsive.min.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'js/plugins/font-awesome/css', file: 'font-awesome.min.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'css', file: 'style-metro.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'css', file: 'style.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'css', file: 'style-responsive.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'css/themes', file: 'default.css')}" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="${resource(dir: 'js/plugins/uniform/css', file: 'uniform.default.css')}" rel="stylesheet" type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES -->
	<link href="${resource(dir: 'css/pages', file: 'login.css')}" rel="stylesheet" type="text/css"/>
	<!-- END PAGE LEVEL STYLES -->
	<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
	
	</head>
	<body class="login">
		<!-- BEGIN LOGO -->
	<div class="logo">
		<img src="${resource(dir: 'images', file: 'logo-big.png')}" alt="" style="max-height:40px" /> 
	</div>
	<!-- END LOGO -->
	<!-- BEGIN LOGIN -->
	<div class="content">
		<!-- BEGIN MESSAGE CORRECT USER CREATION -->
		<div id="correct-create-user" role="main">
			<h3 class="form-title" style="text-align:center;"><g:message code="Te has registrado correctamente, ya puedes acceder con tú email y contraseña. Gracias." /></h3>
			
			<a href="${createLink(uri:'/')}">Ir a la ventana de acceso</a>
		</div>
		<!-- END MESSAGE CORRECT USER CREATION -->
	</div>
	<!-- END LOGIN -->
	<!-- BEGIN COPYRIGHT -->
	<div class="copyright">
		2014 &copy; Nido Aplicaciones.
	</div>
	<!-- END COPYRIGHT -->
	<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<script src="${resource(dir: 'js/plugins', file: 'jquery-1.10.1.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir: 'js/plugins', file: 'jquery-migrate-1.2.1.min.js')}" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="${resource(dir: 'js/plugins/jquery-ui', file: 'jquery-ui-1.10.1.custom.min.js')}" type="text/javascript"></script>      
	<script src="${resource(dir: 'js/plugins/bootstrap/js', file: 'bootstrap.min.js')}" type="text/javascript"></script>
	<!--[if lt IE 9]>
	<script src="${resource(dir: 'js/plugins', file: 'excanvas.min.js')}"></script>
	<script src="${resource(dir: 'js/plugins', file: 'respond.min.js')}"></script>  
	<![endif]-->   
	<script src="${resource(dir: 'js/plugins/jquery-slimscroll', file: 'jquery.slimscroll.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir: 'js/plugins', file: 'jquery.blockui.min.js')}" type="text/javascript"></script>  
	<script src="${resource(dir: 'js/plugins', file: 'jquery.cookie.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir: 'js/plugins/uniform', file: 'jquery.uniform.min.js')}" type="text/javascript" ></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="${resource(dir: 'js/plugins/jquery-validation/dist', file: 'jquery.validate.min.js')}" type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${resource(dir: 'js', file: 'app.js')}" type="text/javascript"></script>
	<script src="${resource(dir: 'js', file: 'login.js')}" type="text/javascript"></script>      
	<!-- END PAGE LEVEL SCRIPTS --> 
	<script>
		jQuery(document).ready(function() {     
		  App.init();
		  Login.init();
		});
	</script>
	<!-- END JAVASCRIPTS -->
	</body>
</html>
