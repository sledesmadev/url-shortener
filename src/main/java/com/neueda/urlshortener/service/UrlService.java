package com.neueda.urlshortener.service;

import java.time.Instant;

public interface UrlService {
	
	String setShortUrl(String fullUrl, String baseUrl);
	
	String getFullUrl(String shortUrl);

}
