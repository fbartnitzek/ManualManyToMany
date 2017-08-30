package org.test

import grails.util.Environment

class BootStrap {

    def init = { servletContext ->

        if (Environment.current == Environment.DEVELOPMENT) {
            def author1 = new Author(name: "Douglas Adams").save(failOnError: true)
            def author2 = new Author(name: "Adam Smith").save(failOnError: true)
            def author3 = new Author(name: "Stephen King").save(failOnError: true)

            def book1 = new Book(title: "The Hitchhikers guide to the galaxy").save(failOnError: true)
            def book2 = new Book(title: "The wealth of nations").save(failOnError: true)
            def book3 = new Book(title: "The Shining").save(failOnError: true)

            new Book2Author(book: book1, author: author1).save(failOnError: true)
            new Book2Author(book: book2, author: author2).save(failOnError: true)
            new Book2Author(book: book3, author: author3).save(failOnError: true)
        }
    }
    def destroy = {


    }
}
