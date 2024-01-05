package com.happiday.Happi_Day.domain.entity.event.dto.review;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class EventReviewCreateDto {

    public EventReviewCreateDto() {
    }

    public EventReviewCreateDto(String description, int rating) {
        this.description = description;
        this.rating = rating;
    }

    @NotBlank(message = "내용을 입력해주세요.")
    private String description;

    @NotNull(message = "별점을 입력해주세요.")
    @Min(1)
    @Max(5)
    private int rating;

}
