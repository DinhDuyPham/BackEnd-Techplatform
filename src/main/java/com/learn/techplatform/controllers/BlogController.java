package com.learn.techplatform.controllers;

import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.dto_modals.BlogDTO;
import com.learn.techplatform.security.AuthSession;
import com.learn.techplatform.security.AuthUser;
import com.learn.techplatform.services.Blog.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ApiPath.BLOG_API)
public class BlogController extends AbstractBaseController{
    @Autowired
    BlogService blogService;

    @PostMapping(ApiPath.ADD)
    @Operation(summary = "Create blog")
    ResponseEntity<RestAPIResponse<Object>> createBlog(@RequestBody BlogDTO blogDTO, @Parameter(hidden = true) @AuthSession() AuthUser user) {
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
    ResponseEntity<RestAPIResponse<Object>> editBlog(@PathVariable("id") String id,@RequestBody BlogDTO blogDTO, @Parameter(hidden = true) @AuthSession() AuthUser user) {
        blogService.editBlog(id,blogDTO);
        return responseUtil.successResponse("OK!");
    }
}


