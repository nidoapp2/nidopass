package com.nidoapp.nidopub.service.geo

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.PI;

/**
 * Conjunto de servicios que realizan operaciones asociadas a coordenadas geograficas
 * @author Developer
 *
 */
class CoordinatesRangeService {

    /**
     * Servicio que obtiene un rango a partir de unas coordenadas y un radio
     * @param latitude latidud
     * @param longitude longitud
     * @param radius radio en metros
     * @return area rectangular
     */
    def getRange(double latitude, double longitude, double radius) {
		
		def values = [:]
		
		
		//Earth?s radius, sphere
		double R=6378137

	   
		//Coordinate offsets in radians
		double dLat = radius/R
		double dLon = radius/(R*cos(PI*latitude/180))
	   
		//OffsetPosition, decimal degrees
		values.latmax = latitude + dLat * 180/PI
		values.lonmax = longitude + dLon * 180/PI
		
		values.latmin = latitude - dLat * 180/PI
		values.lonmin = longitude - dLon * 180/PI
		
		
		values

    }	

}
