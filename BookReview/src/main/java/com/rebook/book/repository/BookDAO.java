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
import java.sql.SQLException;
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
import com.rebook.book.model.MyBookMarkDTO;
import com.rebook.book.model.MyBookRankDTO;
import com.rebook.book.model.MyBookReviewDTO;
import com.rebook.book.model.MyBookWishDTO;
import com.rebook.book.model.OtherInfoDTO;
import com.rebook.book.model.WishBookDTO;
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
			String queryType = "Keyword";

			String urlString = "https://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=" + apiKey + "&Query="
					+ encodedTitle
					+ "&QueryType="
					+ queryType
					+ "&MaxResults=100&start=1&SearchTarget=Book&output=xml&Version=20131101";

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
						
						OtherInfoDTO idto = new OtherInfoDTO();

						// 책 정보 추출
						String itemId = element.getElementsByTagName("isbn13").item(0).getTextContent();
						String bookTitle = element.getElementsByTagName("title").item(0).getTextContent();
						String author = element.getElementsByTagName("author").item(0).getTextContent();
						String cover = element.getElementsByTagName("cover").item(0).getTextContent();
						String description = element.getElementsByTagName("description").item(0).getTextContent();
						String categoryName = element.getElementsByTagName("categoryName").item(0).getTextContent();

						String priceSales = element.getElementsByTagName("priceSales").item(0).getTextContent();
						String pubDate = element.getElementsByTagName("pubDate").item(0).getTextContent();
						String link = element.getElementsByTagName("link").item(0).getTextContent();
						String publisher = element.getElementsByTagName("publisher").item(0).getTextContent();
						
						idto.setLink(link);
						idto.setPublisher(publisher);
						idto.setPubDate(pubDate);
						
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
	        String sql = "SELECT seq, name, author, cover, story, subGenre_seq FROM tblBook WHERE seq IN (" + placeholders + ")";
	        
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
			
			rs.close();
			pstat.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public BookDTO getBookDetail(String bookSeq) {
		
		try {
			
			String sql = "SELECT b.seq as seq, b.name as name, b.author as author, b.cover as cover, b.story as story, "
						+ "s.subgenre as subgenre, g.genre as genre "
						+ "FROM tblBook b INNER JOIN tblSubGenre s ON b.subGenre_seq = s.seq "
						+ "INNER JOIN tblGenreList g ON g.seq = s.genre_seq WHERE b.seq = ?";
			
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
				dto.setSubgenre(rs.getString("subgenre"));
				dto.setGenre(rs.getString("genre"));
				//getGenre()
			}
			
			rs.close();
			pstat.close();
			
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
			
			rs.close();
			pstat.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

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
			
			rs.close();
			pstat.close();
			
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

			rs.close();
			pstat.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
		
	}

	public OtherInfoDTO getOtherInfo(String bookSeq) {
		

		try {
				
			OtherInfoDTO idto = new OtherInfoDTO();
			
			String apiKey = "ttbdbwjd22ek1603001";
			String encodedTitle = URLEncoder.encode(bookSeq, "UTF-8");
			String queryType = "Keyword";

			String urlString = "https://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=" + apiKey + "&Query="
					+ encodedTitle
					+ "&QueryType="
					+ queryType
					+ "&MaxResults=100&start=1&SearchTarget=Book&output=xml&Version=20131101";

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

						

						String priceSales = element.getElementsByTagName("priceSales").item(0).getTextContent();
						String pubDate = element.getElementsByTagName("pubDate").item(0).getTextContent();
						String link = element.getElementsByTagName("link").item(0).getTextContent();
						String publisher = element.getElementsByTagName("publisher").item(0).getTextContent();
						
						idto.setLink(link);
						idto.setPublisher(publisher);
						idto.setPubDate(pubDate);
					}

				} catch (Exception e) {
					System.err.println("오류 발생: " + e.getMessage());
					e.printStackTrace();
					continue; 
				}
			}
			
			return idto;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	public List<WishBookDTO> getBookWish(String bookSeq) {
		
		ArrayList<WishBookDTO> list = new ArrayList<WishBookDTO>();

		try {
			
			String sql = "select w.seq as seq, w.book_seq as book_seq, w.member_seq as member_seq, i.name as memberName "
					   + "from tblBook b inner join tblWishBook w ON b.seq = w.book_seq "
					   + "inner join tblMember m ON m.seq = w.member_seq "
					   + "inner join tblMemberInfo i on i.member_seq = m.seq "
					   + "where b.seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, bookSeq);
			rs = pstat.executeQuery();
			
			while (rs.next()) {
				
				WishBookDTO dto = new WishBookDTO();
				dto.setSeq(rs.getString("seq"));
				dto.setBook_seq(rs.getString("book_seq"));
				dto.setMember_seq(rs.getString("member_seq"));
				dto.setMemberName(rs.getString("memberName"));
				list.add(dto);
			}

			rs.close();
			pstat.close();
			
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public List<MyBookMarkDTO> getMyBookMark(String bookSeq, String memberSeq) {

		ArrayList<MyBookMarkDTO> list = new ArrayList<MyBookMarkDTO>();
		
		try {
			
			String sql = "select B.seq AS bookSeq, I.seq AS memberSeq, I.name AS memberName, M.seq AS bookmarkSeq, M.regdate AS bookmarkDate, m.famousline AS famousline "
					+ "from tblBookMark M\n"
					+ "inner join tblBook B\n"
					+ "on M.book_seq = B.seq\n"
					+ "inner join tblMemberInfo I\n"
					+ "on M.member_seq = I.seq\n"
					+ "where M.book_seq = ? AND M.member_seq = ? \n"
					+ "order by M.seq desc";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, bookSeq);
			pstat.setString(2, memberSeq);
			rs = pstat.executeQuery();
			
			while (rs.next()) {
				
				MyBookMarkDTO dto = new MyBookMarkDTO();
				dto.setBookSeq(rs.getString("bookSeq"));
				dto.setMemberSeq(rs.getString("memberSeq"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setBookmarkSeq(rs.getString("bookmarkSeq"));
				dto.setBookmarkDate(rs.getString("bookmarkDate"));
				dto.setFamousline(rs.getString("famousline"));
				list.add(dto);
			}

			rs.close();
			pstat.close();
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public List<MyBookRankDTO> getMyBookRank(String bookSeq, String memberSeq) {
		
		ArrayList<MyBookRankDTO> list = new ArrayList<MyBookRankDTO>();
		
		try {
			
			String sql = "SELECT B.seq AS bookSeq, I.seq AS memberSeq, I.name AS memberName, " +
		             "r.seq AS rankSeq, r.score AS score, r.rankDate AS rankDate " +
		             "FROM tblRank r " +
		             "INNER JOIN tblBook B ON r.book_seq = B.seq " +
		             "INNER JOIN tblMemberInfo I ON r.member_seq = I.seq " +
		             "WHERE r.book_seq = ? AND r.member_seq = ? " +
		             "ORDER BY r.seq DESC";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, bookSeq);
			pstat.setString(2, memberSeq);
			rs = pstat.executeQuery();
			
			while (rs.next()) {
				
				MyBookRankDTO dto = new MyBookRankDTO();
				dto.setBookSeq(rs.getString("bookSeq"));
				dto.setMemberSeq(rs.getString("memberSeq"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setRankSeq(rs.getString("rankSeq"));
				dto.setRankDate(rs.getString("rankDate"));
				dto.setScore(rs.getString("score"));
				list.add(dto);
			}

			rs.close();
			pstat.close();
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public List<MyBookReviewDTO> getMyBookReview(String bookSeq, String memberSeq) {
		
		ArrayList<MyBookReviewDTO> list = new ArrayList<MyBookReviewDTO>();
		
		try {
			
			String sql = "SELECT B.seq AS bookSeq, M.seq AS memberSeq, M.name AS memberName, " +
		             "R.seq AS reviewSeq, R.commend AS commend, R.review_date AS reviewDate " +
		             "FROM tblBookReview R " +
		             "INNER JOIN tblBook B ON R.book_seq = B.seq " +
		             "INNER JOIN tblMemberInfo M ON R.member_seq = M.member_seq " +
		             "WHERE R.book_seq = ? AND R.member_seq = ? " +
		             "ORDER BY R.seq DESC";

			pstat = conn.prepareStatement(sql);
			pstat.setString(1, bookSeq);
			pstat.setString(2, memberSeq);
			rs = pstat.executeQuery();
			//v.seq AS reviewSeq, v.commend, v.review_date AS reviewDate,
			// reviewSeq, v.commend, v.review_date AS reviewDate,
			while (rs.next()) {
				
				MyBookReviewDTO dto = new MyBookReviewDTO();
				dto.setBookSeq(rs.getString("bookSeq"));
				dto.setMemberSeq(rs.getString("memberSeq"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setReviewSeq(rs.getString("reviewSeq"));
				dto.setReviewDate(rs.getString("reviewDate"));
				dto.setCommend(rs.getString("commend"));
				list.add(dto);
			}

			rs.close();
			pstat.close();
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public List<MyBookWishDTO> getMyBookWish(String bookSeq, String memberSeq) {
		
		ArrayList<MyBookWishDTO> list = new ArrayList<MyBookWishDTO>();
		
		try {
			
			String sql = "SELECT B.seq AS bookSeq, I.seq AS memberSeq, I.name AS memberName, " +
		             "w.seq AS wishSeq " +
		             "FROM tblWishBook w " +
		             "INNER JOIN tblBook B ON w.book_seq = B.seq " +
		             "INNER JOIN tblMemberInfo I ON w.member_seq = I.seq " +
		             "WHERE w.book_seq = ? AND w.member_seq = ? " +
		             "ORDER BY w.seq DESC";

			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, bookSeq);
			pstat.setString(2, memberSeq);
			rs = pstat.executeQuery();
			
			while (rs.next()) {
				
				MyBookWishDTO dto = new MyBookWishDTO();
				dto.setBookSeq(rs.getString("bookSeq"));
				dto.setMemberSeq(rs.getString("memberSeq"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setWishSeq(rs.getString("wishSeq"));
				list.add(dto);
			}

			rs.close();
			pstat.close();
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}


	public void updateRank(String bookSeq, String memberSeq, int score) throws SQLException {
	    String sql = "MERGE INTO tblRank USING DUAL ON (book_seq = ? AND member_seq = ?) " +
	                 "WHEN MATCHED THEN UPDATE SET score = ?, rankDate = SYSDATE " +
	                 "WHEN NOT MATCHED THEN " +
	                 "INSERT (seq, book_seq, member_seq, score, rankDate) " +
	                 "VALUES (rank_seq.NEXTVAL, ?, ?, ?, SYSDATE)";
	    try (PreparedStatement pstat = conn.prepareStatement(sql)) {
	        pstat.setString(1, bookSeq);
	        pstat.setString(2, memberSeq);
	        pstat.setInt(3, score);
	        pstat.setString(4, bookSeq);
	        pstat.setString(5, memberSeq);
	        pstat.setInt(6, score);
	        pstat.executeUpdate();
	    }
	}


	public void addReview(String bookSeq, String memberSeq, String commend) throws SQLException {
	    String sql = "INSERT INTO tblBookReview (seq, book_seq, member_seq, commend, review_date) " +
	                 "VALUES (bookReview_seq.NEXTVAL, ?, ?, ?, SYSDATE)";
	    try (PreparedStatement pstat = conn.prepareStatement(sql)) {
	        pstat.setString(1, bookSeq);
	        pstat.setString(2, memberSeq);
	        pstat.setString(3, commend);
	        pstat.executeUpdate();
	    }
	}


	public void addBookmark(String bookSeq, String memberSeq, String famousline) throws SQLException {
	    String sql = "INSERT INTO tblBookMark (seq, book_seq, member_seq, famousline, regDate) " +
	                 "VALUES (bookmark_seq.NEXTVAL, ?, ?, ?, SYSDATE)";
	    try (PreparedStatement pstat = conn.prepareStatement(sql)) {
	        pstat.setString(1, bookSeq);
	        pstat.setString(2, memberSeq);
	        pstat.setString(3, famousline);
	        pstat.executeUpdate();
	    }
	}

	public void addWish(String bookSeq, String memberSeq) throws SQLException {
	    String sql = "INSERT INTO tblWishBook (seq, book_seq, member_seq) VALUES (wishBook_seq.NEXTVAL, ?, ?)";
	    try (PreparedStatement pstat = conn.prepareStatement(sql)) {
	        pstat.setString(1, bookSeq);
	        pstat.setString(2, memberSeq);
	        pstat.executeUpdate();
	    }
	}

	public List<MyBookWishDTO> deleteWish(String bookSeq, String memberSeq) throws SQLException {
		
		ArrayList<MyBookWishDTO> list = new ArrayList<MyBookWishDTO>();
		
	    String sql = "DELETE FROM tblWishBook WHERE book_seq = ? AND member_seq = ?";
	    try (PreparedStatement pstat = conn.prepareStatement(sql)) {
	        pstat.setString(1, bookSeq);
	        pstat.setString(2, memberSeq);
	        pstat.executeUpdate();
	        
	        // MyBookWishDTO 초기화 
	        MyBookWishDTO dto = new MyBookWishDTO();
	        dto.setBookSeq("0");
	        dto.setMemberName("0");
	        dto.setMemberSeq("0");
	        dto.setWishSeq("0");
	        list.add(dto);
	       
	        return list;

	    }
		
	}

	public void deleteRank(String bookSeq, String memberSeq) throws SQLException {
	    String sql = "DELETE FROM tblRank WHERE book_seq = ? AND member_seq = ?";
	    try (PreparedStatement pstat = conn.prepareStatement(sql)) {
	        pstat.setString(1, bookSeq);
	        pstat.setString(2, memberSeq);
	        pstat.executeUpdate();
	    }
	}




}
