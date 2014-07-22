package com.nidoapp.nidopub.job




/**
 * Job que comprueba los emails de eventos y realiza el envío de los que corresponda en ese momento.
 * @author Developer
 *
 */
class TicketsEMailSendJob {
	static triggers = {
		// Cada 30 minutos 	0 0/30 * * * ?
		// Cada minuto 		0 0/1 * * * ?
		cron name: 'ticketsEMailSendJob', cronExpression: "0 0/1 * * * ?"
	}

	def ticketsEMailSendService

    def execute() {
		log.info "Sistema automático de envío de emails de venta de entradas que no se ha podido efectuar el pago..."
		
        ticketsEMailSendService.sendTicketsCanceledEMails()
		
		//log.info "Se han marcado todos las reservas expiradas."
    }
}
