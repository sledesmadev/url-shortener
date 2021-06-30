package com.neueda.urlshortener.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.neueda.urlshortener.dto.FullUrlDTO;
import com.neueda.urlshortener.dto.ShortUrlDTO;
import com.neueda.urlshortener.error.ErrorMessage;
import com.neueda.urlshortener.service.UrlService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
public class UrlController {

	@Autowired
	private UrlService urlService;
	
	
	/**
	 * @param fullUrl - The url to be shorten
	 * @param request - To get request and return validations
	 * @return shorten URL
	 */
	@PostMapping("/shorten")
    public ResponseEntity<Object> saveUrl(@RequestBody FullUrlDTO fullUrl, HttpServletRequest request) {

		final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
		ShortUrlDTO shortUrl = new ShortUrlDTO();
		
		
        if (!urlValidator.isValid(fullUrl.getUrl())) {
            log.error("Invalid URL.");
            ErrorMessage error = new ErrorMessage("Invalid URL.");
            return ResponseEntity.badRequest().body(error);
        }
        
        String baseUrl = null;
        try {
        	URL url = new URL(request.getRequestURL().toString());
            baseUrl = url.getProtocol()+"://"+url.getHost()+":"+url.getPort();
        } catch (MalformedURLException e) {
            log.error("Malformed request url");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requested url is invalid", e);
        }

        shortUrl.setUrl(urlService.setShortUrl(fullUrl, baseUrl));

        return new ResponseEntity<>(shortUrl, HttpStatus.OK);
    }
	
	/**
	 * @param shortUrl - Generated url to find the url to redirect
	 * @param response - Redirect to full url
	 */
	@GetMapping("/{shortUrl}")
	public void redirectFromShortUrl(@PathVariable String shortUrl, HttpServletResponse response) {
		try {
			FullUrlDTO url = new FullUrlDTO(urlService.getFullUrl(shortUrl), null);
            response.sendRedirect(url.getUrl());
		}catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url not found", e);
		} catch (IOException e) {
			 throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot redirect to the URL", e);
		}
	}
}
