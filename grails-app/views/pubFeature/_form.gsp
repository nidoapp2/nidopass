<%@ page import="com.nidoapp.nidopub.domain.PubFeature" %>



<div class="fieldcontain ${hasErrors(bean: pubFeatureInstance, field: 'code', 'error')} ">
	<label for="code">
		<g:message code="pubFeature.code.label" default="Code" />
		
	</label>
	<g:textField name="code" value="${pubFeatureInstance?.code}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubFeatureInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="pubFeature.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${pubFeatureInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubFeatureInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="pubFeature.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${pubFeatureInstance?.name}"/>
</div>
<!--
<div class="fieldcontain ${hasErrors(bean: pubFeatureInstance, field: 'repositoryImageId', 'error')} ">
	<label for="repositoryImageId">
		<g:message code="pubFeature.repositoryImageId.label" default="Repository Image Id" />
		
	</label>
	<g:textField name="repositoryImageId" value="${pubFeatureInstance?.repositoryImageId}"/>
</div>
-->
<div class="fieldcontain ${hasErrors(bean: pubFeatureInstance, field: 'repositoryImageId', 'error')} ">
	<label for="repositoryImageId">
		<g:message code="pubFeature.repositoryImageId.label" default="Repository Image Id" />
		
	</label>
	<div id="imagenCaracteristica" style="width:30%; text-align:right;">
	<img src="${g.createLink(controller:'resources', action: 'featureImage', params:[feature_id: pubFeatureInstance.id])}" alt="" />
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

