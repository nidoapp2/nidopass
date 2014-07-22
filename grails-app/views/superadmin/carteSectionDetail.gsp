<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Administracion NidoPass</title>
	</head>
	<body>
		Section de Carta: ${carteSection?.name}
		<br/>
		
		Platos:
		<br/>
		<g:each in="${carteSection?.plates}">
		    <p>${it.name} - ${it.description} -  <g:link action="productDetail" id="${it.id}">Ver</g:link></p>
		</g:each>
		<br/>		
	
	</body>
</html>
