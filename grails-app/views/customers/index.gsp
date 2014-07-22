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
							Clientes <small>descubre a tus mejores clientes</small>
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">Clientes</a> 
							</li>							
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<div id="dashboard">					
					
					<div class="row-fluid">
						<div class="span6">
							<!-- BEGIN SAMPLE TABLE PORTLET-->           
							<div class="portlet">
								<div class="portlet-title">
									<div class="caption"><i class="icon-shopping-cart"></i>Mejores clientes</div>
									<div class="tools">
										<a href="javascript:;" class="collapse"></a>
										<a href="#portlet-config" data-toggle="modal" class="config"></a>
										<a href="javascript:;" class="reload"></a>
										<a href="javascript:;" class="remove"></a>
									</div>
								</div>
								<div class="portlet-body">
									<table class="table table-striped table-bordered table-advance table-hover">
										<thead>
											<tr>
												<th>Nombre</th>
												<th>Nº Reservas</th>
												<th>Nº Servicios</th>
												<th>Total pagado</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td><a href="#">Nombre Apellidos Apellidos</a></td>
												<td>13</td>
												<td>18</td>
												<td>525 €</td>
											</tr>
											<tr>
												<td><a href="#">Nombre Apellidos Apellidos</a></td>
												<td>13</td>
												<td>18</td>
												<td>525 €</td>
											</tr>
											<tr>
												<td><a href="#">Nombre Apellidos Apellidos</a></td>
												<td>13</td>
												<td>18</td>
												<td>525 €</td>
											</tr>
											<tr>
												<td><a href="#">Nombre Apellidos Apellidos</a></td>
												<td>13</td>
												<td>18</td>
												<td>525 €</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
							<!-- END SAMPLE TABLE PORTLET-->
						</div>					
						<div class="span6">
							<!-- BEGIN REGIONAL STATS PORTLET-->
							<div class="portlet">
								<div class="portlet-title">
									<div class="caption"><i class="icon-globe"></i>De dónde son</div>
									<div class="tools">
										<a href="" class="collapse"></a>
										<a href="#portlet-config" data-toggle="modal" class="config"></a>
										<a href="" class="reload"></a>
										<a href="" class="remove"></a>
									</div>
								</div>
								<div class="portlet-body">
									<div id="region_statistics_loading">
										<img src="${resource(dir: 'images', file: 'loading.gif')}" alt="loading" />
									</div>
									<div id="region_statistics_content" class="hide">
										<div class="btn-toolbar">
											<div class="btn-group " data-toggle="buttons-radio">
												<a href="" class="btn mini active">Users</a>
												<a href="" class="btn mini">Orders</a> 
											</div>
											<div class="btn-group pull-right">
												<a href="" class="btn mini dropdown-toggle" data-toggle="dropdown">
												Select Region <span class="icon-angle-down"></span>
												</a>
												<ul class="dropdown-menu pull-right">
													<li><a href="javascript:;" id="regional_stat_world">World</a></li>
													<li><a href="javascript:;" id="regional_stat_usa">USA</a></li>
													<li><a href="javascript:;" id="regional_stat_europe">Europe</a></li>
													<li><a href="javascript:;" id="regional_stat_russia">Russia</a></li>
													<li><a href="javascript:;" id="regional_stat_germany">Germany</a></li>
												</ul>
											</div>
										</div>
										<div id="vmap_world" class="vmaps chart hide"></div>
										<div id="vmap_usa" class="vmaps chart hide"></div>
										<div id="vmap_europe" class="vmaps chart hide"></div>
										<div id="vmap_russia" class="vmaps chart hide"></div>
										<div id="vmap_germany" class="vmaps chart hide"></div>
									</div>
								</div>
							</div>
							<!-- END REGIONAL STATS PORTLET-->
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

			$("#menu-customers").addClass("active");
			
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