package org.riking.home._42.avaj;

public final class AircraftFactory {
    private AircraftFactory() {}

    public static Flyable newAircraft(String type, String name, int longitude, int latitude, int height) {
        Coordinates coordinates = new Coordinates(longitude, latitude, height);

        switch (type) {
            case "Helicopter":
                return new Helicopter(name, coordinates);
            case "JetPlane":
                return new JetPlane(name, coordinates);
            case "Baloon":
            case "Balloon":
                // Errata #1: Baloon is misspelled; accept both spellings
                return new Baloon(name, coordinates);

            default:
                throw new IllegalArgumentException("Unknown aircraft type " + type);
        }
    }
}
