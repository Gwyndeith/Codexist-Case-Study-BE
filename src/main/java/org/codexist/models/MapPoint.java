package org.codexist.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.codexist.interfaces.Coordinate;

@Getter
public class MapPoint implements Coordinate {

    private final String name;
    private final double longitude;
    private final double latitude;

    @JsonCreator
    public MapPoint(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.name = "";
    }

    public MapPoint(String name, double latitude, double longitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "\nMapPoint {\n\tname=" + name + ",\n\tlongitude=" + longitude + ",\n\tlatitude=" + latitude + "\n}";
    }

}
