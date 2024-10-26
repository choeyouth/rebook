package com.rebook.book.model;

import lombok.Builder;
import lombok.Data;

@Data
public class MyBookWishDTO {
	
	private String bookSeq;
	private String memberSeq;
	private String memberName;

	private String wishSeq;

}
