package nfjs

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(GeocoderService)
class GeocoderServiceSpec extends Specification {

    void "check Mountain View, CA"() {
        given:
        Castle google = new Castle(name: 'Google', city: 'Mountain View', state: 'CA')

        when:
        service.fillInLatLng(google)

        then:
        (google.latitude - 37.4).abs() < 0.1
        (google.longitude - -122.1).abs() < 0.1
    }
}
