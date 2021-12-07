package io.lana.simplespring.lib.controller;

import io.lana.simplespring.lib.repo.CrudRepository;
import io.lana.simplespring.lib.repo.pageable.Page;
import io.lana.simplespring.lib.repo.pageable.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public abstract class AbstractQueryableCrudController<T extends Identified<ID>, ID> extends AbstractCrudController<T, ID> {
    protected AbstractQueryableCrudController(CrudRepository<T> repo) {
        super(repo);
    }

    @GetMapping
    public String index(final Pageable pageable, final Model model, final @RequestParam(name = "search", defaultValue = "") String search) {
        Page<T> result = query(pageable, search);
        model.addAttribute("pageable", pageable);
        model.addAttribute("meta", result.getMeta());
        model.addAttribute("data", result.getData());
        return path + "/index";
    }

    protected abstract Page<T> query(final Pageable pageable, String search);
}
