package com.runtime.url.controllers;

import com.runtime.url.dto.UrlDto;
import com.runtime.url.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("shorten")
    public ResponseEntity<UrlDto> shortenUrl(@RequestBody UrlDto url) {
        String shortenUrl = urlService.shortenUrl(url.getOriginalUrl());
        url.setShortenUrl(shortenUrl);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/{shortenUrl}")
    public RedirectView redirectToOriginalUrl(@PathVariable String shortenUrl) {
        String originalUrl = urlService.getOriginalUrl(shortenUrl);
        return new RedirectView(originalUrl);
    }

}
