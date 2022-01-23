package com.c0721g2srsrealestatebe.dto;

import com.c0721g2srsrealestatebe.dto.realstatenews.CusDTO;
import com.c0721g2srsrealestatebe.dto.realstatenews.DirectionDTO;
import com.c0721g2srsrealestatebe.dto.realstatenews.ImageDTO;
import com.c0721g2srsrealestatebe.dto.realstatenews.RealEstateTypeDTO;

import javax.validation.constraints.*;
import java.util.List;

public class RealEstateDTO{

    private String id;
    @NotBlank(message = "NotBlank")
    @Size(max = 256, message = "max 256")
    private String title;
    @Size(max = 256, message = "max 256")
    private String description;
    @NotBlank(message = "NotBlank")
    @Size(max = 256, message = "max 256")
    private String address;
    @Min(value = 1, message = "positive numbers")
    @NotNull(message = "NotNull")
    private Double area;
    @Min(value = 1, message = "positive numbers")
    @NotNull(message = "NotNull")
    private Double price;
    private Integer approval;
    @Min(value = 1, message = "value 1-2")
    @Max(value = 2, message = "value 1-2")
    @NotNull(message = "NotNull")
    private Integer kindOfNews;
    @Min(value = 1, message = "value 1-2")
    @Max(value = 2, message = "value 1-2")
    @NotNull(message = "NotNull")
    private Integer status;
    @NotNull(message = "NotNull")
    private RealEstateTypeDTO realEstateType;
    @NotNull(message = "NotNull")
    private DirectionDTO direction;
    private CusDTO customer;
    @Size(min=1, max=5,message = "size 1-5")
    private List<ImageDTO> imageList;
    private String urls;

    public RealEstateDTO() {
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getApproval() {
        return approval;
    }

    public void setApproval(Integer approval) {
        this.approval = approval;
    }

    public Integer getKindOfNews() {
        return kindOfNews;
    }

    public void setKindOfNews(Integer kindOfNews) {
        this.kindOfNews = kindOfNews;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public RealEstateTypeDTO getRealEstateType() {
        return realEstateType;
    }

    public void setRealEstateType(RealEstateTypeDTO realEstateType) {
        this.realEstateType = realEstateType;
    }

    public DirectionDTO getDirection() {
        return direction;
    }

    public void setDirection(DirectionDTO direction) {
        this.direction = direction;
    }

    public CusDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CusDTO customer) {
        this.customer = customer;
    }

    public List<ImageDTO> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageDTO> imageList) {
        this.imageList = imageList;
    }

    @Override
    public String toString() {
        return "RealEstateDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", area=" + area +
                ", price=" + price +
                ", approval=" + approval +
                ", kindOfNews=" + kindOfNews +
                ", status=" + status +
                ", realEstateType=" + realEstateType +
                ", direction=" + direction +
                ", customer=" + customer +
                ", imageList=" + imageList +
                ", urls='" + urls + '\'' +
                '}';
    }
}
