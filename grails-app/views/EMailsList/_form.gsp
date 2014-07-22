<%@ page import="com.nidoapp.nidopub.domain.EMailsList" %>



<div class="fieldcontain ${hasErrors(bean: EMailsListInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="EMailsList.title.label" default="Título listado" />
		
	</label>
	<g:textField name="title" value="${EMailsListInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: EMailsListInstance, field: 'emails', 'error')} ">
	<label for="emails">
		<g:message code="EMailsList.emails.label" default="Emails" />
		
	</label>
	<g:select name="emails" from="${com.nidoapp.nidopub.domain.EMail.list()}" multiple="multiple" optionKey="id" size="5" value="${EMailsListInstance?.emails*.id}" class="many-to-many"/>
</div>

