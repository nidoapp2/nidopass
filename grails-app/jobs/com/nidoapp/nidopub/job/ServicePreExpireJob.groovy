package com.nidoapp.nidopub.job




/**
 * Job que comprueba los servicios de salas que han expirado, los
 * desactiva para que no puedan utilizarlos y les envia un email informativo
 * a las salas.
 * @author Developer
 *
 */
class ServicePreExpireJob {
	static triggers = {
		// Cada 30 minutos 	0 0/30 * * * ?
		// Cada minuto 		0 0/1 * * * ?
		cron name: 'ServicePreExpireJob', cronExpression: "0 0/60 * * * ?"
	}

	def servicePreExpireService

    def execute() {
		log.info "Sistema automático de control de servicios de salas que van a expirar en 15 días..."
		
        servicePreExpireService.sendServicePreExpireEMails()
		
		//log.info "Se han marcado todos las reservas expiradas."
    }
}