package com.rebook.discussion;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.discussion.model.DiscussionBoardDTO;
import com.rebook.discussion.repository.BoardDAO;

@WebServlet("/discussion/boardlist.do")
public class BoardList extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		BoardDAO dao = BoardDAO.getInstance();
		
		ArrayList<DiscussionBoardDTO> list = dao.list();

		
		req.setAttribute("list", list);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/discussion/boardlist.jsp");
		dispatcher.forward(req, resp);
	}
}