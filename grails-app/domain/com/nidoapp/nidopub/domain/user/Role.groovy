package com.nidoapp.nidopub.domain.user

/**
 * Clase que representa los posibles roles de usuario
 * @author Developer
 *
 */
class Role {
	
	public static final String ROLE_CUSTOMER = "ROLE_CUSTOMER"
	public static final String ROLE_PUB_OWNER = "ROLE_PUB_OWNER"
	public static final String ROLE_ASSOCIATION_OWNER = "ROLE_ASSOCIATION_OWNER"
	public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN"

	String authority

	static mapping = {
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
	
	String toString() {
		"$authority"
	}
}
