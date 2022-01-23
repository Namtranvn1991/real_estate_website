package com.c0721g2srsrealestatebe.service.image;

import com.c0721g2srsrealestatebe.model.image.Image;
import com.c0721g2srsrealestatebe.repository.image.IImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService implements IImageService {
    @Autowired
    IImageRepo iImageRepo;

    @Override
    public void saveImg(Image image, String realEstateId) {
        System.out.println(image);
        iImageRepo.saveImg(image.getId(), image.getUrl());
        iImageRepo.saveRelative(iImageRepo.lastId(), realEstateId);
    }
}
