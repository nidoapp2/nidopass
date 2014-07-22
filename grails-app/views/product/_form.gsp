<%@ page import="com.nidoapp.nidopub.domain.Product" %>



<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="product.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${productInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'photo', 'error')} ">
	<label for="photo">
		<g:message code="product.photo.label" default="Photo" />
		
	</label>
	<g:textField name="photo" value="${productInstance?.photo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'price', 'error')} ">
	<label for="price">
		<g:message code="product.price.label" default="Price" />
		
	</label>
	<g:field name="price" value="${fieldValue(bean: productInstance, field: 'price')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'available', 'error')} ">
	<label for="available">
		<g:message code="product.available.label" default="Available" />
		
	</label>
	<g:checkBox name="available" value="${productInstance?.available}" />
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'carteSections', 'error')} ">
	<label for="carteSections">
		<g:message code="product.carteSections.label" default="Carte Sections" />
		
	</label>
	
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="product.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${productInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'pub_id', 'error')} required">
	<label for="pub_id">
		<g:message code="product.pub_id.label" default="Pubid" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="pub_id" type="number" value="${productInstance.pub_id}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'reviews', 'error')} ">
	<label for="reviews">
		<g:message code="product.reviews.label" default="Reviews" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${productInstance?.reviews?}" var="r">
    <li><g:link controller="customerReview" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="customerReview" action="create" params="['product.id': productInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'customerReview.label', default: 'CustomerReview')])}</g:link>
</li>
</ul>

</div>

