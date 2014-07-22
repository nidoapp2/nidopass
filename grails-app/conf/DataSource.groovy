dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
    development {
        dataSource {
			dbCreate = "update"
			//username = "nidoapp_dev"
			username = "cust__nidoappdev"
			password = "nidocloud"
			//url = "jdbc:mysql://ec2-50-19-213-178.compute-1.amazonaws.com/nidoapp_nidopub_dev?autoReconnect=true"
			url = "jdbc:mysql://nidoapp.com:3306/cust_h0079u066_nidoapp_nidopub_dev?autoReconnect=true"
			driverClassName = "com.mysql.jdbc.Driver"
			pooling = true
			properties {
				   minEvictableIdleTimeMillis=1800000
				   timeBetweenEvictionRunsMillis=1800000
				   numTestsPerEvictionRun=3
				   testOnBorrow=true
				   testWhileIdle=true
				   testOnReturn=true
				   validationQuery="SELECT 1"
			}
        }
		/*dataSource_horecalegacy {
			dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
			readOnly = true
			url = "jdbc:mysql://ec2-23-21-211-172.compute-1.amazonaws.com/horeca_legacy?autoReconnect=true"
		    driverClassName = "com.mysql.jdbc.Driver"
		    username = "horeca"
		    password = "nidocloud"
		}*/
    }
    test {
        dataSource {
			dbCreate = "update"
			username = "cust__nidoapptes"
			//username = "cust__nidopass_t"
			password = "nidocloud"
			url = "jdbc:mysql://nidoapp.com:3306/cust_h0079u066_nidoapp_nidopub_test?autoReconnect=true"
			//url = "jdbc:mysql://nidoapp.com:3306/cust_h0079u066_nidoapp_nidopass_test?autoReconnect=true"
			driverClassName = "com.mysql.jdbc.Driver"
			pooling = true
			properties {
				   minEvictableIdleTimeMillis=1800000
				   timeBetweenEvictionRunsMillis=1800000
				   numTestsPerEvictionRun=3
				   testOnBorrow=true
				   testWhileIdle=true
				   testOnReturn=true
				   validationQuery="SELECT 1"
			}
        }
		/*dataSource_horecalegacy {
			dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
			readOnly = true
			url = "jdbc:mysql://ec2-23-21-211-172.compute-1.amazonaws.com/horeca_legacy?autoReconnect=true"
		    driverClassName = "com.mysql.jdbc.Driver"
		    username = "horeca"
		    password = "nidocloud"
		}*/
    }
    production {
        dataSource {
            dbCreate = "update"
			username = "cust__nidoapppro"
			password = "nidocloud"
			//url = "jdbc:mysql://ec2-176-34-253-124.eu-west-1.compute.amazonaws.com/nidoapp_nidopub_eu_prod?autoReconnect=true"
			url = "jdbc:mysql://nidoapp.com:3306/cust_h0079u066_nidoapp_nidopub_prod?autoReconnect=true"
			driverClassName = "com.mysql.jdbc.Driver"
			pooling = true
			properties {
				   minEvictableIdleTimeMillis=1800000
				   timeBetweenEvictionRunsMillis=1800000
				   numTestsPerEvictionRun=3
				   testOnBorrow=true
				   testWhileIdle=true
				   testOnReturn=true
				   validationQuery="SELECT 1"
			}
        }
		
		/*dataSource_horecalegacy {
			dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
			readOnly = true
			url = "jdbc:mysql://ec2-23-21-211-172.compute-1.amazonaws.com/horeca_legacy?autoReconnect=true"
			driverClassName = "com.mysql.jdbc.Driver"
			username = "horeca"
			password = "nidocloud"
		}*/
    }
}
