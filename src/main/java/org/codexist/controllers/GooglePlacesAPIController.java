package org.codexist.controllers;

import org.codexist.services.GooglePlacesService;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class GooglePlacesAPIController {
    private final JsonParser jsonParser;
    private final GooglePlacesService googlePlacesService;

    public GooglePlacesAPIController(JsonParser jsonParser, RestTemplate restTemplate) {
        this.jsonParser = jsonParser;
        this.googlePlacesService = new GooglePlacesService(restTemplate);
    }

    public Map<String, Object> searchNearbyPlaces(String latitude, String longitude, String radius) {
        ResponseEntity<String> responseEntity = googlePlacesService.searchNearbyPlaces(latitude, longitude, radius);
        return parseData(responseEntity.getBody());
    }

    private Map<String, Object> parseData(String responseBody) {
        return this.jsonParser.parseMap(responseBody);
    }
}
