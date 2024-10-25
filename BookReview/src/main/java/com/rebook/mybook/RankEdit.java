package com.rebook.mybook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.mybook.repository.RankDAO;

@WebServlet("/mybook/rankedit.do")
public class RankEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
	    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/mybook/rankedit.jsp");
	    dispatcher.forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	req.setCharacterEncoding("UTF-8");
    	
    	String score = req.getParameter("score");
    	String rankseq = req.getParameter("rankseq");
    	
    	RankDAO rankDAO = RankDAO.getInstance();
    	int result = rankDAO.edit(score,rankseq);
    	
    	resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        try (PrintWriter out = resp.getWriter()) {
            if (result > 0) {
                out.println("<script>");
                out.println("alert('수정이 완료되었습니다.');");
                out.println("window.opener.location.reload();");
                out.println("window.close();");
                out.println("</script>");
            } else {
                out.println("<script>");
                out.println("alert('수정에 실패했습니다. 다시 시도해주세요.');");
                out.println("window.close();");
                out.println("</script>");
            }
        }
    }
} 
