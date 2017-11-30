package org.riking.home._42.avaj;

public class JetPlane extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    /* package */ JetPlane(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    @Override
    public void updateConditions() {

    }

    @Override
    public void registerTower(WeatherTower weatherTower) {

    }
}
