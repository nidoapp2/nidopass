package com.nidoapp.nidopub.domain

/**
 * Clase que representa una noticia de asociación
 * @author Developer
 *
 */
class AssociationNews {
	
	String title
	String description
	Date creationDate
	String photo
	String url
	
	static belongsTo = [association: Association]
	
	static constraints = {
		title nullable: false
		description nullable:false
		creationDate nullable: false
		photo nullable:true
		url nullable:true
    }
	
	String toString() {
		"$title"
	}
}
