package com.example.staff_management.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PictureService {
    public String getPictureInfoByRestTemplate() {
        String url = "https://api.thecatapi.com/v1/images/search";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    public String getPictureInfoByWebClient() {
        WebClient webClient = WebClient.create("https://api.thecatapi.com/v1/images/search");
        return webClient.get().retrieve().bodyToMono(String.class).block();

    }

}
