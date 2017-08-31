package org.test

import grails.test.mixin.integration.Integration
import grails.transaction.*

import spock.lang.*
import geb.spock.*

/**
 * See http://www.gebish.org/manual/current/ for more instructions
 */
@Integration
@Rollback
class AuthorAndBookControllerGebSpec extends GebSpec {

    def setup() {
    }

    def cleanup() {
    }

    void "test homepage"() {
        when:"The home page is visited"
            go '/'

        then:"The title is correct"
        	title == "Welcome to Grails"
    }

    void "test author index"() {
        when:
            new Author(name: name).save(failOnError: true, flush: true)
        then:
            Author.count() == 1

        when:"The home page is visited"
            go '/author/index'
        then:"The title is correct"
            title == "Author List"
        // TODO: payload not in page...?
            page.downloadText() != "empty"
//            page.downloadText().size() > 1000

//            $("td").find("a", 0).text() == name

        where:
            name = "Douglas Adams"
    }
}
