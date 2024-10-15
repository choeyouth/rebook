package com.rebook.library;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.library.model.LibraryDTO;
import com.rebook.library.repository.LibraryDAO;

@WebServlet("/library/search.do")
public class Search extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	String search = req.getParameter("search");
    	
        LibraryDAO dao = LibraryDAO.getInstance();
        ArrayList<LibraryDTO> libraryList = dao.listLibrary();
        
        if (search != null && !search.isEmpty()) {
            libraryList = dao.searchLib(search);
        } else {
            libraryList = dao.listLibrary();
        }
        
        req.setAttribute("libraryList", libraryList);
        
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/library/search.jsp");
        dispatcher.forward(req, resp);
    }
}