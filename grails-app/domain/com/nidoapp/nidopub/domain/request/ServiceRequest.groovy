package com.nidoapp.nidopub.domain.request


/**
 * Clase que representa una peticion completa de servicio de mesa, incluyendo todas las comandas
 * realizadas durante el servicio
 * @author Developer
 *
 */
class ServiceRequest {
	
	public static final String STATE_DRAFT = "STATE_DRAFT"
	public static final String STATE_PENDING = "STATE_PENDING"
	public static final String STATE_PROCESSING = "STATE_PROCESSING"
	public static final String STATE_SERVED = "STATE_SERVED"
	public static final String STATE_PAYMENT_WAITING = "STATE_PAYMENT_WAITING"
	public static final String STATE_PAYED = "STATE_PAYED"
	
	public static final String PAYMENT_MODE_ALL = "PAYMENT_MODE_ALL"
	public static final String PAYMENT_MODE_EACHONE = "PAYMENT_MODE_EACHONE"
	
		

	String state = STATE_DRAFT
	Date lastStateDate = new Date()
	
	Date createDate = new Date()
	
	String paymentMode = PAYMENT_MODE_ALL
	
	//static belongsTo = [table : RestaurantTable]
	
	static hasMany = [customerRequests: CustomerRequest, orders:RequestOrder]

    static constraints = {
    }
	
	def calculatePrice()
	{
		def price = 0
		customerRequests?.each{
			price =+ it.calculatePrice()
		}
		
		price
	}
}
