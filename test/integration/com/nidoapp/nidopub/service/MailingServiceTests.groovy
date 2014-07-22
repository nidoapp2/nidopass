package com.nidoapp.nidopub.service

import static org.junit.Assert.*
import com.nidoapp.nidopub.domain.*
import com.nidoapp.nidopub.domain.user.*
import com.nidoapp.nidopub.domain.api.*
import org.junit.*

class MailingServiceTests {
	
	def mailingService
	def templateService
	def messageSource
	def grailsApplication

    @Before
    void setUp() {
        // Setup logic here
    }

    @After
    void tearDown() {
        // Tear down logic here
    }

    @Test
    void testSendToEmail() {
		
		
		/*
		def emailTo = "juanrferia@gmail.com"
		
		def messageContent = templateService.renderToString('/mailing/_test', [date:new Date()])
		
		mailingService.sendMail(emailTo, "Prueba", messageContent)
		
		*/
		
		
		/*
		grailsApplication.config.mail.limits = false
		
		def reservation = Reservation.list().last()
		def apiKey = ApiKey.list().last()
		
		def emailTo = "juanrferia@gmail.com"
		
		def messageSubject = messageSource.getMessage('mailing.subject.pubNewReservation', null, null)
		
		def messageContent = templateService.renderToString('/mailing/_pubNewReservation', [reservation: reservation, apiKey: apiKey.apiKey])
		
		mailingService.sendMail(emailTo, messageSubject, messageContent)
		
		*/
		
		
		
		
		
    }
}
