<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8"><%-- 
<%@include file="/WEB-INF/views/inc/header.jsp"%> --%>

</head>
<style>
</style>
<body>
	<!--  -->
	<!-- <form action="/preference/booklist.do" method="post"> -->
	<!-- 필요한 hidden 필드들 추가 -->
	<input type="hidden" name="action" value="getRecommendations" />

	

	<c:if test="${not empty bookList}">
	 	
		<ul>
			<c:forEach var="book" items="${bookList}" varStatus="status">
			 <c:if test="${status.index == 0}">
			<li>
            	<strong>현재 선택된 서브장르:</strong> ${book.subgenre_seq} <!-- 첫 번째 책의 서브장르 표시 -->
            	<button onclick="window.open('/rebook/preference/booklistedit.do', 'popupWindow', 'width=600,height=400,scrollbars=yes,resizable=yes');">장르 변경</button>
			</li>
        	<hr>
            </c:if>
			
				<li
					onclick="location.href='/rebook/book/detail.do?seq=${book.seq}';"
					style="cursor: pointer;"><img src="${book.cover}"
					alt="${book.name}" /> <strong>${book.name}</strong> -
					${book.author} <input type="hidden" value="${book.seq}"
					name="bookseq"></li>
				<hr>
			</c:forEach>
		</ul>
	</c:if>
	<!--     </form> -->




	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>
		
	</script>
</body>
</html>