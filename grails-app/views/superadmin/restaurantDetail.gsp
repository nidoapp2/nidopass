<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Administracion NidoPass</title>
	</head>
	<body>
		Sala: ${pub?.name}
		<br/>
		
		Propietario: ${pub?.owner?.firstName}, ${pub?.owner?.username}
		<br/>
		
		Carta:
		<p>${pub?.carte?.name} - ${pub?.carte?.description} - <g:link action="carteDetail" id="${it.id}">Ver</g:link></p>
			
	</body>
</html>
