package com.rebook.preference;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.rebook.preference.model.preferenceDTO;
import com.rebook.preference.repository.PreferenceDAO;
import com.rebook.preference.repository.SubGenreDAO;
import com.rebook.user.model.MemberDTO;

@WebServlet("/preference/booklist.do")
public class BookList extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // GET 요청도 POST와 동일한 로직 수행
        processRequest(req, resp);
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
    
    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        
        HttpSession session = req.getSession();
        String seq = (String) session.getAttribute("seq");
        
        if (seq == null) {
            // 세션이 없는 경우 처리
            PrintWriter writer = resp.getWriter();
            writer.print("<html><head><meta charset='UTF-8'></head><body><script>");
            writer.printf("alert('%s');", "로그인이 필요합니다.");
            writer.print("location.href='/rebook/user/login.do';"); // 로그인 페이지로 리다이렉트
            writer.print("</script></body></html>");
            writer.close();
            return;
        }

        PreferenceDAO prefdao = PreferenceDAO.getInstance();
        preferenceDTO rs1 = prefdao.getSeq(seq);
        System.out.println("PreferenceDTO: " + rs1); // 디버깅용
        
        if (rs1 == null) {
            // 선호도 정보가 없는 경우 처리
            PrintWriter writer = resp.getWriter();
            writer.print("<html><head><meta charset='UTF-8'></head><body><script>");
            writer.printf("alert('%s');", "선호도 정보가 없습니다.");
            writer.print("history.back();");
            writer.print("</script></body></html>");
            writer.close();
            return;
        }

        String member_seq = rs1.getSeq();
        String subGenre_seq = rs1.getSubgenre_seq();
        String targetread = rs1.getTargetread();
        
        List<BookDTO> bookList = null;
        
        if (!member_seq.equals(seq)) {
            PrintWriter writer = resp.getWriter();
            writer.print("<html><head><meta charset='UTF-8'></head><body><script>");
            writer.printf("alert('%s');", "추천받은 기록이 없습니다.");
            writer.print("history.back();");
            writer.print("</script></body></html>");
            writer.close();
        } else {
            int count = prefdao.count(subGenre_seq);
            
            System.out.println("Count: " + count); // 디버깅용
            
            if (count > Integer.parseInt(targetread)) {
                bookList = prefdao.getBooks(subGenre_seq, targetread);
            } else {
                bookList = prefdao.getBooks(subGenre_seq, String.valueOf(count));
                PrintWriter writer = resp.getWriter();
                writer.print("<html><head><meta charset='UTF-8'></head><body><script>");
                writer.printf("alert('%s');", "추천할 책이 부족합니다. \r\n조속히 추가하도록하겠습니다.");
                writer.print("</script></body></html>");
                writer.close();
            }
            
            System.out.println("BookList size: " + (bookList != null ? bookList.size() : "null")); // 디버깅용
            System.out.println("BookList size: " + (bookList)); // 디버깅용
        }

        req.setAttribute("bookList", bookList);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/preference/booklist.jsp");
        dispatcher.forward(req, resp);
    }
}
