package io.lana.simplespring.product;

import io.lana.simplespring.lib.controller.AbstractCrudController;
import io.lana.simplespring.lib.repo.CrudRepository;
import io.lana.simplespring.lib.repo.pageable.Page;
import io.lana.simplespring.lib.repo.pageable.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/products")
class ProductController extends AbstractCrudController<Product> {
    private final CrudRepository<Category> categoryRepo;

    protected ProductController(CrudRepository<Product> repo, CrudRepository<Category> categoryRepo) {
        super(repo);
        this.categoryRepo = categoryRepo;
    }

    @GetMapping
    public String index(final Pageable pageable, final Model model, final @RequestParam(name = "search", defaultValue = "") String search,
                        final @RequestParam(name = "category", defaultValue = "") String category) {
        Page<Product> result = repo.page(pageable, "name like ?1 and category.id like ?2", "%" + search + "%", "%" + category + "%");
        model.addAttribute("pageable", pageable);
        model.addAttribute("meta", result.getMeta());
        model.addAttribute("data", result.getData());
        return "/products/index";
    }

    @ModelAttribute("categories")
    private List<Category> categories() {
        return categoryRepo.list();
    }
}
