
<div class="fieldcontain ${hasErrors(bean: pubInstance, field: 'city', 'error')} required">
	<label for="city">
		<g:message code="pub.city.label" default="Ciudad" />
		<span class="required-indicator">*</span>
	</label>
	<g:if test="${citiesList}">
		<g:select id="city" name="city.id" from="${citiesList}" optionKey="id" required="" noSelection="['' : 'Selecciona una ciudad']" value="${pubInstance?.city?.id}" class="many-to-one" />
	</g:if>
	<g:else>
		No existen ciudades para esta provincia.
	</g:else>
</div>

