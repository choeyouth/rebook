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

import com.rebook.preference.model.GenreListDTO;
import com.rebook.preference.model.SubGenreDTO;
import com.rebook.preference.model.preferenceDTO;
import com.rebook.preference.repository.GenreList;
import com.rebook.preference.repository.PreferenceDAO;
import com.rebook.preference.repository.SubGenreDAO;

@WebServlet("/preference/booklistedit.do")
public class BookListEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 요청 파라미터의 인코딩 설정
        req.setCharacterEncoding("UTF-8");
        
        String selectedGenreId = req.getParameter("genreId");
        
        // AJAX 요청 처리
        if (selectedGenreId != null && !selectedGenreId.isEmpty()) {
            try {
                int genreId = Integer.parseInt(selectedGenreId);
                SubGenreDAO subgenreDAO = new SubGenreDAO().getInstance();
                List<SubGenreDTO> subgenreList = subgenreDAO.getSubgenre(genreId);
                
                // JSON 응답 설정
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                
                // List를 JSON 배열로 변환
                PrintWriter out = resp.getWriter();
                StringBuilder jsonResponse = new StringBuilder("[");
                for (int i = 0; i < subgenreList.size(); i++) {
                    SubGenreDTO subgenre = subgenreList.get(i);
                    jsonResponse.append("{\"seq\":\"").append(subgenre.getSeq())
                               .append("\",\"subgenre\":\"").append(subgenre.getSubgenre())
                               .append("\"}");
                    if (i < subgenreList.size() - 1) {
                        jsonResponse.append(",");
                    }
                }
                jsonResponse.append("]");
                
                out.print(jsonResponse.toString());
                out.flush();
                return;
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }

        // 일반 페이지 요청 처리
        HttpSession session = req.getSession();
        String member_seq = (String) session.getAttribute("seq");
        
        GenreList genreDAO = new GenreList().getInstance();
        List<GenreListDTO> genrelist = genreDAO.getGenre();
        
        req.setAttribute("genrelist", genrelist);
        req.setAttribute("subgenrelist", new ArrayList<>()); // 초기 빈 리스트
        
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/preference/booklistedit.jsp");
        dispatcher.forward(req, resp);
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            
            // 세션에서 member_seq 가져오기
            HttpSession session = req.getSession();
            String memberSeq = (String)session.getAttribute("seq");
            System.out.println("[BookListEdit] Member SEQ: " + memberSeq);
            
            if (memberSeq == null || memberSeq.trim().isEmpty()) {
                System.out.println("[BookListEdit] Member SEQ is null or empty");
                resp.sendRedirect("/rebook/user/login.do");
                return;
            }
            
            // 폼 데이터 가져오기
            String subgenreSeq = req.getParameter("subgenrelist");
            String targetRead = req.getParameter("targetread");
            System.out.println("[BookListEdit] Form Data - SubgenreSeq: " + subgenreSeq);
            System.out.println("[BookListEdit] Form Data - TargetRead: " + targetRead);
            
            // 유효성 검사
            if (subgenreSeq == null || subgenreSeq.equals("서브장르") || 
                targetRead == null || targetRead.trim().isEmpty()) {
                System.out.println("[BookListEdit] Invalid form data");
                resp.sendRedirect("/rebook/preference/booklistedit.do?error=invalid");
                return;
            }
            
            // DAO를 통한 업데이트 수행
            PreferenceDAO dao = PreferenceDAO.getInstance();
            System.out.println("[BookListEdit] Attempting database update...");
            int result = dao.updatePreference(memberSeq, subgenreSeq, targetRead);
            
            if (result > 0) {
                System.out.println("[BookListEdit] Update successful");
                resp.sendRedirect("/rebook/preference/booklist.do?success=true");
            } else {
                System.out.println("[BookListEdit] Update failed");
                resp.sendRedirect("/rebook/preference/booklistedit.do?error=update");
            }
            
        } catch (Exception e) {
            System.out.println("[BookListEdit] Error in doPost: " + e.getMessage());
            e.printStackTrace();
            resp.sendRedirect("/rebook/preference/booklistedit.do?error=unknown");
        }
    }
}


 