package com.nidoapp.nidopub.domain

import com.nidoapp.nidopub.domain.request.ServiceRequest

/**
 * Clase que representa una factura o ticket
 * @author Developer
 *
 */
class Invoice {
	
	Pub pub
	Customer customer
	String invoiceFile
	Double ammunt
	Date issueDate		
	
	ServiceRequest request
	Customer customerRequest


    static constraints = {
		
		customerRequest nullable:true
    }
}
