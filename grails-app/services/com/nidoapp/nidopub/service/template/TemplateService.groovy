package com.nidoapp.nidopub.service.template

import org.codehaus.groovy.grails.web.context.ServletContextHolder
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.support.WebApplicationContextUtils


/**
 * COnjutno de servicios relacionados con la generacion de texto a partir de plantillas
 * NOTA: Requiere grails.serverURL
 * @author Developer
 *
 */
class TemplateService {
	
	//def groovyPagesTemplateEngine
	grails.gsp.PageRenderer groovyPageRenderer
	

	

    /**
     * Gernera texto a patir de una plantilla de vista y un modelo de datos
     * @param view vista
     * @param model modelo
     * @return texto generado
     */
    def renderToString(view, model){
		
		log.info "Generando texto aplicando plantilla '${view}' y modelo: ${model}"
		/*
		WebApplicationContextUtils a

		def webRequest = RequestContextHolder.getRequestAttributes()
		if(!webRequest) {
			def servletContext  = ServletContextHolder.getServletContext()
			def applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext)
			webRequest = grails.util.GrailsWebUtil.bindMockWebRequest(applicationContext)
		}


		
		
		def template = groovyPagesTemplateEngine.createTemplate(view)
		
		log.info "Template: " + template
		
		
		def out = new StringWriter()
		template.make(model).writeTo(out)
		*/
		
		def string = groovyPageRenderer.render(view: view, model: model)
		
		//log.info "Texto generado: " + string


		
		string
	}
}
