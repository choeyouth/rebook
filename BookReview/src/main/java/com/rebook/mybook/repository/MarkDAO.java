package com.rebook.mybook.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.rebook.mybook.model.MarkDTO;
import com.test.util.DBUtil;

public class MarkDAO { 
	public static MarkDAO dao;
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	private MarkDAO() {
		this.conn = DBUtil.open("43.203.106.58:1521:xe", "rebook", "java1234");

	}
	
	public static MarkDAO getInstance() {
		if(dao == null) {
			dao = new MarkDAO();
		}
		return dao;
	}
	
	public ArrayList<MarkDTO> listMark(String seq) {
		ArrayList<MarkDTO> list = new ArrayList<MarkDTO>();
		
		try {
			String sql = "select M.seq as bookmarkseq,M.famousline as famousline,M.member_seq as memberseq,M.regdate as regdate, B.name as bookname, B.author as author,\n"
					+ "B.cover as cover, I.name as membername\n"
					+ "from tblBookMark M\n"
					+ "inner join tblBook B\n"
					+ "on M.book_seq = B.seq\n"
					+ "inner join tblMemberInfo I\n"
					+ "on M.member_seq = I.seq\n"
					+ "where M.member_seq = ? \n"
					+ "order by M.seq desc";
			
			pstat = conn.prepareStatement(sql);
	        pstat.setString(1, seq);
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
	
	public ArrayList<MarkDTO> list_ForEditMark(String bookmarkseq) {
		ArrayList<MarkDTO> list = new ArrayList<MarkDTO>();
		
		try {
			String sql = "select M.seq as bookmarkseq,M.famousline as famousline,M.member_seq as memberseq,M.regdate as regdate, B.name as bookname, B.author as author,B.cover as cover, I.name as membername \r\n"
					+ "from tblBookMark M \r\n"
					+ "inner join tblBook B \r\n"
					+ "on M.book_seq = B.seq \r\n"
					+ "inner join tblMemberInfo I \r\n"
					+ "on M.member_seq = I.seq \r\n"
					+ "where M.seq = ? \r\n"
					+ "order by M.seq desc";
			
			pstat = conn.prepareStatement(sql);
	        pstat.setString(1, bookmarkseq);
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
	
	public int edit(MarkDTO dto) {
		
		try {
			String sql = "UPDATE tblBookMark SET famousline = ? WHERE seq = ?";
			
			pstat = conn.prepareStatement(sql);
	        pstat.setString(1, dto.getFamousline() );
	        pstat.setString(2, dto.getBookmarkseq());
	        rs = pstat.executeQuery();
			
	        return pstat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public int del(String seq) {
	    try {
	        String sql = "DELETE FROM tblBookMark WHERE seq = ?";

	        pstat = conn.prepareStatement(sql);
	        pstat.setString(1, seq);
	        
	        return pstat.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0; 
	}
}