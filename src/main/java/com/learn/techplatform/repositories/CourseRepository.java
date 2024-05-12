package com.learn.techplatform.repositories;

import com.learn.techplatform.common.enums.CourseType;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.dto_modals.CourseDTO;
import com.learn.techplatform.dto_modals.course.CourseDetailInformationDTO;
import com.learn.techplatform.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Course findByIdAndSystemStatus(String id, SystemStatus systemStatus);
    boolean existsByTitle(String title);

    @Query("""
        SELECT c 
        FROM Course c 
        WHERE c.title LIKE :search_key
    """)
    Page<Course> getPageCourse(@Param("search_key") String searchKey, Pageable pageable);


    @Query("""
        select new com.learn.techplatform.dto_modals.CourseDTO(c)
        from Course c
        where c.systemStatus = 'ACTIVE'
    """)
    List<CourseDTO> getCourseDTOList();

    boolean existsBySlug(String slug);

    Course getBySlugAndSystemStatus(String slug, SystemStatus systemStatus);

    @Query("""
        select new com.learn.techplatform.dto_modals.course.CourseDetailInformationDTO(c, coHi)
        from Course c, CourseHistory coHi
        where c.systemStatus = 'ACTIVE' and coHi.systemStatus = 'ACTIVE' and
            c.id = :course_id and coHi.courseId = c.id and coHi.userId = :user_id
    """)
    CourseDetailInformationDTO getCourseDetailByIdAndUserId(@Param("course_id") String courseId,@Param("user_id") String userId);


    Course getByCodeAndSystemStatus(String code, SystemStatus systemStatus);

}
