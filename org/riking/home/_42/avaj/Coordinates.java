package org.riking.home._42.avaj;

public class Coordinates {
    private int longitude;
    private int latitude;
    private int height;

    /* package */ Coordinates(int longitude, int latitude, int height) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.height = height;
    }


    public int getLongitude() {
        return longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public int getHeight() {
        return height;
    }

}
