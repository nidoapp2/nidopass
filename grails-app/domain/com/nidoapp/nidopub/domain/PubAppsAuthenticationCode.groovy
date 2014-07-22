package com.nidoapp.nidopub.domain

/**
 * Clase que representa un Código de Acceso a Apps para una Sala
 * @author Developer
 *
 */
class PubAppsAuthenticationCode {
	
	String code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6).toUpperCase()
	
	String email
	
	static belongsTo = [pub: Pub]
	
	static constraints = {
		code nullable:false
		
		email nullable:false
		
		pub nullable:false
		
    }
	
}
