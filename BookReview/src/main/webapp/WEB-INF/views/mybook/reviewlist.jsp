<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.rebook.mybook.model.ReviewDTO" %>
<%@ page import="com.rebook.mybook.repository.ReviewDAO" %>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined&display=swap" />
<head>
    <meta charset="UTF-8">
    <title>나의 책 리뷰</title>
    <style>
    	body {
	    	padding-top: 80px;
	    	text-align: center;
	  	} 
        #mark-table {
            margin: 0 auto;
            width: 75%;
            border-collapse: collapse;
            border-radius: 10px; /* 모서리를 둥글게 */
            overflow: hidden; /* 둥근 모서리가 적용된 테이블에서 넘치는 부분 제거 */
            border: 2px solid #90ee90; /* 연두색 테두리 */
        }

        #mark-table th, #mark-table td {
            border: 1px solid #90ee90; /* 연두색 테두리 */
            padding: 12px;
            text-align: center;
        }

        #mark-table th {
            background-color: #dfffd6; /* 연두색 배경 */
            color: black;
            font-weight: bold;
        }

        .book-cover {
            width: 100px;
            height: auto;
            border-radius: 5px; /* 책 사진의 모서리를 살짝 둥글게 */
        }
        .material-symbols-outlined {
	        color: green;
	    }
    </style>
</head>
<body>
    <h2>나의 책 리뷰</h2>
    <%
        String seq = request.getParameter("seq");

        ReviewDAO reviewDAO = ReviewDAO.getInstance();
        ArrayList<ReviewDTO> reviewList = reviewDAO.listReview(seq);
    %>

    <table id="mark-table">
        <tr>
            <th>책 사진</th>
            <th>책 제목</th>
            <th>저자</th>
            <th>나의 북 리뷰</th>
            <th>작성 날짜</th>
            <th>수정<br>/삭제</th>
        </tr>
        <%
        if (reviewList != null && !reviewList.isEmpty()) {
            for (ReviewDTO review : reviewList) {
        %>
        <tr>
            <td>
                <img class="book-cover" src="<%= review.getCover() %>" alt="Cover Image">
                <input type="hidden" name="bookmarkseq" value="<%= review.getBookreviewseq() %>">
            </td>
            <td><%= review.getBookname() %></td>
            <td><%= review.getAuthor() %></td>
            <td><%= review.getCommend() %></td>
            <td><%= review.getReviewdate() %></td>
            <td>
                <a href="http://localhost:8090/rebook/mybook/reviewedit.do?reviewseq=<%= review.getBookreviewseq() %>" style="display: block;">
                    <span class="material-symbols-outlined" title="수정">edit</span>
                </a>
                <a href="http://localhost:8090/rebook/mybook/reviewdel.do?reviewseq=<%= review.getBookreviewseq() %>"
                	 style="display: block;"
                	 onclick="return confirmDelete();">
                    <span class="material-symbols-outlined" title="삭제">delete</span>
                </a>
                
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="4">등록된 책 리뷰가 없습니다.</td>
        </tr>
        <%
        }
        %>
    </table>
    <input type="hidden" name="seq" value="<%= seq %>">

    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script src="https://bit.ly/4cMuheh"></script>
    <script>
	    function confirmDelete() {
	        // confirm 창을 띄우고 사용자의 응답을 받음
	        return confirm("정말 삭제하시겠습니까?");
	    }
	</script>
</body>
</html>
