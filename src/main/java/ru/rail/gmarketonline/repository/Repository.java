package ru.rail.gmarketonline.repository;


import ru.rail.gmarketonline.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository<K extends Serializable, E extends BaseEntity<K>> {
    E save(E entity);

    void delete(K id);

    Optional<E> findById(K id);

    List<E> findAll();

    void update(E entity);

}
