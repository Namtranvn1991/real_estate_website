package com.c0721g2srsrealestatebe.model.realestatenews;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity(name = "directions")
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "direction")
    @JsonBackReference("real_estate_news_directions")
    private List<RealEstateNews> realEstateNewsList ;
    public Direction() {
        //this is constructor
    }

    public List<RealEstateNews> getRealEstateNewsList() {
        return realEstateNewsList;
    }

    public void setRealEstateNewsList(List<RealEstateNews> realEstateNewsList) {
        this.realEstateNewsList = realEstateNewsList;
    }

    public Direction(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
