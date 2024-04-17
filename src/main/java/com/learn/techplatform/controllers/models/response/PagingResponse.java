package com.learn.techplatform.controllers.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class PagingResponse {
    private List<?> content;
    private long totalItems;
    private int numberOfItems;
    private int pageNumber;
    private int pageSize;
    private int totalPages;

    public PagingResponse(Page<?> page) {
        this.content = page.getContent();
        this.totalItems = page.getTotalElements();
        this.numberOfItems = page.getNumberOfElements();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
    };

    public PagingResponse(List<?> content, Page<?> page) {
        this.content = content;
        this.totalItems = page.getTotalElements();
        this.numberOfItems = page.getNumberOfElements();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
    };
}
