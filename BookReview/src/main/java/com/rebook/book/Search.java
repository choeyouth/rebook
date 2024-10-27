package com.rebook.book;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rebook.book.model.BookDTO;
import com.rebook.book.model.NaverBookDTO;
import com.rebook.book.repository.BookDAO;
import com.rebook.book.repository.NaverBookDAO;

@WebServlet("/book/search.do")
public class Search extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		
	    // 세션에서 검색 결과 삭제 (새로고침 시 사라지게)
	    session.removeAttribute("queryType");
	    session.removeAttribute("query");
	    System.out.println("삭제 테스트 " + session.getAttribute("query"));
	    
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/book/search.jsp");
		dispatcher.forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException {

	    req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");

	    String queryType = req.getParameter("queryType");
	    String query = req.getParameter("query");
	    int count = Integer.parseInt(req.getParameter("count"));
	    int start = Integer.parseInt(req.getParameter("start"));

	    System.out.println("queryType: " + queryType);
	    System.out.println("query: " + query);
	    System.out.println("count: " + count);
	    System.out.println("start: " + start); 

	    BookDAO dao = BookDAO.getInstance();
	    List<String> bookseqs = dao.bookAdd(queryType, query, start, count);
	    List<BookDTO> bookList = null;
	    int resultCount = 0;

	    if (!bookseqs.isEmpty()) {
	        bookList = dao.bookList(bookseqs);
	        resultCount = bookList.size();
	    }

	    req.setAttribute("bookseqs", bookseqs);
	    req.setAttribute("resultCount", resultCount);
	    req.setAttribute("bookList", bookList);
	    req.setAttribute("count", count);
	    req.setAttribute("start", start);
	    req.setAttribute("queryType", queryType);
	    req.setAttribute("query", query);

	    RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/book/search.jsp");
	    dispatcher.forward(req, resp);
	}

	
}
