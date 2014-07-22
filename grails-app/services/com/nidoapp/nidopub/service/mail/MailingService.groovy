package com.nidoapp.nidopub.service.mail

import grails.plugin.rendering.pdf.PdfRenderingService;
import grails.util.Environment
import pl.touk.excel.export.WebXlsxExporter
import pl.touk.excel.export.XlsxExporter

/**
 * Conjunto de servicios que realizan operaciones asociadas al envio o recepcion de Mail 
 * @author Developer
 *
 */
class MailingService {
	
	def grailsApplication
	def mailService

    /**
     * Servicio que realiza un envio de un email. Su comportamiento depende del entorno en el que se ejecuta
     * la aplicacion. Solo el entorno de produccion tiene abierto el envio de emails a correos no aprobados
     * 
     * @param emailTo destinatario
     * @param subjectTitle asunto
     * @param htmlContent contenido en HTML
     */
    def sendMail(emailFrom, emailTo, subjectTitle, htmlContent, host, port, username, password) {
		
	
		def environment = Environment.current.name
		
		log.info "Enviando Mail indicando en entorno: " +  environment
		
		if (!emailFrom) {
			emailFrom = grailsApplication.config.mail.from
			grailsApplication.config.mail.host = "mail.nidoapp.com"
			grailsApplication.config.mail.port = 587
			grailsApplication.config.mail.username = "nidorest@nidoapp.com"
			grailsApplication.config.mail.password = "nidocloud"
		} else {
			grailsApplication.config.mail.host = host
			grailsApplication.config.mail.port = port
			grailsApplication.config.mail.username = username
			grailsApplication.config.mail.password = password
		}
		
		if (Environment.current == Environment.DEVELOPMENT )
		{
			
			def recipient = recipientChecked(emailTo)
			
			mailService.sendMail
			{
				to recipient
				from emailFrom
				subject subjectTitle
				html htmlContent
			}
		}
		else if (Environment.current == Environment.TEST )
		{
			def recipient = recipientChecked(emailTo)
			
			mailService.sendMail
			{
				to recipient
				from emailFrom
				subject subjectTitle
				html htmlContent
			}
		}
		
		else if (Environment.current == Environment.PRODUCTION )
		{
			mailService.sendMail
			{
				to emailTo
				from emailFrom
				subject subjectTitle
				html htmlContent
			}
		}
		
		
		
		

    }
	
