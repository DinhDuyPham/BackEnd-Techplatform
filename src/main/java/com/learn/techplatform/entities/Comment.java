package com.learn.techplatform.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.learn.techplatform.common.enums.CommentType;
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
@Table(name = "[comment]")
public class Comment extends AbstractBaseEntity<String> implements Serializable {
    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "viewed")
    private int viewed;

    @Column(name = "comment_type")
    @Enumerated(EnumType.STRING)
    private CommentType commentType;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "root_id")
    private String rootId;

    @Column(name = "parent_id")
    private String parentId;
}
