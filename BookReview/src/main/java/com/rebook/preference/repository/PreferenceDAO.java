package com.rebook.preference.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.rebook.book.model.BookDTO;
import com.rebook.preference.model.preferenceDTO;
import com.rebook.user.model.MemberDTO;
import com.rebook.user.model.MemberInfoDTO;
import com.rebook.user.repository.MemberDAO;
import com.test.util.DBUtil;

public class PreferenceDAO {
	public static PreferenceDAO dao;
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	public PreferenceDAO() {
    	this.conn = DBUtil.open("43.203.106.58:1521:xe", "rebook", "java1234");
	} 
	
	public static PreferenceDAO getInstance() {
		
		
		if (dao == null) {
			dao = new PreferenceDAO();
		}
		
		return dao;
		
	}

	public preferenceDTO getSeq(String seq) {
		try {
					
					String sql = "SELECT * FROM tblPreference WHERE member_seq=?";
					
					pstat = conn.prepareStatement(sql);
					pstat.setString(1, seq);
					
					rs = pstat.executeQuery();
					
					while (rs.next()) {
						
						preferenceDTO prefDto = new preferenceDTO();
						
						prefDto.setSeq(rs.getString("seq"));
						prefDto.setMember_seq(rs.getString("member_seq"));
						prefDto.setSubgenre_seq(rs.getString("subgenre_seq"));
						prefDto.setTargetread(rs.getString("targetread"));
		
						return prefDto;
						
					}

					rs.close();
					pstat.close();
					
			}catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}

//	public String count(String subgenre_seq) {
//		try {
//			
//			String sql = "SELECT count(*) FROM tblBook book INNER join tblsubgenre sub ON book.subgenre_seq = sub.seq where sub.seq= ?";
//			pstat = conn.prepareStatement(sql);
//			pstat.setString(1, subgenre_seq);
//			
//			rs = pstat.executeQuery();
//			String count = null;
//			
//	        if (rs.next()) {
//	            count = rs.getString(1); // ResultSet에서 카운트를 가져옴
//	        }
//
//			rs.close();
//			pstat.close();
//			
//			return count;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public Integer count(String subgenre_seq) {
	    String sql = "SELECT COUNT(*) FROM tblBook book INNER JOIN tblsubgenre sub ON book.subgenre_seq = sub.seq WHERE sub.seq = ?";
	    
	    try (PreparedStatement pstat = conn.prepareStatement(sql)) {
	        pstat.setString(1, subgenre_seq);
	        
	        try (ResultSet rs = pstat.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1); // ResultSet에서 카운트를 가져옴
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // SQL 예외 로깅

	    }
	    
	    return null; // 기본값 반환
	}
	
	
	public List<BookDTO> getBooks(String subGenre_seq, String targetread) {
	    List<BookDTO> bookList = new ArrayList<>();

	    try  {
	    String sql = "SELECT * FROM ( " +
	    			   "SELECT book.seq, book.name, book.author,book.cover,sub.subGenre  " +
	                   "FROM tblBook book " +
	                   "INNER JOIN tblsubgenre sub " +
	                   "ON book.subgenre_seq = sub.seq " +
	                   "WHERE sub.seq = ? " +
	                   "ORDER BY DBMS_RANDOM.VALUE " +
	                   ") " +
	                   "WHERE ROWNUM <= ?";


	    		pstat = conn.prepareStatement(sql);
	    		pstat.setString(1, subGenre_seq);
	    		pstat.setString(2, targetread);
	    		rs = pstat.executeQuery();
	    						
	        
	            while (rs.next()) {
	                BookDTO book = new BookDTO();
	                book.setSubgenre_seq(rs.getString("subGenre"));
	                book.setName(rs.getString("name"));
	                book.setAuthor(rs.getString("author"));
	                book.setCover(rs.getString("cover"));
	                book.setSeq(rs.getString("seq"));	    
	                bookList.add(book);
	           
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // 예외 처리
	    }

	    return bookList; // 결과 리스트 반환
	}
	public List<BookDTO> getBooks(String subGenre_seq, int count) {
		List<BookDTO> bookList = new ArrayList<>();

	    try  {
	    String sql = "SELECT * FROM ( " +
	                   "SELECT book.seq, book.name, book.author,book.cover,sub.subGenre  " +
	                   "FROM tblBook book " +
	                   "INNER JOIN tblsubgenre sub " +
	                   "ON book.subgenre_seq = sub.seq " +
	                   "WHERE sub.seq = ? " +
	                   "ORDER BY DBMS_RANDOM.VALUE " +
	                   ") " +
	                   "WHERE ROWNUM <= ?";


	    		pstat = conn.prepareStatement(sql);
	    		pstat.setString(1, subGenre_seq);
	    		pstat.setInt(2, count);
	    		rs = pstat.executeQuery();
	    						
	        
	            while (rs.next()) {
	                BookDTO book = new BookDTO();
	                book.setSubgenre_seq(rs.getString("subGenre"));
	                book.setName(rs.getString("name"));
	                book.setAuthor(rs.getString("author"));
	                book.setCover(rs.getString("cover"));
	                book.setSeq(rs.getString("seq"));	                
	                bookList.add(book);
	           
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // 예외 처리
	    }

	    return bookList; // 결과 리스트 반환
	}
	
    public int updatePreference(String memberSeq, String subgenreSeq, String targetRead) {
        int result = 0;
        PreparedStatement pstat = null;
        
        try {
            // 연결 확인
            if (conn == null || conn.isClosed()) {
                System.out.println("DB 연결이 닫혀있어 재연결을 시도합니다.");
                this.conn = DBUtil.open("43.203.106.58:1521:xe", "rebook", "java1234");
            }
            
            String sql = "UPDATE tblpreference SET subgenre_seq = ?, targetread = ? WHERE member_seq = ?";
            
            System.out.println("[PreferenceDAO] SQL: " + sql);
            System.out.println("[PreferenceDAO] Parameters - subgenreSeq: " + subgenreSeq 
                             + ", targetRead: " + targetRead 
                             + ", memberSeq: " + memberSeq);
            
            pstat = conn.prepareStatement(sql);
            pstat.setString(1, subgenreSeq);
            pstat.setString(2, targetRead);
            pstat.setString(3, memberSeq);
            
            result = pstat.executeUpdate();
            System.out.println("[PreferenceDAO] Update result: " + result);
            
            return result;
            
        } catch (Exception e) {
            System.out.println("[PreferenceDAO] Error in updatePreference: " + e.getMessage());
            e.printStackTrace();
            return 0;
        } finally {
            try {
                if (pstat != null) pstat.close();
            } catch (Exception e) {
                System.out.println("[PreferenceDAO] Error closing PreparedStatement: " + e.getMessage());
            }
        }
    }
}
