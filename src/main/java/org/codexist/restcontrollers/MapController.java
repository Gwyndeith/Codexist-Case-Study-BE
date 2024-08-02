package org.codexist.restcontrollers;

import org.codexist.models.SearchItem;
import org.codexist.services.MapService;
import org.codexist.models.MapPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
public class MapController {

    @Autowired
    private MapService mapService;

    @GetMapping("/findNearbyLocations/")
    public ArrayList<MapPoint> findNearbyLocations(@RequestParam double latitude, @RequestParam double longitude, @RequestParam double radius) {
        MapPoint requestMapPoint = new MapPoint(latitude, longitude);
        return mapService.findNearbyLocations(requestMapPoint, radius);
    }

    @GetMapping("/getSearchHistory")
    public ArrayList<SearchItem> getSearchHistory() {
        return mapService.getSearchHistory();
    }
}
