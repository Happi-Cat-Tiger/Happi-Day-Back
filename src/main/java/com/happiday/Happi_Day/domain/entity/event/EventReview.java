package com.happiday.Happi_Day.domain.entity.event;

import com.happiday.Happi_Day.domain.entity.BaseEntity;
import com.happiday.Happi_Day.domain.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Slf4j
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name ="event_review")
@SQLDelete(sql = "UPDATE event_review SET deleted_at = now() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class EventReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @Setter
    private String imageUrl;

    public void update(EventReview updateReview) {
        if (updateReview.getDescription() != null && !updateReview.getDescription().isEmpty()) {
            this.description = updateReview.getDescription();
        }
        if (updateReview.getRating() >= 1 && updateReview.getRating() <= 5) {
            this.rating = updateReview.getRating();
        }
    }



}
