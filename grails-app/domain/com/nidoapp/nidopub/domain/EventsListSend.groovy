package com.nidoapp.nidopub.domain

/**
 * Clase que representa un Listado de Eventos para Envío por mail de un Pub
 * @author Developer
 *
 */
class EventsListSend {
	
	String title
	Date sendDate
	
	Boolean sent = false
	
	static belongsTo = [pub: Pub]
	
	static hasMany = [events: PubEvent, emailsLists:EMailsList]
	
	static constraints = {
		title nullable:false
		sendDate nullable:false
		
		events nullable:true
		emailsLists nullable:true
    }
	
	String toString() {
		"$title"
	}
}
