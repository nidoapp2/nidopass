package com.nidoapp.nidopub.domain.request

import com.nidoapp.nidopub.domain.Customer;

/**
 * Clase que representa una peticion de un cliente, que podra incluir varias peticiones de menu y/o carta
 * @author Developer
 *
 */
class CustomerRequest {
	
	Customer customer
	
	static belongsTo = [serviceRequest : ServiceRequest]
	static hasMany = [carteRequests: CarteRequest]

    static constraints = {
    }
	
	def calculatePrice()
	{
		def price = 0	
		carteRequests?.each{
			price =+ it.calculatePrice()
		}
		price
	}
}
