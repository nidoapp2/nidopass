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
								<a href="#">Ubicación</a>
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid profile">
					<div class="span12">
					
					
						
					
					
					
											<div class="span8">
											
												<g:hasErrors bean="${cmd}">
												    <g:eachError bean="${cmd}">
															    <div class="alert alert-danger show">
															<button class="close" data-dismiss="alert"></button>
															<span><g:message error="${it}"/></span>
														</div>
													</g:eachError>
												</g:hasErrors>
											
															<form action="${createLink(controller:'pub', action:'saveLocation')}" method="post">
																<input type="hidden" name="pubUri" value="${cmd ? cmd?.pubUri : pub?.uri}"/>
																<label class="control-label">Latitud</label>
																<input type="text" name="latitude" placeholder="Ej: 40.416827" class="m-wrap span8" value="${cmd ? cmd.latitude : pub.latitude}"/>
																<label class="control-label">Longitud</label>
																<input type="text" name="longitude" placeholder="Ej: -3.70379" class="m-wrap span8" value="${cmd ? cmd.longitude : pub.longitude}"/>															
																<label class="control-label">Dirección</label>
																<input type="text" name="address" placeholder="Ej: Navarra 34" class="m-wrap span8" value="${cmd ? cmd.address : pub.address}"/>															
																<label class="control-label">Barrio (se autoestablece a partir de las coordenadas)</label>
																<input type="text" value="${pub?.district?.name}" disabled/>															
																<label class="control-label">Ciudad (se autoestablece a partir de las coordenadas)</label>
																<input type="text" value="${pub?.city?.name}" disabled/>	
																<label class="control-label">Provincia (se autoestablece a partir de las coordenadas)</label>
																<input type="text" value="${pub?.province?.name}" disabled/>	
																<label class="control-label">País (se autoestablece a partir de las coordenadas)</label>
																<input type="text" value="${pub?.country?.name}" disabled/>	
																<div class="submit-btn">
																	<button type="submit" class="btn green">Guardar</button>
																	<a href="${createLink(controller:'pub', params:['id': pub?.uri])}" class="btn">Cancelar</a>
																</div>																
															</form>

											</div>
											<!--end span8-->
											<div class="span4">

												<!-- BEGIN MARKERS PORTLET-->
												
												<div class="portlet">
													<div class="portlet-title">
														<div class="caption"><i class="icon-map-marker"></i>Mapa</div>
														<div class="tools">
															<a href="javascript:;" class="collapse"></a>
														</div>
													</div>
													<div class="portlet-body">
														<div id="gmap_pub" class="gmaps"></div>
													</div>
												</div>
												 
												<!-- END MARKERS PORTLET-->
											</div>
											<!--end span4-->
										
					
									
										
										
										
									</div>
									<!--end span9-->
					
					
					
					
					

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
			   $("#menu-configuration-pub-${pub?.uri}").addClass("active");

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