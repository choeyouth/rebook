package com.rebook.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rebook.user.model.MemberDTO;
import com.rebook.user.model.MemberInfoDTO;
import com.rebook.user.repository.MemberDAO;

@WebServlet("/user/login.do")
public class Login extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		 
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/user/login.jsp");
		dispatcher.forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
		
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		
		MemberDTO dto = new MemberDTO();
		dto.setId(id);
		dto.setPassword(pw);
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO result = dao.login(dto);
		
		if (result != null && !result.equals("")) {
			
			MemberInfoDTO infoResult = dao.loginInfo(result.getSeq());
			session.setAttribute("ing", result.getIng());
			
			if (((String)session.getAttribute("ing")).equals("0")) {
				PrintWriter writer = resp.getWriter();
				writer.print("<html><head><meta charset='UTF-8'></head><body><script>"); 
				writer.printf("alert('%s');", "탈퇴한 회원입니다.");
				writer.print("history.back();");
				writer.print("</script></body></html>"); 
				writer.close(); 
			}
			
			
			if (infoResult != null && !infoResult.equals("") && ((String)session.getAttribute("ing")).equals("1")) {
				
				//TODO dto로 보내기로 수정하기.. > jsp, 회원수정도 고쳐야함 
				session.setAttribute("seq", result.getSeq());
				session.setAttribute("id", id);
				session.setAttribute("lv", result.getLv());
				session.setAttribute("name", infoResult.getName());
				session.setAttribute("password", result.getPassword());
				session.setAttribute("tel", infoResult.getTel());
				session.setAttribute("email", infoResult.getEmail());
				session.setAttribute("pic", infoResult.getPic());
				session.setAttribute("address", infoResult.getAddress());
				session.setAttribute("addrDetail", infoResult.getAddrDetail());
				session.setAttribute("zipcode", infoResult.getZipcode());
				session.setAttribute("regDate", infoResult.getRegDate());
				
				resp.sendRedirect("/rebook/home/main.do?seq=" + result.getSeq());
			} 
			
		} else {
			PrintWriter writer = resp.getWriter();
			writer.print("<html><head><meta charset='UTF-8'></head><body><script>"); 
			writer.printf("alert('%s');", "로그인에 실패했습니다.");
			writer.print("history.back();");
			writer.print("</script></body></html>"); 
			writer.close(); 
		}
		
	}
	
}