class UrlMappings {

	static mappings = {
		
		
		"/foto/pub/$pubUri/$photoId/$repositoryImageId"(controller: 'resources', action: 'pubImage')
		"/imagen/caracteristica-pub/$repositoryImageId"(controller: 'resources', action: 'pubFeatureImage')
		
		
		
		
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(controller: 'home')
		
		"/api/1.0/$action?/$id?"(controller: 'api1')
		
		
		
		
		"404"(view:'/404')
		"500"(view:'/500')
		
		
	}
}
