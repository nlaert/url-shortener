package com.runtime.url.exceptions;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String url) {
        super(String.format("No match could be found for the url %s", url));
    }
}
