package com.rebook.discussion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.rebook.discussion.model.DiscussionReplyDTO;
import com.rebook.discussion.repository.BoardDAO;

@WebServlet("/discussion/replyadd.do")
public class ReplyAdd extends HttpServlet {
   @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	   	try {
	   
	   	req.setCharacterEncoding("UTF-8");
	   	resp.setCharacterEncoding("UTF-8");
	   
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

        String postSeq = String.valueOf(jsonObject.get("postSeq"));
        String memberSeq = String.valueOf(jsonObject.get("memberSeq"));
        String content = (String) jsonObject.get("content");
        
        
        // 댓글 추가
        BoardDAO dao = BoardDAO.getInstance();
        int newSeq = dao.addComment(postSeq, memberSeq, content);  // 새 댓글 번호 반환
        
		List<DiscussionReplyDTO> replies = new ArrayList<>();
		replies = dao.getReply(postSeq);
        
        
        System.out.println(newSeq);
        // JSON 응답 설정
        resp.setContentType("application/json");
        JSONObject responseObj = new JSONObject();
        responseObj.put("seq", newSeq);
        responseObj.put("memberSeq", memberSeq);
        responseObj.put("content", content);

        PrintWriter writer = resp.getWriter();
        writer.print(responseObj.toString());
        writer.close();
        
	   	} catch (Exception e) {
			// TODO: handle exception
	   		e.printStackTrace();
		}
    }
}