package io.lana.simplespring.product;

import io.lana.simplespring.lib.repo.CrudRepositorySkeleton;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
class ProductRepoImpl extends CrudRepositorySkeleton<Product> implements ProductRepo {
    public ProductRepoImpl(SessionFactory factory) {
        super(factory);
    }
}
