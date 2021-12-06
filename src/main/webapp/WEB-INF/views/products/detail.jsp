<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layouts" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<layout:base>
    <jsp:attribute name="title">Product Detail</jsp:attribute>
    <jsp:attribute name="body">
        <h4>Product Detail</h4>
        <form:form modelAttribute="entity">
            <div class="form-group mb-2">
                <form:label path="id" cssClass="form-label">Id</form:label>
                <form:input path="id" cssClass="form-control form-control-sm"
                            cssErrorClass="form-control form-control-sm is-invalid" disabled="true"/>
            </div>

            <div class="form-group mb-2">
                <form:label path="name" cssClass="form-label">Name</form:label>
                <form:input path="name" cssClass="form-control form-control-sm"
                            cssErrorClass="form-control is-invalid form-control-sm" disabled="true"/>
            </div>

            <div class="form-group mb-2">
                <form:label path="producer" cssClass="form-label">Producer</form:label>
                <form:input path="producer" cssClass="form-control form-control-sm"
                            cssErrorClass="form-control is-invalid form-control-sm" disabled="true"/>
            </div>

            <div class="form-group mb-2">
                <form:label path="yearMaking" cssClass="form-label">Year Making</form:label>
                <form:input path="yearMaking" type="number" cssClass="form-control form-control-sm"
                            cssErrorClass="form-control form-control-sm is-invalid" step="1" disabled="true"/>
            </div>

            <div class="form-group mb-2">
                <form:label path="price" cssClass="form-label">Price</form:label>
                <form:input path="price" type="number" step="0.01" cssClass="form-control form-control-sm"
                            cssErrorClass="form-control form-control-sm is-invalid" disabled="true"/>
            </div>

             <div class="form-group mb-2">
                 <form:label path="category.name" cssClass="form-label">Category</form:label>
                 <form:input path="category.name" cssClass="form-control form-control-sm"
                             cssErrorClass="form-control is-invalid form-control-sm" disabled="true"/>
             </div>

            <c:if test="${not empty entity.images}">
                <div class="form-group mb-2 row" style="max-width: 300px">
                    <c:forTokens items="${entity.images}" delims="," var="item">
                        <div class="col-6">
                            <img src="${item}" alt="image" class="w-100 rounded">
                        </div>
                    </c:forTokens>
                </div>
            </c:if>

            <div>
                <a class="btn btn-primary btn-sm" href="/products">Back</a>
            </div>
        </form:form>
    </jsp:attribute>
</layout:base>
