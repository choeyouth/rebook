package com.rebook.preference.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.rebook.preference.model.GenreListDTO;
import com.rebook.preference.model.SubGenreDTO;
import com.rebook.user.repository.MemberDAO;
import com.test.util.DBUtil;

public class SubGenreDAO {
	public static SubGenreDAO dao;
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	public SubGenreDAO() {
    	this.conn = DBUtil.open("43.203.106.58:1521:xe", "rebook", "java1234");
	} 
	
	public static SubGenreDAO getInstance() {
	
		
		
		if (dao == null) {
			dao = new SubGenreDAO();
		}
		
		return dao;
		
	}

	public List<SubGenreDTO> getSubgenre(int genreId) {
		List<SubGenreDTO> dtoList = new ArrayList<>(); // 리스트를 메서드 시작 부분에서 초기화합니다.

	    try {
	        String sql = "select * from tblSubGenre where genre_seq=?";
	        pstat = conn.prepareStatement(sql);
	        pstat.setInt(1, genreId);
	        rs = pstat.executeQuery();
	        
	        while (rs.next()) {
	            SubGenreDTO dto = new SubGenreDTO(); // 새로운 DTO 객체를 생성합니다.
	            dto.setSeq(rs.getString("seq"));
	            dto.setGenre_seq(rs.getString("genre_seq"));
	            dto.setSubgenre(rs.getString("subGenre"));
	            dtoList.add(dto); // 생성한 DTO 객체를 리스트에 추가합니다.
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close(); // ResultSet 닫기
	            if (pstat != null) pstat.close(); // PreparedStatement 닫기
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return dtoList.isEmpty() ? null : dtoList;
	 
	}
}