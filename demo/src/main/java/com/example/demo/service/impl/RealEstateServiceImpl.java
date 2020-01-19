package com.example.demo.service.impl;

import com.example.demo.model.RealEstate;
import com.example.demo.repository.RealEstateRepository;
import com.example.demo.service.RealEstateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RealEstateServiceImpl implements RealEstateService {

    private static final Logger logger = LoggerFactory.getLogger(RealEstateServiceImpl.class);

    @Autowired
    private RealEstateRepository realEstateRepository;

    @Override
    public RealEstate createRealEstate(RealEstate realEstate) {
        return realEstateRepository.save(realEstate);
    }

    @Override
    public RealEstate updateRealEstate(RealEstate realEstate) {
        return realEstateRepository.save(realEstate);
    }

    @Override
    public void deleteRealEstate(RealEstate realEstate) {
        realEstateRepository.delete(realEstate);
    }

    @Override
    public RealEstate findById(Long id) {
        Optional<RealEstate> realEstate = realEstateRepository.findById(id);

        if (realEstate.isPresent()) {
            RealEstate realEstateToIncrementView = realEstate.get();
            realEstateToIncrementView.incrementViewsCounter();
            realEstateRepository.save(realEstateToIncrementView);
        }

        return realEstate.orElse(null);
    }

    @Override
    public List<RealEstate> findAll() {
        List<RealEstate> realEstates = (List<RealEstate>) realEstateRepository.findAll();
        realEstates.forEach(RealEstate::incrementViewsCounter);
        realEstateRepository.saveAll(realEstates);

        return realEstates;
    }

    @Override
    public Optional<RealEstate> findByTitle(String title) {
        Optional<RealEstate> realEstate = realEstateRepository.findByTitle(title);
        if (realEstate.isPresent()) {
            RealEstate realEstateToIncrementView = realEstate.get();
            realEstateToIncrementView.incrementViewsCounter();
            realEstateRepository.save(realEstateToIncrementView);
        }

        return realEstate;
    }

    @Override
    public void sell(String title) {
        Optional<RealEstate> realEstate = realEstateRepository.findByTitle(title);

        if (realEstate.isPresent()) {
            logger.info("Selling real estate with title = {}", title);

            RealEstate soldRealEstate = realEstate.get();
            soldRealEstate.setSold(true);
            soldRealEstate.setSellingDate(new Date());

            realEstateRepository.save(soldRealEstate);
        } else {
            logger.error("No real estate with title = {} found", title);
        }
    }

    @Override
    public void changePrice(String title, double newPrice) {
        Optional<RealEstate> realEstate = realEstateRepository.findByTitle(title);

        if (realEstate.isPresent()) {
            logger.info("Changing the price for real estate with title = {}", title);

            RealEstate realEstateWithChangedPrice = realEstate.get();

            if (!realEstateWithChangedPrice.isSold()) {
                realEstateWithChangedPrice.setPrice(newPrice);
                realEstateRepository.save(realEstateWithChangedPrice);
            }
        } else {
            logger.error("No real estate with title = {} found", title);
        }
    }
}
