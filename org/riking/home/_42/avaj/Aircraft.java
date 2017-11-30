package org.riking.home._42.avaj;

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
}
