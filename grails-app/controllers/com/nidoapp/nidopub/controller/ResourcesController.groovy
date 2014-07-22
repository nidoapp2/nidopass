package com.nidoapp.nidopub.controller

import com.nidoapp.nidopub.domain.AssociationNews;
import com.nidoapp.nidopub.domain.Carte;
import com.nidoapp.nidopub.domain.Product;
import com.nidoapp.nidopub.domain.Association;
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.PubFeature;
import com.nidoapp.nidopub.domain.PubPhoto
import com.nidoapp.nidopub.domain.PubPromotionalImage;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.PubEvent;


/**
 * Controlador que atiende peticiones de recursos desde la administracion
 * @author Developer
 *
 */
class ResourcesController {



	def imageRepositoryService
	
    def pubImage = { 

    	def pubUri = params.pubUri
        def photoId = params.photoId
		def imageFileName = params.repositoryImageId
		
		def pub = Pub.findByUri(pubUri)
		
        def photo = PubPhoto.findByPubAndId(pub,photoId)
		

    	if(photo)
    	{
			def file = imageRepositoryService.retrieveImage(imageFileName)
			if (file != null) {
				response.outputStream << file.getInputStream()
				response.contentType = file.getContentType()
			}

    	}

    	
		return;

    }   
	
	
	def pubFeatureImage = {
		
				def imageFileName = params.repositoryImageId			
		
		
				if(imageFileName)
				{
					def file = imageRepositoryService.retrieveImage(imageFileName)
					if (file != null) {
						response.outputStream << file.getInputStream()
						response.contentType = file.getContentType()
					}
		
				}
		
				
				return;
		
			}

	
	def productImage = {
		
		if (params.product_id) {
			def product = Product.findById(params.product_id)
			
			def imageFileName = product.photo
					
			
			if(imageFileName)
			{
				def file = imageRepositoryService.retrieveImage(imageFileName)
				if (file != null) {
					response.outputStream << file.getInputStream()
					response.contentType = file.getContentType()
				}
	
			}

		}
		return;

	}
	
	def pubLogoImage = {
		
		if (params.pubUri) {
			//def pub = PubEvent.findById(params.event_id)
			def pub = Pub.findByUri(params.pubUri)
			
			def imageFileName = pub.logoImage
					
			
			if(imageFileName)
			{
				def file = imageRepositoryService.retrieveImage(imageFileName)
				if (file != null) {
					response.outputStream << file.getInputStream()
					response.contentType = file.getContentType()
				}
	
			}

		}
		return;

	}
	
	def pubEventImage = {
		
		if (params.event_id) {
			def event = PubEvent.findById(params.event_id)
			
			def imageFileName = event.photo
					
			
			if(imageFileName)
			{
				def file = imageRepositoryService.retrieveImage(imageFileName)
				if (file != null) {
					response.outputStream << file.getInputStream()
					response.contentType = file.getContentType()
				}
	
			}

		}
		return;

	}
	
	def associationNewsImage = {
		
		if (params.associationNews_id) {
			def associationNews = AssociationNews.findById(params.associationNews_id)
			
			def imageFileName = associationNews.photo
					
			
			if(imageFileName)
			{
				def file = imageRepositoryService.retrieveImage(imageFileName)
				if (file != null) {
					response.outputStream << file.getInputStream()
					response.contentType = file.getContentType()
				}
	
			}

		}
		return;

	}
	
	def userPhoto = {
		
		if (params.user_id) {
			def user = User.findById(params.user_id)
			
			def imageFileName = user.photo
					
			
			if(imageFileName)
			{
				def file = imageRepositoryService.retrieveImage(imageFileName)
				if (file != null) {
					response.outputStream << file.getInputStream()
					response.contentType = file.getContentType()
				}
	
			}

		}
		return;

	}
	
	def associationLogo = {
		
		if (params.association_id) {
			def association = Association.findById(params.association_id)
			
			def imageFileName = association.photo
					
			
			if(imageFileName)
			{
				def file = imageRepositoryService.retrieveImage(imageFileName)
				if (file != null) {
					response.outputStream << file.getInputStream()
					response.contentType = file.getContentType()
				}
	
			}

		}
		return;

	}
	
	def featureImage = {
		
		if (params.feature_id) {
			def feature = PubFeature.findById(params.feature_id)
			
			def imageFileName = feature.repositoryImageId
					
			
			if(imageFileName)
			{
				def file = imageRepositoryService.retrieveImage(imageFileName)
				if (file != null) {
					response.outputStream << file.getInputStream()
					response.contentType = file.getContentType()
				}
	
			}

		}
		return;

	}
	
	
	def pubPromotionalImage = {
		
				def pubUri = params.pubUri
				def imageId = params.imageId
				def imageFileName = params.image
				
				def pub = Pub.findByUri(pubUri)
				
				def photo = PubPromotionalImage.findByPubAndId(pub,imageId)
				
		
				if(photo)
				{
					def file = imageRepositoryService.retrieveImage(imageFileName)
					if (file != null) {
						response.outputStream << file.getInputStream()
						response.contentType = file.getContentType()
					}
		
				}
		
				
				return;
		
			}
	
	
	def pubPromotionalImagePdf = {
		
				def event = PubEvent.findById(params.event)
				def pubUri = event.pub.uri
				
				/*def imageId = params.imageId
				def imageFileName = params.image*/
				
				def pub = Pub.findByUri(pubUri)
				
				//def image = PubPromotionalImage.findByPubAndDateFromLessThanEqualsAndDateToGreaterThanEquals(pub, event.dateEvent, event.dateEvent)
				
				def photo = PubPromotionalImage.findByPubAndDateFromLessThanEqualsAndDateToGreaterThanEquals(pub, event.dateEvent, event.dateEvent)
				
		
				if(photo)
				{
					def file = imageRepositoryService.retrieveImage(photo.image)
					if (file != null) {
						response.outputStream << file.getInputStream()
						response.contentType = file.getContentType()
					}
		
				}
		
				
				return;
		
			}
	
}
