package com.neueda.urlshortener.service;

import java.nio.charset.Charset;
import java.time.Instant;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.neueda.urlshortener.dto.FullUrlDTO;
import com.neueda.urlshortener.error.ErrorMessage;
import com.neueda.urlshortener.exception.UrlException;
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
	public String setShortUrl(final FullUrlDTO fullUrl, final String baseUrl) {
		 Integer count = 1;
		 String generatedString = null;
		 Url newUrl = new Url();
		 
		
		
		 if(fullUrl.getAlias() == null || fullUrl.getAlias().isEmpty()) {
			 //Generate random until doesn't exists.
			 do {
			     generatedString = UrlUtils.getRandomAlphanumeric();
			     count = urlRepository.findByShortUrl(generatedString);
			 }while(count > 0);
		 }else {
			 count = urlRepository.findByShortUrl(fullUrl.getAlias().trim());
			 if(count > 0) {
				 log.error("The alias "+fullUrl.getAlias()+" exists.");
				 ErrorMessage error = new ErrorMessage("The Alias currently exists !!.");
				 throw new ResponseStatusException(HttpStatus.FOUND, error.getMessage());
			 }else {
				 generatedString = fullUrl.getAlias().trim();
			 }
		 }
		 
		 newUrl.setFullUrl(fullUrl.getUrl());
		 newUrl.setShortUrl(baseUrl+"/"+generatedString);
		 newUrl.setCreatedTime(Instant.now());
		 
		 urlRepository.save(newUrl);
		 
		 return newUrl.getShortUrl();

	}

	@Override
	public String getFullUrl(String shortUrl) {
		
		if(urlRepository.findByShortUrl(shortUrl) > 0) {
			return urlRepository.getFullUrl(shortUrl);
		}else {
			 log.error("The generated url /"+shortUrl+" doesn't exists.");
			 ErrorMessage error = new ErrorMessage("The URL Doesn't exists");
			 throw new ResponseStatusException(HttpStatus.NOT_FOUND, error.getMessage());
		}
	
	}

	
}
