<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원 정보 수정</title>
    <link rel="stylesheet" href="/styles/profile.css">
    <%@ include file="/WEB-INF/views/inc/header.jsp" %>
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
			font-family: 'KOTRA_BOLD-Bold', 'Paperlogy-8ExtraBold', 'Pretendard-Regular', 'GmarketSansMedium', sans-serif !important; 
		}

        .update-body {
            padding: 50px;
            background-color: #EFEAD8;
        }

        .update-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 30px;
            border: 1px solid #ddd;
            border-radius: 10px;
            background-color: #f9f9f9;
        }

        .update-container h1 {
            text-align: center;
            margin-bottom: 40px;
            font-weight: bold;
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
            height: 40px;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #eee;
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

        .update-container button {
            width: 100%;
            padding: 12px;
            background-color: #A2CA71;
            color: white;
            border: none;
            border-radius: 5px;
            margin-top: 10px;
            cursor: pointer;
        }

        .update-container button:hover {
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
<body class="update-body">
    <div class="update-container">
        <h1>회원 정보 수정</h1>
        <form onsubmit="return validateUpdateForm();" method="POST" action="/rebook/user/update.do">
            <div class="form-group">
                <label for="name">이름:</label>
                <input type="text" id="name" name="name" value="${name}" required onblur="validateName()">
                <span class="error-message" id="name-error">이름은 한글로 2~4자여야 합니다.</span>
            </div>
            <div class="form-group">
                <label for="tel">전화번호:</label>
                <input type="tel" id="tel" name="tel" value="${tel}" required oninput="formatPhoneNumber(this)" onblur="validateTel()">
                <span class="error-message" id="tel-error">올바른 전화번호 형식이 아닙니다. (예: 010-1234-5678)</span>
            </div>
            <div class="form-group">
                <label for="email">이메일:</label>
                <input type="email" id="email" name="email" value="${email}" required onblur="validateEmail()">
                <span class="error-message" id="email-error">유효한 이메일 주소를 입력해주세요.</span>
            </div>
            <div class="form-group">
                <label for="address">주소:</label>
                <input type="text" id="address" name="address" value="${address}" required>
                <button type="button" onclick="openPostcodePopup()">우편번호 찾기</button>
                <span class="error-message" id="address-error">주소를 입력해주세요.</span>
            </div>
            <div class="form-group">
                <label for="addrDetail">상세 주소:</label>
                <input type="text" id="addrDetail" name="addrDetail" value="${addrDetail}" required>
            </div>
            <div class="form-group">
                <label for="zipcode">우편번호:</label>
                <input type="text" id="zipcode" name="zipcode" value="${zipcode}" required>
            </div>
            <input type="hidden" name="seq" value="${auth}">
            <input type="hidden" name="id" value="${id}">
            <button type="submit">수정 완료</button>
        </form>
        <button onclick="history.back()">취소</button>
    </div>

    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script>
        function openPostcodePopup() {
            new daum.Postcode({
                oncomplete: function(data) {
                    let addr = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
                    document.getElementById('zipcode').value = data.zonecode;
                    document.getElementById('address').value = addr;
                }
            }).open({
                popupName: 'postcodePopup', 
                width: 500,
                height: 600
            });
        }

        function validateUpdateForm() {
            return validateName() & validateTel() & validateEmail();
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

        function formatPhoneNumber(input) {
            input.value = input.value.replace(/[^0-9]/g, '');
            const value = input.value;

            if (value.length < 4) {
                input.value = value;
            } else if (value.length < 7) {
                input.value = value.slice(0, 3) + '-' + value.slice(3);
            } else {
                input.value = value.slice(0, 3) + '-' + value.slice(3, 7) + '-' + value.slice(7);
            }
        }
    </script>
</body>
</html>
