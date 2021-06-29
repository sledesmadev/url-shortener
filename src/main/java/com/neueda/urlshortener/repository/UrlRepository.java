package com.neueda.urlshortener.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.neueda.urlshortener.model.Url;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long>{
	
    @Query("SELECT count(u) FROM Url u WHERE upper(u.shortUrl) LIKE UPPER(CONCAT('%', :shortUrl,'%'))")
    Integer findByShortUrl(@Param("shortUrl") String shortUrl);
    
    @Query("SELECT u.fullUrl FROM Url u WHERE upper(u.shortUrl) LIKE UPPER(CONCAT('%', :shortUrl,'%'))")
    String getFullUrl(@Param("shortUrl") String shortUrl);
	
}
