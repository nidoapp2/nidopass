package com.nidoapp.nidopub.domain

/**
 * Clase que representa una opinion y valoracion de un plato de un restaurante, realizada por un cliente
 * @author Developer
 * 
 */
class CustomerReview {
	
	Integer valoration
	String comments
	Date date
	Invoice invoice
	
	static belongsTo = [product: Product, customer: Customer]
	
    static constraints = {
		
		comments nullable:true
    }
}
