<%@ page import="org.test.Book2Author; org.test.Book" %>
<div class="fieldcontain">
    <div class="col-sm-2">
        <label for="bookIds">
            ${label}
            <g:if test="${required}"><span class="required-indicator">*</span></g:if>
        </label>
    </div>

    <div class="col-sm-10">
        <g:select name="bookIds" from="${Book.list()}" value="${Book2Author.getBooks(bean)*.id}" optionKey="id" class="form-control" multiple="true"/>
    </div>
</div>