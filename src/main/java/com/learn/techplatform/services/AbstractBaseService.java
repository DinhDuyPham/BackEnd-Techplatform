package com.learn.techplatform.services;

import com.learn.techplatform.common.utils.AppValueConfigure;
import com.learn.techplatform.entities.AbstractBaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractBaseService<T extends AbstractBaseEntity<ID>, ID extends Serializable> implements InterfaceBaseService<T, ID> {

    @Autowired
    AppValueConfigure valueConfigure;
    @Autowired
    PasswordEncoder passwordEncoder;

    protected JpaRepository<T, ID> genericRepository;

    public AbstractBaseService(JpaRepository<T, ID> genericRepository) {
        this.genericRepository = genericRepository;
    }

    public T save(T t) {
        return this.genericRepository.save(t);
    }

    public List<T> saveAll(List<T> ts) {
        return this.genericRepository.saveAll(ts);
    }

    public void delete(T t) {
        this.genericRepository.delete(t);
    }

    public void deleteAll(List<T> ts) {
        this.genericRepository.deleteAll(ts);
    }

    public T getById(ID id) {
        return this.genericRepository.findById(id).orElse(null);
    }

    public List<T> getAll() {
        return this.genericRepository.findAll();
    }

    public List<T> getAll(Sort sort) {
        return this.genericRepository.findAll(sort);
    }

}
