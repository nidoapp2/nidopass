package com.nidoapp.nidopub.controller.api

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.Date;

import com.nidoapp.nidopub.controller.CustomersController;
import com.nidoapp.nidopub.domain.AssociationNews;
import com.nidoapp.nidopub.domain.Carte;
import com.nidoapp.nidopub.domain.Customer
import com.nidoapp.nidopub.domain.PubAppsAuthenticationCode;
import com.nidoapp.nidopub.domain.TicketsSold
import com.nidoapp.nidopub.domain.PubEventPromotionalCode
import com.nidoapp.nidopub.domain.Ticket
import com.nidoapp.nidopub.domain.ProductValorations;
import com.nidoapp.nidopub.domain.Reservation
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.Association
import com.nidoapp.nidopub.domain.CarteSection
import com.nidoapp.nidopub.domain.Product
import com.nidoapp.nidopub.domain.CustomerReview
import com.nidoapp.nidopub.domain.PubFeature
import com.nidoapp.nidopub.domain.PubMusicType
import com.nidoapp.nidopub.domain.PubPhoto
import com.nidoapp.nidopub.domain.PubEvent;
import com.nidoapp.nidopub.domain.PubValorations;
import com.nidoapp.nidopub.domain.geo.City
import com.nidoapp.nidopub.domain.geo.Country
import com.nidoapp.nidopub.domain.geo.District
import com.nidoapp.nidopub.domain.geo.Province
import com.nidoapp.nidopub.domain.i18n.I18nItem;
import com.nidoapp.nidopub.domain.i18n.I18nLanguage;
import com.nidoapp.nidopub.domain.i18n.I18nTranslation;
import com.nidoapp.nidopub.domain.request.ServiceRequest
import com.nidoapp.nidopub.domain.request.RequestOrder
import com.nidoapp.nidopub.domain.request.CustomerRequest
import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.dto.ReservationRequest
import com.nidoapp.nidopub.domain.api.ApiKey;

import grails.plugins.springsecurity.Secured
import grails.converters.JSON
import groovy.json.JsonSlurper

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.CharSet;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.validator.EmailValidator

import sun.security.smartcardio.TerminalImpl;
import groovyx.net.http.*
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

/**
 * Controlador que implementa el API de la aplicacion
 * @author Developer
 *
 */
class Api1Controller {
	
	def grailsApplication
	
	/*
	def secureApiService
	
	def beforeInterceptor = {
		def isAuthorized = secureApiService.isAuthorizedRequest(request)
		if (isAuthorized == 200) {
			return true
		} else {
			render status: isAuthorized
			return false
		}
	}
	*/
	
	//def reservationService
	def countryDialingService
	def coordinatesRangeService
    def seoFriendlyUrlsService
	
	def mailingService
	def messageSource
	def templateService
	
	
	/**
	 * Servicio que muestra los distintos codigos diales de telefono en todos los paises
	 */
	def listDialCodes = {
		
		def response = [:]
		response.error = false
		
		response.codes = countryDialingService.getDialCodes()
		
		render getJsonOrJsonp(response, params)
	
	}
	
	/**
	 * Servicio que muestra todos los paises datos de alta en el sistema
	 */
	def getCountries = {
		
		def response = [:]
		response.error = false
		
		response.countries = Country.list(sort: "name", order: "asc")		
		
	
		render getJsonOrJsonp(response, params)
	
	}
	
	/**
	 * Servicio que muestra todos las provincias de un pais dadas de alta en el sistema
	 */
	def getProvinces = {
		
		def response = [:]
		response.error = false
		
		def countryId = params.id
		
		if(countryId)
		{
			def country = Country.get(countryId)
			
			if(country)
			{
				response.provinces = Province.findAllByCountry(country,  [sort: "name", order: "asc"])
				
			}
			else
			{
				response.error = true
				response.errorCode = "COUNTRY_NOT_FOUND"
			}
		}
		else
		{
			response.error = true
			response.errorCode = "MANDATORY_COUNTRY_ID"
		}
		
		
		render getJsonOrJsonp(response, params)
	
	}
	
