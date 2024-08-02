package org.codexist.services;

import org.codexist.models.MapPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class MongoDBAtlasService {

    @Value("${mongoatlas.apiKey}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> getSearchedCoordinateFromDB(double latitude, double longitude, double radius) {
        String url = UriComponentsBuilder.fromHttpUrl("https://eu-central-1.aws.data.mongodb-api.com/app/data-afgjmpq/endpoint/data/v1/action/findOne").toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("api-key", this.apiKey);
        headers.add("Accept", "application/json");

        String requestBody = "{"
                + "\"dataSource\":\"CodexistCluster\","
                + "\"database\":\"searched_map_points\","
                + "\"collection\":\"searchedCoordinates\","
                + "\"filter\":{"
                + "\"latitude\":\"" + latitude + "\","
                + "\"longitude\":\"" + longitude + "\","
                + "\"radius\":\"" + radius + "\""
                + "}"
                + "}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }

    public ResponseEntity<String> getMapPointRelatedNearbyLocations(String searchId) {
        String url = UriComponentsBuilder.fromHttpUrl("https://eu-central-1.aws.data.mongodb-api.com/app/data-afgjmpq/endpoint/data/v1/action/find").toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("api-key", this.apiKey);
        headers.add("Accept", "application/json");

        String requestBody = "{"
                + "\"dataSource\":\"CodexistCluster\","
                + "\"database\":\"searched_map_points\","
                + "\"collection\":\"mappoints\","
                + "\"filter\":{"
                + "\"search_id\":\"" + searchId + "\""
                + "}"
                + "}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }

    public ResponseEntity<String> createNewSearchCoordinate(MapPoint mapPoint, double radius) {
        String url = UriComponentsBuilder.fromHttpUrl("https://eu-central-1.aws.data.mongodb-api.com/app/data-afgjmpq/endpoint/data/v1/action/insertOne").toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("api-key", this.apiKey);

        String requestBody = "{"
                + "\"dataSource\":\"CodexistCluster\","
                + "\"database\":\"searched_map_points\","
                + "\"collection\":\"searchedCoordinates\","
                + "\"document\":{"
                + "\"latitude\":\"" + mapPoint.getLatitude() + "\","
                + "\"longitude\":\"" + mapPoint.getLongitude() + "\","
                + "\"radius\":\"" + radius + "\""
                + "}"
                + "}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }

    public void addNearbyLocationToDB(List<MapPoint> nearbyLocations, String searchId) {
        String url = UriComponentsBuilder.fromHttpUrl("https://eu-central-1.aws.data.mongodb-api.com/app/data-afgjmpq/endpoint/data/v1/action/insertMany").toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("api-key", this.apiKey);
        StringBuilder stringBuilder = new StringBuilder();

        for (MapPoint nearbyLocation : nearbyLocations) {
            stringBuilder.append("{\"latitude\":\"")
                    .append(nearbyLocation.getLatitude())
                    .append("\"")
                    .append(",\"longitude\":\"")
                    .append(nearbyLocation.getLongitude())
                    .append("\"")
                    .append(",\"name\":\"")
                    .append( nearbyLocation.getName())
                    .append("\"")
                    .append(",\"search_id\":\"")
                    .append(searchId)
                    .append("\"},");
        }
        String requestBody = "{"
                + "\"dataSource\":\"CodexistCluster\","
                + "\"database\":\"searched_map_points\","
                + "\"collection\":\"mappoints\","
                + "\"documents\":["
                + stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "")
                + "]"
                + "}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        restTemplate.postForEntity(url, entity, String.class);
    }

    public ResponseEntity<String> getSearchHistory() {
        String url = UriComponentsBuilder.fromHttpUrl("https://eu-central-1.aws.data.mongodb-api.com/app/data-afgjmpq/endpoint/data/v1/action/find").toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("api-key", this.apiKey);

        String requestBody = "{"
                + "\"dataSource\":\"CodexistCluster\","
                + "\"database\":\"searched_map_points\","
                + "\"collection\":\"searchedCoordinates\","
                + "\"filter\":{"
                + "}"
                + "}";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }
}
