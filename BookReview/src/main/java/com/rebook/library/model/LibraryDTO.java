package com.rebook.library.model;

import lombok.Data;

@Data
public class LibraryDTO {
	private String seq;
	private String name;
	private String lat;
	private String lng;
	private String address;
	private String category;
	
	private CategoryDTO categoryDTO;
	
	public String getCategory() {
		return category;
	}
	
	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}
}