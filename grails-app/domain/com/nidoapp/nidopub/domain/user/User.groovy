package com.nidoapp.nidopub.domain.user

import com.nidoapp.nidopub.domain.Association;
import com.nidoapp.nidopub.domain.Customer;
import com.nidoapp.nidopub.domain.Pub;

/**
 * Clase que representa un usuario
 * @author Developer
 *
 */
class User {

	transient springSecurityService

	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	
	String firstName
	String lastName
	
	String photo
	
	Association association
	Customer customer
	
	static hasMany = [pubs: Pub]

	static constraints = {
		username blank: false, unique: true
		password blank: false
		
		photo nullable: true
		
		association nullable:true
		customer nullable:true
	}

	static mapping = {
		password column: '`password`'
	}
	
	String toString() {
		"$firstName" + " " + "$lastName"
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
}
