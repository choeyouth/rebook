<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/inc/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
<link rel="stylesheet" href="/rebook/assets/css/main.css">
    <style>
.container {
    width: 100%;
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
}

.row {
    display: block;  /* flex에서 block으로 변경 */
}

.col-md-4 {
    width: 100%;  /* 전체 너비 사용 */
    max-width: 100%;  /* 최대 너비 제한 해제 */
    margin-bottom: 40px;  /* 섹션 간 여백 */
}

.book-container {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));  /* 반응형 그리드 */
    gap: 20px;
    margin-bottom: 20px;
}

.book-item {
    width: 100%;
}

.card {
    background: white;
    border: 1px solid #e0e0e0;
    border-radius: 12px;
    padding: 15px;
    margin-bottom: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
    transition: transform 0.2s;
}
.card:hover {
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}
.card-img-top {
    width: 130px;
    height: 200px;
    object-fit: contain;  /* 이미지 비율 유지 */
    margin-bottom: 10px;
}

.card-body {
    padding: 10px 0;
}

.card-title {
    font-size: 0.9rem;
    margin: 8px 0;
    color: #333;
    line-height: 1.4;
}


h3 {
    margin: 30px 0 20px 0;
    font-size: 1.5rem;
    padding-bottom: 10px;
    border-bottom: 2px solid #eee;
}

/* Random Quote 섹션 스타일 */
blockquote {
    margin: 0;
    padding: 20px;
    background-color: #f9f9f9;
    border-left: 5px solid #ddd;
}

.blockquote-footer {
    margin-top: 10px;
    color: #666;
}


/* 반응형 처리 */
@media (max-width: 768px) {
    .book-grid {
        grid-template-columns: repeat(2, 1fr);
        gap: 15px;
    }
    
    .card-img-top {
        height: 160px;
    }
}

@media (max-width: 480px) {
    .book-grid {
        grid-template-columns: 1fr;
    }
}
    </style>
</head>
<body>
<div class="container">
  <div class="row">
    <!-- Recommended Books Section -->
    <div class="col-md-4">
      <h3>Recommended Books</h3>
      <div class="card">
        <div class="row">
          <c:forEach items="${recommendedBooks}" var="book">
            <div class="col-12 mb-3" onclick="location.href='/rebook/book/detail.do?seq=${book.seq}'">
              <div class="card">
                <img src="${book.cover}" class="card-img-top" alt="${book.name}">
                <div class="card-body">
                  <h5 class="card-title">${book.name}</h5>
                  <p class="card-text">
                    Author: ${book.author}
                  </p>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>
      </div>
    </div>
    

    <!-- Top Reviewed Books Section -->
    <div class="col-md-4">
      <h3>Top Reviewed Books</h3>
      <div class="card">
        <div class="row">
          <c:forEach items="${topReviewedBooks}" var="book">
            <div class="col-12 mb-3" onclick="location.href='/rebook/book/detail.do?seq=${book.seq}'">
              <div class="card">
                <img src="${book.cover}" class="card-img-top" alt="${book.name}">
                <div class="card-body">
                  <h5 class="card-title">${book.name}</h5>
                  <p class="card-text">
                    Review Count: ${book.count}
                  </p>
                </div>
              </div>
            </div>
          </c:forEach>
        </div>
      </div>
    </div>

    <!-- Random Quote Section -->
    <div class="col-md-4">
      <h3>Random Quote</h3>
      <div class="card">
        <div class="card-body" >
          <blockquote class="blockquote">
            <img src="/rebook/asset/pic/${quote.authorpic}" class="card-img-top mb-3">
            <p class="mb-0">${quote.quote}</p>
            <footer class="blockquote-footer">${quote.author}</footer>
          </blockquote>
        </div>
      </div>
    </div>
  </div>
</div>

	
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>
	
	</script>
</body>
</html>