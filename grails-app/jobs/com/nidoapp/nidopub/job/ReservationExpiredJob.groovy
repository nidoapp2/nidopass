package com.nidoapp.nidopub.job




/**
 * Job que comprueba  las peticiones de reserva de mesa que acaban de expirar, y las marca como caducadas ademas
 * de enviar notificacion al cliente.
 * @author Developer
 *
 */
/*class ReservationExpiredJob {
	static triggers = {
		// Cada 30 minutos 	0 0/30 * * * ?
		// Cada minuto 		0 0/1 * * * ?
		cron name: 'reservationExpiredJob', cronExpression: "0 0/1 * * * ?"
	}

	def reservationService

    def execute() {
		log.info "Marcando como excedidos aquellas reservas que han superado el tiempo permitido sin respuesta..."
		
        reservationService.processExpiredReservations()
		
		//log.info "Se han marcado todos las reservas expiradas."
    }
}*/
