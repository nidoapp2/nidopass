package com.nidoapp.nidopub.job




/**
 * Job que comprueba los emails de eventos y realiza el envío de los que corresponda en ese momento.
 * @author Developer
 *
 */
class EventsEMailSendJob {
	static triggers = {
		// Cada 30 minutos 	0 0/30 * * * ?
		// Cada minuto 		0 0/1 * * * ?
		cron name: 'eventsEMailSendJob', cronExpression: "0 0/60 * * * ?"
	}

	def eventsEMailSendService

    def execute() {
		log.info "Sistema automático de envío de emails de eventos de pubs..."
		
        eventsEMailSendService.sendEventsEMails()
		
		//log.info "Se han marcado todos las reservas expiradas."
    }
}
