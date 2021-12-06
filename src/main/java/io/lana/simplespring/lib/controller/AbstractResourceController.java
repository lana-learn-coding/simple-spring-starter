package io.lana.simplespring.lib.controller;

import io.lana.simplespring.lib.repo.CrudRepository;
import io.lana.simplespring.lib.utils.ModelUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public abstract class AbstractResourceController<T extends Identified> {
    protected final CrudRepository<T> repo;
    protected final String path;
    protected final Class<T> clazz;

    protected AbstractResourceController(CrudRepository<T> repo) {
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

    @GetMapping("delete/{id}")
    public String destroy(@PathVariable final String id) {
        repo.first("id = ?1", id).ifPresent(repo::delete);
        return "redirect:" + path;
    }

    @GetMapping("update/{id}")
    public String edit(@PathVariable final String id, Model model) {
        var item = repo.first("id = ?1", id);
        if (item.isEmpty()) throw new IllegalArgumentException("Id not found: " + id);
        model.addAttribute("entity", item.get());
        return path + "/edit";
    }

    @GetMapping("detail/{id}")
    public String show(@PathVariable final String id, Model model) {
        var item = repo.first("id = ?1", id);
        if (item.isEmpty()) throw new IllegalArgumentException("Id not found: " + id);
        model.addAttribute("entity", item.get());
        return path + "/detail";
    }
}
