package com.learn.techplatform.repositories;

import com.learn.techplatform.controllers.models.response.course_response.ChapterDetailResponse;
import com.learn.techplatform.controllers.models.response.course_response.LessonDetailResponse;
import com.learn.techplatform.dto_modals.course.CourseChapterListDTO;
import com.learn.techplatform.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, String> {
    @Query("""
        select NEW com.learn.techplatform.controllers.models.response.course_response.ChapterDetailResponse(ch)
        from Chapter ch, Lesson l, Course co
        where ch.id = l.chapterId and ch.courseId = co.id and co.id = :course_id and l.systemStatus = 'ACTIVE'
    """)
    List<ChapterDetailResponse> getChaptersByCourseId(@Param("course_id") String courseId);

    @Query("""
        select NEW com.learn.techplatform.dto_modals.course.CourseChapterListDTO(ch)
        from Chapter ch, Course co
        where ch.courseId = co.id and ch.systemStatus = 'ACTIVE' and co.systemStatus = 'ACTIVE'
        order by ch.numericalOrder
    """)
    List<CourseChapterListDTO> getCourseChapterListByCourseId(@Param("course_id") String courseId);
}
