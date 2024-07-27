package org.codexist.restcontrollers;

import org.codexist.services.MapService;
import org.codexist.models.MapPoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;

@RestController
@RequestMapping
public class MapController {

    MapService mapService = new MapService();

    @GetMapping("/findNearbyLocations")
    public ArrayList<MapPoint> findNearbyLocations(@RequestParam double latitude, @RequestParam double longitude, @RequestParam double radius) {
        MapPoint requestMapPoint = new MapPoint(latitude, longitude);
        ArrayList<MapPoint> neighbors = mapService.findNearbyLocations(requestMapPoint, radius);
        System.out.println(neighbors);
        return neighbors;
    }
}