	/**
	 * Servicio que muestra todos las ciudades o de una provincia concreta, dadas de alta en el sistema
	 */
	def getCities = {
		
		def response = [:]
		response.error = false
		
		
		def apiKeyString = params.key
		def provinceId = params.province
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def allowed = apiKey?.roleAny([ApiKey.ROLE_PUB_APP, ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		/*if(!response.error && !provinceId)
		{
			log.warn "Se requiere provincia."
			response.error = true
			response.errorCode = "MANDATORY_PROVINCE"
		}*/
		
		if(params.province) {
			def province = Province.get(provinceId)
			if(!response.error && !province)
			{
				log.warn "No se encuentra provincia."
				response.error = true
				response.errorCode = "PROVINCE_NOT_FOUND"
			}
		
		
			if(!response.error)
			{
				response.cities = City.findAllByProvince(province,  [sort: "name", order: "asc"])	
			}
		} else {
			
			if(!response.error)
			{
				response.cities = City.findAll([sort: "name", order: "asc"])
			}
		}
		
		render getJsonOrJsonp(response, params)
	
	}
	
	/**
	 * Servicio que muestra todos los barrios de una provincia dados de alta en el sistema
	 */
	def getDistricts = {
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def cityId = params.city
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def allowed = apiKey?.roleAny([ApiKey.ROLE_PUB_APP, ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && !cityId)
		{
			log.warn "Se requiere ciudad."
			response.error = true
			response.errorCode = "MANDATORY_CITY"
		}
		
		def city = City.get(cityId)
		if(!response.error && !city)
		{
			log.warn "No se encuentra ciudad."
			response.error = true
			response.errorCode = "CITY_NOT_FOUND"
		}
		
		if(!response.error)
		{
			response.districts = District.findAllByCity(city,  [sort: "name", order: "asc"])			
		}
		
		render getJsonOrJsonp(response, params)
	
	}
	
	
	/**
	 * Servicio que muestra todos los tipos de música dados de alta en el sistema
	 */
	def getMusicTypes = {
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def allowed = apiKey?.roleAny([ApiKey.ROLE_PUB_APP, ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		
		if(!response.error)
		{
			response.musictypes = PubMusicType.list(sort: "name", order: "asc")
		}			
			
		
		render getJsonOrJsonp(response, params)
	
	}
	
	
	/**
	 * Servicio que muestra todas las caracteristicas de pub dadas de alta
	 */
	def getPubFeatures = {
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def allowed = apiKey?.roleAny([ApiKey.ROLE_PUB_APP, ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error)
		{
			def features = PubFeature.list(sort: "name", order: "asc")
			response.features = []
			
			features.each{ feature ->
				response.features << [id: feature.id, code: feature.code, name: feature.name, imageUrl: createLink(absolute:true, controller:'resources', action: 'pubFeatureImage', params:[repositoryImageId: feature.repositoryImageId])]
			}
		}
			
		
		render getJsonOrJsonp(response, params)
	
	}
	
		
	
	/**
	 * Servicio que muestra resultados de autocompletar para nombres de Pubs
	 */
	def pubsAutocomplete = {
		
		def response = [:]
		response.error = false
		
	
		def apiKeyString = params.key
		def query = params.query
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def allowed = apiKey?.roleAny([ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && !query)
		{
			response.error = true
				response.errorCode = "NECESSARY_QUERY"
		}
		
		
		if(!response.error && query.length() < 3)
		{
			response.error = true
				response.errorCode = "QUERY_TOO_SHORT"
		}
		
		
		
		if(!response.error)
		{
	
			response.results = []
			
			def association = apiKey.association
			
			def pubs = Pub.withCriteria{
				ilike("name", "%"+query+"%")
				if(association)
				{
					associations {
						eq('id', association.id)
					}
				}
				order("name", "asc")
			} 
			
				 
			pubs.each{
				
				def preSearched
				def searched
				def posSearched
				
				def name = it.name
				
                def nameNormalized = seoFriendlyUrlsService.removeAccents(name)?.toUpperCase()
				log.debug "nameNormalized: " + nameNormalized
				
                def queryNormalized = seoFriendlyUrlsService.removeAccents(query)?.toUpperCase()
				log.debug "queryNormalized: " + queryNormalized
				
				def index = nameNormalized.indexOf(queryNormalized)
				
				preSearched = name.substring(0, index)
				searched = name.substring(index, index + query.length())
				posSearched = name.substring(index + query.length())
				
				def photoUrl
				def mainPhoto = PubPhoto.findByPubAndMain(it, true)
				
				if(mainPhoto)
				{
					photoUrl = createLink(absolute:true, controller:'resources', action: 'pubImage', params:[pubUri: it.uri, photoId: mainPhoto.id,repositoryImageId: mainPhoto.repositoryImageId])
				}
				else if(it.photos)
				{
					def firstPhoto = it.photos?.first()
					photoUrl = createLink(absolute:true, controller:'resources', action: 'pubImage', params:[pubUri: it.uri, photoId: firstPhoto.id,repositoryImageId: firstPhoto.repositoryImageId])
				}

				
				response.results << [preSearched: preSearched, searched: searched, posSearched: posSearched, photo: photoUrl, id: it.id, uri: it.uri]
			}

		}


		render getJsonOrJsonp(response, params)
	}
	
	

	
	
	/**
	 * Servicio que realiza una busqueda de Pubs
	 */
	def pubsSearch = {
		
		log.info "Servicio de busqueda de pubs..."
		log.debug "Parametros: " + params
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def query = params.query
		//def countryId = params.countryId
		def provinceId = params.provinceId
		def cityId = params.city
		def districtId = params.district?.toInteger()		
		def musicTypeId = params.musicType?.toInteger()		
		def distance = params.distance?.toDouble()
		def latitude = params.latitude?.toDouble()
		def longitude = params.longitude?.toDouble()
        def priceMax = params.priceMax?.toDouble()
        def priceMin = params.priceMin?.toDouble()
		
		def max = params.max?.toInteger()
		def first = params.first?.toInteger()
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		def allowed = apiKey?.roleAny([ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && distance != null && (latitude == null || longitude == null))
		{
			log.warn "Se necesitan coordenadas para calcular distancias."
			response.error = true
			response.errorCode = "NECESSARY_COORDINATES"
		}
		
		def province
		if(!response.error && provinceId != null)
		{
			province = Province.get(provinceId)
			if(!province)
			{
				log.warn "Provincia no encontrada."
				response.error = true
				response.errorCode = "PROVINCE_NOT_VALID"
			}
		}
		
		def city
		if(!response.error && cityId != null)
		{
			city = City.get(cityId)
			if(!city)
			{
				log.warn "Ciudad no encontrada."
				response.error = true
				response.errorCode = "CITY_NOT_VALID"
			}
		}
		
		
		def district
		if(!response.error && districtId != null)
		{
			district = District.get(districtId)
			if(!district)
			{
				log.warn "Barrio no encontrado."
				response.error = true
				response.errorCode = "DISTRICT_NOT_VALID"
			}
			else
			{
				if(city && district.city != city)
				{
					log.warn "Barrio no pertenece a la ciudad indicada."
					response.error = true
					response.errorCode = "DISTRICT_NOT_VALID_IN_CITY"
				}
			}
		}
		
		def musicType
		if(!response.error && musicTypeId != null)
		{
			musicType = PubMusicType.get(musicTypeId)
			if(!musicType)
			{
				log.warn "Tipo de música no encontrada."
				response.error = true
				response.errorCode = "MUSIC_TYPE_NOT_VALID"
			}
		}
        
        if(!response.error && priceMin != null && priceMin < 0)
		{
			log.warn "Precio minimo debe ser mayor que cero."
			response.error = true
			response.errorCode = "PRICE_MIN_MUST_BE_POSITIVE"
		}
        if(!response.error && priceMax != null && priceMax < 0)
		{
			log.warn "Precio maximo debe ser mayor que cero."
			response.error = true
			response.errorCode = "PRICE_MAX_MUST_BE_POSITIVE"
		}
        if(!response.error && priceMin != null && priceMax != null && priceMin > priceMax)
		{
			log.warn "Precio maximo debe ser mayor que precio minimo."
			response.error = true
			response.errorCode = "PRICE_MAX_MUST_BE_GREATER_THAN_PRICE_MIN"
		}
		
		
		if(!response.error)
		{
			log.info "Estableciendo criterio de busqueda: "
			
			def association = apiKey.association
			
			if(first)
			{
				log.info "Primer resultado: " + first
			}
			else
			{
				first = 0
			}
			
			
			if(max)
			{
				log.info "Numero maximo de resultados: " + max
			}
			else
			{
				def defaultMax = 500
				log.info "No se ha establecido maximo de resultados. Se establece " + defaultMax
				max = defaultMax
			}
			
			
			def pubs = Pub.createCriteria().list(max: max, offset: first){
				if(query)
				{
					log.info "Nombre, descripcion, o especialidad que contenga: " + query
					or
					{
						ilike("name", "%"+query+"%")
						ilike("description", "%"+query+"%")
						ilike("specialty", "%"+query+"%")
					}
				}
				
				if(distance)
				{
					log.info "A ${distance} metros de coordenadas ${latitude}, ${longitude}"
					
					def ranges =  coordinatesRangeService.getRange(latitude, longitude, distance)
					
					between("latitude", ranges.latmin, ranges.latmax)
					between("longitude", ranges.lonmin, ranges.lonmax)
				}
				
				if(province)
				{
					log.info "Provincia: " + province.name
					
					eq("province", province)
				}
				
				if(city)
				{
					log.info "Ciudad: " + city.name
					
					eq("city", city)
				}
				
				
				if(district)
				{
					log.info "Distrito: " + district.name
					
					eq("district", district)
				}
				
				if(musicType)
				{
					log.info "Tipo de música: " + musicType.name
					
					eq("musicType", musicType)
				}
                
                /*if(priceMin && priceMax)
				{
					log.info "Precio entre: " + priceMin + " y " + priceMax
					
					between("menuPriceFrom", priceMin, priceMax)
				}
                else if(priceMin)
                {
                    log.info "Precio mayor o igual que: " + priceMin
					
					ge("menuPriceFrom", priceMin)
                }
                else if(priceMax)
                {
                    log.info "Precio menor o igual que: " + priceMax
					
					//le("menuPriceFrom", priceMax)
					between("menuPriceFrom", new Double(0.1), priceMax)
                }*/
				
				if(association)
				{
					associations {
						eq('id', association.id)
					}
				}
				
				
				
				
				
				order("name", "asc")

								
			}
			
			
			response.pubs = []
			response.totalPubs = pubs.totalCount
			
			pubs.each{
				
				def pub = [:]
				
				pub.id = it.id
				
				pub.name = it.name
				pub.uri = it.uri
				pub.description = it.description
				
				def mainPhoto = PubPhoto.findByPubAndMain(it, true)
				
				if(mainPhoto)
				{
					pub.photo = [url: createLink(absolute:true, controller:'resources', action: 'pubImage', params:[pubUri: it.uri, photoId: mainPhoto.id,repositoryImageId: mainPhoto.repositoryImageId]), description: mainPhoto.description]
				}		
				else if(it.photos)
				{
					def firstPhoto = it.photos?.first()
					pub.photo = [url: createLink(absolute:true, controller:'resources', action: 'pubImage', params:[pubUri: it.uri, photoId: firstPhoto.id,repositoryImageId: firstPhoto.repositoryImageId]), description: firstPhoto.description]
				}
								
					
				if(it.province)
					pub.province = [id: it.province.id, name:it.province.name]
					
				if(it.city)
					pub.city = [id: it.city.id, name:it.city.name]
				
				
				if(it.district)
					pub.district = [id: it.district.id, name:it.district.name]
				
				pub.address = it.address
		
				pub.coordinates = [latitude: it.latitude, longitude:it.longitude]
				
				if (params.latitude && params.longitude && it.latitude && it.longitude) {
					pub.distance = calculateMapDistance(params.latitude, params.longitude, it.latitude, it.longitude)
				}
				
				pub.telephone = it.telephone
				
				pub.features = []
				it.features.each{ feature ->
					pub.features << [id: feature.id, code: feature.code, name: feature.name, imageUrl: createLink(absolute:true, controller:'resources', action: 'pubFeatureImage', params:[repositoryImageId: feature.repositoryImageId])]
				}
				
				pub.musicType = [id: it.musicType?.id, name: it.musicType?.name]
				
				
				pub.openingHours = it.openingHours
				
				//pub.reservationPhone = it.reservationPhone
				
				//pub.reservationEmail = it.reservationEmail
				
				//pub.onlineReservations = it.onlineReservations
				
				//pub.MinComensales = it.MinComensales
				
				pub.LunesM = it.LunesM
				pub.LunesN = it.LunesN
				pub.MartesM = it.MartesM
				pub.MartesN = it.MartesN
				pub.MiercolesM = it.MiercolesM
				pub.MiercolesN = it.MiercolesN
				pub.JuevesM = it.JuevesM
				pub.JuevesN = it.JuevesN
				pub.ViernesM = it.ViernesM
				pub.ViernesN = it.ViernesN
				pub.SabadoM = it.SabadoM
				pub.SabadoN = it.SabadoN
				pub.DomingoM = it.DomingoM
				pub.DomingoN = it.DomingoN
				
				pub.website = it.website
				
				def pubValorations = PubValorations.findAllByPub(it)
				def pubValoration = 0
				if(pubValorations.size()>0) {
					pubValorations.each {
						pubValoration = pubValoration+it.valoration
					}
					pubValoration = (int) Math.round(pubValoration/pubValorations.size())
				}
				pub.valoration = pubValoration
				
				/*def x1, y1, x2, y2
				
				double respuest;
				
				double res1,res2;
				
				x1=latitude
				y1=longitude
				x2=it.latitude
				y2=it.longitude
				
				res1=x2-x1;
				
				res2=y2-y1;
				
				res1=Math.pow(res1,2)+Math.pow(res2,2);
				
				respuest=Math.sqrt(res1);
				
				pub.distance = respuest*/
				
				
				response.pubs << pub
			
			}
		}
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	
	
	
	/**
	 * Servicio que devuelve el detalle de un Pub
	 */
	def pubDetail = {
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def pubId = params.pubId
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def pub = Pub.get(pubId)
		
		def allowed = allowedOperationOverPub(pub, apiKey)
		
		
		if(allowed)
		{
						
			if(pub)
			{
				response.pub = [:]
				
				response.pub.id = pub.id
				response.pub.name = pub.name
				response.pub.description = pub.description
				response.pub.specialty = pub.specialty
				
				response.pub.uri = pub.uri
				
				response.pub.musicType = [id: pub.musicType?.id, name: pub.musicType?.name]
				
				response.pub.features = []
				pub.features.each{ feature ->
					response.pub.features << [id: feature.id, name: feature.name, code: feature.code, imageUrl: createLink(absolute:true, controller:'resources', action: 'pubFeatureImage', params:[repositoryImageId: feature.repositoryImageId])]
				}
				
				response.pub.photos = []
				pub.photos.each{ photo ->
					response.pub.photos << [url: createLink(absolute:true, controller:'resources', action: 'pubImage', params:[pubUri: pub.uri, photoId: photo.id,repositoryImageId: photo.repositoryImageId]), description: photo.description]
				}
				
				def mainPhoto = PubPhoto.findByPubAndMain(pub, true)
				
				if(mainPhoto)
				{
					response.pub.photo = [url: createLink(absolute:true, controller:'resources', action: 'pubImage', params:[pubUri: pub.uri, photoId: mainPhoto.id,repositoryImageId: mainPhoto.repositoryImageId]), description: mainPhoto.description]
				}
				
				
				
				response.pub.email = pub.email				
				response.pub.telephone = pub.telephone
				response.pub.fax = pub.fax
				
				response.pub.address = pub.address
				
				if(pub.district)
					response.pub.district = [id: pub.district.id, name: pub.district.name]
				
				if(pub.city)
					response.pub.city = [id: pub.city.id, name: pub.city.name]
					
				if(pub.province)
					response.pub.province = [id: pub.province.id, name: pub.province.name]
				
				response.pub.coordinates = [latitude: pub.latitude, longitude:pub.longitude]
				
				response.pub.openingHours = pub.openingHours
				//response.pub.closingDays = pub.closingDays
				//response.pub.closingSpecial = pub.closingSpecial
					
				
				response.pub.cartePriceFrom = pub.cartePriceFrom
				
				//response.pub.reservationPhone = pub.reservationPhone
				//response.pub.reservationEmail = pub.reservationEmail
				//response.pub.onlineReservations = pub.onlineReservations
				
				//response.pub.MinComensales = pub.MinComensales
				
				response.pub.LunesM = pub.LunesM
				response.pub.LunesN = pub.LunesN
				response.pub.MartesM = pub.MartesM
				response.pub.MartesN = pub.MartesN
				response.pub.MiercolesM = pub.MiercolesM
				response.pub.MiercolesN = pub.MiercolesN
				response.pub.JuevesM = pub.JuevesM
				response.pub.JuevesN = pub.JuevesN
				response.pub.ViernesM = pub.ViernesM
				response.pub.ViernesN = pub.ViernesN
				response.pub.SabadoM = pub.SabadoM
				response.pub.SabadoN = pub.SabadoN
				response.pub.DomingoM = pub.DomingoM
				response.pub.DomingoN = pub.DomingoN
				
				response.pub.website = pub.website
				
				response.pub.events = PubEvent.findAllByPubAndDateEventGreaterThanEquals(pub, new Date()-1 )
				
				response.pub.carte = Carte.findByPub_idAndActive(pub.id, true)
				response.pub.carteSections = CarteSection.findAllByCarte(Carte.findByPub_idAndActive(pub.id, true))
				response.pub.carteSectionProducts = CarteSection.findAllByCarte(Carte.findByPub_idAndActive(pub.id, true)).products
				
				response.pub.inquiryActive = pub.inquiryActive
				
				//response.pub.reservationsAvailable = pub.reservationsAvailable
				response.pub.translationsAvailable = pub.translationsAvailable
				response.pub.cartesAvailable = pub.cartesAvailable
				response.pub.eventsAvailable = pub.eventsAvailable
				response.pub.inquiryAvailable = pub.inquiryAvailable
				response.pub.sellTicketsOnlineAvailable = pub.sellTicketsOnlineAvailable
				
				def pubValorations = PubValorations.findAllByPub(pub)
				def pubValoration = 0
				if(pubValorations.size()>0) {
					pubValorations.each {
						pubValoration = pubValoration+it.valoration
					}
					pubValoration = (int) Math.round(pubValoration/pubValorations.size())
				}
				response.pub.valoration = pubValoration
				
			}
			else
			{
				log.warn "No existe pub con id: ${pubId}"
				response.error = true
				response.errorCode = "PUB_NOT_FOUND"
			}
			
			
			
		}
		else
		{
			log.error "No tiene permisos"
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	
	
	/**
	 * Servicio que realiza una reserva de mesa en Pub
	 */
	/*def restaurantTableBook = {
		
		log.info "Servicio de reserva de mesa"
		log.info "Parametros: " + params
		
		def response = [:]
		response.error = false
		response.reservationNotAvailable = false
		
		def apiKeyString = params.key
		def restaurantId = params.restaurantId
		def reservationDateString = params.reservationDate
		def customerName = params.customerName
		def customerEmail = params.customerEmail
		def customerPhone = params.customerPhone
		def customerPhoneCountryIsoCode = params.customerPhoneCountryIsoCode
		def adults = params.adults
		def children = params.children
		def comments = params.comments
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def restaurant = Pub.get(restaurantId)
		
		def allowed = allowedOperationOverRestaurant(restaurant, apiKey)
		
		
		//
		// Validaciones:
		//
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && !restaurant)
		{
			response.error = true
			response.errorCode = "RESTAURANT_NOT_FOUND"
			log.warn "Restaurante no encontrado."
		}
		
		if(!response.error && (!restaurant.reservationPhone && !restaurant.reservationEmail))
		{
			response.error = true
			response.errorCode = "RESTAURANT_NOT_VALID_FOR_RESERVATION"
			log.warn "Restaurante no valido para reserva. No tiene ni email ni telefono movil de reservas."
		}
		
		if(!response.error && !reservationDateString)
		{
			response.error = true
			response.errorCode = "NECESSARY_RESERVATION_DATE_AND_HOUR"
			log.warn "Se necesita fecha y hora de reserva"
		}
		if(!response.error && (reservationDateString && !validReservationDateString(reservationDateString)))
		{
			response.error = true
			response.errorCode = "RESERVATION_DATE_AND_HOUR_NOT_VALID"
		}
		
		if(!response.error && !customerName)
		{
			response.error = true
			response.errorCode = "NECESSARY_CUSTOMER_NAME"
		}
		
		if(!response.error && !customerEmail)
		{
			response.error = true
			response.errorCode = "NECESSARY_EMAIL"
		}
		
		if(!response.error && customerEmail)
		{
			EmailValidator emailValidator = EmailValidator.getInstance()
			if(!emailValidator.isValid(customerEmail))
			{
				log.warn "Email incorrecto:" + customerEmail
				response.error = true
				response.errorCode = "EMAIL_NOT_VALID"
			}
		}
		
		if(!response.error && !customerPhone)
		{
			response.error = true
			response.errorCode = "NECESSARY_PHONE"
		}
		
		if(!response.error && !customerPhone.matches("6[0-9]{8}"))
		{
			response.error = true
			response.errorCode = "PHONE_NOT_VALID"
		}
		
		if(!response.error && !customerPhoneCountryIsoCode)
		{
			response.error = true
			response.errorCode = "NECESSARY_PHONE_COUNTRY_ISO_CODE"
		}
		
		if(!response.error && !adults)
		{
			response.error = true
			response.errorCode = "NECESSARY_ADULTS_NUMBER"
		}
		
		if(!response.error && !children)
		{
			response.error = true
			response.errorCode = "NECESSARY_CHILDREN_NUMBER"
		}
		
		if(!response.error)
		{
			try
			{
				def total = new Integer(adults) +  new Integer(children)
				if(total <= 0)
				{
					response.error = true
					response.errorCode = "NECESSARY_ADULTS_AND_CHILDREN_POSITIVE_NUMBER"
				}
			}
			catch(Exception e)
			{
				response.error = true
				response.errorCode = "NECESSARY_ADULTS_AND_CHILDREN_POSITIVE_NUMBER"
			}
			
		}
		
		
		
		//
		// Validación petición de reserva ya realizada
		//
		if(!response.error)
		{
			def customer = Customer.findByPhoneOrEmail(customerPhone, customerEmail)
			
			if(customer)
			{
				def minReservationDate
				def maxReservationDate
				def reservationDate = Date.parse("dd/MM/yyyy HH:mm", reservationDateString)
				
				use(groovy.time.TimeCategory) {
								
					minReservationDate = reservationDate - 3.hours
					maxReservationDate = reservationDate + 3.hours
				}
				def reservations = Reservation.createCriteria().list(max: 1){
					
					eq("customer", customer)					
					between("reservationDate", minReservationDate, maxReservationDate)
					
				}
				
				if(reservations)
				{
					response.error = true
					response.errorCode = "RESERVATION_ALREADY_DONE"
				}
				
				
				
			}
			
			
		}
		
		
		
		if(!response.error)
		{
			def reservationRequest = new ReservationRequest()
			reservationRequest.restaurantId = restaurant.id
			reservationRequest.customerName = customerName
			reservationRequest.customerEmail = customerEmail
			reservationRequest.customerPhone = customerPhone
			reservationRequest.customerPhoneCountryIsoCode = customerPhoneCountryIsoCode
			reservationRequest.reservationDate = Date.parse("dd/MM/yyyy HH:mm", reservationDateString)
			reservationRequest.adults = new Integer(adults)
			reservationRequest.children = new Integer(children)
			reservationRequest.comments = comments
			reservationRequest.sourceApiKey = apiKey
	
		
			def reservation = reservationService.makeReservation(reservationRequest)
	
			if(reservation && reservation!=false) {
				log.info "Reserva realizada correctamente"
				response.reservationId = reservation.id
			}
			else if(reservation==false) {
				log.info "Se ha producido un error durante la reserva porque el restaurante cierra ese día"
				response.error = true
				response.reservationNotAvailable = true
				response.errorCode = "RESERVATION_ERROR"
			}
			else {
				log.info "Se ha producido un error durante la reserva"
				response.error = true
				response.errorCode = "RESERVATION_ERROR"
			}
		}
		
		
		render getJsonOrJsonp(response, params)
		
	
	}*/
	
	/**
	 * Metodo privado que comprueba si la fecha indicada como String es valida
	 * @param reservationDateString fecha en formato string
	 * @return
	 */
	/*def private validReservationDateString(reservationDateString)
	{
		def valid = false
		try
		{
			def date = Date.parse("dd/MM/yyyy HH:mm", reservationDateString)
			
			if(date < new Date())
				valid = false
			else
				valid = true
		}
		catch(Exception e)
		{
			valid = false
		}
		
		valid
		
	}*/
	
	
	
	/**
	 * Servicio que ejecuta la resolucion de una peticion de reserva
	 */
	/*def restaurantReservationResolution = {
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def reservationId = params.reservationId
		def confirmed = new Boolean(params.confirmed)
		
		log.info "Obteniendo Reserva con ID: " + reservationId
		
		def reservation = Reservation.get(reservationId)
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def allowed = allowedOperationOverRestaurant(reservation?.restaurant, apiKey)
		
				
		if(allowed)
		{
			if(reservation)
			{
				if(confirmed != null)
				{
					reservationService.processInProgressReservation(reservation, confirmed)
					log.info "Respuesta de solicitud reserva realizada correctamente."
				}
				else
				{
					log.error "No se ha indicado si la reserva ha sido confirmada o denegada."
					response.error = true
					response.errorCode = "RESERVATION_NOT_CONFIRMED_OR_DENIED"
				}
				
			}
			else
			{
				log.error "No existe reserva con ID: " + reservationId
				response.error = true
				response.errorCode = "RESERVATION_NOT_FOUND"
			}
		}
		else
		{
			log.error "No tiene permisos para realizar la operacion."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
				
		
		
		
		render getJsonOrJsonp(response, params)
	
	}*/
	
	
	
	
	/**
	 * Servicio que permite cambiar las preferencias de notificacion de reservas
	 */
	/*def changeRestaurantReservationNotificationSettings = {
		
		log.info "Servicio de configuracion de notificacion de reservas de restaurente..."
		log.info "Parametros: " + params
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def restaurantId = params.restaurant
		def notificationMode = params.notificationMode
		def reservationEmail = params.reservationEmail
		def reservationPhone = params.reservationPhone
		
		
		
		//
		// Validaciones:
		//
		log.info "Obteniendo Restaurante con ID: " + restaurantId
		def restaurant = Pub.get(restaurantId)
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		def allowed = allowedOperationOverRestaurant(restaurant, apiKey)
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		
		if(!response.error && !restaurant)
		{
			response.error = true
			response.errorCode = "RESTAURANT_NOT_FOUND"
			log.warn "Restaurante con ID '${restaurantId}' no encontrado."
		}
		
		if(!response.error && !reservationEmail && !reservationPhone && !notificationMode)
		{
			log.warn "No se ha indicado nueva configuracion de notificacion."
			response.error = true
			response.errorCode = "NOTIFICATION_SETTINGS_MISSING"
		}
		
		if(!response.error && reservationEmail)
		{
			EmailValidator emailValidator = EmailValidator.getInstance()
			if(!emailValidator.isValid(reservationEmail))
			{
				log.warn "Email incorrecto:" + reservationEmail
				response.error = true
				response.errorCode = "EMAIL_NOT_VALID"
			}
		}
		
		if(!response.error && notificationMode)
		{
			if(Pub.NOTIFICATION_MODE_SMS != notificationMode && Pub.NOTIFICATION_MODE_EMAIL != notificationMode)
			{
				log.warn "Modo de notificacion incorrecto:" + notificationMode
				response.error = true
				response.errorCode = "NOTIFICATION_MODE_NOT_VALID"
			}
		}
		
		
		if(!response.error)
		{
			if(reservationEmail)
			{
				log.info "Estableciendo email de notificacion de reservas: " + reservationEmail

				restaurant.reservationEmail = reservationEmail
			}

			if(reservationPhone)
			{
				log.info "Estableciendo telefono movil de notificacion de reservas: " + reservationPhone
				restaurant.reservationPhone = reservationPhone
			}

			if(notificationMode)
			{
				log.info "Estableciendo modo de notificacion: " + notificationMode
				restaurant.reservationNotificationPreferenceMode = notificationMode

			}

			log.info "Registrando nuevos datos de notificacion de reservas..."
			restaurant.save(flush:true, failOnError:true)
		}

		
		
		render getJsonOrJsonp(response, params)
	
	}*/
	
	
	
	/**
	 * Metodo privado que comprueba si mediante una clave de API se pueden realizar operaciones sobre un pub
	 * @param pub, pub sobre el que se pretende realizar operaciones
	 * @param apiKey clave de API
	 * @return si se permite o no la operacion
	 */
	def private allowedOperationOverPub(pub, apiKey)
	{
		def allowed
		
		if(apiKey && apiKey.roleAny([ApiKey.ROLE_PUB_APP, ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_SUPERADMIN]))
		{
			if(apiKey.role == ApiKey.ROLE_PUB_APP && apiKey.pub && apiKey.pub == pub)
			{
				log.info "La invocacion la realiza una aplicacion de sala."
				allowed = true
			}
			else if(apiKey.role == ApiKey.ROLE_ASSOCIATION_APP && apiKey.association)
			{
				log.info "La invocacion la realiza una aplicacion de asociacion. Se comprueba si la sala pertenece a la asociacion."
				
				if(pub && pub.associations?.contains(apiKey.association))
				{
					allowed = true
				}
				else
				{
					log.warn "La sala ${pub?.name} (id: ${pub?.id})  no pertenece a la asociacion ${apiKey.association?.name} (id: ${apiKey.association?.id})"
				}
			}
			else if(apiKey.role == ApiKey.ROLE_SUPERADMIN)
			{
				log.info "La invocacion la realiza una aplicacion con permisos de superadministrador. Se selecciona sala indicada."
				allowed = true
				
			}
		}
		
		allowed
	}
	
	
	
	/*
	 * OTROS SERVICIOS PARA SEGUNDA FASE:
	 */
	
	
	
	
	/*@Secured([Role.ROLE_SUPERADMIN])
	def getMenus = {
		
		def response = [:]
		response.error = false
		
	
		def restaurantId = params.restaurantId
		
		log.info "Obteniendo menus del restaurante con ID: " + restaurantId
		
		def restaurant = Pub.get(restaurantId)
		
				
		if(restaurant)
		{
			response.menus = restaurant.menus
		}
		else
		{
			response.error = true
			response.errorCode = "RESTAURANT_NOT_FOUND"
		}	
		
		
		render response as JSON
	
	}
	
	@Secured([Role.ROLE_SUPERADMIN])
	def getMenuSections = {
		
		def response = [:]
		response.error = false
		
		def menuId = params.id
		
		log.info "Obteniendo las secciones del menue con ID: " + menuId
		
		def menu = Menu.get(menuId)
		
				
		if(menu)
		{
			response.sections = menu.sections
		}
		else
		{
			response.error = true
			response.errorCode = "MENU_NOT_FOUND"
		}
		
		
		render response as JSON
	
	}
	
	@Secured([Role.ROLE_SUPERADMIN])
	def getMenuSectionPlates = {
		
		def response = [:]
		response.error = false
		
		def menuSectionId = params.id
		
		log.info "Obteniendo los platos de la seccion de menu con ID: " + menuSectionId
		
		def menuSection = MenuSection.get(menuSectionId)
		
				
		if(menuSection)
		{
			response.plates = menuSection.plates
		}
		else
		{
			response.error = true
			response.errorCode = "MENU_SECTION_NOT_FOUND"
		}
		
		
		render response as JSON
	
	}*/
	
	@Secured([Role.ROLE_SUPERADMIN])
	def getCarteSections = {
		
		def response = [:]
		response.error = false
		
		def pubId = params.id
		
		log.info "Obteniendo las secciones de la carta del pub con ID: " + pubId
		
		def carte = Pub.get(pubId)?.carte
		
				
		if(carte)
		{
			response.sections = carte.sections
		}
		else
		{
			response.error = true
			response.errorCode = "CARTE_NOT_FOUND"
		}
		
		
		render response as JSON
	
	}
	
	@Secured([Role.ROLE_SUPERADMIN])
	def getCarteSectionProducts = {
		
		def response = [:]
		response.error = false
		
		def carteSectionId = params.id
		
		log.info "Obteniendo los productos de la seccion de carta con ID: " + carteSectionId
		
		def carteSection = CarteSection.get(carteSectionId)
		
				
		if(carteSection)
		{
			response.products = carteSection.products
		}
		else
		{
			response.error = true
			response.errorCode = "CARTE_SECTION_NOT_FOUND"
		}
		
		
		render response as JSON
	
	}
	
	@Secured([Role.ROLE_SUPERADMIN])
	def getProduct = {
		
		def response = [:]
		response.error = false
		
		def productId = params.id
		
		log.info "Obteniendo producto con ID: " + productId
		
		def product = Product.get(productId)
		
				
		if(product)
		{
			response.product = product
			response.reviews = CustomerReview.findAllByProduct(product)
		}
		else
		{
			response.error = true
			response.errorCode = "PRODUCT_NOT_FOUND"
		}
		
		
		render response as JSON
	
	}
	
	@Secured([Role.ROLE_SUPERADMIN])
	def listPendingRequests = {
		
		def response = [:]
		response.error = false
		
		
		log.info "Obteniendo lista de pedidos pendientes..."
		
		def pendingRequests = ServiceRequest.findAllByStateInList([ServiceRequest.STATE_PENDING, ServiceRequest.STATE_PROCESSING])
		
		response.pendingRequests = pendingRequests
				
		
		render response as JSON
	
	}
	
	@Secured([Role.ROLE_SUPERADMIN])
	def listPendingOrders = {
		
		def response = [:]
		response.error = false
		
		
		log.info "Obteniendo lista de comandas pendientes..."
		
		//def pendingOrders = RequestOrder.findAllByStateInList([RequestOrder.STATE_PENDING, RequestOrder.STATE_PROCESSING])
		def pendingOrders = RequestOrder.list()
		
		response.pendingOrders = pendingOrders
				
		
		render response as JSON
	
	}
	
	@Secured([Role.ROLE_SUPERADMIN])
	def getCustomerRequest = {
		
		def response = [:]
		response.error = false
		
		def customerRequestId = params.id
		
		log.info "Obteniendo peticion de cliente con ID: " + customerRequestId
		
		def customerRequest = CustomerRequest.get(customerRequestId)
		
				
		if(customerRequest)
		{
			response.customerRequest = customerRequest
		}
		else
		{
			response.error = true
			response.errorCode = "CUSTOMER_REQUEST_NOT_FOUND"
		}
		
		
		render response as JSON
	
	}
	
	/*@Secured([Role.ROLE_SUPERADMIN])
	def getMenuRequest = {
		
		def response = [:]
		response.error = false
		
		def menuRequestId = params.id
		
		log.info "Obteniendo peticion de menu con ID: " + menuRequestId
		
		def menuRequest = MenuRequest.get(menuRequestId)
		
				
		if(menuRequest)
		{
			response.menuRequest = menuRequest
		}
		else
		{
			response.error = true
			response.errorCode = "MENU_REQUEST_NOT_FOUND"
		}
		
		
		render response as JSON
	
	}*/
	
	@Secured([Role.ROLE_SUPERADMIN])
	def getI18nItemTranslation = {
		
		def response = [:]
		response.error = false
		
		def itemId = params.id
		def langIsoCode = params.lang
		
		log.info "Traduccion del item '${itemId}' en lenguaje: " + langIsoCode
		
		def item = I18nItem.get(itemId)	
		
				
		if(item)
		{

			def translation = I18nTranslation.findByItemAndLang(item,I18nLanguage.findByIsoCode(langIsoCode))
			
			if(translation)
			{				
				response.text = translation.text
			}			
			else
			{
				response.text = item.defaultText
			}

			
			
		}
		else
		{
			response.error = true
			response.errorCode = "ITEM_NOT_FOUND"
		}
		
		
		render response as JSON
	
	}
	
	
	/**
	 * Metodo privado que pemite tanto respuesta JSON como JSONP
	 * @param response
	 * @param params
	 * @return
	 */
	def private getJsonOrJsonp(response, params)
	{
		def jsonOrJsonp
		
		if(params.callback) //JSONP
		{
			jsonOrJsonp = "${params.callback}(${response as JSON})"
		}
		else //JSON
		{
			jsonOrJsonp = response as JSON
		}
		
		jsonOrJsonp
	}
	
	/**
	 * Servicicio que realiza una busqueda de Productos de un Pub
	 */
	def pubProductsSearch = {
		
		log.info "Servicio de busqueda de productos de un pub..."
		log.debug "Parametros: " + params
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def query = params.query
		//def countryId = params.countryId
		//def provinceId = params.provinceId
		def pubId = Long.parseLong(params.pubId)
		
		
		def max = params.max?.toInteger()
		def first = params.first?.toInteger()
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		def allowed = apiKey?.roleAny([ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_PUB_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && pubId == null)
		{
			log.warn "Se necesita indicar un pub para buscar los productos."
			response.error = true
			response.errorCode = "NECESSARY_PUB"
		}
		
		
		if(!response.error)
		{
			log.info "Estableciendo criterio de busqueda: "
			
			def association = apiKey.association
			
			if(first)
			{
				log.info "Primer resultado: " + first
			}
			else
			{
				first = 0
			}
			
			
			if(max)
			{
				log.info "Numero maximo de resultados: " + max
			}
			else
			{
				def defaultMax = 500
				log.info "No se ha establecido maximo de resultados. Se establece " + defaultMax
				max = defaultMax
			}
			
			
			def products = Product.createCriteria().list(max: max, offset: first){
				if(query)
				{
					log.info "Productos del pub que contenga: " + query
					or
					{
						ilike("name", "%"+query+"%")
						ilike("reference", "%"+query+"%")
						
					}
				}
				
				
				if(pubId)
				{
					log.info "Pub: " + Pub.findById(pubId).name;
					
					eq("pub_id", pubId)
				}
				
				/*if(association)
				{
					associations {
						eq('id', association.id)
					}
				}*/
				
				
				
				
				
				order("name", "asc")

								
			}
			
			
			response.products = []
			response.totalProducts = products.totalCount
			
			products.each{
				
				def product = [:]
				
				product.id = it.id
				
				product.name = it.name
				product.reference = it.reference
				
				
				response.products << product
			
			}
		}
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	/**
	 * Servicio que devuelve el detalle de un Producto
	 */
	def productDetail = {
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def productId = params.productId
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def product = Product.get(productId)
		
		//def allowed = allowedOperationOverPub(product, apiKey)
		
		
		/*if(allowed)
		{*/
						
			if(product)
			{
				response.product = [:]
				
				response.product.id = product.id
				response.plaproductte.name = product.name
				response.product.reference = product.reference
				response.product.description = product.description
				response.product.ingredients = product.ingredients
				response.product.allergens = product.allergens
				response.pproductlate.youtubeRecipe = product.youtubeRecipe
				
				def productValorations = ProductValorations.findAllByProduct(product)
				def productValoration = 0
				if(productValorations.size()>0) {
					productValorations.each {
						productValoration = productValoration+it.valoration
					}
					productValoration = (int) Math.round(productValoration/productValorations.size())
				}
				response.product.valoration = productValoration
				
				
				
				def photo = product.photo
				
				if(photo)
				{
					response.product.photo = [url: createLink(absolute:true, controller:'resources', action: 'productImage', params:[product_id: product.id])]
				}
				
			}
			else
			{
				log.warn "No existe producto con id: ${productId}"
				response.error = true
				response.errorCode = "PRODUCT_NOT_FOUND"
			}
			
			
			
		/*}
		else
		{
			log.error "No tiene permisos"
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}*/
		
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	
	
	/**
	 * Servicicio que realiza una busqueda de Cartas de un Pub
	 */
	def pubCartesSearch = {
		
		log.info "Servicio de busqueda de cartas de un pub..."
		log.debug "Parametros: " + params
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def query = params.query
		//def countryId = params.countryId
		//def provinceId = params.provinceId
		def pubId = Long.parseLong(params.pubId)
		
		
		def max = params.max?.toInteger()
		def first = params.first?.toInteger()
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		def allowed = apiKey?.roleAny([ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_PUB_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && pubId == null)
		{
			log.warn "Se necesita indicar un pub para buscar las cartas."
			response.error = true
			response.errorCode = "NECESSARY_PUB"
		}
		
		
		if(!response.error)
		{
			log.info "Estableciendo criterio de busqueda: "
			
			def association = apiKey.association
			
			if(first)
			{
				log.info "Primer resultado: " + first
			}
			else
			{
				first = 0
			}
			
			
			if(max)
			{
				log.info "Numero maximo de resultados: " + max
			}
			else
			{
				def defaultMax = 500
				log.info "No se ha establecido maximo de resultados. Se establece " + defaultMax
				max = defaultMax
			}
			
			
			def cartes = Carte.createCriteria().list(max: max, offset: first){
				if(query)
				{
					log.info "Cartas del pub que contenga: " + query
					or
					{
						ilike("name", "%"+query+"%")
						//ilike("reference", "%"+query+"%")
						
					}
				}
				
				
				if(pubId)
				{
					log.info "Pub: " + Pub.findById(pubId).name;
					
					eq("pub_id", pubId)
				}
				
				/*if(association)
				{
					associations {
						eq('id', association.id)
					}
				}*/
				
				
				
				
				
				order("name", "asc")

								
			}
			
			
			response.cartes = []
			response.totalCartes = cartes.totalCount
			
			cartes.each{
				
				def carte = [:]
				
				carte.id = it.id
				
				carte.name = it.name
				//carte.reference = it.reference
				/*plate.description = it.description
				plate.ingredients = it.ingredients
				plate.allergens = it.allergens
				plate.youtubeRecipe = it.youtubeRecipe
				plate.photo = it.photo
				plate.price = it.price
				plate.available = it.available*/
				
				/*def photo = RestaurantPhoto.findByRestaurantAndMain(it, true)
				
				if(mainPhoto)
				{
					restaurant.photo = [url: createLink(absolute:true, controller:'resources', action: 'restaurantImage', params:[restaurantUri: it.uri, photoId: mainPhoto.id,repositoryImageId: mainPhoto.repositoryImageId]), description: mainPhoto.description]
				}
				else if(it.photos)
				{
					def firstPhoto = it.photos?.first()
					restaurant.photo = [url: createLink(absolute:true, controller:'resources', action: 'restaurantImage', params:[restaurantUri: it.uri, photoId: firstPhoto.id,repositoryImageId: firstPhoto.repositoryImageId]), description: firstPhoto.description]
				}*/
				
				
				response.cartes << carte
			
			}
		}
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	/**
	 * Servicio que devuelve el detalle de una Carta
	 */
	def carteDetail = {
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def carteId = params.carteId
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def carte = Carte.get(carteId)
		
		//def allowed = allowedOperationOverRestaurant(plate, apiKey)
		
		
		/*if(allowed)
		{*/
						
			if(carte)
			{
				response.carte = [:]
				
				response.carte.id = carte.id
				response.carte.name = carte.name
				response.carte.description = carte.description
				response.carte.sections = carte.sections
				
				
				/*def plateValorations = PlateValorations.findAllByPlate(plate)
				def plateValoration = 0
				if(plateValorations.size()>0) {
					plateValorations.each {
						plateValoration = plateValoration+it.valoration
					}
					plateValoration = (int) Math.round(plateValoration/plateValorations.size())
				}
				response.plate.valoration = plateValoration*/
				
				
				/*response.restaurant.foodType = [id: restaurant.foodType?.id, name: restaurant.foodType?.name]
				
				response.restaurant.features = []
				restaurant.features.each{ feature ->
					response.restaurant.features << [id: feature.id, name: feature.name, code: feature.code, imageUrl: createLink(absolute:true, controller:'resources', action: 'restaurantFeatureImage', params:[repositoryImageId: feature.repositoryImageId])]
				}*/
				
				/*def photo = plate.photo
				
				if(photo)
				{
					response.plate.photo = [url: createLink(absolute:true, controller:'resources', action: 'plateImage', params:[plate_id: plate.id])]
				}*/
				
			}
			else
			{
				log.warn "No existe carta con id: ${carteId}"
				response.error = true
				response.errorCode = "CARTE_NOT_FOUND"
			}
			
			
			
		/*}
		else
		{
			log.error "No tiene permisos"
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}*/
		
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	/**
	 * Servicio que realiza una valoración de un Producto
	 */
	def sendProductValoration = {
		
		log.info "Servicio de valoración de producto"
		log.info "Parametros: " + params
		
		def response = [:]
		response.error = false
		//response.reservationNotAvailable = false
		
		def apiKeyString = params.key
		def productId = params.productId
		def valoration = Integer.parseInt(params.valoration)
		def comment = params.comment
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def product = Product.get(productId)
		
		//def allowed = allowedOperationOverRestaurant(restaurant, apiKey)
		
		
		/*
		 * Validaciones:
		 */
		
		/*if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}*/
		
		if(!response.error && !product)
		{
			response.error = true
			response.errorCode = "PRODUCT_NOT_FOUND"
			log.warn "Producto no encontrado."
		}
		
		/*if(!response.error && (!restaurant.reservationPhone && !restaurant.reservationEmail))
		{
			response.error = true
			response.errorCode = "RESTAURANT_NOT_VALID_FOR_RESERVATION"
			log.warn "Restaurante no valido para reserva. No tiene ni email ni telefono movil de reservas."
		}
		
		if(!response.error && !reservationDateString)
		{
			response.error = true
			response.errorCode = "NECESSARY_RESERVATION_DATE_AND_HOUR"
			log.warn "Se necesita fecha y hora de reserva"
		}
		if(!response.error && (reservationDateString && !validReservationDateString(reservationDateString)))
		{
			response.error = true
			response.errorCode = "RESERVATION_DATE_AND_HOUR_NOT_VALID"
		}
		*/
		if(!response.error && !valoration)
		{
			response.error = true
			response.errorCode = "NECESSARY_VALORATION"
		}
		
		/*if(!response.error && !customerEmail)
		{
			response.error = true
			response.errorCode = "NECESSARY_EMAIL"
		}
		
		if(!response.error && customerEmail)
		{
			EmailValidator emailValidator = EmailValidator.getInstance()
			if(!emailValidator.isValid(customerEmail))
			{
				log.warn "Email incorrecto:" + customerEmail
				response.error = true
				response.errorCode = "EMAIL_NOT_VALID"
			}
		}
		
		if(!response.error && !customerPhone)
		{
			response.error = true
			response.errorCode = "NECESSARY_PHONE"
		}
		
		if(!response.error && !customerPhone.matches("6[0-9]{8}"))
		{
			response.error = true
			response.errorCode = "PHONE_NOT_VALID"
		}
		
		if(!response.error && !customerPhoneCountryIsoCode)
		{
			response.error = true
			response.errorCode = "NECESSARY_PHONE_COUNTRY_ISO_CODE"
		}
		
		if(!response.error && !adults)
		{
			response.error = true
			response.errorCode = "NECESSARY_ADULTS_NUMBER"
		}
		
		if(!response.error && !children)
		{
			response.error = true
			response.errorCode = "NECESSARY_CHILDREN_NUMBER"
		}*/
		
		/*if(!response.error)
		{
			try
			{
				def total = new Integer(adults) +  new Integer(children)
				if(total <= 0)
				{
					response.error = true
					response.errorCode = "NECESSARY_ADULTS_AND_CHILDREN_POSITIVE_NUMBER"
				}
			}
			catch(Exception e)
			{
				response.error = true
				response.errorCode = "NECESSARY_ADULTS_AND_CHILDREN_POSITIVE_NUMBER"
			}
			
		}*/
		
		/*
		 * Validación valoración Plato ya realizada
		 */
		/*if(!response.error)
		{
			def customer = Customer.findByPhoneOrEmail(customerPhone, customerEmail)
			
			if(customer)
			{
				def minReservationDate
				def maxReservationDate
				def reservationDate = Date.parse("dd/MM/yyyy HH:mm", reservationDateString)
				
				use(groovy.time.TimeCategory) {
								
					minReservationDate = reservationDate - 3.hours
					maxReservationDate = reservationDate + 3.hours
				}
				def reservations = Reservation.createCriteria().list(max: 1){
					
					eq("customer", customer)
					between("reservationDate", minReservationDate, maxReservationDate)
					
				}
				
				if(reservations)
				{
					response.error = true
					response.errorCode = "RESERVATION_ALREADY_DONE"
				}
				
				
				
			}
			
			
		}*/
		
		
		
		if(!response.error)
		{
			def productValoration = new ProductValorations()
			productValoration.product = product
			productValoration.valoration = valoration
			productValoration.comment = comment
			
			//reservationRequest.sourceApiKey = apiKey
	
	
			if(productValoration.save(flush:true)) {
				log.info "Valoración realizada correctamente"
				response.productId = product.id
			}
			else {
				log.info "Se ha producido un error durante la valoración"
				response.error = true
				response.errorCode = "VALORATION_ERROR"
			}
		}
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	/**
	 * Servicio que devuelve la media de la valoración de un Producto
	 */
	def productAverageValoration = {
		
		log.info "Servicio de media de valoración de producto..."
		log.debug "Parametros: " + params
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def query = params.query
		//def countryId = params.countryId
		//def provinceId = params.provinceId
		def productId = Long.parseLong(params.productId)
		
		
		def max = params.max?.toInteger()
		def first = params.first?.toInteger()
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		def allowed = apiKey?.roleAny([ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_PUB_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && productId == null)
		{
			log.warn "Se necesita indicar un producto para poder obtener su valoración media."
			response.error = true
			response.errorCode = "NECESSARY_PRODUCT"
		}
		
		
		if(!response.error)
		{
			log.info "Estableciendo criterio de busqueda: "
			
			def association = apiKey.association
			
			if(first)
			{
				log.info "Primer resultado: " + first
			}
			else
			{
				first = 0
			}
			
			
			if(max)
			{
				log.info "Numero maximo de resultados: " + max
			}
			else
			{
				def defaultMax = 500
				log.info "No se ha establecido maximo de resultados. Se establece " + defaultMax
				max = defaultMax
			}
			
			
			def products = Product.createCriteria().list(max: max, offset: first){
				if(query)
				{
					log.info "Productos que contenga: " + query
					or
					{
						ilike("name", "%"+query+"%")
						ilike("reference", "%"+query+"%")
						
					}
				}
				
				
				if(productId)
				{
					log.info "Producto: " + Product.findById(productId).name;
					
					eq("product_id", productId)
				}
				
				/*if(association)
				{
					associations {
						eq('id', association.id)
					}
				}*/
				
				
				
				
				
				order("name", "asc")

								
			}
			
			
			response.products = []
			response.totalProducts = products.totalCount
			
			products.each{
				
				def product = [:]
				
				product.id = it.id
				
				product.name = it.name
				product.reference = it.reference
				/*product.description = it.description
				product.ingredients = it.ingredients
				product.allergens = it.allergens
				product.youtubeRecipe = it.youtubeRecipe
				product.photo = it.photo
				product.price = it.price
				product.available = it.available*/
				
				/*def photo = PubPhoto.findByPubAndMain(it, true)
				
				if(mainPhoto)
				{
					pub.photo = [url: createLink(absolute:true, controller:'resources', action: 'pubImage', params:[pubUri: it.uri, photoId: mainPhoto.id,repositoryImageId: mainPhoto.repositoryImageId]), description: mainPhoto.description]
				}
				else if(it.photos)
				{
					def firstPhoto = it.photos?.first()
					pub.photo = [url: createLink(absolute:true, controller:'resources', action: 'pubImage', params:[pubUri: it.uri, photoId: firstPhoto.id,repositoryImageId: firstPhoto.repositoryImageId]), description: firstPhoto.description]
				}*/
				
				
				response.products << product
			
			}
		}
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	
	/**
	 * Servicio que realiza una valoración de un Pub
	 */
	def sendPubValoration = {
		
		log.info "Servicio de valoración de pub"
		log.info "Parametros: " + params
		
		def response = [:]
		response.error = false
		//response.reservationNotAvailable = false
		
		def apiKeyString = params.key
		def pubId //= params.pubId
		if (params.pubId) {
			if (params.pubId.getClass().getName() == 'String') {
				pubId = Long.parseLong(params.pubId)
			} else {
				pubId = Long.parseLong(params.pubId.toString())
			}
		}
		def valoration = Integer.parseInt(params.valoration)
		def comment = "";
		if (params.comment) {	
			comment = params.comment
		}
		def phoneNumber = params.phoneNumber
			
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def pub = Pub.get(pubId)
		
		//def allowed = allowedOperationOverRestaurant(restaurant, apiKey)
		
		
		/*
		 * Validaciones:
		 */
		
		/*if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}*/
		
		if(!response.error && !pub)
		{
			response.error = true
			response.errorCode = "PUB_NOT_FOUND"
			log.warn "Pub no encontrado."
		}
		
		/*if(!response.error && (!restaurant.reservationPhone && !restaurant.reservationEmail))
		{
			response.error = true
			response.errorCode = "RESTAURANT_NOT_VALID_FOR_RESERVATION"
			log.warn "Restaurante no valido para reserva. No tiene ni email ni telefono movil de reservas."
		}
		
		if(!response.error && !reservationDateString)
		{
			response.error = true
			response.errorCode = "NECESSARY_RESERVATION_DATE_AND_HOUR"
			log.warn "Se necesita fecha y hora de reserva"
		}
		if(!response.error && (reservationDateString && !validReservationDateString(reservationDateString)))
		{
			response.error = true
			response.errorCode = "RESERVATION_DATE_AND_HOUR_NOT_VALID"
		}
		*/
		if(!response.error && !valoration)
		{
			response.error = true
			response.errorCode = "NECESSARY_VALORATION"
		}
		
		/*if(!response.error && !customerEmail)
		{
			response.error = true
			response.errorCode = "NECESSARY_EMAIL"
		}
		
		if(!response.error && customerEmail)
		{
			EmailValidator emailValidator = EmailValidator.getInstance()
			if(!emailValidator.isValid(customerEmail))
			{
				log.warn "Email incorrecto:" + customerEmail
				response.error = true
				response.errorCode = "EMAIL_NOT_VALID"
			}
		}
		
		if(!response.error && !customerPhone)
		{
			response.error = true
			response.errorCode = "NECESSARY_PHONE"
		}
		
		if(!response.error && !customerPhone.matches("6[0-9]{8}"))
		{
			response.error = true
			response.errorCode = "PHONE_NOT_VALID"
		}
		
		if(!response.error && !customerPhoneCountryIsoCode)
		{
			response.error = true
			response.errorCode = "NECESSARY_PHONE_COUNTRY_ISO_CODE"
		}
		
		if(!response.error && !adults)
		{
			response.error = true
			response.errorCode = "NECESSARY_ADULTS_NUMBER"
		}
		
		if(!response.error && !children)
		{
			response.error = true
			response.errorCode = "NECESSARY_CHILDREN_NUMBER"
		}*/
		
		/*if(!response.error)
		{
			try
			{
				def total = new Integer(adults) +  new Integer(children)
				if(total <= 0)
				{
					response.error = true
					response.errorCode = "NECESSARY_ADULTS_AND_CHILDREN_POSITIVE_NUMBER"
				}
			}
			catch(Exception e)
			{
				response.error = true
				response.errorCode = "NECESSARY_ADULTS_AND_CHILDREN_POSITIVE_NUMBER"
			}
			
		}*/
		
		/*
		 * Validación valoración Plato ya realizada
		 */
		/*if(!response.error)
		{
			def customer = Customer.findByPhoneOrEmail(customerPhone, customerEmail)
			
			if(customer)
			{
				def minReservationDate
				def maxReservationDate
				def reservationDate = Date.parse("dd/MM/yyyy HH:mm", reservationDateString)
				
				use(groovy.time.TimeCategory) {
								
					minReservationDate = reservationDate - 3.hours
					maxReservationDate = reservationDate + 3.hours
				}
				def reservations = Reservation.createCriteria().list(max: 1){
					
					eq("customer", customer)
					between("reservationDate", minReservationDate, maxReservationDate)
					
				}
				
				if(reservations)
				{
					response.error = true
					response.errorCode = "RESERVATION_ALREADY_DONE"
				}
				
				
				
			}
			
			
		}*/
		
		
		
		if(!response.error)
		{
			def pubValoration
			def valorationExists = PubValorations.findByPubAndPhoneNumber(pub,phoneNumber)
			if(valorationExists) {
				pubValoration = valorationExists
				pubValoration.pub = pub
				pubValoration.valoration = valoration
				pubValoration.comment = comment
				pubValoration.phoneNumber = phoneNumber
			} else {
				pubValoration = new PubValorations()
				pubValoration.pub = pub
				pubValoration.valoration = valoration
				pubValoration.comment = comment
				pubValoration.phoneNumber = phoneNumber
			}
			
			//reservationRequest.sourceApiKey = apiKey
	
	
			if(pubValoration.save(flush:true)) {
				log.info "Valoración realizada correctamente"
				response.pubId = pub.id
			}
			else {
				log.info "Se ha producido un error durante la valoración"
				response.error = true
				response.errorCode = "VALORATION_ERROR"
			}
		}
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	/**
	 * Servicio que realiza una busqueda de Pubs para devolver su media de valoracion
	 */
	def pubsValorationsSearch = {
		
		log.info "Servicio de busqueda de valoraciones de pubs..."
		log.debug "Parametros: " + params
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def query = params.query
		//def countryId = params.countryId
		//def provinceId = params.provinceId
		def pubId //= Long.parseLong(params.pubId)
		if (params.pubId) {
			if (params.pubId.getClass().getName() == 'String') {
				pubId = Long.parseLong(params.pubId)
			} else {
				pubId = Long.parseLong(params.pubId.toString())
			}
		}
		
		def max = params.max?.toInteger()
		def first = params.first?.toInteger()
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		def allowed = apiKey?.roleAny([ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_PUB_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && !pubId)
		{
			log.warn "Pub no encontrado."
			response.error = true
			response.errorCode = "PUB_NOT_VALID"
		}
		
		
		if(!response.error)
		{
			log.info "Estableciendo criterio de busqueda: "
			
			def association = apiKey.association
			
			if(first)
			{
				log.info "Primer resultado: " + first
			}
			else
			{
				first = 0
			}
			
			
			if(max)
			{
				log.info "Numero maximo de resultados: " + max
			}
			else
			{
				def defaultMax = 500
				log.info "No se ha establecido maximo de resultados. Se establece " + defaultMax
				max = defaultMax
			}
			
			
			def pubs = Pub.createCriteria().list(max: max, offset: first){
				if(query)
				{
					log.info "Nombre que contenga: " + query
					or
					{
						ilike("name", "%"+query+"%")
					}
				}
				
				
				if(pubId)
				{
					log.info "Pub Id: " + pubId
					
					eq("id", pubId)
				}
				
				
				if(association)
				{
					associations {
						eq('id', association.id)
					}
				}
				
				
				
				
				
				order("name", "asc")

								
			}
			
			
			response.pubs = []
			response.totalPubs = pubs.totalCount
			
			pubs.each{
				
				def pub = [:]
				
				pub.id = it.id
				
				pub.name = it.name
				
				def pubValorations = PubValorations.findAllByPub(it)
				def pubValoration = 0
				if(pubValorations.size()>0) {
					pubValorations.each {
						pubValoration = pubValoration+it.valoration
					}
					pubValoration = (int) Math.round(pubValoration/pubValorations.size())
				}
				pub.valoration = pubValoration
				
				/*def x1, y1, x2, y2
				
				double respuest;
				
				double res1,res2;
				
				x1=latitude
				y1=longitude
				x2=it.latitude
				y2=it.longitude
				
				res1=x2-x1;
				
				res2=y2-y1;
				
				res1=Math.pow(res1,2)+Math.pow(res2,2);
				
				respuest=Math.sqrt(res1);
				
				restaurant.distance = respuest*/
				
				
				response.pubs << pub
			
			}
		}
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	
	/**
	 * Servicio que realiza una busqueda de Eventos que la fecha actual sea menor
	 * la fecha del evento
	 */
	def eventsSearch = {
		
		log.info "Servicio de busqueda de eventos de pubs..."
		log.debug "Parametros: " + params
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def query = params.query
		def latitude = params.latitude?.toDouble()
		def longitude = params.longitude?.toDouble()
		//def countryId = params.countryId
		//def provinceId = params.provinceId
		//def restaurantId = Long.parseLong(params.restaurantId)
		
		def max = params.max?.toInteger()
		def first = params.first?.toInteger()
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		def allowed = apiKey?.roleAny([ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_PUB_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		/*if(!response.error && !restaurantId)
		{
			log.warn "Restaurante no encontrado."
			response.error = true
			response.errorCode = "RESTAURANT_NOT_VALID"
		}*/
		
		
		if(!response.error)
		{
			log.info "Estableciendo criterio de busqueda: "
			
			def association = apiKey.association
			
			if(first)
			{
				log.info "Primer resultado: " + first
			}
			else
			{
				first = 0
			}
			
			
			if(max)
			{
				log.info "Numero maximo de resultados: " + max
			}
			else
			{
				def defaultMax = 500
				log.info "No se ha establecido maximo de resultados. Se establece " + defaultMax
				max = defaultMax
			}
			
			
			def events = PubEvent.createCriteria().list(max: max, offset: first){
				if(query)
				{
					log.info "Titulo que contenga: " + query
					or
					{
						ilike("title", "%"+query+"%")
						//ilike("description", "%"+query+"%")
					}
				}
				
				Date fechaAct = new Date()
				
				if (fechaAct) {
					log.info "Fecha Actual: " + fechaAct
					//lt("dateFrom", fechaAct)
					ge("dateEvent", fechaAct)
				}
				
				eq("privateEvent", false)
				
				/*if(restaurantId)
				{
					log.info "Restaurante Id: " + restaurantId
					
					eq("id", restaurantId)
				}*/
				
				
				if(association)
				{
					log.info "Asociación: " + association.name
					inList("pub", association.pubs)
					/*associations {
						eq('id', association.id)
					}*/
				}
				
				
				
				
				
				order("dateEvent", "asc")

								
			}
			
			
			response.events = []
			response.totalEvents = events.totalCount
			
			events.each{
				
				def event = [:]
				
				event.id = it.id
				
				event.title = it.title
				
				event.description = it.description
				
				//event.dateFrom = WordUtils.capitalize(g.formatDate(date:it.dateFrom, format: 'dd MMMM'))
				event.dateEvent = WordUtils.capitalize(g.formatDate(date:it.dateEvent, format: 'dd/MM/yyyy HH:mm'))
				
				event.url = it.url
				
				event.pub = it.pub
				
				if (params.latitude && params.longitude && it.pub.latitude && it.pub.longitude) {
					event.distance = calculateMapDistance(params.latitude, params.longitude, it.pub.latitude, it.pub.longitude)
				}
				
				def mainPhoto = PubPhoto.findByPubAndMain(it.pub, true)
				
				if(mainPhoto)
				{
					event.pubPhoto = [url: createLink(absolute:true, controller:'resources', action: 'pubImage', params:[pubUri: it.pub.uri, photoId: mainPhoto.id,repositoryImageId: mainPhoto.repositoryImageId]), description: mainPhoto.description]
				}
				else if(it.pub.photos)
				{
					def firstPhoto = it.pub.photos?.first()
					event.pubPhoto = [url: createLink(absolute:true, controller:'resources', action: 'pubImage', params:[pubUri: it.pub.uri, photoId: firstPhoto.id,repositoryImageId: firstPhoto.repositoryImageId]), description: firstPhoto.description]
				}
				
				def pubValorations = PubValorations.findAllByPub(it.pub)
				def pubValoration = 0
				if(pubValorations.size()>0) {
					pubValorations.each {
						pubValoration = pubValoration+it.valoration
					}
					pubValoration = (int) Math.round(pubValoration/pubValorations.size())
				}
				event.pubValoration = pubValoration
				
				event.pubCoordinates = [latitude: it.pub.latitude, longitude:it.pub.longitude]
				
				event.pubMusicType = [id: it.musicType?.id, name: it.musicType?.name]
				event.musicSubType = it.musicSubType
				
				event.price = it.price
				event.commission= it.commission
				event.artist = it.artist
				event.ticketsOnlineSaleActive = it.ticketsOnlineSaleActive
				event.totalTickets = it.totalTickets
				event.totalTicketsOnlineSale = it.totalTicketsOnlineSale
				event.totalTicketsSold = it.totalTicketsSold
				
				event.totalTicketsValidated = 0
				event.totalTicketsUnvalidated = 0
				if (Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(it), true))
					event.totalTicketsValidated = Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(it), true).size
				if (Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(it), false))
					event.totalTicketsUnvalidated = Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(it), false).size
				
				//Acepta menores de edad
				event.acceptsMinors = it.acceptsMinors
				/*def x1, y1, x2, y2
				
				double respuest;
				
				double res1,res2;
				
				x1=latitude
				y1=longitude
				x2=it.latitude
				y2=it.longitude
				
				res1=x2-x1;
				
				res2=y2-y1;
				
				res1=Math.pow(res1,2)+Math.pow(res2,2);
				
				respuest=Math.sqrt(res1);
				
				restaurant.distance = respuest*/
				
				
				response.events << event
			
			}
		}
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	/**
	 * Servicio que realiza una busqueda de Eventos de una sala concreta
	 * y para un rango de fechas si se han indicado
	 */
	def eventsPubSearchByDate = {
		
		log.info "Servicio de busqueda de eventos de pubs..."
		log.debug "Parametros: " + params
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def query = params.query
		
		def pubId //= params.pubId
		if (params.pubId) {
			if (params.pubId.getClass().getName() == 'String') {
				pubId = Long.parseLong(params.pubId)
			} else {
				pubId = Long.parseLong(params.pubId.toString())
			}
		}
		
		def pub = Pub.findById(pubId)
		
		def dateFrom = null
		if (params.dateFrom) {
			dateFrom = new Date(params.dateFrom)
		}
			
		def dateTo = null
		if (params.dateTo) {
			dateTo = new Date(params.dateTo)
		}
		
			
		def latitude = params.latitude?.toDouble()
		def longitude = params.longitude?.toDouble()
		//def countryId = params.countryId
		//def provinceId = params.provinceId
		//def restaurantId = Long.parseLong(params.restaurantId)
		
		def max = params.max?.toInteger()
		def first = params.first?.toInteger()
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		def allowed = apiKey?.roleAny([ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_PUB_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && !pubId)
		{
			log.warn "Es necesario indicar el id de la sala."
			response.error = true
			response.errorCode = "NECESSARY_PUB_ID"
		}
		
		if(!response.error && !pub)
		{
			log.warn "Sala no encontrada."
			response.error = true
			response.errorCode = "PUB_NOT_FOUND"
		}
		
		/*if(!response.error && !restaurantId)
		{
			log.warn "Restaurante no encontrado."
			response.error = true
			response.errorCode = "RESTAURANT_NOT_VALID"
		}*/
		
		
		if(!response.error)
		{
			log.info "Estableciendo criterio de busqueda: "
			
			def association = apiKey.association
			
			if(first)
			{
				log.info "Primer resultado: " + first
			}
			else
			{
				first = 0
			}
			
			
			if(max)
			{
				log.info "Numero maximo de resultados: " + max
			}
			else
			{
				def defaultMax = 500
				log.info "No se ha establecido maximo de resultados. Se establece " + defaultMax
				max = defaultMax
			}
			
			
			def events = PubEvent.createCriteria().list(max: max, offset: first){
				if(query)
				{
					log.info "Titulo que contenga: " + query
					or
					{
						ilike("title", "%"+query+"%")
						//ilike("description", "%"+query+"%")
					}
				}
				
				
				if (dateFrom && dateTo) {
					log.info "Fecha Desde: " + dateFrom + " - Fecha Hasta: " + dateTo
					lt("dateEvent", dateTo)
					ge("dateEvent", dateFrom)
				} else if (dateFrom && !dateTo) {
					log.info "Fecha Desde: " + dateFrom + " - Fecha Hasta: " + dateTo
					ge("dateEvent", dateFrom)
				} else if (!dateFrom && dateTo) {
					log.info "Fecha Desde: " + dateFrom + " - Fecha Hasta: " + dateTo
					lt("dateEvent", dateTo)
				}
				
				if(pub)
				{
					log.info "Sala: " + pub.name
					
					eq("pub", pub)
					eq("privateEvent", false)
				}
				
				
				if(association)
				{
					log.info "Asociación: " + association.name
					inList("pub", association.pubs)
					/*associations {
						eq('id', association.id)
					}*/
				}
				
				
				
				
				
				order("dateEvent", "asc")

								
			}
			
			
			response.events = []
			response.totalEvents = events.totalCount
			
			events.each{
				
				def event = [:]
				
				event.id = it.id
				
				event.title = it.title
				
				event.description = it.description
				
				//event.dateFrom = WordUtils.capitalize(g.formatDate(date:it.dateFrom, format: 'dd MMMM'))
				event.dateEvent = WordUtils.capitalize(g.formatDate(date:it.dateEvent, format: 'dd/MM/yyyy HH:mm'))
				
				event.url = it.url
				
				event.pub = it.pub
				
				def eventPubLogo = it.pub.logoImage
				
				if(eventPubLogo)
				{
					event.pubLogoImage = [url: createLink(absolute:true, controller:'resources', action: 'pubLogoImage', params:[pubUri: it.pub.uri])]
				}
				else
				{
					event.pubLogoImage = ""
				}
				
				if (params.latitude && params.longitude && it.pub.latitude && it.pub.longitude) {
					event.distance = calculateMapDistance(params.latitude, params.longitude, it.pub.latitude, it.pub.longitude)
				}
				
				def eventPhoto = it.photo
				
				if(eventPhoto)
				{
					event.photo = [url: createLink(absolute:true, controller:'resources', action: 'pubEventImage', params:[event_id: it.id])]
				}
				else
				{
					event.photo = ""
				}
				
				def pubValorations = PubValorations.findAllByPub(it.pub)
				def pubValoration = 0
				if(pubValorations.size()>0) {
					pubValorations.each {
						pubValoration = pubValoration+it.valoration
					}
					pubValoration = (int) Math.round(pubValoration/pubValorations.size())
				}
				event.pubValoration = pubValoration
				
				event.pubCoordinates = [latitude: it.pub.latitude, longitude:it.pub.longitude]
				
				event.pubMusicType = [id: it.musicType?.id, name: it.musicType?.name]
				event.musicSubType = it.musicSubType
				
				event.price = it.price
				event.commission= it.commission
				event.artist = it.artist
				event.ticketsOnlineSaleActive = it.ticketsOnlineSaleActive
				event.totalTickets = it.totalTickets
				event.totalTicketsOnlineSale = it.totalTicketsOnlineSale
				event.totalTicketsSold = it.totalTicketsSold
				
				event.totalTicketsValidated = 0
				event.totalTicketsUnvalidated = 0
				if (Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(it), true))
					event.totalTicketsValidated = Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(it), true).size
				if (Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(it), false))
					event.totalTicketsUnvalidated = Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(it), false).size
				
				event.acceptsMinors = it.acceptsMinors
				
				
				response.events << event
			
			}
		}
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	
	/**
	 * Servicio que realiza una busqueda de un Evento concreto
	 * y devuelve el detalle
	 */
	def eventDetail = {
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def eventId //= params.eventId
		if (params.eventId) {
			if (params.eventId.getClass().getName() == 'String') {
				eventId = Long.parseLong(params.eventId)
			} else {
				eventId = Long.parseLong(params.eventId.toString())
			}
		}
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		//def event = PubEvent.get(eventId)
		
		def event = PubEvent.findByIdAndPrivateEvent(eventId, false)
		
		def pub
		if (event)
			pub = event.pub
		
		def allowed = allowedOperationOverPub(pub, apiKey)
		
		
		if(allowed)
		{
						
			if(event)
			{
				response.event = [:]
				
				response.event.id = event.id
				response.event.title = event.title
				response.event.description = event.description
				response.event.dateEvent = WordUtils.capitalize(g.formatDate(date:event.dateEvent, format: 'dd/MM/yyyy HH:mm'))
				
				response.event.url = event.url
				
				response.event.pub = response.pub
				
				def eventPubLogo = event.pub.logoImage
				
				if(eventPubLogo)
				{
					response.event.pubLogoImage = [url: createLink(absolute:true, controller:'resources', action: 'pubLogoImage', params:[pubUri: event.pub.uri])]
				}
				else
				{
					response.event.pubLogoImage = ""
				}
				
				if (params.latitude && params.longitude && it.pub.latitude && it.pub.longitude) {
					response.event.distance = calculateMapDistance(params.latitude, params.longitude, event.pub.latitude, event.pub.longitude)
				}
				
				def eventPhoto = event.photo
				
				if(eventPhoto)
				{
					response.event.photo = [url: createLink(absolute:true, controller:'resources', action: 'pubEventImage', params:[event_id: event.id])]
				}
				else
				{
					response.event.photo = ""
				}
				
				def pubValorations = PubValorations.findAllByPub(event.pub)
				def pubValoration = 0
				if(pubValorations.size()>0) {
					pubValorations.each {
						pubValoration = pubValoration+it.valoration
					}
					pubValoration = (int) Math.round(pubValoration/pubValorations.size())
				}
				response.event.pubValoration = pubValoration
				
				response.event.pubCoordinates = [latitude: event.pub.latitude, longitude:event.pub.longitude]
				
				response.event.pubMusicType = [id: event.musicType.id, name: event.musicType.name]
				response.event.musicSubType = event.musicSubType
				
				response.event.price = event.price
				response.event.commission= event.commission
				response.event.artist = event.artist
				response.event.ticketsOnlineSaleActive = event.ticketsOnlineSaleActive
				response.event.totalTickets = event.totalTickets
				response.event.totalTicketsOnlineSale = event.totalTicketsOnlineSale
				response.event.totalTicketsSold = event.totalTicketsSold
				
				response.event.totalTicketsValidated = 0
				response.event.totalTicketsUnvalidated = 0
				if (Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(event), true))
					response.event.totalTicketsValidated = Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(event), true).size
				if (Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(event), false))
					response.event.totalTicketsUnvalidated = Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(event), false).size
				
				response.event.acceptsMinors = event.acceptsMinors
				
			}
			else
			{
				log.warn "No existe evento con id: ${eventId}"
				response.error = true
				response.errorCode = "EVENT_NOT_FOUND"
			}
			
			
			
		}
		else
		{
			log.error "No tiene permisos"
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	/**
	 * Servicio que muestra resultados de autocompletar para títulos de Eventos
	 */
	def eventsAutocomplete = {
		
		def response = [:]
		response.error = false
		
	
		def apiKeyString = params.key
		def query = params.query
		def pubId //= params.pubId
		if (params.pubId) {
			if (params.pubId.getClass().getName() == 'String') {
				pubId = Long.parseLong(params.pubId)
			} else {
				pubId = Long.parseLong(params.pubId.toString())
			}
		}
		
		def pub = Pub.findById(pubId)
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def allowed = apiKey?.roleAny([ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_PUB_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && !query)
		{
			response.error = true
				response.errorCode = "NECESSARY_QUERY"
		}
		
		
		if(!response.error && query.length() < 3)
		{
			response.error = true
				response.errorCode = "QUERY_TOO_SHORT"
		}
		
		
		
		if(!response.error)
		{
	
			response.results = []
			
			def association = apiKey.association
			
			def events = PubEvent.withCriteria{
				ilike("title", "%"+query+"%")
				eq("privateEvent", false)
				/*if(association)
				{
					associations {
						eq('id', association.id)
					}
				}*/
				
				if(pub)
				{
					eq('pub', pub)
				}
				order("title", "asc")
			}
			
				 
			events.each{
				
				def preSearched
				def searched
				def posSearched
				
				def title = it.title
				
				def nameNormalized = seoFriendlyUrlsService.removeAccents(title)?.toUpperCase()
				log.debug "nameNormalized: " + nameNormalized
				
				def queryNormalized = seoFriendlyUrlsService.removeAccents(query)?.toUpperCase()
				log.debug "queryNormalized: " + queryNormalized
				
				def index = nameNormalized.indexOf(queryNormalized)
				
				preSearched = title.substring(0, index)
				searched = title.substring(index, index + query.length())
				posSearched = title.substring(index + query.length())
				
				def photoUrl
				def photo = it.photo
				
				if(photo)
				{
					photoUrl = createLink(absolute:true, controller:'resources', action: 'pubEventImage', params:[event_id: it.id])
				}
				else
				{
					photoUrl = ""
				}

				
				response.results << [preSearched: preSearched, searched: searched, posSearched: posSearched, photo: photoUrl, id: it.id]
			}

		}


		render getJsonOrJsonp(response, params)
	}
	
	
	/**
	 * Servicio que realiza una busqueda de Eventos de una sala concreta que la
	 * fecha actual sea menor la fecha del evento
	 */
	def eventsPubSearch = {
		
		log.info "Servicio de busqueda de eventos de sala concreta..."
		log.debug "Parametros: " + params
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		//def query = params.query
		def pubId
		if (params.id) {
			if (params.id.getClass().getName() == 'String') {
				pubId = Long.parseLong(params.id)
			} else {
				pubId = Long.parseLong(params.id.toString())
			}
		}
		
		def allDay = params.allDay
		def allDayAndExtra = params.allDayAndExtra
		
		def latitude = params.latitude?.toDouble()
		def longitude = params.longitude?.toDouble()
		//def countryId = params.countryId
		//def provinceId = params.provinceId
		//def restaurantId = Long.parseLong(params.restaurantId)
		
		def pub = Pub.findById(pubId)
		
		def max = params.max?.toInteger()
		def first = params.first?.toInteger()
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		def allowed = apiKey?.roleAny([ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_PUB_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && !pub)
		{
			log.warn "Sala no encontrada."
			response.error = true
			response.errorCode = "PUB_NOT_FOUND"
		}
		
		
		if(!response.error)
		{
			log.info "Estableciendo criterio de busqueda: "
			
			def association = apiKey.association
			
			if(first)
			{
				log.info "Primer resultado: " + first
			}
			else
			{
				first = 0
			}
			
			
			if(max)
			{
				log.info "Numero maximo de resultados: " + max
			}
			else
			{
				def defaultMax = 500
				log.info "No se ha establecido maximo de resultados. Se establece " + defaultMax
				max = defaultMax
			}
			
			
			def events = PubEvent.createCriteria().list(max: max, offset: first){
				if(pub)
				{
					log.info "Evento que contenga sala: " + pub.name
					
					eq("pub", pub)
					
					eq("privateEvent", false)
				}
				
				Date fechaAct
				def cloudbeesDiference = 2 //Horas diferencia servidor cloudbees
				def salesNotAvailableHours = cloudbeesDiference + 3 //Horas antes del evento para dejar de vender
				def extraHours = 7 //Horas durante el evento para dejar ver en app validación
				
				if (allDay && allDayAndExtra) {
					use(groovy.time.TimeCategory) {
						
						fechaAct = new Date() + cloudbeesDiference.hours - extraHours.hours
						
					}
				} else if (allDay) {
					use(groovy.time.TimeCategory) {
						
						fechaAct = new Date() + cloudbeesDiference.hours
						
					}
				} else {
					use(groovy.time.TimeCategory) {
						
						fechaAct = new Date() + salesNotAvailableHours.hours
						
					}
				}
				
				if (fechaAct) {
					log.info "Fecha Actual: " + fechaAct
					//lt("dateFrom", fechaAct)
					ge("dateEvent", fechaAct)
				}
				
				/*if(restaurantId)
				{
					log.info "Restaurante Id: " + restaurantId
					
					eq("id", restaurantId)
				}*/
				
				
				/*if(association)
				{
					log.info "Asociación: " + association.name
					inList("pub", association.pubs)*/
					/*associations {
						eq('id', association.id)
					}*/
				/*}*/
				
				
				
				
				
				order("dateEvent", "asc")

								
			}
			
			
			response.events = []
			response.totalEvents = events.totalCount
			
			events.each{
				
				def event = [:]
				
				event.id = it.id
				
				event.title = it.title
				
				event.description = it.description
				
				//event.dateFrom = WordUtils.capitalize(g.formatDate(date:it.dateFrom, format: 'dd MMMM'))
				event.dateEvent = WordUtils.capitalize(g.formatDate(date:it.dateEvent, format: 'dd/MM/yyyy HH:mm'))
				
				event.url = it.url
				
				event.pub = it.pub
				
				def eventPubLogo = it.pub.logoImage
				
				if(eventPubLogo)
				{
					event.pubLogoImage = [url: createLink(absolute:true, controller:'resources', action: 'pubLogoImage', params:[pubUri: it.pub.uri])]
				}
				else
				{
					event.pubLogoImage = ""
				}
				
				if (params.latitude && params.longitude && it.pub.latitude && it.pub.longitude) {
					event.distance = calculateMapDistance(params.latitude, params.longitude, it.pub.latitude, it.pub.longitude)
				}
				
				def eventPhoto = it.photo
				
				if(eventPhoto)
				{
					event.photo = [url: createLink(absolute:true, controller:'resources', action: 'pubEventImage', params:[event_id: it.id])]
				}
				else
				{
					event.photo = ""
				}
				
				def pubValorations = PubValorations.findAllByPub(it.pub)
				def pubValoration = 0
				if(pubValorations.size()>0) {
					pubValorations.each {
						pubValoration = pubValoration+it.valoration
					}
					pubValoration = (int) Math.round(pubValoration/pubValorations.size())
				}
				event.pubValoration = pubValoration
				
				event.pubCoordinates = [latitude: it.pub.latitude, longitude:it.pub.longitude]
				
				event.pubMusicType = [id: it.musicType.id, name: it.musicType.name]
				event.musicSubType = it.musicSubType
				
				event.price = it.price
				event.commission= it.commission
				event.artist = it.artist
				event.ticketsOnlineSaleActive = it.ticketsOnlineSaleActive
				event.totalTickets = it.totalTickets
				event.totalTicketsOnlineSale = it.totalTicketsOnlineSale
				event.totalTicketsSold = it.totalTicketsSold
				
				event.totalTicketsValidated = 0 
				event.totalTicketsUnvalidated = 0
				if (Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(it), true))
					event.totalTicketsValidated = Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(it), true).size
				if (Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(it), false))
					event.totalTicketsUnvalidated = Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(it), false).size
				
				event.acceptsMinors = it.acceptsMinors
				
				
				
				response.events << event
			
			}
		}
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	
	/**
	 * Servicio que realiza una busqueda de Entradas de un Evento concreto
	 */
	def eventTicketsSearch = {
		
		log.info "Servicio de busqueda de entradas de evento..."
		log.debug "Parametros: " + params
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		//def query = params.query
		
		def eventId //= params.eventId
		if (params.eventId) {
			if (params.eventId.getClass().getName() == 'String') {
				eventId = Long.parseLong(params.eventId)
			} else {
				eventId = Long.parseLong(params.eventId.toString())
			}
		}
		
		def event = PubEvent.findById(eventId)
		
		def ticketsSold = TicketsSold.findAllByPubEvent(event)
		
		def max = params.max?.toInteger()
		def first = params.first?.toInteger()
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		def allowed = apiKey?.roleAny([ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_PUB_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && !eventId)
		{
			log.warn "Es necesario indicar el id del evento."
			response.error = true
			response.errorCode = "NECESSARY_EVENT_ID"
		}
		
		if(!response.error && !event)
		{
			log.warn "Evento no encontrado."
			response.error = true
			response.errorCode = "EVENT_NOT_FOUND"
		}
		
		/*if(!response.error && !restaurantId)
		{
			log.warn "Restaurante no encontrado."
			response.error = true
			response.errorCode = "RESTAURANT_NOT_VALID"
		}*/
		
		
		if(!response.error)
		{
			log.info "Estableciendo criterio de busqueda: "
			
			def association = apiKey.association
			
			if(first)
			{
				log.info "Primer resultado: " + first
			}
			else
			{
				first = 0
			}
			
			
			if(max)
			{
				log.info "Numero maximo de resultados: " + max
			}
			else
			{
				def defaultMax = 500
				log.info "No se ha establecido maximo de resultados. Se establece " + defaultMax
				max = defaultMax
			}
			
			
			def tickets = Ticket.createCriteria().list(max: max, offset: first){
				if(ticketsSold)
				{
					log.info "Entradas de evento que contenga: " + event
						
						'in'("ticketsSold", ticketsSold)
					
				}
		
				order("ticketNumber", "asc")

								
			}
			
			
			response.tickets = []
			response.totalTickets = tickets.totalCount
			
			tickets.each{
				
				def ticket = [:]
				
				ticket.id = it.id
				
				ticket.ticketsSold = it.ticketsSold
				
				ticket.ticketNumber = it.ticketNumber
				
				//event.dateFrom = WordUtils.capitalize(g.formatDate(date:it.dateFrom, format: 'dd MMMM'))
				ticket.externalId = it.externalId
				
				ticket.accessed = it.accessed
				
				
				response.tickets << ticket
			
			}
		
		}
		
		render getJsonOrJsonp(response, params)
		
	}
	
	
	/**
	 * Servicio que realiza una busqueda de Ventas de Entradas de un Evento concreto
	 */
	def eventTicketsSalesSearch = {
		
		log.info "Servicio de busqueda de ventas de entradas de evento..."
		log.debug "Parametros: " + params
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		//def query = params.query
		
		def eventId //= params.eventId
		if (params.eventId) {
			if (params.eventId.getClass().getName() == 'String') {
				eventId = Long.parseLong(params.eventId)
			} else {
				eventId = Long.parseLong(params.eventId.toString())
			}
		}
		
		def event = PubEvent.findById(eventId)
		
		def max = params.max?.toInteger()
		def first = params.first?.toInteger()
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		def allowed = apiKey?.roleAny([ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_PUB_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && !eventId)
		{
			log.warn "Es necesario indicar el id del evento."
			response.error = true
			response.errorCode = "NECESSARY_EVENT_ID"
		}
		
		if(!response.error && !event)
		{
			log.warn "Evento no encontrado."
			response.error = true
			response.errorCode = "EVENT_NOT_FOUND"
		}
		
		/*if(!response.error && !restaurantId)
		{
			log.warn "Restaurante no encontrado."
			response.error = true
			response.errorCode = "RESTAURANT_NOT_VALID"
		}*/
		
		
		if(!response.error)
		{
			log.info "Estableciendo criterio de busqueda: "
			
			def association = apiKey.association
			
			if(first)
			{
				log.info "Primer resultado: " + first
			}
			else
			{
				first = 0
			}
			
			
			if(max)
			{
				log.info "Numero maximo de resultados: " + max
			}
			else
			{
				def defaultMax = 500
				log.info "No se ha establecido maximo de resultados. Se establece " + defaultMax
				max = defaultMax
			}
			
			
			def sales = TicketsSold.createCriteria().list(max: max, offset: first){
				if(event)
				{
					log.info "Ventas de entradas de evento que contenga: " + event + " y que hayan sido confirmadas."
						
						eq("pubEvent", event)
						eq("status", 'STATUS_CONFIRMED')
					
				}
				
			customer {
				order("name", "asc")
			}
								
			}
			
			
			response.sales = []
			response.totalSales = sales.totalCount
			
			sales.each{
				
				def sale = [:]
				
				sale.id = it.id
				
				sale.externalId = it.externalId
				
				sale.customer = it.customer
				
				sale.pubEvent = it.pubEvent
				
				sale.totalTicketsBuy = it.totalTicketsBuy
				
				sale.totalTicketsValidated = Ticket.findAllByTicketsSoldAndExternalIdAndAccessed(it, it.externalId, true).size
				
				sale.totalTicketsUnvalidated = Ticket.findAllByTicketsSoldAndExternalIdAndAccessed(it, it.externalId, false).size
				
				sale.buyDate = it.buyDate
				sale.operationNumber = it.operationNumber
				
				sale.ticketNumberFrom = it.ticketNumberFrom
				sale.ticketNumberTo = it.ticketNumberTo
				
				sale.status = it.status
				
				sale.notifiedPub = it.notifiedPub
				sale.notifiedPubMode = it.notifiedPubMode
				sale.notifiedPubDate = it.notifiedPubDate
				
				sale.notifiedCustomer = it.notifiedCustomer
				sale.notifiedCustomerMode = it.notifiedCustomerMode
				sale.notifiedCustomerDate = it.notifiedCustomerDate
				
				
				response.sales << sale
			
			}
		
		}
		
		render getJsonOrJsonp(response, params)
		
	}
	
	/**
	 * Servicio que realiza una busqueda de una Venta de entradas concreta
	 * y devuelve el detalle
	 */
	def ticketsSaleDetail = {
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def saleId //= params.saleId
		if (params.saleId) {
			if (params.saleId.getClass().getName() == 'String') {
				saleId = Long.parseLong(params.saleId)
			} else {
				saleId = Long.parseLong(params.saleId.toString())
			}
		}
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		//def event = PubEvent.get(eventId)
		
		def sale = TicketsSold.findById(saleId)
		
		def event
		if (sale)
			event = sale.pubEvent
		
		def allowed 
		if (event) {
			allowed = allowedOperationOverPub(event.pub, apiKey)
		}
		
		
		if(allowed)
		{
						
			if(sale)
			{
				response.sale = [:]
				
				response.sale.id = sale.id
				response.sale.externalId = sale.externalId
				response.sale.tickets = Ticket.findAllByTicketsSold(sale)
				
				response.sale.customer = sale.customer
				response.sale.pubEvent = sale.pubEvent
				response.sale.totalTicketsBuy = sale.totalTicketsBuy
	
				response.sale.buyDate = WordUtils.capitalize(g.formatDate(date:sale.buyDate, format: 'dd/MM/yyyy HH:mm'))
				response.sale.operationNumber = sale.operationNumber
	
				response.sale.ticketNumberFrom = sale.ticketNumberFrom
				response.sale.ticketNumberTo = sale.ticketNumberTo
	
				response.sale.promotionalCode = sale.promotionalCode
				
				/*def eventPubLogo = event.pub.logoImage
				
				if(eventPubLogo)
				{
					response.event.pubLogoImage = [url: createLink(absolute:true, controller:'resources', action: 'pubLogoImage', params:[pubUri: event.pub.uri])]
				}
				else
				{
					response.event.pubLogoImage = ""
				}*/
				
				/*if (params.latitude && params.longitude && it.pub.latitude && it.pub.longitude) {
					response.event.distance = calculateMapDistance(params.latitude, params.longitude, event.pub.latitude, event.pub.longitude)
				}*/
				
				/*def eventPhoto = event.photo
				
				if(eventPhoto)
				{
					response.event.photo = [url: createLink(absolute:true, controller:'resources', action: 'pubEventImage', params:[event_id: event.id])]
				}
				else
				{
					response.event.photo = ""
				}*/
				
				/*def pubValorations = PubValorations.findAllByPub(event.pub)
				def pubValoration = 0
				if(pubValorations.size()>0) {
					pubValorations.each {
						pubValoration = pubValoration+it.valoration
					}
					pubValoration = (int) Math.round(pubValoration/pubValorations.size())
				}
				response.event.pubValoration = pubValoration
				*/
				/*response.event.pubCoordinates = [latitude: event.pub.latitude, longitude:event.pub.longitude]
				
				response.event.pubMusicType = [id: event.musicType.id, name: event.musicType.name]
				response.event.musicSubType = event.musicSubType
				
				response.event.price = event.price
				response.event.commission= event.commission
				response.event.artist = event.artist
				response.event.ticketsOnlineSaleActive = event.ticketsOnlineSaleActive
				response.event.totalTickets = event.totalTickets
				response.event.totalTicketsOnlineSale = event.totalTicketsOnlineSale
				response.event.totalTicketsSold = event.totalTicketsSold
				
				response.event.totalTicketsValidated = 0
				response.event.totalTicketsUnvalidated = 0
				if (Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(event), true))
					response.event.totalTicketsValidated = Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(event), true).size
				if (Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(event), false))
					response.event.totalTicketsUnvalidated = Ticket.findAllByTicketsSoldInListAndAccessed(TicketsSold.findAllByPubEvent(event), false).size
				
				response.event.acceptsMinors = event.acceptsMinors
				*/
			}
			else
			{
				log.warn "No existe evento con id: ${eventId}"
				response.error = true
				response.errorCode = "EVENT_NOT_FOUND"
			}
			
			
			
		}
		else
		{
			log.error "No tiene permisos"
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	
	/**
	 * Servicio que realiza una busqueda de una Sala mediente el código de
	 * autenticación para Apps y permitir o denegar el acceso
	 */
	def pubAppsAuthenticationCodeSearch = {
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def appsAuthenticationCode = params.appsAuthenticationCode
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		//def event = PubEvent.get(eventId)
		
		def appAuthentication = PubAppsAuthenticationCode.findByCode(appsAuthenticationCode)
		
		def allowed
		if (appAuthentication) {
			allowed = allowedOperationOverPub(appAuthentication.pub, apiKey)
		}
		
		if (!response.error && !appAuthentication)
		{
			log.warn "No existe el código de acceso a Apps: ${appsAuthenticationCode}"
			response.error = true
			response.errorCode = "ACCESS_CODE_NOT_FOUND"
		}
		
		if (!response.error) {
			if(!response.error && allowed)
			{
							
				if(!response.error && appAuthentication)
				{
					response.appAuthentication = [:]
					
					response.appAuthentication.pub = appAuthentication.pub
					response.appAuthentication.code = appAuthentication.code
					
					def pubLogo = appAuthentication.pub.logoImage
					
					if(pubLogo)
					{
						response.appAuthentication.logoImage = [url: createLink(absolute:true, controller:'resources', action: 'pubLogoImage', params:[pubUri: appAuthentication.pub.uri])]
					}
					else
					{
						response.appAuthentication.logoImage = ""
					}
					
				}
				
				
				
			}
			else
			{
				log.error "No tiene permisos"
				response.error = true
				response.errorCode = "NOT_ALLOWED"
			}
		}
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	
	def calculateMapDistance(lat1, lon1, lat2, lon2)
	{
		
		//var R     = 6378.137;                          //Radio de la tierra en km
		def R     = 6378137;                          //Radio de la tierra en m
		def dLat  = rad( lat2 - Double.parseDouble(lat1) );
		def dLong = rad( lon2 - Double.parseDouble(lon1) );
		
		def a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(rad(Double.parseDouble(lat1))) * Math.cos(rad(lat2)) * Math.sin(dLong/2) * Math.sin(dLong/2);
		def c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		def d = R * c;
		
		return (int)d//.toFixed(0);                      //Retorna cero decimales
	}
	
	def rad (x) {
		return x*Math.PI/180;
	}
	
	
	/**
	 * Servicio que realiza una busqueda de Noticias de una Asociación
	 */
	def associationNewsSearch = {
		
		log.info "Servicio de busqueda de noticias de una asociación..."
		log.debug "Parametros: " + params
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def query = params.query
		//def countryId = params.countryId
		//def provinceId = params.provinceId
		//def restaurantId = Long.parseLong(params.restaurantId)
		
		
		def max = params.max?.toInteger()
		def first = params.first?.toInteger()
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		def allowed = apiKey?.roleAny([ApiKey.ROLE_ASSOCIATION_APP,ApiKey.ROLE_SUPERADMIN])
		
		/*
		 * Validaciones:
		 */
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		/*if(!response.error && restaurantId == null)
		{
			log.warn "Se necesita indicar un restaurante para buscar los platos."
			response.error = true
			response.errorCode = "NECESSARY_RESTAURANT"
		}*/
		
		
		if(!response.error)
		{
			log.info "Estableciendo criterio de busqueda: "
			
			def association = apiKey.association
			
			if(first)
			{
				log.info "Primer resultado: " + first
			}
			else
			{
				first = 0
			}
			
			
			if(max)
			{
				log.info "Numero maximo de resultados: " + max
			}
			else
			{
				def defaultMax = 500
				log.info "No se ha establecido maximo de resultados. Se establece " + defaultMax
				max = defaultMax
			}
			
			
			def associationNews = AssociationNews.createCriteria().list(max: max, offset: first){
				if(query)
				{
					log.info "Noticias de la asociación que contenga: " + query
					or
					{
						ilike("association", "%"+query+"%")
						ilike("description", "%"+query+"%")
						ilike("creationDate", "%"+query+"%")
						
					}
				}
				
				
				if(association)
				{
					log.info "Asociación: " + association.name;
					
					eq("association", association)
				}
				
				/*if(association)
				{
					associations {
						eq('id', association.id)
					}
				}*/
				
				
				
				
				
				order("creationDate", "desc")

								
			}
			
			
			response.associationNews = []
			response.totalAssociationNews = associationNews.totalCount
			
			associationNews.each{
				
				def associationNew = [:]
				
				associationNew.id = it.id
				
				associationNew.title = it.title
				associationNew.description = it.description
				associationNew.creationDate = it.creationDate
				associationNew.url = it.url
				
				def photo = it.photo
				
				if(photo)
				{
					associationNew.photo = [url: createLink(absolute:true, controller:'resources', action: 'associationNewsImage', params:[associationNews_id: it.id])]
				}
				else
				{
					associationNew.photo = null
				}
				
				
				response.associationNews << associationNew
			
			}
		}
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	/**
	 * Servicio que devuelve el detalle de una Noticia
	 */
	def associationNewDetail = {
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def associationNewId = params.associationNewId
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def associationNew = AssociationNews.get(associationNewId)
		
		/*def allowed = allowedOperationOverPub(associationNew, apiKey)
		
		
		if(allowed)
		{*/
						
			if(associationNew)
			{
				response.associationNew = [:]
				
				response.associationNew.id = associationNew.id
				response.associationNew.title = associationNew.title
				response.associationNew.description = associationNew.description
				response.associationNew.creationDate = associationNew.creationDate
				response.associationNew.url = associationNew.url
				
				
				
				/*response.pub.musicType = [id: pub.musicType?.id, name: pub.musicType?.name]
				
				response.pub.features = []
				pub.features.each{ feature ->
					response.pub.features << [id: feature.id, name: feature.name, code: feature.code, imageUrl: createLink(absolute:true, controller:'resources', action: 'pubFeatureImage', params:[repositoryImageId: feature.repositoryImageId])]
				}*/
				
				def photo = associationNew.photo
				
				if(photo)
				{
					response.associationNew.photo = [url: createLink(absolute:true, controller:'resources', action: 'associationNewsImage', params:[associationNews_id: associationNew.id])]
				}
				
			}
			else
			{
				log.warn "No existe noticia con id: ${associationNew.id}"
				response.error = true
				response.errorCode = "NEW_NOT_FOUND"
			}
			
			
			
		/*}
		else
		{
			log.error "No tiene permisos"
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}*/
		
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	
	/**
	 * Servicio que realiza una compra de entradas y llama al TPV virtual para el pago
	 */
	def sellTickets = {
		
		log.info "Servicio de venta de entradas"
		log.info "Parametros: " + params
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def eventId = params.eventId
		def customerName = params.customerName
		def customerEmail = params.customerEmail
		def customerPhone = params.customerPhone
		def customerPhoneCountryIsoCode = params.customerPhoneCountryIsoCode
		//def comments = params.comments
		def totalTicketsBuy = params.totalTicketsBuy
		def promotionalCode = params.promotionalCode
		
		def receiveAdvertising = params.receiveAdvertising
		def lopd = params.lopd
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def event = PubEvent.get(eventId)
		
		def allowed = allowedOperationOverPub(event.pub, apiKey)
		
		
		//Datos TPV Virtual
		def encryptionKey = grailsApplication.config.tpv.encryptionKey
		def merchantId = '085372647'
		def acquirerBIN = '0000554027'
		def terminalId = '00000003'
		if (apiKey.role == ApiKey.ROLE_PUB_APP
			&& event.pub.encryptionKey && event.pub.encryptionKey != ""
			&& event.pub.merchantId && event.pub.merchantId != ""
			&& event.pub.acquirerBIN && event.pub.acquirerBIN != ""
			&& event.pub.terminalId && event.pub.terminalId != "") {
			
			encryptionKey = event.pub.encryptionKey
			merchantId = event.pub.merchantId
			acquirerBIN = event.pub.acquirerBIN
			terminalId = event.pub.terminalId
		}
		def totalTicketsSold = TicketsSold.findAll()
		def operationNumber
		if (totalTicketsSold.size()>0) {
			operationNumber = TicketsSold.findAll().last().id+1
		} else {
			operationNumber = 1
		}
		def purchaseAmount
		def currencyType = 978
		def exponent = 2
		def urlOK = params.URL_OK
		def urlNOK = params.URL_NOK
		def stringSHA1 = 'SHA1'
		//def supportedPayment = 'SSL'
		//def selectedPayment = 'SSL'
		//def paymentDescription = ''
		//def cardNumber = params.pan
		//def expirationDate = params.expirationDate
		//def cvv2 = params.cvv2
		
		
		//
		// Validaciones:
		//
		
		if(!response.error && !allowed)
		{
			log.warn "No tiene permsisos."
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		if(!response.error && (!lopd || lopd==false || lopd=='false'))
		{
			response.error = true
			response.errorCode = "LOPD_IS_OBLIGATORY_ACCEPT"
			log.warn "LOPD (Protección Datos) no aceptada."
		}
		
		if(!response.error && !event)
		{
			response.error = true
			response.errorCode = "EVENT_NOT_FOUND"
			log.warn "Evento no encontrado."
		}
		
		if(!response.error && promotionalCode)
		{
			if (!PubEventPromotionalCode.findByCodeAndPubEvent(promotionalCode, event)) {
				response.error = true
				response.errorCode = "PROMOTIONAL_CODE_NOT_FOUND"
				log.warn "Código Promocional no encontrado."
			}
		}
		
		if(!response.error && promotionalCode)
		{
			if (PubEventPromotionalCode.findByCodeAndPubEventAndUsed(promotionalCode, event, true)) {
				response.error = true
				response.errorCode = "PROMOTIONAL_CODE_IS_USED"
				log.warn "Código Promocional ya utilizado."
			}
		}
		
		if(!response.error && promotionalCode)
		{
			if (PubEventPromotionalCode.findByCodeAndPubEventAndStatus(promotionalCode, event, PubEventPromotionalCode.STATUS_EXPIRED)) {
				response.error = true
				response.errorCode = "PROMOTIONAL_CODE_IS_EXPIRED"
				log.warn "Código Promocional ha EXPIRADO."
			}
		}
		
		if(!response.error && promotionalCode)
		{
			if (Integer.parseInt(totalTicketsBuy)>PubEventPromotionalCode.findByCodeAndPubEvent(promotionalCode, event).maxTickets) {
				response.error = true
				response.errorCode = "PROMOTIONAL_CODE_MAX_TICKETS_EXCEDED"
				log.warn "Máximo de Entradas para Código Promocional EXCEDIDO."
			}
		}
		
		if(!response.error && (!event.pub.email /*&& !restaurant.reservationEmail*/))
		{
			response.error = true
			response.errorCode = "PUB_NOT_VALID_FOR_BUY_TICKETS"
			log.warn "Sala no valida para comprar tickets. No tiene email para notificación de compra."
		}
		
		
		if(!response.error && !customerName)
		{
			response.error = true
			response.errorCode = "NECESSARY_CUSTOMER_NAME"
		}
		
		if(!response.error && !customerEmail)
		{
			response.error = true
			response.errorCode = "NECESSARY_EMAIL"
		}
		
		if(!response.error && customerEmail)
		{
			EmailValidator emailValidator = EmailValidator.getInstance()
			if(!emailValidator.isValid(customerEmail))
			{
				log.warn "Email incorrecto:" + customerEmail
				response.error = true
				response.errorCode = "EMAIL_NOT_VALID"
			}
		}
		
		/*if(!response.error && !customerPhone)
		{
			response.error = true
			response.errorCode = "NECESSARY_PHONE"
		}*/
		
		if(!response.error && customerPhone && !customerPhone.matches("6[0-9]{8}"))
		{
			response.error = true
			response.errorCode = "PHONE_NOT_VALID"
		}
		
		if(!response.error && customerPhone && !customerPhoneCountryIsoCode)
		{
			response.error = true
			response.errorCode = "NECESSARY_PHONE_COUNTRY_ISO_CODE"
		}
		
		if(!response.error && (!totalTicketsBuy || totalTicketsBuy<1))
		{
			response.error = true
			response.errorCode = "NECESSARY_TOTAL_TICKETS_BUY"
		}
		
		if(!response.error && event.totalTicketsSold+Integer.parseInt(totalTicketsBuy)>event.totalTicketsOnlineSale)
		{
			response.error = true
			response.errorCode = "MAX_TICKETS_TO_SELL_EXCEDED"
		}
		
		/*if(!response.error && !cardNumber)
		{
			response.error = true
			response.errorCode = "NECESSARY_CARD_NUMBER"
		}
		
		if(!response.error && !expirationDate)
		{
			response.error = true
			response.errorCode = "NECESSARY_CARD_EXPIRATION_DATE"
		}
		
		if(!response.error && !cvv2)
		{
			response.error = true
			response.errorCode = "NECESSARY_CARD_CVV2"
		}*/
		
		if(!response.error && !urlOK)
		{
			response.error = true
			response.errorCode = "NECESSARY_URL_OK"
		}
		
		if(!response.error && !urlNOK)
		{
			response.error = true
			response.errorCode = "NECESSARY_URL_NOK"
		}
		
		
		if(!response.error)
		{
			def customer = Customer.findByPhoneOrEmail(customerPhone, customerEmail)
			
			if (!customer) {
				
				customer = new Customer()
				
				customer.name = customerName
				customer.email = customerEmail
				if (customerPhone) {
					customer.phone = customerPhone
				}
				customer.country = Country.findByCode(customerPhoneCountryIsoCode)
				
				if (receiveAdvertising) {
					customer.receiveAdvertising = receiveAdvertising
				}
				
				if (lopd && (lopd==true || lopd=='true')) {
					customer.lopd = true
				}
				
				customer.save(flush:true, failOnError:true)
				
			} else {
				customer.email = customerEmail
				if (customerPhone) {
					customer.phone = customerPhone
				}
				customer.country = Country.findByCode(customerPhoneCountryIsoCode)
				
				if (receiveAdvertising) {
					customer.receiveAdvertising = receiveAdvertising
				}
				
				if (lopd && (lopd==true || lopd=='true')) {
					customer.lopd = true
				}
				
				customer.save(flush:true, failOnError:true)
			}
		}
		
		
		
		if(!response.error)
		{
			
			def customer = Customer.findByPhoneOrEmail(customerPhone, customerEmail)
			
			def ticketsSold = new TicketsSold()
			
			ticketsSold.id = operationNumber
			
			ticketsSold.customer = customer
			ticketsSold.pubEvent = event
			
			ticketsSold.totalTicketsBuy = Integer.parseInt(totalTicketsBuy)
			ticketsSold.buyDate = new Date()
			ticketsSold.operationNumber = operationNumber
			ticketsSold.sourceApiKey = apiKey
			
			if (promotionalCode) {
				ticketsSold.promotionalCode = PubEventPromotionalCode.findByCodeAndPubEvent(promotionalCode, event)
				
				//ticketsSold.promotionalCode.used = true
				
				//ticketsSold.promotionalCode.status = PubEventPromotionalCode.STATUS_CONFIRMED
			}
			
			def tickets = TicketsSold.findAllByPubEventAndStatus(event, TicketsSold.STATUS_CONFIRMED, [sort:'id', order:'asc'])
			if(tickets) {
				ticketsSold.ticketNumberFrom = tickets.last().ticketNumberTo + 1
				ticketsSold.ticketNumberTo = tickets.last().ticketNumberTo + Integer.parseInt(totalTicketsBuy)
			} else {
				ticketsSold.ticketNumberFrom = ticketsSold.pubEvent.ticketsInitNumber
				ticketsSold.ticketNumberTo = ticketsSold.pubEvent.ticketsInitNumber + Integer.parseInt(totalTicketsBuy) - 1
			}
			ticketsSold.save(flush:true, failOnError:true)
	
			def promoDiscount = PubEventPromotionalCode.findByCodeAndPubEvent(promotionalCode, event)
			if (promoDiscount) {
				if (promoDiscount.discount.contains("%")) {
					def percentDiscount = promoDiscount.discount.replaceAll("%", "")
					percentDiscount = percentDiscount.replaceAll(",", ".")
					def discount = (double) (((event.price + event.commission) * Integer.parseInt(totalTicketsBuy)) * Double.parseDouble(percentDiscount))/100 
					purchaseAmount = (double) (((event.price + event.commission) * Integer.parseInt(totalTicketsBuy))-discount)
				} else {
					purchaseAmount = (double) (((event.price + event.commission) * Integer.parseInt(totalTicketsBuy))-Double.parseDouble(promoDiscount.discount.replaceAll(',', '.')))
				}
			} else {
				purchaseAmount = (double) ((event.price + event.commission) * Integer.parseInt(totalTicketsBuy))
			}
			DecimalFormat df = new DecimalFormat("0.00");
			purchaseAmount = df.format(purchaseAmount)
			
			purchaseAmount = purchaseAmount.toString().replace('.', '')
			purchaseAmount = purchaseAmount.toString().replace(',', '')
			
			
			String firmaSinEncriptar = encryptionKey.toString() + merchantId.toString() +
					acquirerBIN.toString() + terminalId.toString() +
					operationNumber.toString() + purchaseAmount.toString() +
					currencyType.toString() + exponent.toString() +
					stringSHA1 + urlOK + urlNOK
			
			MessageDigest cript = MessageDigest.getInstance("SHA-1");
			cript.reset();
			cript.update(firmaSinEncriptar.getBytes("utf8"));
			
			String firma = new String(Hex.encodeHex(cript.digest()));
			
			/*def urlTPV = "http://tpv.ceca.es:8000"
			def http = new HTTPBuilder( urlTPV)
			
			// Se realiza la llamada al TPV Virtual para realizar el pago
			http.request( POST ) {
			  uri.path = '/cgi-bin/tpv'
			  uri.query = [ MerchantId:merchantId, AcquirerBIN: acquirerBIN, TerminalID: terminalId,
				  URL_OK: urlOK, URL_NOK: urlNOK, Firma: firma, Cifrado: stringSHA1,
				  Num_operacion: operationNumber, Importe: purchaseAmount, TipoMoneda: currencyType,
				  Exponente: exponent, Pago_soportado: supportedPayment, Pago_elegido: selectedPayment,
				  PAN: cardNumber, Caducidad: expirationDate, CVV2: cvv2, Idioma: 1]
			  log.info uri.query
			  
			}*/
		
			
			//Datos TPV Virtual a devolver para usar en pasarela de pago
			response.countryCode = customerPhoneCountryIsoCode
			response.encryptionKey = encryptionKey
			response.merchantId = merchantId
			response.acquirerBIN = acquirerBIN
			response.terminalId = terminalId
			response.operationNumber = operationNumber
			response.purchaseAmount = purchaseAmount
			response.currencyType = currencyType
			response.exponent = exponent
			response.urlOK = urlOK
			response.urlNOK = urlNOK
			response.stringSHA1 = stringSHA1
			response.firma = firma
			
			
			
		}
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	
	
	/**
	 * Servicio que ejecuta la resolucion de una compra de entradas
	 */
	def sellTicketsResolution = {
		
		log.info params
		
		def ticketsSold = TicketsSold.findByOperationNumber(params.Num_operacion)
		if (ticketsSold && ticketsSold.status == TicketsSold.STATUS_PENDING) {
			
			def pub = ticketsSold.pubEvent.pub
			
			ticketsSold.status = TicketsSold.STATUS_CONFIRMED
			ticketsSold.notifiedCustomerDate = new Date()
			
			if (ticketsSold.promotionalCode) {
				ticketsSold.promotionalCode.used = true
				
				ticketsSold.promotionalCode.status = PubEventPromotionalCode.STATUS_CONFIRMED
			}
			
			ticketsSold.save(flush:true, failOnError:true)
		
			def event = ticketsSold.pubEvent
			
			event.totalTicketsSold = event.totalTicketsSold + ticketsSold.totalTicketsBuy
			
			event.save(flush:true, failOnError:true)
			
			log.info "Guardando entradas compradas en base de datos."
			
			for (def i=1; i<=ticketsSold.totalTicketsBuy; i++) {
				def ticket = new Ticket()
				
				ticket.ticketsSold = ticketsSold
				ticket.externalId = ticketsSold.externalId
				ticket.ticketNumber = ticketsSold.ticketNumberFrom + i - 1
				
				ticket.save(flush:true, failOnError:true)
			}
			
			log.info "Enviando resolucion de compra de entradas para el evento: '${ticketsSold.pubEvent.title}' de la sala: '${ticketsSold.pubEvent.pub.name}' a email del cliente: '${ticketsSold.customer.email}'..."
			
			
			def messageCustomerSubject
			def messageCustomerContent
			def mailFrom = pub.name + " <" + pub.mailAddress + ">"
			
			//messageSubject = messageSource.getMessage('mailing.subject.customerTicketsBuy', null, null)
			messageCustomerSubject = ticketsSold.pubEvent.pub.name + " - Entrada/s " + ticketsSold.pubEvent.title
			messageCustomerContent = templateService.renderToString('/mailing/_customerTicketsBuy', [ticketsSold:ticketsSold])
			
			
			mailingService.sendMailTicketsWithPDF(mailFrom, ticketsSold.customer.email, messageCustomerSubject, messageCustomerContent, pub.mailHost, pub.mailPort, pub.mailUsername, pub.mailPassword, ticketsSold)
		}
	
	}
	
	
	/**
	 * Servicio para lectura de QR de entradas que valida si es correcta y si 
	 * no ha accedido todavía al evento
	 */
	def readTicketQR = {
		
		def response = [:]
		response.error = false
		
		def apiKeyString = params.key
		def qr = params.qr
		
		def apiKey = ApiKey.findByApiKey(apiKeyString)
		
		def externalId = params.qr.substring(0, params.qr.indexOf('/'))
		
		def ticketNumber = params.qr.substring(params.qr.indexOf('/')+1)
		
		def ticket = Ticket.findByExternalIdAndTicketNumberAndAccessed(externalId, Integer.parseInt(ticketNumber), false)
		
		def allowed
		if (TicketsSold.findByExternalId(externalId)) {
			allowed = allowedOperationOverPub(TicketsSold.findByExternalId(externalId).pubEvent.pub, apiKey)
		}
		
		
		if(allowed)
		{
						
			if(ticket)
			{
				/*response.ticket = [:]
				
				response.ticket.id = ticket.id
				response.ticket.externalId = ticket.externalId
				response.ticket.ticketNumber = ticket.ticketNumber
				response.ticket.accessed = ticket.accessed*/
				
				ticket.accessed = true;
				ticket.save(flush:true, failOnError:true)
				
				
			}
			else
			{
				log.warn "No se encuentra el ticket, ya ha sido utilizado o no es valido"
				response.error = true
				response.errorCode = "TICKET_ACCESSED_OR_NOT_VALID"
			}
			
			
			
		}
		else
		{
			log.error "No tiene permisos"
			response.error = true
			response.errorCode = "NOT_ALLOWED"
		}
		
		
		
		
		render getJsonOrJsonp(response, params)
		
	
	}
	

}
