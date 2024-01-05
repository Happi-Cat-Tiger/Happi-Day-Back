package com.happiday.Happi_Day.domain.entity.event.dto.review;


import com.happiday.Happi_Day.domain.entity.event.EventReview;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class EventReviewUpdateDto {

    public EventReviewUpdateDto() {
    }

    public EventReviewUpdateDto(String description, int rating) {
        this.description = description;
        this.rating = rating;
    }

    private String description;

    private int rating;

    public EventReview toEntity() {
        return EventReview.builder()
                .description(description)
                .rating(rating)
                .build();
    }

}
