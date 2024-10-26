package com.rebook.user.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.rebook.user.model.MemberInfoDTO;
import com.test.util.DBUtil;

public class MemberInfoDAO {

	public static MemberInfoDAO dao;
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	private MemberInfoDAO() {
    	this.conn = DBUtil.open("43.203.106.58:1521:xe", "rebook", "java1234");
	} 
	
	public static MemberInfoDAO getInstance() {
		if (dao == null) {
			dao = new MemberInfoDAO();
		}
		
		return dao;
	}

	public int register(MemberInfoDTO InfoDto) {
		try {
			
			
			String sql = "insert into tblMemberInfo (seq, member_seq, name, tel, email, pic, address, addrDetail, zipcode,regdate) VALUES ((SELECT MAX(seq) FROM tblMember),(SELECT MAX(seq) FROM tblMember) , ? , ?, ?, ?,? ,?, ? , default)";
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, InfoDto.getName());
			pstat.setString(2, InfoDto.getTel());
			pstat.setString(3, InfoDto.getEmail());
			pstat.setString(4, InfoDto.getPic());
			pstat.setString(5, InfoDto.getAddress());
			pstat.setString(6, InfoDto.getAddrDetail());
			pstat.setString(7, InfoDto.getZipcode());

			rs.close();
			pstat.close();
			
			return pstat.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


}
