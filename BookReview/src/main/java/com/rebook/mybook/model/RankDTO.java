package com.rebook.mybook.model;

import lombok.Data;

@Data
public class RankDTO {
	private String rankseq; 
	private String memberseq;
	private String rankdate;
	private String membername; 
	private String score;
	private String bookname;
	private String cover;
	private String author;
}
