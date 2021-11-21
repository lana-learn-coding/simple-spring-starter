<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layouts" %>
<%@taglib prefix="component" tagdir="/WEB-INF/tags/components" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="helper" tagdir="/WEB-INF/tags/helpers" %>

<jsp:useBean id="data" scope="request" type="java.util.List<io.lana.simplespring.product.Product>"/>
<jsp:useBean id="meta" scope="request" type="io.lana.simplespring.lib.repo.pageable.Page.PageMeta"/>
<layout:base>
    <jsp:attribute name="title">List Customers</jsp:attribute>
    <jsp:attribute name="styles">
        <style>
        .truncate {
          max-width: 100px;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }
        </style>
    </jsp:attribute>
    <jsp:attribute name="body">
        <div class="container">
            <div class="d-flex mb-3 align-items-center">
                <div class="h4 mb-0">Products App</div>
                <div class="flex-grow-1"></div>
                <a class="btn btn-primary btn-sm" href="/products/create">New Product</a>
            </div>
            <form method="get" class="d-flex">
                <helper:inherit-param excludes="search"/>
                <input type="text"
                       style="max-width: 300px"
                       class="form-control form-control-sm"
                       name="search"
                       placeholder="Search item"
                       aria-label="search"
                       value="${param.search}">
                <button class="btn btn-sm btn-primary ms-2" type="submit">Search</button>
            </form>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col" class="truncate">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Producer</th>
                    <th scope="col">Year Making</th>
                    <th scope="col">Price</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${data}" varStatus="loop">
                    <tr>
                        <th scope="row">${loop.index + 1}</th>
                        <td class="truncate">${ item.id }</td>
                        <td>${ item.name }</td>
                        <td>${ item.producer }</td>
                        <td>${ item.yearMaking }</td>
                        <td>${ item.price }</td>
                        <td>
                            <a href="/products/detail/${item.id}" class="btn btn-primary btn-sm me-1">View</a>
                            <a href="/products/update/${item.id}" class="btn btn-primary btn-sm me-1">Edit</a>
                            <a onclick="return confirm('Are you sure delete this product: ${item.name}')"
                               href="/products/delete/${item.id}" class="btn btn-danger btn-sm me-1">Del</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <nav class="d-flex justify-content-center">
                <component:pagination pageMeta="${meta}"/>
                <div class="flex-grow-1"></div>
                <div>
                    <component:sorting labels="Name,Year" values="name desc,year_making desc"/>
                </div>
            </nav>
        </div>
    </jsp:attribute>
</layout:base>
