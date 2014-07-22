package com.nidoapp.nidopub.domain.i18n

/**
 * Clase que representa un idioma
 * @author Developer
 *
 */
class I18nLanguage {
	
	String isoCode
	String language

    static constraints = {
    }
	
	String toString() {
		"$language"
	}
}
