package io.lana.simplespring.lib.repo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BaseRepository extends JdbcDaoSupport implements DynamicRepository {
    private final String tableName;

    public BaseRepository(String tableName, JdbcTemplate template) {
        setJdbcTemplate(template);
        this.tableName = tableName;
    }

    protected String getTableName() {
        return tableName;
    }

    @Override
    public List<Map<String, Object>> query(String condition, Object... params) {
        String sql = buildSql("SELECT *", condition);
        return getJdbcTemplate().queryForList(sql, params);
    }

    @Override
    public List<Map<String, Object>> query() {
        String sql = "SELECT * FROM " + getTableName();
        return getJdbcTemplate().queryForList(sql);
    }

    @Override
    public void update(Map<String, Object> entity, String condition, Object... params) {
        List<Object> fieldValueParams = new ArrayList<>(entity.values());
        fieldValueParams.addAll(Arrays.asList(params));
        String columnsUpdateMapping = entity.keySet().stream().map(name -> name + "=?").collect(Collectors.joining(","));
        String sql = buildSql("UPDATE " + getTableName() + " SET " + columnsUpdateMapping, condition);
        getJdbcTemplate().update(sql, fieldValueParams.toArray());
    }

    @Override
    public void save(Map<String, Object> entity) {
        List<Object> fieldValues = new ArrayList<>(entity.values());
        String paramPlaceholder = fieldValues.stream().map(val -> "?").collect(Collectors.joining(", "));
        String fieldNames = String.join(", ", entity.keySet());
        String sql = "INSERT INTO " + getTableName() + "(" + fieldNames + ") VALUES (" + paramPlaceholder + ")";
        getJdbcTemplate().update(sql, fieldValues.toArray());
    }

    @Override
    public void delete(String condition, Object... params) {
        String sql = buildSql("DELETE", condition);
        getJdbcTemplate().update(sql, params);
    }

    @Override
    public long count(String condition, Object... params) {
        String sql = buildSql("SELECT count(*)", condition);
        return getJdbcTemplate().queryForObject(sql, Integer.class, params);
    }

    @Override
    public long count() {
        String sql = "SELECT count(*) FROM " + getTableName();
        return getJdbcTemplate().queryForObject(sql, Integer.class);
    }

    @Override
    public boolean exist(String condition, Object... params) {
        String sql = buildSql("SELECT 1", condition);
        var result = getJdbcTemplate().queryForMap(sql, params);
        return !result.isEmpty();
    }

    protected String buildSql(String select, String where) {
        if (select.contains("*")) select = select.replace("*", getTableName() + ".*");

        StringBuilder sql = new StringBuilder(select);
        if (!select.contains("UPDATE")) sql.append(" FROM ").append(getTableName());
        if (StringUtils.isEmpty(where)) return sql.toString();

        sql.append(" ");
        if (!where.contains("WHERE")) sql.append("WHERE ");
        sql.append(where);
        return sql.toString();
    }
}
