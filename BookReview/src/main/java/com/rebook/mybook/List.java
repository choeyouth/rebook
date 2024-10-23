package com.rebook.mybook;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/mybook/list.do")
public class List extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	HttpSession session = req.getSession();
    	
    	String seq = (String)session.getAttribute("seq");
    	
    	if (seq == null || seq.equals("")) {
    		resp.sendRedirect("/rebook/user/login.do");
            return;
    	}
    	
    	req.setAttribute("seq", seq);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/mybook/list.jsp");
        dispatcher.forward(req, resp);
    }
}
