<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<head>
    <title>ReBook</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <link rel="stylesheet" href="/rebook/assets/css/main.css" />
    
    <style>
       
    </style>
</head>
<header class="header_template">
    <div id="page-wrapper">
        <!-- Nav -->
        <nav id="nav">
            <ul>
                <li class="current" id="titlebar"><a href="/rebook/home/main.do" id="titlebar">Home</a></li>
                <li onclick="location.href='/rebook/mybook/list.do?seq=${seq}';">
                    <a id="titlebar">나의 책</a>
                    <ul>
                        <li onclick="location.href='/rebook/mybook/reviewlist.do?seq=${seq}';"><a>리뷰</a></li>
                        <li onclick="location.href='/rebook/mybook/ranklist.do?seq=${seq}';"><a href="#">평점</a></li>
                        <li onclick="location.href='/rebook/mybook/marklist.do?seq=${seq}';"><a href="#">북마크</a></li>
                    </ul>
                </li>
                <li onclick="location.href='/rebook/preference/booklist.do';"><a>추천 도서</a></li>
                <li onclick="location.href='/rebook/discussion/boardlist.do';"><a>토론 게시판</a></li>
                <li>
                    <a href="#" id="titlebar">검색</a>
                    <ul>
                        <li onclick="location.href='/rebook/book/search.do';"><a>도서</a></li>
                        <li onclick="location.href='/rebook/word/search.do';"><a>단어</a></li>
                        <li onclick="location.href='/rebook/quote/search.do';"><a>명언</a></li>
                    </ul>
                </li>
                <li onclick="location.href='/rebook/library/search.do';">
                    <a id="titlebar">도서관 찾기</a>
                </li>
                <li class="login-menu" onclick="location.href='/rebook/user/login.do';">
				    <c:choose>
				        <c:when test="${not empty seq}">
				            <a href="/rebook/user/mypage.do" id="titlebar">${name}님 환영합니다!</a>
			                <ul>
			                    <li onclick="location.href='/rebook/user/mypage.do';"><a>마이페이지</a></li>
			                    <li onclick="location.href='/rebook/user/logout.do';"><a>로그아웃</a></li>
			                </ul>
				        </c:when>
				        <c:otherwise>
				            <a href="#" class="dropdown" id="titlebar">로그인</a>
			                <ul>
			                    <li onclick="location.href='/rebook/user/login.do';"><a>로그인</a></li>
			                    <li onclick="location.href='/rebook/user/login.do';"><a>회원가입</a></li>
			                </ul>
				        </c:otherwise>
				    </c:choose> 
                </li>
            </ul>
        </nav>
    </div>

    <script src="/rebook/assets/js/jquery.min.js"></script>
    <script src="/rebook/assets/js/jquery.dropotron.min.js"></script>
    <script src="/rebook/assets/js/jquery.scrolly.min.js"></script>
    <script src="/rebook/assets/js/browser.min.js"></script>
    <script src="/rebook/assets/js/breakpoints.min.js"></script>
    <script src="/rebook/assets/js/util.js"></script>
    <script src="/rebook/assets/js/main.js"></script>
    
</header>

