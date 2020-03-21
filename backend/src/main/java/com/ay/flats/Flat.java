package com.ay.flats;

import java.time.LocalDateTime;

public final class Flat {
    private String name;
    private String link;
    private Integer priceUsd;
    private LocalDateTime adDate;

    public Flat(final String name, final String link, final Integer priceUsd, final LocalDateTime adDate) {
        this.name = name;
        this.link = link;
        this.priceUsd = priceUsd;
        this.adDate = adDate;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public Integer getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(final Integer priceUsd) {
        this.priceUsd = priceUsd;
    }

    public LocalDateTime getAdDate() {
        return adDate;
    }

    public void setAdDate(final LocalDateTime adDate) {
        this.adDate = adDate;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "name='" + name + '\'' +
                ", url='" + link + '\'' +
                ", price=" + priceUsd +
                ", adDate=" + adDate +
                '}';
    }
}
