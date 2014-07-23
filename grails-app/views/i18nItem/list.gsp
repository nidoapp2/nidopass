
<%@ page import="com.nidoapp.nidopub.domain.i18n.I18nItem" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="index">
		<g:set var="entityName" value="${message(code: 'i18nItem.label', default: 'Textos')}" />
		
		<title>NidoPass | Textos</title>
	
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
		<!-- END PAGE LEVEL STYLES -->
	</head>
	<body>
		<div class="portlet-body">
			<table class="table table-striped table-bordered table-advance table-hover">
				<thead>
					<tr>
					
						<th><i class="icon-th"></i><span class="title-icon-separation"> Texto </span><acronym title="Crear nuevo texto"><a href="${createLink(controller:'i18nItem', action:'create', params: [id_pub:params.id_pub])}"><i class="icon-create-style icon-plus-sign"></i></a></acronym></th>
						<th><i class="icon-th"></i><span class="title-icon-separation"> Traducciones </span><acronym title="Crear nueva traducción"><a href="${createLink(controller:'i18nTranslation', action:'create', params: [id_pub:params.id_pub])}"><i class="icon-create-style icon-plus-sign"></i></a></acronym></th>
					
					</tr>
				</thead>
				<tbody>
					<g:each in="${i18nItemInstanceList}" status="i" var="i18nItemInstance">
					
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
							<td><g:link action="edit" id="${i18nItemInstance.id}">${fieldValue(bean: i18nItemInstance, field: "defaultText")}</g:link></td>
						
							<td><div id="table_translations_${i18nItemInstance.id}"></div></td>
							
							
							<!-- DESARROLLO -->
							<!--<script>
								$('#table_translations_${i18nItemInstance.id}').load('/nidopub-webapp/i18nTranslation/list', {id_i18nItem:${i18nItemInstance.id}});
							</script>-->
							<!-- FIN DESARROLLO -->
							
							<!-- TEST Y PROD -->
							<script>
								$('#table_translations_${i18nItemInstance.id}').load(${grailsApplication.config.grails.serverURL + '/i18nTranslation/list'}, {id_i18nItem:${i18nItemInstance.id}});
							</script>
							<!-- FIN TEST Y PROD -->
						
						</tr>
					
				</g:each>
				</tbody>
			</table>
		</div>
	</body>
</html>
