package com.runtime.url.services;

import com.runtime.url.UrlNotFoundException;
import com.runtime.url.model.UrlEntity;
import com.runtime.url.repositories.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {

    final UrlRepository urlRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public String shortenUrl(String originalUrl) {
        UrlEntity url = new UrlEntity();
        url.setOriginalUrl(originalUrl);
        url.setShortenUrl(RandomStringUtils.randomAlphanumeric(7));

        //TODO: validate or catch error when repeated shortenUrl
        urlRepository.save(url);
        return url.getShortenUrl();
    }

    @Override
    public String getOriginalUrl(String shortenUrl) {
        return urlRepository.findByShortUrl(shortenUrl)
                .map(UrlEntity::getOriginalUrl)
                .orElseThrow(() -> new UrlNotFoundException(shortenUrl));

    }
}
