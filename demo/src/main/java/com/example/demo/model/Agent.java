package com.example.demo.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Entity
public class Agent {

    public Agent() {
    }

    public Agent(String name) {
        this.name = name;
    }

    public Agent(List<RealEstate> realEstates, String name) {
        this.realEstates = realEstates;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(
            mappedBy = "agent",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<RealEstate> realEstates = new ArrayList<>();

    private String name;

    public List<RealEstate> getSoldProperties() {
        return realEstates.stream()
                .filter(RealEstate::isSold)
                .collect(Collectors.toList());
    }

    public Double getSumOfSoldPropertiesForGivenPeriod(Date startDate, Date endDate) {
        return realEstates.stream()
                .filter(RealEstate::isSold)
                .filter(realEstate -> !realEstate.getSellingDate().before(startDate) && !realEstate.getSellingDate().after(endDate))
                .flatMapToDouble(realEstate -> DoubleStream.of(realEstate.getPrice()))
                .sum();
    }

    public void addRealEstate(RealEstate realEstate) {
        realEstate.setAgent(this);
        this.realEstates.add(realEstate);
    }

    public List<RealEstate> getRealEstates() {
        return realEstates;
    }

    public void setRealEstates(List<RealEstate> realEstates) {
        this.realEstates = realEstates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Agent{" +
                "name='" + name + '\'' +
                '}';
    }
}
