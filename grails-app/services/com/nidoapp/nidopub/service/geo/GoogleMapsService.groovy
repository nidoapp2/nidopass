package com.nidoapp.nidopub.service.geo

/**
 * Conjunto de servicios que realiza operaciones asoicadas a Google Maps.
 * @author Developer
 *
 */
class GoogleMapsService {

    static transactional = false

	def uri = "https://maps.googleapis.com/"
	
	def priorityTypes = ['neighborhood', 'locality', 'administrative_area_level_3','administrative_area_level_2','administrative_area_level_1','country']

    /**
     * Servicio que obtiene la descripcion de paisl provincia, ciudad y barrio a partir de unas coordenadas
     * @param latitude latitud
     * @param longitude longitud
     * @return detalle de ubicacion
     */
    def  getLocationDescription(latitude, longitude) {



    	def apiResult = [:]
    	apiResult.error = false


    	try {

    		def xml

    		def location = "" + latitude + "," + longitude

    		log.info "Onteniendo informacion de punto: " + location

    		def requestUri = uri + "maps/api/geocode/xml"

    		def params = [  sensor: 'false',
							latlng: location,
						    language:'es'
						 ]
    		

			withPub(uri: uri){
				xml = get(path : requestUri, 
							query : params
						).data
			}

			if("OK" == xml?.status.text())
			{
				def bestResult = [:]
				
				xml?.result?.each{ result->
					
					log.debug "Resultado: " + result.formatted_address?.text() + " es de tipos: " + result.type
					
					if(!bestResult.type != priorityTypes[0])
					{
						result.type.each {
							log.info "Resultado de tipo: " + it.text()
							
							if(!bestResult.result)
							{
								bestResult.result = result
								bestResult.type = it.text()
								log.info "Por ahora, mejor resultado de tipo: " + bestResult.type
							}
							
							else if(priorityTypes.contains(it.text()))
							{
								if(priorityTypes.contains(bestResult.type))
								{
									if(priorityTypes.indexOf(it.text()) < priorityTypes.indexOf(bestResult.type))
									{
										bestResult.result = result
										bestResult.type = it.text()
										log.info "Por ahora, mejor resultado de tipo: " + bestResult.type
									}
								}
								else
								{
									bestResult.result = result
									bestResult.type = it.text()
									log.info "Por ahora, mejor resultado de tipo: " + bestResult.type
								}
								
								
								
							}
	
						}
	
					}
					
				}
				
				if(bestResult.result)
				{
					log.info "Mejor resultado obtenido de tipo: " + bestResult.type
					log.debug "Obteniendo barrio, ciudad, provincia, y pais..."
					
					bestResult.result.address_component?.each{ component ->
						
						log.debug "Component: " + component.long_name?.text()
						

						if(!apiResult.district) {
							
							def componentNeighborhoodFound = false
							component.type.each {
								log.debug "component type: " + it.text()
								if(!componentNeighborhoodFound && it.text() == "neighborhood")
								componentNeighborhoodFound = true
							}
							
							if(componentNeighborhoodFound)
							{
								apiResult.district = component.long_name.text()
								log.debug "Barrio: " + apiResult.district
							}
						}

						if(!apiResult.city) {
							
							def componentLocalityFound = false
							component.type.each {
								log.debug "component type: " + it.text()
								if(!componentLocalityFound && it.text() == "locality")
								componentLocalityFound = true
							}
							
							if(componentLocalityFound)
							{
								apiResult.city = component.long_name.text()
								log.debug "Ciudad: " + apiResult.city
							}
							
						}

						if(!apiResult.province) {
							
							def componentAdministrativeAreaFound = false
							component.type.each {
								log.debug "component type: " + it.text()
								if(!componentAdministrativeAreaFound && it.text() == "administrative_area_level_2")
								componentAdministrativeAreaFound = true
							}
							
							if(componentAdministrativeAreaFound)
							{
								apiResult.province = component.long_name.text()
								log.debug "Provincia: " + apiResult.province
							}
							
						}
						
						if(!apiResult.countryName) {
							
							def componentFound = false
							component.type.each {
								log.debug "component type: " + it.text()
								if(!componentFound && it.text() == "country")
								componentFound = true
							}
							
							if(componentFound)
							{
								apiResult.countryName = component.long_name.text()
								apiResult.countryIsoCode = component.short_name.text()
								log.debug "Pais: " + apiResult.countryName + " (" + apiResult.countryIsoCode + ")"
							}
							
						}
					}
				}
				else
				{
					log.error "No se ha obtenido ningun resultado. "
					apiResult.error = true
				}
				
			}
			else
			{
				log.error "No se ha obtenido un estado correcto en la respuesta: " + xml?.status.text()
				apiResult.error = true
			}
					
	
    		
    	}
    	catch(Exception e) {
    		log.error "Error: " + e
    		apiResult.error = true
    	}
    	
		

		apiResult
    }

   


}
