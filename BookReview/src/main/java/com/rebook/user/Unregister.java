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

import com.rebook.user.repository.MemberDAO;

@WebServlet("/user/unregister.do")
public class Unregister extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		doPost(req, resp);		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
	
		HttpSession session = req.getSession();
		
		String seq = (String)session.getAttribute("auth");
		
		MemberDAO dao = MemberDAO.getInstance();
		int result = dao.deleteUser(seq);
		
		if (result > 0) {
			
			session.invalidate();
			
			PrintWriter writer = resp.getWriter();
		    writer.print("<html><head><meta charset='UTF-8'></head><body><script>");
		    writer.printf("alert('%s');", "탈퇴가 완료되었습니다.");
		    writer.print("window.location.href='/rebook/home/main.do';"); 
		    writer.print("</script></body></html>");
		    writer.close();  
			
		} else {
			PrintWriter writer = resp.getWriter();
			writer.print("<html><head><meta charset='UTF-8'></head><body><script>"); 
			writer.printf("alert('%s');", "탈퇴에 실패했습니다. ");
			writer.print("history.back();");
			writer.print("</script></body></html>"); 
			writer.close(); 
		}
	}
}