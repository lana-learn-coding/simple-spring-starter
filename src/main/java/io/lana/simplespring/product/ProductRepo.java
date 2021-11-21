package io.lana.simplespring.product;

import io.lana.simplespring.lib.repo.ModelRepository;
import io.lana.simplespring.lib.repo.TypedRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
class ProductRepo extends ModelRepository<Product> implements TypedRepository<Product> {
    public ProductRepo(JdbcTemplate template) {
        super(Product.class, template);
    }
}
