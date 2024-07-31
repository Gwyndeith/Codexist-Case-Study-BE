package org.codexist.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.codexist.interfaces.Coordinate;

@Getter
public class SearchItem implements Coordinate {

    private final double radius;
    private final double longitude;
    private final double latitude;

    @JsonCreator
    public SearchItem(double latitude, double longitude, double radius) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "\nSearchItem {\n\tlongitude=" + longitude + "\n\tlatitude=" + latitude + "\n\tradius=" + radius + "\n}";
    }
}
