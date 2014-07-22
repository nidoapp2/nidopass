package com.nidoapp.nidopub.domain

import com.nidoapp.nidopub.domain.api.ApiKey

/**
 * Clase que representa un ticket
 * @author Developer
 *
 */
class Ticket {
	
	TicketsSold ticketsSold
	String externalId
	int ticketNumber
	
	boolean accessed = false
	

    static constraints = {
		ticketsSold nullable:false
		externalId nullable:false
		
    }
	
	static mapping = {
		
	 }
}
