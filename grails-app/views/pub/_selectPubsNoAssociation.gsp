
<div class="fieldcontain required">
	<label for="pubs">
		<g:message code="association.pubs.label" default="Pubs" />
		<span class="required-indicator">*</span>
	</label>
	<g:if test="${listNoAssociation}">
		<g:select id="name" name="pub.id" from="${listNoAssociation}" optionKey="id" required="" noSelection="['' : 'Selecciona una sala']" value="${pubInstance?.id}" class="many-to-one" />
	</g:if>
	<g:else>
		No existen salas fuera de la asociación asociación.
	</g:else>
</div>

