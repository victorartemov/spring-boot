package com.example.demo.service;

import com.example.demo.model.RealEstate;

import java.util.List;
import java.util.Optional;

public interface RealEstateService {
    RealEstate createRealEstate(RealEstate realEstate);

    RealEstate updateRealEstate(RealEstate realEstate);

    void deleteRealEstate(RealEstate realEstate);

    RealEstate findById(Long id);

    List<RealEstate> findAll();

    Optional<RealEstate> findByTitle(String title);

    void sell(String title);

    void changePrice(String title, double newPrice);
}
