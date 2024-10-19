<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <script src="https://kit.fontawesome.com/7121714adf.js" crossorigin="anonymous"></script>
    <style>

        @font-face {
            font-family: 'Pretendard';
            src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
            font-weight: 900;
            font-style: normal;
        }
        
        @font-face {
		    font-family: 'Paperlogy-8ExtraBold';
		    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/2408-3@1.0/Paperlogy-8ExtraBold.woff2') format('woff2');
		    font-weight: 800;
		    font-style: normal;
		}
		
		@font-face {
		    font-family: 'KOTRA_BOLD-Bold';
		    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_20-10-21@1.1/KOTRA_BOLD-Bold.woff') format('woff');
		    font-weight: normal;
		    font-style: normal;
		}
		
		@font-face {
		    font-family: 'LINESeedKR-Bd';
		    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/LINESeedKR-Bd.woff2') format('woff2');
		    font-weight: 400;
		    font-style: normal;
		}

        .profile-container h1 {
            font-family: 'KOTRA_BOLD-Bold', 'Paperlogy-8ExtraBold', 'Pretendard-Regular', 'GmarketSansMedium', sans-serif; 
        }
        
       	.profile-container h2 {
            font-family: 'Pretendard-Regular', sans-serif; 
            padding-top: 20px;
            padding-bottom: 20px;
        }
        
        .profile-container .profile-label {
        	font-family: 'LINESeedKR-Bd', sans-serif; 
        }  
        
        .profile-container button {
        	font-family: 'Pretendard-Regular', sans-serif; 
        	font-weight: bold;
        }
        
        .profile-container .member-info {
        	font-family: 'Pretendard-Regular', sans-serif; 
        	weight: 100;
        }
       


        .mypage-body * {
            margin: 0;
            padding: 0;
            box-sizing: border-box; 
        }

        .mypage-body html, body {
        	padding-top: 50px;
            height: 100%;
            background-color: #EFEAD8;
        }

        .mypage-body body {
            display: flex;
            flex-direction: column; /* 세로 정렬 */ 
        }

        .mypage-body .profile-container {
            flex: 1; /* 남은 공간을 모두 차지하도록 설정 */
            display: grid;
            grid-template-columns: 1fr 2fr;
            grid-template-rows: auto;
            gap: 20px;
            padding: 20px; 
            border: 0px solid #ddd;  
            
        }

        .mypage-body .profile-title {
            padding-top: 20px;
            grid-column: 1 / -1;
            text-align: center;
            font-size: 35px;
            margin-bottom: 20px;
        }

        .mypage-body .memberact-title {
            text-align: center;
        }

        .mypage-body .profile-info {
            display: flex;
            flex-direction: column;
            gap: 10px;
            border: 1px solid #ccc;
            padding: 20px;
            position: relative;
            flex: 1; /* 남은 공간을 모두 차지하도록 설정 */
        }

        .mypage-body .profile-info img {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            margin-bottom: 20px;
        }

        .mypage-body .profile-label {
            font-weight: bold;
        }

        button {
            margin-top: 20px;
            padding: 10px;
            background-color: #A2CA71;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }

        .mypage-body .member-act {
            grid-column: 2 / 3;
            border: 1px solid #ccc;
            padding: 20px;
            display: flex;
            flex-direction: column;
            gap: 10px;
            flex: 1; /* 남은 공간을 모두 차지하도록 설정 */
        }

        .mypage-body .action-buttons {
            display: flex;
            flex-direction: column;
            gap: 10px; /* 각 행 사이의 간격 */
        }

        .mypage-body .button-row {
            display: flex;
            justify-content: space-evenly;
        }

        .mypage-body .circle-btn {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            background-color: #9EB384;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 16px;
            display: flex;
            justify-content: center;
            align-items: center;
            transition: background-color 0.3s ease; 
        }

        .mypage-body .circle-btn:hover {
            background-color: #CEDEBD;
        }

        .mypage-body .book-calendar {
            grid-column: 2 / 3;
            border: 1px solid #ccc;
            padding: 20px;
            text-align: center;
            flex: 1; /* 남은 공간을 모두 차지하도록 설정 */
        }

        .mypage-body .profile-gear {
            position: absolute;
            top: 20px;
            right: 20px;
            cursor: pointer;
            font-size: 24px;
        }

        .mypage-body .profile-gear:hover {
            color: #CEDEBD;
        }
        
        
        .btn-delete {
        	width: 100px;
        	height: 35px;
        	border-radius: 30px;
        	margin-top: 10px;
        	background-color: #9EB384;
        	
        }
        
        .btn-delete:hover {
        	background-color: #CEDEBD;
        }
        

        @media (max-width: 768px) {
            .profile-container {
                grid-template-columns: 1fr;
            }

            .profile-info, .member-act, .book-calendar {
                grid-column: 1 / -1;
            }
        }
    </style>

    <%@include file="/WEB-INF/views/inc/header.jsp" %>
</head>
<body class="mypage-body">
    <div class="profile-container">
    
        <!-- <h2 class="profile-title">마이페이지</h2> -->
        <h1 class="profile-title">마이페이지</h1>

        <!-- 프로필 정보 -->
        <div class="profile-info">
            <!-- 톱니바퀴 아이콘 (회원 정보 수정) -->
            <i class="fa-solid fa-gear profile-gear" onclick="location.href='/rebook/user/update.do'"></i>
            
            <div><span class="profile-label"></span><img src="/rebook/asset/pic/${pic}" alt="회원사진" /></div>
            <div><span class="profile-label">이름:</span><span class="member-info"> ${name}</span></div>
            <div><span class="profile-label">전화번호:</span><span class="member-info"> ${tel}</span></div>
            <div><span class="profile-label">이메일:</span><span class="member-info"> ${email}</span></div>
            <div><span class="profile-label">주소:</span><span class="member-info"> ${address} ${addrDetail}</span></div>
            <div><span class="profile-label">우편번호:</span><span class="member-info"> ${zipcode}</span></div>
            <div><span class="profile-label">아이디:</span><span class="member-info"> ${id}</span></div>
            <div><span class="profile-label">비밀번호:</span><span class="member-info"> ${password}</span></div>
            <button class="btn-delete" onclick="location.href='/rebook/user/delete.do'">회원 탈퇴</button>
        </div>
        
        <!-- 활동 내역 -->
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
        
        <!-- 독서 달력 -->
        <div class="book-calendar">
            <h2>독서 달력</h2>
            <div>1 2 3 4 5 6</div>
            <div>7 8 9 10 11 12 13</div>
            <div>14 15 16 17 18 19 20</div>
            <div>21 22 23 24 25 26 27</div>
            <div>28 29 30 31</div>
        </div>
    </div>
    
    

 
</body>
</html>
