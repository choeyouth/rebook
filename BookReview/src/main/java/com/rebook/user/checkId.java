package com.rebook.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.user.model.MemberDTO;
import com.rebook.user.repository.MemberDAO;

@WebServlet("/user/checkId.do")
public class checkId extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // 요청 파라미터에서 id 가져오기
    	String id = req.getParameter("id");

    	MemberDTO dto = new MemberDTO();
    	dto.setId(id);
        
        // DB 연동하여 아이디 존재 여부 확인
        MemberDAO dao = MemberDAO.getInstance();
        boolean isAvailable = dao.checkIdAvailability(dto);
        
        // 응답 설정
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        
        // 결과 전송
        PrintWriter out = resp.getWriter();
        out.print(isAvailable ? "available" : "unavailable");
        out.flush();
    }
}