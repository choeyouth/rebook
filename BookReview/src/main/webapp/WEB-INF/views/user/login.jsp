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
	      <h1 class="title">계정 생성</h1> 
	      <div id="registerInfo1" style="position: absolute; top: 200; left: 0;">
		      <input type="text" name="name" id="name" required class="short" placeholder="Name">
		      <input type="text" name="id" id="id" required class="short" placeholder="ID" />
		      <input type="password" name="pw" placeholder="Password" />
		      <input type="email" name="email" required placeholder="Email" />
		      <input type="tel" name="tel" required placeholder="tel" />
		      <div class="nextInfo">
	            <button type="button" onclick="showNextDiv()">다음 단계</button>
	          </div>
	          
		      <div class="btnSignUp">
		      	<button type="submit">회원가입</button> 
		      </div>  
	      </div>
	      <div id="registerInfo2" style="position: absolute; top: 200; left: 0;visibility: hidden;">
		      <input type="text" name="address" id="address" required class="short" placeholder="Address">
		      <input type="text" name="addrDetail" required class="short" placeholder="addrdetail" />
		      <input type="text" name="zipcode" id="zipcode" required class="short" placeholder="Zipcode">
		      <input type="file" name="pic" id="pic" class="long">
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
	      <input type="text" placeholder="id" name="id"/>
	      <input type="password" placeholder="Password" name="pw"/>
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
		
        function showNextDiv() {
            document.getElementById('registerInfo1').style.visibility= 'hidden';
            document.getElementById('registerInfo2').style.visibility= 'visible';
        }
        function showBeforeDiv() {
            document.getElementById('registerInfo1').style.visibility= 'visible';
            document.getElementById('registerInfo2').style.visibility= 'hidden';
        }
	</script>
</html>
