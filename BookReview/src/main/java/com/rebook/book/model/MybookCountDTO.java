package com.rebook.book.model;

import lombok.Data;

@Data
public class MybookCountDTO {
	
    private int markCount;
    private int rankCount;
    private int reviewCount;
    private int wishCount;

    // 생성자
    public MybookCountDTO(int markCount, int rankCount, int reviewCount, int wishCount) {
        this.markCount = markCount;
        this.rankCount = rankCount;
        this.reviewCount = reviewCount;
        this.wishCount = wishCount;
    }

}
