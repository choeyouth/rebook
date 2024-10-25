<%@page import="com.rebook.mybook.model.ReviewDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.rebook.mybook.repository.ReviewDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
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
	<h2>나의 북 리뷰 수정하기</h2>
	
	<%
		String bookreviewseq = request.getParameter("bookreviewseq");
	
		ReviewDAO reviewDAO = ReviewDAO.getInstance();
		ArrayList<ReviewDTO> reviewList = reviewDAO.list_ForEditReview(bookreviewseq);
		
		if (reviewList != null && !reviewList.isEmpty()) {
			ReviewDTO review = reviewList.get(0);
	%>
	<div>
		<img class="book-cover" src="<%= review.getCover() %>" alt="Cover Image">
		<div class="bookname">
	        <div><label for="bookname">책 제목: </label><%= review.getBookname() %></div>
	    </div>
	    <div class="author">
	        <div><label for="author">저자:</label><%= review.getAuthor() %></div>
	    </div>
	</div>
	
	<hr>
	
	<form method="POST" action="reviewedit.do">
	    <input type="hidden" name="bookreviewseq" value="<%= bookreviewseq %>">
	    <div class="form-group">
	        <label for="commend">나의 북 리뷰:</label>
	        <textarea name="commend" id="commend" rows="6" required style="resize: none;"><%= review.getCommend() %></textarea>
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