package com.c0721g2srsrealestatebe.controller;


import com.c0721g2srsrealestatebe.dto.RealEstateDTO;
import com.c0721g2srsrealestatebe.model.customer.Customer;
import com.c0721g2srsrealestatebe.model.image.Image;
import com.c0721g2srsrealestatebe.model.realestatenews.*;
import com.c0721g2srsrealestatebe.service.image.IImageService;
import com.c0721g2srsrealestatebe.service.realestatenews.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/real-estate-new")
@CrossOrigin("http://localhost:4200")
public class RealEstateNewsController {
    @Autowired
    private IRealEstateNewsService realEstateNewsService;
    @Autowired
    private IImageService iImageService;
    @Autowired
    private EmailService emailService;
    @Autowired
    IApprovalMailService iApprovalMailService;

    // TaiVD get history post - please dont delete my task
    // 5.5.4  List history post
    @GetMapping("/history-post")
    public ResponseEntity< Page< RealEstateNews > > showHistoryPostNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "", value = "customerId") String customerId,
            @RequestParam(defaultValue = "", value = "title") String title,
            @RequestParam(defaultValue = "", value = "kindOfNew") String kindOfNew,
            @RequestParam(defaultValue = "", value = "realNewType") String realNewType) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by("id"));
        Page< RealEstateNews > realEstateNewsPage = realEstateNewsService.
                findAllNewsBySearchField(customerId, title, kindOfNew, realNewType, pageable);

        if (realEstateNewsPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(realEstateNewsPage, HttpStatus.OK);
    }

    //     5.6.3 show Real estate new detail
    @GetMapping("/detail/{id}")
    public ResponseEntity< RealEstateNews > findNewById(@PathVariable(value = "id") String id) {
        Optional< RealEstateNews > realEstateNews = realEstateNewsService.findNewsById(id);
        if (realEstateNews.isPresent()) {
            return new ResponseEntity<>(realEstateNews.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 5.6.3 send mail to customer
    @PostMapping("/email")
    public ResponseEntity< Void > emailSend(@RequestBody() Email email) throws UnsupportedEncodingException, MessagingException {
        if (email == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            emailService.sendSimpleMessage(email.getCustomerMail(), email.getName(), email.getPhone());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    // 5.7.1 Xem danh sách nhu cầu - Hiển thị List DoanhNV
    @GetMapping(value = "/list")
    public ResponseEntity<Page<RealEstateNews>> getListPostApproval(@PageableDefault(value = 10) Pageable pageable){
        Page<RealEstateNews> realEstateNewsPage = realEstateNewsService.findAllNewsPage(pageable);
        if (realEstateNewsPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(realEstateNewsPage, HttpStatus.OK);
    }

    // 5.7.1 Xem danh sách nhu cầu - Tìm kiếm DoanhNV
    @GetMapping("/search-approval-list")
    public ResponseEntity<Page<RealEstateNews>> searchListPostApproval(@PageableDefault(value = 10) Pageable pageable,
                                                                       @RequestParam(defaultValue = "",value ="kind_of_news" ) String kindOfNews,
                                                                       @RequestParam(defaultValue = "", value = "direction_id") String directionId,
                                                                       @RequestParam(defaultValue = "", value = "real_estate_type_id") String realEstateTypeId){
        Page<RealEstateNews> realEstateNewsPage =
                realEstateNewsService.searchRealEstateNewsByKindOfNewsAndRealEstateTypeAndDirection(pageable, kindOfNews, directionId, realEstateTypeId);
        if (realEstateNewsPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(realEstateNewsPage, HttpStatus.OK);
    }
    // 5.7.1 Xem danh sách nhu cầu - Method duyệt gọi Dialog DoanhNV
    @PostMapping("/approve/{id}")
    public ResponseEntity<RealEstateNews> approve(@PathVariable String id, @RequestBody RealEstateNews realEstateNews) {
        Optional<RealEstateNews> realEstateNewsOptional = realEstateNewsService.findById(id);
        if (!realEstateNewsOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        realEstateNewsOptional.get();
        realEstateNewsService.approveListPost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 5.7.1 Xem danh sách nhu cầu - Method Không duyệt gọi Dialog DoanhNV
    @PostMapping("/approval/{id}")
    public ResponseEntity<RealEstateNews> approval(@PathVariable String id, @RequestBody RealEstateNews realEstateNews) {
        Optional<RealEstateNews> realEstateNewsOptional = realEstateNewsService.findById(id);
        if (!realEstateNewsOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        realEstateNewsOptional.get();
        realEstateNewsService.approvalListPost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    5.7.1 DoanhNV
    @PostMapping("/approval-email")
    public ResponseEntity< Void > approvalSendMail(@RequestBody() ApprovalMail approvalMail) throws UnsupportedEncodingException, MessagingException {
        if (approvalMail == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            iApprovalMailService.sendApprovalMail(approvalMail.getCustomerEmail(), approvalMail.getStatus(), approvalMail.getReason());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    //    //5.7.1 DoanhNV
//    @GetMapping("/{id}")
//    public ResponseEntity< RealEstateNews > findNewById(@PathVariable(value = "id") String id) {
//        Optional< RealEstateNews > realEstateNews = realEstateNewsService.findNewsById(id);
//        if (realEstateNews.isPresent()) {
//            return new ResponseEntity<>(realEstateNews.get(), HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//    // 5.6.1  List real-estate ket hop tim kiem approvel, address, kindOfNews, realEstateType, direction KhaiPN
    @GetMapping("/search")
    public ResponseEntity< Page< RealEstateNews > > getListRealEstateNews(
            @RequestParam(defaultValue = "", value = "address") String address,
            @RequestParam(defaultValue = "", value = "kindOfNews") String kindOfNews,
            @RequestParam(defaultValue = "", value = "realEstateType") String realEstateType,
            @RequestParam(defaultValue = "", value = "direction") String direction,
            @RequestParam(defaultValue = "0", value = "minArea") String minArea,
            @RequestParam(defaultValue = "10000000000000000000000", value = "maxArea") String maxArea,
            @RequestParam(defaultValue = "0", value = "minPrice") String minPrice,
            @RequestParam(defaultValue = "10000000000000000000000", value = "maxPrice") String maxPrice,
            @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id"));
        Page< RealEstateNews > realEstateNewsPage = realEstateNewsService.
                findAllRealEstateNewsByFilter(address, kindOfNews,realEstateType, direction,minArea, maxArea, minPrice, maxPrice, pageable);

        if (realEstateNewsPage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(realEstateNewsPage, HttpStatus.OK);
    }


    // 5.6.2 add Real estate new detail TranNN
    @PostMapping("/post")
    public ResponseEntity< List< FieldError > > saveRealEstateNews(@RequestBody @Valid RealEstateDTO realEstateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.NOT_ACCEPTABLE);
        }
        RealEstateNews news = this.copyProperties(realEstateDTO);
        List<Image> imageList = new ArrayList<>();
        List<String> items = Arrays.asList(realEstateDTO.getUrls().split("\\s*,\\s*"));
        items.forEach((item -> {
                    Image image = new Image();
                    image.setUrl(item);
                    imageList.add(image);
                })
        );
        news.setImageList(imageList);
        System.out.println(news);
        System.out.println(news.getCustomer().getId());
        RealEstateNews realEstateNews = realEstateNewsService.saveRealEstateNews(news);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public RealEstateNews copyProperties(RealEstateDTO realEstateDTO) {
        RealEstateNews realEstateNews = new RealEstateNews();
        BeanUtils.copyProperties(realEstateDTO, realEstateNews);
        Customer customer = new Customer();
        customer.setId(realEstateDTO.getCustomer().getId());
        realEstateNews.setRealEstateType(new RealEstateType(realEstateDTO.getRealEstateType().getId()));
        realEstateNews.setDirection(new Direction(realEstateDTO.getDirection().getId()));
        realEstateNews.setCustomer(customer);
        realEstateNews.setApproval(1);
        return realEstateNews;
    }

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




