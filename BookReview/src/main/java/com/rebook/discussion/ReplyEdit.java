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

import com.rebook.discussion.model.DiscussionReplyDTO;
import com.rebook.discussion.repository.BoardDAO;

@WebServlet("/discussion/replyedit.do")
public class ReplyEdit extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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

	    // seq를 Long 타입으로 받고 String으로 변환
	    Long seqLong = (Long) jsonObject.get("seq");
	    String seq = String.valueOf(seqLong); // Long -> String 변환

	    String reply = (String) jsonObject.get("reply");

	    System.out.println("Reply: " + reply);
	    System.out.println("Seq: " + seq);

	    BoardDAO dao = BoardDAO.getInstance();
	    DiscussionReplyDTO dto = new DiscussionReplyDTO();
	    dto.setReply(reply);
	    dto.setSeq(seq);

	    int result = dao.editComment(dto);
	    System.out.println("DB Update Result: " + result);

	    resp.setContentType("application/json");

	    // 결과를 JSON으로 응답
	    JSONObject responseObj = new JSONObject();
	    responseObj.put("result", result);

	    PrintWriter writer = resp.getWriter();
	    writer.print(responseObj.toString());
	    writer.close();
	}

}