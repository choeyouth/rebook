<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<style>

	</style>
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
                <li>
                    <strong>${book.name}</strong> - ${book.author} 
                    <p>${book.story}</p>
                    <img src="${book.cover}" alt="${book.name}"/>
                </li>
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