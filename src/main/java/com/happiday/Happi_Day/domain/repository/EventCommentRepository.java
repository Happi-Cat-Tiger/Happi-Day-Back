package com.happiday.Happi_Day.domain.repository;

import com.happiday.Happi_Day.domain.entity.event.Event;
import com.happiday.Happi_Day.domain.entity.event.EventComment;
import com.happiday.Happi_Day.domain.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventCommentRepository extends JpaRepository<EventComment, Long> {
    Page<EventComment> findAllByEvent(Event event, Pageable pageable);

    Page<EventComment> findAllByUser(User user, Pageable pageable);
}
