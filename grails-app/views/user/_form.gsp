<%@ page import="com.nidoapp.nidopub.domain.user.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'username', 'error')} required">
	<label for="username">
		<g:message code="user.username.label" default="Username" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="username" required="" value="${userInstance?.username}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="user.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="password" required="" value="${userInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'association', 'error')} ">
	<label for="association">
		<g:message code="user.association.label" default="Association" />
		
	</label>
	<g:select id="association" name="association.id" from="${com.nidoapp.nidopub.domain.Association.list()}" optionKey="id" value="${userInstance?.association?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'customer', 'error')} ">
	<label for="customer">
		<g:message code="user.customer.label" default="Customer" />
		
	</label>
	<g:select id="customer" name="customer.id" from="${com.nidoapp.nidopub.domain.Customer.list()}" optionKey="id" value="${userInstance?.customer?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountExpired', 'error')} ">
	<label for="accountExpired">
		<g:message code="user.accountExpired.label" default="Account Expired" />
		
	</label>
	<g:checkBox name="accountExpired" value="${userInstance?.accountExpired}" />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'accountLocked', 'error')} ">
	<label for="accountLocked">
		<g:message code="user.accountLocked.label" default="Account Locked" />
		
	</label>
	<g:checkBox name="accountLocked" value="${userInstance?.accountLocked}" />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'enabled', 'error')} ">
	<label for="enabled">
		<g:message code="user.enabled.label" default="Enabled" />
		
	</label>
	<g:checkBox name="enabled" value="${userInstance?.enabled}" />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'firstName', 'error')} ">
	<label for="firstName">
		<g:message code="user.firstName.label" default="First Name" />
		
	</label>
	<g:textField name="firstName" value="${userInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'lastName', 'error')} ">
	<label for="lastName">
		<g:message code="user.lastName.label" default="Last Name" />
		
	</label>
	<g:textField name="lastName" value="${userInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'passwordExpired', 'error')} ">
	<label for="passwordExpired">
		<g:message code="user.passwordExpired.label" default="Password Expired" />
		
	</label>
	<g:checkBox name="passwordExpired" value="${userInstance?.passwordExpired}" />
</div>

<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'pubs', 'error')} ">
	<label for="pubs">
		<g:message code="user.pubs.label" default="Pubs" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${userInstance?.pubs?}" var="r">
    <li><g:link controller="pub" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="pub" action="create" params="['user.id': userInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'pub.label', default: 'Pub')])}</g:link>
</li>
</ul>

</div>

