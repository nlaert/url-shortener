package com.runtime.url.services;

import com.runtime.url.exceptions.UrlNotFoundException;
import com.runtime.url.model.UrlEntity;
import com.runtime.url.repositories.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.InvalidParameterException;

@Service
public class UrlServiceImpl implements UrlService {

    final UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public String shortenUrl(String originalUrl) {
        if (StringUtils.isEmpty(originalUrl)) {
            throw new InvalidParameterException("originalUrl can't be null");
        }
        UrlEntity url = new UrlEntity();
        url.setOriginalUrl(originalUrl);
        url.setShortenUrl(generateNewShortenUrl());
        urlRepository.save(url);
        return url.getShortenUrl();
    }

    @Override
    public String getOriginalUrl(String shortenUrl) {
        return urlRepository.findByShortUrl(shortenUrl)
                .map(UrlEntity::getOriginalUrl)
                .orElseThrow(() -> new UrlNotFoundException(shortenUrl));
    }

    /**
     * Generates a random alphanumeric string and checks if it exists in DB.
     * If it does, tries again until a non existing string is generated.
     * @return a random alphanumeric string.
     */
    private String generateNewShortenUrl() {
        String randomString;
        do {
            randomString = RandomStringUtils.randomAlphanumeric(7);
        } while(urlRepository.existsByShortenUrl(randomString));
        return randomString;
    }
}
