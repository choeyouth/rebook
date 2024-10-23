<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.rebook.mybook.model.MarkDTO" %>
<%@ page import="com.rebook.mybook.repository.MarkDAO" %>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined&display=swap" />
<head>
	<meta charset="UTF-8">
	<title>My Book Marks</title>
	<style>
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

	</style>
</head>
<body>
	<h2>나의 책 마크</h2>
	<form method="POST" action="/">
		<%
			String seq = request.getParameter("seq");
			
			MarkDAO markDao = MarkDAO.getInstance();
			ArrayList<MarkDTO> markList = markDao.listMark(seq);
		%>
		
		<table id="mark-table">
			<tr>
				<th>책 사진</th>
				<th>책 제목</th>
				<th>저자</th>
				<th>나의 북 마크</th>
				<th>작성 날짜</th>
				<th>기타</th>
			</tr>
			<%
			if (markList != null && !markList.isEmpty()) {
				for (MarkDTO mark : markList) {
			%>
			<tr>
				<td><img class="book-cover" src="<%= mark.getCover() %>" alt="Cover Image"></td>
				<td><%= mark.getBookname() %></td>
				<td><%= mark.getAuthor() %></td>
				<td><%= mark.getFamousline() %></td>
				<td><%= mark.getRegdate() %></td>
				<td>
				    <a href="http://localhost:8090/rebook/mybook/markedit.do?bookmarkseq=<%= mark.getBookmarkseq() %>">
				        <span class="material-symbols-outlined">edit</span>
				    </a>
				    <a href="http://localhost:8090/rebook/mybook/markdel.do?bookmarkseq=<%= mark.getBookmarkseq() %>">
				        <span class="material-symbols-outlined">delete</span>
				    </a>
				</td>

			</tr>
			<%
				}
			} else {
			%>
			<tr>
				<td colspan="5">등록된 책 마크가 없습니다.</td>
			</tr>
			<%
			}
			%>
		</table>
		<input type="hidden" name="seq" value="<%= seq %>">
	</form>

	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
</body>
</html>