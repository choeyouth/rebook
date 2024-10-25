package com.rebook.mybook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.mybook.repository.ReviewDAO;

@WebServlet("/mybook/reviewedit.do")
public class ReviewEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
	    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/mybook/reviewedit.jsp");
	    dispatcher.forward(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	req.setCharacterEncoding("UTF-8");
    	
    	String commend = req.getParameter("commend");
    	String bookreviewseq = req.getParameter("bookreviewseq");
    	
    	ReviewDAO reviewDAO = ReviewDAO.getInstance();
    	int result = reviewDAO.edit(commend, bookreviewseq);
    	
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
