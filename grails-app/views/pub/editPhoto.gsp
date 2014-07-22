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
								<a href="${createLink(controller:'pub', action:'editPhotos', params:['id': pub?.uri])}">Editar Fotos</a>
								<i class="icon-angle-right"></i>
							</li>
							<li>
								<a href="#">Editar Foto</a>
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid profile">
					<div class="span12">	
					
					
					
		
															<g:uploadForm action="savePhoto">
																<input type="hidden" name="pubUri" value="${pub?.uri}"/>
																<div class="controls">
																	<div class="thumbnail" style="width: 291px; height: 170px;">
																		<img src="${g.createLink(controller:'resources', action: 'pubImage', params:[pubUri: pub.uri, photoId: photo.id,repositoryImageId: photo.repositoryImageId])}" alt="${photo.description }" style="max-height: 100%;">
																	</div>
																</div>
																<div class="space10"></div>
																<div class="fileupload fileupload-new" data-provides="fileupload">
																	<div class="input-append">
																		<div class="uneditable-input">
																			<i class="icon-file fileupload-exists"></i> 
																			<span class="fileupload-preview"></span>
																		</div>
																		<span class="btn btn-file">
																		<span class="fileupload-new">Selecciona otra foto</span>
																		<span class="fileupload-exists">Cambiar</span>
																		<input type="file" class="default" name="photoFile" accept="image/*">
																		</span>
																		<a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Eliminar</a>
																	</div>
																</div>
																<div class="clearfix"></div>
																<label class="control-label">Descripción de la foto</label>																
																<textarea class="span8 m-wrap" rows="2" name="description">${photo.description }</textarea>
																
																<div class="control-group">
																	<label class="control-label">Imagen principal</label>
																	<div class="controls">
																			<label class="checkbox line">
																				<g:checkBox name="main" value="true" checked="${photo.main}"/> Establecer como imagen principal
																			</label>																		
																	</div>
																</div>
																
																<div class="space10"></div>
																<input type="hidden" name="repositoryImageId" value="${photo.repositoryImageId }">
																<div class="submit-btn">
																	<button type="submit" class="btn green">Guardar</button>
																	<a href="${createLink(controller:'pub', action:'editPhotos', params:['id': pub?.uri])}" class="btn">Cancelar</a>
																</div>
															</g:uploadForm>
					
					
					
					
														
														
							
				

														
														
														
														
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