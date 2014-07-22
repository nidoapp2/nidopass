package com.nidoapp.nidopub.controller.endpoint

import com.nidoapp.nidopub.domain.Reservation

/**
 * Controlador que atiende peticiones de confirmacion, denegacion o cancelaci???n de reservas.
 * @author Developer
 *
 */
class ReservationResolutionController {
	
	def reservationService

    def confirm = {
		
		def reservationExternalId = params.id
		def apiKey = params.key
		
		
		def reservation = Reservation.findByExternalId(reservationExternalId )
		
		
		if(reservation)
		{
			def processed = reservationService.processInProgressReservation(reservation, true)
			
			[processed: processed, reservation: reservation]
			
		}
		else
		{
			log.warn "No existe reserva con external id: " + reservationExternalId
			response.sendError(404)
		}		
    }
	
	def deny = {
		
		def reservationExternalId = params.id
		def apiKey = params.key
		
		
		def reservation = Reservation.findByExternalId(reservationExternalId )
		
		
		if(reservation)
		{
			def processed = reservationService.processInProgressReservation(reservation, false)
			
			[processed: processed, reservation: reservation]			
		}
		else
		{
			log.warn "No existe reserva con external id: " + reservationExternalId
			response.sendError(404)
		}	
	
	}
	
	def cancel = {
		
		def reservationExternalId = params.id
		def apiKey = params.key
		
		
		def reservation = Reservation.findByExternalId(reservationExternalId )
		
		
		if(reservation)
		{
			def processed = reservationService.processCancelConfirmedReservation(reservation, true)
			
			[processed: processed, reservation: reservation]
		}
		else
		{
			log.warn "No existe reserva con external id: " + reservationExternalId
			response.sendError(404)
		}
	
	}
}
