package com.runtime.url.services;

import com.runtime.url.UrlNotFoundException;
import com.runtime.url.model.UrlEntity;
import com.runtime.url.repositories.UrlRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class UrlServiceImplTest {

    @MockBean
    private UrlRepository urlRepository;

    @Autowired
    private UrlService urlService;

    @Test
    void shouldReturnShortenUrlWhenGivenOriginalUrl() {
        String shortenUrl = urlService.shortenUrl("ThisIsAnOriginalUrl");
        Assertions.assertEquals(7, shortenUrl.length());
    }

    @Test
    void shouldReturnShortenUrlWhenSaveThrowsException() {
        when(urlRepository.existsByShortenUrl(anyString())).thenReturn(true).thenReturn(false);
        String shortenUrl = urlService.shortenUrl("ThisIsAnOriginalUrl");
        Assertions.assertEquals(7, shortenUrl.length());
    }

    @Test
    void shouldReturnOriginalUrlWhenGivenExistingShortenUrl() {
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setShortenUrl("shortenUrl");
        urlEntity.setOriginalUrl("ThisIsTheOriginalUrl");
        when(urlRepository.findByShortUrl(urlEntity.getShortenUrl())).thenReturn(Optional.of(urlEntity));

        String originalUrl = urlService.getOriginalUrl(urlEntity.getShortenUrl());
        Assertions.assertEquals(urlEntity.getOriginalUrl(), originalUrl);
    }

    @Test
    void shouldThrowUrlNotFoundExceptionWhenGivenNonExistingShortenUrl() {
        when(urlRepository.findByShortUrl(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UrlNotFoundException.class, () -> urlService.getOriginalUrl("404"));
    }

}