package com.learn.techplatform.dto_modals;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.learn.techplatform.entities.Blog;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class BlogDTO {
    private String id;

    private String title;

    private String content;

    private int viewed;

    private int liked;

    private String userId;

    public BlogDTO (Blog blog){
        this.id = blog.getId();
        this.title = blog.getTitle();
        this.content = blog.getContent();
        this.viewed = blog.getViewed();
        this.liked = blog.getLiked();
        this.userId = blog.getUserId();
    }
}
