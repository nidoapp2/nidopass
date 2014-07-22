<%@ page import="com.nidoapp.nidopub.domain.AssociationNews" %>



<div class="fieldcontain ${hasErrors(bean: associationNewsInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="associationNews.title.label" default="Título" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" value="${associationNewsInstance?.title}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: associationNewsInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="associationNews.description.label" default="Noticia" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="description" value="${associationNewsInstance?.description}" required=""/>
</div>

<!--<div class="fieldcontain ${hasErrors(bean: associationNewsInstance, field: 'photo', 'error')} ">
	<label for="photo">
		<g:message code="associationNews.photo.label" default="Photo" />
		
	</label>
	<g:textField name="photo" value="${associationNewsInstance?.photo}"/>
</div>-->

<div class="fieldcontain ${hasErrors(bean: associationNewsInstance, field: 'photo', 'error')} ">
	<label for="photo">
		<g:message code="associationNews.photo.label" default="Foto" />
		
	</label>
	<div id="imagenNoticia" style="width:30%; text-align:right;">
	<img src="${g.createLink(controller:'resources', action: 'associationNewsImage', params:[associationNews_id: associationNewsInstance.id])}" alt="" />
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
</div>

<div class="fieldcontain ${hasErrors(bean: associationNewsInstance, field: 'url', 'error')} ">
	<label for="url">
		<g:message code="associationNews.url.label" default="Dirección Web Noticia" />
		
	</label>
	<g:textField name="url" value="${associationNewsInstance?.url}"/>
</div>

<!--<div class="fieldcontain ${hasErrors(bean: associationNewsInstance, field: 'association', 'error')} required">
	<label for="association">
		<g:message code="associationNews.association.label" default="Association" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="association" name="association.id" from="${com.nidoapp.nidopub.domain.Association.list()}" optionKey="id" required="" value="${associationNewsInstance?.association?.id}" class="many-to-one"/>
</div>-->

