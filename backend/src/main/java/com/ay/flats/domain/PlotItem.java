package com.ay.flats.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "plotItems")
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class PlotItem {
    @Id
    @JsonIgnore
    private String id;
    private LocalDateTime date;
    private Double price;
    private Integer newFlats;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public PlotItem id(final String id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(final LocalDateTime date) {
        this.date = date;
    }

    public PlotItem date(final LocalDateTime date) {
        this.date = date;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public PlotItem price(final Double price) {
        this.price = price;
        return this;
    }

    public Integer getNewFlats() {
        return newFlats;
    }

    public void setNewFlats(final Integer newFlats) {
        this.newFlats = newFlats;
    }

    public PlotItem newFlats(final Integer newFlats) {
        this.newFlats = newFlats;
        return this;
    }

    @Override
    public String toString() {
        return "PlotItem{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", price=" + price +
                ", newFlats=" + newFlats +
                '}';
    }
}
