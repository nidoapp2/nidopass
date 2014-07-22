package com.nidoapp.nidopub.domain.request

import java.util.Date;

/**
 * Clase que representa una comanda que tiene que ser servida a la vez
 * @author Developer
 *
 */
class RequestOrder {
	
	public static final String STATE_DRAFT = "STATE_DRAFT"
	public static final String STATE_PENDING = "STATE_PENDING"
	public static final String STATE_PROCESSING = "STATE_PROCESSING"
	public static final String STATE_SERVED = "STATE_SERVED"
	
	String state = STATE_DRAFT
	
	Integer orderNumber
	
	Date lastStateDate = new Date()
	
	Date estimatedServingDate = new Date()
	
	Date createDate = new Date()
	
	static belongsTo = [serviceRequest: ServiceRequest]
	static hasMany = [productRequests: ProductRequest]

    static constraints = {
    }
}
