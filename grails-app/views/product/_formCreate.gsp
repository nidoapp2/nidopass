<%@ page import="com.nidoapp.nidopub.domain.Product" %>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="product.name.label" default="Nombre Producto" />
		
	</label>
	<g:textField name="name" value="${productInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'reference', 'error')} ">
	<label for="reference">
		<g:message code="product.reference.label" default="Referencia" />
		
	</label>
	<g:textField name="reference" value="${productInstance?.reference}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="product.description.label" default="Descripción" />
		
	</label>
	<g:textField name="description" value="${productInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'ingredients', 'error')} ">
	<label for="ingredients">
		<g:message code="product.ingredients.label" default="Ingredientes" />
		
	</label>
	<g:textField name="ingredients" value="${productInstance?.ingredients}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'allergens', 'error')} ">
	<label for="allergens">
		<g:message code="product.allergens.label" default="Alérgenos" />
		
	</label>
	<g:textField name="allergens" value="${productInstance?.allergens}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'youtubeRecipe', 'error')} ">
	<label for="youtubeRecipe">
		<g:message code="product.youtubeRecipe.label" default="Receta de Youtube" />
		
	</label>
	<g:textField name="youtubeRecipe" value="${productInstance?.youtubeRecipe}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'photo', 'error')} ">
	<label for="photo">
		<g:message code="product.photo.label" default="Foto" />
		
	</label>
	<div id="imagenProducto" style="width:30%; text-align:right;">
	<img src="${g.createLink(controller:'resources', action: 'productImage', params:[product_id: productInstance.id])}" alt="" />
	<br>
	<div class="fileupload fileupload-new" data-provides="fileupload">
		<div class="input-append">
			<div class="uneditable-input">
				<i class="icon-file fileupload-exists"></i> 
				<span class="fileupload-preview"></span>
			</div>
			<span class="btn btn-file">
			<span class="fileupload-new">Seleccionar foto</span>
			<span class="fileupload-exists">Cambiar</span>
			<input type="file" class="default" name="photoFile" accept="image/*">
			</span>
			<a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Eliminar</a>
		</div>
	</div>
	</div>
	<!--<g:textField name="photo" value="${productInstance?.photo}"/>-->
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'price', 'error')} ">
	<label for="price">
		<g:message code="product.price.label" default="Precio" />
		
	</label>
	<g:field name="price" value="${fieldValue(bean: productInstance, field: 'price')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: productInstance, field: 'available', 'error')} ">
	<label for="available">
		<g:message code="product.available.label" default="Activo" />
		
	</label>
	<g:checkBox name="available" value="${productInstance?.available}" />
</div>

<!--
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
-->
