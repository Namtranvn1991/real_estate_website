package com.c0721g2srsrealestatebe.dto.realstatenews;

import javax.validation.constraints.Pattern;

public class CusDTO {
    @Pattern(regexp = "KH-\\d{4}",message = "wrong format")
    private String id;

    public CusDTO() {
    }

    public CusDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CusDTO{" +
                "id='" + id + '\'' +
                '}';
    }
}
