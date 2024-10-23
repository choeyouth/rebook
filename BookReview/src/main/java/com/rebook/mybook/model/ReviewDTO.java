package com.rebook.mybook.model;

import lombok.Data;

@Data
public class ReviewDTO {
	private String bookreviewseq;
	private String commend;
	private String memberseq;
	private String reviewdate;
	private String bookname;
	private String author;
	private String cover;
	private String membername;
}