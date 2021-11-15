<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layouts" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<layout:base>
    <jsp:attribute name="title">Hello World Message Creator</jsp:attribute>
    <jsp:attribute name="body">
        <%--@elvariable id="message" type="io.lana.simplespring.app.Message"--%>
        <form:form modelAttribute="message">
            <div class="mb-3">
                <form:label path="greet" cssClass="form-label">Greet clause</form:label>
                <form:input path="greet" cssClass="form-control" cssErrorClass="form-control is-invalid"
                            readonly="true" placeholder="Hello"/>
            </div>

            <div class="mb-3">
                <form:label path="target" cssClass="form-label">Target to greet</form:label>
                <form:input path="target" cssClass="form-control" cssErrorClass="form-control is-invalid"
                            readonly="true" placeholder="World"/>
            </div>

            <div>
                <a href="/hello" class="btn btn-primary btn-sm">Back</a>
            </div>
        </form:form>
    </jsp:attribute>
</layout:base>
