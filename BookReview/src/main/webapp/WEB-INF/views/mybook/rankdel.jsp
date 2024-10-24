<%@page import="com.rebook.mybook.repository.RankDAO"%>
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
		String rankseq = request.getParameter("rankseq");
		
		RankDAO rankDAO = RankDAO.getInstance();
		int result = 0;
		
		if (rankseq != null && !rankseq.isEmpty()) {
			result = rankDAO.del(rankseq);
		}
		
		if (result > 0) {
	%>
		<script>
			alert("삭제가 완료되었습니다");
			window.location.href = "http://localhost:8090/rebook/mybook/ranklist.do?seq=<%= seq %>";
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