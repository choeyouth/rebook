<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원 정보 수정</title>
    <style>
        @import url("https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard.css");

		@font-face {
		    font-family: 'Paperlogy-8ExtraBold';
		    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/2408-3@1.0/Paperlogy-8ExtraBold.woff2') format('woff2');
		    font-weight: 800;
		    font-style: normal;
		}
		
		@font-face {
		    font-family: 'KOTRA_BOLD-Bold';
		    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10-21@1.1/KOTRA_BOLD-Bold.woff') format('woff');
		    font-weight: normal;
		    font-style: normal;
		}
		
		@font-face {
		    font-family: 'LINESeedKR-Bd';
		    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/LINESeedKR-Bd.woff2') format('woff2');
		    font-weight: 400;
		    font-style: normal;
		}

        * {
            box-sizing: border-box;
            font-family: 'Pretendard', sans-serif;
        }

        h1 {
            font-family: 'KOTRA_BOLD-Bold', sans-serif;
            text-align: center;
            margin-bottom: 40px;
        }

        .update-body {
            padding: 50px;
            background-color: #EFEAD8;
        }

        .update-container {
            max-width: 600px;
            margin: 20px auto;
            padding: 30px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #f9f9f9;
        }

        .form-group {
            display: flex;
            align-items: center; 
            margin-bottom: 5px;
        }

        .form-group label {
            width: 100px; 
            text-align: right;
            margin-right: 10px;
            font-weight: bold;
        }

        .form-group input {
            flex: 1;
            height: 40px;
            padding: 5px 10px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #eee;
            width: 300px;
        }

        .address-container {
            display: flex;
            align-items: center;
            flex: 1;
            gap: 10px;
        }

        .address-container input {
            flex: 1;
            height: 40px;
            width: 300px;
        }

        .address-container button {
            width: 120px;
            height: 42px;
            background-color: #a1d09b;
            color: white;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            display: flex;
            justify-content: center;
            align-items: center;
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

        .form-actions {
            display: flex;
            justify-content: center;
            margin-top: 20px;
        }

        .form-actions button {
       		width: 120px;
            height: 42px;
            border-radius: 10px;
            background-color: #a1d09b;
            color: white;
            border: none;
            cursor: pointer;
            display: flex;
            justify-content: center;
            align-items: center;
            margin-right: 20px;
        }
        
        .update-container button {
        	font-weight: bold;
        }

		.address-container button:hover,
		.form-actions button:hover,
        .cancel-btn:hover {
            background-color: #F5DAD2;
        }
        
        .error-group {
        	text-align: center;
        	margin-bottom: 10px;
        }
        
        .address-group {
        	margin-bottom: 25px;
        }
    </style>
</head>
<body class="update-body">
    <div class="update-container">
        <h1>회원 정보 수정</h1>
        <form onsubmit="return validateUpdateForm();" method="POST" action="/rebook/user/update.do">
            <div class="form-group">
                <label for="name">이름:</label>
                <input type="text" id="name" name="name" value="${name}" required onblur="validateName()">
            </div>
            <div class="error-group">
                <span class="error-message" id="name-error">이름은 한글로 2~4자여야 합니다.</span>
            </div>

            <div class="form-group">
                <label for="tel">전화번호:</label>
                <input type="tel" id="tel" name="tel" value="${tel}" required onblur="validateTel()">
            </div>
            <div class="error-group">
                <span class="error-message" id="tel-error">전화번호 형식이 올바르지 않습니다. (예: 010-1234-5678)</span>
            </div>

            <div class="form-group">
                <label for="email">이메일:</label>
                <input type="email" id="email" name="email" value="${email}" required onblur="validateEmail()">
            </div>
            <div class="error-group">
                <span class="error-message" id="email-error">유효한 이메일을 입력하세요.</span>
            </div>

            <div class="form-group">
                <label for="address">주소:</label>
                <div class="address-container">
                    <input type="text" id="address" name="address" value="${address}" required>
                    <button type="button" onclick="openPostcodePopup()">우편번호 찾기</button>
                </div>
            </div>
            <div class="error-group">
                <span class="error-message" id="address-error">주소를 입력하세요.</span>
            </div>

            <div class="form-group address-group">
                <label for="addrDetail">상세 주소:</label>
                <input type="text" id="addrDetail" name="addrDetail" value="${addrDetail}" required>
            </div>

            <div class="form-group address-group">
                <label for="zipcode">우편번호:</label>
                <input type="text" id="zipcode" name="zipcode" value="${zipcode}" required>
            </div>

            <div class="form-actions">
                <button type="submit">수정 완료</button>
                <button type="button" class="cancel-btn" onclick="history.back()">취소</button>
            </div>
        </form>
    </div>

    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script>
        function openPostcodePopup() {
            new daum.Postcode({
                oncomplete: function(data) {
                    const addr = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
                    document.getElementById('zipcode').value = data.zonecode;
                    document.getElementById('address').value = addr;
                }
            }).open();
        }

        function validateUpdateForm() {
            const isNameValid = validateName();
            const isTelValid = validateTel();
            const isEmailValid = validateEmail();
            return isNameValid && isTelValid && isEmailValid;
        }

        function validateName() {
            const nameInput = document.getElementById('name');
            const nameError = document.getElementById('name-error');
            const nameRegex = /^[가-힣]{2,4}$/;

            if (!nameRegex.test(nameInput.value)) {
                nameError.classList.add('visible');
                return false;
            } else {
                nameError.classList.remove('visible');
                return true;
            }
        }

        function validateTel() {
            const telInput = document.getElementById('tel');
            const telError = document.getElementById('tel-error');
            const telRegex = /^010-\d{4}-\d{4}$/;

            if (!telRegex.test(telInput.value)) {
                telError.classList.add('visible');
                return false;
            } else {
                telError.classList.remove('visible');
                return true;
            }
        }

        function validateEmail() {
            const emailInput = document.getElementById('email');
            const emailError = document.getElementById('email-error');
            const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

            if (!emailRegex.test(emailInput.value)) {
                emailError.classList.add('visible');
                return false;
            } else {
                emailError.classList.remove('visible');
                return true;
            }
        }
    </script>
</body>
</html>
