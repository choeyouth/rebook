package com.rebook.discussion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.rebook.discussion.repository.BoardDAO;

@WebServlet("/discussion/replydel.do")
public class ReplyDel extends HttpServlet {

	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // JSON 데이터 읽기
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String jsonData = sb.toString();
        System.out.println("Received JSON: " + jsonData);

        // JSON 파싱
        JSONParser parser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) parser.parse(jsonData);
        } catch (ParseException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // 댓글 seq 가져오기
        Long seqLong = (Long) jsonObject.get("seq");
        String seq = String.valueOf(seqLong);

        // DAO를 이용해 댓글 삭제
        BoardDAO dao = BoardDAO.getInstance();
        int result = dao.deleteComment(seq);

        // JSON 응답 설정
        resp.setContentType("application/json");
        JSONObject responseObj = new JSONObject();
        responseObj.put("result", result);

        PrintWriter writer = resp.getWriter();
        writer.print(responseObj.toString());
        writer.close();
    }
}