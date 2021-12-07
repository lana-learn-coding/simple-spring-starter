package io.lana.simplespring.product;

import io.lana.simplespring.lib.controller.AbstractResourceController;
import io.lana.simplespring.lib.repo.CrudRepository;
import io.lana.simplespring.lib.repo.pageable.Page;
import io.lana.simplespring.lib.repo.pageable.Pageable;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
class ProductController extends AbstractResourceController<Product, String> {
    private final CrudRepository<Category> categoryRepo;
    private final FileSystemManager fs;
    private final FileObject imagesFolder;

    protected ProductController(CrudRepository<Product> repo, CrudRepository<Category> categoryRepo, FileSystemManager fs) throws FileSystemException {
        super(repo);
        this.categoryRepo = categoryRepo;
        this.fs = fs;
        this.imagesFolder = fs.resolveFile("images");
        imagesFolder.createFolder();
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

    @PostMapping("create")
    public String store(@RequestParam("files") final Collection<MultipartFile> files, @Valid @ModelAttribute("entity") final Product entity,
                        final BindingResult result) {
        if (result.hasErrors()) {
            return path + "/create";
        }
        if (repo.exist("id = ?1", entity.getId())) {
            result.addError(new FieldError("entity", "id", entity.getId(), false, null, null, "Id has already been taken"));
            return path + "/create";
        }
        entity.setImages(saveFiles(files));
        repo.save(entity);
        return "redirect:" + path;
    }

    @PostMapping("update/{uid}")
    public ModelAndView update(@PathVariable final String uid, @RequestParam("files") final Collection<MultipartFile> files, @Valid @ModelAttribute("entity") final Product entity,
                               final BindingResult result) {
        entity.setId(uid);
        if (result.hasErrors()) return new ModelAndView("products/edit");
        var item = repo.first("id = ?1", uid);
        if (item.isEmpty()) throw new IllegalArgumentException("Id not found: " + uid);

        entity.setImages(saveFiles(files));
        repo.update(entity);
        return new ModelAndView("redirect:" + path + "/detail/{id}", "id", uid);
    }

    @ModelAttribute("categories")
    private List<Category> categories() {
        return categoryRepo.list();
    }

    private String saveFiles(final Collection<MultipartFile> files) {
        return files.stream()
            .map(file -> {
                var filename = UUID.randomUUID().toString();
                try {
                    var savedFile = fs.resolveFile(imagesFolder, filename);
                    savedFile.createFile();
                    try (var out = savedFile.getContent().getOutputStream(); var in = file.getInputStream()) {
                        in.transferTo(out);
                    }
                    return "/assets/" + imagesFolder.getName().getBaseName() + "/" + savedFile.getName().getBaseName();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            })
            .filter(Objects::nonNull)
            .collect(Collectors.joining(","));
    }
}
