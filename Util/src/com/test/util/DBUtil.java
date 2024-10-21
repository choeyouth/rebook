package com.test.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	
	private static Connection conn = null;
	
	public static Connection open() {
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "server";
		String pw = "java1234";
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pw);
			//conn.setAutoCommit(false);
			
			return conn;
			
		} catch (Exception e) {
			System.out.println("DBUtil.open");
			e.printStackTrace();
		}
		
		return null;
	
	}
	
	//메서드 오버로딩 
	public static Connection open(String serverIP, String id, String pw) {
	    // JDBC URL 형식을 만듭니다. SID 또는 서비스 이름을 여기서 설정해야 합니다.
	    String url = "jdbc:oracle:thin:@" + serverIP;

	    try {
	        // Oracle 드라이버 로드
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	        // 데이터베이스 연결
	        conn = DriverManager.getConnection(url, id, pw);
	        return conn;

	    } catch (Exception e) {
	        System.out.println("DBUtil.open - 데이터베이스 연결 오류");
	        e.printStackTrace();
	    }

	    return null;
	}


}
