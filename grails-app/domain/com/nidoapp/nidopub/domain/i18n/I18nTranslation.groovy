package com.nidoapp.nidopub.domain.i18n

/**
 * Clase que representa una traduccion de un elemento a un idioma
 * @author Developer
 *
 */
class I18nTranslation {
	
	I18nLanguage lang
	
	String text
	
	static belongsTo = [item : I18nItem]

    static constraints = {
		
		lang unique: 'item'
    }
	
}
