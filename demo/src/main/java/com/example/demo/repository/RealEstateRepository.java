package com.example.demo.repository;

import com.example.demo.model.RealEstate;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RealEstateRepository extends CrudRepository<RealEstate, Long> {
    Optional<RealEstate> findByTitle(String title);
}
