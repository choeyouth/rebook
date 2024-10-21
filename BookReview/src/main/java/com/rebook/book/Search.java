package com.rebook.book;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.test.util.DBUtil;

@WebServlet("/book/search.do")
public class Search extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/book/search.jsp");
		dispatcher.forward(req, resp);
		
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	
        resp.setContentType("text/html; charset=UTF-8");

        String apiKey = "ttbdbwjd22ek1603001";
        String title = req.getParameter("title");
        String encodedTitle = URLEncoder.encode(title, "UTF-8");

        String urlString = "https://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=" 
                + apiKey + "&Query=" + encodedTitle 
                + "&QueryType=Title&MaxResults=1&start=1&SearchTarget=Book&output=xml&Version=20131101";

        try (Connection conn = DBUtil.open("43.203.106.58:1521:xe", "rebook", "java1234")) {

            URL apiUrl = new URL(urlString);
            HttpURLConnection httpConn = (HttpURLConnection) apiUrl.openConnection();
            httpConn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String inputLine;
            StringBuilder respBuilder = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                respBuilder.append(inputLine);
            }
            
            in.close();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(respBuilder.toString().getBytes("UTF-8")));
            Element root = document.getDocumentElement();
            NodeList items = root.getElementsByTagName("item");

            String message = "검색된 책이 없습니다.";
            if (items.getLength() > 0) {
                Element item = (Element) items.item(0);

                String itemId = item.getElementsByTagName("isbn13").item(0).getTextContent();
                String bookTitle = item.getElementsByTagName("title").item(0).getTextContent();
                String author = item.getElementsByTagName("author").item(0).getTextContent();
                String pubDate = item.getElementsByTagName("pubDate").item(0).getTextContent();
                String cover = item.getElementsByTagName("cover").item(0).getTextContent();
                String description = item.getElementsByTagName("description").item(0).getTextContent();

                // 데이터베이스에 저장
                String sql = "INSERT INTO tblBook (seq, name, author, publicationDate, story, cover) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, itemId);
                pstmt.setString(2, bookTitle);
                pstmt.setString(3, author);
                pstmt.setDate(4, java.sql.Date.valueOf(pubDate));
                pstmt.setString(5, description);
                pstmt.setString(6, cover);
                pstmt.executeUpdate();

                message = "책이 성공적으로 저장되었습니다: " + bookTitle;
            }
            
            System.out.println(message);
            req.setAttribute("message", message);
            req.getRequestDispatcher("/book/search.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("<p>오류가 발생했습니다: " + e.getMessage() + "</p>");
        }
    }
}
