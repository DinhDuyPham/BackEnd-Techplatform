package com.learn.techplatform.controllers;

import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.controllers.models.request.EditBlogRequest;
import com.learn.techplatform.dto_modals.BlogDTO;
import com.learn.techplatform.security.AuthSession;
import com.learn.techplatform.security.AuthUser;
import com.learn.techplatform.services.Blog.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ApiPath.BLOG_API)
public class BlogController extends AbstractBaseController {
    @Autowired
    BlogService blogService;

    @PostMapping(ApiPath.ADD)
    @Operation(summary = "Create blog")
    ResponseEntity<RestAPIResponse<Object>> createBlog(@RequestBody @Valid BlogDTO blogDTO, @Parameter(hidden = true) @AuthSession() AuthUser user) {
        blogService.createBlog(blogDTO, user.getId());
        return responseUtil.successResponse("OK!");
    }

    @DeleteMapping(ApiPath.DELETE + ApiPath.ID)
    @Operation(summary = "Delete blog")
    ResponseEntity<RestAPIResponse<Object>> deleteBlog(@PathVariable("id") String id) {
        blogService.deleteBlog(id);
        return responseUtil.successResponse("OK!");
    }

    @PutMapping(ApiPath.EDIT + ApiPath.ID)
    @Operation(summary = "Update blog")
    ResponseEntity<RestAPIResponse<Object>> editBlog(@PathVariable("id") String id, @RequestBody @Valid EditBlogRequest editBlogRequest) {
        blogService.editBlog(id, editBlogRequest);
        return responseUtil.successResponse("OK!");
    }

    @GetMapping(ApiPath.GET_PAGE)
    @Operation(summary = "Get Blog Pagination")
    ResponseEntity<RestAPIResponse<Object>> getPageBlog(@RequestParam(name = "page_number", defaultValue = "1", required = false) int pageNumber,
                                                          @RequestParam(name = "page_size", defaultValue = "10", required = false) int pageSize,
                                                          @RequestParam(name = "sort_type", defaultValue = "ASC", required = false) Sort.Direction sortType,
                                                          @RequestParam(name = "sort_type_date", defaultValue = "ASC", required = false) Sort.Direction sortTypeDate,
                                                          @RequestParam(name = "search_key", defaultValue = "", required = false) String searchKey)
    {
        return responseUtil.successResponse(blogService.getPageBlog(pageNumber, pageSize, sortType, sortTypeDate, searchKey));
    }
}


