package io.lana.simplespring.product;

import io.lana.simplespring.lib.repo.pageable.Page;
import io.lana.simplespring.lib.repo.pageable.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
class ProductController {
    private final ProductRepo repo;

    @GetMapping
    public String index(final Pageable pageable, final Model model, final @RequestParam(name = "search", defaultValue = "") String search) {
        Page<Product> result = repo.page(pageable, "name like ?1", "%" + search + "%");
        model.addAttribute("pageable", pageable);
        model.addAttribute("meta", result.getMeta());
        model.addAttribute("data", result.getData());
        return "products/index";
    }

    @GetMapping("create")
    public String create(final Model model) {
        model.addAttribute("entity", new Product());
        return "products/create";
    }

    @PostMapping("create")
    public String store(@Valid @ModelAttribute("entity") final Product entity, final BindingResult result) {
        if (result.hasErrors()) {
            return "products/create";
        }
        repo.save(entity);
        return "redirect:/products";
    }

    @GetMapping("delete/{id}")
    public String destroy(@PathVariable final String id) {
        repo.first("id = ?1", id).ifPresent(repo::delete);
        return "redirect:/products";
    }

    @GetMapping("update/{id}")
    public String edit(@PathVariable final String id, Model model) {
        var item = repo.first("id = ?1", id);
        if (item.isEmpty()) throw new IllegalArgumentException("Id not found: " + id);
        model.addAttribute("entity", item.get());
        return "products/edit";
    }

    @PostMapping("update/{uid}")
    public ModelAndView edit(@PathVariable final String uid, @Valid @ModelAttribute("entity") final Product entity, final BindingResult result) {
        entity.setId(uid);
        if (result.hasErrors()) return new ModelAndView("products/edit");
        var item = repo.first("id = ?1", uid);
        if (item.isEmpty()) throw new IllegalArgumentException("Id not found: " + uid);

        repo.update(entity);
        return new ModelAndView("redirect:/products/detail/{id}", "id", uid);
    }

    @GetMapping("detail/{id}")
    public String show(@PathVariable final String id, Model model) {
        var item = repo.first("id = ?1", id);
        if (item.isEmpty()) throw new IllegalArgumentException("Id not found: " + id);
        model.addAttribute("entity", item.get());
        return "products/detail";
    }
}
