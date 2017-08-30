package org.test

class Book2Author implements Serializable{

    Book book
    Author author

    static mapping = {
        // last step or create controller wont work...
        version false
        id composite: ['book', 'author']
    }

    String toString() {"${book.title}-${author.name}"}


    static List<Book2Author> getBooks(Author a){
        if (a == null){
            return []
        }
        if (Author.findById(a.id) == null){
            return []
        }

        Book2Author.where { author == a }*.book
    }

    static List<Book2Author> getAuthors(Book b){
        if (b == null){
            return []
        }
        if (Book.findById(b.id) == null){
            return []
        }

        Book2Author.where { book == b }*.author
    }

    static boolean remove(Book b, Author a) {
        if (b != null && a != null) {
            Book2Author.where { book == b && author == a }.deleteAll()
        }
    }
}
