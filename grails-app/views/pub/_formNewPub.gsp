<%@page import="com.nidoapp.nidopub.domain.user.Role"%>
<%@page import="com.nidoapp.nidopub.domain.user.UserRole"%>
<%@page import="com.nidoapp.nidopub.domain.geo.Province"%>
<%@page import="com.nidoapp.nidopub.domain.geo.City"%>
<%@ page import="com.nidoapp.nidopub.domain.Pub" %>

<div class="fieldcontain ${hasErrors(bean: pubInstance, field: 'owner', 'error')} required">
	<label for="owner">
		<g:message code="pub.owner.label" default="Propietario" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="owner" name="owner.id" from="${UserRole.findAllByRole(Role.findByAuthority(Role.ROLE_PUB_OWNER), [sort: "user", order: "asc"]).user}" optionKey="id" required="" noSelection="['' : 'Selecciona un propietario']" value="${pubInstance?.owner?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="pub.name.label" default="Nombre" />
		<span class="required-indicator">*</span>
		
	</label>
	<g:textField name="name" required="" value="${pubInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="pub.description.label" default="Descripción" />
		<span class="required-indicator">*</span>
		
	</label>
	<g:textField name="description" required="" value="${pubInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubInstance, field: 'address', 'error')} required">
	<label for="address">
		<g:message code="pub.address.label" default="Dirección" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="address" required="" value="${pubInstance?.address}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubInstance, field: 'province', 'error')} required">
	<label for="province">
		<g:message code="pub.province.label" default="Provincia" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="province" name="province.id" from="${Province.list()}" optionKey="id" required="" 
			noSelection="['' : 'Selecciona una provincia']" 
			onchange="${remoteFunction (
				controller:'city',
				action:'getCities',
				params:'\'id=\' + this.value',
				update: 'estadoDiv'
				)}"
			value="${pubInstance?.province?.id}" class="many-to-one"/>
</div>

<div id="estadoDiv">
	<g:if test="${pubInstance?.city}">
		<g:include controller="city" action="getCities" id="${pubInstance.province.id}" />
	</g:if>
</div>

<div class="fieldcontain ${hasErrors(bean: pubInstance, field: 'telephone', 'error')} required">
	<label for="telephone">
		<g:message code="pub.telephone.label" default="Teléfono" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="telephone" required="" value="${pubInstance?.telephone}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubInstance, field: 'fax', 'error')}">
	<label for="fax">
		<g:message code="pub.fax.label" default="Fax" />
	</label>
	<g:textField name="fax" value="${pubInstance?.fax}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="pub.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="email" required="" value="${pubInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubInstance, field: 'website', 'error')}">
	<label for="website">
		<g:message code="pub.website.label" default="Página Web" />
	</label>
	<g:textField name="website" value="${pubInstance?.website}"/>
</div>

