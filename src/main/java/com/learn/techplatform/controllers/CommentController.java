package com.learn.techplatform.controllers;

import com.learn.techplatform.common.constants.ApiPath;
import com.learn.techplatform.common.restfullApi.RestAPIResponse;
import com.learn.techplatform.controllers.models.request.PostCommentRequest;
import com.learn.techplatform.dto_modals.CommentDTO;
import com.learn.techplatform.security.AuthSession;
import com.learn.techplatform.security.AuthUser;
import com.learn.techplatform.services.Comment.CommentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(ApiPath.COMMENT_API)
public class CommentController extends AbstractBaseController {
    @Autowired
    CommentService commentService;

    @PostMapping
    ResponseEntity<RestAPIResponse<Object>> postComment(@Valid @RequestBody PostCommentRequest comment, @AuthSession AuthUser authUser) {
        CommentDTO commentDTO = new CommentDTO(comment, authUser.getId());
        return responseUtil.successResponse(commentService.postComment(commentDTO));
    }


    @GetMapping(ApiPath.ID)
    ResponseEntity<RestAPIResponse<Object>> getAllComment(@PathVariable("id") String rootId) {
        return responseUtil.successResponse(commentService.getListCommentByRootId(rootId));
    }
}
