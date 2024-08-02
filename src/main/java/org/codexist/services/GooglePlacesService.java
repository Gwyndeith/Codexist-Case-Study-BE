package org.codexist.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GooglePlacesService {

    @Value("${googleplaces.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> searchNearbyPlaces(String latitude, String longitude, String radius) {
        System.out.println(this.apiKey);
        String url = UriComponentsBuilder.fromHttpUrl("https://places.googleapis.com/v1/places:searchNearby").toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Goog-Api-Key", this.apiKey);
        headers.add("X-Goog-FieldMask", "places.displayName,places.location");

        HttpEntity<String> entity = new HttpEntity<>("{\"includedTypes\": [\n" +
                "        \"restaurant\"\n" +
                "    ],\"locationRestriction\": {\"circle\": {\"center\": {\"latitude\":" + latitude + ",\"longitude\":" + longitude + "},\"radius\":" + radius + "}}}", headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }
}