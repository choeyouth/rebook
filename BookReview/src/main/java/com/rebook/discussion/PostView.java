package com.rebook.discussion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.discussion.model.DiscussionBoardDTO;
import com.rebook.discussion.model.DiscussionReplyDTO;
import com.rebook.discussion.repository.BoardDAO;

@WebServlet("/discussion/postview.do")
public class PostView extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String postSeq = req.getParameter("postseq");
		
		BoardDAO dao = BoardDAO.getInstance();
		DiscussionBoardDTO post = dao.getPost(postSeq);
		List<DiscussionReplyDTO> replies = new ArrayList<>();
		replies = dao.getReply(postSeq);

	    // 서버에서 댓글 시퀀스 출력 (디버깅용)
	    for (DiscussionReplyDTO reply : replies) {
	        System.out.println("Reply Seq: " + reply.getSeq());
	    }

		req.setAttribute("post", post);
		req.setAttribute("reply", replies);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/discussion/postview.jsp");
		dispatcher.forward(req, resp);
	}
}