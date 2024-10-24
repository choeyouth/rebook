<%@page import="com.rebook.mybook.model.MarkDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.rebook.mybook.repository.MarkDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>나의 북 마크 수정하기</title>
	<style>
		body {
	    	text-align: center;
	  	} 
	  	.book-cover {
	        width: 150px;
	        height: auto;
	        margin-bottom: 20px;
	    }
	    form {
	        display: inline-block;
	        text-align: left;
	    }
	    .form-group {
	        margin-bottom: 15px;
	    }
	    label {
	        display: block;
	        font-weight: bold;
	        margin-bottom: 5px;
	    }
	    input[type="text"], textarea {
	        width: 300px;
	        padding: 8px;
	        margin-bottom: 10px;
	    }
	    input[type="submit"] {
	        padding: 10px 20px;
	        background-color: #4CAF50;
	        color: white;
	        border: none;
	        cursor: pointer;
	    }
	</style>
</head>
<body>	
	<h2>나의 북 마크 수정하기</h2>
	
	<%
		String bookmarkseq = request.getParameter("bookmarkseq");
		
		MarkDAO markDao = MarkDAO.getInstance();
		ArrayList<MarkDTO> markList = markDao.list_ForEditMark(bookmarkseq);
		
		if (markList != null && !markList.isEmpty()) {
			MarkDTO mark = markList.get(0);
	%>
	<div>
		<img class="book-cover" src="<%= mark.getCover() %>" alt="Cover Image">
		<div class="bookname">
	        <div><label for="bookname">책 제목: </label><%= mark.getBookname() %></div>
	    </div>
	    <div class="author">
	        <div><label for="author">저자:</label><%= mark.getAuthor() %></div>
	    </div>
	</div>
	
	<hr>
	
	<form method="POST" action="markUpdate.do">
	    <input type="hidden" name="bookmarkseq" value="<%= bookmarkseq %>">
	    
	    <div class="form-group">
	        <label for="famousline">나의 북 마크:</label> 
	        <textarea name="famousline" id="famousline" rows="4" required><%= mark.getFamousline() %></textarea>
	    </div>
	    
	    <div>
	        <input type="submit" value="수정하기">
	    </div>
	</form>
	
	<%
		} else {
			out.println("해당 북 마크를 찾을 수 없습니다.");
		}
	%>
	
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
</body>
</html>