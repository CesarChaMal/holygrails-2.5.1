import nfjs.*

class BootStrap {
    def geocoderService

    def init = { servletContext ->
        Quest q = new Quest(name: 'Seek the grail')
                .addToTasks(name: 'Run away from killer rabbit')
                .addToTasks(name: 'Answer the bridgekeeper', priority: 4)
                .addToTasks(name: 'Defeat the Black Knight', completed: true)
                .save()
        Castle camelot = new Castle(name: 'Camelot', city: 'Boston', state: 'MA')
                .addToKnights(title: 'King', name: 'Arthur', quest: q)
                .addToKnights(title: 'Sir', name: 'Lancelot', quest: q)
                .addToKnights(title: 'Sir', name: 'Robin', quest: q)
        geocoderService.fillInLatLng(camelot)
        camelot.save(failOnError: true)
        Castle swamp = new Castle(name: 'Swamp', city: 'Natick', state: 'MA')
        geocoderService.fillInLatLng(swamp)
        swamp.save(failOnError: true)
    }

    def destroy = {
    }
}
