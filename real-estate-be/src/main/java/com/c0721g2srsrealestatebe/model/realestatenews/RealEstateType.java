package com.c0721g2srsrealestatebe.model.realestatenews;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity(name = "real_estate_type")
public class RealEstateType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "realEstateType")
    @JsonBackReference("real_estate_news_real_estate_type")
    private List<RealEstateNews> realEstateNewsList ;

    public RealEstateType() {
    }

    public List<RealEstateNews> getRealEstateNewsList() {
        return realEstateNewsList;
    }

    public void setRealEstateNewsList(List<RealEstateNews> realEstateNewsList) {
        this.realEstateNewsList = realEstateNewsList;
    }

    public RealEstateType(Long id) {
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
        return "RealEstateType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
