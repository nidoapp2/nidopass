package com.nidoapp.nidopub.domain

import com.nidoapp.nidopub.domain.geo.City;
import com.nidoapp.nidopub.domain.geo.Country;
import com.nidoapp.nidopub.domain.geo.District;
import com.nidoapp.nidopub.domain.geo.Province;

/**
 * Clase que representa un cliente
 * @author Developer
 *
 */
class Customer {
	
	//public static final String NOTIFICATION_MODE_SMS = "NOTIFICATION_MODE_SMS"
	//public static final String NOTIFICATION_MODE_EMAIL = "NOTIFICATION_MODE_EMAIL"
	
	String name
	String email
	String phone
	
	//String buyTicketsNotificationPreferenceMode = NOTIFICATION_MODE_SMS
	
	Country country
	Province province
	City city
	District district
	String address
	
	boolean receiveAdvertising = false
	//Ley Protección Datos
	boolean lopd = false
	
	static hasMany = [reviews: CustomerReview]

    static constraints = {
		phone nullable:true
		
		province nullable: true
		city nullable: true
		district nullable: true
		address nullable: true
    }
	
	def phoneWithCountryPrefix()
	{
		country.dialCode + phone
	}
	
	String toString() {
		"$name"
	}
}
