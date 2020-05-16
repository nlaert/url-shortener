package com.runtime.url.repositories;

import com.runtime.url.model.UrlEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@DataJpaTest
class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;

    @BeforeEach
    void setUp() {
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl("pretendThisIsAnUrl");
        urlEntity.setShortenUrl("shortUrl");
        urlRepository.save(urlEntity);
    }

    @Test
    void shouldReturnUrlEntityWhenGivenExistingUrl() {
        Optional<UrlEntity> urlEntity = urlRepository.findByShortUrl("shortUrl");
        Assertions.assertEquals("pretendThisIsAnUrl", urlEntity.get().getOriginalUrl());
    }

    @Test
    void shouldReturnEmptyOptionalWhenGivenNonExistingUrl() {
        Optional<UrlEntity> urlEntity = urlRepository.findByShortUrl("NonExistingUrl");
        Assertions.assertFalse(urlEntity.isPresent());
    }

    @Test
    void shouldThrowErrorWhenTryingToSaveAlreadyExistingShortUrl() {
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl("pretendThisIsAnotherUrl");
        urlEntity.setShortenUrl("shortUrl");
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> urlRepository.saveAndFlush(urlEntity));
    }
}