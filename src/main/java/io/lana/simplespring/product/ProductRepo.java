package io.lana.simplespring.product;

import io.lana.simplespring.lib.repo.CrudRepository;
import io.lana.simplespring.lib.repo.CrudRepositorySkeleton;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
class ProductRepo extends CrudRepositorySkeleton<Product> implements CrudRepository<Product> {
    public ProductRepo(SessionFactory factory) {
        super(factory);
    }
}
