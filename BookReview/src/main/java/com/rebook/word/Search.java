package com.rebook.word;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.word.model.WordDTO;
import com.rebook.word.repository.WordDAO;

@WebServlet("/word/search.do")
public class Search extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String search = req.getParameter("search");
        
        WordDAO dao = WordDAO.getInstance();
        
        ArrayList<WordDTO> wordList = dao.listWordBySearch(search);
        
        req.setAttribute("wordList", wordList);
        
        req.setAttribute("search", search);
        
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/word/search.jsp");
        dispatcher.forward(req, resp);
    }
}