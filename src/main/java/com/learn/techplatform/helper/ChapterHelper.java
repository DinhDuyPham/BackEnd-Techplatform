package com.learn.techplatform.helper;

import com.learn.techplatform.dto_modals.LessonDTO;
import com.learn.techplatform.dto_modals.course.CourseChapterListDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ChapterHelper {
    public List<CourseChapterListDTO> mappingChapterLessonDTO(String courseId, List<CourseChapterListDTO> chapterList, List<LessonDTO> lessonDTOList) {
        var filtered = chapterList.stream().filter(item -> item.getCourseId().equals(courseId)).toList();
        filtered = filtered.stream().peek(item -> {
            var a = this.filterByChapterId(lessonDTOList, item.getId());
            item.setLessons(a);
        }).toList();
        return filtered;
    }

    private List<LessonDTO> filterByChapterId(List<LessonDTO> lessonDTOList, String chapterId) {
        List<LessonDTO> result = new ArrayList<>();
        lessonDTOList.forEach(item -> {
            if(item.getChapterId() != null && item.getChapterId().equals(chapterId)) {
                result.add(item);
            }
        });
        result.sort((item, prev) -> item.getNumericalOrder() - prev.getNumericalOrder());
        return result;
    }
}
