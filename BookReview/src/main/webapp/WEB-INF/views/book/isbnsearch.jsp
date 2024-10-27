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
<%@include file="/WEB-INF/views/inc/header.jsp" %>
</head>
<body>
	<!--  -->
    <h1>게시글 작성</h1>
    <form method="POST" action="/rebook/book/isbnsearch.do">
    
		<div>
		    <div>책 제목:</div>
		    <input type="text" id="query" name="query" value="${query}" />
		    <input type="submit" value="검색하기" />
		</div>
    
        <table class="vertical">
            <tr>
                <th>제목</th>
                <td><input type="text" name="title" required class="full" value="책 검색 후 작성 가능합니다." readonly></td>
            </tr>
            <tr>
                <th>책 제목</th>
                <td>
                	<input type="text" id="query" name="query" class="full" value="책 검색 후 작성 가능합니다." readonly/>
                </td>
            </tr>
            <tr>
                <th>내용</th>
                <td><textarea name="content" required class="full" readonly >책 검색 후 작성 가능합니다.</textarea></td>
            </tr>
        </table>
        <input type="hidden" name="memberSeq" value="${seq}"> 
        <div>
            <button type="button" onclick="location.href='/rebook/discussion/boardlist.do'">취소</button>
            <button type="submit">작성하기</button>
        </div>
    </form>
	
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>
	    /* function searchBook() {
	        const query = document.getElementById('query').value.trim();
	
	        if (query === "") {
	            alert("검색어를 입력해 주세요."); 
	            return;
	        }
			console.log('/rebook/book/isbnsearch.do?query=' + query);
	        location.href = '/rebook/book/isbnsearch.do?query=' + query;
	    } */
	</script>
</body>
</html>