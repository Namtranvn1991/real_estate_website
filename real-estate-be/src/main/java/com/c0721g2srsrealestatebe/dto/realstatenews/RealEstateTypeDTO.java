package com.c0721g2srsrealestatebe.dto.realstatenews;

public class RealEstateTypeDTO {

    private Long id;
    private String name;

    public RealEstateTypeDTO() {
    }

    public RealEstateTypeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RealEstateTypeDTO(Long id) {
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
        return "RealEstateTypeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
