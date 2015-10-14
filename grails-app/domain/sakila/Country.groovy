package sakila

class Country {
    Short id
    String name
    Date lastUpdated

    String toString() { name }

    static hasMany = [cities: City]

    static mapping = {
        id column: 'country_id'
        name column: 'country'
        lastUpdated column: 'last_update'
        version false
        datasource('sakila')
    }

    static constraints = {
        name blank: false
    }
}
