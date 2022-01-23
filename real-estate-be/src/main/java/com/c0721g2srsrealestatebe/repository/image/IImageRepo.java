package com.c0721g2srsrealestatebe.repository.image;

import com.c0721g2srsrealestatebe.model.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface IImageRepo extends JpaRepository<Image,Long> {

    @Query
    Image findImageByUrl(String url);

    @Query(value = "SELECT MAX(id) FROM images;", nativeQuery = true)
    Long lastId();

    @Modifying
    @Transactional
    @Query(value = "insert into images (id, url) VALUES (:id,:url)", nativeQuery = true)
    Integer saveImg(@Param("id")Long id, @Param("url")String url);


    @Modifying
    @Transactional
    @Query(value = "insert into real_estate_news_image_list (real_estate_news_id,image_list_id) VALUES (:real_estate_news_id,:image_list_id)", nativeQuery = true)
    void saveRelative(@Param("image_list_id")Long imgId, @Param("real_estate_news_id")String realEstateId);
}
