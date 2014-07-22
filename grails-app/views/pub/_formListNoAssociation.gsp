<%@ page import="com.nidoapp.nidopub.domain.Pub" %>


<!-- 
<div class="fieldcontain ${hasErrors(bean: pubInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="pub.name.label" default="Pub" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="name" name="id" from="${listNoAssociation}" optionKey="id" required="" 
			noSelection="['' : 'Selecciona un pub']" 
			value="${pubInstanceList?.id}" class="many-to-one"/>
</div>
-->

<div id="estadoDiv">
											
	<g:include controller="pub" action="listNoAssociation" />
	
</div>
