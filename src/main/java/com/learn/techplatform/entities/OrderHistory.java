package com.learn.techplatform.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.learn.techplatform.common.enums.OrderHistoryStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

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
@Table(name = "[order_history]")
public class OrderHistory extends AbstractBaseEntity<String> implements Serializable {
    @Column(name = "course_id")
    private String courseId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderHistoryStatus status;
}
