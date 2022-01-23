package com.c0721g2srsrealestatebe.service.realestatenews.impl;

import com.c0721g2srsrealestatebe.model.realestatenews.RealEstateType;
import com.c0721g2srsrealestatebe.repository.realestatenews.IRealEstateTypeRepository;
import com.c0721g2srsrealestatebe.service.realestatenews.IRealEstateTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RealEstateTypeServiceImpl implements IRealEstateTypeService {
    @Autowired
    IRealEstateTypeRepository iRealEstateTypeRepository;

    @Override
    public List<RealEstateType> realEstateTypeList() {
        return iRealEstateTypeRepository.findAll();
    }


    public List<RealEstateType> findAllRealEstateType() {
        return iRealEstateTypeRepository.findAll();
    }
}
