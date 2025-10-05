<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
  <h2>주문 상세 (주문번호: ${orderId})</h2>

  <table class="table table-striped">
    <thead>
    <tr>
      <th>상품명</th>
      <th>단가</th>
      <th>수량</th>
      <th>합계</th>
    </tr>
    </thead>
    <tbody>
    <c:set var="totalAmount" value="0" />
    <c:forEach var="item" items="${orderItems}">
      <tr>
        <td>${item.productName}</td>
        <td>${item.unitPrice}원</td>
        <td>${item.quantity}</td>
        <td><strong>${item.totalPrice}원</strong></td>
      </tr>
      <c:set var="totalAmount" value="${totalAmount + item.totalPrice}" />
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
      <td colspan="3" class="text-end"><strong>총 금액:</strong></td>
      <td><strong>${totalAmount}원</strong></td>
    </tr>
    </tfoot>
  </table>

  <a href="/mypage/orders.do" class="btn btn-secondary">목록으로</a>
</div>