	/**
	 * Servicio que realiza un envio de un email con PDF adjunto. Su comportamiento depende del entorno en el que se ejecuta
	 * la aplicacion. Solo el entorno de produccion tiene abierto el envio de emails a correos no aprobados
	 *
	 * @param emailTo destinatario
	 * @param subjectTitle asunto
	 * @param htmlContent contenido en HTML
	 */
	def pdfRenderingService
	def sendMailTicketsWithPDF(emailFrom, emailTo, subjectTitle, htmlContent, host, port, username, password, ticketsSold) {
		
	
		def environment = Environment.current.name
		
		log.info "Enviando Mail indicando en entorno: " +  environment
		
		if (!emailFrom) {
			emailFrom = grailsApplication.config.mail.from
			grailsApplication.config.mail.host = "mail.nidoapp.com"
			grailsApplication.config.mail.port = 587
			grailsApplication.config.mail.username = "nidorest@nidoapp.com"
			grailsApplication.config.mail.password = "nidocloud"
		} else {
			grailsApplication.config.mail.host = host
			grailsApplication.config.mail.port = port
			grailsApplication.config.mail.username = username
			grailsApplication.config.mail.password = password
		}
		
		def ruta = grailsApplication.mainContext.getResource('map_and_pdf').file.absolutePath
		
		//String ruta = new File('mapImg-').getAbsolutePath()
		//log.info "Ruta absoluta carpeta Mapa y PDF TEMPORALES: " + ruta
		def url = 'http://maps.googleapis.com/maps/api/staticmap?center='+ticketsSold.pubEvent.pub.latitude+','+ticketsSold.pubEvent.pub.longitude+'&zoom=15&size=300x300&maptype=roadmap&markers=color:blue%7Clabel:R%7C'+ticketsSold.pubEvent.pub.latitude+','+ticketsSold.pubEvent.pub.longitude+'&sensor=false'
		def file = new File(ruta+'/mapImg-'+ticketsSold.externalId+".jpg").newOutputStream()
		file << new URL(url).openStream()
		file.close()
		
		log.info new File(ruta+'/mapImg-'+ticketsSold.externalId+".jpg").getAbsolutePath()
		
		ByteArrayOutputStream bytes = pdfRenderingService.render(template: '/mailing/customerTicketsBuyPDF', model: [ticketsSold:ticketsSold])
		/*def pdfFile = new File("entradas.pdf").withOutputStream { outputStream ->
            outputStream << pdfRenderingService.render(template: '/mailing/customerTicketsBuyPDF', model: [ticketsSold:ticketsSold])
        }*/
		def fos= new FileOutputStream(ruta+'/entradas_'+ticketsSold.externalId+'.pdf')
		fos.write(bytes.toByteArray())
		fos.close()
		
		if (Environment.current == Environment.DEVELOPMENT )
		{
			
			def recipient = recipientChecked(emailTo)
			
			mailService.sendMail
			{
				multipart true
				to recipient
				from emailFrom
				subject subjectTitle
				html htmlContent
				attachBytes 'entradas.pdf','application/pdf', new File(ruta+'/entradas_'+ticketsSold.externalId+'.pdf').readBytes()
			}
		}
		else if (Environment.current == Environment.TEST )
		{
			def recipient = recipientChecked(emailTo)
			
			mailService.sendMail
			{
				multipart true
				to recipient
				from emailFrom
				subject subjectTitle
				html htmlContent
				attachBytes 'entradas.pdf','application/pdf', new File(ruta+'/entradas_'+ticketsSold.externalId+'.pdf').readBytes()
			}
		}
		
		else if (Environment.current == Environment.PRODUCTION )
		{
			mailService.sendMail
			{
				multipart true
				to emailTo
				from emailFrom
				subject subjectTitle
				html htmlContent
				attachBytes 'entradas.pdf','application/pdf', new File(ruta+'/entradas_'+ticketsSold.externalId+'.pdf').readBytes()
			}
		}
		
		new File(ruta+'/mapImg-'+ticketsSold.externalId+".jpg").delete()
		new File(ruta+'/entradas_'+ticketsSold.externalId+'.pdf').delete()
	}
	
	
	/**
	 * Servicio que realiza un envio de un email de ventas de entradas con Excel adjunto.
	 * Su comportamiento depende del entorno en el que se ejecuta la aplicacion.
	 * Solo el entorno de produccion tiene abierto el envio de emails a correos no aprobados
	 *
	 * @param emailTo destinatario
	 * @param subjectTitle asunto
	 * @param htmlContent contenido en HTML
	 */
	
