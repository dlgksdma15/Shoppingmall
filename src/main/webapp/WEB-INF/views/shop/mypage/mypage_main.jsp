<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <h2>마이페이지</h2>

    <div class="card mb-3">
        <div class="card-header">
            <h5>회원 정보</h5>
        </div>
        <div class="card-body">
            <p><strong>아이디:</strong> ${user.userId}</p>
            <p><strong>이름:</strong> ${user.userName}</p>
            <p><strong>생년월일:</strong> ${user.userBirth}</p>
            <p><strong>권한:</strong> ${user.userAuth}</p>
            <p><strong>포인트:</strong> ${user.userPoint}원</p>

            <div class="btn-group mt-3">
                <a href="/mypage/edit.do" class="btn btn-primary">정보 수정</a>
                <form action="/mypage/delete.do" method="post" style="display:inline;"
                      onsubmit="return confirm('정말 탈퇴하시겠습니까? 이 작업은 취소할 수 없습니다.');">
                    <button type="submit" class="btn btn-danger">회원 탈퇴</button>
                </form>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6 mb-3">
            <div class="card">
                <div class="card-body text-center">
                    <h5 class="card-title">장바구니</h5>
                    <p class="card-text">장바구니에 담긴 상품을 확인하세요.</p>
                    <a href="/mypage/cart.do" class="btn btn-primary">장바구니 보기</a>
                </div>
            </div>
        </div>

        <div class="col-md-6 mb-3">
            <div class="card">
                <div class="card-body text-center">
                    <h5 class="card-title">주문 내역</h5>
                    <p class="card-text">지금까지의 주문 내역을 확인하세요.</p>
                    <a href="/mypage/orders.do" class="btn btn-primary">주문 내역 보기</a>
                </div>
            </div>
        </div>
    </div>
</div>