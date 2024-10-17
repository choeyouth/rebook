package com.rebook.word.model;

import lombok.Data;

@Data
public class WordDTO {
	private String target_code;
	private String word;
	private String pos;
	private String definition;
}
