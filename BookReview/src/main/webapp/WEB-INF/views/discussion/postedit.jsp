<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html> 
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" href="http://bit.ly/3WJ5ilK">
	<style>

	</style>
<%@include file="/WEB-INF/views/inc/header.jsp" %>
</head>
<body>
	<!--  -->
	<div id="main">
		<h1 class="page">게시판 <small>수정하기</small></h1>
		
		<form method="POST" action="/rebook/discussion/postedit.do">
		<table class="vertical">
			<tr>
				<th>번호</th>
				<td>${post.seq}</td>
			</tr>
			<tr>
				<th>이름</th>
				<td>${post.memberName}(${post.memberId})</td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" name="subject" id="subject" required class="full" value="${post.title}"></td>
			</tr>
			<tr>
				<th>책제목</th>
				<td>${post.bookTitle}</td>
			</tr>
			<tr>
				<th>내용</th>
				<textarea name="content" id="content" class="full">${post.content}</textarea>
			</tr> 
			<c:if test="${not empty post.img}">
			<tr>
				<th>이미지</th>
				<td>${post.img}</td>
			</tr>
			</c:if>
			<tr>
				<th>날짜</th>
				<td>${post.postDate}</td>
			</tr>
			<tr>
				<th>읽음</th>
				<td>${post.readCount}</td>
			</tr>
		</table>
		
		<div>
			<button type="button" class="back" class="back" onclick="location.href='/rebook/discussion/postview.do';">돌아가기</button>
			<button type="submit" class="edit primary">수정하기</button>
		</div>
		<input type="hidden" name="seq" value="${post.seq}">
		</form>
	</div>
	
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>
	
	</script>
</body>
</html>