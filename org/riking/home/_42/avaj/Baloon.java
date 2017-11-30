package org.riking.home._42.avaj;

/**
 *
 * Note: typo in name is as per spec
 */
public class Baloon extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    /* package */ Baloon(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    @Override
    public void updateConditions() {

    }

    @Override
    public void registerTower(WeatherTower weatherTower) {

    }
}
