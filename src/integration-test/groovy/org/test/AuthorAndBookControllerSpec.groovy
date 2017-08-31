package org.test

import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import grails.util.GrailsWebMockUtil
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

import javax.sql.DataSource

@Integration
@Rollback
class AuthorAndBookControllerSpec extends Specification {

    @Autowired
    AuthorController authorController
    @Autowired
    WebApplicationContext ctx

    def DataSource dataSource
    def Sql sql

    def setup() {
        sql = new Sql(dataSource)
//        GrailsWebMockUtil.bindMockWebRequest(ctx)
    }

    def cleanup() {
        sql = null
    }

    Long queryTableSize(String table) {
        sql.rows("select * from " + table).size()
    }

//    void "show author index view"(){
//        given: 'delete previous'
//            Author.list()*.delete()
//
//        and: 'create author entries'
//        n.times {
//            new Author(name: UUID.randomUUID() as String).save(failOnError: true)
//        }
//        expect:
//            queryTableSize('author') == size
//
//        when: 'call the index action and store result'
//            Map result = authorController.index()
//
//        then:
//            result.authorCount == size
//
//
//        where:
//            n   ||  size
//            0   ||  0
//            1   ||  1
//            2   ||  2
//            5   ||  5
//            10  ||  10
//    }

//    void "create author view"(){
//        given:
//            authorController.params.name = name
//
//        when:
//            authorController.save()
//
//        then:
////            authorController.flash.message.contains("created")
//            queryTableSize('author') == 1
//
//        where:
//            name = "Douglas Adams"
//    }


}
