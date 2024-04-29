package com.learn.techplatform.entities;


import com.fasterxml.jackson.annotation.JsonInclude;
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
@Table(name = "[chapter]")
public class Chapter extends AbstractBaseEntity<String> implements Serializable {
    @Column(name = "title")
    private String title;

    @Column(name = "numerical_order")
    private int numericalOrder;

    @Column(name = "course_id", length = 50)
    private String courseId;
}
