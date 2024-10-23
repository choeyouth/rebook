package com.rebook.book;

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
import com.rebook.mybook.model.MarkDTO;
import com.rebook.mybook.model.RankDTO;
import com.rebook.mybook.model.ReviewDTO;

@WebServlet("/book/detail.do")
public class Detail extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String bookSeq = req.getParameter("seq");

        BookDAO dao = BookDAO.getInstance();
        BookDTO book = dao.getBookDetail(bookSeq); 
        
        List<MarkDTO> mark = dao.getBookMark(bookSeq);  
        List<RankDTO> rank = dao.getBookRank(bookSeq);  
        List<ReviewDTO> review = dao.getBookReview(bookSeq);  
        
        double totalScore = 0.0;

	    for (RankDTO ranks : rank) {
	        totalScore += Double.parseDouble(ranks.getScore());
	    }
	
	    double avgScore = rank.size() > 0 ? totalScore / rank.size() : 0.0;
		
	    System.out.println(mark.toString());
	    System.out.println(review.toString());
	    
        req.setAttribute("book", book);  
        req.setAttribute("mark", mark);  
        req.setAttribute("rank", rank);  
        req.setAttribute("review", review);  
        req.setAttribute("avgScore", avgScore);  
        
        System.out.println("book: " + book);
        System.out.println("mark: " + mark);
        System.out.println("review: " + review);
        System.out.println("avgScore: " + avgScore);

        // 리스트 내용 확인
        for (ReviewDTO r : review) {
            System.out.println("리뷰 내용: " + r.getCommend() + ", 날짜: " + r.getReviewdate());
        }

        for (MarkDTO m : mark) {
            System.out.println("북마크 내용: " + m.getFamousline() + ", 날짜: " + m.getRegdate());
        }

        
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/book/detail.jsp");
		dispatcher.forward(req, resp);
	}
}