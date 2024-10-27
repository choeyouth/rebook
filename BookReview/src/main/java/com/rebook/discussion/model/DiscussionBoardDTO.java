package com.rebook.discussion.model;

import lombok.Data;

@Data
public class DiscussionBoardDTO {

	private String seq;
	private String book_seq;
	private String member_seq;
	private String title;
	private String content;
	private String postDate;
	private int readCount;
	private String img;
	
	private String memberId;
	private String memberName;
	private String bookTitle;
	
}
