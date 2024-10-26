package com.rebook.book.model;

import lombok.Data;

@Data
public class BookDTO {
	
	private String seq;
	private String name;
	private String author;
	private String story;
	private String cover;
	private String subgenre_seq;
	
	private String subgenre;
	private String genre;
}
