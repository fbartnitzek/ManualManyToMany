package org.test


import grails.test.mixin.integration.Integration
import grails.transaction.*
import groovy.sql.Sql
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.*

import javax.sql.DataSource

@Integration
@Rollback
class AuthorsAndBooksSpec extends Specification {

    def DataSource dataSource
    def Sql sql

    def setup() {
        sql = new Sql(dataSource)
    }

    def cleanup() {
        sql = null
    }

    Long queryTableSize(String table) {
        sql.rows("select * from " + table).size()
    }

    void "test domains"(){
        when:
            def author1 = new Author(name: name).save(failOnError: true)
            def author2 = new Author(name: "Adam Smith").save(failOnError: true)
            def author3 = new Author(name: "Stephen King").save(failOnError: true)
        then:
            author1 != null && author2 != null && author3 != null
            Author.count() == 3
            queryTableSize("author") == 3

        when:
            def book1 = new Book(title: "The Hitchhikers guide to the galaxy").save(failOnError: true)
            def book2 = new Book(title: "The wealth of nations").save(failOnError: true)
            def book3 = new Book(title: "The Shining").save(failOnError: true)
        then:
            book1 != null && book2 != null && book3 != null
            Book.count() == 3
            queryTableSize("book") == 3

        when:
            new Book2Author(book: book1, author: author1).save(flush: true, failOnError: true)
            new Book2Author(book: book2, author: author2).save(flush: true, failOnError: true)
            new Book2Author(book: book3, author: author3).save(flush: true, failOnError: true)
        then:
            Book2Author.count() == 3
            queryTableSize('book2author') == 3

        when:
            author1.delete(flush: true, failOnError: true)
        then:
            Book2Author.count() == 2
            Book.count() == 3
            Author.count() == 2
            Author.findByName(name) == null

        when:
            book3.delete(flush: true, failOnError: true)
        then:
            final DataIntegrityViolationException exception = thrown()
            Book.count() == 3 // still wrong...

        where:
            name = "Douglas Adams"
    }

}
