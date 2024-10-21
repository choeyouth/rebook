package com.rebook.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.rebook.user.model.MemberDTO;
import com.rebook.user.model.MemberInfoDTO;
import com.rebook.user.repository.MemberDAO;
import com.rebook.user.repository.MemberInfoDAO;

@WebServlet("/user/register.do")
public class Register extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/user/register.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//RegisterOk.java
		//1. 데이터 가져오기
		//2. DB 작업 > insert
		//3. 결과 처리
		
		//필터 처리
		req.setCharacterEncoding("UTF-8");
		
		
		//req > MultipartRequest
		try {
			System.out.println(req.getRealPath("/asset/pic"));
			
			MultipartRequest multi = new MultipartRequest(
										req,
										req.getRealPath("/asset/pic"),
										1024 * 1024 * 10,
										"UTF-8",
										new DefaultFileRenamePolicy()
									);
			
			//1.
			String name = multi.getParameter("name");
			String id = multi.getParameter("id");
			String pw = multi.getParameter("pw");
			String email = multi.getParameter("email");
			String address = multi.getParameter("address");
			String pic = multi.getFilesystemName("pic");
			
			//System.out.println(pic == null); //true
			//System.out.println(pic == ""); //false
			
			if (pic == null) {
				pic = "default.png";
			}
			
			//2.
			MemberInfoDTO infoDto = new MemberInfoDTO();
			MemberDTO dto = new MemberDTO();
			dto.setId(id);
			dto.setPassword(pw);
			infoDto.setName(name);
			infoDto.setEmail(email);
			infoDto.setAddress(address);
			infoDto.setPic(pic);
			
			
			//DAO의 역할 > DB 작업 수행
			//*** 객체의 역할 > 객체를 1개만 필요로 하는 경우 > 싱글톤(Singleton) 패턴
//			//3.
//			MemberDAO dao = MemberDAO.getInstance();
//			MemberInfoDAO Infodao = MemberInfoDAO.getInstance();
//			
//			int result = dao.register(dto);
//			
//			if (result == 1) {
//				//회원 가입 성공
//				resp.sendRedirect("/toy/index.do");
//			} else {
//				//회원 가입 실패
//				OutputUtil.redirect(resp, "실패했습니다.");
//			}
//						
		} catch (Exception e) {
			System.out.println("Register.doPost");
			e.printStackTrace();
		}
		
	}

}
