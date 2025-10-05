<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <h2>장바구니</h2>

    <c:if test="${param.error != null}">
        <div class="alert alert-danger">
                ${param.error}
        </div>
    </c:if>

    <c:choose>
        <c:when test="${empty cartItems}">
            <div class="alert alert-info">
                장바구니가 비어있습니다.
            </div>
            <a href="/index.do" class="btn btn-primary">쇼핑 계속하기</a>
        </c:when>
        <c:otherwise>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>이미지</th>
                    <th>상품명</th>
                    <th>단가</th>
                    <th>수량</th>
                    <th>합계</th>
                    <th>삭제</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${cartItems}">
                    <tr>
                        <td>
                            <img src="${pageContext.request.contextPath}${item.product.productImage}"
                                 alt="${item.product.productName}"
                                 style="width: 80px; height: 80px; object-fit: cover;">
                        </td>
                        <td>${item.product.productName}</td>
                        <td>${item.product.productUnitCost}원</td>
                        <td>${item.quantity}</td>
                        <td><strong>${item.totalPrice}원</strong></td>
                        <td>
                            <form action="/mypage/cart/delete.do" method="post" style="display:inline;">
                                <input type="hidden" name="cartId" value="${item.cartId}">
                                <button type="submit" class="btn btn-sm btn-danger">삭제</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="4" class="text-end"><strong>총 금액:</strong></td>
                    <td colspan="2"><strong>${totalAmount}원</strong></td>
                </tr>
                </tfoot>
            </table>

            <div class="d-flex justify-content-between mt-3">
                <a href="/index.do" class="btn btn-secondary">쇼핑 계속하기</a>
                <form action="/mypage/order/create.do" method="post" onsubmit="return confirm('주문하시겠습니까?');">
                    <button type="submit" class="btn btn-primary btn-lg">주문하기</button>
                </form>
            </div>
        </c:otherwise>
    </c:choose>
</div>