<!DOCTYPE html>
<%@page import="com.nidoapp.nidopub.domain.Pub"%>
<head>
	<meta name="layout" content="admin"/>
	<title>NidoPass | Datos Financieros Sala</title>
	
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
								<a href="#">Administración de Datos Financieros de Venta de Entradas Online de Sala</a> 
								<i class="icon-angle-right"></i>
							</li>
							<!--<li>
								<a href="${createLink(controller:'pub', params:['id': pub?.uri])}">Sala</a>
								<i class="icon-angle-right"></i>
							</li>-->
							<li>
								<a href="#">Datos Financieros</a>
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
															<form action="${createLink(controller:'pub', action:'savePubFinance')}" method="post">
																<input type="hidden" name="pubUri" value="${cmd ? cmd?.pubUri : pub?.uri}"/>
																<table>
																<tr>
																<td><span class="control-label" style="font-size:14px;">IBAN </span></td>
																<td><g:textField name="iban" placeholder="Ej: ES1100000000000000000000" required="" value="${pub.iban}"/></td>
																</tr><tr>
																<td><span class="control-label" style="font-size:14px;">SEPA </span></td>
																<td><g:checkBox name="sepa" class="m-wrap span8" value="${false}" checked="${pub.sepa}"/><br><br></td>
																</tr><tr>
																<td><span class="control-label" style="font-size:14px;">Razón social </span></td>
																<td><g:textField name="businessName" placeholder="Ej: Empresa S.L." required="" value="${pub.businessName}"/></td>
																</tr><tr>
																<td><span class="control-label" style="font-size:14px;">Persona de contacto </span></td>
																<td><g:textField name="contactPerson" placeholder="Ej: José Pérez" required="" value="${pub.contactPerson}"/></td>
																</tr><tr>
																<td><span class="control-label" style="font-size:14px;">Teléfono de contacto </span></td>
																<td><g:textField name="contactTelephone" placeholder="Ej: 900000000" required="" value="${pub.contactTelephone}"/></td>
																</tr><tr>
																<td><span class="control-label" style="font-size:14px;">CIF/NIF </span></td>
																<td><g:textField name="nif" placeholder="Ej: 00000000A" required="" value="${pub.nif}"/></td>
																</tr><tr>
																<td><span class="control-label" style="font-size:14px;">Dirección de facturación </span></td>
																<td><g:textField name="invoiceAddress" placeholder="Ej: Av/ Navarra, 34, 50010, Zaragoza" required="" value="${pub.invoiceAddress}"/></td>
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