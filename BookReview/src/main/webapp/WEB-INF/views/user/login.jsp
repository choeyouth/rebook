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
	        <input type="button" class="checkbutton" onclick="" value="아이디 중복검사" aria-label="Find Zipcode">
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
        <input type="tel" name="tel" required placeholder="Tel" class="box" aria-label="Telephone" />
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
        function showNextDiv() {
            document.getElementById('registerInfo1').style.visibility= 'hidden';
            document.getElementById('registerInfo2').style.visibility= 'visible';
            document.getElementById('registerInfo2').style.marginTop = '-800px';
        }
        function showBeforeDiv() {
            document.getElementById('registerInfo1').style.visibility= 'visible';
            document.getElementById('registerInfo2').style.visibility= 'hidden';
            document.getElementById('registerInfo2').style.marginTop = '0px';
        }
        
       
        

	</script>
</html>
