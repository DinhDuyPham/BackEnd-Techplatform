package com.learn.techplatform.repositories;

import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.entities.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
    @Query("""
        select n
        from Notification n
        where n.title = :search_key
    """)
    Page<Notification> getPageNotification(@Param("search_key") String searchKey, Pageable pageable);

    Notification findByIdAndSystemStatus(String id, SystemStatus status);
    boolean existsByIdAndSystemStatus(String id, SystemStatus status);
    boolean existsByTitleAndSystemStatus(String title, SystemStatus status);
}
