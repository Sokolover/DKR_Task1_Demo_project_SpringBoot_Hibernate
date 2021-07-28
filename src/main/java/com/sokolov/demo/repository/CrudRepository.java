package com.sokolov.demo.repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Sokolov_SA
 * @created 25.06.2021
 */
public interface CrudRepository<T> {

    Long create(T entity);

    Long delete(Long id);

    T update(T entity);

    Optional<T> findById(long id);

    List<T> findAll();

}
