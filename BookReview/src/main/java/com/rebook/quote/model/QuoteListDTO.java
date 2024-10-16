package com.rebook.quote.model;

import lombok.Data;

@Data
public class QuoteListDTO {
	private String seq;
	private String quote;
	private String author;
	private String engauthor;
	private String authorpic;
}
