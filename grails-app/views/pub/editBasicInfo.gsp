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
								<a href="#">Editar Información Básica</a>
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
									<span><g:message error="${it}"/></span>
								</div>
							</g:eachError>
						</g:hasErrors>
					
						

						
	
	
						<div style="height: auto;" id="accordion1-1"
							class="accordion collapse">
							<form action="${createLink(controller:'pub', action:'saveBasicInfo')}" method="post">
								<input type="hidden" name="pubUri" value="${cmd ? cmd?.pubUri : pub?.uri}"/>
								<label class="control-label">Nombre de la sala</label> <input
									type="text" name="name" placeholder="Nombre de la sala"
									class="m-wrap span8" value="${cmd ? cmd?.name : pub.name}" />
								<label class="control-label">Descripción</label>
								<textarea class="span8 m-wrap" rows="3" name="description">${cmd ? cmd?.description : pub.description}</textarea>
								<label class="control-label">Teléfono de atención al
									cliente</label> <input type="text" name="telephone"
									placeholder="Ej: 666666666" class="m-wrap span8"
									value="${cmd ? cmd?.telephone : pub.telephone}" /> 
								<label class="control-label">Fax</label> <input type="text" name="fax"
									placeholder="Ej: 999999999" class="m-wrap span8"
									value="${cmd ? cmd?.fax : pub.fax}" />	
									<label
									class="control-label">Email de atención al cliente</label> <input
									type="text" name="email" placeholder="Ej: info@dominio.es"
									class="m-wrap span8" value="${cmd ? cmd?.email : pub.email}" />
								<label class="control-label">Sitio Web de la sala</label> <input
									type="text" name="website"
									placeholder="Ej: http://www.dominio.es" class="m-wrap span8"
									value="${cmd ? cmd?.website : pub.website}" />
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