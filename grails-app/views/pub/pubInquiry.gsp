<!DOCTYPE html>
<head>
	<meta name="layout" content="admin"/>
	<title>NidoPass | Administración Encuesta Sala</title>
	
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
							Administración Encuesta
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-cogs"></i>
								<a href="#">Administración Encuesta</a> 
								<!--<i class="icon-angle-right"></i>-->
							</li>
							<!--<li>
								<a href="${createLink(controller:'pub', params:['id': pub?.uri])}">Sala</a>
								<i class="icon-angle-right"></i>
							</li>
							<li>
								<a href="#">Servicios</a>
							</li>-->
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid profile">
					<div class="span12">

						<!--<g:hasErrors bean="${cmd}">
							<g:eachError bean="${cmd}">
								<div class="alert alert-danger show">
									<button class="close" data-dismiss="alert"></button>
									<span><g:message error="${it}" /></span>
								</div>
							</g:eachError>
						</g:hasErrors>-->


					<div style="height: auto;" id="accordion1-1" class="accordion collapse">
							<form action="${createLink(controller:'pub', action:'savePubInquiryActive')}" method="post">
								<input type="hidden" name="pubUri" value="${pub?.uri}"/>
								<span class="control-label" style="font-size:14px;">Encuesta Activa </span>
								<g:checkBox name="inquiryActive" class="m-wrap span8" value="${true}" checked="${pub.inquiryActive}"/>
								<br>
								<div class="submit-btn">
									<button type="submit" class="btn green">Guardar</button>
								</div>																
							</form>
					
							<iframe src="http://encuestas.nidoapp.com/controlador.php?u=${pub?.owner.username }&p=${pub?.owner.password}&r=${pub?.uri}" style="width:100%; min-height:680px; border:none;"></iframe>								
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

		$("#menu-inquiry").addClass("active");
		$("#menu-inquiry-pub-${pub?.uri}").addClass("active");
		

			// initiate layout and plugins
		   App.init();

		});
	</script>

</body>
<!-- END BODY -->
</html>
<!-- Localized -->