package com.learn.techplatform.services.Comment;

import com.learn.techplatform.dto_modals.CommentDTO;
import com.learn.techplatform.entities.Comment;
import com.learn.techplatform.services.InterfaceBaseService;

import java.util.List;

public interface CommentService extends InterfaceBaseService<Comment, String> {

    CommentDTO postComment(CommentDTO comment);


    List<CommentDTO> getListCommentByRootId(String rootId);
}
