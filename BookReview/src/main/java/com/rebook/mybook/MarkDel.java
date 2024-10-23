package com.rebook.mybook;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mybook/markdel.do")
public class MarkDel extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
	    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/mybook/markdel.jsp");
	    dispatcher.forward(req, resp);
    }
}