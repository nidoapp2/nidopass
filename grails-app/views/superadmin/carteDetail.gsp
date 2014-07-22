<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Administracion NidoPass</title>
	</head>
	<body>
		Carta: ${carte?.name}
		<br/>
		
		Descripcion: ${carte?.description}
		<br/>
		
		Secciones:
		<br/>
		<g:each in="${carte?.sections}">
		    <p>${it.name} - ${it.plates?.size()} Platos -  <g:link action="carteSectionDetail" id="${it.id}">Ver</g:link></p>
		</g:each>
		<br/>		
	
	</body>
</html>
