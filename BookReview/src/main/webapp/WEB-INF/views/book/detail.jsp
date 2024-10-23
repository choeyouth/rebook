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
</head>
<body>
	<!--TODO: 위시 가져오기, 평점, 리뷰, 북마크 없을 때 추가코드  -->
    <div class="bookDetail">
        <img src="${book.cover}" alt="${book.name}">

        <h2>${book.name}</h2>
        
        <p><strong>저자:</strong> ${book.author}</p>
        <p><strong>장르 코드:</strong> ${book.subgenre_seq}</p>
        <p><strong>줄거리:</strong> ${book.story}</p>
        
        <h3>위시</h3>
        
        <h3>평점</h3>
        <p><strong>평균 평점:</strong> ${avgScore}</p>
        
		<h3>리뷰</h3>
		<c:if test="${empty review}">
		    <p>리뷰가 없습니다.</p>
		</c:if>
		<c:forEach var="r" items="${review}">
		    <c:if test="${r != null}">
		        <div style="border: 1px solid #ddd; padding: 10px; margin-bottom: 10px;">
		            <p><strong>회원:</strong> ${r.membername}</p>
		            <p><strong>내용:</strong> ${r.commend}</p>
		            <p><strong>날짜:</strong> ${r.reviewdate}</p>
		        </div>
		    </c:if>
		</c:forEach>
		
		<h3>북마크</h3>
		<c:if test="${empty mark}">
		    <p>북마크가 없습니다.</p>
		</c:if>
		<c:forEach var="m" items="${mark}">
		    <c:if test="${m != null}">
		        <div style="border: 1px solid #ddd; padding: 10px; margin-bottom: 10px;">
		            <p><strong>회원:</strong> ${m.membername}</p>
		            <p><strong>문장:</strong> ${m.famousline}</p>
		            <p><strong>날짜:</strong> ${m.regdate}</p>
		        </div>
		    </c:if>
		</c:forEach>



        <a href="/rebook/book/search.do">목록으로 돌아가기</a>
    </div>
    
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>
	
	</script>
</body>
</html>