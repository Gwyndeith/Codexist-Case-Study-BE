package org.codexist.models;

import lombok.Getter;
import org.codexist.interfaces.Coordinate;

@Getter
public class MapPoint implements Coordinate {

    private final double longitude;
    private final double latitude;

    public MapPoint(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

}
