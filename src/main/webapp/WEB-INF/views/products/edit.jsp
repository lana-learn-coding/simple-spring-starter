<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layouts" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<layout:base>
    <jsp:attribute name="title">Update Product</jsp:attribute>
    <jsp:attribute name="body">
        <h4>Update Product</h4>
        <form:form modelAttribute="entity" enctype="multipart/form-data">
            <div class="form-group mb-2">
                <form:label path="id" cssClass="form-label">Id</form:label>
                <form:input path="id" cssClass="form-control form-control-sm"
                            cssErrorClass="form-control form-control-sm is-invalid" readonly="true"/>
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

            <div class="form-group mb-2">
                <form:label path="category" cssClass="form-label">Category</form:label>
                <form:select path="category" items="${categories}" itemLabel="name" itemValue="id"
                             cssClass="form-select form-select-sm"
                             cssErrorClass="form-select form-select-sm is-invalid"/>
                <form:errors path="category" cssClass="invalid-feedback"/>
            </div>

            <div class="form-group mb-2">
                <label for="files" class="form-label">Images</label>
                <input type="file" name="files" id="files" class="form-control form-control-sm" multiple>
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
                <button class="btn btn-primary btn-sm" type="submit">Submit</button>
                <a class="btn btn-success btn-sm" href="/products">Back</a>
            </div>
        </form:form>
    </jsp:attribute>
</layout:base>
