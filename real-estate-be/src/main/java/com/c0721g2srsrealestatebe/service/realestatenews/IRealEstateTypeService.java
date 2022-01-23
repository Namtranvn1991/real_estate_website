package com.c0721g2srsrealestatebe.service.realestatenews;

import com.c0721g2srsrealestatebe.model.realestatenews.RealEstateType;

import java.util.List;


public interface IRealEstateTypeService {
    List<RealEstateType> realEstateTypeList();
    List<RealEstateType> findAllRealEstateType();
}
