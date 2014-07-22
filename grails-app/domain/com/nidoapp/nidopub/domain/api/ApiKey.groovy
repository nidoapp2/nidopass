package com.nidoapp.nidopub.domain.api

import com.nidoapp.nidopub.domain.Association
import com.nidoapp.nidopub.domain.Pub
import java.util.UUID

/**
 * Clase que representa un clave de invocacion a API con los permisos asociados
 * @author Developer
 *
 */
class ApiKey {
	
	public static final String ROLE_PUB_APP = "ROLE_PUB_APP"
	public static final String ROLE_ASSOCIATION_APP = "ROLE_ASSOCIATION_APP"
	public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN"
	
	
	String apiKey = UUID.randomUUID().toString()
	String role
	Boolean enabled = false
	
	Pub pub
	Association association

    static constraints = {
		apiKey unique:true
		
		pub nullable: true
		association nullable:true
    }
	
	def roleAny(roles)
	{
		roles.contains(role)
	}
}
