package com.nidoapp.nidopub.domain.geo

/**
 * Clase que representa un pais
 * @author Developer
 *
 */
class Country {
	
	String code
	String name
	
	String dialCode

    static constraints = {
		name nullable:true
    }
}
