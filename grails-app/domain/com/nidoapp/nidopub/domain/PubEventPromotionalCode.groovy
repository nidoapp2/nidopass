package com.nidoapp.nidopub.domain

/**
 * Clase que representa un Código Promocional de Evento para realizar descuento de una
 * venta de entradas
 * @author Developer
 *
 */
class PubEventPromotionalCode {
	
	public static final String STATUS_PENDING = "STATUS_PENDING"
	public static final String STATUS_CONFIRMED = "STATUS_CONFIRMED"
	public static final String STATUS_EXPIRED = "STATUS_EXPIRED"
	
	String code = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 9)
	
	String discount
	Boolean used = false
	
	int maxTickets
	
	String email
	
	String status = STATUS_PENDING
	
	static belongsTo = [pubEvent: PubEvent]
	
	static constraints = {
		code nullable:false
		discount nullable:false
		maxTickets nullable:false
		
		email nullable:false
		
		pubEvent nullable:false
		
    }
	
}
