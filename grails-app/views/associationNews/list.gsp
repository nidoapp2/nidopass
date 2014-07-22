
<%@ page import="com.nidoapp.nidopub.domain.AssociationNews" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="admin"/>
		<g:set var="entityName" value="${message(code: 'associationNews.label', default: 'AssociationNews')}" />
		
		<title>NidoPass | Noticias</title>
	
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
		<!-- END PAGE LEVEL STYLES -->
	</head>
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
							Noticias Asociación
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-home"></i>
								<a href="#">Noticias</a> 
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
								<div class="caption"><i class="icon-shopping-cart"></i>Noticias</div>
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
					
						<th><i class="icon-th"></i><span class="title-icon-separation"> Noticia </span><acronym title="Crear nueva noticia"><a href="${createLink(controller:'associationNews', action:'create')}"><i class="icon-create-style icon-plus-sign"></i></a></acronym></th>
						<th class="hidden-phone"><i class="icon-calendar"></i> Fecha publicación </th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${associationNewsInstanceList}" status="i" var="associationNewsInstance">
					
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
							<td><g:link action="edit" id="${associationNewsInstance.id}">${fieldValue(bean: associationNewsInstance, field: "title")}</g:link></td>
						
							<td><g:formatDate format="dd-MMMM-yyyy" date="${associationNewsInstance.creationDate}" /></td>
						
						</tr>
					
				</g:each>
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
		   
		$("#menu-associationNews").addClass("active");

			// initiate layout and plugins
		   App.init();
		});
	</script>
	</body>
</html>
