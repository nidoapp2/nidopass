<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" >
	<meta charset="utf-8" />
	<title>Te hemos enviado un email para reestablecer tu contraseña</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
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
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body class="login">
	<!-- BEGIN LOGO -->
	<div class="logo">
		<img src="${resource(dir: 'images', file: 'logo-big.png')}" alt="" style="max-height:40px" /> 
	</div>
	<!-- END LOGO -->
	<!-- BEGIN LOGIN -->
	<div class="content">
	
		<!-- BEGIN LOGIN FORM -->
		<h3 class="form-title">Te hemos enviado un email</h3>
		<p>Te hemos enviado un email a ${cmd.email} con las instrucciones para reestablecer tu contraseña.</p>
		<div class="alert alert-warning show">
				<button class="close" data-dismiss="alert"></button>
				<span>Si no existe una cuenta asociada al email indicado, no recibirás ningún correo. En ese caso, ponte en contacto con nosotros en dmv@nidoapp.com y trataremos tu caso.</span>
		</div>   
		<div class="form-actions"> 
			<a href="${createLink(controller:'home')}" class="btn green" role="button">Acceder</a>
		</div> 


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
<!-- END BODY -->
</html>
<!-- Localized -->