package com.example.urlshortner.entity;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UrlEntityTest {

    @ParameterizedTest
    @CsvSource({
            "5, 5",
            "35, z",
            "100, 2s",
    }
    )
    void testShortendUrlPart(Long id, String expected) {
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setId(id);

        assertEquals(expected, urlEntity.getShortenUrlPart());
    }
}
