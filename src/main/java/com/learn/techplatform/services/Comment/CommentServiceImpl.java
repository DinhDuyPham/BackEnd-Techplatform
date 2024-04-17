package com.learn.techplatform.services.Comment;

import com.learn.techplatform.entities.Comment;
import com.learn.techplatform.repositories.CommentRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl extends AbstractBaseService<Comment, String> implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    public CommentServiceImpl(JpaRepository<Comment, String> genericRepository) {
        super(genericRepository);
    }
}
