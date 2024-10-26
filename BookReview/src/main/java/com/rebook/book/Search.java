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
	    session.removeAttribute("isbnList");
	
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/book/search.jsp");
		dispatcher.forward(req, resp);
		
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String title = req.getParameter("title");
        System.out.println(title);
        
        BookDAO dao = BookDAO.getInstance();
        
        List<String> bookseqs = dao.bookAdd(title);  
        List<BookDTO> bookList = null;
        int resultCount = 0;
        
        if (!bookseqs.isEmpty()) {
        	bookList = dao.bookList(bookseqs);
        	resultCount = bookList.size();
        } 
        
        req.setAttribute("bookseqs", bookseqs);
        req.setAttribute("resultCount", resultCount);
        req.setAttribute("bookList", bookList);
        
//        //네이버 open api 추가 > 판매 링크에 사용 
//  		NaverBookDAO ndao = new NaverBookDAO();
//  		NaverBookDTO ndto = new NaverBookDTO();
//  		
//  		List<NaverBookDTO> naverbookList = ndao.search(bookseqs);
//		System.out.println(ndto.getIsbn());
//		System.out.println(ndto.getLink());
//		System.out.println(ndto.getDescription());
//		
//		req.setAttribute("naverbook", naverbookList);
//        
//        System.out.println(bookseqs.toString());
//        System.out.println(resultCount);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/book/search.jsp");
        dispatcher.forward(req, resp);
        
    }
}
