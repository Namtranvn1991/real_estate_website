package com.c0721g2srsrealestatebe.dto;

public class PositionDTO {
    private Long id;
    private String name;

    public PositionDTO() {
        //comment
    }

    public PositionDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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
        return "PositionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
