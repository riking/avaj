package org.riking.home._42.avaj;

public class Helicopter extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    /* package */ Helicopter(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    @Override
    public void updateConditions() {

    }

    @Override
    public void registerTower(WeatherTower weatherTower) {

    }
}
