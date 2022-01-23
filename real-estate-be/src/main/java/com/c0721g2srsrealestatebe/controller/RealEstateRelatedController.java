package com.c0721g2srsrealestatebe.controller;


import com.c0721g2srsrealestatebe.model.realestatenews.Direction;
import com.c0721g2srsrealestatebe.model.realestatenews.RealEstateType;
import com.c0721g2srsrealestatebe.service.realestatenews.IDirectionService;
import com.c0721g2srsrealestatebe.service.realestatenews.IRealEstateTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/real-estate-related")
@CrossOrigin("http://localhost:4200")
public class RealEstateRelatedController {
    @Autowired
    IDirectionService iDirectionService;
    @Autowired
    IRealEstateTypeService iRealEstateTypeService;

    @GetMapping(value = "/direction")
    public List<Direction> directionList(){
        return iDirectionService.directionList();
    }

    @GetMapping(value = "/realEstateType")
    public List<RealEstateType> realEstateTypes(){
        return iRealEstateTypeService.realEstateTypeList();
    }
}
