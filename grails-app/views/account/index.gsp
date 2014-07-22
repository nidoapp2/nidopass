<%@ page import="com.nidoapp.nidopub.domain.user.Role" %>
<%@ page import="com.nidoapp.nidopub.domain.Association" %>
<!DOCTYPE html>
<head>
	<meta name="layout" content="admin"/>
	<title>NidoPass | Sala</title>
	
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<link href="${resource(dir: 'js/plugins/bootstrap-fileupload', file: 'bootstrap-fileupload.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'js/plugins/chosen-bootstrap/chosen', file: 'chosen.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'css/pages', file: 'profile.css')}" rel="stylesheet" type="text/css"/>
	<!-- END PAGE LEVEL STYLES -->
	
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>

<!-- BEGIN PAGE -->
		<div class="page-content">
			
			<!-- BEGIN PAGE CONTAINER-->
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->
				<div class="row-fluid">
					<div class="span12">
						<!-- BEGIN PAGE TITLE & BREADCRUMB-->
						<h3 class="page-title">
							Mi Perfil <small>datos de usuario</small>
						</h3>
						<ul class="breadcrumb">
							<li>
								<i class="icon-user"></i>
								<a href="#">Mi Perfil</a> 
							</li>							
						</ul>
						<!-- END PAGE TITLE & BREADCRUMB-->
					</div>
				</div>
				<!-- END PAGE HEADER-->
				<!-- BEGIN PAGE CONTENT-->
				<div class="row-fluid profile">
					<div class="span12">
					
						<div class="row-fluid">
										<div class="span12">
											<div class="span3">
												<ul class="ver-inline-menu tabbable margin-bottom-10">
													<li class="active">
														<a data-toggle="tab" href="#tab_1-1">
														<i class="icon-cog"></i> 
														Datos personales
														</a> 
														<span class="after"></span>                                    
													</li>
													<li class=""><a data-toggle="tab" href="#tab_2-2"><i class="icon-picture"></i> Foto</a></li>
													<li class=""><a data-toggle="tab" href="#tab_3-3"><i class="icon-lock"></i> Acceso</a></li>
													<sec:ifAnyGranted roles="${Role.ROLE_ASSOCIATION_OWNER}">
														<li class=""><a data-toggle="tab" href="#tab_4-4"><i class="icon-picture"></i> Logo Asociación</a></li>
													</sec:ifAnyGranted>
												</ul>
											</div>
											<div class="span9">
												<div class="tab-content">
													<!--<div id="tab_1-1" class="tab-pane active">
														<div style="height: auto;" id="accordion1-1" class="accordion collapse">
															<form action="#">
																<label class="control-label">Nombre</label>
																<input type="text" placeholder="Tu nombre de pila, para referirnos a ti" class="m-wrap span8" value="${user.firstName}"/>
																<label class="control-label">Apellidos</label>
																<input type="text" placeholder="Tus apellidos" class="m-wrap span8" value="${user.lastName}"/>															
																<div class="submit-btn">
																	<a href="#" class="btn green">Guardar</a>
																</div>
															</form>
														</div>
													</div>-->
													<div id="tab_1-1" class="tab-pane active">
														<div style="height: auto;" id="accordion1-1" class="accordion collapse">
															<g:form controller="user" method="post" >
																<fieldset class="form">
																	<g:render template="formPersonalInfo"/>
																</fieldset>
																<fieldset class="buttons">
																	<g:actionSubmit class="save btn green" controller="user" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
																</fieldset>
															</g:form>
														</div>
													</div>
													
													
										<!--			<div id="tab_2-2" class="tab-pane">
														<div style="height: auto;" id="accordion2-2" class="accordion collapse">
															<form action="#">
																<p>Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum eiusmod.</p>
																<br />
																<div class="controls">
																	<div class="thumbnail" style="width: 291px; height: 170px;">
																		<img src="http://www.placehold.it/291x170/EFEFEF/AAAAAA&text=no+image" alt="" />
																	</div>
																</div>
																<div class="space10"></div>
																<div class="fileupload fileupload-new" data-provides="fileupload">
																	<div class="input-append">
																		<div class="uneditable-input">
																			<i class="icon-file fileupload-exists"></i> 
																			<span class="fileupload-preview"></span>
																		</div>
																		<span class="btn btn-file">
																		<span class="fileupload-new">Select file</span>
																		<span class="fileupload-exists">Change</span>
																		<input type="file" class="default" />
																		</span>
																		<a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Remove</a>
																	</div>
																</div>
																<div class="clearfix"></div>
																<div class="controls">
																	<span class="label label-important">NOTE!</span>
																	<span>You can write some information here..</span>
																</div>
																<div class="space10"></div>
																<div class="submit-btn">
																	<a href="#" class="btn green">Guardar</a>
																</div>
															</form>
														</div>
													</div>			-->
													
													
													<!--<div id="tab_3-3" class="tab-pane">
														<div style="height: auto;" id="accordion3-3" class="accordion collapse">
															<form action="#">
																<label class="control-label">Tu email (lo usarás para acceder)</label>
																<input type="text" class="m-wrap span8" value="${user.username}"/>
																<label class="control-label">Nueva contraseña</label>
																<input type="password" class="m-wrap span8" />
																<label class="control-label">Repite la nueva contraseña</label>
																<input type="password" class="m-wrap span8" />
																<div class="submit-btn">
																	<a href="#" class="btn green">Guardar</a>
																</div>
															</form>
														</div>
													</div>-->
													
													
													<div id="tab_2-2" class="tab-pane">
														<div style="height: auto;" id="accordion2-2" class="accordion collapse">
															<g:uploadForm controller="user" method="post" >
																<g:hiddenField name="id" value="${userInstance?.id}" />
																<g:hiddenField name="version" value="${userInstance?.version}" />
																<fieldset class="form">
																	<g:render template="formUserPhoto"/>
																</fieldset>
																<fieldset class="buttons">
																	<g:actionSubmit class="save btn green" controller="user" action="updatePhoto" value="${message(code: 'default.button.update.label', default: 'Update')}" />
																</fieldset>
															</g:uploadForm>
														</div>
													</div>
													
													
													<div id="tab_3-3" class="tab-pane">
														<div style="height: auto;" id="accordion3-3" class="accordion collapse">
															<g:form controller="user" method="post" >
																<fieldset class="form">
																	<g:render template="formAccessInfo"/>
																</fieldset>
																<fieldset class="buttons">
																	<g:actionSubmit class="save btn green" controller="user" action="updateAccessInfo" value="${message(code: 'default.button.update.label', default: 'Update')}" />
																</fieldset>
															</g:form>
														</div>
													</div>
													
													<sec:ifAnyGranted roles="${Role.ROLE_ASSOCIATION_OWNER}">
														<div id="tab_4-4" class="tab-pane">
															<div style="height: auto;" id="accordion4-4" class="accordion collapse">
																<g:uploadForm controller="association" method="post" >
																	<g:hiddenField name="id" value="${userInstance?.association.id}" />
																	<g:hiddenField name="version" value="${userInstance?.association.version}" />
																	<fieldset class="form">
																		<g:render template="formAssociationLogo"/>
																	</fieldset>
																	<fieldset class="buttons">
																		<g:actionSubmit class="save btn green" controller="association" action="updateLogo" value="${message(code: 'default.button.update.label', default: 'Update')}" />
																	</fieldset>
																</g:uploadForm>
															</div>
														</div>
													</sec:ifAnyGranted>
													
												</div>
											</div>
											<!--end span9-->                                   
										</div>
									</div>					

					</div>
				</div>
				<!-- END PAGE CONTENT-->
			</div>
			<!-- END PAGE CONTAINER--> 
		</div>
		<!-- END PAGE -->    
		
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="${resource(dir: 'js/plugins/bootstrap-fileupload', file: 'bootstrap-fileupload.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/chosen-bootstrap/chosen', file: 'chosen.jquery.min.js')}" type="text/javascript" ></script>
	
	<script src="http://maps.google.com/maps/api/js?sensor=false" type="text/javascript"></script>
	<!-- <script src="${resource(dir: 'js/plugins/gmaps', file: 'gmaps.js')}" type="text/javascript" ></script>  -->
	
	
	<!-- END PAGE LEVEL PLUGINS -->
	
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${resource(dir: 'js', file: 'app.js')}" type="text/javascript" ></script>
	
	<!-- <script src="${resource(dir: 'js', file: 'maps-google.js')}" type="text/javascript" ></script>  -->
	<!-- END PAGE LEVEL SCRIPTS --> 


	
	

	
	 
	<script>
	jQuery(document).ready(function() {       
		   
		   $("#menu-configuration").addClass("active");
		   $("#menu-configuration-user").addClass("active");

			// initiate layout and plugins
		   App.init();
		});
	</script>

</body>
<!-- END BODY -->
</html>
<!-- Localized -->