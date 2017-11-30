package org.riking.home._42.avaj;

public class WeatherTower extends Tower {
    public String getWeather(Coordinates coordinates) {
        return WeatherProvider.getProvider().getCurrentWeather(coordinates);
    }

    /* package */ void changeWeather() {


        // TODO ...

        this.conditionsChanged();
    }
}
