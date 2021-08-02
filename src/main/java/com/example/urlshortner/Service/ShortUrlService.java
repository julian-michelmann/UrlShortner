package com.example.urlshortner.Service;

import com.example.urlshortner.Entity.ShortUrl;
import com.example.urlshortner.Repository.ShortUrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShortUrlService {

    @Autowired
    ShortUrlRepository shortUrlRepository;

    public Iterable<ShortUrl> index() {
        return shortUrlRepository.findAll();
    }

    public ShortUrl show(long id) {
        return shortUrlRepository.findById(id)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Short URL not found with id " + id));
    }

    public ShortUrl create(ShortUrl shortUrl) {
        return longUrlExists(shortUrl.getLongUrl()).orElse(createUniqShortUrl(shortUrl));
    }

    private Optional<ShortUrl> longUrlExists(String longUrl) {
        return shortUrlRepository.findByLongUrl(longUrl);
    }

    /*In order to generate urls that are as short as possible, a random character is added for each call.
    If the generated id is not uniq, the class calls itself again.*/
    private ShortUrl createUniqShortUrl(ShortUrl shortUrl) {
        String uniqPath = RandomStringUtils.randomAlphanumeric(1);
        shortUrl.setShortPath(shortUrl.getShortPath() + uniqPath);

        if (isUniqPath(uniqPath)) {
            shortUrlRepository.save(shortUrl);
        } else {
            createUniqShortUrl(shortUrl);
        }

        return shortUrl;
    }

    private boolean isUniqPath(String uniqPath) {
        return shortUrlRepository.findByShortPath(uniqPath).isEmpty();
    }
}
