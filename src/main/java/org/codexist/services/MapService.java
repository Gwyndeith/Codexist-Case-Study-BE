package org.codexist.services;


import org.codexist.models.MapPoint;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
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

    public ArrayList<MapPoint> findNearbyLocations(MapPoint mapPoint, double radius) {
        ArrayList<MapPoint> nearbyLocations = new ArrayList<>();
        ResponseEntity<String> responseEntity = googlePlacesService.searchNearbyPlaces(Double.toString(mapPoint.getLatitude()), Double.toString(mapPoint.getLongitude()), Double.toString(radius));
        JsonParser mapPointParser = JsonParserFactory.getJsonParser();
        Map<String, Object> map = mapPointParser.parseMap(responseEntity.getBody());

//        System.out.println("Map: " + map.get("places"));
        ArrayList placesArray = (ArrayList) map.get("places");

        for (int i = 0; i < placesArray.size(); i++) {
            LinkedHashMap place = (LinkedHashMap) placesArray.get(i);
            LinkedHashMap location = (LinkedHashMap) place.get("location");
            LinkedHashMap locationName = (LinkedHashMap) place.get("displayName");

            nearbyLocations.add(new MapPoint(locationName.get("text").toString(), Double.parseDouble(location.get("latitude").toString()), Double.parseDouble(location.get("longitude").toString())));
//            System.out.println("Nearby location details:\n\tname: " + locationName.get("text") + "\n\tlatitude:" + location.get("latitude") + "\n\tlongitude:" + location.get("longitude"));
        }
        return nearbyLocations;
    }
}
