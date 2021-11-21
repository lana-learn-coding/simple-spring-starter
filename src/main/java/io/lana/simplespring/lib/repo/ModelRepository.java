package io.lana.simplespring.lib.repo;


import io.lana.simplespring.lib.repo.pageable.Page;
import io.lana.simplespring.lib.repo.pageable.Pageable;
import io.lana.simplespring.lib.utils.CaseUtils;
import io.lana.simplespring.lib.utils.ModelUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelRepository<T> extends BaseRepository implements TypedRepository<T> {
    private final Class<T> clazz;

    public ModelRepository(Class<T> clazz, JdbcTemplate template) {
        super(CaseUtils.toSnakeCase(clazz.getSimpleName()), template);
        this.clazz = clazz;
    }

    @Override
    public List<T> list(String condition, Object... params) {
        String sql = buildSql("SELECT *", condition);
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(clazz), params);
    }

    @Override
    public List<T> list() {
        String sql = "SELECT * FROM " + getTableName();
        return getJdbcTemplate().query(sql, new BeanPropertyRowMapper<>(clazz));
    }

    @Override
    public Optional<T> first(String condition, Object... params) {
        String sql = buildSql("SELECT *", condition + " LIMIT 1");
        return Optional.ofNullable(getJdbcTemplate().queryForObject(sql, new BeanPropertyRowMapper<>(clazz), params));
    }

    @Override
    public void save(T entity) {
        List<Object> fieldValues = ModelUtils.getPropertiesValue(entity);
        String paramPlaceholder = fieldValues.stream().map(val -> "?").collect(Collectors.joining(", "));
        String fieldNames = String.join(", ", ModelUtils.getPropertiesName(entity));

        String sql = "INSERT INTO " + getTableName() + "(" + fieldNames + ") VALUES (" + paramPlaceholder + ")";
        getJdbcTemplate().update(sql, fieldValues.toArray());
    }

    @Override
    public Page<T> page(Pageable pageable, String condition, Object... params) {
        long count = count(condition, params);
        if (count == 0) {
            return Page.empty(pageable);
        }
        if (StringUtils.isNotBlank(pageable.getSort())) condition += " ORDER BY " + pageable.getSort();

        long limit = pageable.getSize();
        long offset = (long) (pageable.getPage() - 1) * pageable.getSize();
        Object[] paramsWithOffset = Stream.concat(Arrays.stream(params), Stream.of(limit, offset)).toArray();
        condition += " LIMIT ? OFFSET ?";
        List<T> result = getJdbcTemplate().query(buildSql("SELECT *", condition), new BeanPropertyRowMapper<>(clazz), paramsWithOffset);
        return Page.from(result, pageable, count);
    }

    @Override
    public Page<T> page(Pageable pageable) {
        long count = count();
        if (count == 0) {
            return Page.empty(pageable);
        }

        long limit = pageable.getSize();
        long offset = (long) (pageable.getPage() - 1) * pageable.getSize();
        String condition = "WHERE 1 = 1";
        if (StringUtils.isNotBlank(pageable.getSort())) condition += " ORDER BY " + pageable.getSort();
        condition += " LIMIT ? OFFSET ?";
        List<T> result = getJdbcTemplate().query(buildSql("SELECT *", condition), new BeanPropertyRowMapper<>(clazz), limit, offset);
        return Page.from(result, pageable, count);
    }

    @Override
    public void update(T entity, String condition, Object... params) {
        List<Object> fieldValueParams = ModelUtils.getPropertiesValue(entity);
        fieldValueParams.addAll(Arrays.asList(params));
        String sql = buildSql("UPDATE " + getTableName() + " SET " + getColumnsUpdateMapping(entity), condition);
        getJdbcTemplate().update(sql, fieldValueParams.toArray());
    }

    private String getColumnsUpdateMapping(T object) {
        return ModelUtils.getPropertiesName(object).stream()
            .map(name -> name + "=?")
            .collect(Collectors.joining(","));
    }
}
