<%@ page import="org.test.Book2Author; org.test.Author" %>

<div class="fieldcontain">
    <div class="col-sm-2">
        <label for="authorIds">
            ${label}
            <g:if test="${required}"><span class="required-indicator">*</span></g:if>
        </label>
    </div>
    <div class="col-sm-10">
        <g:select name="authorIds" from="${Author.list()}" value="${Book2Author.getAuthors(bean)*.id}" optionKey="id" class="form-control" multiple="true"/>
    </div>
</div>