<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>비밀번호 변경</title>
    <style>
        body {
            font-family: 'Pretendard', sans-serif;
            background-color: #EFEAD8;
            margin: 0;
            padding: 0;
        }

        .pw-container {
            max-width: 400px;
            margin: 100px auto;
            padding: 30px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #f9f9f9;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
        }

        .pw-container h1 {
            text-align: center;
            margin-bottom: 30px;
            font-family: 'LINESeedKR-Bd', sans-serif;
        }

        .form-group {
            margin-bottom: 20px;
            display: flex;
            flex-direction: column;
        }

        .form-group label {
            margin-bottom: 5px;
            font-weight: bold;
        }

        .form-group input {
            height: 35px;
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #f7f7f7;
        }

        .error-message {
            color: red;
            font-size: 12px;
            margin-top: 5px;
            height: 15px;
            visibility: hidden;
        }

        .error-message.visible {
            visibility: visible;
        }

        .pw-container button {
            width: 100%;
            padding: 10px;
            background-color: #A2CA71;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
            font-family: 'Pretendard', sans-serif;
        }

        .pw-container button:hover {
            background-color: #CEDEBD;
        }

        .cancel-btn {
            background-color: #FF6B6B;
        }

        .cancel-btn:hover {
            background-color: #FF4C4C;
        }
    </style>
</head>
<body>
<%
    String sessionPassword = (String) session.getAttribute("password");
%>

<form action="/rebook/user/pwchange.do" method="POST" onsubmit="return validatePasswordChange();">
    <div class="pw-container">
        <h1>비밀번호 변경</h1>

        <div class="form-group">
            <label for="currentPw">현재 비밀번호:</label>
            <input type="password" id="currentPw" name="currentPw" required 
                   onblur="checkCurrentPassword()" oninput="checkCurrentPassword()">
            <span class="error-message" id="currentPw-error">현재 비밀번호가 올바르지 않습니다.</span>
        </div>

        <div class="form-group">
            <label for="newPw">새 비밀번호:</label>
            <input type="password" id="newPw" name="newPw" required 
                   oninput="validateNewPassword()">
            <span class="error-message" id="newPw-error">비밀번호는 8~15자, 대/소문자, 숫자, 특수문자를 포함해야 합니다.</span>
        </div>

        <div class="form-group">
            <label for="confirmPw">새 비밀번호 확인:</label>
            <input type="password" id="confirmPw" name="confirmPw" required 
                   oninput="validatePasswordMatch()">
            <span class="error-message" id="confirmPw-error">비밀번호가 일치하지 않습니다.</span>
        </div>

        <button type="submit">비밀번호 변경</button>
        <button type="button" class="cancel-btn" onclick="history.back()">취소</button>
    </div>
</form>

<script>
    const sessionPassword = "<%= sessionPassword != null ? sessionPassword : "" %>";

    function checkCurrentPassword() {
        const currentPwInput = document.getElementById('currentPw').value.trim();
        const currentPwError = document.getElementById('currentPw-error');

        if (currentPwInput !== sessionPassword.trim()) {
            currentPwError.classList.add('visible');
            return false;
        } else {
            currentPwError.classList.remove('visible');
            return true;
        }
    }

    function validatePasswordChange() {
        const isCurrentPasswordValid = checkCurrentPassword();
        const isNewPasswordValid = validateNewPassword();
        const isPasswordMatchValid = validatePasswordMatch();
        return isCurrentPasswordValid && isNewPasswordValid && isPasswordMatchValid;
    }

    function validateNewPassword() {
        const newPwInput = document.getElementById('newPw').value;
        const newPwError = document.getElementById('newPw-error');
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,15}$/;

        if (!passwordRegex.test(newPwInput)) {
            newPwError.classList.add('visible');
            return false;
        } else {
            newPwError.classList.remove('visible');
            return true;
        }
    }

    function validatePasswordMatch() {
        const newPwInput = document.getElementById('newPw').value;
        const confirmPwInput = document.getElementById('confirmPw').value;
        const confirmPwError = document.getElementById('confirmPw-error');

        if (newPwInput !== confirmPwInput) {
            confirmPwError.classList.add('visible');
            return false;
        } else {
            confirmPwError.classList.remove('visible');
            return true;
        }
    }
</script>

</body>
</html>
