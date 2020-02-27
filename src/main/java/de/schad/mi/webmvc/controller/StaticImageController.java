package de.schad.mi.webmvc.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * StaticImageController serves images at base URL /image
 */
@Controller
@RequestMapping("/image")
public class StaticImageController {

    @Value("${file.upload.directory}")
    private String UPLOADDIR;

    /**
     * downloadImage opens a image with given name file in browser
     * 
     * Request URL: /image/{name}
     * @param name the name of the image to look for
     * @return the image opened in browser
     * @throws IOException if method can't find the resource
     */
    @GetMapping("/{name}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("name") String name) throws IOException {
        Path path = Paths.get(UPLOADDIR, name);
        String mimetype = Files.probeContentType(path);
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
            .headers(null)
            .contentLength(resource.contentLength())
            .contentType(MediaType.parseMediaType(mimetype))
            .body(resource);
    }
}