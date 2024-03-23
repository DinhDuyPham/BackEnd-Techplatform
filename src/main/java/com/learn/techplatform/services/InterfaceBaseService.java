package com.learn.techplatform.services;

import com.learn.techplatform.entities.AbstractBaseEntity;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public interface InterfaceBaseService<T extends AbstractBaseEntity<ID>, ID extends Serializable> {
    T save(T t);
    List<T> saveAll(List<T> ts);
    void delete(T t);
    void deleteAll(List<T> ts);
    T getById(ID id);
    List<T> getAll();
    List<T> getAll(Sort sort);
}
