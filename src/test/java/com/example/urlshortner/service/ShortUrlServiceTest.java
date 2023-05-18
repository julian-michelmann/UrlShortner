package com.example.urlshortner.service;

import com.example.urlshortner.entity.UrlEntity;
import com.example.urlshortner.exceptions.UrlNotFoundException;
import com.example.urlshortner.repository.ShortUrlRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShortUrlServiceTest {

    static final String HOME_URL = "https://www.axelspringer.com/de/";

    ShortUrlRepository shortUrlRepository = mock(ShortUrlRepository.class);
    ShortUrlService shortUrlService = new ShortUrlService(shortUrlRepository);

    @Test
    void findInsteadOfCreatingNewUrl() {
        UrlEntity urlEntity = mock(UrlEntity.class);

        when(shortUrlRepository.findByUrl(HOME_URL)).thenReturn(Optional.of(urlEntity));

        UrlEntity result = shortUrlService.createNewShortenUrl(HOME_URL);

        assertSame(urlEntity, result);
    }

    @Test
    void createNewUrl() {
        UrlEntity urlEntity = mock(UrlEntity.class);

        when(shortUrlRepository.findByUrl(HOME_URL)).thenReturn(Optional.empty());
        when(shortUrlRepository.save(any(UrlEntity.class))).thenReturn(urlEntity);

        UrlEntity result = shortUrlService.createNewShortenUrl(HOME_URL);

        assertSame(urlEntity, result);
    }

    @Test
    void findByUrlShortPart() {
        UrlEntity urlEntity = mock(UrlEntity.class);

        when(shortUrlRepository.findById(10L)).thenReturn(Optional.ofNullable(urlEntity));

        UrlEntity result = shortUrlService.findByUrlShortPart("A");

        assertSame(urlEntity, result);
    }

    @Test
    void canNotFindUrl() {
        when(shortUrlRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(UrlNotFoundException.class, () -> shortUrlService.findByUrlShortPart("A"));
    }
}
