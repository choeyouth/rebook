package com.rebook.mybook.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.rebook.mybook.model.RankDTO;
import com.test.util.DBUtil;

public class RankDAO {
	public static RankDAO dao;
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	private RankDAO() {
		this.conn = DBUtil.open("43.203.106.58:1521:xe", "rebook", "java1234");
	}
	
	public static RankDAO getInstance() {
		if(dao == null) {
			dao = new RankDAO();
		}
		return dao;
	}
	
	public ArrayList<RankDTO> listMark() {
		ArrayList<RankDTO> list = new ArrayList<RankDTO>();
		
		try {
			String sql = "select M.name as membername,R.score as score,B.name as bookname,B.cover as cover from tblRank R inner join tblMemberInfo M\n"
					+ "on R.member_seq = m.member_seq\n"
					+ "inner join tblBook B\n"
					+ "on R.book_seq = B.seq";
			
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			
			while (rs.next()) {
				RankDTO dto = new RankDTO();
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
