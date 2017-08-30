package org.test

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

class AuthorController {

    static scaffold = Author

    @Transactional
    def save(Author author) {
        if (author == null) {
//            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        // store bookIds
        List<Long> bookIds = params.list('bookIds')
        println("bookIds: " + bookIds)
        params.bookIds = []

        // save author without bookIds
        if (author.hasErrors()) {
//            transactionStatus.setRollbackOnly()
            respond author.errors, view:'create'
            return
        }
        author.save flush:true

        // update author with bookIds (you need an id...)
        bookIds.each { bookId ->
            println("adding M2M with authorId ${author.id} and bookId: ${bookId}")
            new Book2Author(book: Book.findById(bookId), author: author).save(failOnError: true)
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'author.label', default: 'Author'), author.id])
                redirect author
            }
            '*' { respond author, [status: CREATED] }
        }
    }

    @Transactional
    def update(Author author) {
        if (author == null) {
//            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (author.hasErrors()) {
//            transactionStatus.setRollbackOnly()
            respond author.errors, view:'edit'
            return
        }
        List<Long> prev = Book2Author.getBooks(author).id
        List<Long> current = params.list('bookIds').collect{it as Long} //  collect needed - else its string...
        List<Long> intersection = prev.intersect(current)

        params.bookIds = []

        println("prev: ${prev}")
        println("current: ${current}")
        println("intersection: ${intersection}")

        List<Long> removed = prev.minus(intersection)
        List<Long> added = current.minus(intersection)

        println("added: ${added}")
        println("removed: ${removed}")

        removed.each{ bookId ->
            Book2Author.remove(Book.findById(bookId), author)
        }

        added.each { bookId ->
            new Book2Author(book: Book.findById(bookId), author: author).save(failOnError: true)
        }

        author.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'author.label', default: 'Author'), author.id])
                redirect author
            }
            '*'{ respond author, [status: OK] }
        }
    }
    

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'author.label', default: 'Author'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

}
