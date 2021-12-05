package io.lana.simplespring.product;

import io.lana.simplespring.lib.repo.CrudRepository;
import io.lana.simplespring.lib.repo.CrudRepositorySkeleton;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
class CategoryRepo extends CrudRepositorySkeleton<Category> implements CrudRepository<Category> {
    public CategoryRepo(SessionFactory factory) {
        super(factory);
    }
}
