package com.happiday.Happi_Day.domain.service;

import com.happiday.Happi_Day.domain.entity.event.Event;
import com.happiday.Happi_Day.domain.entity.event.EventReview;
import com.happiday.Happi_Day.domain.entity.event.dto.review.EventReviewCreateDto;
import com.happiday.Happi_Day.domain.entity.event.dto.review.EventReviewResponseDto;
import com.happiday.Happi_Day.domain.entity.event.dto.review.EventReviewUpdateDto;
import com.happiday.Happi_Day.domain.entity.user.User;
import com.happiday.Happi_Day.domain.repository.EventRepository;
import com.happiday.Happi_Day.domain.repository.EventReviewRepository;
import com.happiday.Happi_Day.domain.repository.UserRepository;
import com.happiday.Happi_Day.exception.CustomException;
import com.happiday.Happi_Day.exception.ErrorCode;
import com.happiday.Happi_Day.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EventReviewService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventReviewRepository reviewRepository;
    private final FileUtils fileUtils;

    @Transactional
    public EventReviewResponseDto createReview(Long eventId, EventReviewCreateDto request, MultipartFile imageFile, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        // 참여한 이벤트인지 검증
        boolean isJoined = user.getEventJoinList().stream()
                .anyMatch(joinList -> joinList.equals(event));

        if (!isJoined) {
            throw new CustomException(ErrorCode.EVENT_NOT_JOINED);
        }

        // 해당 이벤트에 리뷰를 이미 작성했는지 검증
        boolean isAlreadyWritten = user.getEventReviews().stream()
                .anyMatch(review -> review.getEvent().equals(event));

        if (isAlreadyWritten) {
            throw new CustomException(ErrorCode.EVENT_REVIEW_ALREADY_SUBMITTED);
        }

        String imageUrl = fileUtils.uploadFile(imageFile);

        EventReview review = EventReview.builder()
                .user(user)
                .event(event)
                .description(request.getDescription())
                .rating(request.getRating())
                .imageUrl(imageUrl)
                .build();

        reviewRepository.save(review);
        return EventReviewResponseDto.fromEntity(review);

    }

    public Page<EventReviewResponseDto> readReviews(Long eventId, Pageable pageable) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        Page<EventReview> reviewList = reviewRepository.findAllByEvent(event, pageable);

        return reviewList.map(EventReviewResponseDto::fromEntity);
    }

    @Transactional
    public EventReviewResponseDto updateReview (
            Long eventId, Long reviewId, EventReviewUpdateDto request, String username, MultipartFile imageFile
    ) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        EventReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_REVIEW_NOT_FOUND));

        if (imageFile != null && !imageFile.isEmpty()) {
            String newImageUrl = fileUtils.uploadFile(imageFile);
            review.setImageUrl(newImageUrl);
        }

        review.update(request.toEntity());
        reviewRepository.save(review);

        return EventReviewResponseDto.fromEntity(review);
    }

    @Transactional
    public void deleteReview(Long eventId, Long reviewId, String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        eventRepository.findById(eventId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_NOT_FOUND));

        EventReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.EVENT_REVIEW_NOT_FOUND));

        reviewRepository.delete(review);

    }


}
