package com.nidoapp.nidopub.service

/**
 * Conjunto de servicios que realizan operaciones asociadas a apoyar el SEO
 * @author Developer
 *
 */
class SeoFriendlyUrlsService {

    static transactional = false


	/**
	 * Servicio que genera un texto amigable para URL a partir de un texto cualquiera
	 * @param text texto sin formato
	 * @return texto amigable
	 */
	def getFriendlyUrl(text) {

		if(!text) return ""

		// Preserve escaped octets
		text = text.replaceAll('%([a-fA-F0-9][a-fA-F0-9])','---$1---')
		text = text.replaceAll('%','')
		text = text.replaceAll('---([a-fA-F0-9][a-fA-F0-9])---','%$1')

		// Remove accents
		text = removeAccents(text)

		// To lower case
		text = text.toLowerCase()

		// Kill entities
		text = text.replaceAll('&.+?;','')

		// Dots -> ''
		text = text.replaceAll('\\.','')

		// Remove any character except %a-zA-Z0-9 _-
		text = text.replaceAll('[^%a-zA-Z0-9 _-]', '')

		// Trim
		text = text.trim()

		// Spaces -> dashes
		text = text.replaceAll('\\s+', '-')

		// Dashes -> dash
		text = text.replaceAll('-+', '-')

		// It must end in a letter or digit, otherwise we strip the last char
		if (!text[-1].charAt(0).isLetterOrDigit()) text = text[0..-2]

		return text
	}


	def removeAccents(text) {
		java.text.Normalizer.normalize(text, java.text.Normalizer.Form.NFD)
				.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
	}
}
