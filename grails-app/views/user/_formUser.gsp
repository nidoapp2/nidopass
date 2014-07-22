<%@page import="com.nidoapp.nidopub.domain.user.Role"%>
<%@ page import="com.nidoapp.nidopub.domain.user.User" %>


<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'firstName', 'error')} required">
	<label for="firstName">
		<g:message code="user.firstName.label" default="Nombre" />
		<span class="required-indicator">*</span>
		
	</label>
	<g:textField name="firstName" required="" value="${userInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'lastName', 'error')} required">
	<label for="lastName">
		<g:message code="user.lastName.label" default="Apellidos" />
		<span class="required-indicator">*</span>
		
	</label>
	<g:textField name="lastName" required="" value="${userInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
	<label for="username">
		<g:message code="user.username.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" required="" value="${userInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="user.password.label" default="Contraseña" />
		<span class="required-indicator">*</span>
	</label>
	<g:passwordField name="password" required="" value="${userInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'role', 'error')} required">
	<label for="role">
		<g:message code="user.role.label" default="Tipo usuario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="role" name="role.id" from="${Role.findAll(sort:'authority')}" optionKey="id" required="" value="${Role.ROLE_PUB_OWNER}" class="many-to-one"/>
</div>

