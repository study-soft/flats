package com.ay.flats.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "flats")
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class Flat {
    @Id
    @JsonIgnore
    private String id;
    @JsonIgnore
    private Long olxId;
    private String name;
    private String url;
    private Integer priceUsd;
    private LocalDateTime adDate;
    private Integer floor;
    private Integer floorsTotal;
    private Integer totalSquare;
    private Integer kitchenSquare;
    private Integer roomCount;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public Flat url(final String link) {
        this.url = link;
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(final Integer floor) {
        this.floor = floor;
    }

    public Flat floor(final Integer floor) {
        this.floor = floor;
        return this;
    }

    public Integer getFloorsTotal() {
        return floorsTotal;
    }

    public void setFloorsTotal(final Integer floorsTotal) {
        this.floorsTotal = floorsTotal;
    }

    public Flat floorsTotal(final Integer floorsTotal) {
        this.floorsTotal = floorsTotal;
        return this;
    }

    public Integer getTotalSquare() {
        return totalSquare;
    }

    public void setTotalSquare(final Integer totalSquare) {
        this.totalSquare = totalSquare;
    }

    public Flat totalSquare(final Integer totalSquare) {
        this.totalSquare = totalSquare;
        return this;
    }

    public Integer getKitchenSquare() {
        return kitchenSquare;
    }

    public void setKitchenSquare(final Integer kitchenSquare) {
        this.kitchenSquare = kitchenSquare;
    }

    public Flat kitchenSquare(final Integer kitchenSquare) {
        this.kitchenSquare = kitchenSquare;
        return this;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(final Integer roomCount) {
        this.roomCount = roomCount;
    }

    public Flat roomCount(final Integer roomCount) {
        this.roomCount = roomCount;
        return this;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "id='" + id + '\'' +
                ", olxId=" + olxId +
                ", name='" + name + '\'' +
                ", link='" + url + '\'' +
                ", priceUsd=" + priceUsd +
                ", adDate=" + adDate +
                ", floor=" + floor +
                ", floorsTotal=" + floorsTotal +
                ", totalSquare=" + totalSquare +
                ", kitchenSquare=" + kitchenSquare +
                ", roomCount=" + roomCount +
                '}';
    }
}
