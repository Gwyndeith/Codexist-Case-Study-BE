package org.codexist.controllers;

import org.codexist.models.MapPoint;
import org.codexist.services.MongoDBAtlasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MongoDBAtlasController {

    private final JsonParser jsonParser = JsonParserFactory.getJsonParser();
    @Autowired
    private MongoDBAtlasService mongoDBAtlasService;

    public Map<String, Object> getSearchedCoordinateFromDB(double latitude, double longitude, double radius) {
        ResponseEntity<String> databaseResponseEntity = mongoDBAtlasService.getSearchedCoordinateFromDB(latitude, longitude, radius);
        return parseData(databaseResponseEntity.getBody());
    }

    public Map<String, Object> getSearchHistory() {
        ResponseEntity<String> databaseResponseEntity = mongoDBAtlasService.getSearchHistory();
        return parseData(databaseResponseEntity.getBody());
    }

    public Map<String, Object> getMapPointRelatedNearbyLocations(String searchId) {
        ResponseEntity<String> databaseResponseEntity = mongoDBAtlasService.getMapPointRelatedNearbyLocations(searchId);
        return parseData(databaseResponseEntity.getBody());
    }

    public Map<String, Object> createNewSearchCoordinate(MapPoint mapPoint, double radius) {
        ResponseEntity<String> databaseResponseEntity = mongoDBAtlasService.createNewSearchCoordinate(mapPoint, radius);
        return parseData(databaseResponseEntity.getBody());
    }

    public void addNearbyLocationToDB(List<MapPoint> nearbyLocations, String searchId) {
        mongoDBAtlasService.addNearbyLocationToDB(nearbyLocations, searchId);
    }

    private Map<String, Object> parseData(String responseBody) {
        return this.jsonParser.parseMap(responseBody);
    }
}
