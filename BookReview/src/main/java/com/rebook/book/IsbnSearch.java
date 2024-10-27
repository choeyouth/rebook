package com.rebook.book;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.book.model.BookDTO;
import com.rebook.book.repository.BookDAO;

@WebServlet("/book/isbnsearch.do")
public class IsbnSearch extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/book/isbnsearch.jsp");
		dispatcher.forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	    
	    String query = req.getParameter("query");
	    System.out.println(query);
	    BookDAO dao = BookDAO.getInstance();
	    List<String> bookseqs = dao.bookAdd("Title", query, 1, 1);
	    List<BookDTO> bookList = dao.bookList(bookseqs);

	    
	    System.out.println(bookList.toString());
	    
	    req.setAttribute("bookseqs", bookseqs);
	    req.setAttribute("bookList", bookList);
	    req.setAttribute("query", query);
	    
	    if (!bookseqs.isEmpty()) {
	        String bookSeq = bookseqs.get(0);  // 첫 번째 책의 seq
	        String bookTitle = bookList.get(0).getName();
	        System.out.println(bookSeq);
	        System.out.println(bookTitle);
	        // URL 인코딩
	        String encodedBookTitle = URLEncoder.encode(bookTitle, StandardCharsets.UTF_8.toString());
	        String encodedBookSeq = URLEncoder.encode(bookSeq, StandardCharsets.UTF_8.toString());

	        String redirectUrl = String.format("/rebook/discussion/postadd.do?bookseq=%s&booktitle=%s", encodedBookSeq, encodedBookTitle);

	        resp.sendRedirect(redirectUrl); // 인코딩된 URL로 리다이렉트
	        //resp.sendRedirect("/rebook/discussion/postadd.do?bookseq=" + bookSeq + "?booktitle=" + bookTitle);
	    } else {
	        resp.sendRedirect("/rebook/book/search.do?error=noResults");
	    }
		
	}
}