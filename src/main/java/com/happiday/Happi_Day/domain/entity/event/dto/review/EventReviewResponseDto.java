package com.happiday.Happi_Day.domain.entity.event.dto.review;


import com.happiday.Happi_Day.domain.entity.event.EventReview;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class EventReviewResponseDto {

    private Long id;

    private String username;

    private String description;

    private int rating;

    private LocalDateTime updatedAt;

    private String imageUrl;

    private String userProfileUrl;

    public static EventReviewResponseDto fromEntity(EventReview review) {
        return EventReviewResponseDto.builder()
                .id(review.getId())
                .username(review.getUser().getNickname())
                .description(review.getDescription())
                .rating(review.getRating())
                .updatedAt(review.getUpdatedAt())
                .imageUrl(review.getImageUrl())
                .userProfileUrl(review.getUser().getImageUrl())
                .build();
    }
}
