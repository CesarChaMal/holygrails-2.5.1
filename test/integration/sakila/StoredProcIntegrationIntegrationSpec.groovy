package sakila

import grails.test.spock.IntegrationSpec

class StoredProcIntegrationIntegrationSpec extends IntegrationSpec {
    def storedProcService

    void "4 copies of film 1 at store 1"() {
        expect: 4 == storedProcService.getFilmsInStock(1, 1)
    }
}
