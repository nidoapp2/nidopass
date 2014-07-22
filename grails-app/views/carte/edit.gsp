<%@ page import="com.nidoapp.nidopub.domain.Carte" %>
<!DOCTYPE html>
<head>
	<meta name="layout" content="admin"/>
	<title>NidoPass | Editar Carta</title>
	
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
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
							Editar Carta
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">Carta</a> 
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->   

				<div class="row-fluid">
					<div class="span12">
						<g:form method="post" >
							<g:hiddenField name="id" value="${carteInstance?.id}" />
							<g:hiddenField name="version" value="${carteInstance?.version}" />
							<fieldset class="form">
								<g:render template="form"/>
							</fieldset>
							<fieldset class="buttons">
								<g:actionSubmit class="save btn green" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
								<g:actionSubmit class="delete btn red" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" formnovalidate="" onclick="return confirm('${message(code: '¿Seguro que quieres borrar esta carta?, se eliminarán también todas sus secciones, y esta acción no se podrá deshacer.', default: '¿Seguro que quieres borrar esta carta?, se eliminarán también todas sus secciones, y esta acción no se podrá deshacer.')}');" />
							</fieldset>
						</g:form>
					</div>
				</div>
				<div class="clearfix"></div>
				
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
		   
		   //$("#menu-cartesAndProducts").addClass("active");
		   //$("#menu-cartesAndProducts-pub-${pub?.uri}").addClass("active");
		   
		   $("#menu-configuration").addClass("active");
			$("#menu-configuration-pubs").addClass("active");
			$("#menu-configuration-pub-${pub?.uri}").addClass("active");

			// initiate layout and plugins
		   App.init();
		});
	</script>

</body>
</html>
