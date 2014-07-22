package com.nidoapp.nidopub.domain.i18n

/**
 * Clase que representa un elemento que puede tener varias traducciones a distintos idiomas
 * @author Developer
 *
 */
class I18nItem {
	
	String defaultText
	
	long pub_id
	
	static hasMany = [translations: I18nTranslation]
	
    static constraints = {
    }
	
	String toString() {
		"$defaultText"
	}
}
