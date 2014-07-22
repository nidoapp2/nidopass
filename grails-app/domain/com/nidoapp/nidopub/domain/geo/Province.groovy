package com.nidoapp.nidopub.domain.geo

/**
 * Clase que representa una provincia
 * @author Developer
 *
 */
class Province {
	
	String name
	
	Country country

    static constraints = {
    }
	
	String toString() {
		"$name"
	}
}
