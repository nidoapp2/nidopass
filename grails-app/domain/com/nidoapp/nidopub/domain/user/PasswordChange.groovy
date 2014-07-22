package com.nidoapp.nidopub.domain.user

/**
 * Clase que representa una peticion de cambio de password
 * @author Developer
 *
 */
class PasswordChange {

    Date createDate = new Date()
	Date expirationDate

	String urlIdentifier  = java.util.UUID.randomUUID().toString()

	User user

	static constraints = {

	}
}
