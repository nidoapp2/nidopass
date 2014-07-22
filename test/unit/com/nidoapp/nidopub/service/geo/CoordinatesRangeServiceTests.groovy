package com.nidoapp.nidopub.service.geo



import grails.test.mixin.*

import org.junit.*

import com.nidoapp.nidopub.service.geo.CoordinatesRangeService;

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CoordinatesRangeService)
class CoordinatesRangeServiceTests {

    void testRange() {
		
		def values = service.getRange(41.664962,-0.8666,100)
		print values
    }
}
