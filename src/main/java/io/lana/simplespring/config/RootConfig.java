package io.lana.simplespring.config;

import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("io.lana")
public class RootConfig {
    @Bean
    public FileSystemManager fileSystemManager() throws FileSystemException {
        var fs = (DefaultFileSystemManager) VFS.getManager();
        fs.setBaseFile(fs.resolveFile("file:///D:/tmp/ss"));
        return fs;
    }

    @Bean
    public Tika mineTypeDetector() {
        return new Tika();
    }
}
