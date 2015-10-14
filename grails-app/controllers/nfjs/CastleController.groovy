package nfjs


import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CastleController {
    def geocoderService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Castle.list(params), model: [castleInstanceCount: Castle.count(),
            mapColumns: geocoderService.headers(), mapData: geocoderService.data()]
    }

    def show(Castle castleInstance) {
        respond castleInstance
    }

    def edit(Castle castleInstance) {
        respond castleInstance
    }

    def create() {
        respond new Castle(params)
    }

    @Transactional
    def save(Castle castleInstance) {
        if (castleInstance == null) {
            notFound()
            return
        }

        if (castleInstance.hasErrors()) {
            respond castleInstance.errors, view: 'create'
            return
        }

        geocoderService.fillInLatLng(castleInstance)
        castleInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'castle.label', default: 'Castle'), castleInstance.id])
                redirect castleInstance
            }
            '*' { respond castleInstance, [status: CREATED] }
        }
    }

    @Transactional
    def update(Castle castleInstance) {
        if (castleInstance == null) {
            notFound()
            return
        }

        if (castleInstance.hasErrors()) {
            respond castleInstance.errors, view: 'edit'
            return
        }

        geocoderService.fillInLatLng(castleInstance)
        castleInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Castle.label', default: 'Castle'), castleInstance.id])
                redirect castleInstance
            }
            '*' { respond castleInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Castle castleInstance) {

        if (castleInstance == null) {
            notFound()
            return
        }

        castleInstance.delete flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Castle.label', default: 'Castle'), castleInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'castle.label', default: 'Castle'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
