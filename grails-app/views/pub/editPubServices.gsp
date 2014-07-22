<!DOCTYPE html>
<%@page import="com.nidoapp.nidopub.domain.Pub"%>
<head>
	<meta name="layout" content="admin"/>
	<title>NidoPass | Servicios Sala</title>
	
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link href="${resource(dir: 'js/plugins/bootstrap-fileupload', file: 'bootstrap-fileupload.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'js/plugins/chosen-bootstrap/chosen', file: 'chosen.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'css/pages', file: 'profile.css')}" rel="stylesheet" type="text/css"/>
	
	<link href="${resource(dir: 'js/plugins/fancybox/source', file: 'jquery.fancybox.css')}" rel="stylesheet" type="text/css"/>
	
	<!-- END PAGE LEVEL STYLES -->
	<calendar:resources lang="es" theme="tiger"/>
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
								<a href="#">Administración de Servicios</a> 
								<i class="icon-angle-right"></i>
							</li>
							<!--<li>
								<a href="${createLink(controller:'pub', params:['id': pub?.uri])}">Sala</a>
								<i class="icon-angle-right"></i>
							</li>-->
							<li>
								<a href="#">Servicios</a>
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
															<form action="${createLink(controller:'pub', action:'savePubServices')}" method="post">
																<input type="hidden" name="pubUri" value="${cmd ? cmd?.pubUri : pub?.uri}"/>
																<table>
																<tr>
																<td><span class="control-label" style="font-size:14px;">Cartas </span></td>
																<td><g:checkBox name="cartesAvailable" class="m-wrap span8" value="${true}" checked="${pub.cartesAvailable}"/></td>
																<td>Desde: <calendar:datePicker dateFormat="%d/%m/%Y" showTime="false" id="cartesAvailableFrom" name="cartesAvailableFrom" class="m-wrap span8" value="${pub.cartesAvailableFrom}"/></td>
																<td>Hasta: <calendar:datePicker dateFormat="%d/%m/%Y" showTime="false" id="cartesAvailableTo" name="cartesAvailableTo" class="m-wrap span8" value="${pub.cartesAvailableTo}"/></td>
																</tr><tr>
																<td><span class="control-label" style="font-size:14px;">Textos y Traducciones </span></td>
																<td><g:checkBox name="translationsAvailable" class="m-wrap span8" value="${true}" checked="${pub.translationsAvailable}"/></td>
																<td>Desde: <calendar:datePicker dateFormat="%d/%m/%Y" showTime="false" id="translationsAvailableFrom" name="translationsAvailableFrom" class="m-wrap span8" value="${pub.translationsAvailableFrom}"/></td>
																<td>Hasta: <calendar:datePicker dateFormat="%d/%m/%Y" showTime="false" id="translationsAvailableTo" name="translationsAvailableTo" class="m-wrap span8" value="${pub.translationsAvailableTo}"/></td>
																</tr><tr>
																<td><span class="control-label" style="font-size:14px;">Eventos </span></td>
																<td><g:checkBox name="eventsAvailable" class="m-wrap span8" value="${true}" checked="${pub.eventsAvailable}"/></td>
																<td>Desde: <calendar:datePicker dateFormat="%d/%m/%Y" showTime="false" id="eventsAvailableFrom" name="eventsAvailableFrom" class="m-wrap span8" value="${pub.eventsAvailableFrom}"/></td>
																<td>Hasta: <calendar:datePicker dateFormat="%d/%m/%Y" showTime="false" id="eventsAvailableTo" name="eventsAvailableTo" class="m-wrap span8" value="${pub.eventsAvailableTo}"/></td>
																</tr><tr>
																<td><span class="control-label" style="font-size:14px;">Venta Entradas Online </span></td>
																<td><g:checkBox name="sellTicketsOnlineAvailable" class="m-wrap span8" value="${true}" checked="${pub.sellTicketsOnlineAvailable}"/></td>
																<td>Desde: <calendar:datePicker dateFormat="%d/%m/%Y" showTime="false" id="sellTicketsOnlineAvailableFrom" name="sellTicketsOnlineAvailableFrom" class="m-wrap span8" value="${pub.sellTicketsOnlineAvailableFrom}"/></td>
																<td>Hasta: <calendar:datePicker dateFormat="%d/%m/%Y" showTime="false" id="sellTicketsOnlineAvailableTo" name="sellTicketsOnlineAvailableTo" class="m-wrap span8" value="${pub.sellTicketsOnlineAvailableTo}"/></td>
																</tr><tr>
																<td><span class="control-label" style="font-size:14px;">Encuesta </span></td>
																<td><g:checkBox name="inquiryAvailable" class="m-wrap span8" value="${true}" checked="${pub.inquiryAvailable}"/></td>
																<td>Desde: <calendar:datePicker dateFormat="%d/%m/%Y" showTime="false" id="inquiryAvailableFrom" name="inquiryAvailableFrom" class="m-wrap span8" value="${pub.inquiryAvailableFrom}"/></td>
																<td>Hasta: <calendar:datePicker dateFormat="%d/%m/%Y" showTime="false" id="inquiryAvailableTo" name="inquiryAvailableTo" class="m-wrap span8" value="${pub.inquiryAvailableTo}"/></td>
																</tr>
																</table>
																<br>
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

		$("#menu-configuration").addClass("active");
		$("#menu-configuration-pubs").addClass("active");
		$("#menu-configuration-province-${pub?.province.name}").addClass("active");
			//$("#menu-services-admin").addClass("active");
			  $("#menu-services-admin-${province?.name}").addClass("active");

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