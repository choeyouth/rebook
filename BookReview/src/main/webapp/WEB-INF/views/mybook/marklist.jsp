<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.rebook.mybook.model.MarkDTO" %>
<%@ page import="com.rebook.mybook.repository.MarkDAO" %>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<head>
	<meta charset="UTF-8">
	<title>My Book Marks</title>
	<style>
		body {
			padding-top: 50px;
			font-family: Arial, sans-serif;
			background-color: #f4f4f9;
		}
		
		h2 {
			text-align: center;
			color: black;
			margin-bottom: 20px;
		}
		
		.container {
			display: flex;
			flex-wrap: wrap;
			justify-content: center;
			gap: 20px;
			width: 90%;
			margin: 0 auto;
		}
		
		.book-box {
			position: relative;
			border: 1px solid #4CAF50;
			border-radius: 8px;
			width: 250px;
			text-align: center;
			background-color: #fff;
			padding: 20px;
			box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); 
			transition: transform 0.3s ease, opacity 0.3s ease;
		}
		
		.book-box:hover {
			transform: scale(1.05);
		}
		
		.book-box img,
		.book-box strong,
		.book-box div {
			transition: opacity 0.3s ease;
		}
		
		.book-cover {
			width: auto;
			height: 200px;
			object-fit: cover;
			border-radius: 8px;
			margin-bottom: 15px;
		}
		
		.book-box strong {
			color: black;
			font-size: 18px;
			margin-bottom: 10px;
			display: block;
		}
		
		.book-box div {
			color: #555;
			font-size: 14px;
			line-height: 1.5;
		}
		
		small {
			color: #999;
			font-size: 12px;
		}
		
		.action-buttons {
			position: absolute;
			top: 50%;
			left: 50%;
			transform: translate(-50%, -50%);
			display: none; 
			flex-direction: column; 
			justify-content: center;
			align-items: center;
			z-index: 1;
		}
		
		.action-buttons a {
			display: inline-block;
			margin-bottom: 10px;
			padding: 5px 10px;
			background-color: #4CAF50;
			color: white;
			text-decoration: none;
			border-radius: 5px;
			font-size: 12px;
			cursor: pointer;
		}
		
		.book-box:hover .action-buttons {
			display: flex;
		}
		
		.book-box:hover .action-buttons a {
			opacity: 1;
		}
	</style>
</head>
<body>
	<%
        String seq = request.getParameter("seq");
        
        MarkDAO markDao = MarkDAO.getInstance();
        ArrayList<MarkDTO> markList = markDao.listMark(seq);

    %>
    <h2>나의 책 마크</h2>
    
    <div class="container">
    	<% 
            if (markList != null && !markList.isEmpty()) {
                for (int i = 0; i < markList.size(); i++) {
                    MarkDTO mark = markList.get(i);
        %>
        <div class="book-box">
            <div class="action-buttons">
                <a href="http://localhost:8090/rebook/mybook/markedit.do?bookmarkseq=<%= mark.getBookmarkseq() %>">수정하기</a>
                <a href="deleteMark.do?bookmarkseq=<%= mark.getBookmarkseq() %>">삭제하기</a>
            </div>
            <img class="book-cover" src="<%= mark.getCover() %>" alt="Cover Image">
            <input type="hidden" name="rankseq" value="<%= mark.getMemberseq() %>">
            <div><strong><%= mark.getBookname() %></strong></div>
            <input type="hidden" name="bookmarkseq" value="<%= mark.getBookmarkseq() %>">
            <div><%= mark.getFamousline() %></div>
            <div><small><%= mark.getRegdate() %></small></div>
        </div>
        <% 
                }
            }
        %>
    </div>
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
</body>
</html>