package com.runtime.url;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String url) {
        super(String.format("No match could be found for the url %s", url));
    }
}
