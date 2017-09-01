# Grails Example for Many-To-Many

## Domains
- Author
- Book
- Book2Author

## Idea
Authors can write multiple books and books can be coauthored by multiple authors.
- If an author is created, some already existing books can be selected
- If a book is created, some already existing authors can be selected
- If a author is deleted, his books still exist (without him)
- If a book is deleted, his authors still exist (without it)

The last part seems to be a bit useless for books, but not for other more complicated examples 
(like QueueManager and QueueManagerGroups).

## Grails default restriction
One side has to be 'master' with belongsTo
- a deletion works only form that side (sql referential integrity problem...)
- a creation with known 'slave' entries works (the other entities are ignored)

## Example
Used an explicit domain for the join-table (Book2Author).
- deletion works out-of-the-box with one-to-many-logic
- create / update is customized in the controllers via save / update methods and customized fields-templates for the properties

## TODO (report bugs)
- cascade-delete is ignored in integration-tests and just works in run-app/tomcat
    - additional before-delete for integration-tests
- How to test controller-methods save/update with domain against database in integration-test?