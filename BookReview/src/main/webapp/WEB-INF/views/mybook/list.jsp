<%@page import="com.rebook.mybook.repository.MarkDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.rebook.mybook.model.RankDTO" %>
<%@ page import="com.rebook.mybook.repository.RankDAO" %>
<%@ page import="com.rebook.mybook.model.MarkDTO" %>
<%@ page import="com.rebook.mybook.repository.MarkDAO" %>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<head>
    <meta charset="UTF-8">
    <title></title>
    <style>
    	body {
	    	padding-top: 50px;
	  	} 
		.container {
		    position: relative;
		    display: grid;
		    grid-template-columns: repeat(4, 1fr);
		    gap: 20px;
		    padding: 20px;
		    border: 1px solid #4CAF50;
		    border-radius: 10px;
		    background-color: #f9f9f9;
		    width: 90%;
		    margin: 0 auto;
		    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
		}
		
		.book-box {
		    border: 1px solid #4CAF50;
		    border-radius: 8px;
		    text-align: center;
		    padding: 20px;
		    background-color: #fff;
		    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
		    width: 100%;
		    height: auto;
		    box-sizing: border-box;
		}
		
		.book-cover {
		    height: 250px;
		    object-fit: cover;
		    margin-bottom: 10px;
		    border-radius: 8px;
		}
		
		.more-button-container {
		    position: absolute;
		    right: 20px;
		    top: 50%;
		    transform: translateY(-50%);
		}
		
		.more-button {
		    background-color: #4CAF50;
		    color: white;
		    padding: 10px 20px;
		    text-decoration: none;
		    font-size: 16px;
		    border-radius: 5px;
		    display: inline-block;
		    cursor: pointer;
		}
		
		.star {
		    width: 20px;
		    height: 20px;
		}

    </style>
</head>
<body>
    <%
        String seq = request.getParameter("seq");

        RankDAO dao = RankDAO.getInstance();
        ArrayList<RankDTO> rankList = dao.listMark(seq);
        
        MarkDAO markDao = MarkDAO.getInstance();
        ArrayList<MarkDTO> markList = markDao.listMark(seq);

    %>
    <h2 style="text-align:center;">나의 책 마크</h2>
    
    <div class="container">
        <% 
            if (markList != null && !markList.isEmpty()) {
                for (int i = 0; i < markList.size() && i < 4; i++) {
                    MarkDTO mark = markList.get(i);
        %>
        <div class="book-box">
            <img class="book-cover" src="<%= mark.getCover() %>" alt="Cover Image">
            <input type="hidden" name="rankseq" value="<%= mark.getMemberseq() %>">
            <div><strong><%= mark.getBookname() %></strong></div>
            <div><%= mark.getFamousline() %></div>
            <div><small><%= mark.getRegdate() %></small></div>
        </div>
        <% 
                }
            }
        %>
        <div class="more-button-container">
            <a class="more-button" href="http://localhost:8090/rebook/mybook/marklist.do">더보기</a>
        </div>
    </div>
    
    <!-- ----------------------------------------------------------------------------- -->

    <h2 style="text-align:center;">나의 책 평점</h2>

    <div class="container">
        <% 
            if (rankList != null && !rankList.isEmpty()) {
                for (int i = 0; i < rankList.size() && i < 4; i++) {
                    RankDTO rank = rankList.get(i);
                    int score = Integer.parseInt(rank.getScore());
        %>
        <div class="book-box">
            <img class="book-cover" src="<%= rank.getCover() %>" alt="Cover Image">
            <input type="hidden" name="rankseq" value="<%= rank.getRankseq() %>">
            <div><strong><%= rank.getBookname() %></strong></div>
            <div>
                <% for (int star = 1; star <= 5; star++) { %>
                    <img class="star" src="/rebook/asset/pic/<%= star <= score ? "star_filled.png" : "star_empty.png" %>" alt="Star">
                <% } %>
            </div>
        </div>
        <% 
                }
            }
        %>
        <div class="more-button-container">
            <a class="more-button" href="http://localhost:8090/rebook/mybook/ranklist.do">더보기</a>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
    <script src="https://bit.ly/4cMuheh"></script>

</body>
</html>