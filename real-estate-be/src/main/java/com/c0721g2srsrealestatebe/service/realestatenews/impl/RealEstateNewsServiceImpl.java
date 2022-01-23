package com.c0721g2srsrealestatebe.service.realestatenews.impl;

import com.c0721g2srsrealestatebe.model.realestatenews.Direction;
import com.c0721g2srsrealestatebe.model.realestatenews.RealEstateNews;
import com.c0721g2srsrealestatebe.model.realestatenews.RealEstateType;
import com.c0721g2srsrealestatebe.repository.realestatenews.IRealEstateNewsRepository;
import com.c0721g2srsrealestatebe.service.realestatenews.IRealEstateNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RealEstateNewsServiceImpl implements IRealEstateNewsService {
    @Autowired
    private IRealEstateNewsRepository iRealEstateNewsRepository;

    // TaiVD get history post - please dont delete my task
    // 5.5.4  List history post

    @Override
    public Page< RealEstateNews > findAllNewsBySearchField
            (String customerId, String title, String typeOfNew, String realNewType, Pageable pageable) {
        return iRealEstateNewsRepository.findAllNewsBySearchField
                (customerId, title, typeOfNew, typeOfNew, pageable);
    }

    // 5.6.3 show Real estate new detail
    @Override
    public Optional< RealEstateNews > findNewsById(String id) {
        return iRealEstateNewsRepository.findById(id);
    }

    // 5.6.2 add Real estate new detail
    @Override
    public RealEstateNews saveRealEstateNews(RealEstateNews realEstateNews) {
//        realEstateNewsRepository.saveNews(realEstateNews.getAddress(),realEstateNews.getApproval(),realEstateNews.getArea(),realEstateNews.getDescription(),
//                realEstateNews.getKindOfNews(),realEstateNews.getPrice(),realEstateNews.getStatus(),realEstateNews.getTitle(),realEstateNews.getCustomer().getId(),
//                realEstateNews.getDirection().getId(),realEstateNews.getRealEstateType().getId());
        iRealEstateNewsRepository.save(realEstateNews);
        return iRealEstateNewsRepository.findNewsById(iRealEstateNewsRepository.lastId()).orElse(null);
    }


    //////////////////////////////////////DOANH//////////////////////////////////////////////////////////////////////

// 5.7.1 Xem danh sách nhu cầu - Override method hiển thị List DoanhNV
    @Override
    public Page<RealEstateNews> findAllNewsPage(Pageable pageable) {
        return iRealEstateNewsRepository.findAllByRealEstateNews(pageable);
    }
    // 5.7.1 Xem danh sách nhu cầu - Override method tìm kiếm DoanhNV
    @Override
    public Page<RealEstateNews> searchRealEstateNewsByKindOfNewsAndRealEstateTypeAndDirection(Pageable pageable,
                                                                                              String kinOfNews,
                                                                                              String directionId,
                                                                                              String realEstateTypeId) {
        return iRealEstateNewsRepository.searchRealEstateNewsByKindOfNewsAndRealEstateTypeAndDirection(pageable,
                "%" +kinOfNews+ "%","%" +directionId+ "%", "%" +realEstateTypeId+ "%");
    }

    // 5.7.1 Xem danh sách nhu cầu - Override method Duyệt hiển thị Dialog DoanhNV
    @Override
    public void approveListPost(String id) {
        this.iRealEstateNewsRepository.updateApproval(id);
    }

    // 5.7.1 Xem danh sách nhu cầu - Override method Không Duyệt hiển thị Dialog DoanhNV
    @Override
    public void approvalListPost(String id) {
        this.iRealEstateNewsRepository.deleteApproval(id);
    }



    @Override
    public Optional<RealEstateNews> findById(String id) {
        return iRealEstateNewsRepository.findById(id);
    }

    /////////////////////////////////////DOANH////////////////////////////////////////////////////////////////////////



    // 5.6.1 KhaiPN
    @Override
    public Page<RealEstateNews> findAllRealEstateNewsByFilter(String address, String kindOfNews, String realEstateType, String direction, String minArea, String maxArea, String minPrice, String maxPrice, Pageable pageable) {
        return iRealEstateNewsRepository.findAllRealEstateNewsByFilter(address,kindOfNews, realEstateType, direction, minArea, maxArea, minPrice,  maxPrice, pageable);
    }

    @Override
    public String findLastId() {
        return iRealEstateNewsRepository.lastId();
    }
}
