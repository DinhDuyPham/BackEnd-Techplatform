package com.learn.techplatform.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.learn.techplatform.common.constants.Constant;
import com.learn.techplatform.common.enums.SystemStatus;
import com.learn.techplatform.common.utils.DateUtil;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@MappedSuperclass
@SuperBuilder()
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners(AuditingEntityListener .class)
public abstract class AbstractBaseEntity<ID extends Serializable> {

    @Id
    @Column(name = "id", nullable = false, length = 32, updatable = false)
    private ID id;

    @Column(name = "system_status", length = 50)
    @Enumerated(EnumType.STRING)
    private SystemStatus systemStatus;

    @CreatedDate
    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.API_FORMAT_DATE)
    protected Date createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.API_FORMAT_DATE)
    protected Date updatedDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = DateUtil.convertToUTC(new Date());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = DateUtil.convertToUTC(new Date());
    }
}