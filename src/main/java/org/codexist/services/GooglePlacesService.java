package org.codexist.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class GooglePlacesService {

    private final String apiKey = "AIzaSyCO8pORQrIxc8nb7Bj04XMYvAVwXznDU3A";

    private final RestTemplate restTemplate;

    public GooglePlacesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> searchNearbyPlaces(String latitude, String longitude, String radius) {
        String url = UriComponentsBuilder.fromHttpUrl("https://places.googleapis.com/v1/places:searchNearby").toUriString();

//        System.out.println("Sending to google services:\n" + latitude + ", " + longitude + ", " + radius);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Goog-Api-Key", apiKey);
        headers.add("X-Goog-FieldMask", "places.displayName,places.location");

        HttpEntity<String> entity = new HttpEntity<>("{\"includedTypes\": [\n" +
                "        \"restaurant\"\n" +
                "    ],\"locationRestriction\": {\"circle\": {\"center\": {\"latitude\":" + latitude + ",\"longitude\":" + longitude + "},\"radius\":" + radius + "}}}", headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }
}