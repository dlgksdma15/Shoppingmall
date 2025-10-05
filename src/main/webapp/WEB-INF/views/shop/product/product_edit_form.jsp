<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <h2>상품 수정</h2>
    <form action="/admin/product/edit.do" method="post" enctype="multipart/form-data">
        <input type="hidden" name="productId" value="${product.productId}">

        <div class="form-group mb-3">
            <label for="productName">상품명:</label>
            <input type="text"
                   class="form-control"
                   id="productName"
                   name="productName"
                   value="${product.productName}"
                   required>
        </div>

        <div class="form-group mb-3">
            <label for="productNumber">상품 수량:</label>
            <input type="number"
                   class="form-control"
                   id="productNumber"
                   name="productNumber"
                   value="${product.productNumber}"
                   required>
        </div>

        <div class="form-group mb-3">
            <label>현재 이미지:</label>
            <div>
                <img src="${pageContext.request.contextPath}${product.productImage}"
                     alt="${product.productName}"
                     style="max-width: 200px; max-height: 200px;">
            </div>
        </div>

        <div class="form-group mb-3">
            <label for="productImage">새 이미지 (선택사항):</label>
            <input type="file"
                   class="form-control-file"
                   id="productImage"
                   name="productImage">
            <small class="form-text text-muted">새 이미지를 선택하지 않으면 기존 이미지가 유지됩니다.</small>
        </div>

        <div class="form-group mb-3">
            <label for="productUnitCost">단가:</label>
            <input type="number"
                   class="form-control"
                   id="productUnitCost"
                   name="productUnitCost"
                   value="${product.productUnitCost}"
                   required>
        </div>

        <div class="form-group mb-3">
            <label for="productDescription">상품 설명:</label>
            <textarea class="form-control"
                      id="productDescription"
                      name="productDescription"
                      rows="3">${product.productDescription}</textarea>
        </div>

        <div class="form-group mb-3">
            <label for="categoryId">카테고리:</label>
            <select class="form-control" id="categoryId" name="categoryId">
                <c:forEach var="category" items="${categories}">
                    <option value="${category.categoryId}"
                        ${category.categoryId == product.categoryId ? 'selected' : ''}>
                            ${category.categoryName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">수정</button>
        <a href="/product/detail.do?productId=${product.productId}" class="btn btn-secondary">취소</a>
    </form>
</div>