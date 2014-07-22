<%@page import="com.nidoapp.nidopub.domain.PubMusicType"%>
<%@page import="java.lang.Math"%>
<%@ page import="com.nidoapp.nidopub.domain.PubEvent" %>
<calendar:resources lang="es" theme="tiger"/>


	<g:if test="${pubEventInstance?.dateEvent && !currentDateEvent}">
	
		<g:set var="actualDateEvent" value="${pubEventInstance?.dateEvent}" />
	
	</g:if>
	<g:elseif test="${currentDateEvent }">
	
		<g:set var="actualDateEvent" value="${currentDateEvent}" />
	</g:elseif>
	<g:else>
	
		<g:set var="actualDateEvent" value="${null}" />
	</g:else>

<script>

	var url = '${createLink(controller: 'pubEvent', action:'setDateEventRangeList')}';
	
	function cambiarPagina(param1) {

		<!-- DESARROLLO -->
		/*if (typeof(param1)=='string') {
			$('#table_pubs_events').load('/nidopub-webapp/pubEvent/listEventsDate', {id_pub:'${pubEventInstance.pub.uri}', currentDateEvent: param1});
		} else {
			$('#table_pubs_events').load('/nidopub-webapp/pubEvent/listEventsDate', {id_pub:'${pubEventInstance.pub.uri}', currentDateEvent: $(param1).val()});
		}*/
		<!-- FIN DESARROLLO -->
		
		<!-- TEST Y PROD -->
		if (typeof(param1)=='string') {
			$('#table_pubs_events').load('/pubEvent/listEventsDate', {id_pub:'${pubEventInstance.pub.uri}', currentDateEvent: param1});
		} else {
			$('#table_pubs_events').load('/pubEvent/listEventsDate', {id_pub:'${pubEventInstance.pub.uri}', currentDateEvent: $(param1).val()});
		}
		<!-- FIN TEST Y PROD -->
		
	}
	
	$(document).ready(function(){
		var date = document.getElementById('dateEventDate_day').value + '/' +document.getElementById('dateEventDate_month').value + '/' + document.getElementById('dateEventDate_year').value + ' - 00:00';
		cambiarPagina(date);
		});
	
</script>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'title', 'error')} required">
	<label for="title">
		<g:message code="pubEvent.title.label" default="Título" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="title" value="${pubEventInstance?.title}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="pubEvent.description.label" default="Descripción" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="description" value="${pubEventInstance?.description}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'musicType', 'error')} required">
	<label for="musicType">
		<g:message code="pubEvent.musicType.label" default="Tipo de música" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="musicType" name="musicType.id" from="${PubMusicType.findAll()}" optionKey="id" required="" noSelection="['' : 'Selecciona un tipo de música']" value="${pubEventInstance?.musicType?.id}" class="one-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'musicSubType', 'error')} ">
	<label for="musicSubType">
		<g:message code="pubEvent.musicSubType.label" default="Subtipo de música" />
		
	</label>
	<g:textField name="musicSubType" value="${pubEventInstance?.musicSubType}" />
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'artist', 'error')}">
	<label for="artist">
		<g:message code="pubEvent.artist.label" default="Artista" />
		
	</label>
	<g:textField name="artist" value="${pubEventInstance?.artist}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'dateEvent', 'error')} required">
	<label for="dateEvent">
		<g:message code="pubEvent.dateEvent.label" default="Fecha y hora evento" />
		<span class="required-indicator">*</span>
	</label>
	<calendar:datePicker id="dateEventDate" dateFormat="%d/%m/%Y" showTime="false" name="dateEventDate" value="${new Date(actualDateEvent.getYear(), actualDateEvent.getMonth(), actualDateEvent.getDate())}" onChange="cambiarPagina(this);" required="" />
	<br/>
	<g:textField name="dateEventHour" value="${String.format( '%02d', actualDateEvent.getHours())}" style="width:89px !important;" required=""/>
	&nbsp;:&nbsp; 
	<g:textField name="dateEventMinute" value="${String.format( '%02d', actualDateEvent.getMinutes())}" style="width:88px !important;" required=""/>
</div>

<div class="portlet-body" id="table_pubs_events"></div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'acceptsMinors', 'error')} ">
	<label for="acceptsMinors">
		<g:message code="pubEvent.acceptsMinors.label" default="Acepta menores de edad" />
		
	</label>
	<g:checkBox name="acceptsMinors" value="${false }" checked="${pubEventInstance.acceptsMinors}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'ticketsOnlineSaleActive', 'error')} ">
	<label for="ticketsOnlineSaleActive">
		<g:message code="pubEvent.ticketsOnlineSaleActive.label" default="Venta de entradas online activa" />
		
	</label>
	<g:checkBox name="ticketsOnlineSaleActive" value="${false }" checked="${pubEventInstance.ticketsOnlineSaleActive}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'price', 'error')} required">
	<label for="price">
		<g:message code="pubEvent.price.label" default="Precio entrada (Sin comisión)" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="price" value="${fieldValue(bean: pubEventInstance, field: 'price')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'commission', 'error')} required">
	<label for="commission">
		<g:message code="pubEvent.commission.label" default="Comisión entrada" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="commission" value="${fieldValue(bean: pubEventInstance, field: 'commission')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'totalTickets', 'error')} required">
	<label for="totalTickets">
		<g:message code="pubEvent.totalTickets.label" default="Total entradas" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="totalTickets" value="${fieldValue(bean: pubEventInstance, field: 'totalTickets')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'totalTicketsOnlineSale', 'error')} required">
	<label for="totalTicketsOnlineSale">
		<g:message code="pubEvent.totalTicketsOnlineSale.label" default="Total entradas venta online" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="totalTicketsOnlineSale" value="${fieldValue(bean: pubEventInstance, field: 'totalTicketsOnlineSale')}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'privateEvent', 'error')} ">
	<label for="privateEvent">
		<g:message code="pubEvent.privateEvent.label" default="Evento privado" />
		
	</label>
	<g:checkBox name="privateEvent" value="${false }" checked="${pubEventInstance.privateEvent}"/>
	<span style="color:red">(<b>Importante: </b>Si selecciona este campo, el evento no se mostrará en la web hasta que sea desmarcado)</span>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'url', 'error')} ">
	<label for="url">
		<g:message code="pubEvent.url.label" default="Dirección web evento sala" />
		
	</label>
	<g:textField name="url" value="${pubEventInstance?.url}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: pubEventInstance, field: 'photo', 'error')} ">
	<label for="photo">
		<g:message code="pubEvent.photo.label" default="Foto" />
		
	</label>
	<div id="imagenEvento" style="width:30%; text-align:right;">
	<img src="${g.createLink(controller:'resources', action: 'pubEventImage', params:[event_id: pubEventInstance.id])}" alt="" />
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

