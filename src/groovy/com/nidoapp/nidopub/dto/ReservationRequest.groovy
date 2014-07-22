package com.nidoapp.nidopub.dto

import com.nidoapp.nidopub.domain.api.ApiKey
import java.util.Date;

/**
 * Encapsula datos de una peticion de reserva
 * @author Developer
 *
 */
class ReservationRequest {
	
	Integer pubId
	
	String customerName
	String customerEmail
	String customerPhone
	String customerPhoneCountryIsoCode
	
	Date reservationDate
	Integer adults
	Integer children
	
	String comments
	
	ApiKey sourceApiKey

}
