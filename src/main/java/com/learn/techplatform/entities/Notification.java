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
@Table(name = "[notification]")
public class Notification extends AbstractBaseEntity<String> implements Serializable {
    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "linked")
    private String linked;

    @Column(name = "seen")
    private boolean seen;

    @Column(name = "user_id")
    private String userId;
}
