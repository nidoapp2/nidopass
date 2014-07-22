package com.nidoapp.nidopub.domain

/**
 * Clase que representa una Lista de EMails de Pub
 * @author Developer
 *
 */
class EMailsList {
	
	String title
	
	Pub pub
	
	static belongsTo = [Pub, EventsListSend]
	
	static hasMany = [emails:EMail, eventsListsSend:EventsListSend]
	
	static constraints = {
		title nullable:false
		
		emails nullable:true
		
		eventsListsSend nullable:true
    }
	
	String toString() {
		"$title"
	}
}
