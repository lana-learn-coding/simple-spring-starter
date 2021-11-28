package io.lana.simplespring.product;

import io.lana.simplespring.lib.controller.AbstractQueryableCrudController;
import io.lana.simplespring.lib.repo.CrudRepository;
import io.lana.simplespring.lib.repo.pageable.Page;
import io.lana.simplespring.lib.repo.pageable.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
class ProductController extends AbstractQueryableCrudController<Product> {
    protected ProductController(CrudRepository<Product> repo) {
        super(repo, "products");
    }

    @Override
    protected Page<Product> query(Pageable pageable, String search) {
        return repo.page(pageable, "name like ?1", "%" + search + "%");
    }
}
