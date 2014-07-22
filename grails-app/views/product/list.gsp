
<%@ page import="com.nidoapp.nidopub.domain.Product" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="index">
		<g:set var="entityName" value="${message(code: 'product.label', default: 'Product')}" />
		
		<title>NidoPass | Productos</title>
	
		<!-- BEGIN PAGE LEVEL STYLES --> 
		<link href="${resource(dir: 'js/plugins/data-tables', file: 'DT_bootstrap.css')}" rel="stylesheet" type="text/css"/>
		<!-- END PAGE LEVEL STYLES -->
	</head>
	<body>
		<div class="portlet-body">
			<table class="table table-striped table-bordered table-advance table-hover">
				<thead>
					<tr>
					
						<th><i class="icon-th"></i><span class="title-icon-separation"> Producto </span><acronym title="Crear nuevo producto"><a href="${createLink(controller:'product', action:'create', params: [id_pub:params.id_pub])}"><i class="icon-create-style icon-plus-sign"></i></a></acronym></th>
						<th class="hidden-phone"> Descripción</th>
						<th class="hidden-phone"> Precio</th>
						<th class="hidden-phone"> Activo</th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${productInstanceList}" status="i" var="productInstance">
					
						<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
						
							<td><g:link action="edit" id="${productInstance.id}">${fieldValue(bean: productInstance, field: "name")}</g:link></td>
							
							<td>${fieldValue(bean: productInstance, field: "description")}</td>
							
							<td>${fieldValue(bean: productInstance, field: "price")}</td>
							
							<td>
							<g:if test="${fieldValue(bean: productInstance, field: "available").equalsIgnoreCase('true')}">
								SI
							</g:if>
							<g:else>
								NO
							</g:else>
							</td>
						
						</tr>
					
				</g:each>
				</tbody>
			</table>
		</div>
	</body>
</html>
