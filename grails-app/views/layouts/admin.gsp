<%@ page import="com.nidoapp.nidopub.domain.user.Role" %>
<%@ page import="com.nidoapp.nidopub.domain.geo.Province" %>
<%@ page import="com.nidoapp.nidopub.domain.Pub" %>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" >
		<meta charset="utf-8" />
		<meta content="width=device-width, initial-scale=1.0" name="viewport" />
		<meta content="" name="description" />
		<meta content="" name="author" />
		
		<title><g:layoutTitle default="NidoPass | Admin"/></title>
		
		<!-- BEGIN GLOBAL MANDATORY STYLES -->
		<link href="${resource(dir: 'js/plugins/bootstrap/css', file: 'bootstrap.min.css')}" rel="stylesheet" type="text/css"/>
		<link href="${resource(dir: 'js/plugins/bootstrap/css', file: 'bootstrap-responsive.min.css')}" rel="stylesheet" type="text/css"/>
		<link href="${resource(dir: 'js/plugins/font-awesome/css', file: 'font-awesome.min.css')}" rel="stylesheet" type="text/css"/>
		<link href="${resource(dir: 'css', file: 'style-metro.css')}" rel="stylesheet" type="text/css"/>
		<link href="${resource(dir: 'css', file: 'style.css')}" rel="stylesheet" type="text/css"/>
		<link href="${resource(dir: 'css', file: 'style-responsive.css')}" rel="stylesheet" type="text/css"/>
		<link href="${resource(dir: 'css/themes', file: 'default.css')}" rel="stylesheet" type="text/css" id="style_color"/>
		<link href="${resource(dir: 'js/plugins/uniform/css', file: 'uniform.default.css')}" rel="stylesheet" type="text/css"/>
		<!-- END GLOBAL MANDATORY STYLES -->
		
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		
		
		<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<!-- BEGIN CORE PLUGINS -->
	<script src="${resource(dir: 'js/plugins', file: 'jquery-1.10.1.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir: 'js/plugins', file: 'jquery-migrate-1.2.1.min.js')}" type="text/javascript"></script>
	<!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	<script src="${resource(dir: 'js/plugins/jquery-ui', file: 'jquery-ui-1.10.1.custom.min.js')}" type="text/javascript"></script>      
	<script src="${resource(dir: 'js/plugins/bootstrap/js', file: 'bootstrap.min.js')}" type="text/javascript"></script>
	<!--[if lt IE 9]>
	<script src="${resource(dir: 'js/plugins', file: 'excanvas.min.js')}"></script>
	<script src="${resource(dir: 'js/plugins', file: 'respond.min.js')}"></script>  
	<![endif]-->   
	<script src="${resource(dir: 'js/plugins/jquery-slimscroll', file: 'jquery.slimscroll.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir: 'js/plugins', file: 'jquery.blockui.min.js')}" type="text/javascript"></script>  
	<script src="${resource(dir: 'js/plugins', file: 'jquery.cookie.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir: 'js/plugins/uniform', file: 'jquery.uniform.min.js')}" type="text/javascript" ></script>
	<!-- END CORE PLUGINS -->
	
	
	
	
	<!-- END JAVASCRIPTS -->

		<g:layoutHead/>
		<r:layoutResources />
	</head>
	
	<body class="page-header-fixed">
	<!-- BEGIN HEADER -->
	<div class="header navbar navbar-inverse navbar-fixed-top">
		<!-- BEGIN TOP NAVIGATION BAR -->
		<div class="navbar-inner">
			<div class="container-fluid">
				<!-- BEGIN LOGO -->
				<a class="brand" href="${createLink(controller:'home')}">
				<img src="${resource(dir: 'images', file: 'logo.png')}" alt="logo"  style="max-height:30px"/>
				</a>
				<!-- END LOGO -->
				<!-- BEGIN RESPONSIVE MENU TOGGLER -->
				<a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">
				<!--<img src="${resource(dir: 'images', file: 'menu-toggler.png')}" alt="" />-->
				Menú <i class="icon-angle-down"></i>
				</a>          
				<!-- END RESPONSIVE MENU TOGGLER -->            
				<!-- BEGIN TOP NAVIGATION MENU -->              
				<ul class="nav pull-right">
					<!-- BEGIN NOTIFICATION DROPDOWN -->   
	<!--				<li class="dropdown" id="header_notification_bar">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="icon-warning-sign"></i>
						<span class="badge">6</span>
						</a>
						<ul class="dropdown-menu extended notification">
							<li>
								<p>You have 14 new notifications</p>
							</li>
							<li>
								<a href="#">
								<span class="label label-success"><i class="icon-plus"></i></span>
								New user registered. 
								<span class="time">Just now</span>
								</a>
							</li>
							<li>
								<a href="#">
								<span class="label label-important"><i class="icon-bolt"></i></span>
								Server #12 overloaded. 
								<span class="time">15 mins</span>
								</a>
							</li>
							<li>
								<a href="#">
								<span class="label label-warning"><i class="icon-bell"></i></span>
								Server #2 not respoding.
								<span class="time">22 mins</span>
								</a>
							</li>
							<li>
								<a href="#">
								<span class="label label-info"><i class="icon-bullhorn"></i></span>
								Application error.
								<span class="time">40 mins</span>
								</a>
							</li>
							<li>
								<a href="#">
								<span class="label label-important"><i class="icon-bolt"></i></span>
								Database overloaded 68%. 
								<span class="time">2 hrs</span>
								</a>
							</li>
							<li>
								<a href="#">
								<span class="label label-important"><i class="icon-bolt"></i></span>
								2 user IP blocked.
								<span class="time">5 hrs</span>
								</a>
							</li>
							<li class="external">
								<a href="#">See all notifications <i class="m-icon-swapright"></i></a>
							</li>
						</ul>
					</li>			-->
					<!-- END NOTIFICATION DROPDOWN -->
					<!-- BEGIN INBOX DROPDOWN -->
	<!--				<li class="dropdown" id="header_inbox_bar">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="icon-envelope"></i>
						<span class="badge">5</span>
						</a>
						<ul class="dropdown-menu extended inbox">
							<li>
								<p>You have 12 new messages</p>
							</li>
							<li>
								<a href="inbox-a=view.html">
								<span class="photo"><img src="${resource(dir: 'images', file: 'avatar2.jpg')}" alt="" /></span>
								<span class="subject">
								<span class="from">Lisa Wong</span>
								<span class="time">Just Now</span>
								</span>
								<span class="message">
								Vivamus sed auctor nibh congue nibh. auctor nibh
								auctor nibh...
								</span>  
								</a>
							</li>
							<li>
								<a href="inbox-a=view.html">
								<span class="photo"><img src="${resource(dir: 'images', file: 'avatar3.jpg')}" alt="" /></span>
								<span class="subject">
								<span class="from">Richard Doe</span>
								<span class="time">16 mins</span>
								</span>
								<span class="message">
								Vivamus sed congue nibh auctor nibh congue nibh. auctor nibh
								auctor nibh...
								</span>  
								</a>
							</li>
							<li>
								<a href="inbox-a=view.html">
								<span class="photo"><img src="${resource(dir: 'images', file: 'avatar1.jpg')}" alt="" /></span>
								<span class="subject">
								<span class="from">Bob Nilson</span>
								<span class="time">2 hrs</span>
								</span>
								<span class="message">
								Vivamus sed nibh auctor nibh congue nibh. auctor nibh
								auctor nibh...
								</span>  
								</a>
							</li>
							<li class="external">
								<a href="inbox.html">See all messages <i class="m-icon-swapright"></i></a>
							</li>
						</ul>
					</li>			-->
					<!-- END INBOX DROPDOWN -->
					<!-- BEGIN TODO DROPDOWN -->
	<!--				<li class="dropdown" id="header_task_bar">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<i class="icon-tasks"></i>
						<span class="badge">5</span>
						</a>
						<ul class="dropdown-menu extended tasks">
							<li>
								<p>You have 12 pending tasks</p>
							</li>
							<li>
								<a href="#">
								<span class="task">
								<span class="desc">New release v1.2</span>
								<span class="percent">30%</span>
								</span>
								<span class="progress progress-success ">
								<span style="width: 30%;" class="bar"></span>
								</span>
								</a>
							</li>
							<li>
								<a href="#">
								<span class="task">
								<span class="desc">Application deployment</span>
								<span class="percent">65%</span>
								</span>
								<span class="progress progress-danger progress-striped active">
								<span style="width: 65%;" class="bar"></span>
								</span>
								</a>
							</li>
							<li>
								<a href="#">
								<span class="task">
								<span class="desc">Mobile app release</span>
								<span class="percent">98%</span>
								</span>
								<span class="progress progress-success">
								<span style="width: 98%;" class="bar"></span>
								</span>
								</a>
							</li>
							<li>
								<a href="#">
								<span class="task">
								<span class="desc">Database migration</span>
								<span class="percent">10%</span>
								</span>
								<span class="progress progress-warning progress-striped">
								<span style="width: 10%;" class="bar"></span>
								</span>
								</a>
							</li>
							<li>
								<a href="#">
								<span class="task">
								<span class="desc">Web server upgrade</span>
								<span class="percent">58%</span>
								</span>
								<span class="progress progress-info">
								<span style="width: 58%;" class="bar"></span>
								</span>
								</a>
							</li>
							<li>
								<a href="#">
								<span class="task">
								<span class="desc">Mobile development</span>
								<span class="percent">85%</span>
								</span>
								<span class="progress progress-success">
								<span style="width: 85%;" class="bar"></span>
								</span>
								</a>
							</li>
							<li class="external">
								<a href="#">See all tasks <i class="m-icon-swapright"></i></a>
							</li>
						</ul>
					</li>			-->
					<!-- END TODO DROPDOWN -->
					<!-- BEGIN USER LOGIN DROPDOWN -->
					<li class="dropdown user">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<g:if test="${user?.photo }">
							<img alt="" style="height:25px !important;" src="${g.createLink(controller:'resources', action: 'userPhoto', params:[user_id: user?.id])}" />
						</g:if>
						<g:else>
							<img alt="" style="height:25px !important;" src="${g.createLink(controller:'resources', action: 'userPhoto', params:[user_id: 1])}" />
						</g:else>
						<span class="username">${user?.firstName}</span>
						<i class="icon-angle-down"></i>
						</a>
						<ul class="dropdown-menu">
							<li><a href="${createLink(controller:'account')}"><i class="icon-user"></i> Mi Perfil</a></li>
	<!--						<li><a href="#"><i class="icon-th-large"></i> Mis Pubs</a></li>
							<li><a href="#"><i class="icon-envelope"></i> Mis Mensajes</a></li>			-->
							<li class="divider"></li>
	<!--						<li><a href="#"><i class="icon-lock"></i> Bloquear pantalla</a></li>			-->
							<li><a href="${createLink(controller:'logout')}"><i class="icon-key"></i> Salir</a></li>
						</ul>
					</li>
					<!-- END USER LOGIN DROPDOWN -->
				</ul>
				<!-- END TOP NAVIGATION MENU --> 
			</div>
		</div>
		<!-- END TOP NAVIGATION BAR -->
	</div>
	<!-- END HEADER -->
	<!-- BEGIN CONTAINER -->
	<div class="page-container">
		<!-- BEGIN SIDEBAR -->
		<div class="page-sidebar nav-collapse collapse">
			<!-- BEGIN SIDEBAR MENU -->        
			<ul class="page-sidebar-menu">
				<li>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
					<div class="sidebar-toggler hidden-phone">Menú</div>
					<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
				</li>
				<li>
					<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
	<!--				<form class="sidebar-search">
						<div class="input-box">
							<a href="javascript:;" class="remove"></a>
							<input type="text" placeholder="Search..." />
							<input type="button" class="submit" value=" " />
						</div>
					</form>			-->
					<!-- END RESPONSIVE QUICK SEARCH FORM -->
				</li>
	<!--			<li id="menu-home" class="start">
					<a href="${createLink(controller:'home')}">
					<i class="icon-dashboard"></i> 
					<span class="title">Dashboard</span>
					<span class="selected"></span>
					</a>
				</li>		-->
				
				<!-- Cocina -->
	<!--			<sec:ifAllGranted roles="${Role.ROLE_PUB_OWNER}">
					<li id="menu-kitchen" class="">
						<a href="${createLink(controller:'kitchen')}">
						<i class="icon-unchecked"></i> 
						<span class="title">Cocina</span>
						<span class="selected"></span>
						</a>
					</li>
				</sec:ifAllGranted>			-->
				
				<!-- Servicio -->
	<!--			<sec:ifAllGranted roles="${Role.ROLE_PUB_OWNER}">
					<li id="menu-waiters" class="">
						<a href="${createLink(controller:'waiters')}">
						<i class="icon-glass"></i> 
						<span class="title">Servicio</span>
						<span class="selected"></span>
						</a>
					</li>
				</sec:ifAllGranted>			-->
				
				<!-- Mesas -->
	<!--			<sec:ifAllGranted roles="${Role.ROLE_PUB_OWNER}">
					<li id="menu-tables" class="">
						<a href="#">
						<i class="icon-th"></i> 
						<span class="title">Mesas</span>
						<span class="selected"></span>
						</a>
					</li>
				</sec:ifAllGranted>			-->			
				
				<!-- Configuracion -->
				<sec:ifAnyGranted roles="${Role.ROLE_PUB_OWNER},${Role.ROLE_ASSOCIATION_OWNER},${Role.ROLE_SUPERADMIN}">
					<li id="menu-configuration" class="">
						<a href="javascript:;">
						<i class="icon-cogs"></i> 
						<span class="title">Configuración</span>
						<span class="arrow "></span>
						</a>				
						<ul class="sub-menu">
							<sec:ifAnyGranted roles="${Role.ROLE_SUPERADMIN}">
								<li id="menu-configuration-users">
									<a href="javascript:;">
									Usuarios
									<span class="arrow"></span>
									</a>
									<ul class="sub-menu">
											<li id="menu-new-user">
												<a href="${createLink(controller:'user', action:'create')}">Crear Nuevo Usuario</a>
											</li>
									</ul>
								</li>
							</sec:ifAnyGranted>
							<sec:ifAnyGranted roles="${Role.ROLE_SUPERADMIN}">
							<li id="menu-configuration-associations">
								<a href="javascript:;">
								Asociaciones
								<span class="arrow"></span>
								</a>
								<ul class="sub-menu">
									<sec:ifAnyGranted roles="${Role.ROLE_SUPERADMIN}">
										<li id="menu-new-association">
											<a href="${createLink(controller:'association', action:'create')}">Crear Nueva Associación</a>
										</li>
									</sec:ifAnyGranted>
									<sec:ifAnyGranted roles="${Role.ROLE_SUPERADMIN}">
										<g:each in="${Province.findAll([sort:"name", order:"asc"])}">
											<li id="menu-configuration-associations-province-${it.name}">
												<a href="${createLink(controller:'association', action:'listProvinceAssociations', params:['id_province': it.id])}">${it.name}</a>
											</li>
										</g:each>
									</sec:ifAnyGranted>
								</ul>
							</li>
							</sec:ifAnyGranted>
							<li id="menu-configuration-pubs">
								<a href="javascript:;">
								Salas
								<span class="arrow"></span>
								</a>
								<ul class="sub-menu">
									<sec:ifAnyGranted roles="${Role.ROLE_SUPERADMIN}">
										<li id="menu-new-pub">
											<a href="${createLink(controller:'pub', action:'newPub')}">Crear Nueva Sala</a>
										</li>
									</sec:ifAnyGranted>
									<sec:ifAnyGranted roles="${Role.ROLE_ASSOCIATION_OWNER}">
										<li id="menu-new-association-pub">
											<a href="${createLink(controller:'pub', action:'newAssociationPub')}">Crear Nueva Sala</a>
										</li>
										<li id="menu-list-pubs">
											<a href="${createLink(controller:'pub', action:'pubsAssociation')}">Administ. Salas</a>
										</li>
									</sec:ifAnyGranted>
									<sec:ifAnyGranted roles="${Role.ROLE_SUPERADMIN}">
										<!--<li id="menu-services-admin" class="">
											<a href="javascript:;">
											<span class="title">Administ. Servicios</span>
											<span class="arrow"></span>
											</a>
											<ul class="sub-menu">
												<g:each in="${Province.findAll([sort:"name", order:"asc"])}">
													<li id="menu-services-admin-${it.name}">
														<a href="${createLink(controller:'pub', action:'listProvincePubs', params:['id_province': it.id])}">${it.name}</a>
													</li>
												</g:each>
											</ul>
										</li>-->
									</sec:ifAnyGranted>
									
									<sec:ifAnyGranted roles="${Role.ROLE_PUB_OWNER}">
										<g:each in="${user?.pubs}">
											<li id="menu-configuration-pub-${it.uri}">
												<a href="${createLink(controller:'pub', params:['id': it.uri])}">${it.name}</a>
											</li>
										</g:each>
									</sec:ifAnyGranted>
									<sec:ifAnyGranted roles="${Role.ROLE_SUPERADMIN}">
										<g:each in="${Province.findAll([sort:"name", order:"asc"])}">
											<li id="menu-configuration-province-${it.name}">
												<a href="${createLink(controller:'pub', action:'listProvincePubs', params:['id_province': it.id])}">${it.name}</a>
											</li>
										</g:each>
									</sec:ifAnyGranted>
								</ul>
							</li>	
							
							
							
							<sec:ifAnyGranted roles="${Role.ROLE_SUPERADMIN}">
										<li id="menu-configuration-inquiries-admin" class="">
											<a href="${createLink(controller:'user', action:'inquiriesAdmin')}">Administ. Encuestas</a>
										</li>
									</sec:ifAnyGranted>

							<li id="menu-configuration-user" class="">
								<a href="${createLink(controller:'account')}">
								Mi Perfil</a>
							</li>							
						</ul>
					</li>
				</sec:ifAnyGranted>
				
				
				
				<!-- Cartas y Productos -->
				<!--<sec:ifAllGranted roles="${Role.ROLE_PUB_OWNER}">
					<g:if test="${Pub.findByOwnerAndCartesAvailable(user, true) }">
						<li id="menu-cartesAndProducts" class="">
							<a href="javascript:;">
							<i class="icon-file-text"></i> 
							<span class="title">Cartas y Productos</span>
							<span class="arrow"></span>
							</a>
							<ul class="sub-menu">
								<g:each in="${user?.pubs}">
									<li id="menu-cartesAndProducts-pub-${it.uri}">
										<a href="${createLink(controller:'cartesAndProducts', params:['id': it.uri])}">${it.name}</a>
									</li>
								</g:each>
							</ul>
						</li>
					</g:if>
				</sec:ifAllGranted>-->
				
				<!-- Encuesta -->
				<!--<sec:ifAllGranted roles="${Role.ROLE_PUB_OWNER}">
					<g:if test="${Pub.findByOwnerAndInquiryAvailable(user, true) }">
						<li id="menu-inquiry" class="">
							<a href="javascript:;">
							<i class="icon-file-text"></i> 
							<span class="title">Encuesta</span>
							<span class="arrow"></span>
							</a>
							<ul class="sub-menu">
								<g:each in="${user?.pubs}">
									<li id="menu-inquiry-pub-${it.uri}">
										<g:if test="${it.inquiryAvailable }">
											<a href="${createLink(controller:'pub', action:'pubInquiry', params:['id': it.uri])}">${it.name}</a>
										</g:if>
										<g:else>
											<a href="${createLink(controller:'pub', action:'pubInquiryDenied', params:['id': it.uri])}">${it.name}</a>
										</g:else>
									</li>
								</g:each>
							</ul>
						</li>
					</g:if>
				</sec:ifAllGranted>-->
				
				<!-- Eventos -->
				<!--<sec:ifAllGranted roles="${Role.ROLE_PUB_OWNER}">
					<g:if test="${Pub.findByOwnerAndEventsAvailable(user, true) }">
						<li id="menu-events" class="">
							<a href="javascript:;">
							<i class="icon-file-text"></i> 
							<span class="title">Eventos</span>
							<span class="arrow"></span>
							</a>
							<ul class="sub-menu">
								<g:each in="${user?.pubs}">
									<li id="menu-events-pub-${it.uri}">
										<a href="${createLink(controller:'pubEvent', action:'list', params:['id_pub': it.uri])}">${it.name}</a>
									</li>
								</g:each>
							</ul>
						</li>
					</g:if>
				</sec:ifAllGranted>	-->
				
				
				<!-- EMails -->
				<!--<sec:ifAllGranted roles="${Role.ROLE_PUB_OWNER}">
					<g:if test="${Pub.findByOwnerAndEventsAvailable(user, true) }">
						<li id="menu-emails" class="">
							<a href="javascript:;">
							<i class="icon-file-text"></i> 
							<span class="title">EMails</span>
							<span class="arrow"></span>
							</a>
							<ul class="sub-menu">
								<g:each in="${user?.pubs}">
									<li id="menu-emails-pub-${it.uri}">
										<a href="${createLink(controller:'EMailsAdminAccess', params:['id': it.uri])}">${it.name}</a>
									</li>
								</g:each>
							</ul>
						</li>
					</g:if>
				</sec:ifAllGranted>	-->		
				
				
				<!-- Noticias -->
				<sec:ifAllGranted roles="${Role.ROLE_ASSOCIATION_OWNER}">
					<li id="menu-news" class="">
						<a href="${createLink(controller:'associationNews', action:'list')}">
						<i class="icon-file-text"></i> 
						<span class="title">Noticias</span>
						</a>
					</li>
				</sec:ifAllGranted>
				
				<!-- Recomendaciones -->
	<!--			<sec:ifAnyGranted roles="${Role.ROLE_PUB_OWNER},${Role.ROLE_ASSOCIATION_OWNER},${Role.ROLE_SUPERADMIN},${Role.ROLE_CUSTOMER}">	
		
					<li class="">
						<a href="#">
						<i class="icon-thumbs-up"></i> 
						<span class="title">Recomendaciones</span>
						<span class="selected"></span>
						</a>
					</li>
				</sec:ifAnyGranted>			-->
				
				<!-- Reservas -->
	<!--			<sec:ifAnyGranted roles="${Role.ROLE_PUB_OWNER},${Role.ROLE_CUSTOMER}">
					<li id="menu-booking" class="">
						<a href="${createLink(controller:'booking')}">
						<i class="icon-calendar"></i> 
						<span class="title">Reservas</span>
						<span class="selected"></span>
						</a>
					</li>
				</sec:ifAnyGranted>		-->
				
				<!-- Puntos -->
				<!--<sec:ifAllGranted roles="${Role.ROLE_CUSTOMER}">			
					<li class="">
						<a href="#">
						<i class="icon-gift"></i> 
						<span class="title">Puntos</span>
						<span class="selected"></span>
						</a>
					</li>
				</sec:ifAllGranted>-->
				
				<!-- Clientes -->
	<!--			<sec:ifAnyGranted roles="${Role.ROLE_PUB_OWNER},${Role.ROLE_ASSOCIATION_OWNER},${Role.ROLE_SUPERADMIN}">
					<li id="menu-customers" class="">
						<a href="${createLink(controller:'customers')}">
						<i class="icon-user"></i> 
						<span class="title">Clientes</span>
						<span class="selected"></span>
						</a>
					</li>
				</sec:ifAnyGranted>			-->
				
				<!-- Ingresos -->
	<!--			<sec:ifAllGranted roles="${Role.ROLE_PUB_OWNER}">
					<li class="">
						<a href="#">
						<i class="icon-usd"></i> 
						<span class="title">Ingresos</span>
						<span class="selected"></span>
						</a>
					</li>
				</sec:ifAllGranted>			-->
				
				<!-- Estadisticas -->
	<!--			<sec:ifAnyGranted roles="${Role.ROLE_PUB_OWNER},${Role.ROLE_ASSOCIATION_OWNER},${Role.ROLE_SUPERADMIN}">						
					<li class="last ">
						<a href="#">
						<i class="icon-bar-chart"></i> 
						<span class="title">Estadísticas</span>
						</a>
					</li>
				</sec:ifAnyGranted>			-->
			</ul>
			<!-- END SIDEBAR MENU -->
		</div>
		<!-- END SIDEBAR -->
		
		
		<!-- BEGIN PAGE -->
		<g:layoutBody/>
		<!-- END PAGE -->
		
		
	</div>
	<!-- END CONTAINER -->
	<!-- BEGIN FOOTER -->
	<div class="footer">
		<div class="footer-inner">
			2014 &copy; Nido Aplicaciones.
		</div>
		<div class="footer-tools">
			<span class="go-top">
			<i class="icon-angle-up"></i>
			</span>
		</div>
	</div>
	<!-- END FOOTER -->
	
	
	<r:layoutResources />
	
</body>	

</html>
