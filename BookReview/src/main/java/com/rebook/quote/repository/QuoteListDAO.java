package com.rebook.quote.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.rebook.quote.model.QuoteListDTO;
import com.test.util.DBUtil;
                                  
public class QuoteListDAO {
    private static QuoteListDAO dao;

    private Connection conn;
	private Statement stat;
    private PreparedStatement pstat;
    private ResultSet rs;

    private QuoteListDAO() {
    	this.conn = DBUtil.open("43.203.106.58:1521:xe", "rebook", "java1234");    
    }

    
    public static QuoteListDAO getInstance() {
        if (dao == null) {
            dao = new QuoteListDAO();
        }
        return dao;
    }

    public ArrayList<QuoteListDTO> listQuote() {
        ArrayList<QuoteListDTO> list = new ArrayList<>();
        
        String sql = "SELECT * FROM tblQuoteList";
        
        try {
            pstat = conn.prepareStatement(sql);
            rs = pstat.executeQuery();
            
            while (rs.next()) {
            	
                QuoteListDTO dto = new QuoteListDTO();
                dto.setSeq(rs.getString("seq"));
                dto.setQuote(rs.getString("quote"));
                dto.setAuthor(rs.getString("author"));
                dto.setEngauthor(rs.getString("engauthor"));
                dto.setAuthorpic(rs.getString("authorpic"));
                
                list.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

	public  QuoteListDTO getRandomQuote() {
		QuoteListDTO quote = new QuoteListDTO();
        try {
            String sql = "select* from (select * from tblquotelist order by dbms_random.value) where rownum=1";
            stat = conn.createStatement();
            rs = stat.executeQuery(sql);
            
            if (rs.next()) {
                quote.setSeq(rs.getString("seq"));
                quote.setQuote(rs.getString("quote"));
                quote.setAuthor(rs.getString("author"));
                quote.setAuthorpic(rs.getString("authorpic"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return quote;

	}
}