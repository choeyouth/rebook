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

@WebServlet("/user/update.do")
public class Update extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/user/update.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		HttpSession session = req.getSession();
		
	    if (session == null || session.getAttribute("auth") == null) {
	        resp.sendRedirect("/user/login.jsp"); 
	        return;
	    }
		
		String seq = (String)session.getAttribute("auth");
		String name = req.getParameter("name");
	    String tel = req.getParameter("tel");
	    String email = req.getParameter("email");
	    String address = req.getParameter("address");
	    String addrDetail = req.getParameter("addrDetail");
	    String zipcode = req.getParameter("zipcode");
	    
	    MemberInfoDTO dto = new MemberInfoDTO();
	    
	    dto.setMember_seq(seq);
	    dto.setName(name);
	    dto.setTel(tel);
	    dto.setEmail(email);
	    dto.setAddress(address);
	    dto.setAddrDetail(addrDetail);
	    dto.setZipcode(zipcode);

	    MemberDAO dao = MemberDAO.getInstance();
	    int result = dao.updateUser(dto);
	    
	    if (result > 0) {
	    	//업데이트 성공 
			MemberInfoDTO infoResult = dao.loginInfo(seq);
			
			//TODO dto로 보내기, 주소 api 사용 
			session.setAttribute("name", infoResult.getName());
			session.setAttribute("tel", infoResult.getTel());
			session.setAttribute("email", infoResult.getEmail());
			session.setAttribute("pic", infoResult.getPic());
			session.setAttribute("address", infoResult.getAddress());
			session.setAttribute("addrDetail", infoResult.getAddrDetail());
			session.setAttribute("zipcode", infoResult.getZipcode());
			session.setAttribute("regDate", infoResult.getRegDate());
	    	
	    	System.out.println(result);
	    	System.out.println(name);
	    	resp.sendRedirect("/rebook/user/mypage.do");
	    } else {
	    	//업데이트 실패  
			PrintWriter writer = resp.getWriter();
			writer.print("<html><head><meta charset='UTF-8'></head><body><script>"); 
			writer.printf("alert('%s');", "회원 정보 수정에 실패했습니다.");
			writer.print("history.back();");
			writer.print("</script></body></html>"); 
			writer.close(); 
	    }
	    
		
	}
}