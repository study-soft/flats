package com.ay.flats.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "flats")
public final class Flat {
    @Id
    @JsonIgnore
    private String id;
    @JsonIgnore
    private Long olxId;
    private String name;
    private String link;
    private Integer priceUsd;
    private LocalDateTime adDate;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Flat id(final String id) {
        this.id = id;
        return this;
    }

    public Long getOlxId() {
        return olxId;
    }

    public void setOlxId(final Long olxId) {
        this.olxId = olxId;
    }

    public Flat olxId(final Long olxId) {
        this.olxId = olxId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Flat name(final String name) {
        this.name = name;
        return this;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public Flat link(final String link) {
        this.link = link;
        return this;
    }

    public Integer getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(final Integer priceUsd) {
        this.priceUsd = priceUsd;
    }

    public Flat priceUsd(final Integer priceUsd) {
        this.priceUsd = priceUsd;
        return this;
    }

    public LocalDateTime getAdDate() {
        return adDate;
    }

    public void setAdDate(final LocalDateTime adDate) {
        this.adDate = adDate;
    }

    public Flat adDate(final LocalDateTime adDate) {
        this.adDate = adDate;
        return this;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "id='" + id + '\'' +
                ", olxId=" + olxId +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", priceUsd=" + priceUsd +
                ", adDate=" + adDate +
                '}';
    }
}
