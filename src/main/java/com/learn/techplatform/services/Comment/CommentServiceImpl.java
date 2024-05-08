package com.learn.techplatform.services.Comment;

import com.learn.techplatform.common.enums.CommentType;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.dto_modals.CommentDTO;
import com.learn.techplatform.dto_modals.LessonDTO;
import com.learn.techplatform.entities.Comment;
import com.learn.techplatform.entities.Lesson;
import com.learn.techplatform.repositories.CommentRepository;
import com.learn.techplatform.services.AbstractBaseService;
import com.learn.techplatform.services.Lesson.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl extends AbstractBaseService<Comment, String> implements CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    LessonService lessonService;

    public CommentServiceImpl(JpaRepository<Comment, String> genericRepository) {
        super(genericRepository);
    }

    @Override
    public CommentDTO postComment(CommentDTO commentRequest) {
        LessonDTO lesson;
        if(commentRequest.getType() == CommentType.LESSON) {
             lesson = lessonService.getLessonById(commentRequest.getRootId());
             Validator.notNull(lesson, RestAPIStatus.NOT_FOUND, RestStatusMessage.LESSON_NOT_FOUND);
        }
        Comment parentComment;
        if(commentRequest.getParentId() != null && !commentRequest.getContent().isEmpty()) {
            parentComment = commentRepository.findByIdAndSystemStatus(commentRequest.getParentId(), SystemStatus.ACTIVE);
            Validator.notNull(parentComment, RestAPIStatus.NOT_FOUND, RestStatusMessage.NOT_FOUND);
        }
        Comment newComment = Comment.builder()
                .id(UniqueID.getUUID())
                .systemStatus(SystemStatus.ACTIVE)
                .commentType(commentRequest.getType())
                .content(commentRequest.getContent())
                .rootId(commentRequest.getRootId())
                .parentId(commentRequest.getParentId())
                .userId(commentRequest.getUserId())
                .build();
        this.save(newComment);
        return new CommentDTO(newComment);
    }

    @Override
    public List<CommentDTO> getListCommentByRootId(String rootId) {
        List<CommentDTO> list = this.commentRepository.getAllCommentByRootId(rootId);
        List<CommentDTO> result = new ArrayList<>(list.stream().filter(item -> item.getParentId() == null).toList());
        result = result.stream().peek(item -> list.forEach(childItem -> {
            if(childItem.getParentId() != null && childItem.getParentId().equals(item.getId())) {
                if(item.getChildren() == null) {
                    item.setChildren(new ArrayList<>());
                }
                item.getChildren().add(childItem);
            }
        })).toList();
        return result;
    }
}
