<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 정보 수정</title>
    <link rel="stylesheet" href="/styles/profile.css">
    <%@include file="/WEB-INF/views/inc/header.jsp" %>
    <style>
    
	    @font-face {
            font-family: 'Pretendard';
            src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
            font-weight: 900;
            font-style: normal;
        }
        
     	@font-face {
		    font-family: 'LINESeedKR-Bd';
		    src: url('https://fastly.jsdelivr.net/gh/projectnoonnu/noonfonts_11-01@1.0/LINESeedKR-Bd.woff2') format('woff2');
		    font-weight: 400;
		    font-style: normal;
		}
    
    	.update-body {
    		margin-top: 100px;
    		background-color: #EFEAD8;
    	}
    	
    	.update-container {
		    max-width: 600px;
		    margin: 50px auto;
		    padding: 20px;
		    border: 1px solid #ddd;
		    border-radius: 10px;
		    background-color: #f9f9f9;
		    font-family: 'Pretendard';
		}
		
		.update-container h1 {
		    text-align: center;
		    margin-bottom: 50px;
		    font-family: 'LINESeedKR-Bd';
		}
		
		.update-container form div {
		    margin-bottom: 15px;
		    display: flex;
		    justify-content: space-between;
		}
		
		.update-container input {
		    width: 70%;
		    padding: 8px;
		    border: 1px solid #ccc;
		    border-radius: 5px;
		}
		
		.update-container button {
		    width: 100%;
		    padding: 10px;
		    background-color: #A2CA71;
		    color: white;
		    border: none;
		    cursor: pointer;
		    border-radius: 5px;
		    margin-top: 10px;
		    font-family: 'Pretendard';
		}
		
		.update-container button:hover {
		    background-color: #CEDEBD;
		}
		    	
    
    </style>
</head>
<body class="update-body">
    <div class="update-container">
        <h1>회원 정보 수정</h1>
        <form action="/rebook/user/update.do" method="POST">
            <div>
                <label>이름: </label>
                <input type="text" name="name" value="${name}" required>
            </div>
            <div>
                <label>전화번호: </label>
                <input type="text" name="tel" value="${tel}" required>
            </div>
            <div>
                <label>이메일: </label>
                <input type="email" name="email" value="${email}" required>
            </div>
            <div>
                <label>주소: </label>
                <input type="text" name="address" value="${address}" required>
            </div>
            <div>
                <label>상세 주소: </label>
                <input type="text" name="addrDetail" value="${addrDetail}" required>
            </div>
            <div>
                <label>우편번호: </label>
                <input type="text" name="zipcode" value="${zipcode}" required>
            </div> 
            <div>
            	<input type="hidden" name="seq" value="${auth}">
            	<input type="hidden" name="id" value="${id}">
            </div>
            <button type="submit">수정 완료</button>
        </form>
        <button onclick="history.back()">취소</button>
    </div>
</body>
</html>
