package com.neueda.urlshortener.service;

import java.time.Instant;

import com.neueda.urlshortener.dto.FullUrlDTO;

public interface UrlService {
	
	String setShortUrl(FullUrlDTO fullUrl, String baseUrl);
	
	String getFullUrl(String shortUrl);

}
