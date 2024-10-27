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
	<h1 class="board-subject">게시판 <small>목록보기</small></h1>
		<table id="book-list">
			<tr>
				<th>번호</th>
				<th>제목</th>
				<th>책제목</th>
				<th>이름</th>
				<th>날짜</th>
				<th>조회수</th>
			</tr>
			<c:forEach var="l" items="${list}">
			<tr>
				<td>${l.seq}</td>
				<td>
					<a href="/rebook/discussion/postview.do?postseq=${l.seq}">${l.title}</a>
				</td>
				<td>${l.bookTitle}</td>
				<td>${l.memberId}</td>
				<td>${l.postDate}</td>
				<td>${l.readCount}</td>
			</tr>
		 	</c:forEach>
		 </table>
		<c:if test="${not empty seq}">
 		<div>
			<button type="button" class="post-add" onclick="location.href='/rebook/book/isbnsearch.do';">쓰기</button>
		</div>
		</c:if>
	
	
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>
	
	</script>
</body>
</html>