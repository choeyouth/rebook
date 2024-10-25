<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/rebook/assets/css/login.css" />
<%@include file="/WEB-INF/views/inc/header.jsp"%>

</head>
<body class="loginbody">
	<main class="main">
		<div class="container" id="container">
			<div class="form-container sign-up-container">
				<form method="POST" action="/rebook/user/register.do"
					enctype="multipart/form-data">
					<div class="registerInfo" id="registerInfo1"
						style="margin-top: 500px;">
						<h1 class="title">계정 생성</h1>
						<div class="alignbox">
							<input type="text" name="id" id="id" required class="textcheck"
								placeholder="ID" aria-label="ID" /> <input type="button"
								class="checkbutton" onclick="checkId()" value="아이디 중복검사"
								aria-label="ID Check">
						</div>
						<input type="text" name="name" id="name" required class="box"
							placeholder="Name" onblur="validateName()" aria-label="Name" > 
							<span id="name-validation-message" style="color: red; margin-left: 10px;"></span>
							<input
							type="password" name="pw" class="box" placeholder="Password"
							oninput="checkPasswordStrength()" aria-label="Password" /> <span
							id="password-strength-message" style="margin-left: 10px;"></span>
						<input type="password" name="pwcheck" class="box"
							placeholder="Passcheck" oninput="validatePasswordMatch()"
							onblur="checkPasswordMatch()" aria-label="Passcheck" /> 
							<span id="password-match-message" style="color: red; margin-left: 10px;"></span> 
							<input type="email" name="email" required class="box"
							placeholder="Email" aria-label="Email" />

						<div class="nextInfo">
							<button type="button" onclick="showNextDiv()">다음 단계</button>
						</div>
					</div>

					<div class="registerInfo" id="registerInfo2"
						style="visibility: hidden;">
						<h1 class="title">계정 생성</h1>
						<div class="alignbox">
							<input type="text" name="zipcode" id="zipcode" required
								class="textcheck" placeholder="Zipcode" aria-label="Zipcode"
								readonly> <input type="button" class="checkbutton"
								onclick="execDaumPostcode()" value="우편번호 찾기"
								aria-label="Find Zipcode">
						</div>
						<input type="text" name="address" id="address" required
							class="box" placeholder="Address" aria-label="Address" readonly>
						<input type="text" name="addrDetail" required class="box"
							placeholder="Detail Address" aria-label="Detail Address" /> <input
							type="tel" name="tel" required placeholder="Tel" class="box"
							oninput="formatPhoneNumber(this)" aria-label="Telephone" /> <input
							type="file" name="pic" id="pic" class="box"
							aria-label="Profile Picture">

						<div class="nextInfo">
							<button type="button" onclick="showBeforeDiv()">이전 단계</button>
						</div>
						<div class="btnSignUp">
							<button type="submit">회원가입</button>
						</div>
					</div>

				</form>
			</div>

			<div class="form-container sign-in-container">
				<form method="POST" action="/rebook/user/login.do">
					<h1 class="title">로그인</h1>
					<input type="text" placeholder="id" name="id" class="box" /> <input
						type="password" placeholder="Password" name="pw" class="box" /> <a
						href="#" class="forgot">아이디/비밀번호 찾기</a>
					<button>로그인</button>
				</form>
			</div>
			<div class="overlay-container">
				<div class="overlay">
					<div class="overlay-panel overlay-left">
						<h1 class="titlecomment">다시 오셨군요!</h1>
						<p>이미 계정이 있다면 로그인 해주세요.</p>
						<button class="ghost" id="signIn">로그인</button>
					</div>
					<div class="overlay-panel overlay-right">
						<h1 class="titlecomment">처음이신가요?</h1>
						<p>지금 바로 가입해 보세요!</p>
						<button class="ghost" id="signUp">회원가입</button>
					</div>
				</div>
			</div>
		</div>
	</main>
