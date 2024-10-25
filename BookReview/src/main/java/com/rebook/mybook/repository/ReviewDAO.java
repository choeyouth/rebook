package com.rebook.mybook.repository; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.rebook.mybook.model.ReviewDTO;
import com.test.util.DBUtil;

public class ReviewDAO {
	public static ReviewDAO dao;
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	private ReviewDAO() {
		this.conn = DBUtil.open("43.203.106.58:1521:xe", "rebook", "java1234");
	} 
	
	public static ReviewDAO getInstance() {
		if(dao == null) {
			dao = new ReviewDAO();
		}
		return dao;
	}
	
	public ArrayList<ReviewDTO> listReview(String seq) {
		ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();
		
		try {
			String sql = "select R.seq as bookreviewseq, R.commend as commend, R.member_seq as memberseq, R.review_date as reviewdate, B.name as bookname,B.author as author,\r\n"
					+ "B.cover as cover,M.name as membername from tblBookReview R\r\n"
					+ "inner join tblBook B\r\n"
					+ "on R.Book_seq = B.seq\r\n"
					+ "inner join tblMemberInfo M\r\n"
					+ "on R.member_seq = M.seq\r\n"
					+ "where R.member_seq = ?\r\n"
					+ "order by R.seq desc";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
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
	}
	
	public int del(String bookreviewseq) {
		try {
			String sql = "DELETE FROM tblBookreview WHERE seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, bookreviewseq);
			
			return pstat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public ArrayList<ReviewDTO> list_ForEditReview(String bookreviewseq) {
		ArrayList<ReviewDTO> list = new ArrayList<ReviewDTO>();
		
		try {
			String sql = "select R.seq as bookreviewseq, R.commend as commend, R.member_seq as memberseq, R.review_date as reviewdate, B.name as bookname,B.author as author,\n"
					+ "B.cover as cover,M.name as membername \n"
					+ "from tblBookReview R \n"
					+ "inner join tblBook B \n"
					+ "on R.Book_seq = B.seq \n"
					+ "inner join tblMemberInfo M \n"
					+ "on R.member_seq = M.seq \n"
					+ "where R.seq = ?\n"
					+ "order by R.seq desc";
			
			pstat = conn.prepareStatement(sql);
	        pstat.setString(1, bookreviewseq);
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
	}
	public int edit(String commend, String bookreviewseq) {
		
	    try {
	        String sql = "update tblBookReview set commend = ? where seq = ?";
	        
	        pstat = conn.prepareStatement(sql);
	        pstat.setString(1, commend);
	        pstat.setString(2, bookreviewseq);
	        
	        return pstat.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0;
	}

}