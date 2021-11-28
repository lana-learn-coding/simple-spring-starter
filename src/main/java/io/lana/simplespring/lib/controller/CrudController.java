package io.lana.simplespring.lib.controller;

import io.lana.simplespring.lib.repo.CrudRepository;
import io.lana.simplespring.lib.repo.pageable.Page;
import io.lana.simplespring.lib.repo.pageable.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class CrudController<T extends Identified> extends AbstractCrudController<T> {
    public CrudController(CrudRepository<T> repo) {
        super(repo);
    }

    @GetMapping
    public String index(final Pageable pageable, final Model model) {
        Page<T> result = repo.page(pageable);
        model.addAttribute("pageable", pageable);
        model.addAttribute("meta", result.getMeta());
        model.addAttribute("data", result.getData());
        return path + "/index";
    }
}
