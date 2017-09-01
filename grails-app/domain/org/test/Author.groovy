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

    // cascade 'delete' is ignored in integrationtests - see Book and AuthorsAndBooksSpec
    def beforeDelete(){
        // books is null before Book2Author-entries are deleted in integrationtest
        Book2Author.where { author == this }.deleteAll()
    }

}
