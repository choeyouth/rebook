package com.rebook.book.model;

import lombok.Data;

@Data
public class MyBookDTO {
	
	private String bookSeq;
	private String memberSeq;
	private String memberName;

	private String bookmarkSeq;
	private String bookmarkDate; 
	private String famousline; 
	           
	private String wishSeq;
	           
	private String rankSeq;
	private String rankDate; 
	private String score;
	           
	private String reviewSeq;
	private String reviewDate;
	private String commend;

}
