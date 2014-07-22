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
								<a href="#">Editar Fotos</a>
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid profile">
					<div class="span12">										
														
							
														
								<!-- BEGIN GALLERY MANAGER PANEL-->
								<div class="row-fluid">
										<div class="span12">
										<div class="pull-right">
											<a href="${createLink(controller:'pub', action:'editNewPhoto', params:['id': pub?.uri])}" class="btn pull-right blue"><i class="icon-plus"></i> Nueva foto</a>
				
										</div>
									</div>
								</div>
								<!-- END GALLERY MANAGER PANEL-->
								<hr class="clearfix">
								<!-- BEGIN GALLERY MANAGER LISTING-->
								
								<g:if test="${pub?.photos}">
									<g:set var="lastPhotoIndex" value="${pub?.photos?.size() - 1}" />
								</g:if>
								
								<g:each in="${pub?.photos}" status="i" var="it">
												    
												    <g:if test="${i%4 == 0 }">
												    	<div class="row-fluid">
												    </g:if>
												    
												    
												    <div class="span3">
												    	<g:if test="${it.main}">
															<b>
														</g:if>
														${it.description }
														<g:if test="${it.main}">
															</b> (Foto Principal)
														</g:if>
														<div class="item">
															<a class="fancybox-button" data-rel="fancybox-button" title="${it.description }" href="${g.createLink(controller:'resources', action: 'pubImage', params:[pubUri: pub.uri, photoId: it.id,repositoryImageId: it.repositoryImageId])}">
																<div class="zoom">
																	<img src="${g.createLink(controller:'resources', action: 'pubImage', params:[pubUri: pub.uri, photoId: it.id,repositoryImageId: it.repositoryImageId])}" alt="${it.description }">                    
																	<div class="zoom-icon"></div>
																</div>
																<div class="details">
																	<g:if test="${!it.main}">
																		<a href="${g.createLink(controller:'pub', action: 'saveMainPhoto', params:[id: pub?.uri, repositoryImageId: it.repositoryImageId])}" class="icon"><i class="icon-star" title="Establecer como foto principal"></i></a>
																	</g:if>																	
																	<a href="${g.createLink(controller:'pub', action: 'editPhoto', params:[id: pub?.uri, repositoryImageId: it.repositoryImageId])}" class="icon"><i class="icon-pencil" title="Modificar foto o descripción"></i></a>
																	<a href="${g.createLink(controller:'pub', action: 'removePhoto', params:[id: pub?.uri, repositoryImageId: it.repositoryImageId])}" class="icon"><i class="icon-remove"  title="Eliminar foto completamente (no hay vuelta atrás)"></i></a>    
																</div>
															</a>
														</div>
														
													</div>
													
													<g:if test="${(i+1)%4 == 0 || i == lastPhotoIndex}">
												    	</div>
												    </g:if>
													
												
												
												</g:each>
				

														
														
														
														
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