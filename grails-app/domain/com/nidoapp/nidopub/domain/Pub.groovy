package com.nidoapp.nidopub.domain

import com.nidoapp.nidopub.domain.geo.City
import com.nidoapp.nidopub.domain.geo.Country
import com.nidoapp.nidopub.domain.geo.District
import com.nidoapp.nidopub.domain.geo.Province
import com.nidoapp.nidopub.domain.user.User

class Pub {
	
	//public static final String NOTIFICATION_MODE_SMS = "NOTIFICATION_MODE_SMS"
	//public static final String NOTIFICATION_MODE_EMAIL = "NOTIFICATION_MODE_EMAIL"
	//public static final String NOTIFICATION_MODE_SMS_AND_EMAIL = "NOTIFICATION_MODE_SMS_AND_EMAIL"
	
	String name
	String uri
	String description
	String address
	Country country
	Province province
	City city
	District district
	String telephone
	String fax
	String email
	String website
	String specialty
	
	String notificationsEmail
	
	String logoImage
	
	//String reservationPhone
	//String reservationEmail
	
	//String reservationNotificationPreferenceMode = NOTIFICATION_MODE_SMS_AND_EMAIL
	
	//Double menuPriceFrom
	//Double menuPriceTo		
	Double cartePriceFrom
	Double cartePriceTo
	
	String openingHours //TODO: Mejor un calendario
	//String closingDays
	//String closingSpecial
	
	Double latitude
	Double longitude
	
	PubMusicType musicType
	
	
	Integer customerCapacity
	//Integer tableCapacity
	
	Carte carte
	
	User owner
	
	//Días apertura para permitir reservas
	Boolean LunesM = false
	Boolean LunesN = false
	Boolean MartesM = false
	Boolean MartesN = false
	Boolean MiercolesM = false
	Boolean MiercolesN = false
	Boolean JuevesM = false
	Boolean JuevesN = false
	Boolean ViernesM = false
	Boolean ViernesN = false
	Boolean SabadoM = false
	Boolean SabadoN = false
	Boolean DomingoM = false
	Boolean DomingoN = false
	
	//Número mínimo de comensales para reserva
	//Integer MinComensales
	
	
	//Reservas activas o no (USUARIO)
	//Boolean onlineReservations = false
	
	//Encuesta activa o no (USUARIO)
	Boolean inquiryActive = false
	
	
	//Servicios Nido-Pub (Activación)
	//Traducciones, cartas, eventos, encuesta y venta de entradas online
	//Boolean reservationsAvailable = false
	Boolean translationsAvailable = false
	Boolean cartesAvailable = false
	Boolean eventsAvailable = false
	Boolean inquiryAvailable = false
	Boolean sellTicketsOnlineAvailable = false
	
	//Fechas validez servicios activos
	Date translationsAvailableFrom
	Date translationsAvailableTo
	Date cartesAvailableFrom
	Date cartesAvailableTo
	Date eventsAvailableFrom
	Date eventsAvailableTo
	Date inquiryAvailableFrom
	Date inquiryAvailableTo
	Date sellTicketsOnlineAvailableFrom
	Date sellTicketsOnlineAvailableTo
	
	//Datos venta entradas online
	String businessName
	String contactPerson
	String contactTelephone
	String nif
	String invoiceAddress
	String iban
	Boolean sepa = false
	
	//Otros datos venta entradas online
	int ticketsSaleInitNumber
	Date ticketsSaleInitNumberReset
	
	//Configuración envío emails
	String mailAddress
	String mailHost
	int mailPort
	String mailUsername
	String mailPassword
	
	//Datos Configuración TPV Virtual
	String encryptionKey
	String merchantId
	String acquirerBIN
	String terminalId
	
	//Código de autenticación para Apps
	String appsAuthenticationCode = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6).toUpperCase()
	
	
	static belongsTo = [User, Association]
	
	static hasMany = [associations: Association, features: PubFeature, photos: PubPhoto, promotionalImages: PubPromotionalImage]

    static constraints = {
		description nullable:true
		telephone nullable: true
		//reservationPhone nullable: true
		fax nullable: true
		email nullable: true
		website nullable: true
		
		notificationsEmail nullable:true
		
			
		carte nullable: true
		associations nullable: true
		
		//reservationEmail nullable: true, email:true
		
		
		//TODO: Quitar estas restricciones
		address nullable: true
		uri nullable: true
		specialty nullable: true
		//menuPriceFrom nullable: true
		//menuPriceTo nullable: true
		cartePriceFrom nullable: true
		cartePriceTo nullable: true
		openingHours nullable: true
		//closingDays nullable: true
		//closingSpecial nullable: true
		latitude nullable: true
		longitude nullable: true
		province nullable: true
		city nullable: true
		district nullable: true
		musicType nullable: true
		customerCapacity nullable: true
		//tableCapacity nullable: true
		
		businessName nullable:true
		contactPerson nullable:true
		contactTelephone nullable:true
		nif nullable:true
		invoiceAddress nullable:true
		iban nullable:true
		
		mailAddress nullable: true
		mailHost nullable: true
		mailUsername nullable: true
		mailPassword nullable: true
		
		ticketsSaleInitNumberReset nullable:true
		
		translationsAvailableFrom nullable:true
		translationsAvailableTo nullable:true
		cartesAvailableFrom nullable:true
		cartesAvailableTo nullable:true
		eventsAvailableFrom nullable:true
		eventsAvailableTo nullable:true
		inquiryAvailableFrom nullable:true
		inquiryAvailableTo nullable:true
		sellTicketsOnlineAvailableFrom nullable:true
		sellTicketsOnlineAvailableTo nullable:true
		
		logoImage nullable:true
		
		encryptionKey nullable:true
		merchantId nullable:true
		acquirerBIN nullable:true
		terminalId nullable:true
		
		appsAuthenticationCode nullable:false
		
    }
	
	static mapping = {
		description type: 'text'
		specialty type: 'text'
	 }
	
	String toString() {
		"$name"
	}
	
	
	/*def reservationPhoneWithCountryPrefix()
	{
		country.dialCode + reservationPhone
	}*/
	
	
	boolean equals (Object object)
	{
		(object instanceof Pub) && (object.id == id)  
	}
}
