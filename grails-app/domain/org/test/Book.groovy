package org.test

class Book {

    String title
    static hasMany = [authors: Book2Author]

    static constraints = {
        title [:]
        authors [:]
    }

    static mapping = {
        authors cascade: 'delete'
    }

    String toString(){title}

}
