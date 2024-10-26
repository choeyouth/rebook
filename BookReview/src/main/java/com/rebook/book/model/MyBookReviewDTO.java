package com.rebook.book.model;

import lombok.Data;

@Data
public class MyBookReviewDTO {
	
	private String bookSeq;
	private String memberSeq;
	private String memberName;
	           
	private String reviewSeq;
	private String reviewDate;
	private String commend;

}
