package com.neueda.urlshortener.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neueda.urlshortener.dto.FullUrlDTO;
import com.neueda.urlshortener.repository.UrlRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UrlServiceTest {

	@Autowired
	private MockMvc mock;
	
	@Autowired
	private UrlService urlService;
	
	
	@Test
    public void givenFullUrlReturnStatusOk() throws Exception {
        FullUrlDTO fullUrl = new FullUrlDTO("https://google.es", null);

        mock.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isOk());
    }
	
	@Test
    public void givenFullUrlAliasReturnStatusOk() throws Exception {
        FullUrlDTO fullUrl = new FullUrlDTO("https://google.es", "abcde");

        mock.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isOk());
    }
	
	@Test
    public void givenFullUrlReturnStatusInvalid() throws Exception {
        FullUrlDTO fullUrl = new FullUrlDTO("hts://google.es", null);

        mock.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isBadRequest());
    }
	
	@Test
    public void givenFullUrlAliasReturnStatusInvalid() throws Exception {
        FullUrlDTO fullUrl = new FullUrlDTO("hts://google.es", "aaa");

        mock.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isBadRequest());
    }
	
	@Test
    public void givenFullUrlRepeatedAliasReturnStatusInvalid() throws Exception {
        FullUrlDTO fullUrl = new FullUrlDTO("https://google.es", "aaa");

        mock.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isOk());
        
        mock.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isFound());
    }
	
	@Test
    public void givenShortUrlReturnStatusValid() throws Exception {
		
		FullUrlDTO fullUrl = new FullUrlDTO("https://google.es", "abc");
		
        mock.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isOk());

        mock.perform(get("/abc"))
                .andExpect(redirectedUrl("https://google.es"));
    }
	
	@Test
    public void givenNotExistingShortUrlReturnStatusNotFound() throws Exception {
		
		FullUrlDTO fullUrl = new FullUrlDTO("https://google.es", "google");
		
        mock.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isOk());

        mock.perform(get("/xyz"))
        .andExpect(status().isNotFound());
    }
	
	@Test
    public void givenBlanksURLInvalid() throws Exception {
		
		FullUrlDTO fullUrl = new FullUrlDTO("", null);
		
        mock.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isBadRequest());
    }
	
	@Test
    public void givenOnlyAliasInvalid() throws Exception {
		
		FullUrlDTO fullUrl = new FullUrlDTO("", "google");
		
        mock.perform(post("/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isBadRequest());
    }
	
	@Test
    public void NonExistingReturnStatusNotFound() throws Exception {
        mock.perform(get("/lpo"))
        .andExpect(status().isNotFound());
    }
	
	
	
	
    public static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
	
}
