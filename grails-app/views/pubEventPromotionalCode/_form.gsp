<%@ page import="com.nidoapp.nidopub.domain.PubEventPromotionalCode" %>



<!--<div class="fieldcontain ${hasErrors(bean: pubEventPromotionalCodeInstance, field: 'code', 'error')} ">
	<label for="code">
		<g:message code="pubEventPromotionalCode.code.label" default="Code" />
		
	</label>
	<g:textField name="code" value="${pubEventPromotionalCodeInstance?.code}"/>
</div>-->

<div class="fieldcontain ${hasErrors(bean: pubEventPromotionalCodeInstance, field: 'discount', 'error')} required">
	<label for="discount">
		<g:message code="pubEventPromotionalCode.discount.label" default="Descuento" />
		<span class="required-indicator">*</span>
		<span style="color:red;">(Si se pone solo el número, el descuento se tomará como euros, si se pone además el símbolo %, se aplicará como porcentaje a descontar)</span>
	</label>
	<g:textField name="discount" value="${pubEventPromotionalCodeInstance?.discount}" placeholder="Ej: 2 o 20%" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventPromotionalCodeInstance, field: 'maxTickets', 'error')} required">
	<label for="maxTickets">
		<g:message code="pubEventPromotionalCode.maxTickets.label" default="Máximo Entradas" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="maxTickets" value="${pubEventPromotionalCodeInstance?.maxTickets}" placeholder="Ej: 2" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventPromotionalCodeInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="pubEventPromotionalCode.email.label" default="Email envío" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="email" value="${pubEventPromotionalCodeInstance?.email}" required=""/>
</div>

<!--<div class="fieldcontain ${hasErrors(bean: pubEventPromotionalCodeInstance, field: 'pubEvent', 'error')} required">
	<label for="pubEvent">
		<g:message code="pubEventPromotionalCode.pubEvent.label" default="Pub Event" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="pubEvent" name="pubEvent.id" from="${com.nidoapp.nidopub.domain.PubEvent.list()}" optionKey="id" required="" value="${pubEventPromotionalCodeInstance?.pubEvent?.id}" class="many-to-one"/>
</div>-->

<!--<div class="fieldcontain ${hasErrors(bean: pubEventPromotionalCodeInstance, field: 'used', 'error')} ">
	<label for="used">
		<g:message code="pubEventPromotionalCode.used.label" default="Used" />
		
	</label>
	<g:checkBox name="used" value="${pubEventPromotionalCodeInstance?.used}" />
</div>-->

