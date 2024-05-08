package com.learn.techplatform.dto_modals;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.common.enums.CommentType;
import com.learn.techplatform.controllers.models.request.PostCommentRequest;
import com.learn.techplatform.entities.Comment;
import com.learn.techplatform.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Slf4j
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class CommentDTO {
    private String id;
    private String title;
    private String content;
    private int viewed;
    private CommentType type;
    private String userId;
    private String rootId;
    private String parentId;
    private Date createdAt;
    private Date updatedAt;
    private List<CommentDTO> children;
    private UserDTO user;

    public CommentDTO(PostCommentRequest request, String userId) {
        this.content = request.getContent();
        this.parentId = request.getParentId();
        this.rootId = request.getRootId();
        this.type = request.getType();
        this.userId = userId;
    }

    public CommentDTO(Comment comment){
        this.id = comment.getId();
        this.title = comment.getTitle();
        this.content = comment.getContent();
        this.viewed = comment.getViewed();
        this.type = comment.getCommentType();
        this.userId = comment.getUserId();
        this.rootId = comment.getRootId();
        this.parentId = comment.getParentId();
        this.createdAt = comment.getCreatedDate();
        this.updatedAt = comment.getUpdatedDate();
    }

    public CommentDTO(Comment comment, User user){
        this.id = comment.getId();
        this.title = comment.getTitle();
        this.content = comment.getContent();
        this.viewed = comment.getViewed();
        this.type = comment.getCommentType();
        this.userId = comment.getUserId();
        this.rootId = comment.getRootId();
        this.parentId = comment.getParentId();
        this.createdAt = comment.getCreatedDate();
        this.updatedAt = comment.getUpdatedDate();
        this.user = new UserDTO(user);
    }
}
