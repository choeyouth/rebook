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
		#map {
		  width: 1000px;
		  height: 550px;
		  margin: 0 auto;
		  border: 1px solid #ccc;

		}
		form {
			margin: 20px 0;
		}
		
		input[type="text"] {
			width: 400px;
			height: 35px;
			padding: 0 10px;
			font-size: 16px;
			border: 1px solid #ccc;
			border-radius: 5px;
			box-shadow: 0 2px 5px rgba(0,0,0,0.1);
		}
		
		button {
			height: 40px;
			padding: 0 20px;
			font-size: 16px;
			border: none;
			border-radius: 5px;
			cursor: pointer;
			margin-left: 10px;
			box-shadow: 0 2px 5px rgba(0,0,0,0.1);
		}
		
		#searchBox {
			background-color: #4CAF50;
			color: white;
		}
		#resetBox {
			background-color: #bababa;
		}
		
		button:hover {
			background-color: #45a049;
		}
		
		
		.table-container {
			display: flex;
			justify-content: space-between;
			flex-wrap: wrap;
			margin: 20px auto;
			width: 90%;
		}

		table {
			width: 30%;
			border-collapse: collapse;
			margin-bottom: 20px;
			box-shadow: 0 2px 5px rgba(0,0,0,0.1);
		}
		
		th, td {
			padding: 8px 12px;
			border: 1px solid #ccc;
		}

		th {
			background-color: #f0f2f1;
			text-align: left;
		}

		td {
			text-align: center;
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
		.table-container {
        display: flex;
        justify-content: center;
        margin-top: 20px;
	    }
	
	    .table-container table {
	        border-collapse: collapse;
	        width: 80%;
	        background-color: #f0f2f1;
	        border-radius: 10px;
	        overflow: hidden;
	        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
	    }
	
	    .table-container th, .table-container td {
	        padding: 12px 15px;
	        border: 1px solid #ccc;
	        text-align: center;
	    }
	
	    .table-container th {
	        background-color: #d1e0e0;
	        font-weight: bold;
	    }
	
	    .table-container td {
	        background-color: #ffffff;
	    }
	
	    .table-container tr:last-child td {
	        border-bottom: none;
	    }
	
	    .table-container tr:first-child th:first-child {
	        border-top-left-radius: 10px;
	    }
	    
	    .table-container tr:first-child th:last-child {
	        border-top-right-radius: 10px;
	    }
	    
	    .table-container tr:last-child td:first-child {
	        border-bottom-left-radius: 10px;
	    }
	    
	    .table-container tr:last-child td:last-child {
	        border-bottom-right-radius: 10px;
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

	<h1 id="page_name">도서관 / 주소 검색</h1>
	
	<div>
		<div id="map"></div>
	</div>
	
	<form method="GET" action="search.do">
        <input type="text" id="search" placeholder="검색을 원하시는 (구)를 입력하세요.">
        <button type="submit" id="searchBox">검색</button>
        <button type="button" id="resetBox" onclick="location.href='http://localhost:8090/BookReview/library/search.do'">초기화</button>
    </form>
	
	<hr>
	
	<div id="result_box">검색 결과입니다</div>
	
	<div class="table-container">
		<c:if test="${not empty libraryList}">
	        <table border="1">
	            <tr>
	                <th>장소명</th>
	                <th>주소</th>
	                <th>카테고리</th>
	            </tr>
	            <c:forEach var="library" items="${libraryList}">
	                <tr>
	                    <td>${library.name}</td>
	                    <td>${library.address}</td>
	                    <td>${library.category}</td>
	                </tr>
	            </c:forEach>
	        </table>
	    </c:if>
    </div>
	
	<button id="scrollToTopBtn" title="맨 위로">&#8679;</button>
	
	
	<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=3018135f77d0e580f8314f4923a20f61"></script>
	<script>
	    var libraryList = [];
	    
	    <c:forEach var="library" items="${libraryList}">
	        libraryList.push({
	            lat: ${library.lat},
	            lng: ${library.lng},
	            name: "${library.name}"
	        });
	    </c:forEach>
	
	    var mapContainer = document.getElementById('map');
	    var mapOption = {
	        center: new kakao.maps.LatLng(37.5665, 126.9780),
	        level: 3
	    };
	    
	    var map = new kakao.maps.Map(mapContainer, mapOption);
	    
	    if (libraryList.length > 0) {
	        var bounds = new kakao.maps.LatLngBounds();

	        libraryList.forEach(function(library) {
	            var markerPosition = new kakao.maps.LatLng(library.lat, library.lng);
	            var marker = new kakao.maps.Marker({
	                position: markerPosition,
	                map: map
	            });
	            
	            var infowindow = new kakao.maps.InfoWindow({
	                content: '<div style="padding:5px;">' + library.name + '</div>'
	            });

	            kakao.maps.event.addListener(marker, 'mouseover', function() {
	                infowindow.open(map, marker);
	            });

	            kakao.maps.event.addListener(marker, 'mouseout', function() {
	                infowindow.close();
	            });

	            // 각 마커의 위치를 bounds에 추가
	            bounds.extend(markerPosition);
	        });

	        // 지도에 bounds를 설정하여 모든 마커가 보이도록 함
	        map.setBounds(bounds);
	    }
		

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