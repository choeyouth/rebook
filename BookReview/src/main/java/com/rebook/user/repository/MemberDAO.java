package com.rebook.user.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.rebook.user.model.MemberDTO;
import com.rebook.user.model.MemberInfoDTO;
import com.test.util.DBUtil;

public class MemberDAO {

	public static MemberDAO dao;
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	private MemberDAO() {
    	this.conn = DBUtil.open("43.203.106.58:1521:xe", "rebook", "java1234");
	} 
	
	public static MemberDAO getInstance() {
		
		
		if (dao == null) {
			dao = new MemberDAO();
		}
		
		return dao;
		
	}
	
	public MemberDTO login(MemberDTO dto) {
		
		try {
			
			String sql = "select * from tblMember where id = ? and password = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getId());
			pstat.setString(2, dto.getPassword());
			
			rs = pstat.executeQuery();
			
			while (rs.next()) {
				
				MemberDTO mdto = new MemberDTO();
				
				mdto.setSeq(rs.getString("seq"));
				mdto.setId(rs.getString("id"));
				mdto.setPassword(rs.getString("password"));
				mdto.setIng(rs.getString("ing"));
				mdto.setLv(rs.getString("lv"));
				
				return mdto;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public MemberInfoDTO loginInfo(String memberSeq) {
		
		try {
			
			String sql = "select * from tblMemberInfo where member_seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, memberSeq);
			
			rs = pstat.executeQuery();
			
			while (rs.next()) {
				
				MemberInfoDTO idto = new MemberInfoDTO();
				idto.setSeq(rs.getString("seq"));
				idto.setName(rs.getString("Name"));
				idto.setTel(rs.getString("tel"));
				idto.setEmail(rs.getString("email"));
				idto.setPic(rs.getString("pic"));
				idto.setAddress(rs.getString("address"));
				idto.setAddrDetail(rs.getString("addrDetail"));
				idto.setZipcode(rs.getString("zipcode"));
				idto.setRegDate(rs.getString("regDate"));
				
				return idto;
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public int updateUser(MemberInfoDTO dto) {
		
		try {
			
			String sql = "update tblMemberInfo set name = ?, tel = ?, email = ? , address = ?, addrDetail = ?, zipcode = ? where member_seq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getName());
			pstat.setString(2, dto.getTel());
			pstat.setString(3, dto.getEmail());
			pstat.setString(4, dto.getAddress());
			pstat.setString(5, dto.getAddrDetail());
			pstat.setString(6, dto.getZipcode());
			pstat.setString(7, dto.getMember_seq());
				
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public int deleteUser(String seq) {
		
		try {
			
			String sql = "delete from tblMemberInfo where member_seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			int result = pstat.executeUpdate();
			
			if (result > 0) {
				String uSql = "update tblMember set ing = 0 where seq = ?";
				pstat = conn.prepareStatement(uSql);
				pstat.setString(1, seq);
				
				conn.commit();
				return pstat.executeUpdate();
			} else {
				conn.rollback();
				return 0;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public boolean isPw(String seq, String pw) {
		
		try {
			
			String sql = "select * from tblMember where seq = ? and password = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			pstat.setString(2, pw);
			rs = pstat.executeQuery();
			
			if (rs.next()) {
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public int updatePw(String seq, String pw) {

		try {
			
			String sql = "update tblMember set password = ? where seq = ?";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, pw);
			pstat.setString(2, seq);
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public int register(MemberDTO dto) {
		try {
			
			String sql = "INSERT INTO tblMember (seq,id,password,ing,lv) values (member_seq.NEXTVAL, ?, ?, default, default)";
			pstat = conn.prepareStatement(sql);			
			pstat.setString(1, dto.getId());
			pstat.setString(2, dto.getPassword());
			
			return pstat.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("UserDAO.register");
			e.printStackTrace();
		}
		
		return 0;
	}

}

