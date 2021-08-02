package com.example.urlshortner.Repository;

import com.example.urlshortner.Entity.ShortUrl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends CrudRepository<ShortUrl, Long> {
    Optional<ShortUrl> findByLongUrl(String longUrl);

    Optional<ShortUrl> findByShortPath(String shortPath);
}
