package com.example.urlshortner.repository;

import com.example.urlshortner.entity.UrlEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortUrlRepository extends CrudRepository<UrlEntity, Long> {
    Optional<UrlEntity> findByUrl(String url);
}
