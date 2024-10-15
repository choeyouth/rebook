package com.rebook.library.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.rebook.library.model.CategoryDTO;
import com.rebook.library.model.LibraryDTO;
import com.test.util.DBUtil;

public class LibraryDAO {
	public static LibraryDAO dao;
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	private LibraryDAO() {
		this.conn = DBUtil.open("localhost", "book_project", "java1234");
	}
	

	public static LibraryDAO getInstance() {
		if (dao == null) {
			dao = new LibraryDAO();
		}
		return dao;
	}

	public ArrayList<LibraryDTO> listLibrary() {
	    ArrayList<LibraryDTO> list = new ArrayList<LibraryDTO>();

	    try {
	        String sql = "SELECT l.seq as seq, l.name as name, l.lat as lat, l.lng as lng, l.address as address, c.category as category " +
	                     "FROM tblLibrary l INNER JOIN tblCategory c ON l.category = c.seq";
	        
	        pstat = conn.prepareStatement(sql);
	        rs = pstat.executeQuery();
	        
	        while (rs.next()) {
	            LibraryDTO dto = new LibraryDTO();
	            dto.setSeq(rs.getString("seq"));
	            dto.setName(rs.getString("name"));
	            dto.setLat(rs.getString("lat"));
	            dto.setLng(rs.getString("lng"));
	            dto.setAddress(rs.getString("address"));
	            dto.setCategory(rs.getString("category"));

	            CategoryDTO cdto = new CategoryDTO();
	            cdto.setSeq(rs.getString("seq"));
	            cdto.setCategory(rs.getString("category"));
	            
	            dto.setCategoryDTO(cdto);
	            list.add(dto);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return list;
	}


	public ArrayList<LibraryDTO> searchLib(String search) {
		
		ArrayList<LibraryDTO> list = new ArrayList<LibraryDTO>();
		try {
			 String sql = "SELECT l.seq as seq, l.name as name, l.lat as lat, l.lng as lng, l.address as address, c.category as category " +
                     "FROM tblLibrary l INNER JOIN tblCategory c ON l.category = c.seq " +
                     "WHERE l.address LIKE '%' || ? || '%'";
        
	        pstat = conn.prepareStatement(sql);
	        pstat.setString(1, search);
	        rs = pstat.executeQuery();
	        
	        while(rs.next()) {
	        	LibraryDTO dto = new LibraryDTO();
	            dto.setSeq(rs.getString("seq"));
	            dto.setName(rs.getString("name"));
	            dto.setLat(rs.getString("lat"));
	            dto.setLng(rs.getString("lng"));
	            dto.setAddress(rs.getString("address"));
	            dto.setCategory(rs.getString("category"));
	            
	            CategoryDTO cdto = new CategoryDTO();
	            cdto.setSeq(rs.getString("seq"));
	            cdto.setCategory(rs.getString("category"));
	            
	            dto.setCategoryDTO(cdto);
	            list.add(dto);
	            
	        }
	        
	        return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
