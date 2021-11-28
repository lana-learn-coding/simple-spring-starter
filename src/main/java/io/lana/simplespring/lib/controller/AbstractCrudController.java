package io.lana.simplespring.lib.controller;

import io.lana.simplespring.lib.repo.CrudRepository;
import io.lana.simplespring.lib.utils.ModelUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

public abstract class AbstractCrudController<T extends Identified> {
    protected final CrudRepository<T> repo;
    protected final String path;
    protected final Class<T> clazz;

    protected AbstractCrudController(CrudRepository<T> repo) {
        this.repo = repo;
        this.clazz = ModelUtils.getGenericType(getClass());

        var paths = getClass().getAnnotation(RequestMapping.class).value();
        if (paths.length != 1) {
            throw new IllegalArgumentException("Cannot initialize controller " + clazz.getName() + ": Invalid number of path specified via @RequestMapping. Required 1, found" + paths.length);
        }
        this.path = paths[0];
    }

    @GetMapping("create")
    public String create(final Model model) {
        model.addAttribute("entity", ModelUtils.construct(clazz));
        return path + "/create";
    }

    @PostMapping("create")
    public String store(@Valid @ModelAttribute("entity") final T entity, final BindingResult result) {
        if (result.hasErrors()) {
            return path + "/create";
        }
        repo.save(entity);
        return "redirect:/" + path;
    }

    @GetMapping("delete/{id}")
    public String destroy(@PathVariable final String id) {
        repo.first("id = ?1", id).ifPresent(repo::delete);
        return "redirect:/" + path;
    }

    @GetMapping("update/{id}")
    public String edit(@PathVariable final String id, Model model) {
        var item = repo.first("id = ?1", id);
        if (item.isEmpty()) throw new IllegalArgumentException("Id not found: " + id);
        model.addAttribute("entity", item.get());
        return path + "/edit";
    }

    @PostMapping("update/{uid}")
    public ModelAndView edit(@PathVariable final String uid, @Valid @ModelAttribute("entity") final T entity, final BindingResult result) {
        entity.setId(uid);
        if (result.hasErrors()) return new ModelAndView("products/edit");
        var item = repo.first("id = ?1", uid);
        if (item.isEmpty()) throw new IllegalArgumentException("Id not found: " + uid);

        repo.update(entity);
        return new ModelAndView("redirect:/" + path + "/detail/{id}", "id", uid);
    }

    @GetMapping("detail/{id}")
    public String show(@PathVariable final String id, Model model) {
        var item = repo.first("id = ?1", id);
        if (item.isEmpty()) throw new IllegalArgumentException("Id not found: " + id);
        model.addAttribute("entity", item.get());
        return path + "/detail";
    }
}
