package com.nidoapp.nidopub.controller.geo

import org.springframework.dao.DataIntegrityViolationException

import com.nidoapp.nidopub.domain.geo.City;
import com.nidoapp.nidopub.domain.geo.Province;

class CityController {

    def scaffold = true
	
	def getCities = {
		
		//Se obtiene la provincia
		def provinceInstance = Province.findById(params.id)
		
		//Lista de ciudades
		def citiesList = City.findAllByProvince(provinceInstance)
		
		//Se hace render del template "_selectCities.gsp" con la lista de ciudades obtenida
		render (template:"selectCities", model: [citiesList:citiesList])		
		
	}
}
