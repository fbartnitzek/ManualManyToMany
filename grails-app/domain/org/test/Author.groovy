package org.test

class Author {

    String name
    static hasMany = [books: Book2Author]


    static constraints = {
        name [:]
        books [:]
    }

    static mapping = {
        books cascade: 'delete'
    }

    String toString(){name}

}
