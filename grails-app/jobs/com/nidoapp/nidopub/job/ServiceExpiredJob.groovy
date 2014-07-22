package com.nidoapp.nidopub.job




/**
 * Job que comprueba los servicios de salas que han expirado, los
 * desactiva para que no puedan utilizarlos y les envia un email informativo
 * a las salas.
 * @author Developer
 *
 */
class ServiceExpiredJob {
	static triggers = {
		// Cada 30 minutos 	0 0/30 * * * ?
		// Cada minuto 		0 0/1 * * * ?
		cron name: 'ServiceExpiredJob', cronExpression: "0 0/60 * * * ?"
	}

	def serviceExpiredService

    def execute() {
		log.info "Sistema automático de control de servicios de salas que han expirado..."
		
        serviceExpiredService.disableServices()
		
		//log.info "Se han marcado todos las reservas expiradas."
    }
}
