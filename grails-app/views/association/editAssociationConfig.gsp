<!DOCTYPE html>
<%@page import="com.nidoapp.nidopub.domain.Pub"%>
<head>
	<meta name="layout" content="admin"/>
	<title>NidoPass | Datos Configuración Asociación</title>
	
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
							Asociación <small>${association?.name}</small>
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-cogs"></i>
								<a href="#">Administración de Datos de Configuración de Asociación</a> 
								<i class="icon-angle-right"></i>
							</li>
							<!--<li>
								<a href="${createLink(controller:'association', params:['id': association?.id])}">Asociación</a>
								<i class="icon-angle-right"></i>
							</li>-->
							<li>
								<a href="#">Datos Configuración</a>
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
															<form action="${createLink(controller:'association', action:'saveAssociationConfig')}" method="post">
																<input type="hidden" name="associationId" value="${cmd ? cmd?.associationId : association?.id}"/>
																<table>
																<tr>
																<td><span class="control-label" style="font-size:14px;">Nombre Asociación </span></td>
																<td><g:textField name="name" placeholder="Ej: Ejemplo" required="" value="${association.name}"/></td>
																</tr>
																<tr>
																<td><span class="control-label" style="font-size:14px;">Límite Creación Salas </span></td>
																<td><g:textField name="maxPubsCreate" placeholder="Ej: 50" required="" value="${association.maxPubsCreate}"/></td>
																</tr>
																</table>
																<br>
																<div class="submit-btn">
																	<button type="submit" class="btn green">Guardar</button>
																	<a href="${createLink(controller:'association', action:'listProvinceAssociations', params:['id_province': association?.province.id])}" class="btn">Cancelar</a>
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
		$("#menu-configuration-associations").addClass("active");
		$("#menu-configuration-associations-province-${association?.province.name}").addClass("active");
			//$("#menu-finance-admin").addClass("active");
			  //$("#menu-finance-admin-${province?.name}").addClass("active");

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