package com.nidoapp.nidopub.domain

import com.nidoapp.nidopub.domain.geo.Province;
import com.nidoapp.nidopub.domain.user.User;

/**
 * Clase que representa una asociacion de pubs
 * @author Developer
 *
 */
class Association {
	
	
	String name
	String photo
	
	Province province
	
	//Número máximo de pubs que tiene permitido crear
	int maxPubsCreate
	
	static belongsTo = [owner : User]
		
	static hasMany = [pubs: Pub]

    static constraints = {
		pubs nullable:true
		photo nullable:true
    }
	
	boolean equals (object)
	{
		(object instanceof Association) && (object.id == id)
	}
}
