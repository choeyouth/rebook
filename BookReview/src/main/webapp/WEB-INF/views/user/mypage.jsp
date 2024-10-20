<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/rebook/assets/css/mypage.css" />
    <script src="https://kit.fontawesome.com/7121714adf.js" crossorigin="anonymous"></script>
	 
    <%@include file="/WEB-INF/views/inc/header.jsp" %>
</head>
<body class="mypage-body">
	<h1 class="profile-title">마이페이지</h1>
    <div class="profile-container">
        <div class="profile-info">
            <i class="fa-solid fa-gear profile-gear" onclick="location.href='/rebook/user/update.do'"></i>
            
            <div><span class="profile-label"></span><img src="/rebook/asset/pic/${pic}" alt="회원사진" /></div>
            <div><span class="profile-label">이름:</span><span class="member-info"> ${name}</span></div>
            <div><span class="profile-label">전화번호:</span><span class="member-info"> ${tel}</span></div>
            <div><span class="profile-label">이메일:</span><span class="member-info"> ${email}</span></div>
            <div><span class="profile-label">주소:</span><span class="member-info"> ${address} ${addrDetail}</span></div>
            <div><span class="profile-label">우편번호:</span><span class="member-info"> ${zipcode}</span></div>
            <div><span class="profile-label">아이디:</span><span class="member-info"> ${id}</span></div>
            <div><span class="profile-label">비밀번호:</span><span class="member-info"> ${password}</span></div>
            <div class="button-container">
	            <button class="btn-pwchange" onclick="location.href='/rebook/user/pwchange.do'">비밀번호 변경</button>
	            <button class="btn-unregister" onclick="location.href='/rebook/user/unregister.do'">회원 탈퇴</button>
            </div>
        </div>
        
        <div class="member-act">
            <h2 class="memberact-title">활동 내역</h2>
            <div class="action-buttons">
                <div class="button-row">
                    <button class="circle-btn">게시물</button> 
                    <button class="circle-btn">댓글</button>
                    <button class="circle-btn">위시<br>리스트</button>
                </div>
                <div class="button-row">
                    <button class="circle-btn">리뷰</button>
                    <button class="circle-btn">평점</button>
                    <button class="circle-btn">북마크</button>
                </div>
            </div>
        </div>
        
        <div class="book-calendar">
	        <h2 id="titleCalendar">
				<span>
					<span>독서 달력 </span>
				</span>	
				<span class="btnCalendar">
					<span id="btnPrev"><i class="fa-solid fa-backward"></i></span>
					<span><span id="date">2024. 10</span></span>
					<span id="btnNext"><i class="fa-solid fa-forward"></i></span>
				</span>
				<button id="btnNow">now</button>
			</h2>
			
			<table id="tblCalendar">
				<thead>
					<tr>
						<th>SUN</th>
						<th>MON</th>
						<th>TUE</th>
						<th>WED</th>
						<th>THU</th>
						<th>FRI</th>
						<th>SAT</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
    </div>
</body>

	<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
	<script>
	
		window.onload = function () {
			
		    const unregister = document.querySelector('.btn-unregister');
	
		    unregister.addEventListener('click', function () {
		    	
		        const confirmed = confirm('정말 탈퇴하시겠습니까?');
		        
		        if (confirmed == true) {
		            const form = document.createElement('form');
		            form.method = 'POST';
		            form.action = '/rebook/user/unregister.do';
	
		            document.body.appendChild(form);
		            form.submit();
		        } 
		    });
		};
		 
		let year = 0;
		let month = 0;
		
		let now = new Date();
		year = now.getFullYear();
		month = now.getMonth();
		
		function create(year, month) {
			$('#date').text(year + '.' + String(month + 1).padStart(2, '0'));
		
			let date = new Date(year, month, 1);
			let firstDay = date.getDay();
			let lastDate = new Date(year, month + 1, 0).getDate();
			
			let temp = '';
			
			temp += '<tr>';

			for (let i=0; i<firstDay; i++) {
				temp += '<td></td>';
			}
			
			//날짜 <td>
			for (let i=1; i<=lastDate; i++) {
				temp += '<td>';
				temp += `<span class="no" data-date="\${i}">\${i}</span>`;
				temp += '<div>'; 
				temp += '</div>'
				temp += '</td>';
				
				if ((i + firstDay) % 7 == 0) {
					temp += '</tr><tr>'
				}
			}
			
			temp += '</tr>';
			
			$('#tblCalendar tbody').html(temp);
			/* loadCalendar(year, month); */
		}
		
		create(year, month);
		
		$('#btnPrev').click(()=>{
			now.setMonth(now.getMonth() - 1);
			year = now.getFullYear();
			month = now.getMonth();
			create(year, month);
		});
		
		$('#btnNow').click(()=>{
			console.log('cc');
			now = new Date();
			year = now.getFullYear();
			month = now.getMonth();
			create(year, month);		
		});
				
		$('#btnNext').click(()=>{
			now.setMonth(now.getMonth() + 1);
			year = now.getFullYear();
			month = now.getMonth();
			create(year, month);
		});
	
	</script>

</html>
