package com.c0721g2srsrealestatebe.repository.realestatenews;

import com.c0721g2srsrealestatebe.model.realestatenews.RealEstateNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IRealEstateNewsRepository extends JpaRepository< RealEstateNews, String > {
    // TaiVD get history post - please dont delete my task
    // 5.5.4  List history post
    @Query(value = " select * \n" +
            "  from real_estate_news\n" +
            "  where customer_id like concat('%',trim(:customerId),'%') \n" +
            "  and title like concat('%',trim(:title),'%')\n" +
            "  and kind_of_news like concat('%',:kindOfNew,'%')\n" +
            "  and real_estate_type_id like concat('%',:realNewType,'%') ",
            nativeQuery = true, countQuery = " select count(*) from real_estate_news " +
            " where customer_id like concat('%',trim(:customerId),'%') " +
            " and title like concat('%',trim(:title),'%') " +
            " and kind_of_news like concat('%',:kindOfNew,'%') " +
            " and real_estate_type_id like concat('%',:realNewType,'%')")
    Page< RealEstateNews > findAllNewsBySearchField(@Param("customerId") String customerId,
                                                    @Param("title") String title,
                                                    @Param("kindOfNew") String typeOfNew,
                                                    @Param("realNewType") String realNewType,
                                                    @Param("page") Pageable pageable);

    // 5.6.3 show Real estate new detail
    @Query(value = " select * from real_estate_news where id =:id ", nativeQuery = true)
    Optional< RealEstateNews > findNewsById(@Param("id") String id);

    // 5.6.2 add Real estate new detail TranNN
    @Query(value = "SELECT MAX(id) FROM real_estate_news;", nativeQuery = true)
    String lastId();

    @Modifying
    @Transactional
    @Query(value = "insert into real_estate_news (address,approval,area,`description`,kind_of_news,price,`status`,title,customer_id,direction_id,real_estate_type_id) " +
            " values (:address,:approval,:area,:description,:kind_of_news,:price,:status,:title,:customer_id,:direction_id,:real_estate_type_id)", nativeQuery = true)
    Integer saveNews(@Param("address") String address, @Param("approval") Integer approval,
                     @Param("area") Double area, @Param("description") String description, @Param("kind_of_news") Integer kind_of_news,
                     @Param("price") Double price, @Param("status") Integer status, @Param("title") String title, @Param("customer_id") String customer_id,
                     @Param("direction_id") Long direction_id, @Param("real_estate_type_id") Long real_estate_type_id);

    Optional< RealEstateNews > findById(String id);

    //////////////////////////////////////DOANH//////////////////////////////////////////////////////////////

    // 5.7.1 Xem danh sách nhu cầu - Câu Query hiển thị List DoanhNV
    @Query(value = "select * from real_estate_news ", nativeQuery = true)
    Page<RealEstateNews> findAllByRealEstateNews(Pageable pageable);

    // 5.7.1 Xem danh sách nhu cầu - Câu Query tìm kiếm DoanhNV
    @Query(value = "select * \n" +
            "from real_estate_news \n" +
            "where kind_of_news like :kind_of_news " +
            " and direction_id like :direction_id " +
            " and real_estate_type_id like :real_estate_type_id " +
            "and approval =1 "
            , nativeQuery = true )
    Page<RealEstateNews> searchRealEstateNewsByKindOfNewsAndRealEstateTypeAndDirection(Pageable pageable,@Param("kind_of_news") String kindOfNews,
                                                                                       @Param("direction_id") String directionId,
                                                                                       @Param("real_estate_type_id") String realEstateTypeId);

    // 5.7.1 Xem danh sách nhu cầu - Câu Query Duyệt bài đăng DoanhNV
    @Modifying
    @javax.transaction.Transactional
    @Query(value = "update real_estate_news a set a.approval= 2 where a.id = :id", nativeQuery = true)
    void updateApproval(@Param("id") String id);

    // 5.7.1 Xem danh sách nhu cầu - Câu Query Không Duyệt bài đăng DoanhNV
    @Modifying
    @javax.transaction.Transactional
    @Query(value = "update real_estate_news a set a.approval = 3 where a.id = :id", nativeQuery = true)
    void deleteApproval(@Param("id") String id);

    ////////////////////////////////////////DOANH//////////////////////////////////////////////////////////////////////////////

    // 5.6.1 KhaiPN
    @Query(value = " select * \n" +
            " from real_estate_news\n " +
            " where address like concat('%',trim(:address),'%')\n " +
            " and kind_of_news like concat('%',trim(:kindOfNews),'%')\n " +
            " and real_estate_type_id like concat('%',trim(:realEstateType),'%')\n " +
            " and direction_id like concat('%',trim(:direction),'%')\n " +
            " and area between :minArea and :maxArea " +
            " and price between :minPrice and :maxPrice " +
            " and approval =2 ", nativeQuery = true, countQuery = " select count(*) from real_estate_news " +
            " where address like concat('%',trim(:address),'%') " +
            " and kind_of_news like concat('%',trim(:kindOfNews),'%') " +
            " and real_estate_type_id like concat('%',trim(:realEstateType),'%') " +
            " and direction_id like concat('%',trim(:direction),'%') " +
            " and area between :minArea and :maxArea " +
            " and price between :minPrice and :maxPrice " +
            " and approval =2 ")
    Page<RealEstateNews> findAllRealEstateNewsByFilter(@Param("address")String address,
                                                       @Param("kindOfNews")String kindOfNews,
                                                       @Param("realEstateType")String realEstateType,
                                                       @Param("direction")String direction,
                                                       @Param("minArea")String minArea,
                                                       @Param("maxArea")String maxArea,
                                                       @Param("minPrice")String minPrice,
                                                       @Param("maxPrice")String maxPrice,
                                                       Pageable pageable);


}
