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

                            <!-- 관리자만 Edit 버튼 표시 -->
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