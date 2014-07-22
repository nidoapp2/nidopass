<!DOCTYPE html>
<%@page import="org.codehaus.groovy.grails.commons.GrailsApplication"%>
<%@page import="com.nidoapp.nidopub.domain.Pub"%>
<%@page import="com.nidoapp.nidopub.domain.Association"%>
<%@page import="com.nidoapp.nidopub.domain.user.UserRole"%>
<%@page import="com.nidoapp.nidopub.domain.user.Role"%>
<head>
	<meta name="layout" content="admin"/>
	<title>NidoPass | Admin Dashboard</title>
	
	<!-- BEGIN PAGE LEVEL STYLES --> 
	<!--<link href="${resource(dir: 'js/plugins/gritter/css', file: 'jquery.gritter.css')}" rel="stylesheet" type="text/css"/>-->
	<link href="${resource(dir: 'js/plugins/bootstrap-daterangepicker', file: 'daterangepicker.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'js/plugins/fullcalendar/fullcalendar', file: 'fullcalendar.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'js/plugins/jqvmap/jqvmap', file: 'jqvmap.css')}" rel="stylesheet" type="text/css"/>
	<link href="${resource(dir: 'js/plugins/jquery-easy-pie-chart', file: 'jquery.easy-pie-chart.css')}" rel="stylesheet" type="text/css"/>
	
	<link href="${resource(dir: 'js/plugins/jquery-ui', file: 'jquery-ui-1.10.1.custom.min.css')}" rel="stylesheet" type="text/css"/>
	<!-- END PAGE LEVEL STYLES -->
	<calendar:resources lang="es" theme="tiger"/>
	
	
</head>
<!-- END HEAD -->
<!-- BEGIN BODY -->
<body>

<!-- BEGIN PAGE -->
		<div class="page-content" id="divContent" style="text-align:center; padding-top:2.5em; background:gray; color:white;">
			<h1>Bienvenido/a al panel de administración de</h1>
			<img src="${grailsApplication.config.grails.serverURL + '/static/images/logo.png' }" alt="logo" style="background:black; max-height:10em;">
			<br><br>
			<g:if test="${UserRole.findByUser(user).role.authority == Role.ROLE_PUB_OWNER }">
				<g:set var="userAssociation" value="null" scope="page" />
				<g:set var="endEach" value="0" scope="page" />
				<g:if test="${user.pubs }">
					<g:each var="asoc" in="${Association.findAll()}">
					    <g:if test="${userAssociation == 'null' }">
						    <g:each var="pub" in="${asoc.pubs}">
						    	<g:if test="${user.pubs.first() == pub && userAssociation == 'null' }">
						    		<g:set var="userAssociation" value="${userAssociation=asoc}" scope="page" />
						    		<g:set var="endEach" value="1" scope="page" />
						    	</g:if>
							</g:each>
						</g:if>
					</g:each>
				
					<g:if test="${userAssociation!='null' }">
						<img src="${g.createLink(controller:'resources', action: 'associationLogo', params:[association_id: userAssociation.id])}" alt="" />
					</g:if>
				</g:if>
			</g:if>
			<g:elseif test="${UserRole.findByUser(user).role.authority == Role.ROLE_ASSOCIATION_OWNER }" >
				<img src="${g.createLink(controller:'resources', action: 'associationLogo', params:[association_id: user.association.id])}" alt="" />
			</g:elseif>
			
		</div>
		
		<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap', file: 'jquery.vmap.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap/maps', file: 'jquery.vmap.russia.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap/maps', file: 'jquery.vmap.world.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap/maps', file: 'jquery.vmap.europe.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap/maps', file: 'jquery.vmap.germany.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap/maps', file: 'jquery.vmap.usa.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/jqvmap/jqvmap/data', file: 'jquery.vmap.sampledata.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/flot', file: 'jquery.flot.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/flot', file: 'jquery.flot.resize.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins', file: 'jquery.pulsate.min.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/bootstrap-daterangepicker', file: 'date.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js/plugins/bootstrap-daterangepicker', file: 'daterangepicker.js')}" type="text/javascript" ></script>
	<!--<script src="${resource(dir: 'js/plugins/gritter/js', file: 'jquery.gritter.js')}" type="text/javascript" ></script>-->
	<script src="${resource(dir: 'js/plugins/fullcalendar/fullcalendar', file: 'fullcalendar.min.js')}" type="text/javascript" ></script>
    <script src="${resource(dir: 'js/plugins/jquery-easy-pie-chart', file: 'jquery.easy-pie-chart.js')}" type="text/javascript" ></script> 
    <script src="${resource(dir: 'js/plugins', file: 'jquery.sparkline.min.js')}" type="text/javascript" ></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${resource(dir: 'js', file: 'app.js')}" type="text/javascript" ></script>
	<script src="${resource(dir: 'js', file: 'index.js')}" type="text/javascript" ></script>  
	
	<script src="${resource(dir: 'js', file: 'ui-jqueryui.js')}" type="text/javascript" ></script> 
	<!-- END PAGE LEVEL SCRIPTS -->  
	<script>
		jQuery(document).ready(function() {    

			$("#menu-home").addClass("active");
			
		   App.init(); // initlayout and core plugins
		   Index.init();
		   Index.initJQVMAP(); // init index page's custom scripts
		   Index.initCalendar(); // init index page's custom scripts
		   Index.initCharts(); // init index page's custom scripts
		   Index.initChat();
		   Index.initMiniCharts();
		   Index.initDashboardDaterange();
		   Index.initIntro();
		   
		   // basic dialog3
    	$( "#dialog_features" ).dialog({
		      autoOpen: false,
		      dialogClass: 'ui-dialog-grey',
		      show: {
		        effect: "blind",
		        duration: 500
		      },
		      hide: {
		        effect: "explode",
		        duration: 500
		      }
	    });
	    
	    $( "#dialog_features" ).dialog( "open" );	
	     $('.ui-dialog button').blur();// avoid button autofocus
	 
	    
		});
	</script>

</body>
<!-- END BODY -->
</html>
<!-- Localized -->