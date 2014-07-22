import java.util.Date;

import com.nidoapp.nidopub.domain.Association
import com.nidoapp.nidopub.domain.Carte
import com.nidoapp.nidopub.domain.CarteSection
import com.nidoapp.nidopub.domain.Customer
import com.nidoapp.nidopub.domain.CustomerReview
import com.nidoapp.nidopub.domain.Invoice
import com.nidoapp.nidopub.domain.Product
import com.nidoapp.nidopub.domain.Pub
import com.nidoapp.nidopub.domain.geo.Country
import com.nidoapp.nidopub.domain.i18n.I18nItem;
import com.nidoapp.nidopub.domain.i18n.I18nLanguage;
import com.nidoapp.nidopub.domain.i18n.I18nTranslation;
import com.nidoapp.nidopub.domain.request.CustomerRequest
import com.nidoapp.nidopub.domain.request.ProductRequest
import com.nidoapp.nidopub.domain.request.RequestOrder
import com.nidoapp.nidopub.domain.request.ServiceRequest
import com.nidoapp.nidopub.domain.user.Role;
import com.nidoapp.nidopub.domain.user.User;
import com.nidoapp.nidopub.domain.user.UserRole;

import grails.util.Environment

class BootStrap {
	
	def countryDialingService

    def init = { servletContext ->
		
		if(Country.count() == 0)
		{
			log.info "No existen paises. Se crea Spain."
			
			def dialCode = countryDialingService.getDialCode('ES')
			new Country(name: "España", code: "ES", dialCode: dialCode).save(flush:true, failOnError:true)


			assert Country.count() == 1
						
		}
		
		if(I18nLanguage.count() == 0)
		{
			log.info "No existen idiomas. Se crean..."
			
			new I18nLanguage(isoCode: "es", language:'Español').save(flush: true)
			new I18nLanguage(isoCode: "en", language:'English').save(flush: true)

			assert I18nLanguage.count() == 2
						
		}
		
		if(Role.count() == 0)
		{
			log.info "No existen roles de usuario. Se crean..."
			
			new Role(authority: Role.ROLE_SUPERADMIN).save(flush: true)
			new Role(authority: Role.ROLE_PUB_OWNER).save(flush: true)
			new Role(authority: Role.ROLE_ASSOCIATION_OWNER).save(flush: true)

			assert Role.count() == 3
						
		}
		
		if(User.count() == 0)
		{
			log.info "No existen usuarios. Se crea usuario superadministrador..."
			
			def superadminRole = Role.findByAuthority(Role.ROLE_SUPERADMIN)
			
			def superadminUser = new User(username: 'daniel@nidoapp.com', enabled: true, password: 'emenu', firstName:'Juan', lastName:'Rodriguez').save(flush: true)
			
			
			UserRole.create superadminUser, superadminRole, true
			
			assert User.count() == 1
			assert UserRole.count() == 1
						
		}
		
		
		
		Environment.executeForCurrentEnvironment {
			
				development {
					
					sampleData()
					
				}
				
				test {
					
					sampleData()
					
				}
		}
		
		
		
		
		
		
		log.info "Pubs: " + Pub.count()
		
		
		
    }
    def destroy = {
    }
	
	
	def private sampleData()
	{
		log.info "Inicializando datos de prueba en entorno de desarrollo..."
		
		
		
		if(Pub.count() == 0)
		{
			log.info "Incluyendo datos de prueba..."
			
			log.info "Incluyendo usuario para pub...."
			
			def userCount = User.count()
			def userRoleCount = UserRole.count()
			
			def pubOwnerRole = Role.findByAuthority(Role.ROLE_PUB_OWNER)
			
			def pubOwnerUser = new User(username: 'info@salalopez.com', enabled: true, password: 'emenu', firstName:'Sala López', lastName:'Pub').save(flush: true)
			
			
			UserRole.create pubOwnerUser, pubOwnerRole, true
			
			assert User.count() == userCount + 1
			assert UserRole.count() == userRoleCount + 1
			
			log.info "Creando pub...."
			
			def pub = new Pub(name: "Pub de prueba")
			pub.website = "http://www.pubdeprueba.com"
			pub.customerCapacity = 80
			//pub.tableCapacity = 8
			
			pub.country = Country.findByCode('ES')
			
			//pub.owner = pubOwnerUser
			//pub.save(flush:true, failOnError:true)
			
			pubOwnerUser.addToPubs(pub)
			pubOwnerUser.save(flush:true, failOnError:true)
			
			assert pubOwnerUser.pubs.contains(pub)
			assert pub.owner == pubOwnerUser
			
			
			log.info "Incluyendo usuario para asociacion...."
			
			userCount = User.count()
			userRoleCount = UserRole.count()
			
			def associationOwnerRole = Role.findByAuthority(Role.ROLE_ASSOCIATION_OWNER)
			
			def associationOwnerUser = new User(username: 'info@asociaciondeprueba.com', enabled: true, password: 'emenu', firstName:'Prueba', lastName:'Prueba').save(flush: true)
			
			
			UserRole.create associationOwnerUser, associationOwnerRole, true
			
			assert User.count() == userCount + 1
			assert UserRole.count() == userRoleCount + 1
			
			
			log.info "Creando asociacion...."
			
			def association = new Association(name:'Asociacion de prueba')
			association.addToPubs(pub)
			
			associationOwnerUser.association = association
			
			associationOwnerUser.save(flush: true, failOnError:true)
			
			
			def carte = new Carte(name: "Prueba")
			
			carte.description = "Descripcion de prueba"
			
			//carte.pub = pub
			//carte.save(flush:true, failOnError:true)
			
			//log.info "Carta en pub: " + pub.carte?.properties
			
			pub.carte = carte
			pub.save(flush:true, failOnError:true)
			
			log.info "Pub en Carta: " + carte.pub?.properties
			
			
			def carteSection = new CarteSection(name:"Carnes")
			carteSection.orderNumber = 1
			//carteSection.carte = carte
			//carteSection.save(flush:true, failOnError:true)
			
			//carte.addToSections(carteSection)
			
			carte.addToSections(carteSection)
			carte.save(flush:true, failOnError:true)
			
			
			
			def product1 = new Product(name:"Solomillo")
			product1.addToCarteSections(carteSection)
			product1.description = "Solomillo con patatas asadas"
			product1.photo = "url foto"
			
			product1.save(flush:true, failOnError:true)
			
			
			
			//def description = new I18nItem(defaultText:'Descripcion de menu 1')
			//description.addToTranslations(new I18nTranslation(lang:I18nLanguage.findByIsoCode('es'), text:'Desc en Espanyol')).save(flush:true, failOnError:true)
			
			/*log.debug "Description: " + description?.properties
			menu1.description = description
			
			menu1.save(flush:true, failOnError:true)
			
			def menu1Section = new MenuSection(name:"Primeros")
			menu1Section.menu = menu1
			menu1Section.orderNumber = 1
			menu1Section.save(flush:true, failOnError:true)
			
			
			plate1.addToMenuSections(menu1Section)
			plate1.save(flush:true, failOnError:true)*/
			
			
			log.info "Carta: " + pub?.carte?.properties
			
			
			/*def table = new PubTable(tableKey:'1')
			table.pub = pub
			table.save(flush:true, failOnError:true)*/
			
			
			// Cliente, peticion de comanda, y valoracion de plato
			
			def customer = new Customer(name:'juan', email:'cliente@hotmail.com', phone: '666666666')
			customer.country = Country.findByCode('ES')
			customer.save(flush:true, failOnError:true)
			
			def serviceRequest = new ServiceRequest()
			serviceRequest.table = table
			serviceRequest.save(flush:true, failOnError:true)
			
			
			def customerRequest = new CustomerRequest()
			customerRequest.customer = customer
			customerRequest.serviceRequest = serviceRequest			
			//serviceRequest.addToCustomerRequests(customerRequest)
			customerRequest.save(flush:true, failOnError:true)
			
			/*def menuRequest = new MenuRequest()
			menuRequest.menu = menu1
			menuRequest.customerRequest = customerRequest
			menuRequest.save(flush:true, failOnError:true)*/
			//menuRequest.addToPlateRequests(new PlateRequest (plate: plate1))
			
			def requestOrder = new RequestOrder()
			requestOrder.orderNumber = 1
			requestOrder.serviceRequest = serviceRequest
			requestOrder.save(flush:true, failOnError:true)
			
			def productRequest = new ProductRequest (product: product1)
			//productRequest.menuRequest = menuRequest
			productRequest.requestOrder = requestOrder
			productRequest.save(flush:true, failOnError:true)
			
			
			//log.info "MenuRequest: " + menuRequest?.properties
			
			//Se hace el pedido
			serviceRequest.state = ServiceRequest.STATE_PENDING
			serviceRequest.lastStateDate = new Date()
			serviceRequest.save(flush:true, failOnError:true)
			
			
			def invoice = new Invoice()
			invoice.pub = pub
			invoice.customer = customer
			invoice.invoiceFile = "dfdf"
			invoice.ammunt = serviceRequest?.calculatePrice()
			invoice.issueDate = new Date()	
			invoice.request = serviceRequest
			invoice.save(flush:true, failOnError:true)			
			
			
			def review = new CustomerReview()
			review.valoration = 8
			review.comments = "Muy bueno"
			review.date = new Date();
			review.invoice = invoice
			review.product = product1
			review.customer = customer			
			review.save(flush:true, failOnError:true)
			
		}
		
	}
}
