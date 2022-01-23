package com.c0721g2srsrealestatebe.service.realestatenews;


import com.c0721g2srsrealestatebe.model.realestatenews.Direction;

import java.util.List;
import java.util.Optional;

public interface IDirectionService {
    List<Direction> directionList();
    List<Direction> findAllDirection();
    Optional<Direction> findById(Long id);
}

