package com.runtime.url.controllers;

import com.runtime.url.dto.UrlDto;
import com.runtime.url.services.UrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UrlController {

    private final UrlService urlService;

    @Autowired
    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * Saves the given original url on the DB and returns an shorten url that will redirect to the original url.
     * @param url {@link UrlDto} that should have the originalUrl field filled with the url to redirect to later.
     * @param request helper object to be able to get the hostname in order to build the complete url.
     * @return {@link UrlDto} with both the original and the shorten Url filled.
     */
    @PostMapping("shorten")
    public ResponseEntity<UrlDto> shortenUrl(@RequestBody UrlDto url, HttpServletRequest request) {
        String shortenUrl = urlService.shortenUrl(url.getOriginalUrl());
        StringBuffer hostUrl = request.getRequestURL();
        String host = hostUrl.substring(0, hostUrl.indexOf("shorten"));
        // In here we build the complete url, and add the "1" needed on the redirectToOriginalUrl method.
        url.setShortenUrl(host + 1 + shortenUrl);
        return ResponseEntity.ok(url);
    }

    /**
     * If the given shortenUrl exists, redirects the call to the original url.
     * Note: The "1" before the path variable is a way to differentiate this controller mapping from the index.html
     * @param shortenUrl the shorten url to search for an original url.
     * @return HttpStatus.FOUND with Location to the original url or HttpStatus.NOT_FOUND if not found.
     */
    @GetMapping("/1{shortenUrl}")
    public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortenUrl) {
        String originalUrl = null;
        try {
            originalUrl = urlService.getOriginalUrl(shortenUrl);
        } catch (Exception ex) {
            ResponseEntity.notFound();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, originalUrl);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
