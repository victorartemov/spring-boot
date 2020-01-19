package com.example.demo.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class RealEstate {

    public RealEstate() {
    }

    public RealEstate(double price, String title) {
        this.price = price;
        this.title = title;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Agent agent;

    private int viewsCounter = 0;
    private boolean isSold;
    private double price;
    private String title;
    private String description;
    private Date sellingDate;

    public int getViewsCounter() {
        return viewsCounter;
    }

    public void incrementViewsCounter() {
        viewsCounter++;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(Date sellingDate) {
        this.sellingDate = sellingDate;
    }

    @Override
    public String toString() {
        return "RealEstate{" +
                "agent=" + agent +
                ", viewsCounter=" + viewsCounter +
                ", isSold=" + isSold +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", sellingDate=" + sellingDate +
                '}';
    }
}
