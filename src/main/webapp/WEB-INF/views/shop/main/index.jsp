<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
    <c:forEach var="product" items="${productList}">
        <div class="col">
            <div class="card shadow-sm">
                <img src="${pageContext.request.contextPath}${product.productImage}"
                     class="bd-placeholder-img card-img-top"

                     width="100%"
                     height="225"
                     alt="${product.productName}">
                <div class="card-body">
                    <p class="card-text">${product.productName}</p>

                    <div class="d-flex justify-content-between align-items-center">
                        <div class="btn-group">
                            <a href="/product/detail.do?productId=${product.productId}"
                               class="btn btn-sm btn-outline-secondary">View</a>


                            <c:if test="${not empty sessionScope.user && sessionScope.user.userAuth == 'ROLE_ADMIN'}">
                                <a href="/admin/product/edit.do?productId=${product.productId}"

                                   class="btn btn-sm btn-outline-primary">Edit</a>
                            </c:if>
                        </div>

                        <small class="text-muted">${product.productUnitCost}원</small>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<div class="row mt-4">
    <div class="col">
        <nav aria-label="Page navigation">
            <ul class="pagination justify-content-center">
                <c:choose>
                    <c:when test="${currentPage > 1}">
                        <li class="page-item">
                            <a class="page-link" href="/index.do?page=${currentPage - 1}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item disabled">
                            <span class="page-link" aria-hidden="true">&laquo;</span>
                        </li>
                    </c:otherwise>
                </c:choose>

                <c:forEach begin="1" end="${totalPages}" var="i">
                    <c:choose>
                        <c:when test="${i == currentPage}">
                            <li class="page-item active" aria-current="page"><span class="page-link">${i}</span></li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item"><a class="page-link" href="/index.do?page=${i}">${i}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:choose>
                    <c:when test="${currentPage < totalPages}">
                        <li class="page-item">
                            <a class="page-link" href="/index.do?page=${currentPage + 1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item disabled">
                            <span class="page-link" aria-hidden="true">&raquo;</span>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </div>
</div>