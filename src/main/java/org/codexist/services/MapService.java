package org.codexist.services;


import org.codexist.controllers.GooglePlacesAPIController;
import org.codexist.controllers.MongoDBAtlasController;
import org.codexist.models.MapPoint;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class MapService {

    private final JsonParser jsonParser = JsonParserFactory.getJsonParser();
    private final RestTemplate restTemplate = new RestTemplate();
    private final MongoDBAtlasController mongoDBAtlasController = new MongoDBAtlasController(jsonParser, restTemplate);
    private final GooglePlacesAPIController googlePlacesAPIController = new GooglePlacesAPIController(jsonParser, restTemplate);

    public ArrayList<MapPoint> findNearbyLocations(MapPoint mapPoint, double radius) {
        ArrayList<MapPoint> nearbyLocations = new ArrayList<>();

        Map<String, Object> dbDataMap = mongoDBAtlasController.getSearchedCoordinateFromDB(mapPoint.getLatitude(), mapPoint.getLongitude(), radius);
        if (dbDataMap.get("document") != null) {
            Map<String, Object> nearbyLocationResponseEntity = new LinkedHashMap<>();
            for (String key : dbDataMap.keySet()) {
                Map<String, Object> dbNearbyLocationsMap = (Map<String, Object>) dbDataMap.get(key);
                nearbyLocationResponseEntity = mongoDBAtlasController.getMapPointRelatedNearbyLocations(String.valueOf(dbNearbyLocationsMap.get("_id")));
            }

            ArrayList nearbyLocationList = (ArrayList) nearbyLocationResponseEntity.get("documents");

            for (Object locationObj : nearbyLocationList) {
                LinkedHashMap location = (LinkedHashMap) locationObj;
                nearbyLocations.add(new MapPoint(location.get("name").toString(), Double.parseDouble(location.get("latitude").toString()), Double.parseDouble(location.get("longitude").toString())));
            }

            return nearbyLocations;
        } else {
            Map<String, Object> newSearchCoordinateMap = mongoDBAtlasController.createNewSearchCoordinate(mapPoint, radius);
            Map<String, Object> map = googlePlacesAPIController.searchNearbyPlaces(Double.toString(mapPoint.getLatitude()), Double.toString(mapPoint.getLongitude()), Double.toString(radius));

            ArrayList placesArray = (ArrayList) map.get("places");

            if (!placesArray.isEmpty()) {
                for (Object placeObj : placesArray) {
                    LinkedHashMap place = (LinkedHashMap) placeObj;
                    LinkedHashMap location = (LinkedHashMap) place.get("location");
                    LinkedHashMap locationName = (LinkedHashMap) place.get("displayName");

                    nearbyLocations.add(new MapPoint(locationName.get("text").toString(), Double.parseDouble(location.get("latitude").toString()), Double.parseDouble(location.get("longitude").toString())));
                }
                mongoDBAtlasController.addNearbyLocationToDB(nearbyLocations, String.valueOf(newSearchCoordinateMap.get("insertedId")));
                return nearbyLocations;
            } else {
                return new ArrayList<>();
            }
        }
    }
}
