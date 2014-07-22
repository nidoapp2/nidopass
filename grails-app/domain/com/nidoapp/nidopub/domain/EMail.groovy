package com.nidoapp.nidopub.domain

/**
 * Clase que representa un EMail de la libreta de un Pub
 * @author Developer
 *
 */
class EMail {
	
	String name
	String email
	
	Pub pub
	
	static belongsTo = [Pub, EMailsList]
	
	static hasMany = [emailsLists:EMailsList]
	
	static constraints = {
		name nullable:false
		email nullable:false
		pub nullable:false
		
		emailsLists nullable:true
    }
	
	String toString() {
		"$name"
	}
}
