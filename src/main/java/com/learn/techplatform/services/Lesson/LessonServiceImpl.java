package com.learn.techplatform.services.Lesson;

import com.learn.techplatform.common.enums.LessonType;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.restfullApi.RestAPIStatus;
import com.learn.techplatform.common.restfullApi.RestStatusMessage;
import com.learn.techplatform.common.utils.StringUtils;
import com.learn.techplatform.common.utils.UniqueID;
import com.learn.techplatform.common.validations.Validator;
import com.learn.techplatform.controllers.models.response.PagingResponse;
import com.learn.techplatform.dto_modals.LessonDTO;
import com.learn.techplatform.entities.Lesson;
import com.learn.techplatform.repositories.LessonRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonServiceImpl extends AbstractBaseService<Lesson, String> implements LessonService{
    @Autowired
    LessonRepository lessonRepository;

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
    public LessonDTO getVideoLessonById(String id) {
        return lessonRepository.getLessonByIdAndLessonTypeAndSystemStatus(id, LessonType.VIDEO, SystemStatus.ACTIVE);
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
}
