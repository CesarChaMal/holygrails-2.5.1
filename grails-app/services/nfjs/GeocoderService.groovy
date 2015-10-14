package nfjs

import grails.transaction.Transactional

@Transactional
class GeocoderService {
    public static final String BASE =
            'http://maps.googleapis.com/maps/api/geocode/xml?'

    def fillInLatLng(Castle castle) {
        println "Castle: $castle"
        String encoded = [castle.city, castle.state].collect {
            URLEncoder.encode(it, 'UTF-8')
        }.join(',')
        String qs = "address=$encoded"
        println "$BASE$qs"
        def root = new XmlSlurper().parse("$BASE$qs")
        def loc = root.result[0].geometry.location
        castle.latitude = loc.lat.toDouble()
        castle.longitude = loc.lng.toDouble()
        println "Castle ${castle.name}: ${castle.city}, ${castle.state}, (${castle.latitude},${castle.longitude})"
    }

    def headers() {
        [['number','Latitude'],['number','Longitude'],['string','Name']]
    }

    def data() {
        Castle.list().collect { c ->
            [c.latitude, c.longitude, c.toString()]
        }
    }
}
