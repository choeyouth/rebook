<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title></title> 
	<link rel="stylesheet" href="/rebook/assets/star/star-rating.css">
	<link rel="stylesheet" href="/rebook/assets/css/book-detail.css">
	<script src="https://kit.fontawesome.com/7121714adf.js" crossorigin="anonymous"></script>
	
	
<%@include file="/WEB-INF/views/inc/header.jsp" %>
</head>
<body class="bookDetail-body"> 
	<!--TODO: 위시 가져오기, 평점, 리뷰, 북마크 없을 때 추가코드  -->
    <div class="bookDetail-container">
	
		<!-- 책 정보 -->
		<div class="book-info">
		    <div class="book-image">
			    <img src="${naverbook.image}" alt="${book.name}" id="book-img">
			
			    <div class="star-wish-container">
			        <!-- 하트 아이콘 -->
			        <div id="wish" class="wish ${not empty myWish[0].wishSeq && myWish[0].wishSeq ne '0' ? 'active' : 'delete'}">
			            <i class="fa-regular fa-heart"></i>
			            <i class="fa-solid fa-heart"></i>
			        </div>
			        <div class="wishCount">
			        	<p>WISH (${mybookCount.wishCount}명)</p>
			        </div>
			
			        <!-- 별점 섹션 -->
			        <div class="starlist">
			            <div class="star-add">
						    <select class="star-rating" name="star" id="star" onchange="handleRatingChange(this)">
						        <option value="5" ${myRank[0].score == '5' ? 'selected' : ''}>5점</option>
						        <option value="4" ${myRank[0].score == '4' ? 'selected' : ''}>4점</option>
						        <option value="3" ${myRank[0].score == '3' ? 'selected' : ''}>3점</option>
						        <option value="2" ${myRank[0].score == '2' ? 'selected' : ''}>2점</option>
						        <option value="1" ${myRank[0].score == '1' ? 'selected' : ''}>1점</option>
						        <option value="0" ${myRank[0].score == '0' || empty myRank[0].score ? 'selected' : ''}>0점</option>
						    </select>
			            </div>
			        </div>
			    </div>
			
			    <div class="avgLank">
		            <p>
		            	평균 별점: ${avgScore}
		            	(${mybookCount.rankCount}명)
		            </p>
			    </div>
			    
			    
			    <div class="store-link">
		            <div class="store-images">
			            <p>구매 링크<i class="fa-solid fa-right-long"></i></p>
			            <img src="/rebook/asset/pic/aladin.png" 
			                 onclick="window.open('${otherinfo.link}', '_blank', 'noopener,noreferrer')" 
			                 alt="알라딘">
			            <img src="/rebook/asset/pic/naver.png" 
			                 onclick="window.open('${naverbook.link}', '_blank', 'noopener,noreferrer')" 
			                 alt="네이버">
		            </div>
		        </div>
			</div>
			
		    <!-- 책 상세 정보 -->
		    <div class="details">
		        <h2>${book.name}</h2>
		        <p><strong>저자:</strong> ${book.author}</p>
		        <p><strong>출판:</strong> ${otherinfo.publisher}</p>
		        <p><strong>발행:</strong> ${otherinfo.pubDate}</p>
		        <p><strong>장르:</strong> ${book.genre} > ${book.subgenre}</p>
		        <br>
		        <h3>책 소개</h3>
		        <p>${book.story}</p>
		        <br>
		        <p>${naverbook.description}</p>
		    </div>
		</div>
		
		<hr class="custom-hr">
	
	    <!-- 평점 및 리뷰 -->
	    <div class="rating-review">
	        <h3>리뷰</h3>
	        	<div>(${mybookCount.reviewCount}명)</div>
	        <c:if test="${empty review}">
	            <p>리뷰가 없습니다.</p>
	        </c:if>
	        
	        <c:forEach var="r" items="${review}">
	            <c:if test="${r != null}">
	                <div class="review-item">
	                    <p><strong>회원:</strong> ${r.membername}</p>
	                    <p><strong>내용:</strong> ${r.commend}</p>
	                    <p><strong>날짜:</strong> ${r.reviewdate}</p>
	                </div>
	            </c:if>
	        </c:forEach>
	        
    	    <h3>북마크</h3>
	        <c:if test="${empty mark}">
	            <p>북마크가 없습니다.</p>
	        </c:if>
	        <c:if test="${not empty mark}">
	            <div>(${mybookCount.markCount}명)</div>
	        </c:if>
	        <c:forEach var="m" items="${mark}">
	            <c:if test="${m != null}">
	                <div class="mark-item">
	                    <p><strong>회원:</strong> ${m.membername}</p>
	                    <p><strong>내용:</strong> ${m.famousline}</p>
	                    <p><strong>날짜:</strong> ${m.regdate}</p>
	                </div>
	            </c:if>
	        </c:forEach>
	    </div>
	    
	    
	    
	    <div class="mybook-section">
	    <c:if test="${not empty seq}">
			
			<!-- 리뷰 섹션 -->
			<div class="review-section">
			    <h3>나의 리뷰</h3>
			
			    <c:choose>
			        <c:when test="${empty myReview}">
			        	<textarea placeholder="리뷰 추가"></textarea>
			            <button class="add-review-btn">등록하기</button>
			        </c:when>
			        <c:otherwise>
			            <c:forEach var="review" items="${myReview}">
			                <c:if test="${not empty review.commend}">
			                    <p><strong>내용:</strong> ${review.commend}</p>
			                    <p><strong>날짜:</strong> ${review.reviewDate}</p>
				                <button 
								    class="edit-review-btn mybook-btn" 
								    style="display: block;" 
								    onclick="openWindow('http://localhost:8090/rebook/mybook/reviewedit.do?bookreviewseq=${review.reviewSeq}')">
								    <span class="material-symbols-outlined" title="수정">수정하기</span>
								</button>
								<button 
								    class="delete-review-btn mybook-btn" 
								    style="display: block;" 
								    onclick="if(confirmDelete()) { window.location.href='http://localhost:8090/rebook/mybook/reviewdel.do?bookreviewseq=${review.reviewSeq}&seq=${seq}'; }">
								    <span class="material-symbols-outlined" title="삭제">삭제하기</span>
								</button> 
			                </c:if>
			            </c:forEach>
			        </c:otherwise>
			    </c:choose>
			</div>
			
			<!-- 북마크 섹션 -->
			<div class="mark-section">
			    <h3>나의 북마크</h3>
			
			    <c:choose>
			        <c:when test="${empty myMark}">
			        	<textarea placeholder="북마크 추가"></textarea>
			            <button class="add-mark-btn">등록하기</button>
			        </c:when>
			        <c:otherwise>
			            <c:forEach var="mark" items="${myMark}">
			                <c:if test="${not empty mark.famousline}">
			                    <p><strong>내용:</strong> ${mark.famousline}</p>
			                    <p><strong>날짜:</strong> ${mark.bookmarkDate}</p>
			                    <button 
								    class="edit-mark-btn mybook-btn" 
								    style="display: block;" 
								    onclick="openWindow('http://localhost:8090/rebook/mybook/markedit.do?bookmarkseq=${mark.bookmarkSeq}')">
								    <span class="material-symbols-outlined" title="수정">수정하기</span>
								</button>
								<button 
									class="del-mark-btn mybook-btn"
								    style="display: block;" 
								    onclick="if(confirmDelete()) { window.location.href='http://localhost:8090/rebook/mybook/markdel.do?bookmarkseq=${mark.bookmarkSeq}&seq=${seq}'; }">
								    <span class="material-symbols-outlined" title="삭제">삭제하기</span>
								</button>
			                </c:if>
			            </c:forEach>
			        </c:otherwise>
			    </c:choose>
			</div>
		</c:if>
		</div>
	
	    <!-- 저자의 다른 책 섹션 -->
