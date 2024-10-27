package com.rebook.discussion.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.rebook.discussion.model.DiscussionBoardDTO;
import com.rebook.discussion.model.DiscussionReplyDTO;
import com.test.util.DBUtil;

public class BoardDAO {
	
	public static BoardDAO dao;
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	private BoardDAO() {
		this.conn = DBUtil.open("43.203.106.58:1521:xe", "rebook", "java1234");
	}

	public static BoardDAO getInstance() {
		if (dao == null) {
			dao = new BoardDAO();
		}
		return dao;
	}
	
	public ArrayList<DiscussionBoardDTO> list() {
		
		try {
					
			String sql = "select db.seq AS seq, db.title AS title, db.postDate AS postDate, db.readCount, m.id AS memberId, b.name AS bookTitle from tblDiscussionBoard db\n"
						+ "inner join tblMember m ON db.member_seq = m.seq\n"
						+ "inner join tblBook b ON db.book_seq = b.seq\n"
						+ "order by seq desc";
			
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			ArrayList<DiscussionBoardDTO> list = new ArrayList<DiscussionBoardDTO>();
			
			while (rs.next()) {
				
				DiscussionBoardDTO dto = new DiscussionBoardDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setTitle(rs.getString("title"));
				dto.setPostDate(rs.getString("postDate"));
				dto.setReadCount(rs.getInt("readCount"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setBookTitle(rs.getString("bookTitle"));
				
				list.add(dto);				
			}	
			
			rs.close();
			stat.close();
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	public DiscussionBoardDTO getPost(String postSeq) {
		
		try {
			
			String sql = "select db.seq AS seq, db.title AS title, db.postDate AS postDate, db.readCount, db.content AS content, db.img AS img, "
						+ "m.id AS memberId, m.seq AS memberSeq, COALESCE(mi.name, '탈퇴 유저') AS memberName, b.name AS bookTitle, b.seq AS bookSeq from tblDiscussionBoard db\n"
						+ "inner join tblMember m ON db.member_seq = m.seq\n"
						+ "inner join tblBook b ON db.book_seq = b.seq "
						+ "left join tblMemberInfo mi ON mi.member_seq = m.seq "
						+ "where db.seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, postSeq);
			rs = pstat.executeQuery();
			
			DiscussionBoardDTO dto = new DiscussionBoardDTO();
			
			if (rs.next()) {
				dto.setSeq(rs.getString("seq"));
				dto.setTitle(rs.getString("title"));
				dto.setPostDate(rs.getString("postDate"));
				dto.setReadCount(rs.getInt("readCount"));
				dto.setContent(rs.getString("content"));
				dto.setImg(rs.getString("img"));
				dto.setMemberId(rs.getString("memberId"));
				dto.setMember_seq(rs.getString("memberSeq"));
				dto.setMemberName(rs.getString("memberName"));
				dto.setBookTitle(rs.getString("bookTitle"));
				dto.setBook_seq(rs.getString("bookSeq"));
			} 
			
			rs.close();
			pstat.close();
			return dto;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public List<DiscussionReplyDTO> getReply(String postSeq) {
		
		try {
			
			String sql = "select r.seq AS seq, r.reply, m.id AS memberId, r.member_seq AS memberSeq, COALESCE(mi.name, '탈퇴 유저') AS memberName, r.commitDate from tblDiscussionBoard db\n"
					+ "inner join tblBook b ON db.book_seq = b.seq\n"
					+ "inner join tblDiscussionReply r ON r.discussionBoard_seq = db.seq\n"
					+ "inner join tblMember m ON r.member_seq = m.seq\n"
					+ "LEFT JOIN tblMemberInfo mi ON mi.member_seq = m.seq "
					+ "where db.seq = ? "
					+ "order by r.seq desc ";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, postSeq);
			rs = pstat.executeQuery();
			
			ArrayList<DiscussionReplyDTO> list = new ArrayList<DiscussionReplyDTO>();
			
			while (rs.next()) {
				DiscussionReplyDTO dto = new DiscussionReplyDTO();
				dto.setSeq(rs.getString("seq"));
				dto.setReply(rs.getString("reply"));
				dto.setCommitDate(rs.getString("commitDate"));
				dto.setMember_seq(rs.getString("memberSeq"));
				dto.setMemberId(rs.getString("memberId"));
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

	public int editComment(DiscussionReplyDTO dto) {
		
		try {
			
			String sql = "UPDATE tblDiscussionReply SET reply = ? WHERE seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getReply());
			pstat.setString(2, dto.getSeq());
			pstat.close();
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public int deleteComment(String seq) {
		
		try {
			
			String sql = "DELETE FROM tblDiscussionReply WHERE seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	   
	    return 0;  
	}

	public int addComment(String memberSeq, String postSeq, String content) {
		
		try {
			
	        // SEQ 값을 미리 가져오기
	        String seqSql = "SELECT discussionReply_seq.NEXTVAL FROM DUAL";
	        PreparedStatement seqStmt = conn.prepareStatement(seqSql);
	        ResultSet seqRs = seqStmt.executeQuery();

	        int seq = 0;
	        if (seqRs.next()) {
	            seq = seqRs.getInt(1);  // 새로운 SEQ 값 가져오기
	        }
	        seqRs.close();
	        seqStmt.close();
			
			 String sql = "INSERT INTO tblDiscussionReply (seq, discussionBoard_seq, member_seq, reply, commitDate) "
		               + "VALUES (?, ?, ?, ?, SYSDATE)";
			 
			 pstat = conn.prepareStatement(sql);
		 
	         pstat.setInt(1, seq);           // SEQ 값 설정
	         pstat.setString(2, postSeq);     // 게시물 번호
	         pstat.setString(3, memberSeq);   // 회원 번호
	         pstat.setString(4, content);     // 댓글 내용

	         int result = pstat.executeUpdate();

	         if (result > 0) {
	        	 pstat.close();
	             return seq;  // 새 댓글 번호 반환
	         }
	         
			 pstat.close();
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	    return 0;  
	}

	public int updatePost(DiscussionBoardDTO dto) {
	    int result = 0;

	    try {
	        String sql = "UPDATE tblDiscussionBoard SET title = ?, content = ? WHERE seq = ?";

	        pstat = conn.prepareStatement(sql);
	        pstat.setString(1, dto.getTitle());
	        pstat.setString(2, dto.getContent());
	        pstat.setString(3, dto.getSeq());

	        result = pstat.executeUpdate();

	        pstat.close();
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return result;
	}

	public boolean deletePost(String postSeq) {
		
        PreparedStatement pstat1 = null;
        PreparedStatement pstat2 = null;
        boolean isDeleted = false;
		
		try {
			
            String replyDel = "DELETE FROM tblDiscussionReply WHERE discussionBoard_seq = ?";
            pstat1 = conn.prepareStatement(replyDel);
            pstat1.setString(1, postSeq);
            pstat1.executeUpdate();

            String postDel = "DELETE FROM tblDiscussionBoard WHERE seq = ?";
            pstat2 = conn.prepareStatement(postDel);
            pstat2.setString(1, postSeq);
            int result = pstat2.executeUpdate();

            if (result > 0) {
                isDeleted = true;
                conn.commit();  
            } else {
                conn.rollback(); 
            }
            
		} catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();  
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                if (pstat1 != null) pstat1.close();
                if (pstat2 != null) pstat2.close();
                conn.setAutoCommit(true);  
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		
		return false;
	}

	public boolean addPost(DiscussionBoardDTO post) {
	    String sql = "INSERT INTO tblDiscussionBoard (seq, book_seq, readcount, member_seq, title, content, postDate) "
	    		   + "VALUES (discussionBoard_seq.NEXTVAL, ?, DEFAULT, ?, ?, ?, SYSDATE)";
	    
	    try (PreparedStatement pstat = conn.prepareStatement(sql)) {
	    	pstat.setString(1, post.getBook_seq());
	        pstat.setString(2, post.getMember_seq());
	        pstat.setString(3, post.getTitle());
	        pstat.setString(4, post.getContent());

	        int result = pstat.executeUpdate();
	        return result > 0; 
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}

}
