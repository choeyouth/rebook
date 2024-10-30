<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<h1>장르 변경</h1>
<form method="POST" action="/rebook/preference/booklist.do"
					enctype="multipart/form-data">
<div>
    <select id="genrelist" class="genrelist" onchange="updateSubgenre()">
        <option selected>장르</option>
        <c:forEach var="genre" items="${genrelist}">
            <option value="${genre.seq}"><c:out value="${genre.genre}"/></option>
        </c:forEach>
    </select>
</div>

<div>
    <select id="subgenrelist" class="subgenrelist">
        <option selected>서브장르</option>
        <c:forEach var="subgenre" items="${subgenrelist}">
            <option value="${subgenre.seq}"><c:out value="${subgenre.subgenre}"/></option>
        </c:forEach>
    </select>
</div>
<div><input type="text" name="targetread" id="targetread"></div>
<button>장르 변경</button>
</form>
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
<script>

function updateSubgenre() {
    const genreSelect = document.getElementById('genrelist');
    const subgenreSelect = document.getElementById('subgenrelist');
    const selectedGenreId = genreSelect.value;
    
    console.log("선택된 장르 ID:", selectedGenreId); // 디버깅용 로그
    
    // 장르가 선택되지 않았거나 기본 옵션인 경우
    if (!selectedGenreId || selectedGenreId === '장르') {
        subgenreSelect.innerHTML = '<option selected>서브장르</option>';
        return;
    }

    // AJAX 요청
    $.ajax({
        url: 'booklistedit.do',  // 상대 경로로 변경
        method: 'GET',
        data: { genreId: selectedGenreId },
        dataType: 'json', // 응답 타입을 JSON으로 설정
        success: function(response) {
            console.log("서버 응답:", response); // 디버깅용 로그
            
            // 서브장르 select 초기화
            subgenreSelect.innerHTML = '<option selected>서브장르</option>';
            
            // 받아온 서브장르 데이터로 옵션 추가
            if (response && response.length > 0) {
                response.forEach(function(subgenre) {
                    const option = new Option(subgenre.subgenre, subgenre.seq);
                    subgenreSelect.appendChild(option);
                });
            }
        },
        error: function(xhr, status, error) {
            console.error('서브장르 데이터를 가져오는데 실패했습니다.'); // 디버깅용 로그
            console.error('상태:', status);
            console.error('에러:', error);
            console.error('응답:', xhr.responseText);
        }
    });
}

// 페이지 로드 시 이벤트 리스너 등록
$(document).ready(function() {
    // 초기 상태 설정
    const subgenreSelect = document.getElementById('subgenrelist');
    subgenreSelect.innerHTML = '<option selected>서브장르</option>';
    
    // 장르 선택 이벤트 리스너
    $('#genrelist').on('change', updateSubgenre);
});
</script>
</body>
</html>