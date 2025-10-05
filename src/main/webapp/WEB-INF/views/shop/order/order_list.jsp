<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container">
    <h2>주문 내역</h2>

    <c:if test="${param.success == 'true'}">
        <div class="alert alert-success">
            주문이 완료되었습니다!
        </div>
    </c:if>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="alert alert-info">
                주문 내역이 없습니다.
            </div>
            <a href="/index.do" class="btn btn-primary">쇼핑하러 가기</a>
        </c:when>
        <c:otherwise>
            <p>총 ${totalCount}건의 주문</p>

            <table class="table table-hover">
                <thead>
                <tr>
                    <th>주문번호</th>
                    <th>주문일시</th>
                    <th>총 금액</th>
                    <th>상세보기</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="order" items="${orders}">
                    <tr>
                        <td>${order.orderId}</td>
                        <td>
                            <fmt:formatDate value="${order.orderedAt}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </td>
                        <td><strong>${order.totalAmount}원</strong></td>
                        <td>
                            <a href="/mypage/order/detail.do?orderId=${order.orderId}"
                               class="btn btn-sm btn-primary">상세보기</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <!-- 페이징 -->
            <nav aria-label="Page navigation">
                <ul class="pagination justify-content-center">
                    <!-- 이전 페이지 -->
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" href="/mypage/orders.do?page=${currentPage - 1}">이전</a>
                    </li>

                    <!-- 페이지 번호 -->
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${i == currentPage ? 'active' : ''}">
                            <a class="page-link" href="/mypage/orders.do?page=${i}">${i}</a>
                        </li>
                    </c:forEach>

                    <!-- 다음 페이지 -->
                    <li class="page-item ${currentPage >= totalPages ? 'disabled' : ''}">
                        <a class="page-link" href="/mypage/orders.do?page=${currentPage + 1}">다음</a>
                    </li>
                </ul>
            </nav>
        </c:otherwise>
    </c:choose>
</div>