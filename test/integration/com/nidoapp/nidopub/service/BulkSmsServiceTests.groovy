package com.nidoapp.nidopub.service



import grails.test.mixin.*
import org.junit.*
import org.apache.commons.logging.LogFactory

import com.nidoapp.nidopub.service.sms.providers.BulkSmsService;

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(BulkSmsService)
class BulkSmsServiceTests {
	
	

	/*
	void testSendMessage() {
		//def text = "Hola Diego, cuando puedas responde a este SMS con solo un SI (con acento). A ver si ahora llega!"
		//def phone = "34655890699"
		//ProcessId del mensje mandado a Diego: 474957878
		
		println "Enviando SMS..."
		
		def result = service.sendSmsFromEnvironment(text, phone)
		if(!result.error)
		{
			println "SMS enviado correctamente. Codigo: ${result.reference}" 
		}
		else
		{
			println "Error enviando SMS. (Codigo: ${result.errorCode}, Description: ${result.errorDescription})"
		}
    }
    */
    
    
	
    void testGetInbox() {
		println "Obteniendo bandeja de entrada de SMS..."
		def result = service.getInbox(0)
		
		if(!result.error)
		{
			println "Existen ${result.messages?.size()} mensajes:"
			result.messages?.each {
				println "Mensaje ${it.id}, a telefono ${it.phone}, referencia: ${it.reference}, contenido: ${it.message}"
			}
		}
		else
		{
			println "Error obteniendo bandeja de entrada de SMS. (Codigo: ${result.errorCode}, Description: ${result.errorDescription})"
		}
    }
    
    
}
