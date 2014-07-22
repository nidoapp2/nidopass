package com.nidoapp.nidopub.domain

/**
 * Clase que representa una foto de pub
 * @author Developer
 *
 */
class PubPhoto {
	
	String repositoryImageId
	
	String description
	
	Boolean main = false
	
	static belongsTo = [pub: Pub]
	
    static constraints = {
    }
}
