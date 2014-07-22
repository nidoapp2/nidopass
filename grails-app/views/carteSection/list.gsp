
<%@ page import="com.nidoapp.nidopub.domain.CarteSection" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="index">
		<g:set var="entityName" value="${message(code: 'carteSection.label', default: 'CarteSection')}" />
		
		<title>NidoPass | Secciones Cartas</title>
	
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
		<!-- END PAGE LEVEL STYLES -->
	</head>
	<body>
		<div>
			<ul>
				<g:each in="${carteSectionInstanceList}" status="i" var="carteSectionInstance">
					
						<li><g:link action="edit" id="${carteSectionInstance.id}">${fieldValue(bean: carteSectionInstance, field: "name")}</g:link></li>
					
				</g:each>
			</ul>
		</div>
	</body>
</html>
