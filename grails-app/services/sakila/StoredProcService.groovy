package sakila

import grails.transaction.Transactional
import groovy.sql.Sql

@Transactional
class StoredProcService {
    def dataSource_sakila

    int getFilmsInStock(filmId, storeId) {
        String sql = "{call film_in_stock(?,?,?)}"
        Sql db = new Sql(dataSource_sakila)
        int result = 0
        db.call(sql, [filmId, storeId, Sql.INTEGER]) { count ->
            println count
            result = count
        }
        return result
    }
}
