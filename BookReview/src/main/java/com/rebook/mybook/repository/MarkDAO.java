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
	
	public ArrayList<MarkDTO> listMark() {
		ArrayList<MarkDTO> list = new ArrayList<MarkDTO>();
		
		try {
			String sql = "";
			
			pstat = conn.prepareStatement(sql);
			rs = pstat.executeQuery();
			
			while (rs.next()) {
				MarkDTO dto = new MarkDTO();
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}