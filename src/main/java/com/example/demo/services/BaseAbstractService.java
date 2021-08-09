package com.example.demo.services;

import com.example.demo.repository.BaseRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public abstract class BaseAbstractService<T> implements BaseService<T>{
    protected abstract BaseRepository<T> getEntityRepository();

    @Override
    public List<T> findAll() {
        return getEntityRepository().findAll();
    }

    @Override
    public Optional<T> findById(Long id) {
        return getEntityRepository().findById(id);
    }

    @Override
    public T save(T entity) {
        return getEntityRepository().save(entity);
    }

    @Override
    public void delete(T entity) {
        getEntityRepository().delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        getEntityRepository().deleteById(id);
    }

    @Override
    public void saveAll(List<T> entities) {
        for (T entity: entities) {
            save(entity);
        }
    }
}
