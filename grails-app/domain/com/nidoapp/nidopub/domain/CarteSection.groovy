package com.nidoapp.nidopub.domain

/**
 * Clase que representa una seccion de una carta de restaurante
 * @author Developer
 *
 */
class CarteSection {
	
	String name
	
	Integer orderNumber
	
	static belongsTo = [carte: Carte]
	
	static hasMany = [products: Product]

    static constraints = {
    }
	
	String toString() {
		"$name"
	}
}
