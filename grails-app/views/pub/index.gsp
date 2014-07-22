<%@ page import="com.nidoapp.nidopub.domain.user.Role" %>
<%@ page import="com.nidoapp.nidopub.domain.Pub" %>
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
								<a href="#">Sala</a>
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid profile">
					<div class="span12">
										
					
									<ul class="unstyled profile-nav span3">
										<li><img src="${g.mainPhotoUrl('pub':pub)}" alt="" /> <a href="#portlet-photos" class="profile-edit">edit</a></li>
										<li><a href="#portlet-logo">Logo Sala</a></li>
										<li><a href="#portlet-specialty">Especialidad y tipo de música</a></li>
										<li><a href="#portlet-features">Características</a></li>
										<li><a href="#portlet-photos">Fotos</a></li>
										<li><a href="#portlet-promotional-images">Imágenes promocionales</a></li>
										<li><a href="#portlet-location">Ubicación</a></li>
										<li><a href="#portlet-ticketonlinesale">Venta entradas online</a></li>
										<li><a href="#portlet-ticketonlinesaleother">Otros datos venta entradas online</a></li>
										<li><a href="#portlet-others">Otros datos</a></li>
										<sec:ifAllGranted roles="${Role.ROLE_PUB_OWNER}">
											<li><a href="${createLink(controller:'pubAppsAuthenticationCode', action:'list', params:['id':pub?.id])}">Códigos Acceso Apps</a></li>
											<g:if test="${pub?.translationsAvailable }">
												<li><a href="${createLink(controller:'textAndTranslations', params:['id': pub?.uri])}">Textos y Traducciones</a></li>
											</g:if>
											<g:if test="${pub?.cartesAvailable }">
												<li><a href="${createLink(controller:'cartesAndProducts', params:['id': pub?.uri])}">Cartas y Productos</a></li>
											</g:if>
											<g:if test="${pub?.inquiryAvailable }">
												<li><a href="${createLink(controller:'pub', action:'pubInquiry', params:['id': pub?.uri])}">Encuesta Satisfacción</a></li>
											</g:if>
											<g:if test="${pub?.eventsAvailable }">
												<li><a href="${createLink(controller:'pubEvent', action:'list', params:['id_pub': pub?.uri])}">Eventos</a></li>
											</g:if>
											<g:if test="${pub?.eventsAvailable }">
												<li><a href="${createLink(controller:'EMailsAdminAccess', params:['id': pub?.uri])}">Envío Automático EMails</a></li>
											</g:if>
										</sec:ifAllGranted>
									</ul>
									<div class="span9">
										<div class="row-fluid">
											<div class="span8 profile-info">
												<h1>${pub?.name}</h1>
												<p>${pub?.description}</p>
												<ul class="unstyled inline">
													<li><i class="icon-globe"></i><a href="${pub?.website}">${pub?.website}</a></li>
													<li><i class="icon-envelope"></i> <a href="emailto:${pub?.email}">${pub?.email}</a></li>
													<li><i class="icon-info-sign"></i> ${pub?.telephone}</li>
												</ul>
												<p/>
												<p>
													<a href="${createLink(controller:'pub', action:'editBasicInfo', params:['id': pub?.uri])}" class="btn blue">Modificar datos básicos</a>
													<a href="javascript:eliminarPub();" class="btn red">Eliminar esta sala</a>
												</p>
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
													<a href="#portlet-location" class="btn blue mini">Ver datos de ubicación</a>													
												</div>
												 
												<!-- END MARKERS PORTLET-->
											</div>
											<!--end span4-->
										</div>
										<!--end row-fluid-->
										
										
										<div class="portlet solid bordered light-grey" id="portlet-logo">
											<div class="portlet-title">
												<div class="caption"><i class="icon-tags"></i>Logo</div>												
											</div>
											<div class="portlet-body">
												<img src="${g.createLink(controller:'resources', action: 'pubLogoImage', params:['pubUri': pub?.uri])}"></img>
												<p/>
												<p>
													<a href="${createLink(controller:'pub', action:'editPubLogo', params:['id': pub?.uri])}" class="btn blue">Cambiar Logo</a>
												</p>
											</div>
										</div>
										
										
										
										<div class="portlet solid bordered light-grey" id="portlet-specialty">
											<div class="portlet-title">
												<div class="caption"><i class="icon-star"></i>Especialidad y tipo de música</div>												
											</div>
											<div class="portlet-body">
												<p>Especialidad: ${pub?.specialty}</p>
												<p>Tipo de música: ${pub?.musicType?.name}</p>
												<p/>
												<p>
													<a href="${createLink(controller:'pub', action:'editSpecialty', params:['id': pub?.uri])}" class="btn blue">Modificar especialidad</a>
												</p>
											</div>
										</div>	
										
										<div class="portlet solid bordered light-grey" id="portlet-features">
											<div class="portlet-title">
												<div class="caption"><i class="icon-tags"></i>Características</div>												
											</div>
											<div class="portlet-body">
												<g:each in="${pub?.features}">
												    <p><img src="${g.createLink(controller:'resources', action: 'pubFeatureImage', params:[repositoryImageId: it.repositoryImageId])}"></img> ${it.name}</p>
												</g:each>
												<p/>
												<p>
													<a href="${createLink(controller:'pub', action:'editFeatures', params:['id': pub?.uri])}" class="btn blue">Añadir o quitar características</a>
												</p>
											</div>
										</div>	
										
										<div class="portlet solid bordered light-grey" id="portlet-photos">
											<div class="portlet-title">
												<div class="caption"><i class="icon-camera"></i>Fotos</div>												
											</div>
											<div class="portlet-body">
											
												<g:if test="${pub?.photos}">
													<g:set var="lastPhotoIndex" value="${pub?.photos?.size() - 1}" />
												</g:if>
												<g:each in="${pub?.photos}" status="i" var="it">
												    
												    <g:if test="${i%4 == 0 }">
												    	<div class="row-fluid">
												    </g:if>
												    
												    
												    <div class="span3">
														<div class="item">
															<a class="fancybox-button" data-rel="fancybox-button" title="${it.description }" href="${g.createLink(controller:'resources', action: 'pubImage', params:[pubUri: pub.uri, photoId: it.id,repositoryImageId: it.repositoryImageId])}">
																<div class="zoom">
																	<img src="${g.createLink(controller:'resources', action: 'pubImage', params:[pubUri: pub.uri, photoId: it.id,repositoryImageId: it.repositoryImageId])}" alt="${it.description }">                    
																	<div class="zoom-icon"></div>
																</div>
															</a>
														</div>
													</div>
													
													<g:if test="${(i+1)%4 == 0 || i == lastPhotoIndex}">
												    	</div>
												    </g:if>
													
												
												
												</g:each>	
												
												<p/>
												<p>
													<a href="${createLink(controller:'pub', action:'editPhotos', params:['id': pub?.uri])}" class="btn blue">Añadir o quitar fotos</a>
												</p>											
												
											</div>
										</div>	
										
										
										<div class="portlet solid bordered light-grey" id="portlet-promotional-images">
											<div class="portlet-title">
												<div class="caption"><i class="icon-camera"></i>Imágenes Promocionales</div>												
											</div>
											<div class="portlet-body">
											
												<g:if test="${pub?.promotionalImages}">
													<g:set var="lastPhotoIndex" value="${pub?.promotionalImages?.size() - 1}" />
												</g:if>
												<g:each in="${pub?.promotionalImages}" status="i" var="it">
												    
												    <g:if test="${i%4 == 0 }">
												    	<div class="row-fluid">
												    </g:if>
												    
												    
												    <div class="span3">
														<div class="item">
															<a class="fancybox-button" data-rel="fancybox-button" title="" href="${g.createLink(controller:'resources', action: 'pubPromotionalImage', params:[pubUri: pub.uri, imageId: it.id,image: it.image])}">
																<div class="zoom">
																	<img src="${g.createLink(controller:'resources', action: 'pubPromotionalImage', params:[pubUri: pub.uri, imageId: it.id, image: it.image])}" alt="">                    
																	<div class="zoom-icon"></div>
																</div>
															</a>
														</div>
													</div>
													
													<g:if test="${(i+1)%4 == 0 || i == lastPhotoIndex}">
												    	</div>
												    </g:if>
													
												
												
												</g:each>	
												
												<p/>
												<p>
													<a href="${createLink(controller:'pub', action:'editPromotionalImages', params:['id': pub?.uri])}" class="btn blue">Añadir o quitar imágenes promocionales</a>
												</p>											
												
											</div>
										</div>	
										
										
										<div class="portlet solid bordered light-grey" id="portlet-location">
											<div class="portlet-title">
												<div class="caption"><i class="icon-map-marker"></i>Ubicación</div>												
											</div>
											<div class="portlet-body">
											
												<p>Dirección: ${pub?.address}</p>
												<p>Barrio: ${pub?.district?.name}</p>
												<p>Ciudad: ${pub?.city?.name}</p>
												<p>Provincia: ${pub?.province?.name}</p>
												<p>País: ${pub?.country?.name}</p>
												<p/>
												<p>Latitud: ${pub?.latitude}</p>
												<p>Longitud: ${pub?.longitude}</p>
												<p/>
												<p>
													<a href="${createLink(controller:'pub', action:'editLocation', params:['id': pub?.uri])}" class="btn blue">Cambiar ubicación</a>
												</p>											
							
											</div>
										</div>	
										
										
										
										<div class="portlet solid bordered light-grey" id="portlet-ticketonlinesale">
											<div class="portlet-title">
												<div class="caption"><i class="icon-cogs"></i>Venta entradas online</div>												
											</div>
											<div class="portlet-body">
											
												<p>Persona contacto: ${pub?.contactPerson}</p>
												<p>Teléfono contacto: ${pub?.contactTelephone}</p>
												<p>NIF: ${pub?.nif}</p>
												<p>Dirección facturación: ${pub?.invoiceAddress}</p>
												<p>
													<a href="${createLink(controller:'pub', action:'editTicketOnlineSaleSettings', params:['id': pub?.uri])}" class="btn blue">Modificar datos venta online</a>
												</p>											
							
											</div>
										</div>
										
										
										
										<div class="portlet solid bordered light-grey" id="portlet-ticketonlinesaleother">
											<div class="portlet-title">
												<div class="caption"><i class="icon-cogs"></i>Otros datos venta entradas online</div>												
											</div>
											<div class="portlet-body">
											
												<p>Número inicial entradas venta online: ${pub?.ticketsSaleInitNumber}</p>
												<g:if test="${pub?.ticketsSaleInitNumberReset}">
													<p>Fecha reinicio numeración entradas: ${pub?.ticketsSaleInitNumberReset.getDate()+'/'+(pub?.ticketsSaleInitNumberReset.getMonth()+1)+'/'+(pub?.ticketsSaleInitNumberReset.getYear()+1900)}</p>
												</g:if>
												<g:else>
													<p>Fecha reinicio numeración entradas: Sin fecha de reinicio</p>
												</g:else>
												<p>
													<a href="${createLink(controller:'pub', action:'editTicketOnlineSaleOtherSettings', params:['id': pub?.uri])}" class="btn blue">Modificar datos venta online</a>
												</p>											
							
											</div>
										</div>	
										
										
										
										<div class="portlet solid bordered light-grey" id="portlet-others">
											<div class="portlet-title">
												<div class="caption"><i class="icon-cogs"></i>Otros datos</div>												
											</div>
											<div class="portlet-body">
												<p>Capacidad: ${pub?.customerCapacity} personas</p>
												<p>Horario apertura: ${pub?.openingHours}</p>
												<p>Precio carta (desde): ${pub?.cartePriceFrom}</p>
												<p>Precio carta (hasta): ${pub?.cartePriceTo}</p>
												<p/>
												<p>
													<a href="${createLink(controller:'pub', action:'editOtherData', params:['id': pub?.uri])}" class="btn blue">Modificar datos</a>
												</p>
											</div>
										</div>
										
										
										
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
	
	<script> 
		function eliminarPub(){ 
			confirmar=confirm("Antes de eliminar la sala, avisa a la asociación (si pertenece a alguna), para que lo den de baja en su listado de salas. ¿Seguro que quieres eliminar esta sala?, una vez confirmado no podrás volver atrás."); 
			if (confirmar){
				// si pulsamos en aceptar
				window.location.href="${createLink(controller:'pub', action:'delete', params:['id': pub?.id])}";
			} else {
				// si pulsamos en cancelar
				 
			}
		}
	</script>

</body>
<!-- END BODY -->
</html>
<!-- Localized -->