package com.rebook.book.repository;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.rebook.book.model.BookDTO;
import com.rebook.mybook.model.MarkDTO;
import com.rebook.mybook.model.RankDTO;
import com.rebook.mybook.model.ReviewDTO;
import com.test.util.DBUtil;

public class BookDAO {
	
	public static BookDAO dao;
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	private BookDAO() {
    	this.conn = DBUtil.open("43.203.106.58:1521:xe", "rebook", "java1234");
	} 
	
	public static BookDAO getInstance() {
		
		
		if (dao == null) {
			dao = new BookDAO();
		}
		
		return dao;
		
	}
	

	public List<String> bookAdd(String title) {

		List<String> bookseqs = new ArrayList<String>();

		try {
			String apiKey = "ttbdbwjd22ek1603001";
			String encodedTitle = URLEncoder.encode(title, "UTF-8");

			String urlString = "https://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=" + apiKey + "&Query="
					+ encodedTitle
					+ "&QueryType=Title&MaxResults=100&start=1&SearchTarget=Book&output=xml&Version=20131101";

			URL apiUrl = new URL(urlString);
			HttpURLConnection httpConn = (HttpURLConnection) apiUrl.openConnection();
			httpConn.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
			StringBuffer response = new StringBuffer();
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

				try {

					Node item = items.item(i);

					if (item.getNodeType() == Node.ELEMENT_NODE) {
						Element element = (Element) item;

						// 책 정보 추출
						String itemId = element.getElementsByTagName("isbn13").item(0).getTextContent();
						String bookTitle = element.getElementsByTagName("title").item(0).getTextContent();
						String author = element.getElementsByTagName("author").item(0).getTextContent();
						String priceSales = element.getElementsByTagName("priceSales").item(0).getTextContent();
						String pubDate = element.getElementsByTagName("pubDate").item(0).getTextContent();
						String cover = element.getElementsByTagName("cover").item(0).getTextContent();
						String description = element.getElementsByTagName("description").item(0).getTextContent();
						String categoryName = element.getElementsByTagName("categoryName").item(0).getTextContent();

						String[] categoryParts = categoryName.split(">");
						String genreName = categoryParts.length > 1 ? categoryParts[1].trim() : "기타";
						String subGenreName = categoryParts.length > 2 ? categoryParts[2].trim() : "기타";

						// 장르 검색
						Integer genreSeq = getGenreSeq(genreName);
						Integer subGenreSeq = getSubGenreSeq(genreSeq, subGenreName);

						if (itemId == null || itemId.isEmpty() || bookTitle == null || bookTitle.isEmpty()) {
							System.out.println("잘못된 데이터입니다: " + bookTitle);
							continue;
						}

						if (description == null || description.isEmpty()) {
							description = "준비 중입니다.";
						}

						if (!isBookExists(itemId)) { // 중복 확인
							insertBook(itemId, subGenreSeq, bookTitle, author, pubDate, description, cover);
							System.out.println("데이터 저장 완료: " + bookTitle);
						} else {
							System.out.println("이미 존재하는 책: " + bookTitle);
						}

						// ISBN 저장
						bookseqs.add(itemId);
					}

				} catch (Exception e) {
					System.err.println("오류 발생: " + e.getMessage());
					e.printStackTrace();
					continue; 
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookseqs;
	}

	// 장르 검색 메서드
	private Integer getGenreSeq(String genreName) throws Exception {
	    String sql = "SELECT seq FROM tblGenreList WHERE genre LIKE ?";
	    PreparedStatement pstat = conn.prepareStatement(sql);
	    pstat.setString(1, "%" + genreName + "%");
	    ResultSet rs = pstat.executeQuery();

	    Integer genreSeq = rs.next() ? rs.getInt("seq") : 64; // 기타 장르: 64
	    rs.close();
	    pstat.close();
	    return genreSeq;
	}

	// 서브 장르 검색 메서드
	private Integer getSubGenreSeq(Integer genreSeq, String subGenreName) throws Exception {
	    String sql = "SELECT seq FROM tblSubGenre WHERE genre_seq = ? AND subGenre LIKE ?";
	    PreparedStatement pstat = conn.prepareStatement(sql);
	    pstat.setInt(1, genreSeq);
	    pstat.setString(2, "%" + subGenreName + "%");
	    ResultSet rs = pstat.executeQuery();

	    Integer subGenreSeq = rs.next() ? rs.getInt("seq") : 9863; // 기타 서브 장르: 9863
	    rs.close();
	    pstat.close();
	    return subGenreSeq;
	}

	// 중복 확인 메서드
	private boolean isBookExists(String itemId) throws Exception {
	    String sql = "SELECT COUNT(*) FROM tblBook WHERE seq = ?";
	    PreparedStatement pstat = conn.prepareStatement(sql);
	    pstat.setString(1, itemId);
	    ResultSet rs = pstat.executeQuery();

	    rs.next();
	    boolean exists = rs.getInt(1) > 0;
	    rs.close();
	    pstat.close();
	    return exists;
	}

	// 책 삽입 메서드
	private void insertBook(String itemId, Integer subGenreSeq, String bookTitle, String author,
	                        String pubDate, String description, String cover) throws Exception {
	    String sql = "INSERT INTO tblBook (seq, subGenre_seq, name, author, publicationDate, story, cover) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?)";
	    PreparedStatement pstat = conn.prepareStatement(sql);
	    pstat.setString(1, itemId);
	    pstat.setInt(2, subGenreSeq);
	    pstat.setString(3, bookTitle);
	    pstat.setString(4, author);
	    pstat.setDate(5, java.sql.Date.valueOf(pubDate));
	    pstat.setString(6, description);
	    pstat.setString(7, cover);

	    pstat.executeUpdate();
	    pstat.close();

	}

	public List<BookDTO> bookList(List<String> bookseqs) {
		
		List<BookDTO> list = new ArrayList<>();
		
	    if (bookseqs == null || bookseqs.isEmpty()) {
	        return null; 
	    }
		
		try {
			
	        String placeholders = String.join(",", Collections.nCopies(bookseqs.size(), "?"));
	        String sql = "SELECT seq, name, author, cover, story FROM tblBook WHERE seq IN (" + placeholders + ")";
	        
	        pstat = conn.prepareStatement(sql);
	        
			for (int i = 0; i < bookseqs.size(); i++) {
				pstat.setString(i + 1, bookseqs.get(i));
			}

			rs = pstat.executeQuery();

			while (rs.next()) {
				BookDTO dto = new BookDTO();
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setAuthor(rs.getString("author"));
				dto.setCover(rs.getString("cover"));
				dto.setStory(rs.getString("story"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public BookDTO getBookDetail(String bookSeq) {
		
		try {
			
			String sql = "select * from tblBook where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, bookSeq);
			
			rs = pstat.executeQuery();
			
			BookDTO dto = new BookDTO();
			
			if (rs.next()) {
				dto.setSeq(rs.getString("seq"));
				dto.setName(rs.getString("name"));
				dto.setAuthor(rs.getString("author"));
				dto.setStory(rs.getString("story"));
				dto.setCover(rs.getString("cover"));
				dto.setSubgenre_seq(rs.getString("subgenre_seq"));
				//getGenre()
			}
			
			return dto;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public List<MarkDTO> getBookMark(String bookSeq) {
		
		ArrayList<MarkDTO> list = new ArrayList<MarkDTO>();
		
		try {
			String sql = "select M.seq as bookmarkseq,M.famousline as famousline,M.member_seq as memberseq,M.regdate as regdate, B.name as bookname, B.author as author,\n"
					+ "B.cover as cover, I.name as membername\n"
					+ "from tblBookMark M\n"
					+ "inner join tblBook B\n"
					+ "on M.book_seq = B.seq\n"
					+ "inner join tblMemberInfo I\n"
					+ "on M.member_seq = I.seq\n"
					+ "where M.book_seq = ? \n"
					+ "order by M.seq desc";
			
			pstat = conn.prepareStatement(sql);
	        pstat.setString(1, bookSeq);
	        rs = pstat.executeQuery();
			
			while (rs.next()) {
				MarkDTO dto = new MarkDTO();
				dto.setBookmarkseq(rs.getString("bookmarkseq"));
				dto.setFamousline(rs.getString("famousline"));
				dto.setMemberseq(rs.getString("memberseq"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setBookname(rs.getString("bookname"));
				dto.setAuthor(rs.getString("author"));
				dto.setCover(rs.getString("cover"));
				dto.setMembername(rs.getString("membername"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
		
//		try {
//			
//			String sql = "select * from tblBookMark where book_seq = ?";
//			
//			pstat = conn.prepareStatement(sql);
//			pstat.setString(1, bookSeq);
//			
//			List<MarkDTO> list = new ArrayList<>();
//			
//			rs = pstat.executeQuery();
//
//			while (rs.next()) {
//				
//				System.out.println(rs.getString("famousline"));
//				System.out.println(rs.getString("member_seq"));
//				System.out.println(rs.getString("regdate"));
//				
//				MarkDTO dto = new MarkDTO();
//				dto.setFamousline(rs.getString("famousline"));
//				dto.setMemberseq(rs.getString("member_seq"));
//				dto.setRegdate(rs.getString("regdate"));
//				list.add(dto);
//			}
//			
//			return list;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}

	public List<RankDTO> getBookRank(String bookSeq) {
		
		try {
			
			String sql = "select * from tblRank where book_seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, bookSeq);

			List<RankDTO> list = new ArrayList<>();
			
			rs = pstat.executeQuery();
			
			while (rs.next()) {
				RankDTO dto = new RankDTO();
				dto.setRankseq(rs.getString("seq"));
				dto.setScore(rs.getString("score"));
				dto.setMemberseq(rs.getString("member_seq"));
				dto.setRankdate(rs.getString("rankdate"));
				list.add(dto);
			}
			
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public List<ReviewDTO> getBookReview(String bookSeq) {
		ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();
		
		try {
			String sql = "select R.seq as bookreviewseq, R.commend as commend, R.member_seq as memberseq, R.review_date as reviewdate, B.name as bookname,B.author as author,\r\n"
					+ "B.cover as cover,M.name as membername from tblBookReview R\r\n"
					+ "inner join tblBook B\r\n"
					+ "on R.Book_seq = B.seq\r\n"
					+ "inner join tblMemberInfo M\r\n"
					+ "on R.member_seq = M.seq\r\n"
					+ "where R.Book_seq = ?\r\n"
					+ "order by R.seq desc";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, bookSeq);
			rs = pstat.executeQuery();
			
			while (rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				dto.setBookreviewseq(rs.getString("bookreviewseq"));
				dto.setCommend(rs.getString("commend"));
				dto.setMemberseq(rs.getString("memberseq"));
				dto.setReviewdate(rs.getString("reviewdate"));
				dto.setBookname(rs.getString("bookname"));
				dto.setAuthor(rs.getString("author"));
				dto.setCover(rs.getString("cover"));
				dto.setMembername(rs.getString("membername"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
//		try {
//			
//			String sql = "select * from tblBookReview where book_seq = ?";
//			
//			pstat = conn.prepareStatement(sql);
//			pstat.setString(1, bookSeq);
//
//			List<ReviewDTO> list = new ArrayList<>();
//			
//			rs = pstat.executeQuery();
//			
//			
//			while (rs.next()) {
//				ReviewDTO dto = new ReviewDTO();
//				
//				System.out.println(rs.getString("seq"));
//				System.out.println(rs.getString("commend"));
//				System.out.println(rs.getString("member_seq"));
//				System.out.println(rs.getString("bookdate"));
//				
//				dto.setBookreviewseq(rs.getString("seq"));
//				dto.setCommend(rs.getString("commend"));
//				dto.setMemberseq(rs.getString("member_seq"));
//				dto.setReviewdate(rs.getString("bookdate"));
//				list.add(dto);
//			}
//			
//			return list;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return null;
	}
}
