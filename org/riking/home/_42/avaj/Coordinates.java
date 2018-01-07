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

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != Coordinates.class) {
            return false;
        }
        Coordinates o = (Coordinates) other;
        return this.longitude == o.longitude &&
                this.latitude == o.latitude &&
                this.height == o.height;
    }

    @Override
    public int hashCode() {
        // height ranges from 0 to 100
        // provide range of 4k before looping long/lat
        return this.height + 100 * (this.longitude + (1 << 12) * (this.latitude));
    }
}
