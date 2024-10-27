<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head> 
	<meta charset="UTF-8">
	<title></title>
	<link rel="stylesheet" href="http://bit.ly/3WJ5ilK">
	<script src="https://kit.fontawesome.com/7121714adf.js" crossorigin="anonymous"></script>
	
	<style>

	</style>
	
<%@include file="/WEB-INF/views/inc/header.jsp" %>
	
</head>
<body>
	<!--  -->
	<h1 class="page">게시판 <small>상세보기</small></h1>

		<table class="vertical" id="view">
			<tr>
				<th>번호</th>
				<td>${post.seq}</td>
			</tr>
			<tr>
				<th>이름</th>
				<td>${post.memberName}(${post.memberId})</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${post.title}</td>
			</tr>
			<tr>
				<th>책제목</th>
				<td>${post.bookTitle}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>${post.content}</td>
			</tr> 
			<c:if test="${not empty post.img}">
			<tr>
				<th>이미지</th>
				<td>${post.img}</td>
			</tr>
			</c:if>
			<tr>
				<th>날짜</th>
				<td>${post.postDate}</td>
			</tr>
			<tr>
				<th>읽음</th>
				<td>${post.readCount}</td>
			</tr>
		</table>
		
		<table id="comment">
		    <c:forEach var="r" items="${reply}">
		        <tr id="row-${r.seq}"> 
		            <td>
		                <div>${r.seq}</div>
		                <div id="reply-${r.seq}">${r.reply}</div>
		                <div>${r.commitDate}</div>
		            </td>
		            <td>
		                <div>
		                    <div>${r.memberName}(${r.memberId})</div>
		                    <c:if test="${not empty seq && (r.member_seq == seq)}">
		                        <div>
		                            <span class="material-symbols-outlined" 
		                                  onclick="editReply(${r.seq})">edit_note</span>
		                            <span class="material-symbols-outlined"
		                                  onclick="deleteReply(${r.seq})">delete</span>
		                        </div>
		                    </c:if>
		                </div>
		            </td>
		        </tr> 
		    </c:forEach>
		</table>


		
		<c:if test="${not empty seq}">
		<form id="addCommentForm" onsubmit="return false;">
		<table id="addComment">
			<tr>	
				<td><input type="text" name="content"></td>
				<td><button type="button" class="comment" id="btnAddComment" onclick="addComment(${seq}, ${post.seq})">댓글 쓰기</button></td>
			</tr>
		</table>
		</form>
		</c:if>
		
		<div>
			<button type="button" class="back" onclick="location.href='/rebook/discussion/boardlist.do?';">돌아가기</button>
			<c:if test="${not empty seq && (post.member_seq == seq)}">
			<button type="button" class="edit" onclick="location.href='/rebook/discussion/postedit.do?postseq=${post.seq}';">수정하기</button>
			<button type="button" class="del" onclick="deletePost(${post.seq});">삭제하기</button>
			</c:if>	
		</div>
	
	
	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script src="https://bit.ly/4cMuheh"></script>
	<script>

		let id;
		let reseq;
	
		function editReply(replySeq) {
			
			console.log("replySeq : " + replySeq);
			id = 'reply-' + replySeq;
			reseq = replySeq;
			const replyDiv = document.getElementById(id);
			
			
	        if (!replyDiv) {
	            console.error(`Element with ID reply-${replySeq} not found.`);
	            return;
	        }
	        
	        const replyText = replyDiv.innerText;
	        console.log(replyText);

	        // 댓글을 텍스트 필드로 변환
	        let template = `
			    <input type="text" id="edit-input-%s" value="%s" style="width: 80%;">
			    <button onclick="saveReply(%s)">저장</button>
			    <button onclick="cancelEdit(%s, '%s')">취소</button>
			`;
			
			let formattedHTML = template
			    .replace('%s', replySeq)
			    .replace('%s', replyText)
			    .replace('%s', replySeq)
			    .replace('%s', replySeq)
			    .replace('%s', replyText);

	        replyDiv.innerHTML = formattedHTML;
			
		}
		
		function saveReply(replySeq) {
			const editid = 'edit-input-' + replySeq;
			console.log(editid);
			const editedReply = document.getElementById(editid).value;
	        console.log('Saving reply ' + replySeq + ' : ' + editedReply);

	        const replyDiv = document.getElementById(id);

	        if (!replyDiv) {
	            console.error('Element with ID reply-' + replySeq + 'not found.');
	            return;
	        }

	        // 서버에 저장 요청 (AJAX 사용)
	        $.ajax({
	            url: '/rebook/discussion/replyedit.do',  // 서버의 엔드포인트 설정
	            type: 'POST',
	            data: JSON.stringify({
	                seq: replySeq,
	                reply: editedReply
	            }),
	            contentType: 'application/json',  // JSON 형식으로 전송
	            success: function(response) {
	                console.log('Reply updated successfully:', response);
	                replyDiv.innerText = editedReply;  // 서버 응답 후 DOM 업데이트
	            },
	            error: function(xhr, status, error) {
	                console.error('Failed to update reply:', status, error);
	                alert('댓글을 저장하는 중 오류가 발생했습니다.');
	            }
	        });
		}
		
		function cancelEdit(replySeq, originalText) {
	        const replyDiv = document.getElementById('reply-' + replySeq);
	        replyDiv.innerText = originalText;
	    };
		
		
		function deleteReply(replySeq) {
			if (confirm("댓글을 삭제하시겠습니까?")) {
	            console.log('Deleting reply ' + replySeq);
	            const row = document.getElementById('row-' + replySeq);
	            
	            if (row) {
	                row.remove();  // DOM에서 댓글 삭제
	                console.log('Deleted reply ' + replySeq);
	                
	                $.ajax({
	                	url: '/rebook/discussion/replydel.do',
	                	type: 'POST',
	                	data: JSON.stringify({
	    	                seq: replySeq
	    	            }),
	    	            contentType: 'applicaion/json',
	    	            success: function(response) {
	    	                console.log('Deleted reply successfully:', response);

	    	                const row = document.getElementById('row-' + replySeq);
	    	                if (row) {
	    	                    row.remove();
	    	                } else {
	    	                    console.error('Element with ID row-' + replySeq + ' not found.');
	    	                }
	    	            },
	    	            error: function(xhr, status, error) {
	    	                console.error('Failed to delete reply:', status, error);
	    	                alert('댓글을 삭제하는 중 오류가 발생했습니다.');
	    	            }
	                });
	                
	            } else {
	                console.error('Element with ID row-' + replySeq + ' not found.');
	            }
	        }
		}
		
	
		function addComment(postSeq, memberSeq) {
		    const contentInput = document.querySelector('input[name="content"]');
		    const content = contentInput.value.trim(); // 입력된 댓글 내용

		    if (content === "") {
		        alert("댓글 내용을 입력해 주세요.");
		        return;
		    }

		    $.ajax({
		        url: '/rebook/discussion/replyadd.do',
		        type: 'POST',
		        data: JSON.stringify({
		            postSeq: postSeq,
		            memberSeq: memberSeq,
		            content: content
		        }),
		        contentType: 'application/json; charset=utf-8',
		        dataType: 'json',  
		        success: function(response) {
		            console.log('Comment added successfully:', response);
		            location.reload();  
		        },
		        error: function(xhr, status, error) {
		            console.error('Failed to add comment:', status, error);
		            alert('댓글을 추가하는 중 오류가 발생했습니다.');
		        }
		    });
		}
		
		function deletePost(postSeq) {
		    if (confirm("정말로 이 게시글을 삭제하시겠습니까?")) {
		        $.ajax({
		            url: '/rebook/discussion/postdel.do',
		            type: 'POST',
		            data: { seq: postSeq },
		            success: function(response) {
		                alert('게시글이 삭제되었습니다.');
		                window.location.href = '/rebook/discussion/boardlist.do';  // 목록 페이지로 이동
		            },
		            error: function(xhr, status, error) {
		                console.error('Failed to delete post:', error);
		                alert('게시글 삭제에 실패했습니다.');
		            }
		        });
		    }
		}


	</script>
</body>
</html>