package com.rebook.mybook;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.mybook.model.MarkDTO;
import com.rebook.mybook.repository.MarkDAO;

@WebServlet("/mybook/markedit.do") 
public class MarkEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
	    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/mybook/markedit.jsp");
	    dispatcher.forward(req, resp);
    }
}