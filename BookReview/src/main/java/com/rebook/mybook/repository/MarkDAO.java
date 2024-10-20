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
		this.conn = DBUtil.open("jdbc:oracle:thin:@localhost:1521:xe", "book", "java1234");

	}
	
	public static MarkDAO getInstance() {
		if(dao == null) {
			dao = new MarkDAO();
		}
		return dao;
	}
	
	public ArrayList<MarkDTO> listMark() {
		ArrayList<MarkDTO> list = new ArrayList<MarkDTO>();
		
		try {
			String sql = "select M.name as membername,R.score as score,B.name as bookname,B.cover as cover from tblRank R inner join tblMemberInfo M\n"
					+ "on R.member_seq = m.member_seq\n"
					+ "inner join tblBook B\n"
					+ "on R.book_seq = B.seq;";
			
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			
			while (rs.next()) {
				MarkDTO dto = new MarkDTO();
				dto.setMembername(rs.getString("membername"));
				dto.setScore(rs.getString("score"));
				dto.setBookname(rs.getString("bookname"));
				dto.setCover(rs.getString("cover"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}