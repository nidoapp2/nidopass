package com.nidoapp.nidopub.domain.geo

/**
 * Clase que representa una ciudad
 * @author Developer
 *
 */
class City {
	
	String name
	
	
	Province province

    static constraints = {
    }
	
	String toString() {
		"$name"
	}
}
