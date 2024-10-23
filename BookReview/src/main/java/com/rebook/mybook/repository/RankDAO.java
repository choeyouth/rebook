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
	
	public ArrayList<RankDTO> listMark(String seq) {
		ArrayList<RankDTO> list = new ArrayList<RankDTO>();
		
		try {
			String sql = "select R.seq as rankseq, M.seq as memberseq, M.name as membername, R.score as score, B.name as bookname, B.cover as cover ,B.author as author " +
		             "from tblRank R " +
		             "inner join tblMemberInfo M " +
		             "on R.member_seq = M.seq " +
		             "inner join tblBook B " +
		             "on R.book_seq = B.seq " +
		             "where M.seq = ? " +
		             "order by R.seq desc";

			
			pstat = conn.prepareStatement(sql);
	        pstat.setString(1, seq);
	        rs = pstat.executeQuery();
			
			
			
			while (rs.next()) {
				RankDTO dto = new RankDTO();
				dto.setRankseq(rs.getString("rankseq"));
				dto.setMemberseq(rs.getString("memberseq"));
				dto.setMembername(rs.getString("membername"));
				dto.setScore(rs.getString("score"));
				dto.setBookname(rs.getString("bookname"));
				dto.setCover(rs.getString("cover"));
				dto.setAuthor(rs.getString("author"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
