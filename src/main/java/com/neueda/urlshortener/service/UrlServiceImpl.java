package com.neueda.urlshortener.service;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.urlshortener.model.Url;
import com.neueda.urlshortener.repository.UrlRepository;
import com.neueda.urlshortener.utils.UrlUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UrlServiceImpl implements UrlService{

	@Autowired
	private UrlRepository urlRepository;

	@Override
	public String setShortUrl(final String fullUrl, final String baseUrl) {
		 Integer count = 1;
		 String generatedString = null;
		 Url newUrl = new Url();
		 
		 //Generate random until doesn't exists.
		 do {
		     generatedString = UrlUtils.getRandomAlphanumeric();
		     count = urlRepository.findByShortUrl(generatedString);
		 }while(count > 0);
		 
		 newUrl.setFullUrl(fullUrl);
		 newUrl.setShortUrl(baseUrl+"/"+generatedString);
		 newUrl.setCreatedTime(Instant.now());
		 
		 urlRepository.save(newUrl);
		 
		 return newUrl.getShortUrl();

	}

	@Override
	public String getFullUrl(String shortUrl) {
		return urlRepository.getFullUrl(shortUrl);
	}

	
}
