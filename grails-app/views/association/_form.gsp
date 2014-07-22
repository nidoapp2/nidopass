<%@page import="com.nidoapp.nidopub.domain.geo.Province"%>
<%@page import="com.nidoapp.nidopub.domain.user.Role"%>
<%@page import="com.nidoapp.nidopub.domain.user.UserRole"%>
<%@page import="com.nidoapp.nidopub.domain.user.User"%>
<%@ page import="com.nidoapp.nidopub.domain.Association" %>

<div class="fieldcontain ${hasErrors(bean: associationInstance, field: 'owner', 'error')} required">
	<label for="owner">
		<g:message code="association.owner.label" default="Usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="owner" name="owner.id" from="${UserRole.findAllByRole(Role.findByAuthority(Role.ROLE_ASSOCIATION_OWNER)).user}" optionKey="id" required="" value="${associationInstance?.owner?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: associationInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="association.name.label" default="Nombre Asociación" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" value="${associationInstance?.name}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: associationInstance, field: 'province', 'error')} required">
	<label for="province">
		<g:message code="association.province.label" default="Provincia" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="province" name="province.id" from="${Province.findAll(sort:'name',order:'asc')}" optionKey="id" required="" value="${associationInstance?.province?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: associationInstance, field: 'maxPubsCreate', 'error')} ">
	<label for="maxPubsCreate">
		<g:message code="association.maxPubsCreate.label" default="Límite creación pubs" />
		
	</label>
	<g:textField name="maxPubsCreate" value="${associationInstance?.maxPubsCreate}"/>
</div>

