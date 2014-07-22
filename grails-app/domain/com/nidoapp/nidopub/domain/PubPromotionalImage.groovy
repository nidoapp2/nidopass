package com.nidoapp.nidopub.domain

/**
 * Clase que representa una Imagen Promocional de Pub para añadir en emails de entradas
 * @author Developer
 *
 */
class PubPromotionalImage {
	
	String image
	
	Date dateFrom
	Date dateTo
	
	static belongsTo = [pub: Pub]
	
	static constraints = {
		image nullable:false
		dateFrom nullable:false
		dateTo nullable:false
		
		pub nullable:false
		
    }
	
}
