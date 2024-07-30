package org.codexist.services;


import org.codexist.models.MapPoint;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class MapService {

    RestTemplate restTemplate = new RestTemplate();
    GooglePlacesService googlePlacesService = new GooglePlacesService(restTemplate);
    MongoDBAtlasService mongoDBAtlasService = new MongoDBAtlasService(restTemplate);

    public ArrayList<MapPoint> findNearbyLocations(MapPoint mapPoint, double radius) {
        ArrayList<MapPoint> nearbyLocations = new ArrayList<>();
        JsonParser jsonParser = JsonParserFactory.getJsonParser();

        ResponseEntity<String> databaseResponseEntity = mongoDBAtlasService.getSearchedCoordinateFromDB(mapPoint.getLatitude(), mapPoint.getLongitude(), radius);
        Map<String, Object> dbDataMap = jsonParser.parseMap(databaseResponseEntity.getBody());
        System.out.println("dbData: " + dbDataMap);
        if (dbDataMap.get("document") != null) {
            ResponseEntity<String> dbNearbyLocationsResponseEntity = new ResponseEntity<>(HttpStatusCode.valueOf(200));
            for (String key : dbDataMap.keySet()) {
                Map<String, Object> dbNearbyLocationsMap = (Map<String, Object>) dbDataMap.get(key);
                dbNearbyLocationsResponseEntity = mongoDBAtlasService.getMapPointRelatedNearbyLocations(String.valueOf(dbNearbyLocationsMap.get("_id")));
            }

            System.out.println(dbNearbyLocationsResponseEntity.getBody());
            Map<String, Object> nearbyLocationResponseEntity = jsonParser.parseMap(dbNearbyLocationsResponseEntity.getBody());
            ArrayList nearbyLocationList = (ArrayList) nearbyLocationResponseEntity.get("documents");

            for (int i = 0; i < nearbyLocationList.size(); i++) {
                LinkedHashMap location = (LinkedHashMap) nearbyLocationList.get(i);
                nearbyLocations.add(new MapPoint(location.get("name").toString(), Double.parseDouble(location.get("latitude").toString()), Double.parseDouble(location.get("longitude").toString())));
            }

            System.out.println("Search related nearby location list: " + nearbyLocationList);
            return nearbyLocationList;
        } else {
            ResponseEntity<String> createSearchCoordinateResponseEntity = mongoDBAtlasService.createNewSearchCoordinate(mapPoint, radius);
            Map<String, Object> newSearchCoordinateMap = jsonParser.parseMap(createSearchCoordinateResponseEntity.getBody());

            ResponseEntity<String> responseEntity = googlePlacesService.searchNearbyPlaces(Double.toString(mapPoint.getLatitude()), Double.toString(mapPoint.getLongitude()), Double.toString(radius));
            Map<String, Object> map = jsonParser.parseMap(responseEntity.getBody());

//        System.out.println("Map: " + map.get("places"));
            ArrayList placesArray = (ArrayList) map.get("places");

            if (placesArray.size() > 0) {
                for (int i = 0; i < placesArray.size(); i++) {
                    LinkedHashMap place = (LinkedHashMap) placesArray.get(i);
                    LinkedHashMap location = (LinkedHashMap) place.get("location");
                    LinkedHashMap locationName = (LinkedHashMap) place.get("displayName");

                    nearbyLocations.add(new MapPoint(locationName.get("text").toString(), Double.parseDouble(location.get("latitude").toString()), Double.parseDouble(location.get("longitude").toString())));
//            System.out.println("Nearby location details:\n\tname: " + locationName.get("text") + "\n\tlatitude:" + location.get("latitude") + "\n\tlongitude:" + location.get("longitude"));
                }
                mongoDBAtlasService.addNearbyLocationToDB(nearbyLocations, String.valueOf(newSearchCoordinateMap.get("insertedId")));
                return nearbyLocations;
            } else {
                return new ArrayList<>();
            }
        }
    }
}
