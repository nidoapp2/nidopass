
<%@ page import="com.nidoapp.nidopub.domain.Carte" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="index">
		<g:set var="entityName" value="${message(code: 'carte.label', default: 'Carte')}" />
		
		<title>NidoPass | Cartas</title>
	
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
		<!-- END PAGE LEVEL STYLES -->
	</head>
	<body>
		<div class="portlet-body">
			<table class="table table-striped table-bordered table-advance table-hover">
				<thead>
					<tr>
					
						<th><i class="icon-th"></i><span class="title-icon-separation"> Carta </span><acronym title="Crear nueva carta"><a href="${createLink(controller:'carte', action:'create', params: [id_pub:params.id_pub])}"><i class="icon-create-style icon-plus-sign"></i></a></acronym></th>
						<th><i class="icon-th"></i><span class="title-icon-separation"> Secciones </span><acronym title="Crear nueva sección de carta"><a href="${createLink(controller:'carteSection', action:'create', params: [id_pub:params.id_pub])}"><i class="icon-create-style icon-plus-sign"></i></a></acronym></th>
						<th class="hidden-phone"> Descripción</th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${carteInstanceList}" status="i" var="carteInstance">
					
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
							<td><g:link action="edit" id="${carteInstance.id}">${fieldValue(bean: carteInstance, field: "name")}</g:link></td>
						
							<td><div id="table_carteSections_${carteInstance.id}"></div></td>
							
							<td>${fieldValue(bean: carteInstance, field: "description")}</td>
							
							
							<!--DESARROLLO-->
							<!--<script>
								$('#table_carteSections_${carteInstance.id}').load('/nidopub-webapp/carteSection/list', {id_carte:${carteInstance.id}});
							</script>-->
							<!-- FIN DESARROLLO -->
							
							<!--TEST Y PROD-->
							<script>
								$('#table_carteSections_${carteInstance.id}').load('${grailsApplication.config.grails.serverURL + '/carteSection/list'}', {id_carte:${carteInstance.id}});
							</script>
							<!-- FIN TEST Y PROD -->
						
						</tr>
					
				</g:each>
				</tbody>
			</table>
		</div>
		
	</body>
</html>
