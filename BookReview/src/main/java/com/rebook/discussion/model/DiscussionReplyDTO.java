package com.rebook.discussion.model;

import lombok.Data;

@Data
public class DiscussionReplyDTO {

	private String seq;
	private String reply;
	private String commitDate;
	private String member_seq;
	
	private String memberId;
	private String memberName;
	
}
