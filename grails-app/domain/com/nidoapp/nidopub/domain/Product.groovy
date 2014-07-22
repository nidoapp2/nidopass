package com.nidoapp.nidopub.domain

/**
 * Clase que representa un plato
 * @author Developer
 *
 */
class Product {
	
	String name
	String description
	String photo
	Double price
	Boolean available = true
	
	String ingredients
	String allergens
	String youtubeRecipe
	String reference
	
	long pub_id
	
	static belongsTo = [CarteSection]
	
	static hasMany = [carteSections: CarteSection, reviews: CustomerReview, valoration:ProductValorations]
	
	static constraints = {
		description nullable:true
		photo nullable:true
		price nullable:true
		
		ingredients nullable:true
		allergens nullable:true
		youtubeRecipe nullable:true
    }
	
	String toString() {
		"$name"
	}
}
