package com.nidoapp.nidopub.domain

import com.nidoapp.nidopub.domain.api.ApiKey
import com.nidoapp.nidopub.domain.PubEventPromotionalCode

/**
 * Clase que representa una venta de tickets
 * @author Developer
 *
 */
class TicketsSold {
	
	
	public static final String STATUS_PENDING = "STATUS_PENDING"
	//public static final String STATUS_IN_PROGRESS = "STATUS_IN_PROGRESS"
	//public static final String STATUS_DENIED = "STATUS_DENIED"
	//public static final String STATUS_CANCELLED = "STATUS_CANCELLED"
	public static final String STATUS_CONFIRMED = "STATUS_CONFIRMED"
	public static final String STATUS_EXPIRED = "STATUS_EXPIRED"
	//public static final Integer EXPIRED_MINUTES = 30
	
	
	String externalId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12)
	
	Customer customer
	PubEvent pubEvent
	int totalTicketsBuy
	
	//RestaurantTable table
	
	Date buyDate
	int operationNumber
	//Integer adults
	//Integer children
	
	int ticketNumberFrom
	int ticketNumberTo
	
	//String comments
	
	String status = STATUS_PENDING
	
	Boolean notifiedPub = false
	String notifiedPubMode
	Date notifiedPubDate
	
	Boolean notifiedCustomer = false
	String notifiedCustomerMode
	Date notifiedCustomerDate	
	
	//String smsPubNotificationReference
	
	
	//Date createDate
	
	PubEventPromotionalCode promotionalCode
	
	ApiKey sourceApiKey
	
	

    static constraints = {
		
		//table nullable:true
		notifiedPubMode nullable:true
		notifiedPubDate nullable:true
		notifiedCustomerMode nullable:true
		notifiedCustomerDate nullable:true
		//smsPubNotificationReference nullable:true
		sourceApiKey nullable:true
		promotionalCode nullable:true
		//comments nullable:true
		
		//todo: qiutar
		externalId nullable:false
    }
	
	static mapping = {
		//comments type: 'text'
	 }
}
