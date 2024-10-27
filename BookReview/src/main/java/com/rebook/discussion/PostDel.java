package com.rebook.discussion;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.discussion.repository.BoardDAO;

@WebServlet("/discussion/postdel.do")
public class PostDel extends HttpServlet {

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String postSeq = req.getParameter("seq");

        // DAO를 통해 게시글 및 댓글 삭제 수행
        BoardDAO dao = BoardDAO.getInstance();
        boolean isDeleted = dao.deletePost(postSeq);

        if (isDeleted) {
            resp.sendRedirect("/rebook/discussion/boardlist.do");
        } else {
            resp.getWriter().print("<script>alert('게시물 삭제에 실패했습니다.'); history.back();</script>");
        }
    }
}