package com.runtime.url.dto;

import java.util.Objects;

public class UrlDto {
    private String originalUrl;
    private String shortenUrl;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getShortenUrl() {
        return shortenUrl;
    }

    public void setShortenUrl(String shortenUrl) {
        this.shortenUrl = shortenUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlDto urlDto = (UrlDto) o;
        return Objects.equals(getOriginalUrl(), urlDto.getOriginalUrl()) &&
                Objects.equals(getShortenUrl(), urlDto.getShortenUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOriginalUrl(), getShortenUrl());
    }
}
