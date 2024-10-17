<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/rebook/assets/css/mypage.css" />
<style>
/* MyPage-specific CSS */
.main .profile-title {
    font-size: 24px;
    font-weight: bold;
    padding: 20px;
    text-align: center;
}

.main .profile-info {
    background-color: #f9f9f9;
    padding: 30px;
    border-radius: 10px;
    width: 600px;
    margin: 0 auto;
    box-shadow: 0 14px 28px rgba(0,0,0,0.25), 
                0 10px 10px rgba(0,0,0,0.22);
}

.main .profile-info div {
    margin-bottom: 10px;
    font-size: 16px;
}

.main .profile-info .profile-label {
    font-weight: bold;
    margin-right: 10px;
}

.main .profile-container {
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    height: 100vh;
}

.main .profile-info button {
    background-color: #60eb8c;
    color: white;
    border: none;
    padding: 10px 20px;
    border-radius: 20px;
    cursor: pointer;
    font-size: 14px;
    transition: background-color 0.3s ease;
}

.main .profile-info button:hover {
    background-color: #50c76c;
}
</style>

<%@include file="/WEB-INF/views/inc/header.jsp" %>

</head>
<body class="mypage-body">
<main class="main">
    <div class="profile-container">
        <h1 class="profile-title">마이페이지</h1>
        <div class="profile-info">
            <div><span class="profile-label">회원 번호:</span> ${auth}</div>
            <div><span class="profile-label">아이디:</span> ${id}</div>
            <div><span class="profile-label">이름:</span> ${name}</div>
            <div><span class="profile-label">등급:</span> 
                <c:choose>
                    <c:when test="${lv == '1'}">회원</c:when>
                    <c:otherwise>관리자</c:otherwise>
                </c:choose>
            </div>
            <div><span class="profile-label">전화번호:</span> ${tel}</div>
            <div><span class="profile-label">이메일:</span> ${email}</div>
            <div><span class="profile-label">사진:</span> <img src="/rebook/uploads/${pic}" alt="회원사진" /></div>
            <div><span class="profile-label">주소:</span> ${address} ${addrDetail}</div>
            <div><span class="profile-label">우편번호:</span> ${zipcode}</div>
            <div><span class="profile-label">가입일:</span> ${regDate}</div>
            
            <button onclick="location.href='/rebook/user/update.do'">정보 수정</button>
        </div>
    </div>
</main>
</body>
</html>
