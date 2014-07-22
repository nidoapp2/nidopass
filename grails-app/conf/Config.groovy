// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*', '/map_and_pdf/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
		grails.serverURL = "http://localhost:8080/${appName}"
		//grails.serverURL = "http://nidopub.nidoapp.cloudbees.net"
		
		imagesMongoDatabase.databaseName = "nidoapp_nidopub_images_dev"
		imagesMongoDatabase.databaseHost = "oceanic.mongohq.com"
		imagesMongoDatabase.databasePort = 10015
		imagesMongoDatabase.databaseUsername = "nidoapp"
		imagesMongoDatabase.databasePassword = "nidocloud"	
		
		// Cuenta en Redis Cloud (redis-cloud.com). Cuenta: jrferia@nidoapp.com // nidocloud
		grails {
			redis {
				poolConfig {
					// jedis pool specific tweaks here, see jedis docs & src
					// ex: testWhileIdle = true
				}
				port = 12619
				host = "pub-redis-12619.eu-west-1-1.1.ec2.garantiadata.com"
				timeout = 2000 //default in milliseconds
				password = "nidocloud" //defaults to no password
			}
		}
		
		/*sms.bulkSms.uri = "http://bulksms.com.es:5567"
		sms.bulkSms.username = "nidoapp2"
		sms.bulkSms.password = "nidocloud"
		sms.limits = true
		sms.recipients = ["34658475697", "34654148478", "34655890699", "34699140181"]*/
		mail.limits = true
		mail.recipients = ["info@nidoapp.com", "jrferia@nidoapp.com", "daniel@nidoapp.com", "dmv@nidoapp.com", "diego@morteo.info", "diego@gihsoft.com", "francisco@nidoapp.com"]
		
		mail.from = "NidoPub Dev <no-reply@nidoapp.com>"
		mail.support = "info@nidoapp.com"
		
		tpv.encryptionKey = 'PU4TFT8T'
		
    }
	
	
	test {
		grails.logging.jul.usebridge = true
		//grails.serverURL = "http://nidopub-eu-test.nidoapp.eu.cloudbees.net"
		grails.serverURL = "http://212.97.161.15/nidopass-test"
		//grails.serverURL = "http://nidopass-eu-prod.nidoapp2.eu.cloudbees.net"
		
		imagesMongoDatabase.databaseName = "nidoapp_nidopub_images_test"
		imagesMongoDatabase.databaseHost = "oceanic.mongohq.com"
		imagesMongoDatabase.databasePort = 10019
		imagesMongoDatabase.databaseUsername = "nidoapp"
		imagesMongoDatabase.databasePassword = "nidocloud"
		
	
		// Cuenta en Redis Cloud (redis-cloud.com). Cuenta: daniel@nidoapp.com // nidocloud	
		grails {
			redis {
				poolConfig {
					// jedis pool specific tweaks here, see jedis docs & src
					// ex: testWhileIdle = true
				}
				port = 12779
				host = "pub-redis-12779.eu-west-1-1.2.ec2.garantiadata.com"
				timeout = 2000 //default in milliseconds
				password = "nidocloud" //defaults to no password
			}
		}
		
		
		/*sms.bulkSms.uri = "http://bulksms.com.es:5567"
		sms.bulkSms.username = "nidoapp2"
		sms.bulkSms.password = "nidocloud"
		sms.limits = true
		sms.recipients = ["34658475697", "34654148478", "34655890699", "34699140181"]*/
		mail.limits = true
		mail.recipients = ["info@nidoapp.com", "jrferia@nidoapp.com", "daniel@nidoapp.com", "dmv@nidoapp.com", "diego@morteo.info", "diego@gihsoft.com", "francisco@nidoapp.com"]
		
		mail.from = "NidoPub Test <no-reply@nidoapp.com>"
		mail.support = "info@nidoapp.com"
		
		tpv.encryptionKey = 'PU4TFT8T'
		
		quartz.autoStartup = true
		
	}
	
	
	
    production {
        grails.logging.jul.usebridge = false
        //grails.serverURL = "http://pass.nidoapp.com"
		grails.serverURL = "http://nidopub-eu-prod.nidoapp.eu.cloudbees.net"
		//grails.serverURL = "http://212.97.161.15/nidopass"
		
		imagesMongoDatabase.databaseName = "nidoapp_nidopub_images_prod"
		imagesMongoDatabase.databaseHost = "oceanic.mongohq.com"
		imagesMongoDatabase.databasePort = 10021
		imagesMongoDatabase.databaseUsername = "nidoapp"
		imagesMongoDatabase.databasePassword = "nidocloud"
		
		// Cuenta en Redis Cloud (redis-cloud.com). Cuenta: dmv@nidoapp.com // nidocloud
		grails {
			redis {
				poolConfig {
					// jedis pool specific tweaks here, see jedis docs & src
					// ex: testWhileIdle = true
				}
				port = 12247
				host = "pub-redis-12247.eu-west-1-1.1.ec2.garantiadata.com"
				timeout = 2000 //default in milliseconds
				password = "nidocloud" //defaults to no password
			}
		}
		
		/*sms.bulkSms.uri = "http://bulksms.com.es:5567"
		sms.bulkSms.username = "nidoapp"
		sms.bulkSms.password = "nidocloud"*/	
		
		mail.from = "NidoPub <no-reply@nidoapp.com>"
		mail.support = "info@nidoapp.com"
		
		tpv.encryptionKey = 'IYD4SKGT'

    }
}

grails{
	mail{
		host = "mail.nidoapp.com"
		port = 587
		username = "nidorest@nidoapp.com"
		password = "nidocloud" 
	}
}




// log4j configuration
log4j = {
    // Example of defining the default console appender
    //
    appenders {
        console name:'stdout', layout:pattern(conversionPattern: '%d [%t] %-5p %c - %m%n'), threshold: org.apache.log4j.Level.DEBUG
    }//

    error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
           'org.codehaus.groovy.grails.web.pages', //  GSP
           'org.codehaus.groovy.grails.web.sitemesh', //  layouts
           'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
           'org.codehaus.groovy.grails.web.mapping', // URL mapping
           'org.codehaus.groovy.grails.commons', // core / classloading
           'org.codehaus.groovy.grails.plugins', // plugins
           'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
           'org.springframework',
           'org.hibernate',
           'net.sf.ehcache.hibernate'

    warn   'org.mortbay.log'

    info  'grails.app'

    root {
       error 'stdout'
       info 'stdout'
       warn 'stdout'
       //debug 'stdout'
       additivity = true
    }
}


// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.nidoapp.nidopub.domain.user.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.nidoapp.nidopub.domain.user.UserRole'
grails.plugins.springsecurity.authority.className = 'com.nidoapp.nidopub.domain.user.Role'


//grails.secure.api.consumers = ["my_first_api_user": "the_secret_of_my_first_api_user", "my_second_api_user": "the_secret_of_my_second_api_user"]
