package com.learn.techplatform.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.learn.techplatform.common.enums.CourseType;
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
@Table(name = "[course]")
public class Course extends AbstractBaseEntity<String> implements Serializable {
    @Column(name = "title")
    private String title;

    @Column(name = "thumbnail_url", columnDefinition = "TEXT")
    private String thumbnailUrl;

    @Column(name = "slug", columnDefinition = "TEXT")
    private String slug;

    @Column(name = "viewed")
    private int viewed;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "course_type")
    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    @Column(name = "discount")
    private float discount;
}
