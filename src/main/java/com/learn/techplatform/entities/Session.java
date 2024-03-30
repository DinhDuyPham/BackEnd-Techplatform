package com.learn.techplatform.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.learn.techplatform.common.enums.SessionType;
import com.learn.techplatform.common.enums.SystemStatus;
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
@Table(name = "[session]")
public class Session extends AbstractBaseEntity<String> implements Serializable {

    @Column(name = "data", columnDefinition = "TEXT")
    private String data;

    @Column(name = "user_id", length = 50)
    private String userId;

    @Column(name = "expire_time")
    private long expireTime;

    @Column(name = "session_type", length = 50)
    @Enumerated(EnumType.STRING)
    private SessionType sessionType;
}
