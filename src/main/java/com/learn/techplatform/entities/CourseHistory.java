package com.learn.techplatform.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@EntityListeners(AuditingEntityListener.class)
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "[course_history]")
public class CourseHistory extends AbstractBaseEntity<String> implements Serializable {
    @Column(name = "course_id")
    private String courseId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "last_seen_at")
    private Date lastSeenAt;

    @Column(name = "current_lesson_id")
    private String currentLessonId;
}
