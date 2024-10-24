<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<link rel="stylesheet" href="http://bit.ly/3WJ5ilK">
	<title></title>
	<style>

	</style>
	
<%@include file="/WEB-INF/views/inc/header.jsp" %>

</head>
<body>
	<h1>책 검색</h1>
    <form action="/rebook/book/search.do" method="POST">
        <label for="title">책 제목:</label>
        <input type="text" id="title" name="title" required/>
        <button type="submit">검색</button>
    </form>

    <c:if test="${resultCount > 0}">
        <p>총 검색 결과 수: ${resultCount}개</p>
        <ul>
            <c:forEach var="book" items="${bookList}">
                <li onclick="location.href='/rebook/book/detail.do?seq=${book.seq}';" style="cursor: pointer;">
               		<img src="${book.cover}" alt="${book.name}"/>
                    <strong>${book.name}</strong> - ${book.author} 
                    <%-- <p>${book.story}</p> --%>
                    <input type="hidden" value="${book.seq}" name="bookseq">
                </li>
                <hr>
            </c:forEach>
        </ul>
    </c:if>

    <c:if test="${resultCount == 0}">
        <p>검색 결과가 없습니다.</p>
    </c:if>
    
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>
	
	</script>
</body>
</html>