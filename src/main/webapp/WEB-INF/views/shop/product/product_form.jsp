<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <h2>상품 등록</h2>
    <form action="/admin/product/create.do" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="productName">상품명:</label>
            <input type="text" class="form-control" id="productName" name="productName" required>
        </div>
        <div class="form-group">
            <label for="productNumber">상품 수량:</label>
            <input type="number" class="form-control" id="productNumber" name="productNumber" required>
        </div>
        <div class="form-group">
            <label for="productImage">상품 이미지:</label>
            <input type="file" class="form-control-file" id="productImage" name="productImage">
        </div>
        <div class="form-group">
            <label for="productUnitCost">단가:</label>
            <input type="number" class="form-control" id="productUnitCost" name="productUnitCost" required>
        </div>
        <div class="form-group">
            <label for="productDescription">상품 설명:</label>
            <textarea class="form-control" id="productDescription" name="productDescription" rows="3"></textarea>
        </div>
        <div class="form-group">
            <label for="categoryId">카테고리:</label>
            <select class="form-control" id="categoryId" name="categoryId">
                <c:forEach var="category" items="${categories}">
                    <option value="${category.categoryId}">${category.categoryName}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">등록</button>
    </form>
</div>
