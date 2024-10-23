<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.rebook.mybook.model.ReviewDTO" %>
<%@ page import="com.rebook.mybook.repository.ReviewDAO" %>
<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/views/inc/header.jsp" %>
<head>
	<meta charset="UTF-8">
	<title></title>
	<style>

	</style>
</head>
<body>
	<h2>나의 책 리뷰</h2>
	<%
		String seq = request.getParameter("seq");
	
	  	ReviewDAO reviewDAO = ReviewDAO.getInstance();
	  	ArrayList<ReviewDTO> reviewList = reviewDAO.listReview(seq);
	%>
	
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>
	
	</script>
</body>
</html>