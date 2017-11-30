package org.riking.home._42.avaj;

import java.io.PrintStream;

public abstract class Aircraft {
    protected long id;
    protected String name;
    protected Coordinates coordinates;

    private static long idCounter;

    protected Aircraft(String name, Coordinates coordinates) {
        this.id = Aircraft.nextId();
        this.name = name;
        this.coordinates = coordinates;
    }

    private static long nextId() {
        idCounter = idCounter + 1;
        return idCounter;
    }

    public String getDescriptor() {
        return this.getClass().getSimpleName() + "#" + this.name + "(" + this.id + ")";
    }

    protected void log(String message) {
        Logger.getLogger().getOutput().printf("%s: %s\n",
                this.getDescriptor(), message);
    }

    protected void logLanding(Coordinates coordinates) {
        Logger.getLogger().getOutput().format("%s landing at (%d, %d).\n",
                this.getDescriptor(),
                coordinates.getLongitude(),
                coordinates.getLatitude());
    }

    protected void adjustCoordinates(int longitudeDelta, int latitudeDelta, int heightDelta) {
        int longitude = this.coordinates.getLongitude();
        int latitude = this.coordinates.getLatitude();
        int height = this.coordinates.getHeight();

        longitude += longitudeDelta;
        latitude += latitudeDelta;
        height += heightDelta;

        if (height > 100)
            height = 100;
        if (height < 0)
            height = 0;

        this.coordinates = new Coordinates(longitude, latitude, height);
    }
}
