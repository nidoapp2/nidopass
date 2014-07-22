package com.nidoapp.nidopub.domain.request


/**
 * Clase que representa una peticion de carta
 * @author Developer
 *
 */
class CarteRequest {
	
	static belongsTo = [customerRequest : CustomerRequest]
	static hasMany = [productRequests: ProductRequest]

    static constraints = {
    }
	
	
	def calculatePrice()
	{
		def price = 0 
		productRequests?.each{
			price =+ it.product.price
		}
		
		price
	}
}
