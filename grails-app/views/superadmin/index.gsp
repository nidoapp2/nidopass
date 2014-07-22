<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Administracion NidoPass</title>
	</head>
	<body>
		Hola ${user.firstName}
		<br/>
		
		Salas:
		<br/>
		
		
		<g:each in="${pubs}">
		    <p>${it.name} - ${it.website} - <g:link action="pubDetail" id="${it.id}">Ver</g:link></p>
		</g:each>
		
	</body>
</html>
