package org.codexist.controllers;

import org.codexist.services.GooglePlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GooglePlacesAPIController {
    private final JsonParser jsonParser = JsonParserFactory.getJsonParser();
    @Autowired
    private GooglePlacesService googlePlacesService;

    public Map<String, Object> searchNearbyPlaces(String latitude, String longitude, String radius) {
        ResponseEntity<String> responseEntity = googlePlacesService.searchNearbyPlaces(latitude, longitude, radius);
        return parseData(responseEntity.getBody());
    }

    private Map<String, Object> parseData(String responseBody) {
        return this.jsonParser.parseMap(responseBody);
    }
}
