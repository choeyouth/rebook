package com.rebook.book.model;

import lombok.Data;

@Data
public class MyBookRankDTO {
	
	private String bookSeq;
	private String memberSeq;
	private String memberName;

	private String rankSeq;
	private String rankDate; 
	private String score;
	           
}
