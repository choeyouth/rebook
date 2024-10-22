package com.rebook.mybook;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rebook.mybook.model.MarkDTO;
import com.rebook.mybook.repository.MarkDAO;

@WebServlet("/mybook/markedit.do")
public class MarkEdit extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookmarkseq = req.getParameter("bookmarkseq");

        MarkDAO dao = MarkDAO.getInstance();
        ArrayList<MarkDTO> markList = dao.listMark(bookmarkseq);

        if (!markList.isEmpty()) {
            MarkDTO mark = markList.get(0);
            req.setAttribute("mark", mark);

            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/mybook/markedit.jsp");
            dispatcher.forward(req, resp);
        } else {
            resp.getWriter().write("<script>alert('aadnlfgadnlf'); history.back();</script>");
            System.out.println("실패1");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String famousline = req.getParameter("famousline");
        String bookmarkseq = req.getParameter("bookmarkseq");

        MarkDTO dto = new MarkDTO();
        dto.setBookmarkseq(bookmarkseq);
        dto.setFamousline(famousline);

        MarkDAO dao = MarkDAO.getInstance();
        int result = dao.edit(dto);

        if (result > 0) {
            resp.sendRedirect("/rebook/mybook/list.do?seq=" + req.getParameter("seq"));
        } else {
            resp.getWriter().write("<script>alert('abcd'); history.back();</script>");
            System.out.println("실패");
        }
    }
}