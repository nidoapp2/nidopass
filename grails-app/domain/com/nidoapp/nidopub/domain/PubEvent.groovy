package com.nidoapp.nidopub.domain

/**
 * Clase que representa un Evento de Pub
 * @author Developer
 *
 */
class PubEvent {
	
	String title
	String description
	Date dateEvent
	//Date dateTo
	String url
	String photo
	double price
	double commission=1
	String artist
	int totalTickets
	int totalTicketsOnlineSale
	int totalTicketsSold
	boolean ticketsOnlineSaleActive = false
	
	boolean ticketsSalesSendEmail = false
	
	boolean expiredPromotionalCodes = false
	
	boolean privateEvent = false
	
	//Acepta menores de edad
	boolean acceptsMinors=false
	
	//Número inicial entradas evento
	int ticketsInitNumber
	
	Pub pub
	
	PubMusicType musicType
	String musicSubType
	
	static belongsTo = [Pub, EventsListSend]
	
	static hasMany = [eventsListsSend:EventsListSend]
	
	static constraints = {
		title nullable:false
		description nullable:false
		dateEvent nullable:false
		url nullable:true
		photo nullable:true
		artist nullable:true
		musicType nullable:false
		musicSubType nullable:true
		
		eventsListsSend nullable: true
    }
	
	String toString() {
		"$title"
	}
}
