package com.nidoapp.nidopub.domain

/**
 * Clase que representa una carta de un pub
 * @author Developer
 *
 */
class Carte {
	
	String name
	String description
	Boolean active
	//Boolean wineCarte
	
	static belongsTo = [pub : Pub]
	
	long pub_id
	
	static hasMany = [sections: CarteSection]

    static constraints = {
		
		name nullable: true
		description nullable: true
    }
	
	String toString() {
		"$name"
	}
}
