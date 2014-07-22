<%@ page import="com.nidoapp.nidopub.domain.EMail" %>



<div class="fieldcontain ${hasErrors(bean: EMailInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="EMail.name.label" default="Nombre" />
		
	</label>
	<g:textField name="name" value="${EMailInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: EMailInstance, field: 'email', 'error')} ">
	<label for="email">
		<g:message code="EMail.email.label" default="Email" />
		
	</label>
	<g:textField name="email" value="${EMailInstance?.email}"/>
</div>

