package com.rebook.discussion;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.book.model.BookDTO;
import com.rebook.book.repository.BookDAO;
import com.rebook.discussion.model.DiscussionBoardDTO;
import com.rebook.discussion.repository.BoardDAO;

@WebServlet("/discussion/postadd.do")
public class PostAdd extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	String bookSeq = req.getParameter("bookseq");  
        String bookTitle = req.getParameter("booktitle");  

        req.setAttribute("bookSeq", bookSeq);
        req.setAttribute("bookTitle", bookTitle);
        
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/discussion/postadd.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

    	String bookSeq = req.getParameter("bookSeq");  
        String bookTitle = req.getParameter("bookTitle");  
        String memberSeq = req.getParameter("memberSeq");
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        System.out.println(bookSeq);
        System.out.println(bookTitle);
        // DTO에 데이터 설정
        DiscussionBoardDTO post = new DiscussionBoardDTO();
        post.setMember_seq(memberSeq);
        post.setTitle(title);
        post.setContent(content);
        post.setBookTitle(bookTitle);
        post.setBook_seq(bookSeq);

        // DAO를 통해 게시글 저장
        BoardDAO dao = BoardDAO.getInstance();
        boolean result = dao.addPost(post);

        if (result) {
            // 성공 시 게시판 목록으로 리디렉션
            resp.sendRedirect("/rebook/discussion/boardlist.do");
        } else {
            // 실패 시 오류 메시지 출력
            resp.getWriter().print("<script>alert('글 작성에 실패했습니다.'); history.back();</script>");
        }
    }
}