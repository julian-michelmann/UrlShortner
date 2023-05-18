package com.example.urlshortner.service;

import com.example.urlshortner.entity.UrlEntity;
import com.example.urlshortner.exceptions.UrlNotFoundException;
import com.example.urlshortner.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;

    public ShortUrlService(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    public UrlEntity createNewShortenUrl(String url) {
        Optional<UrlEntity> shortUrlOptional = shortUrlRepository.findByUrl(url);
        UrlEntity result;

        if (shortUrlOptional.isPresent()) {
            result = shortUrlOptional.get();
        } else {
            UrlEntity newUrlEntity = new UrlEntity();
            newUrlEntity.setUrl(url);
            result = shortUrlRepository.save(newUrlEntity);
        }

        return result;
    }

    public UrlEntity findByUrlShortPart(String shortUrlPart) {
        return shortUrlRepository
                .findById(Long.parseLong(shortUrlPart, 36))
                .orElseThrow(UrlNotFoundException::new);
    }
}
