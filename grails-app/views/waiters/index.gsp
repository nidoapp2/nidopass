<!DOCTYPE html>
<head>
	<meta name="layout" content="admin"/>
	<title>NidoPass | Cocina</title>
	
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
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
					<h3>portlet Settings</h3>
				</div>
				<div class="modal-body">
					<p>Here will be a configuration form</p>
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
							El servicio <small>en tiempo real</small>
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">El servicio</a> 
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->   

				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN SAMPLE TABLE PORTLET-->           
						<div class="portlet">
							<div class="portlet-title">
								<div class="caption"><i class="icon-shopping-cart"></i>Platos listos para servir</div>
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
											<th><i class="icon-th"></i> Mesa</th>
											<th><i class="icon-time"></i> Espera</th>
											<th class="hidden-phone"><i class="icon-info-sign"></i> Platos</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><a href="#">Mesa 15</a></td>
											<td>								
											<span class="label label-important label-mini">5 minutos de espera</span>
											</td>
											<td class="hidden-phone">
											Ensaladilla rusa - Menu Especial (Primeros) - <b>Juan</b>
											<br/>
											Ensalada aragonesa - Carta (Ensaladas) - <b>Antonio</b>
											</td>
											<td><a href="#" class="btn green icn-only"><i class="icon-signout icon-white"></i></a></td>
										</tr>
										<tr>
											<td>
												<a href="#">
												Mesa 7
												</a>  
											</td>
											<td>
											<span class="label label-warning label-mini">1 minuto de espera</span>
											</td>
											<td class="hidden-phone">
											Ensalada de pasta - Menu Especial (Primeros) - <b>Javier</b>
											<br/>
											Ensalada aragonesa - Carta (Ensaladas) - <b>Ana</b>
											<br/>
											Paella - Carta (Arroces) - <b>Paula</b>
											</td>
											<td><a href="#" class="btn green icn-only"><i class="icon-signout icon-white"></i></a></td>
										</tr>
										<tr>
											<td><a href="#">Mesa 1</a></td>
											<td><span class="label label-success label-mini">En tiempo</span></td>
											<td class="hidden-phone">
											Solomillo (al punto) - Menu Especial (Segundos) - <b>Ricardo</b>
											<br/>
											Ternasco (poco hecho) - Carta (Carnes) - <b>María</b>
											</td>
											<td><a href="#" class="btn green icn-only"><i class="icon-signout icon-white"></i></a></td>
										</tr>										
									</tbody>
								</table>
							</div>
						</div>
						<!-- END SAMPLE TABLE PORTLET-->
					</div>
				</div>
				<div class="clearfix"></div>
				
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN SAMPLE TABLE PORTLET-->           
						<div class="portlet">
							<div class="portlet-title">
								<div class="caption"><i class="icon-shopping-cart"></i>Próximos platos a salir</div>
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
											<th><i class="icon-th"></i> Mesa</th>
											<th><i class="icon-time"></i> Salida estimada</th>
											<th class="hidden-phone"><i class="icon-info-sign"></i> Platos</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td><a href="#">Mesa 11</a></td>
											<td>								
											En 5 minutos
											</td>
											<td class="hidden-phone">
											Ensaladilla rusa - Menu Especial (Primeros) - <b>Juan</b>
											<br/>
											Ensalada aragonesa - Carta (Ensaladas) - <b>Antonio</b>
											</td>
										</tr>
										<tr>
											<td>
												<a href="#">
												Mesa 8
												</a>  
											</td>
											<td>
											En 15 minutos
											</td>
											<td class="hidden-phone">
											Ensalada de pasta - Menu Especial (Primeros) - <b>Javier</b>
											<br/>
											Ensalada aragonesa - Carta (Ensaladas) - <b>Ana</b>
											<br/>
											Paella - Carta (Arroces) - <b>Paula</b>
											</td>
										</tr>
										<tr>
											<td><a href="#">Mesa 17</a></td>
											<td>En 17 minutos</td>
											<td class="hidden-phone">
											Solomillo (al punto) - Menu Especial (Segundos) - <b>Ricardo</b>
											<br/>
											Ternasco (poco hecho) - Carta (Carnes) - <b>María</b>
											</td>
										</tr>										
									</tbody>
								</table>
							</div>
						</div>
						<!-- END SAMPLE TABLE PORTLET-->
					</div>
				</div>
				
				<!-- END PAGE CONTENT-->
			</div>
			<!-- END PAGE CONTAINER-->
		</div>
		<!-- END PAGE -->
		
		<!-- BEGIN PAGE LEVEL PLUGINS -->
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${resource(dir: 'js', file: 'app.js')}" type="text/javascript" ></script>
	<!-- END PAGE LEVEL SCRIPTS -->  
	<script>
	jQuery(document).ready(function() {       
		   
		   $("#menu-waiters").addClass("active");

			// initiate layout and plugins
		   App.init();
		});
	</script>

</body>
<!-- END BODY -->
</html>
<!-- Localized -->