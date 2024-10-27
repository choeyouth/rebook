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
    <form action="/rebook/book/search.do" method="POST" id="searchForm">
	    <label for="queryType">검색 기준:</label>
	    <select id="queryType" name="queryType">
	        <option value="Title" selected>책 제목</option>
	        <option value="Author">작가</option>
	        <option value="Publisher">출판사</option>
	        <option value="Keyword">키워드</option>
	    </select>
	
	    <label for="query">검색어:</label>
	    <input type="text" id="query" name="query" value="${query}" required/>
	
	
	    <select name="count" id="count">
	        <option value="5">5개씩 보기</option>
	        <option value="10" selected>10개씩 보기</option>
	        <option value="20">20개씩 보기</option>
	        <option value="50">50개씩 보기</option>
	    </select>
	    &nbsp;
		<input type="submit" value="검색하기">
		<input type="button" value="초기화" onclick="location.href='/rebook/book/search.do';">
		&nbsp;
	
	    <div>
	        <input type="button" value="이전 ${count}개" id="btnPrev">
	        <input type="button" value="다음 ${count}개" id="btnNext">
	    </div>
	    <input type="hidden" name="start" id="start" value="${start}">
	</form>


    <c:if test="${resultCount > 0}">
        <p>총 검색 결과 수: ${resultCount}개</p>
        <ul>
            <c:forEach var="book" items="${bookList}">
                <li onclick="location.href='/rebook/book/detail.do?seq=${book.seq}';" style="cursor: pointer;">
                	<img src="${book.cover}" alt="${book.name}"/>
                    <strong>${book.name}</strong> - ${book.author} 
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
	
		$(document).ready(() => {
			
		    const count = '${count}' || '10'; 
			
		    if (!$('#start').val()) {
		        $('#start').val(0);
		    }
	
		    // count 값 유지
		    $('#count').val(count); // JSP에서 전달된 count 값으로 초기화
	
		    $('#count').change(() => {
		        const selectedCount = $('#count').val();
	
		        $('#start').val(0); // 페이지 변경 시 시작 값을 0으로 초기화
		        $('#searchForm').submit(); 
		    });
	
		    $('#btnPrev').click(() => {
		        let start = parseInt($('#start').val()) || 0;
		        let count = parseInt($('#count').val());
	
		        start -= count;
		        if (start < 0) start = 0; 
	
		        console.log("이전 페이지 시작 값: " + start);
		        $('#start').val(start);
		        $('#searchForm').submit(); 
		    });
	
		    $('#btnNext').click(() => {
		        let start = parseInt($('#start').val()) || 0;
		        let count = parseInt($('#count').val());
	
		        start += count; // 다음 페이지로 이동
	
		        $('#start').val(start);
		        $('#searchForm').submit(); 
		    });
		});


	</script>
</body>
</html>