package com.rebook.discussion;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.discussion.model.DiscussionBoardDTO;
import com.rebook.discussion.repository.BoardDAO;

@WebServlet("/discussion/postedit.do")
public class PostEdit extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String postSeq = req.getParameter("postseq");
		
		BoardDAO dao = BoardDAO.getInstance();
		DiscussionBoardDTO post = dao.getPost(postSeq);
		
		req.setAttribute("post", post);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/discussion/postedit.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		// 파라미터에서 값 가져오기
        String seq = req.getParameter("seq");
        String title = req.getParameter("subject");
        String content = req.getParameter("content");

        DiscussionBoardDTO dto = new DiscussionBoardDTO();
        dto.setSeq(seq);
        dto.setTitle(title);
        dto.setContent(content);

        BoardDAO dao = BoardDAO.getInstance();
        int result = dao.updatePost(dto);

        if (result > 0) {
            resp.sendRedirect("/rebook/discussion/postview.do?postseq=" + seq);
        } else {
            resp.getWriter().print("<script>alert('게시물 수정에 실패했습니다.'); history.back();</script>");
        }
	
	}
}