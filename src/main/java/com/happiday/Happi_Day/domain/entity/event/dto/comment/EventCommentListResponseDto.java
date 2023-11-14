package com.happiday.Happi_Day.domain.entity.event.dto.comment;

import com.happiday.Happi_Day.domain.entity.event.Event;
import com.happiday.Happi_Day.domain.entity.event.EventComment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class EventCommentListResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private Event event;

    public static EventCommentListResponseDto fromEntity(EventComment comment) {
        return EventCommentListResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .event(comment.getEvent())
                .build();
    }
}
