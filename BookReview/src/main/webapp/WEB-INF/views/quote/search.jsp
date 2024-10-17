<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html> 
<head>
	<meta charset="UTF-8">
	<title>Book Review</title>
	<style>
		/* 상단 메뉴 임시 템플릿 */
		.navbar {
		   display: flex;
		   justify-content: center;
		   align-items: center;
		   background-color: #f0f2f1;
		   padding: 10px 0;
		   box-shadow: 0 2px 5px rgba(0,0,0,0.1);
		}
		
		.navbar div {
		   margin: 0 15px;
		   padding: 10px 20px;
		   background-color: #d5e8d4;
		   border-radius: 5px;
		   cursor: pointer;
		}
		
		.navbar div:hover {
		   background-color: #a8d08d;
		}
		
		.navbar div.active {
		   background-color: #b3e5ab;
		}
		
		.navbar div.dropdown {
		   position: relative;
		}
		
		.navbar div.dropdown-content {
		   display: none;
		   position: absolute;
		   top: 40px;
		   left: 0;
		   background-color: #6e7b69;
		   border-radius: 5px;
		   padding: 10px;
		   text-align: left;
		   box-shadow: 0 4px 8px rgba(0,0,0,0.1);
		}
		
		.navbar div.dropdown:hover .dropdown-content {
		   display: block;
		}
		
		.navbar div.dropdown-content div {
		   padding: 5px 0;
		   color: white;
		}
		
		.navbar div.dropdown-content div:hover {
		   background-color: #4a5148;
		   cursor: pointer;
		}
		/* -------------------------------------------------------- */
		body {
	    	text-align: center;
	  	} 
		
		.table-container {
			display: flex;
			flex-wrap: wrap;
			gap: 20px;
			padding: 20px;
			justify-content: center;
		}

		.quote-box {
			width: 320px;
			height: 320px;
			perspective: 1000px;
			flex-basis: calc(33.333% - 100px);
			margin: 20px;
		}
		
		.quote-inner {
			width: 100%;
			height: 100%;
			position: relative;
			transform-style: preserve-3d;
			transition: transform 0.6s;
			transform-origin: center;
		}
		
		.quote-box:hover .quote-inner {
			transform: rotateY(180deg);
		}
		
		.quote-front, .quote-back {
			position: absolute;
			width: 100%;
			height: 100%;
			backface-visibility: hidden;
			border-radius: 10px;
			display: flex;
			flex-direction: column;
			align-items: center;
			justify-content: center;
			font-size: 14px;
			color: #fff;
			text-align: center;
			box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
			padding: 20px;
			box-sizing: border-box;
		}
		
		.quote-front {
			background: linear-gradient(135deg, #6e45e2, #88d3ce);
		}
		
		.quote-box:nth-child(4n+1) .quote-front {
			background: linear-gradient(135deg, #ff9a9e, #fad0c4);
		}
		.quote-box:nth-child(4n+2) .quote-front {
			background: linear-gradient(135deg, #a18cd1, #fbc2eb);
		}
		.quote-box:nth-child(4n+3) .quote-front {
			background: linear-gradient(135deg, #fbc2eb, #a6c1ee);
		}
		.quote-box:nth-child(4n) .quote-front {
			background: linear-gradient(135deg, #ffecd2, #fcb69f);
		}
		
		.quote-back {
			background-color: gray;
			color: #fff;
			transform: rotateY(180deg);
			font-size: 16px;
			font-style: italic;
		}
		
		.quote-back img {
			border-radius: 50%;
			height: 160px;
			margin-bottom: 10px;
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
	<!-- 상단 메뉴 -->
	<div class="navbar">
	  <div class="active">HOME</div>
	  <div class="dropdown">
	    나의 책
	    <div class="dropdown-content">
	      <div>리뷰</div>
	      <div>평점</div>
	      <div>북마크</div>
	    </div>
	  </div>
	  <div>추천 도서</div>
	  <div>토론 게시판</div>
	  <div>검색</div>
	  <div>도서관 찾기</div>
	  <div>홍길동님 환영합니다.</div>
	</div>
	
	<h1 id="page_name">책 관련 명언</h1>
	
	<div class="table-container">
		<c:forEach var="quote" items="${quoteList}">
			<div class="quote-box">
				<div class="quote-inner">
					<div class="quote-front">
						<p>${quote.quote}</p>
					</div>
					<div class="quote-back">
						<img src="<c:choose>
							<c:when test='${quote.authorpic != null}'>
								/BookReview/asset/pic/${quote.authorpic}
							</c:when>
							<c:otherwise>
								/BookReview/asset/pic/default.png
							</c:otherwise>
						</c:choose>" alt="${quote.engauthor}">
						<p>${quote.author}</p>
						<p>${quote.engauthor}</p>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>



	<button id="scrollToTopBtn" title="맨 위로">&#8679;</button>

	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script>
		// 스크롤 버튼 기능
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