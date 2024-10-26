<%@page import="com.rebook.mybook.repository.ReviewDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" href="http://bit.ly/3WJ5ilK">
	<style>

	</style>
</head>
<body>
	<%
		String seq = request.getParameter("seq");
		String bookreviewseq = request.getParameter("bookreviewseq");
		
		ReviewDAO reviewDAO = ReviewDAO.getInstance();
		int result = 0;
		
		if (bookreviewseq != null && !bookreviewseq.isEmpty()) {
			result = reviewDAO.del(bookreviewseq);
		}
		
		if (result > 0) {
	%>
		<script>
			alert("삭제가 완료되었습니다");
			window.location.href = document.referrer;
			<%-- window.location.href = "http://localhost:8090/rebook/mybook/reviewlist.do?seq=<%= seq %>"; --%>
		</script>
	<%
		} else {
	%>
		<script>
			alert("삭제를 실패했습니다");
			window.history.back();
		</script>
	<%
		}
	%>
	
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>
	
	</script>
</body>
</html>