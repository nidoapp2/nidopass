<%@ page import="com.nidoapp.nidopub.domain.EventsListSend" %>
<calendar:resources lang="es" theme="tiger"/>


<div class="fieldcontain ${hasErrors(bean: eventsListSendInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="eventsListSend.title.label" default="Título Listado Eventos Envío" />
		
	</label>
	<g:textField name="title" value="${eventsListSendInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eventsListSendInstance, field: 'sendDate', 'error')} required">
	<label for="sendDate">
		<g:message code="eventsListSend.sendDate.label" default="Fecha de Envío" />
		<span class="required-indicator">*</span>
	</label>
	<!--<g:datePicker name="sendDate" precision="day"  value="${eventsListSendInstance?.sendDate}"  />-->
	<calendar:datePicker id="sendDate" name="sendDate" dateFormat="%d/%m/%Y" showTime="false" value="${eventsListSendInstance?.sendDate}" required="" />
</div>

<div class="fieldcontain ${hasErrors(bean: eventsListSendInstance, field: 'events', 'error')} ">
	<label for="events">
		<g:message code="eventsListSend.events.label" default="Eventos" />
		
	</label>
	<g:select name="events" from="${com.nidoapp.nidopub.domain.PubEvent.list()}" multiple="multiple" optionKey="id" size="5" value="${eventsListSendInstance?.events*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: eventsListSendInstance, field: 'emailsLists', 'error')} ">
	<label for="emailsLists">
		<g:message code="eventsListSend.emailsLists.label" default="Listas Emails Envío" />
		
	</label>
	<g:select name="emailsLists" from="${com.nidoapp.nidopub.domain.EMailsList.list()}" multiple="multiple" optionKey="id" size="5" value="${eventsListSendInstance?.emailsLists*.id}" class="many-to-many"/>
</div>

