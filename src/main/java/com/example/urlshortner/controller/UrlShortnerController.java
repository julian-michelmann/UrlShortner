package com.example.urlshortner.controller;

import com.example.urlshortner.entity.UrlEntity;
import com.example.urlshortner.model.CreateResponse;
import com.example.urlshortner.model.UrlInput;
import com.example.urlshortner.service.ShortUrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class UrlShortnerController {
    private final ShortUrlService shortUrlService;

    public UrlShortnerController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping(value = "/urls")
    public ResponseEntity<CreateResponse> createNewShortenUrl(@Valid @RequestBody UrlInput url) {
        String shortUrlPart = shortUrlService
                .createNewShortenUrl(url.getUrl())
                .getShortenUrlPart();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", "/" + shortUrlPart)
                .body(new CreateResponse(shortUrlPart));
    }

    @GetMapping(value = "/{shortenUrlPart}")
    public ResponseEntity<Void> redirect(@PathVariable String shortenUrlPart) {
        UrlEntity urlEntity = shortUrlService.findByUrlShortPart(shortenUrlPart);

        return ResponseEntity
                .status(HttpStatus.PERMANENT_REDIRECT)
                .header("Location", urlEntity.getUrl())
                .build();
    }
}
