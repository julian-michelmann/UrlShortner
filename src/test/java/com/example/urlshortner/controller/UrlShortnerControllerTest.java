package com.example.urlshortner.controller;

import com.example.urlshortner.entity.UrlEntity;
import com.example.urlshortner.model.CreateResponse;
import com.example.urlshortner.model.UrlInput;
import com.example.urlshortner.service.ShortUrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlShortnerControllerTest {

    static final String HOME_URL = "https://www.axelspringer.com/de/";

    UrlShortnerController urlShortnerController;

    ShortUrlService shortUrlService = mock(ShortUrlService.class);
    @MockBean
    ServletUriComponentsBuilder servletUriComponentsBuilder;

    @BeforeEach
    void setUp() {
        urlShortnerController = new UrlShortnerController(shortUrlService);
    }

    @Test
    void testCreateNewShortenUrl() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        UrlInput urlInput = mock(UrlInput.class);
        when(urlInput.getUrl()).thenReturn(HOME_URL);

        UrlEntity urlEntity = mock(UrlEntity.class);
        when(urlEntity.getShortenUrlPart()).thenReturn("1");

        when(shortUrlService.createNewShortenUrl(HOME_URL))
                .thenReturn(urlEntity);

        ResponseEntity<CreateResponse> result = urlShortnerController.createNewShortenUrl(urlInput);
        CreateResponse resultBody = result.getBody();
        HttpHeaders headerResult = result.getHeaders();

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(resultBody);
        assertEquals("1", resultBody.getUniqPathPart());

        assertNotNull(headerResult);
        assertEquals("/1", Objects.requireNonNull(headerResult.getLocation()).toString());
    }
    
    @Test
    void redirectToUrl() {
        UrlEntity urlEntity = mock(UrlEntity.class);
        when(urlEntity.getUrl()).thenReturn(HOME_URL);
        
        when(shortUrlService.findByUrlShortPart("A")).thenReturn(urlEntity);
        
        ResponseEntity<Void> result = urlShortnerController.redirect("A");
        
        HttpHeaders headerResult = result.getHeaders();
        
        assertEquals(HttpStatus.PERMANENT_REDIRECT, result.getStatusCode());
        
        assertNotNull(headerResult);
        assertNotNull(headerResult.getLocation());
        assertEquals(HOME_URL, headerResult.getLocation().toString());
    }

}
