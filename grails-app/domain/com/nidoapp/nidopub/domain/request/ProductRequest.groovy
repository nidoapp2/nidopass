package com.nidoapp.nidopub.domain.request

import com.nidoapp.nidopub.domain.Product

/**
 * Clase que representa una peticion de un plato
 * @author Developer
 *
 */
class ProductRequest {
	
	public static final String STATE_PENDING = "STATE_PENDING"
	public static final String STATE_PROCESSING = "STATE_PROCESSING"
	public static final String STATE_SERVED = "STATE_SERVED"
	
	Product product
	
	String state = STATE_PENDING
	
	RequestOrder requestOrder
	
	static belongsTo = [carteRequest: CarteRequest]	
		
	//TODO: Esto deben ser caracteristicas tipificadas por tipo de plato: carne hecha, poco hecha, al punto, etc.
	String comments

    static constraints = {
		carteRequest nullable:true
		comments nullable:true
    }
}
