package com.rebook.quote;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.quote.model.QuoteListDTO;
import com.rebook.quote.repository.QuoteListDAO;

@WebServlet("/quote/search.do")
public class Search extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QuoteListDAO dao = QuoteListDAO.getInstance();
        ArrayList<QuoteListDTO> quoteList = dao.listQuote();
        
        req.setAttribute("quoteList", quoteList);
        
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/quote/search.jsp");
        dispatcher.forward(req, resp);
    }
}