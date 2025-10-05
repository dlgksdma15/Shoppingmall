<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
  <c:if test="${param.error == 'duplicate'}">
    <div class="alert alert-warning">
      이미 장바구니에 담긴 상품입니다.
    </div>
  </c:if>

  <div class="row">
    <div class="col-md-6">
      <img src="${pageContext.request.contextPath}${product.productImage}"
           class="img-fluid"
           alt="${product.productName}">
    </div>
    <div class="col-md-6">
      <h2>${product.productName}</h2>
      <hr>
      <p class="lead"><strong>가격:</strong> ${product.productUnitCost}원</p>
      <p><strong>재고:</strong> ${product.productNumber}개</p>
      <p><strong>상품 설명:</strong></p>
      <p>${product.productDescription}</p>

      <hr>

      <!-- 로그인한 사용자만 장바구니 담기 가능 -->
      <c:if test="${not empty sessionScope.user}">
        <form action="/mypage/cart/add.do" method="post" class="mb-3">
          <input type="hidden" name="productId" value="${product.productId}">
          <div class="input-group mb-3" style="max-width: 200px;">
            <label class="input-group-text">수량</label>
            <input type="number" class="form-control" name="quantity"
                   value="1" min="1" max="${product.productNumber}" required>
          </div>
          <button type="submit" class="btn btn-success btn-lg">장바구니 담기</button>
        </form>
      </c:if>

      <div class="btn-group" role="group">
        <a href="/index.do" class="btn btn-secondary">목록으로</a>

        <!-- 관리자만 수정/삭제 가능 -->
        <c:if test="${not empty sessionScope.user && sessionScope.user.userAuth == 'ROLE_ADMIN'}">
          <a href="/admin/product/edit.do?productId=${product.productId}"
             class="btn btn-primary">수정</a>

          <form action="/admin/product/delete.do" method="post" style="display:inline;"
                onsubmit="return confirm('정말 삭제하시겠습니까?');">
            <input type="hidden" name="productId" value="${product.productId}">
            <button type="submit" class="btn btn-danger">삭제</button>
          </form>
        </c:if>
      </div>
    </div>
  </div>
</div>