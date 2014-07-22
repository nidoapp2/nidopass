<%@ page import="com.nidoapp.nidopub.domain.PubAppsAuthenticationCode" %>

<div class="fieldcontain ${hasErrors(bean: pubAppsAuthenticationCodeInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="pubAppsAuthenticationCode.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="email" value="${pubAppsAuthenticationCodeInstance?.email}" required=""/>
</div>

