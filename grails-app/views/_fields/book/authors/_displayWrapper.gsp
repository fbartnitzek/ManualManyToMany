<ul>
    <g:each in="${org.test.Book2Author.getAuthors(bean)}" var="it" status="i">
        <li>
            <g:link controller="author" action="show" id="${it.id}">${it.toString()}</g:link>
        </li>
    </g:each>
</ul>