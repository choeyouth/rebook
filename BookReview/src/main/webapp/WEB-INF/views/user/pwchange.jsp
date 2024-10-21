<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
</head>
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

    .pw-container form div {
        margin-bottom: 15px;
        display: flex;
        justify-content: space-between; /* label과 input을 양 끝에 정렬 */
        align-items: center; /* 수직 가운데 정렬 */
    } 

    .pw-container label {
        width: 30%; /* label의 고정된 너비 설정 */
        text-align: right; /* 텍스트 오른쪽 정렬 */
        margin-right: 10px;
    }

    .pw-container input {
        width: 150px; 
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 5px;
    }

    .pw-container button {
        width: 100%;
        padding: 12px;
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
    
    .pw-container div {
    	padding: 10px;
    }
     
</style>
<body>
<form action="/rebook/user/pwchange.do" method="POST">
    <div class="pw-container">
        <h1>비밀번호 변경</h1>
        <div>
            <label for="currentPw">현재 비밀번호 : </label>
            <input type="password" id="currentPw" name="currentPw" required>
        </div>
        <div>
            <label for="newPw">새 비밀번호 : </label>
            <input type="password" id="newPw" name="newPw" required>
        </div>
        <div>
            <label for="confirmPw">새 비밀번호 확인 : </label>
            <input type="password" id="confirmPw" name="confirmPw" required>
        </div>
        <button type="submit">비밀번호 변경</button>
        <button type="button" class="cancel-btn" onclick="history.back()">취소</button>
    </div>
</form>
</body>
</html>
