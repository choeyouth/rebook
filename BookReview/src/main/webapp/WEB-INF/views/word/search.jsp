<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Rebook</title>
	<style>
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
		#searchContainer {
			margin: 20px;
		}
		#results {
			margin-top: 20px;
			border-top: 1px solid #ddd;
			padding-top: 10px;
		}
		.result-item {
			border-bottom: 1px solid #ddd;
			padding: 10px 0;
		}
	</style>
</head>
<body>
	
	<div id="searchContainer">
		<input type="text" id="searchBox" placeholder="단어를 입력하세요." />
		<button onclick="searchWord()">검색</button>
	</div>
	
	<div id="results"></div>

	<button id="scrollToTopBtn" title="맨 위로">&#8679;</button>
	
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

		function searchWord() {
		    var searchValue = $('#searchBox').val();
		    if (searchValue.trim() === '') {
		        alert('검색어를 입력하세요');
		        return;
		    }

		    $.ajax({
		        url: 'http://localhost:8090/BookReview/word/search.do',
		        type: 'GET',
		        data: { search: searchValue },
		        success: function(response) {
		            console.log("서버 응답:", response);
		            $('#results').html(response);
		        },
		        error: function() {
		            alert('검색 중 오류가 발생했습니다.');
		        }
		    });
		    
		    $('#results').html('<pre>' + response + '</pre>');

		}
		
	</script>
</body>
</html>
