<%@page import="com.rebook.word.model.WordDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/inc/header.jsp" %>
<head>
	<meta charset="UTF-8">
	<title>Rebook</title>
	<style>
		body {
			margin: 0;
			padding-top: 80px;
			font-family: Arial, sans-serif;
			background-color: #f4f4f9;
		}

		#searchContainer, #results {
			width: 80%;
			max-width: 800px;
			margin: 20px auto;
			padding: 20px;
			background-color: #fff;
			border-radius: 8px;
			box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
			text-align: center;
		}
		
		#searchContainer input[type="text"] {
			width: 70%;
			padding: 10px;
			font-size: 16px;
			border: 1px solid #ddd;
			border-radius: 4px;
			margin-right: 10px;
		}
		
		#searchContainer button {
			padding: 10px 20px;
			background-color: #4CAF50;
			color: white;
			border: none;
			border-radius: 4px;
			cursor: pointer;
			font-size: 16px;
		}
		
		#searchContainer button:hover {
			background-color: #45a049;
		}

		#results {
			margin-top: 20px;
			border-top: 1px solid #ddd;
			padding-top: 20px;
			width: 80%;
			margin-left: auto;
			margin-right: auto;
			text-align: center;
		}
		
		.result-item {
			background-color: #fff;
			border: 1px solid #ddd;
			padding: 15px;
			border-radius: 8px;
			margin-bottom: 10px;
			box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
			width: 100%;
			max-width: 600px;
			margin-left: auto;
			margin-right: auto;
		}
		
		#scrollToTopBtn {
			display: none;
			position: fixed;
			bottom: 40px;
			right: 40px;
			z-index: 99;
			background-color: #4CAF50;
			color: white;
			border: none;
			padding: 10px 15px;
			border-radius: 50%;
			cursor: pointer;
			box-shadow: 0 2px 5px rgba(0,0,0,0.3);
			font-size: 18px;
		}
	</style>
</head>
<body>

	<button id="scrollToTopBtn" title="맨 위로">&#8679;</button>
	
	<!-- 검색 기능 -->
	<div id="searchContainer">
		<h2>단어 검색</h2>
		<form action="<%=request.getContextPath()%>/word/search.do" method="get">
			<input type="text" name="search" value="${search}" placeholder="검색어를 입력하세요" required>
			<button type="submit">검색</button>
		</form>
	</div>

	<div id="results">
		<h3>검색 결과</h3>
		<% 
			ArrayList<WordDTO> wordList = (ArrayList<WordDTO>) request.getAttribute("wordList");
			if (wordList != null && !wordList.isEmpty()) {
				for (WordDTO word : wordList) { 
		%>
			<div class="result-item">
				<p><strong>단어:</strong> <%= word.getWord() %></p>
				<p><strong>품사:</strong> <%= word.getPos() %></p>
				<p><strong>정의:</strong> <%= word.getDefinition() %></p>
			</div>
		<% 
				}
			} else { 
		%>
			<p>검색 결과가 없습니다.</p>
		<% 
			}
		%>
	</div>

	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>
		var scrollToTopBtn = document.getElementById("scrollToTopBtn");

		window.onscroll = function() {
			scrollFunction();
		};
	
		function scrollFunction() {
			if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
				scrollToTopBtn.style.display = "block";
			} else {
				scrollToTopBtn.style.display = "none";
			}
		}
	
		scrollToTopBtn.onclick = function() {
			smoothScrollToTop();
		};
	
		function smoothScrollToTop() {
			var currentPosition = document.documentElement.scrollTop || document.body.scrollTop;
			if (currentPosition > 0) {
				window.requestAnimationFrame(smoothScrollToTop);
				window.scrollTo(0, currentPosition - currentPosition / 10); 
			}
		}
		
	</script>
</body>
</html>