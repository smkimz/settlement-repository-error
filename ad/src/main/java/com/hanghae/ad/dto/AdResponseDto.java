package com.hanghae.ad.dto;

import com.hanghae.ad.domain.Ad;
import lombok.Data;

@Data
public class AdResponseDto {
    private Long id;
    private Long videoId;
    private Long viewCount;

    public AdResponseDto(Ad ad) {
        this.id = ad.getId();
        this.videoId = ad.getVideoId();
        this.viewCount = ad.getViewCount();
    }
}

