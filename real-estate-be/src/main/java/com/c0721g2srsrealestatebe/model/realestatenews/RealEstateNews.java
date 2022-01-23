package com.c0721g2srsrealestatebe.model.realestatenews;

import com.c0721g2srsrealestatebe.customid.CustomIdGenerator;
import com.c0721g2srsrealestatebe.model.customer.Customer;
import com.c0721g2srsrealestatebe.model.image.Image;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity(name = "real_estate_news")
@SQLDelete(sql = "UPDATE customers SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class RealEstateNews {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "real_estate_news_seq")
    @GenericGenerator(
            name = "real_estate_news_seq",
            strategy = "com.c0721g2srsrealestatebe.customid.CustomIdGenerator",
            parameters = {
                    @Parameter(name = CustomIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = CustomIdGenerator.VALUE_PREFIX_PARAMETER, value = "BD-"),
                    @Parameter(name = CustomIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%04d")})
    private String id;
    private String title;
    private String description;
    private String address;
    private Double area;
    private Double price;
    @Column(name = "approval", columnDefinition = "TINYINT")
    private Integer approval;
    @Column(name = "kind_of_news", columnDefinition = "TINYINT")
    private Integer kindOfNews;
    @Column(name = "status", columnDefinition = "TINYINT")
    private Integer status;
    @ManyToOne(targetEntity = RealEstateType.class)
    private RealEstateType realEstateType;
    @ManyToOne(targetEntity = Direction.class)
    private Direction direction;
    @ManyToOne(targetEntity = Customer.class)
//    @JsonManagedReference
    private Customer customer;
    @OneToMany(targetEntity = Image.class,cascade = CascadeType.PERSIST)
    private List<Image> imageList;
    private Boolean deleted = Boolean.FALSE;

    public RealEstateNews() {
    }

    @SuppressWarnings("squid:S00107")
    public RealEstateNews(String id, String title, String description, String address, Double area, Double price, Integer approval, Integer kindOfNews, Integer status, RealEstateType realEstateType, Direction direction, Customer customer, List<Image> imageList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.address = address;
        this.area = area;
        this.price = price;
        this.approval = approval;
        this.kindOfNews = kindOfNews;
        this.status = status;
        this.realEstateType = realEstateType;
        this.direction = direction;
        this.customer = customer;
        this.imageList = imageList;
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

    public RealEstateType getRealEstateType() {
        return realEstateType;
    }

    public void setRealEstateType(RealEstateType realEstateType) {
        this.realEstateType = realEstateType;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    @Override
    public String toString() {
        return "RealEstateNews{" +
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
                '}';
    }
}
