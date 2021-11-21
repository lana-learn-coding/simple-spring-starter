package io.lana.simplespring.lib.repo;

import java.util.List;
import java.util.Map;

public interface DynamicRepository {
    List<Map<String, Object>> query(String condition, Object... params);

    List<Map<String, Object>> query();

    void update(Map<String, Object> entity, String condition, Object... params);

    void save(Map<String, Object> entity);

    long count(String condition, Object... params);

    long count();

    boolean exist(String condition, Object... params);

    void delete(String condition, Object... params);
}
