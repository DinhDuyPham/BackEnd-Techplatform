package com.learn.techplatform.repositories;

import com.learn.techplatform.common.enums.LessonStatus;
import com.learn.techplatform.common.enums.LessonType;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.controllers.models.response.course_response.LessonDetailResponse;
import com.learn.techplatform.dto_modals.LessonDTO;
import com.learn.techplatform.entities.Course;
import com.learn.techplatform.entities.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, String> {
    boolean existsByTitle(String title);
    @Query("""
        SELECT l
        FROM Lesson l 
        WHERE l.title LIKE :search_key
    """)
    Page<Lesson> getPageLesson(@Param("search_key") String searchKey, Pageable pageable);
    LessonDTO getLessonByIdAndLessonTypeAndSystemStatus(String id, LessonType lessonType, SystemStatus status);
    Lesson findLessonByIdAndSystemStatus(String id, SystemStatus status);
    @Query("""
        select NEW com.learn.techplatform.controllers.models.response.course_response.LessonDetailResponse(l)
        from Lesson l, Chapter ch, Course co
        where l.chapterId = ch.id and ch.courseId = co.id and co.id = :course_id and l.systemStatus = 'ACTIVE'
    """)
    List<LessonDetailResponse> getLessonsByCourseIdAndSystemStatus(@Param("course_id") String courseId);

    @Query("""
        select new com.learn.techplatform.dto_modals.LessonDTO(le, :is_expland)
        from Lesson le, Chapter ch
        where le.chapterId = ch.id and 
            ch.courseId = :course_id and 
            ch.systemStatus = 'ACTIVE' and
            le.systemStatus = 'ACTIVE'
   """)
    List<LessonDTO> getByCourseId(@Param("course_id") String courseId, @Param("is_expland") boolean isExpland);

    @Query("""
        select new com.learn.techplatform.dto_modals.LessonDTO(l, true)
        from Lesson l
        where l.id = :id and l.systemStatus = 'ACTIVE'
    """)
    LessonDTO getDTOById(@Param("id") String id);


    Lesson getByNumericalOrderAndSystemStatus(int numericalOrder, SystemStatus status);
    Lesson getByChapterIdAndNumericalOrderAndSystemStatus(String chapterId, int numericalOrder, SystemStatus systemStatus);

    @Query("""
        select l
        from Lesson l, Course c, Chapter ch
        where l.chapterId = ch.id and l.systemStatus = 'ACTIVE' and
            ch.courseId = c.id and ch.systemStatus = 'ACTIVE' and
            c.systemStatus = 'ACTIVE' and
            c.id = :course_id and l.numericalOrder = :lesson_number
    """)
    Lesson getLessonByCourseIdAndLessonNumber(@Param("course_id") String courseId, @Param("lesson_number") int lessonNumber);

    @Query("""
        select nextLess
        from Lesson nextLess, Chapter nextCh, Course nextC,
        	(
        		select currC.id as courseId, currL.numericalOrder as numberLesson
                from Lesson currL, Course currC, Chapter currCh
                where currL.id = :id and
        				currL.chapterId = currCh.id and
                        currCh.courseId =  currC.id and
                        currL.systemStatus = 'ACTIVE' and
                        currCh.systemStatus = 'ACTIVE' and
                        currC.systemStatus = 'ACTIVE'\s
        	) as currentLesson
        where nextLess.numericalOrder = currentLesson.numberLesson + 1 and
        	nextLess.chapterId = nextCh.id and
            nextCh.courseId = nextC.id and
            currentLesson.courseId = nextC.id and
            nextLess.systemStatus = 'ACTIVE' and
        	nextCh.systemStatus = 'ACTIVE' and
        	nextC.systemStatus = 'ACTIVE'\s
    """)
    Lesson getNextLessonByCurrentLessonId(@Param("id") String id);
}
