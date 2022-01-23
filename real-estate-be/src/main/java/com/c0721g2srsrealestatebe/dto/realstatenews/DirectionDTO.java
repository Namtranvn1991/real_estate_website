package com.c0721g2srsrealestatebe.dto.realstatenews;

public class DirectionDTO {
    private Long id;
    private String name;

    public DirectionDTO() {
    }

    public DirectionDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public DirectionDTO(Long id) {
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
        return "DirectionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