<!-- 	    <div class="author-info">
	        <h3>저자의 또 다른 책</h3>
	        여기에 추가될 책 정보
	    </div> -->
		
		<hr>
		<button onclick="location.href='/rebook/book/search.do';" style="background-color: #789DBC;" >목록보기</button>
	</div>
    
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="/rebook/assets/star/star-rating.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>
		new StarRating('.star-rating');
		$('.star-rating').parent().css('--gl-tooltip-background', 'transparent');	
		
		document.querySelector('.wish').addEventListener('click', function() {
		    this.classList.toggle('active');
		});
		
		$(document).ready(function () {
		    $('.wish').on('click', function () {
		    	
		        if ('${seq}' === '') {
		            alert('회원만 사용할 수 있는 기능입니다.');
		            $('.wish').removeClass('active').addClass('delete');
		            return;
		        }
		    	
		        const isActive = $(this).hasClass('active');
		        const action = isActive ? 'add' : 'delete';
		        
		        console.log(action);

		        $.ajax({
		            type: 'POST',
		            url: '/rebook/book/detail.do',
		            data: {
		                type: action,
		                bookSeq: '${book.seq}',
		                memberSeq: '${myWish[0].memberSeq}'
		            },
		            success: function (response) {
		                console.log(`Wish ${action} 성공:`, response);
		                if (action === 'add') {
		                    $('.wish').addClass('active').removeClass('delete');
		                    location.reload();
		                } else if (action === 'delete') {
		                    $('.wish').removeClass('active').addClass('delete');
		                    location.reload();
		                }
		            },
		            error: function (xhr, status, error) {
		                console.error(`Wish ${action} 실패:`, xhr.responseText, error);
		            }
		        });
		    });



		   /*  // 평점 변경 시 DB 저장 요청
		    $('#star').on('change', function () {
		    	
		        if ('${seq}' === '') {
		            alert('회원만 사용할 수 있는 기능입니다.');
		            location.reload();
		            return;
		        }
		    	
		        const score = $(this).val();

		        $.ajax({
		            type: 'POST',
		            url: '/rebook/book/detail.do',
		            data: {
		                type: 'rank',
		                bookSeq: '${book.seq}',
		                memberSeq: '${mybooks[0].memberSeq}',
		                score: score
		            },
		            success: function (response) {
		                console.log('평점 업데이트 성공:', response);
		                location.reload();
		            },
		            error: function (xhr, status, error) {
		                console.error('평점 업데이트 실패:', error);
		            }
		        });
		    }); */

		    // 리뷰 등록 요청
		    $('.review-section .add-review-btn').on('click', function () {
		        const commend = $(this).prev().val();

		        if (!commend.trim()) {
		            alert('리뷰 내용을 입력해주세요.');
		            return;
		        }

		        $.ajax({
		            type: 'POST',
		            url: '/rebook/book/detail.do',
		            data: {
		                type: 'review',
		                bookSeq: '${book.seq}',
		                memberSeq: '${mybooks[0].memberSeq}',
		                commend: commend
		            },
		            success: function (response) {
		                console.log('리뷰 등록 성공:', response);
		                location.reload();
		            },
		            error: function (xhr, status, error) {
		                console.error('리뷰 등록 실패:', error);
		            }
		        });
		    });

		    // 북마크 등록 요청
		    $('.mark-section .add-mark-btn').on('click', function () {
		        const famousline = $(this).prev().val();

		        if (!famousline.trim()) {
		            alert('북마크 내용을 입력해주세요.');
		            return;
		        }

		        $.ajax({
		            type: 'POST',
		            url: '/rebook/book/detail.do',
		            data: {
		                type: 'bookmark',
		                bookSeq: '${book.seq}',
		                memberSeq: '${mybooks[0].memberSeq}',
		                famousline: famousline
		            },
		            success: function (response) {
		                console.log('북마크 등록 성공:', response);
		                location.reload();
		            },
		            error: function (xhr, status, error) {
		                console.error('북마크 등록 실패:', error);
		            }
		        });
		    });
		});

	    function confirmDelete() {
	        return confirm("정말 삭제하시겠습니까?");
	    }
	 
	    function openWindow(url) {
	        window.open(url, '_blank', 'width=600,height=800');
	    }
	    
	    function handleRatingChange(selectElement) {
	        const score = selectElement.value; // 선택된 별점 값 가져오기

	        if (!score) {
	            // 별점이 0일 때 삭제 요청
                $.ajax({
                    type: 'POST',
                    url: '/rebook/book/detail.do',
                    data: {
                        type: 'deleteRank',  // 서버에서 삭제를 구분하기 위해 새로운 타입
                        bookSeq: '${book.seq}',
                        memberSeq: '${mybooks[0].memberSeq}'
                    },
                    success: function (response) {
                        alert('별점이 삭제되었습니다.');
                        location.reload(); // 새로고침
                    },
                    error: function (xhr, status, error) {
                        console.error('별점 삭제 실패:', error);
                        alert('별점 삭제에 실패했습니다.');
                    }
                });
	        } else {
	            // 별점이 0이 아닌 경우 업데이트 요청
	            $.ajax({
	                type: 'POST',
	                url: '/rebook/book/detail.do',
	                data: {
	                    type: 'rank',
	                    bookSeq: '${book.seq}',
	                    memberSeq: '${mybooks[0].memberSeq}',
	                    score: score
	                },
	                success: function (response) {
	                    console.log('별점 업데이트 성공:', response);
	                    location.reload();
	                },
	                error: function (xhr, status, error) {
	                    console.error('별점 업데이트 실패:', error);
	                }
	            });
	        }
	    }

	    
	</script>
</body>
</html>