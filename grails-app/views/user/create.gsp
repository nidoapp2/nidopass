<%@ page import="com.nidoapp.nidopub.domain.user.User" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin"/>
		<title>NidoPass | Nuevo Usuario</title>
		
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
		<!-- END PAGE LEVEL STYLES -->
	</head>
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
							Crear Nuevo Usuario
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">Usuario</a> 
							</li>
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->   

				<div class="row-fluid">
					<div class="span12">
						<div style="color:RED; font-size:18px;">
							<g:message code="${flash.message}"/>
						</div>
						<g:form action="save" >
							<fieldset class="form">
								<g:render template="formUser"/>
							</fieldset>
							<fieldset class="buttons">
								<g:submitButton name="create" class="save btn green" value="${message(code: 'default.button.create.label', default: 'Create')}" />
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

		$("#menu-configuration").addClass("active");
		$("#menu-new-user").addClass("active");
		$("#menu-configuration-users").addClass("active");

			// initiate layout and plugins
		   App.init();
		});
	</script>
	</body>
</html>
