<%@ page import="com.nidoapp.nidopub.domain.user.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
	<label for="username">
		<g:message code="user.username.label" default="Tú email (Lo usarás para acceder)" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" required="" value="${userInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} ">
	<label for="password">
		<g:message code="user.password.label" default="Nueva contraseña" />
		
	</label>
	<g:passwordField name="password" value="${userInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, 'error')} ">
	<label>
		<g:message code="user.password.label" default="Repita la nueva contraseña" />
		
	</label>
	<g:passwordField name="password2"/>
</div>

