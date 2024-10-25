<%@page import="com.rebook.mybook.model.RankDTO"%> 
<%@page import="java.util.ArrayList"%>
<%@page import="com.rebook.mybook.repository.RankDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>나의 북 평점 수정하기</title>
	<link rel="stylesheet" href="http://bit.ly/3WJ5ilK">
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
	    .star {
	        width: 25px;
	        height: auto;
	        margin: 2px;
	        cursor: pointer;
	    }
	</style>
</head>
<body>
	<h2>나의 북 평점 수정하기</h2>
	
	<%
		String rankseq = request.getParameter("rankseq");
	
		RankDAO rankDAO = RankDAO.getInstance();
		ArrayList<RankDTO> rankList = rankDAO.list_ForEditRank(rankseq);
		
		if (rankList != null && !rankList.isEmpty()) {
			RankDTO rank = rankList.get(0);
			int score = Integer.parseInt(rank.getScore());
	%>
	<div>
		<img class="book-cover" src="<%= rank.getCover() %>" alt="Cover Image">
		<div class="bookname">
	        <div><label for="bookname">책 제목: </label><%= rank.getBookname() %></div>
	    </div>
	    <div class="author">
	        <div><label for="author">저자:</label><%= rank.getAuthor() %></div>
	    </div>
	</div>
	
	<hr>
	
	<form method="POST" action="rankedit.do">
	    <input type="hidden" name="rankseq" value="<%= rankseq %>">
	    <input type="hidden" name="score" id="score" value="<%= score %>">
	    
	    <div class="form-group">
	        <label for="score">나의 북 평점:</label>
	        <div id="star-container">
	            <% for (int star = 1; star <= 5; star++) { %>
	                <img class="star" data-value="<%= star %>" 
	                     src="/rebook/asset/pic/<%= star <= score ? "star_filled.png" : "star_empty.png" %>" 
	                     alt="Star">
	            <% } %>
	        </div>
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
	<script>
		$(document).ready(function() {
		    $('.star').on('click', function() {
		        var clickedStarValue = $(this).data('value');
		        
		        $('#score').val(clickedStarValue);
		        
		        $('.star').each(function() {
		            var starValue = $(this).data('value');
		            if (starValue <= clickedStarValue) {
		                $(this).attr('src', '/rebook/asset/pic/star_filled.png');
		            } else {
		                $(this).attr('src', '/rebook/asset/pic/star_empty.png');
		            }
		        });
		    });
		});
	</script>
</body>
</html>