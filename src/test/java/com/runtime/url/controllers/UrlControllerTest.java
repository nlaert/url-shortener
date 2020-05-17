package com.runtime.url.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.runtime.url.dto.UrlDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UrlControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void shouldReturnShortUrlGivenOriginalUrl() throws Exception {
        UrlDto dto = new UrlDto();
        dto.setOriginalUrl("www.duck.com");
        mvc.perform(post("/shorten")
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortenUrl").exists());
    }

    @Test
    void shouldReturn400WhenGivenEmptyOriginalUrl() throws Exception {
        UrlDto dto = new UrlDto();
        mvc.perform(post("/shorten")
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnOriginalUrlWhenGivenShortenUrl() throws Exception {
        UrlDto dto = new UrlDto();
        dto.setOriginalUrl("www.duck.com");
        String shortenUrl = getANewShortenUrl(dto);
        mvc.perform(get(shortenUrl))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, dto.getOriginalUrl()));
    }

    @Test
    void shouldReturn404WhenGivenNonExistingShortenUrl() throws Exception {
        mvc.perform(get("/1shortenUrl")
                .contentType(MediaType.APPLICATION_JSON))
//                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private String getANewShortenUrl(UrlDto dto) throws Exception {
        // This call is only meant to get the generated url
        String response = mvc.perform(post("/shorten")
                .content(asJsonString(dto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortenUrl").exists())
                .andReturn().getResponse().getContentAsString();

        UrlDto returnedDto = new ObjectMapper().readValue(response, UrlDto.class);
        return returnedDto.getShortenUrl();
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}