package com.learn.techplatform.repositories;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.dto_modals.CommentDTO;
import com.learn.techplatform.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    Comment findByIdAndSystemStatus(String id, SystemStatus systemStatus);

    @Query("""
        select new com.learn.techplatform.dto_modals.CommentDTO(c, u)
        from Comment c, User u
        where c.rootId = :root_id and
            c.systemStatus = 'ACTIVE' and
            u.id = c.userId and
            u.systemStatus = 'ACTIVE'
        order by c.createdDate   desc
    """)
    List<CommentDTO> getAllCommentByRootId(@Param("root_id") String rootId);

}
