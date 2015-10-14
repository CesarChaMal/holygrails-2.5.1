package sakila

class City {
    Short id
    String name
    Date lastUpdated

    String toString() { name }

    static belongsTo = [country: Country]

    static mapping = {
        id column: 'city_id'
        name column: 'city'
        lastUpdated column: 'last_update'
        version(false)
        datasource('sakila')
    }

    static constraints = {
        name blank: false
    }
}
