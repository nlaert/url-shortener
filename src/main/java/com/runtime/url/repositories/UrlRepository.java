package com.runtime.url.repositories;

import com.runtime.url.model.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    @Query("select u from UrlEntity u where u.shortenUrl = :shortenUrl ")
    Optional<UrlEntity> findByShortUrl(@Param("shortenUrl") String shortenUrl);

    boolean existsByShortenUrl(String shortenUrl);
}
