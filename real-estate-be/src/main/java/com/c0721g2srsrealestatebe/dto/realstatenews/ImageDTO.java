package com.c0721g2srsrealestatebe.dto.realstatenews;

public class ImageDTO {
    private String url;

    public ImageDTO() {
    }

    public ImageDTO(String url) {
        this.url = url;
    }

    public ImageDTO(Long id, String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ImageDTO{" +
                ", url='" + url + '\'' +
                '}';
    }
}
