package com.example.urlshortner.Controller;

import com.example.urlshortner.Entity.ShortUrl;
import com.example.urlshortner.Service.ShortUrlService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@Tag(name = "Short Url", description = "Read and create shortened urls")
public class ShortUrlController {
    @Autowired
    ShortUrlService shortUrlService;

    @GetMapping("urls")
    public ResponseEntity<Iterable<ShortUrl>> indexUsers() {
        return ResponseEntity.ok().body(shortUrlService.index());
    }

    @GetMapping("urls/{id}")
    public ResponseEntity<ShortUrl> showUser(@PathVariable(value = "id") long id) {
        return ResponseEntity.ok().body(shortUrlService.show(id));
    }

    @GetMapping("{id}")
    public void redirect(HttpServletResponse httpServletResponse, @PathVariable(value = "id") String shortPath) {


        //httpServletResponse.setHeader("Location", projectUrl);
        //httpServletResponse.setStatus(302);
    }

    @PostMapping("urls")
    public ResponseEntity<ShortUrl> createUser(@Validated @RequestBody ShortUrl shortUrl) {
        shortUrl.init();
        return ResponseEntity.ok().body(shortUrlService.create(shortUrl));
    }
}
