<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layouts" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<layout:base>
    <jsp:attribute name="title">Create Product</jsp:attribute>
    <jsp:attribute name="body">
        <h4>Create Product</h4>
        <form:form modelAttribute="entity">
            <div class="form-group mb-2">
                <form:label path="id" cssClass="form-label">Id</form:label>
                <form:input path="id" cssClass="form-control form-control-sm"
                            cssErrorClass="form-control form-control-sm is-invalid"/>
                <form:errors path="id" cssClass="invalid-feedback"/>
            </div>

            <div class="form-group mb-2">
                <form:label path="name" cssClass="form-label">Name</form:label>
                <form:input path="name" cssClass="form-control form-control-sm"
                            cssErrorClass="form-control is-invalid form-control-sm"/>
                <form:errors path="name" cssClass="invalid-feedback"/>
            </div>

            <div class="form-group mb-2">
                <form:label path="producer" cssClass="form-label">Producer</form:label>
                <form:input path="producer" cssClass="form-control form-control-sm"
                            cssErrorClass="form-control is-invalid form-control-sm"/>
                <form:errors path="producer" cssClass="invalid-feedback"/>
            </div>

            <div class="form-group mb-2">
                <form:label path="yearMaking" cssClass="form-label">Year Making</form:label>
                <form:input path="yearMaking" type="number" cssClass="form-control form-control-sm"
                            cssErrorClass="form-control form-control-sm is-invalid" step="1"/>
                <form:errors path="yearMaking" cssClass="invalid-feedback"/>
            </div>

            <div class="form-group mb-2">
                <form:label path="price" cssClass="form-label">Price</form:label>
                <form:input path="price" type="number" step="0.01" cssClass="form-control form-control-sm"
                            cssErrorClass="form-control form-control-sm is-invalid"/>
                <form:errors path="price" cssClass="invalid-feedback"/>
            </div>

            <div>
                <button class="btn btn-primary btn-sm" type="submit">Submit</button>
                <a class="btn btn-success btn-sm" href="/products">Back</a>
            </div>
        </form:form>
    </jsp:attribute>
</layout:base>