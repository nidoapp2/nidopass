<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" >
	<meta charset="utf-8" />
	<title>Denegación de reserva</title>
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
	
		<g:if test="${processed}">		
			<h3 class="form-title">Reserva Denegada</h3>	
			<p>La reserva ha sido denegada correctamente.</p>		
		</g:if>
		<g:elseif test="${reservation.STATUS_CONFIRMED ==  reservation.status}">
			<h3 class="form-title">Reserva ya denegada previamente</h3>	
			<p>No se ha realizado denegación. La reserva había sido confirmada previamente.</p>		
		</g:elseif>
		<g:elseif test="${reservation.STATUS_DENIED ==  reservation.status}">
			<h3 class="form-title">Reserva ya denegada previamente</h3>	
			<p>No se ha realizado denegación. La reserva había sido denegada previamente.</p>		
		</g:elseif>
		<g:elseif test="${reservation.STATUS_CANCELLED ==  reservation.status}">
			<h3 class="form-title">Reserva ya cancelada previamente</h3>	
			<p>No se ha realizado denegación. La reserva había sido cancelada previamente.</p>		
		</g:elseif>
		<g:elseif test="${reservation.STATUS_EXPIRED ==  reservation.status}">
			<h3 class="form-title">Reserva ya expirada</h3>	
			<p>No se ha realizado denegación. La reserva ha expirado.</p>		
		</g:elseif>
		
		<br/>
		<p>Datos de la reserva:</p>
		<p>
			Fecha:
			${reservation?.reservationDate?.format('dd/MM/yyyy')}
		</p>
		<p>
			Hora:
			${reservation?.reservationDate?.format('HH:mm')}
		</p>
		<p>
			Comensales:
			${reservation?.adults}
			adultos,
			${reservation?.children}
			niños
		</p>
		<p>
			Nombre:
			${reservation?.customer?.name}
		</p>
		<p>
			Teléfono:
			${reservation?.customer?.phone}
		</p>
		<p>
			Email:
			${reservation?.customer?.email}
		</p>
		<g:if test="${reservation?.comments}">
			<p>
				Comentarios: "${reservation?.comments}"
			</p>
		</g:if>



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