<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" href="http://bit.ly/3WJ5ilK">
	<style>

	</style>
</head>
<body>
	<!--  -->
	
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>
    	window.onload = function() {
    	    setTimeout(function() {
    	        const referrer = document.referrer;
    	        const allowedReferrer = "http://localhost:8090/rebook/user/login.do"; 

    	        if (referrer !== allowedReferrer) {
    	            window.location.href = "http://localhost:8090/rebook/home/main.do";
    	            return;
    	        } else {
    	            const userResponse = confirm("회원가입을 축하합니다! 장르에 따른 책추천을 받으시겠습니까?\n취소시 메인화면으로 이동합니다.");
    	            if (userResponse) {
    	                window.location.href = "http://localhost:8090/rebook/preference/booklistedit.do";
    	            } else {
    	                window.location.href = "http://localhost:8090/rebook/home/main.do";
    	            }
    	        }
    	    }, 2000); // 2000밀리초 = 2초
	    };
	</script>
</body>
</html>