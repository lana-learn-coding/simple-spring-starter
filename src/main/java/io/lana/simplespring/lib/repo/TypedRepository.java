package io.lana.simplespring.lib.repo;

import io.lana.simplespring.lib.repo.pageable.Page;
import io.lana.simplespring.lib.repo.pageable.Pageable;

import java.util.List;
import java.util.Optional;

public interface TypedRepository<T> extends DynamicRepository {
    void save(T entity);

    Page<T> page(Pageable pageable, String condition, Object... params);

    Page<T> page(Pageable pageable);

    List<T> list(String condition, Object... params);

    List<T> list();

    Optional<T> first(String condition, Object... params);

    void update(T entity, String condition, Object... params);
}
