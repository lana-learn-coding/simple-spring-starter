package io.lana.simplespring.lib.controller;

import io.lana.simplespring.lib.repo.CrudRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

public abstract class AbstractCrudController<T extends Identified<ID>, ID> extends AbstractResourceController<T, ID> {
    protected AbstractCrudController(CrudRepository<T> repo) {
        super(repo);
    }

    @PostMapping("create")
    public String store(@Valid @ModelAttribute("entity") final T entity, final BindingResult result) {
        if (result.hasErrors()) {
            return path + "/create";
        }
        if (repo.exist("id = ?1", entity.getId())) {
            result.addError(new FieldError("entity", "id", entity.getId(), false, null, null, "Id has already been taken"));
            return path + "/create";
        }
        repo.save(entity);
        return "redirect:" + path;
    }

    @PostMapping("update/{uid}")
    public ModelAndView update(@PathVariable final ID uid, @Valid @ModelAttribute("entity") final T entity, final BindingResult result) {
        entity.setId(uid);
        if (result.hasErrors()) return new ModelAndView("products/edit");
        var item = repo.first("id = ?1", uid);
        if (item.isEmpty()) throw new IllegalArgumentException("Id not found: " + uid);

        repo.update(entity);
        return new ModelAndView("redirect:" + path + "/detail/{id}", "id", uid);
    }
}
