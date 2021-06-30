package com.neueda.urlshortener.controller;


import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.neueda.urlshortener.model.Url;
import com.neueda.urlshortener.repository.UrlRepository;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@DataJpaTest 
@RunWith(SpringRunner.class)
public class UrlRepositoryTest {

    @Autowired
    private UrlRepository urlRepository;
    
    
    @Test
    public void shouldInsert() {
        Url newUrl = new Url((long) 1, "http://example.com", "http://localhost:8080/abc", Instant.now());
        urlRepository.save(newUrl);
        assertThat(newUrl.getId(), equalTo((long)1));
    }
    
    @Test
    public void shouldReturnOne() {
    	Url newUrl = new Url((long) 1, "http://example.com", "http://localhost:8080/abc", Instant.now());
        urlRepository.save(newUrl);
    	String url = "http://localhost:8080/abc";
    	Integer cont = urlRepository.findByShortUrl(url);
    	assertThat(cont, equalTo(1));
    }
    
    @Test
    public void shouldReturnZero() {
    	Url newUrl = new Url((long) 1, "http://example.com", "http://localhost:8080/abc", Instant.now());
        urlRepository.save(newUrl);
    	String url = "http://localhost:8080/abcx";
    	Integer cont = urlRepository.findByShortUrl(url);
    	assertThat(cont, equalTo(0));
    }
    
    
}
