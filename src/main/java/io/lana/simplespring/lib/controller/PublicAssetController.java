package io.lana.simplespring.lib.controller;

import lombok.RequiredArgsConstructor;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.tika.Tika;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/assets")
public class PublicAssetController {
    private final FileSystemManager fs;
    private final Tika tika;

    @GetMapping("{type}/{filename}")
    public void getFile(@PathVariable String type, @PathVariable String filename, HttpServletResponse response) throws FileSystemException {
        var base = fs.resolveFile(type);
        var file = fs.resolveFile(base, filename);
        if (!file.exists()) {
            response.setStatus(404);
            return;
        }

        try (var out = response.getOutputStream(); var in = file.getContent().getInputStream()) {
            in.transferTo(out);
            response.setContentType(tika.detect(in));
        } catch (IOException e) {
            response.setStatus(403);
            return;
        }
        response.setStatus(200);
    }
}
