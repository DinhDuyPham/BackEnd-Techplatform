package com.learn.techplatform.services.Chapter;

import com.learn.techplatform.entities.Chapter;
import com.learn.techplatform.repositories.ChapterRepository;
import com.learn.techplatform.services.AbstractBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ChapterServiceImpl extends AbstractBaseService<Chapter, String> implements ChapterService {
    @Autowired
    ChapterRepository chapterRepository;

    public ChapterServiceImpl(JpaRepository<Chapter, String> genericRepository) {
        super(genericRepository);
    }
}
