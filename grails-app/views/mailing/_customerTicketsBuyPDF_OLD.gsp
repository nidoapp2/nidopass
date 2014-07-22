<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.codehaus.groovy.grails.commons.GrailsApplication"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>NidoPass | ${ticketsSold?.pubEvent?.pub?.name } - Entrada/s evento ${ticketsSold?.pubEvent?.title.replaceAll('&', '&amp;')}</title>
		
		<style type="text/css">
			#outlook a{padding:0;} /* Force Outlook to provide a "view in browser" button. */
			body{width:100% !important; margin:0; font-family:Open Sans;} 
			body{-webkit-text-size-adjust:none;} /* Prevent Webkit platforms from changing default text sizes. */
			body{margin:0; padding:0;}
			img{border:0; height:auto; line-height:100%; outline:none; text-decoration:none;}
			table td{border-collapse:collapse;}
			#backgroundTable{height:100% !important; margin:0; padding:0; width:100% !important;}
			@import url(http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700); /* Loading Open Sans Google font */ 
			body, #backgroundTable{ background-color:#FFF; }
			.TopbarLogo{
			padding:10px;
			text-align:left;
			vertical-align:middle;
			}
			h1, .h1{
			color:#444444;
			display:block;
			font-family:Open Sans;
			font-size:35px;
			font-weight: 400;
			line-height:100%;
			margin-top:2%;
			margin-right:0;
			margin-bottom:1%;
			margin-left:0;
			text-align:left;
			}
			h2, .h2{
			color:#444444;
			display:block;
			font-family:Open Sans;
			font-size:30px;
			font-weight: 400;
			line-height:100%;
			margin-top:2%;
			margin-right:0;
			margin-bottom:1%;
			margin-left:0;
			text-align:left;
			}
			h3, .h3{
			color:#444444;
			display:block;
			font-family:Open Sans;
			font-size:24px;
			font-weight:400;
			margin-top:2%;
			margin-right:0;
			margin-bottom:1%;
			margin-left:0;
			text-align:left;
			}
			h4, .h4{
			color:#444444;
			display:block;
			font-family:Open Sans;
			font-size:18px;
			font-weight:400;
			line-height:100%;
			margin-top:2%;
			margin-right:0;
			margin-bottom:1%;
			margin-left:0;
			text-align:left;
			}
			h5, .h5{
			color:#444444;
			display:block;
			font-family:Open Sans;
			font-size:14px;
			font-weight:400;
			line-height:100%;
			margin-top:2%;
			margin-right:0;
			margin-bottom:1%;
			margin-left:0;
			text-align:left;
			}
			.textdark { 
			color: #444444;
			font-family: Open Sans;
			font-size: 13px;
			line-height: 150%;
			text-align: left;
			}
			.textwhite { 
			color: #fff;
			font-family: Open Sans;
			font-size: 13px;
			line-height: 150%;
			text-align: left;
			}
			.fontwhite { color:#fff; }
			.btn {
			background-color: #e5e5e5;
			background-image: none;
			filter: none;
			border: 0;
			box-shadow: none;
			padding: 7px 14px; 
			text-shadow: none;
			font-family: "Segoe UI", Helvetica, Arial, sans-serif;
			font-size: 14px;  
			color: #333333;
			cursor: pointer;
			outline: none;
			-webkit-border-radius: 0 !important;
			-moz-border-radius: 0 !important;
			border-radius: 0 !important;
			}
			.btn:hover, 
			.btn:focus, 
			.btn:active,
			.btn.active,
			.btn[disabled],
			.btn.disabled {  
			font-family: "Segoe UI", Helvetica, Arial, sans-serif;
			color: #333333;
			box-shadow: none;
			background-color: #d8d8d8;
			}
			.btn.red {
			color: white;
			text-shadow: none;
			background-color: #d84a38;
			}
			.btn.red:hover, 
			.btn.red:focus, 
			.btn.red:active, 
			.btn.red.active,
			.btn.red[disabled], 
			.btn.red.disabled {    
			background-color: #bb2413 !important;
			color: #fff !important;
			}
			.btn.green {
			color: white;
			text-shadow: none; 
			background-color: #35aa47;
			}
			.btn.green:hover, 
			.btn.green:focus, 
			.btn.green:active, 
			.btn.green.active,
			.btn.green.disabled, 
			.btn.green[disabled]{ 
			background-color: #1d943b !important;
			color: #fff !important;
			}
			a.btn:link   
			{   
			 text-decoration:none;   
			}  
		</style>
		
	</head>
	<body>
		<div style="width:100%;">
					<center>
									<h2>Entrada/s Evento ${ticketsSold?.pubEvent?.title.replaceAll('&', '&amp;')}</h2>
									<br />
									<div class="textdark">
										<p>
										Hola,
										</p>
										<p>
										Estas son la/s entrada/s que ha comprado para el evento ${ticketsSold?.pubEvent?.title.replaceAll('&', '&amp;')} del día ${String.format( '%02d', ticketsSold?.pubEvent?.dateEvent.getDate())+"/"+String.format( '%02d', (ticketsSold?.pubEvent?.dateEvent.getMonth()+1))+"/"+(ticketsSold?.pubEvent?.dateEvent.getYear()+1900)} a las ${String.format( '%02d', ticketsSold?.pubEvent?.dateEvent.getHours())+":"+String.format( '%02d', ticketsSold?.pubEvent?.dateEvent.getMinutes())}:
										</p>
										<ul style="list-style-type: none;">
											<li style="list-style-type: none;">
												<b>Entrada/s</b>
											</li>
											
												<g:each in="${1..ticketsSold.totalTicketsBuy}" var="ticketNumber">
													<li style="list-style-type: none;">
													<center> 
														
														<table style="width:600px; padding:20px 50px 20px 50px !important; border:2px dashed black;"> 
															<tr>
																<td style="font-size:10px; text-align:center;" colspan="4"><img src="${g.pubLogoUrl('pub':ticketsSold?.pubEvent?.pub)}" style="height:80px;" alt="" /></td>
																<td rowspan="10" style="padding-left:25px; padding-top:20px;"><img src="${g.pubEventImageUrl('event':ticketsSold?.pubEvent)}" style="width:200px;" alt="" /></td>
															</tr><tr><td style="height:5px;" colspan="4"></td></tr><tr>
																<td style="font-size:10px; text-align:left !important; background:#d0d0d0; color:black;" colspan="4"><b>${ticketsSold?.pubEvent?.title.replaceAll('&', '&amp;')}</b></td>
															</tr><tr><td style="height:5px;" colspan="4"></td></tr><tr>
																<td style="font-size:10px; text-align:left !important; background:black; color:white;">FECHA:</td><td  style="font-size:10px; text-align:left !important; background:#d0d0d0; color:black;"><b>${String.format( '%02d', ticketsSold?.pubEvent?.dateEvent.getDate())+"/"+String.format( '%02d', (ticketsSold?.pubEvent?.dateEvent.getMonth()+1))+"/"+(ticketsSold?.pubEvent?.dateEvent.getYear()+1900)}</b></td>
																<td style="font-size:10px; text-align:left !important; background:black; color:white;">HORA:</td><td style="font-size:10px; text-align:left !important; background:#d0d0d0; color:black;"><b>${String.format( '%02d', ticketsSold?.pubEvent?.dateEvent.getHours())+":"+String.format( '%02d', ticketsSold?.pubEvent?.dateEvent.getMinutes())}</b></td>
															</tr><tr><td style="height:5px;" colspan="4"></td></tr><tr>
																<td style="font-size:10px; text-align:left !important; background:black; color:white;" colspan="2">ARTISTA:</td><td style="font-size:10px; text-align:left !important; background:#d0d0d0; color:black;" colspan="2"><b>${ticketsSold?.pubEvent?.artist.replaceAll('&', '&amp;')}</b></td>
															</tr><tr><td style="height:5px;" colspan="4"></td></tr><tr>
																<td style="font-size:10px; text-align:left !important; background:black; color:white;" colspan="2">PRECIO:</td><td style="font-size:10px; text-align:left !important; background:#d0d0d0; color:black;" colspan="2"><b>${ticketsSold?.pubEvent.price+ticketsSold?.pubEvent?.commission}€</b></td>
															</tr><tr>
																<td colspan="4" style="font-size:6px; text-align:left !important; font-size:10px;">Localizador: ${ticketsSold?.externalId }</td>
															</tr><tr>
																<td style="text-align:center;" colspan="4"><img src="${grailsApplication.config.grails.serverURL}/qrcode?text=${ticketsSold?.externalId}%2F${ticketsSold?.ticketNumberFrom + ticketNumber - 1 }" style="width:100px; height:100px;" /></td>
															
																<td style="font-size:8px; line-height:1;"><b>CONDICIONES DE USO:</b><br/>
1.La adquisición de la entrada es en firme y no se aceptaran cambios ni devoluciones 2.Queda reservado el derecho de admisión 3.Es potestad de la organización permitir el acceso al recinto una vez iniciado el
espectáculo 4.La Organización se reserva el derecho a modificar el programa del evento 5.La Organización no se hace responsable de las entradas que no hayan sido adquiridas en los puntos de venta
oficiales 6.No se aceptaran entradas dañadas, rotas, usadas o con indicios de falsificación 7.El poseedor de la entrada perderá los derechos que ésta le otorga al salir del recinto 8.No se permite
la entrada de cámaras fotográficas, videocámaras y punteros láser en el recinto, así como cualquier otro objeto que la Organización considere peligroso o molesto para el correcto
desarrollo del espectáculo 9.Se debe conservar la entrada hasta el final del espectáculo, toda persona en el recinto deberá mostrar su entrada al personal de la Organización cuando se le solicite
10.Queda prohibida la reventa o cualquier uso promocional de la entrada sin la autorización de la Organización 11. Esta entrada está sometida a las Condiciones Generales de compra.</td>
															</tr>
															
														</table>
														
														<table style="width:600px; text-align:left !important; font-size:10px;">
															<tr>
																<td style="text-align:center; padding-top:10px;"><img src="${grailsApplication.config.grails.serverURL+'/map_and_pdf/mapImg-'+ticketsSold?.externalId+'.jpg'}" style="height:200px; width:200px;'" /></td>
																<td style="text-align:center; padding-top:10px;"><img src="${g.pubPromotionalImageTicketsUrlPdf('event':ticketsSold?.pubEvent)}" style="height:150px;" alt="" /></td>
															</tr>
														</table>
														<br/>
														<hr size="2" noshade="noshade" width="300" style="color:black; margin-top:15px; margin-bottom:15px;" />
														<br/><br/><br/><br/><br/><br/><br/>
													</center>
													</li>
												</g:each>
											
										</ul>
																		
										
										<br/>
									</div>

									<p style="text-align:left !important;">
										Muchas gracias. Saludos,
									</p>
					</center>
		</div>
	</body>
</html>