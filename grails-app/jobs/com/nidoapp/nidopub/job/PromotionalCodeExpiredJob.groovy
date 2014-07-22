package com.nidoapp.nidopub.job




/**
 * Job que comprueba los códigos promocionales de venta de entradas que han expirado, los
 * marca como expirados para que no puedan utilizarlos
 * @author Developer
 *
 */
class PromotionalCodeExpiredJob {
	static triggers = {
		// Cada 30 minutos 	0 0/30 * * * ?
		// Cada minuto 		0 0/1 * * * ?
		cron name: 'PromotionalCodeExpiredJob', cronExpression: "0 0/1 * * * ?"
	}

	def promotionalCodeExpiredService

    def execute() {
		log.info "Sistema automático de control de códigos promocionales de compra de entradas que han expirado..."
		
        promotionalCodeExpiredService.expirePromotionalCodes()
		
		//log.info "Se han marcado todos las reservas expiradas."
    }
}
