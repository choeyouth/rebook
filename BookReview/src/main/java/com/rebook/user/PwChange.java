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

import com.rebook.user.model.MemberInfoDTO;
import com.rebook.user.repository.MemberDAO;

@WebServlet("/user/pwchange.do")
public class PwChange extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/user/pwchange.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String seq = (String)session.getAttribute("auth");
		String currentPw = req.getParameter("currentPw");
		String newPw = req.getParameter("newPw");
		String confirmPw = req.getParameter("confirmPw");
		
		if (!newPw.equals(confirmPw)) {
			PrintWriter writer = resp.getWriter();
			writer.print("<html><head><meta charset='UTF-8'></head><body><script>"); 
			writer.printf("alert('%s');", "새로운 비밀번호가 일치하지 않습니다.");
			writer.print("history.back();");
			writer.print("</script></body></html>"); 
			writer.close(); 
			
			return;
		}
		
		MemberDAO dao = MemberDAO.getInstance();
		boolean isPw = dao.isPw(seq, currentPw);
		
		if (isPw == false) {
			
			PrintWriter writer = resp.getWriter();
			writer.print("<html><head><meta charset='UTF-8'></head><body><script>"); 
			writer.printf("alert('%s');", "비밀번호가 일치하지 않습니다.");
			writer.print("history.back();");
			writer.print("</script></body></html>"); 
			writer.close(); 
			
			return;
		}
		
		int result = dao.updatePw(seq, newPw);
		
		if (result > 0) {
			
			session.setAttribute("password", newPw);
			System.out.println(newPw);
			
			PrintWriter writer = resp.getWriter();
			writer.print("<html><head><meta charset='UTF-8'></head><body><script>"); 
			writer.printf("alert('%s');", "비밀번호가 변경되었습니다.");
			writer.print("window.location.href='/rebook/user/mypage.do';"); 
			writer.print("</script></body></html>"); 
			writer.close(); 
		}
		
	
	}
}