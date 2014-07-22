<!DOCTYPE html>
<%@page import="com.nidoapp.nidopub.domain.Pub"%>
<head>
	<meta name="layout" content="admin"/>
	<title>NidoPass | Información Facturación Entradas</title>
	
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link href="${resource(dir: 'js/plugins/bootstrap-fileupload', file: 'bootstrap-fileupload.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'js/plugins/chosen-bootstrap/chosen', file: 'chosen.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'css/pages', file: 'profile.css')}" rel="stylesheet" type="text/css"/>
	
	<link href="${resource(dir: 'js/plugins/fancybox/source', file: 'jquery.fancybox.css')}" rel="stylesheet" type="text/css"/>
	
	<calendar:resources lang="es" theme="tiger"/>
	
	<!-- END PAGE LEVEL STYLES -->
	
	
<script>

	var url = "${createLink(controller: 'pubEvent', action:'listPubTicketsSoldByDates')}";
	
	function cambiarPagina(param1, param2) {

		<!-- DESARROLLO -->
		/*if (typeof(param1)=='string' && typeof(param2)=='string') {
			$('#table_tickets_sold').load('/nidopub-webapp/pubEvent/listPubTicketsSoldByDates', {id_pub:'${pub.uri}', dateInvoiceFrom: param1,  dateInvoiceTo: param2});
		} else {
			$('#table_tickets_sold').load('/nidopub-webapp/pubEvent/listPubTicketsSoldByDates', {id_pub:'${pub.uri}', dateInvoiceFrom: $(param1).val(),  dateInvoiceTo: $(param2).val()});
		}*/
		<!-- FIN DESARROLLO -->
		
		<!-- TEST Y PROD -->
		if (typeof(param1)=='string' && typeof(param2)=='string') {
			$('#table_tickets_sold').load('/pubEvent/listPubTicketsSoldByDates', {id_pub:'${pub.uri}', dateInvoiceFrom: param1,  dateInvoiceTo: param2});
		} else {
			$('#table_tickets_sold').load('/pubEvent/listPubTicketsSoldByDates', {id_pub:'${pub.uri}', dateInvoiceFrom: $(param1).val(),  dateInvoiceTo: $(param2).val()});
		}
		<!-- FIN TEST Y PROD -->
		
	}
	
	$(document).ready(function(){
		var dateFrom = $('#dateFrom_value');
		var dateTo = $('#dateTo_value');
		cambiarPagina(dateFrom, dateTo);
		});
	
</script>
	
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
								<a href="#">Información Facturación de Venta de Entradas Online de Sala</a> 
								<i class="icon-angle-right"></i>
							</li>
							<!--<li>
								<a href="${createLink(controller:'pub', params:['id': pub?.uri])}">Sala</a>
								<i class="icon-angle-right"></i>
							</li>-->
							<li>
								<a href="#">Información Facturación</a>
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
															<!--<form action="${createLink(controller:'pub', action:'listPubTicketsSoldByDates')}" method="post">-->
																<input type="hidden" name="pubUri" value="${cmd ? cmd?.pubUri : pub?.uri}"/>
																<table>
																<tr>
																<td><span class="control-label" style="font-size:14px;">Fecha (Desde): </span></td>
																<td><calendar:datePicker id="dateFrom" name="dateFrom" dateFormat="%d/%m/%Y" showTime="false" required="" value="${new Date().minus(new Date().getDate()-1)}"/></td>
																</tr><tr>
																<td><span class="control-label" style="font-size:14px;">Fecha (Hasta): </span></td>
																<td><calendar:datePicker id="dateTo" name="dateTo" dateFormat="%d/%m/%Y" showTime="false" required="" value="${new Date()}"/></td>
																</tr>
																</table>
																<br>
																<!--<div class="submit-btn">
																	<button type="submit" class="btn green">Ver listado</button>
																</div>																
															</form>-->
															
															<span class="btn green" onClick="cambiarPagina($('#dateFrom_value'),$('#dateTo_value'));">Ver listado</span>
															
															<div class="portlet-body" id="table_tickets_sold" style="margin-top:10px !important;"></div>
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