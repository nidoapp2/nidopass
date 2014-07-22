<%@ page import="com.nidoapp.nidopub.domain.Carte" %>



<div class="fieldcontain ${hasErrors(bean: carteInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="carte.name.label" default="Nombre Carta" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" value="${carteInstance?.name}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: carteInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="carte.description.label" default="Descripción" />
		
	</label>
	<g:textField name="description" value="${carteInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: carteInstance, field: 'active', 'error')} ">
	<label for="active">
		<g:message code="carte.active.label" default="Activa" />
		
	</label>
	<g:checkBox name="active" value="${carteInstance?.active}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: carteInstance, field: 'sections', 'error')} ">
	<label for="sections">
		<g:message code="carte.sections.label" default="Secciones" />
		
	</label>
	
<ul class="one-to-many">
<g:if test="${carteInstance?.sections}">
	<g:each in="${carteInstance?.sections?}" var="s">
	    <li><g:link controller="carteSection" action="edit" id="${s.id}">${s?.encodeAsHTML()}</g:link></li>
	</g:each>
</g:if>
<g:else>
	Esta carta no tiene secciones todavía.
</g:else>
<!--
<li class="add">
<g:link controller="carteSection" action="create" params="['carte.id': carteInstance?.id, 'id_pub':carteInstance?.pub_id]" style="color: green">${message(code: 'default.add.label', args: [message(code: 'carteSection.label', default: 'Secciones Carta')])}</g:link>
</li>
-->
</ul>

</div>

