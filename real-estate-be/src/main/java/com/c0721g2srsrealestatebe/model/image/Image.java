package com.c0721g2srsrealestatebe.model.image;

import com.c0721g2srsrealestatebe.model.realestatenews.RealEstateType;

import javax.persistence.*;

@Entity(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;


    public Image() {
        //this is constructor
    }

    public Image(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public Image(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
