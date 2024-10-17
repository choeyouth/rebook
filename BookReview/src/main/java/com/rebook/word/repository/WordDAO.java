package com.rebook.word.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.rebook.word.model.WordDTO;
import com.test.util.DBUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WordDAO {
    private static WordDAO dao;
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    private WordDAO() {
        this.conn = DBUtil.open("localhost", "book", "java1234");
    }

    public static WordDAO getInstance() {
        if (dao == null) {
            dao = new WordDAO();
        }
        return dao;
    }

    private static String getValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0 && nodeList.item(0).getFirstChild() != null) {
            return nodeList.item(0).getFirstChild().getNodeValue();
        }
        return null;
    }

    public ArrayList<WordDTO> listWordBySearch(String word) {
        ArrayList<WordDTO> list = new ArrayList<>();

        try {
            String query = "SELECT * FROM tblWordList WHERE word LIKE ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + word + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                WordDTO dto = new WordDTO();
                dto.setTarget_code(rs.getString("target_code"));
                dto.setWord(rs.getString("word"));
                dto.setPos(rs.getString("pos"));
                dto.setDefinition(rs.getString("definition"));
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 목록이 비어 있을 때만 searchWord 메서드를 한 번 호출
        if (list.isEmpty()) {
            searchWord(word);
            // 데이터를 가져온 후 다시 쿼리를 실행하여 새 목록을 얻습니다.
            try {
                String query = "SELECT * FROM tblWordList WHERE word LIKE ?";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, "%" + word + "%");
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    WordDTO dto = new WordDTO();
                    dto.setTarget_code(rs.getString("target_code"));
                    dto.setWord(rs.getString("word"));
                    dto.setPos(rs.getString("pos"));
                    dto.setDefinition(rs.getString("definition"));
                    list.add(dto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (pstmt != null) pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }


    public void searchWord(String word) {
        if (word == null || word.trim().isEmpty()) {

            return;
        }
        try {
            String key = "760C3909BB89C07D50BD72F9FEE11727";
            String encodedWord = URLEncoder.encode(word, "UTF-8");
            String urlString = "https://stdict.korean.go.kr/api/search.do?key=" + key + "&type_search=search&q=" + encodedWord;
            URL apiUrl = new URL(urlString);
            HttpURLConnection httpConn = (HttpURLConnection) apiUrl.openConnection();
            httpConn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(response.toString().getBytes("UTF-8"));
            Document document = builder.parse(input);

            NodeList items = document.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) item;

                    String targetCode = getValue("target_code", element);
                    String wordValue = getValue("word", element);
                    String pos = getValue("pos", element);
                    String definition = getValue("definition", element);

                    String insertSql = "INSERT INTO tblWordList (target_code, word, pos, definition) VALUES (?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(insertSql);
                    pstmt.setString(1, targetCode);
                    pstmt.setString(2, wordValue);
                    pstmt.setString(3, pos);
                    pstmt.setString(4, definition);
                    pstmt.executeUpdate();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}