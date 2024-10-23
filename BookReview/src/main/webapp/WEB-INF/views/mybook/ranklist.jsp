<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.rebook.mybook.model.RankDTO" %>
<%@ page import="com.rebook.mybook.repository.RankDAO" %>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined&display=swap" />
<head>
	<meta charset="UTF-8">
	<title></title>
	<style>
		body {
	    	padding-top: 80px;
	    	text-align: center;
	  	}  
        #mark-table {
            margin: 0 auto;
            width: 75%;
            border-collapse: collapse;
            border-radius: 10px;
            overflow: hidden;
            border: 2px solid #90ee90; 
        }

        #mark-table th, #mark-table td {
            border: 1px solid #90ee90; 
            padding: 12px;
            text-align: center;
        }

        #mark-table th {
            background-color: #dfffd6; 
            color: black;
            font-weight: bold;
        }

        .book-cover {
            width: 100px;
            height: auto;
            border-radius: 5px;
        }
        .star-container {
		    display: inline-block;
		}
		
		.star {
		    width: auto; 
		    height: 40px; 
		}
		.material-symbols-outlined {
	        color: green;
	    }
        
    </style>
</head>
<body>
	<h2>나의 책 평점</h2>
	<%
		String seq = request.getParameter("seq");
		
		RankDAO rankDAO = RankDAO.getInstance();
		ArrayList<RankDTO> rankList = rankDAO.listMark(seq);
	%>
	<table id="mark-table">
		<tr>
			<th>책 사진</th>
			<th>책 제목</th>
			<th>저자</th>
			<th>나의 북 평점</th>
			<th>수정<br>/<br>삭제</th>
		</tr>
		<%
		if (rankList != null && !rankList.isEmpty()) {
			for (RankDTO rank : rankList) {
				int score = Integer.parseInt(rank.getScore());
		%>
		<tr>
			<td>
				<img class="book-cover" src="<%= rank.getCover() %>" alt="Cover Image">
				<input type="hidden" name="bookmarkseq" value="<%= rank.getRankseq() %>">
			</td>
			<td><%= rank.getBookname() %></td>
			<td><%= rank.getAuthor() %></td>
			<td>
			    <div class="star-container">
			        <% for (int star = 1; star <= 5; star++) { %>
			            <img class="star" src="/rebook/asset/pic/<%= star <= score ? "star_filled.png" : "star_empty.png" %>" alt="Star">
			        <% } %>
			    </div>
			</td>
			<td>
			    <a href="http://localhost:8090/rebook/mybook/rankedit.do?bookmarkseq=<%= rank.getRankseq() %>" style="display: block;">
			        <span class="material-symbols-outlined" title="수정">edit</span>
			    </a>
			    <a href="http://localhost:8090/rebook/mybook/rankdel.do?bookmarkseq=<%= rank.getRankseq() %>" 
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
			<td colspan="5">등록된 책 마크가 없습니다.</td>
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