<%@ page import="com.nidoapp.nidopub.domain.user.User" %>



<div class="fieldcontain ${hasErrors(bean: userInstance, field: 'photo', 'error')} ">
	<label for="photo">
		<g:message code="user.photo.label" default="Foto" />
		
	</label>
	<div id="userPhoto" style="width:30%; text-align:right;">
	<img src="${g.createLink(controller:'resources', action: 'userPhoto', params:[user_id: userInstance.id])}" alt="" />
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

