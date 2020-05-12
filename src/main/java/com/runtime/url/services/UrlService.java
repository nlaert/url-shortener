package com.runtime.url.services;

public interface UrlService {

    /**
     * Generates a random String, stores it in DB with the original Url and then returns it associated with the entities Id.
     * @param originalUrl the url to shorten.
     * @return the shortenUrl uniquely identified on our DB.
     */
    String shortenUrl(String originalUrl);

    /**
     * Searches for the shortenUrl in DB and returns the original Url
     * @param shortenUrl the shorten Url to search for.
     * @return the original Url to redirect to.
     */
    String getOriginalUrl(String shortenUrl);
}
