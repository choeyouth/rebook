package com.rebook.book;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rebook.book.model.BookDTO;
import com.rebook.book.model.MyBookDTO;
import com.rebook.book.model.MyBookMarkDTO;
import com.rebook.book.model.MyBookRankDTO;
import com.rebook.book.model.MyBookReviewDTO;
import com.rebook.book.model.MyBookWishDTO;
import com.rebook.book.model.MybookCountDTO;
import com.rebook.book.model.NaverBookDTO;
import com.rebook.book.model.OtherInfoDTO;
import com.rebook.book.model.WishBookDTO;
import com.rebook.book.repository.BookDAO;
import com.rebook.book.repository.NaverBookDAO;
import com.rebook.mybook.model.MarkDTO;
import com.rebook.mybook.model.RankDTO;
import com.rebook.mybook.model.ReviewDTO;

@WebServlet("/book/detail.do")
public class Detail extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		String memberSeq = (String)session.getAttribute("seq");
		String bookSeq = req.getParameter("seq");

        BookDAO dao = BookDAO.getInstance();
        BookDTO book = dao.getBookDetail(bookSeq); 
        OtherInfoDTO idto = new OtherInfoDTO();
        MyBookDTO mdto = new MyBookDTO();
        
        List<MarkDTO> mark = dao.getBookMark(bookSeq);  
        List<RankDTO> rank = dao.getBookRank(bookSeq);  
        List<ReviewDTO> review = dao.getBookReview(bookSeq);  
        List<WishBookDTO> wish = dao.getBookWish(bookSeq);
        
        List<MyBookMarkDTO> myMark = dao.getMyBookMark(bookSeq, memberSeq);
        List<MyBookRankDTO> myRank = dao.getMyBookRank(bookSeq, memberSeq);
        List<MyBookReviewDTO> myReview = dao.getMyBookReview(bookSeq, memberSeq);
        List<MyBookWishDTO> myWish = dao.getMyBookWish(bookSeq, memberSeq);
        
        
        
        idto = dao.getOtherInfo(bookSeq);
        
        double totalScore = 0.0;

	    for (RankDTO ranks : rank) {
	        totalScore += Double.parseDouble(ranks.getScore());
	    }
	
	    double avgScore = rank.size() > 0 ? totalScore / rank.size() : 0.0;
		
	    MybookCountDTO mybookCount = new MybookCountDTO(
    	    mark.size(),
    	    rank.size(),
    	    review.size(),
    	    wish.size()
    	);
	    
        req.setAttribute("book", book);  
        req.setAttribute("mark", mark);  
        req.setAttribute("rank", rank);  
        req.setAttribute("review", review);  
        req.setAttribute("avgScore", avgScore);  
        req.setAttribute("wish", wish);
        req.setAttribute("mybookCount", mybookCount);
        req.setAttribute("myMark", myMark);
        req.setAttribute("myRank", myRank);
        req.setAttribute("myReview", myReview);
        req.setAttribute("myWish", myWish);
        
		//네이버 open api 추가 > 판매 링크에 사용 
		NaverBookDAO ndao = new NaverBookDAO();
		NaverBookDTO ndto = new NaverBookDTO();
		
		ndto = ndao.search(bookSeq);
		req.setAttribute("naverbook", ndto);

		//알라딘 추가 정보
		req.setAttribute("otherinfo", idto);
		
		System.out.println("myReview test" + myReview.toString());
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/book/detail.jsp");
		dispatcher.forward(req, resp);
	}
	
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	        
		 	System.out.println("post 호출");
		 	HttpSession session = req.getSession();
			
			String memberSeq = (String)session.getAttribute("seq");
		 	String type = req.getParameter("type");
	        String bookSeq = req.getParameter("bookSeq");
	        String action = req.getParameter("action");  // 추가된 부분: 'add' 또는 'delete'
	        
	        System.out.println(type);
	        System.out.println(bookSeq);
	        System.out.println(memberSeq);

	        BookDAO dao = BookDAO.getInstance();
	        
	        try {
	            switch (type) {
	            
		            case "add":
		                    dao.addWish(bookSeq, memberSeq);  // Wish 추가
		                    resp.getWriter().write("Wish added");
		                    System.out.println("Wish added");
		                    
		                    break;
		                    
		            case "delete":
		                	List<MyBookWishDTO> myWish = new ArrayList<>();
		                	
		                	MyBookWishDTO dto = new MyBookWishDTO();
		        	        dto.setBookSeq("0");
		        	        dto.setMemberName("0");
		        	        dto.setMemberSeq("0");
		        	        dto.setWishSeq("0");
		        	        myWish.add(dto);
		        	        
		                    req.setAttribute("myWish", myWish);
		                	
		                	dao.deleteWish(bookSeq, memberSeq);  // Wish 삭제
		                    resp.getWriter().write("Wish deleted");
		                    System.out.println("Wish deleted");
		                    System.out.println(myWish.toString());
		                	
		                break;

		            case "rank":
		                int score = Integer.parseInt(req.getParameter("score"));
		                dao.updateRank(bookSeq, memberSeq, score);
		                resp.getWriter().write("Rank updated");
		                System.out.println("rank");
		                break;

	                case "review":
	                    String commend = req.getParameter("commend");
	                    dao.addReview(bookSeq, memberSeq, commend);
	                    resp.getWriter().write("Review added");
	                    System.out.println("review");
	                    break;

	                case "bookmark":
	                    String famousline = req.getParameter("famousline");
	                    dao.addBookmark(bookSeq, memberSeq, famousline);
	                    resp.getWriter().write("Bookmark added");
	                    System.out.println("bookmark");
	                    break;
	                    
	                case "deleteRank":
	                	
	                	List<MyBookRankDTO> myRank = new ArrayList<>();
	                	
	                	MyBookRankDTO rdto = new MyBookRankDTO();
	        	        rdto.setBookSeq("0");
	        	        rdto.setMemberName("0");
	        	        rdto.setMemberSeq("0");
	        	        rdto.setRankDate("0");
	        	        rdto.setRankSeq("0");
	        	        rdto.setScore("0");
	        	        myRank.add(rdto);
	        	        
	                    req.setAttribute("myRank", myRank);
	                	
	                    dao.deleteRank(bookSeq, memberSeq);
	                    resp.getWriter().write("Rank deleted");
	                    break;


	                default:
	                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request type");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred");
	        }
	    }
}