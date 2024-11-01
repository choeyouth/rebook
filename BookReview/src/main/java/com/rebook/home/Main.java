package com.rebook.home;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.book.model.BookDTO;
import com.rebook.book.repository.BookDAO;
import com.rebook.preference.repository.PreferenceDAO;
import com.rebook.quote.model.QuoteListDTO;
import com.rebook.quote.repository.QuoteListDAO;

@WebServlet("/home/main.do")
public class Main extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BookDAO bdao = BookDAO.getInstance();
        List<BookDTO> recommendedBooks = bdao.getRecommendedBooks();
        req.setAttribute("recommendedBooks", recommendedBooks);
//        System.out.println(recommendedBooks);
        
        // 리뷰 많은 도서 목록 가져오기
        List<BookDTO> topReviewedBooks = bdao.getTopReviewedBooks();
        req.setAttribute("topReviewedBooks", topReviewedBooks);
//        System.out.println(topReviewedBooks);
        
        // 랜덤 인용구 가져오기
        QuoteListDAO dao = QuoteListDAO.getInstance();
        QuoteListDTO quote = dao.getRandomQuote();
        req.setAttribute("quote", quote);
//        System.out.println(quote);
        
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/home/main.jsp");
		dispatcher.forward(req, resp);	
	}
} 