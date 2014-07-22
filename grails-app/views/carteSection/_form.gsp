<%@ page import="com.nidoapp.nidopub.domain.CarteSection" %>



<div class="fieldcontain ${hasErrors(bean: carteSectionInstance, field: 'carte', 'error')} required">
	<label for="carte">
		<g:message code="carteSection.carte.label" default="Carta" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="carte" name="carte.id" from="${com.nidoapp.nidopub.domain.Carte.findAllByPub_id(pub.id)}" optionKey="id" required="" noSelection="['' : 'Selecciona una carta']" value="${carteSectionInstance?.carte?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: carteSectionInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="carteSection.name.label" default="Nombre sección" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" value="${carteSectionInstance?.name}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: carteSectionInstance, field: 'orderNumber', 'error')} required">
	<label for="orderNumber">
		<g:message code="carteSection.orderNumber.label" default="Posición Ordenación" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="orderNumber" type="number" value="${carteSectionInstance.orderNumber}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: carteSectionInstance, field: 'products', 'error')} ">
	<label for="products">
		<g:message code="carteSection.products.label" default="Productos" />
		
	</label>
	<g:select name="products" from="${com.nidoapp.nidopub.domain.Product.findAllByPub_id(pub.id)}" multiple="multiple" optionKey="id" size="5" noSelection="['' : 'Sin productos']" value="${carteSectionInstance?.products*.id}" class="many-to-many"/>
</div>

