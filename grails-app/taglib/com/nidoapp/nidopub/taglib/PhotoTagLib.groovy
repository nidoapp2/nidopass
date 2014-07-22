package com.nidoapp.nidopub.taglib

import com.nidoapp.nidopub.domain.PubPhoto
import com.nidoapp.nidopub.domain.PubPromotionalImage;

/**
 * TagLib que facilita generacion de codigo HTML asociados a fotos
 * @author Developer
 *
 */
class PhotoTagLib {
	
	def  mainPhotoUrl = { attrs ->
		def url
		def pub = attrs.pub
		def mainPhoto = PubPhoto.findByPubAndMain(pub, true)
		if(pub && mainPhoto)
		{
			url = g.createLink(absolute:true, controller:'resources', action: 'pubImage', params:[pubUri: pub.uri, photoId: mainPhoto.id,repositoryImageId: mainPhoto.repositoryImageId])
		}
		else
		{
			url = resource(dir: 'images/profile', file: 'profile-img.png')
			
		}
				
		
		out << url
	 }
	
	def  pubLogoUrl = { attrs ->
		def url
		def pub = attrs.pub
		//def logoPhoto = pub.logoImage
		if(pub /*&& logoPhoto*/)
		{
			url = g.createLink(absolute:true, controller:'resources', action: 'pubLogoImage', params:[pubUri: pub.uri/*, photoId: mainPhoto.id,repositoryImageId: mainPhoto.repositoryImageId*/])
		}
		else
		{
			url = ""//resource(dir: 'images/profile', file: 'profile-img.png')
			
		}
				
		
		out << url
	 }
	
	def  pubEventImageUrl = { attrs ->
		def url
		def event = attrs.event
		//def logoPhoto = pub.logoImage
		if(event /*&& logoPhoto*/)
		{
			url = g.createLink(absolute:true, controller:'resources', action: 'pubEventImage', params:[event_id: event.id/*, photoId: mainPhoto.id,repositoryImageId: mainPhoto.repositoryImageId*/])
		}
		else
		{
			url = ""//resource(dir: 'images/profile', file: 'profile-img.png')
			
		}
				
		
		out << url
	 }
	
	
	def  pubPromotionalImageTicketsUrl = { attrs ->
		def url
		def event = attrs.event
		def pub = event.pub
		def image = PubPromotionalImage.findByPubAndDateFromLessThanEqualsAndDateToGreaterThanEquals(pub, event.dateEvent, event.dateEvent)
		if(image)
		{
			url = g.createLink(absolute:true, controller:'resources', action: 'pubPromotionalImage', params:[pubUri: pub.uri, imageId: image.id, image: image.image])
		}
		else
		{
			url = ""//resource(dir: 'images/profile', file: 'profile-img.png')
			
		}
				
		
		out << url
	 }
	
	def  pubPromotionalImageTicketsUrlPdf = { attrs ->
		def url
		def event = attrs.event
		if (event) {
			def pub = event.pub
			def image = PubPromotionalImage.findByPubAndDateFromLessThanEqualsAndDateToGreaterThanEquals(pub, event.dateEvent, event.dateEvent)
			if(image)
			{
				url = g.createLink(absolute:true, controller:'resources', action: 'pubPromotionalImagePdf', params:[/*pubUri: pub.uri,*/ event:event.id /*, imageId: image.id, image: image.image*/])
			}
			else
			{
				url = ""//resource(dir: 'images/profile', file: 'profile-img.png')
				
			}
		}
				
		
		out << url
	 }

}
