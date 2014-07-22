package com.nidoapp.nidopub.domain

/**
 * Clase que representa una Valorari??n de Plato
 * @author Developer
 *
 */
class ProductValorations {
	
	int valoration
	String comment
	String phoneNumber
	
	static belongsTo = [product:Product]
	
	static constraints = {
		valoration nullable:false
		
    }
}