	def sendMailTicketsSalesWithExcel(emailFrom, emailTo, subjectTitle, htmlContent, host, port, username, password, pubEvent, salesList) {
		
	
		def environment = Environment.current.name
		
		log.info "Enviando Mail indicando en entorno: " +  environment
		
		if (!emailFrom) {
			emailFrom = grailsApplication.config.mail.from
			grailsApplication.config.mail.host = "mail.nidoapp.com"
			grailsApplication.config.mail.port = 587
			grailsApplication.config.mail.username = "nidorest@nidoapp.com"
			grailsApplication.config.mail.password = "nidocloud"
		} else {
			grailsApplication.config.mail.host = host
			grailsApplication.config.mail.port = port
			grailsApplication.config.mail.username = username
			grailsApplication.config.mail.password = password
		}
		
		def ruta = grailsApplication.mainContext.getResource('excel').file.absolutePath
		
		def  headers  =  [ 'Cliente' ,  'Email' ,  'Teléfono' ,  'Entradas compradas']
		def  withProperties  =  [ 'customer.name' ,  'customer.email' ,  'customer.phone' ,  'totalTicketsBuy']
		
		new  WebXlsxExporter(ruta+'/ventas_'+pubEvent.title.replaceAll(" ", "_")+'.xlsx').with {
		    setResponseHeaders(response)
		    fillHeader(headers)
		    add(salesList, withProperties)
		    save(response.outputStream)
		}
		
		//ByteArrayOutputStream bytes = pdfRenderingService.render(template: '/mailing/customerTicketsBuyPDF', model: [ticketsSold:ticketsSold])
		/*def pdfFile = new File("entradas.pdf").withOutputStream { outputStream ->
			outputStream << pdfRenderingService.render(template: '/mailing/customerTicketsBuyPDF', model: [ticketsSold:ticketsSold])
		}*/
		def fos= new FileOutputStream(ruta+'/ventas_'+pubEvent.title.replaceAll(" ", "_")+'.xlsx')
		fos.write(bytes.toByteArray())
		fos.close()
		
		if (Environment.current == Environment.DEVELOPMENT )
		{
			
			def recipient = recipientChecked(emailTo)
			
			mailService.sendMail
			{
				multipart true
				to recipient
				from emailFrom
				subject subjectTitle
				html htmlContent
				attachBytes 'ventas_entradas.xlsx','application/xlsx', new File(ruta+'/ventas_'+pubEvent.title.replaceAll(" ", "_")+'.xlsx').readBytes()
			}
		}
		else if (Environment.current == Environment.TEST )
		{
			def recipient = recipientChecked(emailTo)
			
			mailService.sendMail
			{
				multipart true
				to recipient
				from emailFrom
				subject subjectTitle
				html htmlContent
				attachBytes 'ventas_entradas.xlsx','application/xlsx', new File(ruta+'/ventas_'+pubEvent.title.replaceAll(" ", "_")+'.xlsx').readBytes()
			}
		}
		
		else if (Environment.current == Environment.PRODUCTION )
		{
			mailService.sendMail
			{
				multipart true
				to emailTo
				from emailFrom
				subject subjectTitle
				html htmlContent
				attachBytes 'ventas_entradas.xlsx','application/xlsx', new File(ruta+'/ventas_'+pubEvent.title.replaceAll(" ", "_")+'.xlsx').readBytes()
			}
		}
		
		new File(ruta+'/ventas_'+pubEvent.title.replaceAll(" ", "_")+'.xlsx').delete()
	}
	
	
	/**
	 * Servicio que envia un email al correo de soporte
	 * @param subjectTitle asunto
	 * @param htmlContent contenido en HTML
	 */
	def sendMailToSupport(subjectTitle, htmlContent)
	{
		def emailTo = grailsApplication.config.mail.support
		def emailFrom = grailsApplication.config.mail.from
		
		mailService.sendMail
			{
				to emailTo
				from emailFrom
				subject subjectTitle
				html htmlContent
			}
	}
	
	/**
	 * Metodo privado que comprueba si un destinatario es valido segun el entorno, y su no lo es
	 * devuelve uno que si sea aceptado para el entorno.
	 * @param to destinatario
	 * @return destinatario correcto
	 */
	def private recipientChecked(to)
	{
		def recipient
		
		def limits = grailsApplication.config.mail.limits
		
		if(limits)
		{
			log.info "Hay limites en los receptores de emails."
			
			def recipients = grailsApplication.config.mail.recipients
			
			log.info "Comprobando si el destinatario '${to}' esta entre los destinatarios permitidos: " + recipients
			
			if(recipients?.contains(to))
			{
				log.info "El destinatario '${to}' esta entre los destinatarios permitidos. Se le envia el email."
				recipient = to
			}
			else
			{
				recipient = recipients?.first()
				log.info "El destinatario '${to}' NO esta entre los destinatarios permitidos. Se envia email a: " + recipient
			}
			
		}
		else
		{
			log.info "No hay limites en los receptores de emails. Se envia a: " + to
			recipient = to
		}
		
		
		
		recipient
	}
}
