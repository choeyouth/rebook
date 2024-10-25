<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/rebook/assets/css/login.css" />
<%@include file="/WEB-INF/views/inc/header.jsp" %>

</head>
<body class="loginbody">
<main class="main">
<div class="container" id="container">
  <div class="form-container sign-up-container">
    <form method="POST" action="/rebook/user/register.do" enctype="multipart/form-data">
      <div class="registerInfo" id="registerInfo1" style="margin-top: 500px;">
      	<h1 class="title">계정 생성</h1> 
        <div class="alignbox">
	        <input type="text" name="id" id="id" required class="textcheck" placeholder="ID" aria-label="ID" />
	        <input type="button" class="checkbutton" onclick="checkId()" value="아이디 중복검사" aria-label="ID Check">
        </div>
        <input type="text" name="name" id="name" required class="box" placeholder="Name" aria-label="Name">
        <input type="password" name="pw" class="box" placeholder="Password" aria-label="Password" />
        <input type="password" name="pwcheck" class="box" placeholder="Passcheck" aria-label="Passcheck" />
        <input type="email" name="email" required class="box" placeholder="Email" aria-label="Email" />
        
        <div class="nextInfo">
          <button type="button" onclick="showNextDiv()">다음 단계</button>
        </div>
      </div>

      <div class="registerInfo" id="registerInfo2" style=" visibility: hidden;">
	    <h1 class="title">계정 생성</h1> 
		<div class="alignbox">
	        <input type="text" name="zipcode" id="zipcode" required class="textcheck" placeholder="Zipcode" aria-label="Zipcode" readonly>
	        <input type="button" class="checkbutton" onclick="execDaumPostcode()" value="우편번호 찾기" aria-label="Find Zipcode">
		</div>
        <input type="text" name="address" id="address" required class="box" placeholder="Address" aria-label="Address"readonly>
        <input type="text" name="addrDetail" required class="box" placeholder="Detail Address" aria-label="Detail Address" />
        <input type="tel" name="tel" required placeholder="Tel" class="box" oninput="formatPhoneNumber(this)" aria-label="Telephone" />
        <input type="file" name="pic" id="pic" class="box" aria-label="Profile Picture">
        
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
	      <input type="text" placeholder="id" name="id" class="box" />
	      <input type="password" placeholder="Password" name="pw" class="box"/>
	      <a href="#" class="forgot">아이디/비밀번호 찾기</a>
	      <button>로그인</button><!-- 로그인 -->
	    </form>
	  </div>
	  <div class="overlay-container">
	    <div class="overlay">
	      <div class="overlay-panel overlay-left">
	        <h1 class="titlecomment">다시 오셨군요!</h1>
	        <p>이미 계정이 있다면 로그인 해주세요.</p>
	        <button class="ghost" id="signIn">로그인</button><!-- 전환 -->
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
		const email = document.querySelector('input[name="email"]').value;
		signUpButton.addEventListener('click', () => {
		  container.classList.add("right-panel-active");
		});
	
		signInButton.addEventListener('click', () => {
		  container.classList.remove("right-panel-active");
		});
		function execDaumPostcode() {
            new daum.Postcode({
                oncomplete: function(data) {
                    
                    var addr = ''; 
                    var extraAddr = ''; 

            
                    if (data.userSelectedType === 'R') { 
                        addr = data.roadAddress;
                    } else { 
                        addr = data.jibunAddress;
                    }

                   
                    if(data.userSelectedType === 'R'){
                        if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                            extraAddr += data.bname;
                        }
                        if(data.buildingName !== '' && data.apartment === 'Y'){
                            extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                        }

                    } else {
                        document.getElementById("sample6_extraAddress").value = '';
                    }

                    document.getElementById('zipcode').value = data.zonecode;
                    document.getElementById("address").value = addr;
                }
            }).open();
        }

        
        function checkId() {
            const id = document.getElementById('id').value;
            const idInput = document.getElementById('id');
            
            // 입력값 검증
            if (!id) {
                alert('아이디를 입력해주세요.');
                return;
            }
            
            // AJAX 요청
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
                    // confirm 다이얼로그로 사용자 선택 받기
                    const useThisId = confirm('사용 가능한 아이디입니다. 이 아이디를 사용하시겠습니까?');
                    
                    if (useThisId) {
                        // 사용자가 '확인'을 선택한 경우
                        idInput.readOnly = true;  // ID 입력창 읽기전용으로 변경
                        idInput.dataset.valid = 'true';  // 유효성 상태 저장
                        document.querySelector('.checkbutton').disabled = true;  // 중복검사 버튼 비활성화
                    } else {
                        // 사용자가 '취소'를 선택한 경우
                        idInput.value = '';  // ID 입력창 초기화
                        idInput.focus();     // ID 입력창에 포커스
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
        
        
        function validatePassword() {
            const pw = document.querySelector('input[name="pw"]').value;
            const pwcheck = document.querySelector('input[name="pwcheck"]').value;
            
            if(pw !== pwcheck) {
                alert('비밀번호가 일치하지 않습니다.');
                document.querySelector('input[name="pwcheck"]').focus();
                return false;
            }
            return true;
        }
        //이메일 유효성 검사
        function isValidEmail(email) {
            const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
            return emailRegex.test(email);
        }
        
        
        
        
        // 다음 단계 버튼 클릭 시 검증
        function showNextDiv() {
        	//비밀번호유효성검사
            if(!validatePassword()) {
                return;
            }
            
            // 이메일 유효성 검사
            const emailInput = document.querySelector('input[name="email"]');
            const email = emailInput.value;

            if (!isValidEmail(email)) {
                alert('올바른 이메일 형식이 아닙니다.');
                emailInput.focus();
                return; // 유효하지 않으면 다음 div를 보여주지 않음
            }
        	
            // 비밀번호 일치하면 다음 단계로 진행
            document.getElementById('registerInfo1').style.visibility = 'hidden';
            document.getElementById('registerInfo2').style.visibility = 'visible';
            document.getElementById('registerInfo2').style.marginTop = '-800px';
        }


        function showBeforeDiv() {
            document.getElementById('registerInfo1').style.visibility= 'visible';
            document.getElementById('registerInfo2').style.visibility= 'hidden';
            document.getElementById('registerInfo2').style.marginTop = '0px';
        }
        
        function formatPhoneNumber(input) {
            // 숫자만 입력받기
            input.value = input.value.replace(/[^0-9]/g, '');

            // 전화번호 형식으로 변환
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


	</script>
</html>
