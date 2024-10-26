<%@page import="com.rebook.mybook.repository.MarkDAO"%>
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
		String bookmarkseq = request.getParameter("bookmarkseq");
	
		MarkDAO markDAO = MarkDAO.getInstance();
		int result = 0;
		
		if (bookmarkseq != null && !bookmarkseq.isEmpty()) {
			result = markDAO.del(bookmarkseq);
		}
		
		if (result > 0) {
	%>
		<script>
			alert("삭제가 완료되었습니다");

			// 로컬 스토리지에 새로고침 플래그 저장
			localStorage.setItem('refreshPreviousPage', 'true');

			// 이전 페이지로 이동
			window.location.href = document.referrer;
			<%-- window.location.href = "http://localhost:8090/rebook/mybook/marklist.do?seq=<%= seq %>"; --%>
		</script>
	<%
		} else {
	%>
		<script>
			alert("삭제를 실패했습니다");
			setTimeout(function() {
				window.history.back();
			}, 0);
		</script>
	<%
		}
	%>
	
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>

	
</body>
</html>