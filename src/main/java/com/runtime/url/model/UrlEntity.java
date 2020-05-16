package com.runtime.url.model;

import javax.persistence.*;

@Entity
@Table(indexes={@Index(columnList="shortenUrl", name="shortenUrl_Index", unique = true)})
public class UrlEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String shortenUrl;

    @Column
    private String originalUrl;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getShortenUrl() {
        return shortenUrl;
    }

    public void setShortenUrl(String shortenUrl) {
        this.shortenUrl = shortenUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }


}
