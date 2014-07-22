<!DOCTYPE html>
<head>
	<meta name="layout" content="admin"/>
	<title>NidoPass | Admin Dashboard</title>
	
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link href="${resource(dir: 'js/plugins/gritter/css', file: 'jquery.gritter.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'js/plugins/bootstrap-daterangepicker', file: 'daterangepicker.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'js/plugins/fullcalendar/fullcalendar', file: 'fullcalendar.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'js/plugins/jqvmap/jqvmap', file: 'jqvmap.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'js/plugins/jquery-easy-pie-chart', file: 'jquery.easy-pie-chart.css')}" rel="stylesheet" type="text/css"/>
	<!-- END PAGE LEVEL STYLES -->
	
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>

<!-- BEGIN PAGE -->
		<div class="page-content">
			<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<div id="portlet-config" class="modal hide">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3>Widget Settings</h3>
				</div>
				<div class="modal-body">
					Widget settings form goes here
				</div>
			</div>
			<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
			<!-- BEGIN PAGE CONTAINER-->
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->
				<div class="row-fluid">
					<div class="span12">						  
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<h3 class="page-title">
							Reservas <small>calendario</small>
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">Reservas</a> 
							</li>							
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<div id="dashboard">					
					
					<div class="row-fluid">
						<div class="span12">
							<!-- BEGIN PORTLET-->
							<div class="portlet box blue calendar">
								<div class="portlet-title">
									<div class="caption"><i class="icon-calendar"></i>Reservas</div>
								</div>
								<div class="portlet-body light-grey">
									<div id="calendar">
									</div>
								</div>
							</div>
							<!-- END PORTLET-->
						</div>
					</div>
				</div>
			</div>
			<!-- END PAGE CONTAINER-->    
		</div>
		<!-- END PAGE -->
		
		<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap', file: 'jquery.vmap.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap/maps', file: 'jquery.vmap.russia.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap/maps', file: 'jquery.vmap.world.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap/maps', file: 'jquery.vmap.europe.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap/maps', file: 'jquery.vmap.germany.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap/maps', file: 'jquery.vmap.usa.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap/data', file: 'jquery.vmap.sampledata.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/flot', file: 'jquery.flot.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/flot', file: 'jquery.flot.resize.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins', file: 'jquery.pulsate.min.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/bootstrap-daterangepicker', file: 'date.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/bootstrap-daterangepicker', file: 'daterangepicker.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/gritter/js', file: 'jquery.gritter.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/fullcalendar/fullcalendar', file: 'fullcalendar.min.js')}" type="text/javascript" ></script>
    <script src="${resource(dir: 'js/plugins/jquery-easy-pie-chart', file: 'jquery.easy-pie-chart.js')}" type="text/javascript" ></script> 
    <script src="${resource(dir: 'js/plugins', file: 'jquery.sparkline.min.js')}" type="text/javascript" ></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${resource(dir: 'js', file: 'app.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js', file: 'index.js')}" type="text/javascript" ></script>       
	<!-- END PAGE LEVEL SCRIPTS -->  
	<script>
		jQuery(document).ready(function() {    

			$("#menu-booking").addClass("active");
			
		   App.init(); // initlayout and core plugins
		   Index.init();
		   Index.initJQVMAP(); // init index page's custom scripts
		   Index.initCalendar(); // init index page's custom scripts
		   Index.initCharts(); // init index page's custom scripts
		   Index.initChat();
		   Index.initMiniCharts();
		   Index.initDashboardDaterange();
		   Index.initIntro();
		});
	</script>

</body>
<!-- END BODY -->
</html>
<!-- Localized -->