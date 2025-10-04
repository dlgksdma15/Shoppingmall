<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" trimDirectiveWhitespaces="true" %>

<div style="margin: auto; width: 400px;">
    <div class="p-2">
        <form method="post" action="/signup.do" onsubmit="return validatePassword()">

            <h1 class="h3 mb-3 fw-normal">회원가입</h1>

            <div class="form-floating mb-2">
                <input type="text" name="userId" class="form-control" id="userId" placeholder="아이디" required>
                <label for="userId">아이디</label>
            </div>

            <div class="form-floating mb-2">
                <input type="text" name="userName" class="form-control" id="userName" placeholder="이름" required>
                <label for="userName">이름</label>
            </div>

            <div class="form-floating mb-2">
                <input type="password" name="userPassword" class="form-control" id="userPassword" placeholder="비밀번호" required>
                <label for="userPassword">비밀번호</label>
            </div>

            <div class="form-floating mb-2">
                <input type="password" class="form-control" id="userPasswordCheck" placeholder="비밀번호 확인" required>
                <label for="userPasswordCheck">비밀번호 확인</label>
            </div>

            <div class="form-floating mb-2">
                <input type="text" name="userBirth" class="form-control" id="userBirth" placeholder="생년월일 (8자리)" required maxlength="8">
                <label for="userBirth">생년월일 (예: 19900101)</label>
            </div>

            <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">가입하기</button>

            <p class="mt-5 mb-3 text-muted">© 2022-2024</p>

        </form>
    </div>
</div>

<script>
function validatePassword() {
    var password = document.getElementById("userPassword");
    var confirmPassword = document.getElementById("userPasswordCheck");

    if (password.value !== confirmPassword.value) {
        alert("비밀번호가 일치하지 않습니다.");
        confirmPassword.focus();
        return false;
    }
    return true;
}
</script>
