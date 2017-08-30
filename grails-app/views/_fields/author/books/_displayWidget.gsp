<ul>
    <g:each in="${org.test.Book2Author.getBooks(bean)}" var="it" status="i">
        <li>
            <g:link controller="book" action="show" id="${it.id}">${it.toString()}</g:link>
        </li>
    </g:each>
</ul>