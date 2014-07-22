package com.nidoapp.nidopub.service

import org.springframework.beans.factory.InitializingBean
import com.mongodb.gridfs.GridFS
import com.mongodb.Mongo



/**
 * Servicio que permite realizar operaciones de almacenamiento y recuperacion de imagenes en un repositorio MongoDB
 * More info http://jameswilliams.be/blog/entry/171
 * 
 * @author Developer
 *
 */
class ImageRepositoryService implements InitializingBean{
	
	def grailsApplication
	def gridfs
	
    void afterPropertiesSet() {

    	Mongo mongo = new Mongo(grailsApplication.config.imagesMongoDatabase.databaseHost, grailsApplication.config.imagesMongoDatabase.databasePort);
    	def db = mongo.getDB(grailsApplication.config.imagesMongoDatabase.databaseName)
    	boolean auth = db.authenticate(grailsApplication.config.imagesMongoDatabase.databaseUsername, grailsApplication.config.imagesMongoDatabase.databasePassword.toCharArray());
		gridfs = new GridFS(db)
	}
	
	
	
	
	/**
	 * Guarda una imagen en mongo
	 * @param photoUrl URL de la imagen
	 * @return identificador de imagen guardada
	 */
	def saveImage(String photoUrl)
	{
		def imageId
		
		try {

			URL url = new URL(photoUrl);

			try{

				log.info "Guardando foto '${photoUrl}'"

				def inputStream = url.newInputStream()
				def fileName = UUID.randomUUID().toString()
				def contentType = "image/jpeg"
				
				def saved = saveFile(inputStream, contentType, fileName)
				if(saved && gridfs.findOne(fileName)){

					log.info "La imagen '${photoUrl}' se ha guardado en Mongo, con nombre '${fileName}'"

					imageId = fileName
				}


			}
			catch(IOException e)
			{
				log.error "Error en la imagen '${photoUrl}': " + e
			}

		}
		catch (MalformedURLException e)
		{
			log.error "La foto ${photo} no es una URL"

		}
		
		imageId
	}
	
	
	/**
	 * Guarda una imagen a partir de un stream de datos
	 * @param photoStream stream de datos
	 * @return identificador de la imagen guardada
	 */
	def saveImage(InputStream photoStream)
	{
		def imageId
		
	

			try{

				log.info "Guardando foto..."

				def inputStream = photoStream
				def fileName = UUID.randomUUID().toString()
				def contentType = "image/jpeg"
				
				def saved = saveFile(inputStream, contentType, fileName)
				if(saved && gridfs.findOne(fileName)){

					log.info "La imagense ha guardado en Mongo, con nombre '${fileName}'"

					imageId = fileName
				}


			}
			catch(IOException e)
			{
				log.error "Error en la imagen : " + e
			}

		
		
		imageId
	}
	
	
	
	/**
	 * 
	 * @param filename
	 * @return
	 */
	def retrieveImage(String filename) {
		return gridfs.findOne(filename)
	}
	
	
	
	/**
	 * Guarda una imagen indicando
	 * @param inputStream
	 * @param contentType
	 * @param filename
	 * @return
	 */
	private boolean saveFile(inputStream, contentType, filename) {
	    try {
	        if (gridfs.findOne(filename) == null) 
			{
	            save(inputStream, contentType, filename)
	        } 
			else 
			{
	            gridfs.remove(filename)
	            save(inputStream, contentType, filename)
	        }
	    } 
		catch (Exception ex) {
	       throw ex
	    }
	    return true
	}

	private def save(inputStream, contentType, filename) {
	    def inputFile = gridfs.createFile(inputStream)
	    inputFile.setContentType(contentType)
	    inputFile.setFilename(filename)
	    inputFile.save()
	}

	

	
}
