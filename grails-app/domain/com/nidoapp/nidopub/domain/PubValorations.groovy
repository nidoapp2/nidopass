package com.nidoapp.nidopub.domain

/**
 * Clase que representa una Valoraci??n de Restaurante
 * @author Developer
 *
 */
class PubValorations {
	
	int valoration
	String comment
	String phoneNumber
	
	static belongsTo = [pub:Pub]
	
	static constraints = {
		valoration nullable:false
		
    }
}