</body>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    const signUpButton = document.getElementById('signUp');
    const signInButton = document.getElementById('signIn');
    const container = document.getElementById('container');

    signUpButton.addEventListener('click', () => {
        container.classList.add("right-panel-active");
    });

    signInButton.addEventListener('click', () => {
        container.classList.remove("right-panel-active");
    });

    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                let addr = data.userSelectedType === 'R' ? data.roadAddress : data.jibunAddress;
                document.getElementById('zipcode').value = data.zonecode;
                document.getElementById("address").value = addr;
            }
        }).open();
    }

    function checkId() {
        const id = document.getElementById('id').value;
        const idInput = document.getElementById('id');

        if (!id) {
            alert('아이디를 입력해주세요.');
            return;
        }

        const regex = /^[a-zA-Z0-9_]{4,12}$/;
        if (!regex.test(id)) {
            alert('아이디는 4자 이상 12자 이하의 영문자, 숫자, 언더스코어(_)로만 구성되어야 합니다.');
            idInput.value = '';
            idInput.focus();
            return;
        }

        fetch('/rebook/user/checkId.do', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'id=' + encodeURIComponent(id)
        })
        .then(response => response.text())
        .then(result => {
            if (result === 'available') {
                if (confirm('사용 가능한 아이디입니다. 이 아이디를 사용하시겠습니까?')) {
                    idInput.readOnly = true;
                    idInput.dataset.valid = 'true';
                    document.querySelector('.checkbutton').disabled = true;
                } else {
                    idInput.value = '';
                    idInput.focus();
                }
            } else {
                alert('이미 사용중인 아이디입니다.');
                idInput.value = '';
                idInput.focus();
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('중복 검사 중 오류가 발생했습니다.');
        });
    }

    function validateName() {
        const nameInput = document.getElementById('name');
        const name = nameInput.value;
        const messageElement = document.getElementById('name-validation-message');

        const regex = /^[가-힣]{2,4}$/;
        if (!regex.test(name)) {
            messageElement.textContent = '이름은 한글로 2~4자여야 합니다.';
        } else {
            messageElement.textContent = '';
        }
    }

    function isValidEmail(email) {
        const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
        return emailRegex.test(email);
    }

    function checkPasswordStrength() {
        const pw = document.querySelector('input[name="pw"]').value;
        const strengthMessage = document.getElementById('password-strength-message');

        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&])[A-Za-z\d!@#$%^&]{8,15}$/;
        if (passwordRegex.test(pw)) {
            strengthMessage.textContent = '안전한 비밀번호 입니다.';
            strengthMessage.style.color = 'green';
        } else {
            strengthMessage.textContent = '비밀번호는 8~15자 이내, 대문자, 소문자, 숫자,특수문자(!@#$%^&)중하나를 포함해야 합니다.';
            strengthMessage.style.color = 'red';
        }
    }

    function validatePasswordMatch() {
        const pw = document.querySelector('input[name="pw"]').value;
        const pwcheck = document.querySelector('input[name="pwcheck"]').value;
        const messageElement = document.getElementById('password-match-message');

        if (pw !== pwcheck) {
            messageElement.textContent = '비밀번호가 일치하지 않습니다.';
            messageElement.style.color = 'red';
        } else {
            messageElement.textContent = '비밀번호가 일치합니다.';
            messageElement.style.color = 'green';
        }
    }

    function showNextDiv() {
        const emailInput = document.querySelector('input[name="email"]');
        if (!isValidEmail(emailInput.value)) {
            alert('올바른 이메일 형식이 아닙니다.');
            emailInput.focus();
            return;
        }

        document.getElementById('registerInfo1').style.visibility = 'hidden';
        document.getElementById('registerInfo2').style.visibility = 'visible';
        document.getElementById('registerInfo2').style.marginTop = '-800px';
    }

    function showBeforeDiv() {
        document.getElementById('registerInfo1').style.visibility = 'visible';
        document.getElementById('registerInfo2').style.visibility = 'hidden'; 
        document.getElementById('registerInfo2').style.marginTop = '0px';
    }

    function formatPhoneNumber(input) {
        input.value = input.value.replace(/[^0-9]/g, '');
        const value = input.value;

        if (value.length < 4) {
            input.value = value;
        } else if (value.length < 7) {
            input.value = value.slice(0, 3) + '-' + value.slice(3);
        } else if (value.length < 11) {
            input.value = value.slice(0, 3) + '-' + value.slice(3, 6) + '-' + value.slice(6);
        } else {
            input.value = value.slice(0, 3) + '-' + value.slice(3, 7) + '-' + value.slice(7, 11);
        }
    }

    function validateForm() {
        const idInput = document.getElementById('id');
        const nameInput = document.getElementById('name');
        const pwInput = document.querySelector('input[name="pw"]');
        const pwcheckInput = document.querySelector('input[name="pwcheck"]');
        const emailInput = document.querySelector('input[name="email"]');
        const addressInput = document.getElementById('address');

        if (!idInput.value && idInput.readOnly) {
            alert('유효한 아이디를 입력해야 합니다.');
            idInput.focus();
            return false;
        }
        if (!nameInput.value) {
            alert('이름을 입력해주세요.');
            nameInput.focus();
            return false;
        }
        if (!pwInput.value || pwInput.value !== pwcheckInput.value) {
            alert('비밀번호가 일치하지 않습니다.');
            pwcheckInput.focus();
            return false;
        }
        if (!emailInput.value || !isValidEmail(emailInput.value)) {
            alert('유효한 이메일 주소를 입력해주세요.');
            emailInput.focus();
            return false;
        }
        if (!addressInput.value) {
            alert('주소를 입력해주세요.');
            addressInput.focus();
            return false;
        }

        return true;
    }

    document.querySelector('.btnSignUp button').addEventListener('click', function(event) {
        if (!validateForm()) {
            event.preventDefault();
        }
    });
</script>