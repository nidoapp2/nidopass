package com.nidoapp.nidopub.service

import groovy.sql.Sql
import org.apache.commons.validator.EmailValidator
import com.nidoapp.nidopub.domain.Association
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.PubPhoto
import com.nidoapp.nidopub.domain.PubFeature
import com.nidoapp.nidopub.domain.PubMusicType
import com.nidoapp.nidopub.domain.api.ApiKey
import com.nidoapp.nidopub.domain.geo.City
import com.nidoapp.nidopub.domain.geo.Country
import com.nidoapp.nidopub.domain.geo.District
import com.nidoapp.nidopub.domain.geo.Province
import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.user.UserRole;

/**
 * Servicios que permite realizar operaciones de integracion con bases de datos
 * @author Developer
 *
 */
class DatabaseIntegrationService {
	
	def dataSource_horecalegacy
	def googleMapsService
	def imageRepositoryService
	def seoFriendlyUrlsService
	def countryDialingService
	
	
	/**
	 * Servicio de importacion de datos de la base de datos de Horeca
	 */
	/*def importHorecaLegacyData = {
			def db = new Sql(dataSource_horecalegacy)
			
			def restaurants = db.rows("SELECT * FROM rs_restaurantes")
			
			def horeca
			
			if(restaurants)
			{
				log.info "Se creara asociacion Horeca y usuario...."
				
				log.info "Incluyendo usuario para asociacion...."
				
				def userCount = User.count()
				def userRoleCount = UserRole.count()
				
				def associationOwnerRole = Role.findByAuthority(Role.ROLE_ASSOCIATION_OWNER)
				
				def associationOwnerUser = new User(username: 'info@horeca.com', enabled: true, password: 'emenu', firstName:'Horeca', lastName:'Horeca').save(flush: true, failOnError:true)
				
				
				UserRole.create associationOwnerUser, associationOwnerRole, true
				
				assert User.count() == userCount + 1
				assert UserRole.count() == userRoleCount + 1
				
				
				log.info "Creando asociacion Horeca...."
				
				horeca = new Association(name:'Horeca')
				//horeca.addToRestaurants(restaurant)
				
				associationOwnerUser.association = horeca
				
				associationOwnerUser.save(flush: true, failOnError:true)
				
				
				log.info "Creando API Key para Horeca...."
				
				def apiKey = new ApiKey(role:ApiKey.ROLE_ASSOCIATION_APP, enabled:true);
				apiKey.association = horeca
				apiKey.save(flush: true, failOnError:true)
				
				log.info "API Key para Horeca: " + apiKey.apiKey
				
			}
			
			
			
	
			restaurants?.each{
				
				log.info "Importando restaurante: " + it.nombre
				
				
				log.info "Incluyendo usuario para restaurante...."
				
				def userCount = User.count()
				def userRoleCount = UserRole.count()
				
				def restaurantOwnerRole = Role.findByAuthority(Role.ROLE_RESTAURANT_OWNER)
				
				def user = db.firstRow("SELECT * FROM gl_usuarios WHERE codigo = " + it.fk_usuario)
				
				user.email = user.email?.replaceAll("\\s","")
				
				if(!user.email)
					user.email = it.email?.replaceAll("\\s","")
		

				if(!user.password)
					user.password = UUID.randomUUID().toString()
		
				if(!user.nombre)
					user.nombre = it.nombre
				
				
				
				def restaurantOwnerUser = User.findByUsername(user.email)
				
				if(!restaurantOwnerUser)
				{
					restaurantOwnerUser = new User(username: user.email, enabled: true, password: user.password, firstName: user.nombre, lastName:'Restaurante').save(flush: true, failOnError:true)	
					
					UserRole.create restaurantOwnerUser, restaurantOwnerRole, true
					
					assert User.count() == userCount + 1
					assert UserRole.count() == userRoleCount + 1
				}	
				
								
				
				
				log.info "Creando restaurante..."
				
				def restaurant = new Pub()
				restaurant.name = it.nombre
				restaurant.uri = seoFriendlyUrlsService.getFriendlyUrl(it.nombre)
				restaurant.description = it.descripcion
				restaurant.address = it.direccion
				restaurant.telephone = it.telefono?.replaceAll("\\s","")
				restaurant.fax = it.fax
				
				restaurant.email = it.email?.replaceAll("\\s","")
				
				EmailValidator emailValidator = EmailValidator.getInstance()
				if(!restaurant.email || !emailValidator.isValid(restaurant.email))
				{
					if(emailValidator.isValid(user.email))
					{
						restaurant.email = user.email
					}
					else
					{
						restaurant.email = null
						log.error "Imposible obtener email de restaurante: " + it.nombre
					}
				}
				
				
				
				restaurant.address = it.direccion				
				restaurant.website = it.web
				restaurant.specialty = it.especialidad
				
				log.info "Estableciendo telefono movil de reservas..."
				if(restaurant.telephone)
				{
					def telephoneWithoutPrefix
					
					if(restaurant.telephone.startsWith("+34"))
						telephoneWithoutPrefix = restaurant.telephone.substring(3)
					else if(restaurant.telephone.startsWith("34"))
						telephoneWithoutPrefix = restaurant.telephone.substring(2)
					else if(restaurant.telephone.startsWith("0034"))
						telephoneWithoutPrefix = restaurant.telephone.substring(4)
					else
						telephoneWithoutPrefix = restaurant.telephone
					
					if(telephoneWithoutPrefix?.startsWith("6"))
					{
						restaurant.reservationPhone = telephoneWithoutPrefix
						log.info "Establecido tele telefono movil de reservas: '${telephoneWithoutPrefix}'"
					}
					else
					{
						log.warn "El telefono '${telephoneWithoutPrefix}' no es un telefono movil."
					}
					
					
				}
				else
				{
					log.warn "El restaurante no tiene ningun telefono asignado"
				}
				
				log.info "Estableciendo email de reservas..."
				restaurant.reservationEmail = restaurant.email
				
				//TODO: enviar email de informacion y actualizacion de email y moviles de reserva
				
				restaurant.menuPriceFrom = it.precio_menu_dia_desde
				restaurant.menuPriceTo = it.precio_menu_dia_hasta
				restaurant.cartePriceFrom = it.precio_carta_desde
				restaurant.cartePriceTo = it.precio_carta_hasta
				
				restaurant.openingHours = it.horario_comedor
				restaurant.closingDays = it.cierre_semanal
				restaurant.closingSpecial = it.vacaciones
				
				restaurant.latitude = it.lt
				restaurant.longitude = it.lng
			
				// Restaurantes sin coordenadas:
				
				if(it.codigo == 138) // Bolaso
				{
					restaurant.latitude = 42.17838
					restaurant.longitude = -1.186427
				}
				else if(it.codigo == 243) //La nueva Carambola
				{
					restaurant.latitude = 41.644236
					restaurant.longitude = -0.892913
				}
				else if(it.codigo == 242) //River Hall
				{
					restaurant.latitude = 41.662108
					restaurant.longitude = -0.888798
				}
				
				
				def locationDescription = googleMapsService.getLocationDescription(restaurant.latitude, restaurant.longitude) 
				
				if(!locationDescription.error)
				{
					def country = Country.findByCode(locationDescription.countryIsoCode)
					if(!country && locationDescription.countryName && locationDescription.countryIsoCode)
					{
						def dialCode = countryDialingService.getDialCode(locationDescription.countryIsoCode)
						country = new Country(name: locationDescription.countryName, code: locationDescription.countryIsoCode, dialCode: dialCode).save(flush:true, failOnError:true)
					}
					restaurant.country = country
					
					
					def province = Province.findByNameAndCountry(locationDescription.province, country)
					if(!province && locationDescription.province)
					{
						province = new Province(name: locationDescription.province, country:country).save(flush:true, failOnError:true)
					}
					restaurant.province = province
					
				
					def city = City.findByNameAndProvince(locationDescription.city, province)
					if(!city && locationDescription.city)
					{
						city = new City(name: locationDescription.city, province: province).save(flush:true, failOnError:true)
					}
					restaurant.city = city
					
					
					def district = District.findByNameAndCity(locationDescription.district, city)
					if(!district && locationDescription.district)
					{
						district = new District(name: locationDescription.district, city: city).save(flush:true, failOnError:true)
					}
					restaurant.district = district
					
				}
				else
				{
					log.error "Error obteniendo datos de Google Maps. Estableciendo pais Espania y provincia Zaragoza"
					
				
					def country = Country.findByCode("ES")
					if(!country)
					{
						def dialCode = countryDialingService.getDialCode('ES')
						country = new Country(name: "Espa??a", code: "ES", dialCode: dialCode).save(flush:true, failOnError:true)
					}
					restaurant.country = country
					
					
					def province = Province.findByNameAndCountry("Zaragoza", country)
					if(!province)
					{
						province = new Province(name: "Zaragoza").save(flush:true, failOnError:true)
					}
					restaurant.province = province
				}
				
				
				log.info "Recuperando fotos de restaurante..."
				
				def mainPhotoEstablished = false
				
				if(it.imagen)
				{
					
					
					def repositoryImageId = imageRepositoryService.saveImage("http://www.restauranteszaragoza.org/imagen/restaurantes/" + it.imagen)
					
					if(repositoryImageId)
					{
						def photo = new PubPhoto()
						photo.repositoryImageId = repositoryImageId
						photo.description = it.nombre
						photo.main = true
						
						log.info "Incluyendo foto: " + photo.repositoryImageId
						
						restaurant.addToPhotos(photo)
						
						mainPhotoEstablished = true
					}
					
					
				}
				
				def legacyRestaurantPhotos = db.rows("SELECT * FROM rs_restaurantes_imagenes WHERE fk_restaurante = " + it.codigo)
				
				
				legacyRestaurantPhotos.each{ legacyRestaurantPhoto ->
					
					def repositoryImageId = imageRepositoryService.saveImage("http://www.restauranteszaragoza.org/imagen/restaurantes/" + legacyRestaurantPhoto.imagen_restaurante)
					
					if(repositoryImageId)
					{
						def photo = new PubPhoto()
						photo.repositoryImageId = repositoryImageId
						photo.description = it.nombre
						
						if(!mainPhotoEstablished)
						{
							photo.main = true
							mainPhotoEstablished = true
						}
						
						log.info "Incluyendo foto: " + photo.repositoryImageId
						
						restaurant.addToPhotos(photo)
						
						
						
					}
					
					
										
				}
				
									
				restaurant.addToAssociations(horeca)
				
			
				restaurantOwnerUser.addToRestaurants(restaurant)
				restaurantOwnerUser.save(flush:true, failOnError:true)
				
				assert restaurantOwnerUser.restaurants.contains(restaurant)
				assert restaurant.owner == restaurantOwnerUser
				
				
				
				log.info "Incluyendo caracteristicas del restaurante..."
				
				def legacyRestaurantFeatures = db.rows("SELECT * FROM rs_caracteristica_restaurante WHERE fk_restaurante = " + it.codigo)
				
				legacyRestaurantFeatures?.each { legacyRestaurantFeature ->
					
					def legacyFeature = db.firstRow("SELECT * FROM rs_caracteristicas WHERE codigo = " + legacyRestaurantFeature.fk_caracteristica)
					
					def feature = PubFeature.findByName(legacyFeature.caracteristica)
					
					if(!feature)  
					{
						def repositoryImageId = imageRepositoryService.saveImage("http://www.restauranteszaragoza.org/imagen/carac/" + legacyFeature.path_imagen)
						
						if(repositoryImageId)
						{
							feature = new PubFeature()
							feature.code = seoFriendlyUrlsService.getFriendlyUrl(legacyFeature.caracteristica)
							if(feature.code?.startsWith("indica-que-"))
							{
								feature.code = feature.code.substring(11)
							}
							else if(feature.code?.startsWith("indica-"))
							{
								feature.code = feature.code.substring(7)
							}							
							
							feature.name = legacyFeature.caracteristica
							feature.description = legacyFeature.descripcion
							feature.repositoryImageId = repositoryImageId
							feature.save(flush:true, failOnError:true)
						}
						
						
					}

					
					restaurant.addToFeatures(feature)
					restaurant.save(flush:true, failOnError:true)
				}
				
				
				log.info "Incluyendo tipo de comida del restaurante..."			

					
				def legacyFoodType = db.firstRow("SELECT * FROM rs_tipo_comida WHERE codigo = " + it.fk_tipo_comida)
	
				def foodType = PubMusicType.findByName(legacyFoodType.especialidad)
	
				if(!foodType) 
				{
					foodType = new PubMusicType()
					foodType.name = legacyFoodType.especialidad
					foodType.save(flush:true, failOnError:true)
				}
	
	
				restaurant.foodType =foodType
				restaurant.save(flush:true, failOnError:true)
				
				
				
			}
		}*/
		
}
