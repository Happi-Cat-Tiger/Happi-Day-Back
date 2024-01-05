package com.happiday.Happi_Day.domain.controller;


import com.happiday.Happi_Day.domain.entity.event.dto.review.EventReviewCreateDto;
import com.happiday.Happi_Day.domain.entity.event.dto.review.EventReviewResponseDto;
import com.happiday.Happi_Day.domain.entity.event.dto.review.EventReviewUpdateDto;
import com.happiday.Happi_Day.domain.service.EventReviewService;
import com.happiday.Happi_Day.utils.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/events/{eventId}/reviews")
@RequiredArgsConstructor
@Slf4j
public class EventReviewController {
    private final EventReviewService reviewService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<EventReviewResponseDto> createReview(
            @PathVariable("eventId") Long eventId,
            @Valid @RequestPart(value = "review") EventReviewCreateDto reviewCreateDto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
            ){
        String username = SecurityUtils.getCurrentUsername();
        EventReviewResponseDto responseDto = reviewService.createReview(eventId, reviewCreateDto, imageFile, username);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<EventReviewResponseDto>> readReviews(
            @PathVariable("eventId") Long eventId,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<EventReviewResponseDto> responseDtoList = reviewService.readReviews(eventId, pageable);
        return new ResponseEntity<>(responseDtoList, HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<EventReviewResponseDto> updateReview(
            @PathVariable("eventId") Long eventId,
            @PathVariable("reviewId") Long reviewId,
            @RequestPart(value = "review") EventReviewUpdateDto reviewUpdateDto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
            ) {
        String username = SecurityUtils.getCurrentUsername();
        EventReviewResponseDto responseDto = reviewService.updateReview(eventId, reviewId, reviewUpdateDto, username, imageFile);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable("eventId") Long eventId,
                                                @PathVariable("reviewId") Long reviewId) {
        String username = SecurityUtils.getCurrentUsername();
        reviewService.deleteReview(eventId, reviewId, username);
        return ResponseEntity.ok("삭제 성공");
    }
}
