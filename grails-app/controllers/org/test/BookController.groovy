package org.test

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.OK

class BookController {

    static scaffold = Book

    @Transactional
    def save(Book book) {
        if (book == null) {
//            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        // store bookIds
        List<Long> authorIds = params.list('authorIds')
        println("authorIds: " + authorIds)
        params.authorIds = []

        // save author without bookIds
        if (book.hasErrors()) {
//            transactionStatus.setRollbackOnly()
            respond book.errors, view:'create'
            return
        }
        book.save flush:true

        // update author with bookIds (you need an id...)
        authorIds.each { authorId ->
            println("adding M2M with authorId ${authorId} and bookId: ${book.id}")
            new Book2Author(book: book, author: Author.findById(authorId)).save(failOnError: true)
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'book.label', default: 'Book'), book.id])
                redirect book
            }
            '*' { respond book, [status: CREATED] }
        }
    }

    @Transactional
    def update(Book book) {
        if (book == null) {
//            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (book.hasErrors()) {
//            transactionStatus.setRollbackOnly()
            respond book.errors, view:'edit'
            return
        }
        List<Long> prev = Book2Author.getAuthors(book).id
        List<Long> current = params.list('authorIds').collect{it as Long} //  collect needed - else its string...
        List<Long> intersection = prev.intersect(current)

        params.authorIds = []

        println("prev: ${prev}")
        println("current: ${current}")
        println("intersection: ${intersection}")

        List<Long> removed = prev.minus(intersection)
        List<Long> added = current.minus(intersection)

        println("added: ${added}")
        println("removed: ${removed}")

        removed.each{ authorId ->
            Book2Author.remove(book, Author.findById(authorId))
        }

        added.each { authorId ->
            new Book2Author(book: book, author: Author.findById(authorId)).save(failOnError: true)
        }

        book.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'book.label', default: 'Book'), book.id])
                redirect book
            }
            '*'{ respond book, [status: OK] }
        }
    }


    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'book.label', default: 'Book'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
