package io.lana.simplespring.lib.repo;


import io.lana.simplespring.lib.repo.pageable.Page;
import io.lana.simplespring.lib.repo.pageable.Pageable;
import io.lana.simplespring.lib.utils.ModelUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Transactional
public class CrudRepositorySkeleton<T> implements CrudRepository<T> {
    private final Class<T> clazz;
    protected final SessionFactory sessionFactory;

    public CrudRepositorySkeleton(SessionFactory factory) {
        this.sessionFactory = factory;
        this.clazz = ModelUtils.getGenericType(getClass());
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<T> list(String condition, Object... params) {
        var session = sessionFactory.getCurrentSession();
        var hql = buildHql(condition);
        Query<T> query = session.createQuery(hql, clazz);
        addQueryParams(query, params);
        return query.list();
    }

    @Override
    public List<T> list() {
        var session = sessionFactory.getCurrentSession();
        var query = session.createQuery("from " + clazz.getName() + " entity", clazz);
        return query.list();
    }

    @Override
    public Optional<T> first(String condition, Object... params) {
        var session = sessionFactory.getCurrentSession();
        var hql = buildHql(condition);
        var query = session.createQuery(hql, clazz);
        addQueryParams(query, params);
        query.setMaxResults(1);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public void save(T entity) {
        var session = sessionFactory.getCurrentSession();
        session.save(entity);
    }

    @Override
    public void upsert(T entity) {
        var session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(entity);
    }

    @Override
    public void update(T entity) {
        var session = sessionFactory.getCurrentSession();
        session.update(entity);
    }

    @Override
    public void flush() {
        var session = sessionFactory.getCurrentSession();
        session.flush();
    }

    @Override
    public void clear() {
        var session = sessionFactory.getCurrentSession();
        session.clear();
    }

    @Override
    public Page<T> page(Pageable pageable, String condition, Object... params) {
        long count = count(condition, params);
        if (count == 0) {
            return Page.empty(pageable);
        }
        if (StringUtils.isNotBlank(pageable.getSort())) condition += " order by " + pageable.getSort();

        var session = sessionFactory.getCurrentSession();
        var hql = buildHql(condition);
        Query<T> query = session.createQuery(hql, clazz);
        addQueryParams(query, params);
        query.setMaxResults(pageable.getSize());
        query.setFirstResult((pageable.getPage() - 1) * pageable.getSize());

        return Page.from(query.list(), pageable, count);
    }

    @Override
    public Page<T> page(Pageable pageable) {
        long count = count();
        if (count == 0) {
            return Page.empty(pageable);
        }

        String condition = "where (1 = 1)";
        if (StringUtils.isNotBlank(pageable.getSort())) condition += " order by " + pageable.getSort();

        var session = sessionFactory.getCurrentSession();
        var hql = buildHql(condition);
        Query<T> query = session.createQuery(hql, clazz);
        query.setMaxResults(pageable.getSize());
        query.setFirstResult((pageable.getPage() - 1) * pageable.getSize());

        return Page.from(query.list(), pageable, count);
    }

    @Override
    public void delete(T entity) {
        var session = sessionFactory.getCurrentSession();
        session.delete(entity);
    }

    @Override
    public long count(String condition, Object... params) {
        var session = sessionFactory.getCurrentSession();
        var hql = buildCount(condition);
        var query = session.createQuery(hql, Long.class);
        query.setMaxResults(1);
        addQueryParams(query, params);
        return Optional.ofNullable(query.getSingleResult()).orElse(0L);
    }


    private String buildCount(String hql) {
        if (StringUtils.startsWith(hql, "select")) {
            hql = hql.replaceFirst("(select).+?(?=from)", "");
        }
        return "select count(*) " + buildHql(hql);
    }

    @Override
    public long count() {
        var session = sessionFactory.getCurrentSession();
        var query = session.createQuery("select count(*) from " + clazz.getSimpleName() + " entity", Long.class);
        query.setMaxResults(1);
        return Optional.ofNullable(query.getSingleResult()).orElse(0L);
    }

    @Override
    public boolean exist(String condition, Object... params) {
        return first(condition, params).isPresent();
    }

    private void addQueryParams(Query<?> query, Object... params) {
        var i = 0;
        for (Object param : params) {
            i++;

            if (param instanceof Collection) {
                query.setParameterList(i, (Collection) param);
                continue;
            }

            query.setParameter(i, param);
        }
    }

    private String buildHql(String hql) {
        if (StringUtils.startsWithAny(hql, "select", "from")) {
            return hql;
        }
        var built = "from " + clazz.getSimpleName() + " " + clazz.getSimpleName().toLowerCase();
        if (StringUtils.isBlank(hql)) return built;
        if (StringUtils.startsWith(hql, "where")) return built + " " + hql;
        return built + " where " + hql;
    }
}
