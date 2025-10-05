<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container" style="max-width: 500px;">
    <h2>회원 정보 수정</h2>

    <form method="post" action="/mypage/edit.do">
        <div class="form-group mb-3">
            <label for="userId">아이디</label>
            <input type="text" class="form-control" id="userId" value="${user.userId}" disabled>
            <small class="form-text text-muted">아이디는 변경할 수 없습니다.</small>
        </div>

        <div class="form-group mb-3">
            <label for="userName">이름</label>
            <input type="text" class="form-control" id="userName" name="userName"
                   value="${user.userName}" required>
        </div>

        <div class="form-group mb-3">
            <label for="userPassword">비밀번호</label>
            <input type="password" class="form-control" id="userPassword" name="userPassword"
                   placeholder="변경할 비밀번호 입력" required>
        </div>

        <div class="form-group mb-3">
            <label for="userBirth">생년월일</label>
            <input type="text" class="form-control" id="userBirth" name="userBirth"
                   value="${user.userBirth}" maxlength="8" required>
            <small class="form-text text-muted">8자리 숫자 (예: 19900101)</small>
        </div>

        <div class="btn-group w-100">
            <button type="submit" class="btn btn-primary">수정</button>
            <a href="/mypage/index.do" class="btn btn-secondary">취소</a>
        </div>
    </form>
</div>