package com.learn.techplatform.services.Lesson;

import com.learn.techplatform.common.enums.LessonStatus;
import com.learn.techplatform.common.enums.LessonType;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.StringUtils;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.controllers.models.response.PagingResponse;
import com.learn.techplatform.dto_modals.LessonDTO;
import com.learn.techplatform.entities.CourseHistory;
import com.learn.techplatform.entities.Lesson;
import com.learn.techplatform.entities.LessonQuestion;
import com.learn.techplatform.repositories.LessonRepository;
import com.learn.techplatform.security.AuthUser;
import com.learn.techplatform.services.AbstractBaseService;
import com.learn.techplatform.services.CourseHistory.CourseHistoryService;
import com.learn.techplatform.services.LessonQuestion.LessonQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonServiceImpl extends AbstractBaseService<Lesson, String> implements LessonService {
    @Autowired
    LessonRepository lessonRepository;
    @Autowired
    LessonQuestionService lessonQuestionService;

    @Autowired
    CourseHistoryService courseHistoryService;

    public LessonServiceImpl(JpaRepository<Lesson, String> genericRepository) {
        super(genericRepository);
    }

    @Override
    public PagingResponse getPageLesson(int pageNumber, int pageSize, Sort.Direction sortType, Sort.Direction sortTypeDate, String searchKey) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(sortType, "title"));
        orders.add(new Sort.Order(sortTypeDate, "updatedDate"));
        orders.add(new Sort.Order(sortTypeDate, "createdDate"));
        Sort sort = Sort.by(orders);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        PagingResponse pagingResponse = new PagingResponse(lessonRepository.getPageLesson("%" + searchKey + "%", pageable));
        return pagingResponse;
    }

    @Override
    public LessonDTO getLessonById(String id, String userId) {
        LessonDTO lessonDTO = lessonRepository.getDTOById(id);
        if(lessonDTO.getLessonType() == LessonType.QUESTION) {
            List<LessonQuestion> answers = this.lessonQuestionService.getByLessonId(lessonDTO.getId());
            lessonDTO.setAnswers(answers);
        }
        return lessonDTO;
    }

    @Override
    public void createVideoLesson(LessonDTO lessonDTO) {
        boolean isLessonExist = lessonRepository.existsByTitle(lessonDTO.getTitle());
        Validator.mustTrue(!isLessonExist, RestAPIStatus.EXISTED, RestStatusMessage.LESSON_ALREADY_EXISTED);

        Validator.notNullAndNotEmpty(lessonDTO.getTitle(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_TITLE_FORMAT);
        Validator.notNullAndNotEmpty(lessonDTO.getThumbnailUrl(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_URL_FORMAT);
        Validator.notNullAndNotEmpty(lessonDTO.getContent(), RestAPIStatus.BAD_REQUEST, RestStatusMessage.INVALID_DESCRIPTION_FORMAT);

        Lesson lesson = Lesson.builder()
                .id(UniqueID.getUUID())
                .title(lessonDTO.getTitle())
                .thumbnailUrl(lessonDTO.getThumbnailUrl())
                .content(lessonDTO.getContent())
                .slug(StringUtils.slugify(lessonDTO.getTitle()))
                .lessonType(lessonDTO.getLessonType())
                .systemStatus(SystemStatus.ACTIVE)
                .build();

        this.save(lesson);
    }

    @Override
    public void editLesson(String id, LessonDTO lessonDTO) {
        Lesson lesson = lessonRepository.findLessonByIdAndSystemStatus(id, SystemStatus.ACTIVE);
        Validator.notNullAndNotEmpty(lesson, RestAPIStatus.NOT_FOUND, RestStatusMessage.LESSON_NOT_FOUND);

        boolean isTitleExist = lessonRepository.existsByTitle(lessonDTO.getTitle());
        Validator.mustTrue(!isTitleExist, RestAPIStatus.EXISTED, RestStatusMessage.LESSON_ALREADY_EXISTED);
        if (Validator.checkNull(lessonDTO.getTitle()))
            lesson.setTitle(lesson.getTitle());
        else lesson.setTitle(lessonDTO.getTitle());
        lesson.setSlug(StringUtils.slugify(lessonDTO.getTitle()));

        if (Validator.checkNull(lessonDTO.getThumbnailUrl()))
            lesson.setThumbnailUrl(lesson.getThumbnailUrl());
        else lesson.setThumbnailUrl(lessonDTO.getThumbnailUrl());

        if (Validator.checkNull(lessonDTO.getDuration()))
            lesson.setDuration(lesson.getDuration());

        if (Validator.checkNull(lessonDTO.getContent()))
            lesson.setContent(lesson.getContent());
        else lesson.setContent(lessonDTO.getContent());

        if (Validator.checkNull(lessonDTO.getLessonType()))
            lesson.setLessonType(lesson.getLessonType());
        else lesson.setLessonType(lessonDTO.getLessonType());

        if (Validator.checkNull(lessonDTO.getQuestion()))
            lesson.setQuestion(lesson.getQuestion());
        else lesson.setQuestion(lessonDTO.getQuestion());

        if (Validator.checkNull(lessonDTO.getNumericalOrder()))
            lesson.setNumericalOrder(lesson.getNumericalOrder());
        else lesson.setNumericalOrder(lessonDTO.getNumericalOrder());

        if (Validator.checkNull(lessonDTO.getLessonType()))
            lesson.setChapterId(lesson.getChapterId());
        else lesson.setChapterId(lessonDTO.getChapterId());

        if (Validator.checkNull(lessonDTO.getLessonType()))
            lesson.setVideoId(lesson.getVideoId());
        else lesson.setVideoId(lessonDTO.getVideoId());

        this.save(lesson);
    }

    @Override
    public void deleteLesson(String id) {
        Lesson lesson = lessonRepository.findLessonByIdAndSystemStatus(id, SystemStatus.ACTIVE);
        Validator.notNullAndNotEmpty(lesson, RestAPIStatus.NOT_FOUND, RestStatusMessage.LESSON_NOT_FOUND);
        lesson.setSystemStatus(SystemStatus.INACTIVE);
        this.save(lesson);
    }

    @Override
    public LessonDTO nextLesson(String currentLessonId) {
        Lesson currentLesson = lessonRepository.findLessonByIdAndSystemStatus(currentLessonId, SystemStatus.ACTIVE);
        Validator.notNull(currentLesson, RestAPIStatus.NOT_FOUND, RestStatusMessage.LESSON_NOT_FOUND);
        Validator.mustTrue(currentLesson.getLessonStatus() == LessonStatus.UNLOCKED, RestAPIStatus.FORBIDDEN, RestStatusMessage.FORBIDDEN_ACCESS_DENIED );
        currentLesson.setLessonStatus(LessonStatus.DONE);
        this.save(currentLesson);
        Lesson nextLesson = lessonRepository.getByNumericalOrderAndSystemStatus(currentLesson.getNumericalOrder() + 1, SystemStatus.ACTIVE);
        Validator.notNull(nextLesson, RestAPIStatus.NOT_FOUND, RestStatusMessage.NEXT_LESSON_NOT_FOUND);
        nextLesson.setLessonStatus(LessonStatus.UNLOCKED);
        this.save(nextLesson);
        return new LessonDTO(nextLesson, true);
    }
}
