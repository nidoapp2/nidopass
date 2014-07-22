<!DOCTYPE html>
<head>
	<meta name="layout" content="admin"/>
	<title>NidoPass | Sala</title>
	
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link href="${resource(dir: 'js/plugins/bootstrap-fileupload', file: 'bootstrap-fileupload.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'js/plugins/chosen-bootstrap/chosen', file: 'chosen.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'css/pages', file: 'profile.css')}" rel="stylesheet" type="text/css"/>
	
	<link href="${resource(dir: 'js/plugins/fancybox/source', file: 'jquery.fancybox.css')}" rel="stylesheet" type="text/css"/>
	
	<!-- END PAGE LEVEL STYLES -->
	
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
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
							Sala <small>${pub?.name}</small>
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-cogs"></i>
								<a href="#">Configuración</a> 
								<i class="icon-angle-right"></i>
							</li>
							<li>
								<a href="${createLink(controller:'pub', params:['id': pub?.uri])}">Sala</a>
								<i class="icon-angle-right"></i>
							</li>
							<li>
								<a href="#">Editar Configuración de Reservas</a>
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid profile">
					<div class="span12">

						<g:hasErrors bean="${cmd}">
							<g:eachError bean="${cmd}">
								<div class="alert alert-danger show">
									<button class="close" data-dismiss="alert"></button>
									<span><g:message error="${it}" /></span>
								</div>
							</g:eachError>
						</g:hasErrors>


					<div style="height: auto;" id="accordion1-1" class="accordion collapse">
															<form action="${createLink(controller:'pub', action:'saveReservationSettings')}" method="post">
																<input type="hidden" name="pubUri" value="${cmd ? cmd?.pubUri : pub?.uri}"/>
																<span class="control-label" style="font-size:14px;">Reservas Online Activas </span><g:checkBox name="onlineReservations" class="m-wrap span8" value="${true}" checked="${pub.onlineReservations}"/><span class="control-label" style="font-size:14px; color:red;"> (Si activa esta opción, debe completar el teléfono móvil e email)</span>
																<label class="control-label">Teléfono móvil de reservas (Se recibirá SMS)</label>
																<input type="text" name="reservationPhone" placeholder="Ej: 666666666" class="m-wrap span8" value="${cmd ? cmd.reservationPhone : pub.reservationPhone}"/>															
																<label class="control-label">Email de reservas</label>																
																<input type="text" name="reservationEmail" placeholder="Ej: info@dominio.es" class="m-wrap span8" value="${cmd ? cmd.reservationEmail : pub.reservationEmail}"/>
																<label class="control-label">Número mínimo de comensales para reserva</label>																
																<g:field type="number" name="minComensales" class="span8" value="${pub.MinComensales}" required=""/>
																
																<table id="reservationDays" cellpadding="10">
																	<thead>
																		<tr><td colspan="8"><label class="control-label">Días admisión reservas:</label><td></tr>
																		<tr>
																			<td></td>
																			<td>Lunes</td>
																			<td>Martes</td>
																			<td>Miercoles</td>
																			<td>Jueves</td>
																			<td>Viernes</td>
																			<td>Sábado</td>
																			<td>Domingo</td>
																		</tr>
																	</thead>
																	<tbody style="text-align:center;">
																	
																		<tr>
																			<td>Mañana:</td>
																			<td><g:checkBox name="lunesM" class="m-wrap span8" value="${true}" checked="${pub.lunesM}"/></td>
																			<td><g:checkBox name="martesM" class="m-wrap span8" value="${true}" checked="${pub.martesM}"/></td>
																			<td><g:checkBox name="miercolesM" class="m-wrap span8" value="${true }" checked="${cmd ? cmd.miercolesM : pub.miercolesM}"/></td>
																			<td><g:checkBox name="juevesM" class="m-wrap span8" value="${true }" checked="${cmd ? cmd.juevesM : pub.juevesM}"/></td>
																			<td><g:checkBox name="viernesM" class="m-wrap span8" value="${true }" checked="${cmd ? cmd.viernesM : pub.viernesM}"/></td>
																			<td><g:checkBox name="sabadoM" class="m-wrap span8" value="${true }" checked="${cmd ? cmd.sabadoM : pub.sabadoM}"/></td>
																			<td><g:checkBox name="domingoM" class="m-wrap span8" value="${true }" checked="${cmd ? cmd.domingoM : pub.domingoM}"/></td>
																		</tr>
																		<tr>
																			<td>Noche:</td>
																			<td><g:checkBox name="lunesN" class="m-wrap span8" value="${true }" checked="${cmd ? cmd.lunesN : pub.lunesN}"/></td>
																			<td><g:checkBox name="martesN" class="m-wrap span8" value="${true }" checked="${cmd ? cmd.martesN : pub.martesN}"/></td>
																			<td><g:checkBox name="miercolesN" class="m-wrap span8" value="${true }" checked="${cmd ? cmd.miercolesN : pub.miercolesN}"/></td>
																			<td><g:checkBox name="juevesN" class="m-wrap span8" value="${true }" checked="${cmd ? cmd.juevesN : pub.juevesN}"/></td>
																			<td><g:checkBox name="viernesN" class="m-wrap span8" value="${true }" checked="${cmd ? cmd.viernesN : pub.viernesN}"/></td>
																			<td><g:checkBox name="sabadoN" class="m-wrap span8" value="${true }" checked="${cmd ? cmd.sabadoN : pub.sabadoN}"/></td>
																			<td><g:checkBox name="domingoN" class="m-wrap span8" value="${true }" checked="${cmd ? cmd.domingoN : pub.domingoN}"/></td>
																		</tr>
																	</tbody>
																</table>
																<div class="submit-btn">
																	<button type="submit" class="btn green">Guardar</button>
																	<a href="${createLink(controller:'pub', params:['id': pub?.uri])}" class="btn">Cancelar</a>
																</div>																
															</form>
														</div>
					</div>
				</div>
				<!-- END PAGE CONTENT-->
			</div>
			<!-- END PAGE CONTAINER--> 
		</div>
		<!-- END PAGE -->    
		
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="${resource(dir: 'js/plugins/bootstrap-fileupload', file: 'bootstrap-fileupload.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/chosen-bootstrap/chosen', file: 'chosen.jquery.min.js')}" type="text/javascript" ></script>
	
	<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
	<!-- <script src="${resource(dir: 'js/plugins/gmaps', file: 'gmaps.js')}" type="text/javascript" ></script>  -->
	
	<script src="${resource(dir: 'js/plugins/fancybox/source', file: 'jquery.fancybox.pack.js')}" type="text/javascript" ></script>
	
	
	
	<!-- END PAGE LEVEL PLUGINS -->
	
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${resource(dir: 'js', file: 'app.js')}" type="text/javascript" ></script>
	
	<!-- <script src="${resource(dir: 'js', file: 'maps-google.js')}" type="text/javascript" ></script>  -->
	<!-- END PAGE LEVEL SCRIPTS --> 


	
	

	
	 
	<script>
	jQuery(document).ready(function() {       

			$("#menu-reservations").addClass("active");
			//$("#menu-reservation-pubs").addClass("active");
			   $("#menu-reservations-pub-${pub?.uri}").addClass("active");

			// initiate layout and plugins
		   App.init();

		   //MapsGoogle.init();

		   <g:if test="${pub?.latitude && pub?.longitude}">
			   var latlng = new google.maps.LatLng(${pub?.latitude}, ${pub?.longitude});
	
	           var myOptions = {
	               zoom: 18,
	               center: latlng,
	               mapTypeControl: false,
	               streetViewControl: true,
	               overviewMapControl: false,
	               mapTypeId: google.maps.MapTypeId.ROADMAP,
	               scrollwheel: true
	           };
	
	           var map = new google.maps.Map(document.getElementById("gmap_pub"), myOptions);

	           var marker = new google.maps.Marker({
	        	      position: latlng,
	        	      map: map,
	        	      title: '${pub?.name}',
	        	      icon: '${resource(dir: 'images', file: 'restaurant.png')}'
	        	  });
	           
		   </g:if>

		  


		   
		});
	</script>

</body>
<!-- END BODY -->
</html>
<!-- Localized -->